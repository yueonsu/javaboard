const detailContainer = document.querySelector('.detail-container');
if(detailContainer) {
    const url = new URL(location.href);
    const nBoardSeq = url.searchParams.get('nBoardSeq');

    /**
     * 게시글 출력
     */
    const title = detailContainer.querySelector('.title');
    const userName = detailContainer.querySelector('.username');
    const date = detailContainer.querySelector('.date');
    const hit = detailContainer.querySelector('.hit');
    const content = detailContainer.querySelector('.content');

    myFetch.get(`/ajax/board/detail`, data => {
        if(data.status === "200") {
            const item = data.result;
            title.innerText = item.stitle;
            userName.innerText = item.suserName;
            date.innerText = item.dtCreateDate;
            hit.innerText = item.nhitCount;
            content.innerHTML = item.scontent;
        }
    }, {nBoardSeq});

    /** 수정,삭제 버튼 */
    const modButton = document.querySelector('.mod-button');
    const delButton = document.querySelector('.del-button');
    modButton.addEventListener('click', () => {
        location.href = `/board/write?nBoardSeq=${nBoardSeq}`;
    });

    delButton.addEventListener('click', () => {
        myFetch.get('/ajax/board/delete', data => {
            console.log(data);
        }, {nBoardSeq});
    });
}