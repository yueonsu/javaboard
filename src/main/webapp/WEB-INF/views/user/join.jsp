<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>회원가입</title>
</head>
<body>
<div class="join-container">
    <div>
        <h2>회원가입</h2>
    </div>
    <form method="post" action="/user/join" class="join-form">
        <div>
            <input name="sName" class="form-control" type="text" placeholder="이름" aria-label="default input example">
        </div>
        <div>
            <input name="sId" class="form-control" type="text" placeholder="아이디" aria-label="default input example">
            <div class="default-button-wrap">
                <button class="btn btn-secondary use-id-button" type="button">사용</button>
            </div>
        </div>

        <div class="">
            <input name="sPassword" class="form-control" type="password" placeholder="비밀번호" aria-label="default input example">
        </div>

        <div class="">
            <input id="password-check" class="form-control" type="password" placeholder="비밀번호 재입력" aria-label="default input example">

            <div class="default-button-wrap">
                <button class="btn btn-secondary show-password" type="button">보이기</button>
                <button class="btn btn-secondary use-password-button" type="button">확인</button>
            </div>
        </div>

        <div>
            <input class="form-control" type="text" name="sEmail" placeholder="이메일" aria-label="default input example">
            <input id="input-code" class="form-control dis-none" type="text" placeholder="인증번호" aria-label="default input example">
            <div class="default-button-wrap">
                <button type="button" class="btn btn-secondary send-code-button">인증번호 전송</button>
                <button type="button" class="btn btn-secondary check-code-button dis-none">확인</button>
            </div>
        </div>
        <div class="bottom-button-wrap">
            <a href=""><button class="btn btn-secondary" type="button">메인으로</button></a>
            <button class="btn btn-secondary join-submit-button">가입</button>
            <div></div>
        </div>
    </form>
</div>
<%-- 이메일 인증번호 전송 로딩화면 --%>
<div class="spinner-container">
    <div class="spinner-border text-secondary" role="status">
        <span class="visually-hidden">Loading...</span>
    </div>
</div>
</body>
</html>
