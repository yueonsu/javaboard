const detailContainer = document.querySelector('.detail-container');
if (detailContainer) {
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
        if (data.status === "200") {
            const item = data.result;
            title.innerText = item.stitle;
            userName.innerText = item.suserName;
            date.innerText = item.dtCreateDate;
            hit.innerText = item.nhitCount;
            content.innerHTML = item.scontent;

            /**
             * 수정,삭제 버튼 ( 글쓴이만 보이게 )
             */
            if (item.fkUserSeq === data.loginUserPk) {
                /** 수정 */
                const modButton = document.createElement('a');
                modButton.innerText = '수정';
                modButton.href = `/board/write?nBoardSeq=${item.nboardSeq}`;
                modButton.classList.add('mod-button');

                /** 삭제 */
                const delButton = document.createElement('span');
                delButton.innerText = '삭제';
                delButton.classList.add('del-button');
                delButton.addEventListener('click', () => {
                    if (!confirm('정말로 삭제하시겠습니까?')) { return; }
                    myFetch.delete('/ajax/board/detail', data => {
                        if (data.status === '200') {
                            location.href = '/board/list';
                        }
                    }, {nBoardSeq});
                });
                const authDetail = document.querySelector('.auth-detail');
                authDetail.appendChild(modButton);
                authDetail.appendChild(delButton);
            }

            /**
             * 좋아요 표시 ( 로그인한 회원만 보이게 )
             */
            if (0 < data.loginUserPk) {
                const boardLike = document.querySelector('.board-like');
                const likeActive = `<i class="fa-solid fa-heart like"></i>`;
                const likedisabled = `<i class="fa-regular fa-heart like"></i>`;
                /**
                 * data.result = true -> 좋아요 눌러져있음
                 */
                myFetch.get('/ajax/like/show', data => {
                    if (data.status === '200') {
                        if (data.result) {
                            boardLike.innerHTML = likeActive;
                        } else {
                            boardLike.innerHTML = likedisabled;
                        }

                        const like = boardLike.querySelector('.like');

                        /**
                         * 좋아요 버튼 클릭 이벤트
                         */
                        boardLike.addEventListener('click', () => {
                            const data = {
                                fkboardSeq : item.nboardSeq
                            }

                            myFetch.post('/ajax/like/change', data => {
                                /**
                                 * data.result = 1 -> 좋아요 활성화
                                 */
                                if ('200' === data.status) {
                                    if (1 === data.result) {
                                        boardLike.innerHTML = likeActive;
                                    } else if (2 === data.result) {
                                        boardLike.innerHTML = likedisabled;
                                    }
                                }
                            }, data)
                        });
                    }
                }, {'nBoardSeq' : nBoardSeq});
            }

        }
    }, {nBoardSeq});
}