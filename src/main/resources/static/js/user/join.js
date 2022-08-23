// 회원가입 Form 태그
const joinFormElem = document.querySelector('.join-form');
if(joinFormElem) {
    // 이름
    const userNameInput = joinFormElem.querySelector('input[name=sName]');
    // 아이디
    const userIdInput = joinFormElem.querySelector('input[name=sId]');
    // 아이디 사용 버튼
    const useIdButton = joinFormElem.querySelector('.use-id-button');
    // 비밀번호
    const userPasswordInput = joinFormElem.querySelector('input[name=sPassword]');
    // 비밀번호 재입력
    const userPasswordCheckInput = joinFormElem.querySelector('#password-check');
    // 비밀번호 보이기/숨기기 버튼
    const showPasswordButton = joinFormElem.querySelector('.show-password');
    // 비밀번호 사용 버튼
    const usePasswordButton = joinFormElem.querySelector('.use-password-button');
    // 이메일
    const userEmailInput = joinFormElem.querySelector('input[name=sEmail]');
    // 인증번호
    const inputCode = joinFormElem.querySelector('#input-code');
    // 인증번호 전송 버튼
    const sendCodeButton = joinFormElem.querySelector('.send-code-button');
    // 인증번호 확인 버튼
    const checkCodeButton = joinFormElem.querySelector('.check-code-button');
    // 가입 버튼
    const submitButton = joinFormElem.querySelector('.join-submit-button');

    let nameCheck = false;
    let idRegCheck = false;
    let idCheck = false;
    let passwordCheck = false;
    let passwordRegCheck = false;
    let passwordSameCheck = false;
    let emailCheck = false;

    // 이름 유효성 검사
    userNameInput.addEventListener('keyup', () => {
        const classname = 'name-msg';
        const sName = userNameInput.value;
        
        // 오류메시지 삭제
        regexp.delMsg(classname);
        
        // 유효성검사
        if(regexp.isCheck('name', sName)) {
            regexp.makeMsg('사용가능한 이름입니다.', classname, userNameInput);
            nameCheck = true;
        } else {
            regexp.makeMsg(`한글 2~5자로 입력해 주세요.`, classname, userNameInput);
            nameCheck = false;
        }
        if(sName.length === 0) {
            regexp.delMsg(classname);
            nameCheck = false;
        }
    });

    // 아이디 유효성 검사
    userIdInput.addEventListener('keyup', () => {
        const classname = 'id-msg';
        const sId = userIdInput.value;

        regexp.delMsg(classname);

        if(!regexp.isCheck('id', sId)) {
            regexp.makeMsg('영어, 숫자 조합 5~20자로 입력해 주세요.', classname, userIdInput);
            idRegCheck = false;
            idCheck = false;
        } else {
            idRegCheck = true;
            idCheck = false;
        }
    });
    // 아이디 중복 검사
    useIdButton.addEventListener('click', () => {
        const classname = 'id-msg';
        const sId = userIdInput.value;
        regexp.delMsg(classname);
        if(idRegCheck) {
            myFetch.get('/ajax/user/idCheck', data => {
                if(data.result === 1) {
                    if(!confirm('사용가능한 아이디입니다. 사용하시겠습니까?')) { return; }
                    userIdInput.readOnly = true;
                    userIdInput.classList.add('disabled');
                    idCheck = true;
                } else if(data.result === 0) {
                    regexp.delMsg(classname);
                    regexp.makeMsg('가입된 아이디입니다.', classname, userIdInput);
                }
            }, {sId})
        } else {
            regexp.makeMsg('영어, 숫자 조합 5~20자로 입력해 주세요.', classname, userIdInput);
        }
    });

    // 비밀번호 유효성 검사
    userPasswordInput.addEventListener('keyup', () => {
        const classname = 'pw-msg';
        const sPassword = userPasswordInput.value;

        regexp.delMsg(classname);
        if(regexp.isCheck('password', sPassword)) {
            regexp.makeMsg('사용가능한 비밀번호입니다.', classname, userPasswordInput);
            passwordRegCheck = true;
        } else {
            regexp.makeMsg('영어, 숫자, 특수문자( _~!@# ) 조합 6~20자로 입력해 주세요.', classname, userPasswordInput);
            passwordRegCheck = false;
        }
    });

    // 비밀번호 재입력 검사
    userPasswordCheckInput.addEventListener('keyup', () => {
        if(!passwordRegCheck) { return; }
        const classname = 'pw-check-msg';
        const password = userPasswordInput.value;
        const checkPassword = userPasswordCheckInput.value;

        regexp.delMsg(classname);
        if(password === checkPassword) {
            regexp.makeMsg('비밀번호가 일치합니다.', classname, userPasswordCheckInput);
            passwordSameCheck = true;
        } else {
            regexp.makeMsg('비밀번호가 일치하지않습니다.', classname, userPasswordCheckInput);
            passwordSameCheck = false;
        }
    });

    // 비밀번호 보이기/숨기기
    showPasswordButton.addEventListener('click', () => {
        if(userPasswordInput.type === 'password') {
            userPasswordInput.type = 'text';
            userPasswordCheckInput.type = 'text';
            showPasswordButton.innerText = '숨기기';
        } else {
            userPasswordInput.type = 'password';
            userPasswordCheckInput.type = 'password';
            showPasswordButton.innerText = '보이기';
        }
    });

    // 비밀번호 확인 버튼
    usePasswordButton.addEventListener('click', () => {
        if(!passwordRegCheck) { alert('비밀번호를 확인해 주세요.'); return; }
        else if(!passwordSameCheck) { alert('비밀번호가 일치하지 않습니다.'); return; }
        if(!confirm('사용가능한 비밀번호입니다. 사용하시겠습니까?')) { return; }
        userPasswordInput.readOnly = true;
        userPasswordCheckInput.readOnly = true;
        userPasswordInput.classList.add('disabled');
        userPasswordCheckInput.classList.add('disabled');
        passwordCheck = true;
    });

    // 이메일 중복검사, 인증번호 전송
    sendCodeButton.addEventListener('click', () => {
        const classname = 'email-msg';
        const sEmail = userEmailInput.value;
        myFetch.get('/ajax/user/emailCheck', data => {
            regexp.delMsg(classname);
            if(data.result === 1) {
                sendCode(sEmail);
            } else {
                regexp.makeMsg('가입된 이메일입니다.', classname, userEmailInput);
            }
        }, {sEmail});
    });

    // 인증번호 전송
    const sendCode = (sEmail) => {
        // 인증번호 전송 로딩
        const spinnerContainer = document.querySelector('.spinner-container');
        spinnerContainer.style.display = 'flex';

        myFetch.get('/ajax/email/code', data => {
            // 로딩창 없앰
            spinnerContainer.style.display = 'none';
            
            // 인증번호가 null이 아니라면 이벤트 생성
            if(data.resultString != null) {
                alert('인증번호를 전송했습니다.');
                checkCodeButton.classList.remove('dis-none');
                inputCode.classList.remove('dis-none');
                sendCodeButton.classList.add('dis-none');
                userEmailInput.classList.add('dis-none');
                checkCodeButton.addEventListener('click', () => {
                    checkCode(data.resultString);
                })
            } else {
                alert('인증번호 전송에 실패했습니다.');
            }
        }, {sEmail});
    }

    // 인증번호 확인
    const checkCode = (code) => {
        const certificationCode = inputCode.value;
        if(code === certificationCode) {
            alert('확인이 완료됐습니다.');
            inputCode.classList.add('disabled');
            inputCode.readOnly = true;
            emailCheck = true;
        } else {
            alert('인증번호가 일치하지 않습니다.');
        }
    }

    // 회원가입 처리
    joinFormElem.addEventListener('submit', (e) => {
        e.preventDefault();
        if(!nameCheck) {
            alert('이름을 확인해 주세요.');
            return;
        }
        if(!idCheck) {
            alert('아이디를 확인해 주세요.');
            return;
        }
        if(!passwordCheck) {
            alert('비밀번호를 확인해 주세요.');
            return;
        }
        if(!emailCheck) {
            alert('이메일을 확인해 주세요.');
            return;
        }

        joinFormElem.submit();
    });
}