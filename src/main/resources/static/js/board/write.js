const writeContainer = document.querySelector('.write-container');
// 게시판 pk
const url = new URL(location.href);
const nBoardSeq=  url.searchParams.get('nBoardSeq');
// 제목
const title = writeContainer.querySelector('input[name=sTitle]');
// 내용
const content = document.querySelector('.ck-editor');
// 저장 버튼
const saveButton = writeContainer.querySelector('#write-save-button');

if(writeContainer) {
    // 등록, 수정 데이터
    let data;
    // CK에디터
    let edit;
    ClassicEditor
        .create( document.querySelector( '#editor' ) )
        .then( editor => {
            edit = editor;
        } )
        .catch( error => {
            console.error( error );
        } );

    /**
     * 글쓰기
     */
    if(nBoardSeq == null) {
        saveButton.addEventListener('click', () => {
            data = {
                stitle : title.value,
                scontent : edit.getData()
            }
            myFetch.post('/ajax/board/detail', data => {
                if(data.status === '200') {
                    location.href = `/board/detail?nBoardSeq=${data.result.nboardSeq}`;
                } else if (data.status === '400') {
                    alert('글 등록에 실패했습니다.');
                }
            }, data);
        });
    }

    /**
     * 글 수정
     */
    if(nBoardSeq > 0) {
        // 글 수정 데이터 불러오기
        myFetch.get('/ajax/board/mod', data => {
            modData = data.result;
            title.value = modData.stitle;
            setTimeout(() => {
                edit.setData(modData.scontent);
            }, 1);

        }, { nBoardSeq });

        /**
         * 저장 클릭 이벤트
         */
        saveButton.addEventListener('click', () => {
            data = {
                stitle : title.value,
                scontent : edit.getData(),
                nboardSeq : parseInt(nBoardSeq)
            }

            myFetch.put('/ajax/board/detail', data => {
                if(data.status === '200') {
                    location.href = `/board/detail?nBoardSeq=${nBoardSeq}`;
                }
            }, data);
        });
    }
}