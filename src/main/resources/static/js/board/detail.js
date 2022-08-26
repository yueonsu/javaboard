const detailContainer = document.querySelector('.detail-container');
if (detailContainer) {
    const url = new URL(location.href);
    const nBoardSeq = url.searchParams.get('nBoardSeq');

    const title = detailContainer.querySelector('.title');
    const userName = detailContainer.querySelector('.username');
    const date = detailContainer.querySelector('.date');
    const hit = detailContainer.querySelector('.hit');
    const content = detailContainer.querySelector('.content');

    /**
     * 댓글 리스트 가져오기
     */
    const getList = () => {
        myFetch.get('/ajax/comment', data => {
            /** 댓글 리스트 */
            const result = data.result;
            /** 회원pk */
            const loginUserPk = data.loginUserPk;
            /** 댓글 div */
            const commentList = document.querySelector('.comment-list');
            commentList.innerHTML = null;

            result.forEach(item => {
                /** 댓글 출력 */
                const commentDiv = document.createElement('div');
                commentDiv.classList.add('comment-content');
                commentDiv.innerHTML = `
                        <div>
                            <span>${item.username}</span>
                        </div>
                        <div class="comment-ctnt-wrap">
                            <span class="comment-ctnt">${item.scontent.replaceAll('\n', '<br/>')}</span>
                        </div>
                        <div>${item.dtCreateDate}</div>
                        <div class="comment-menu"></div>
                        <div class="user-data" data-iuser="${item.fkUserSeq}"></div>
                    `;
                commentList.appendChild(commentDiv);

                /** 대댓글 리스트 출력 */
                myFetch.get('/ajax/comment/reply', data => {
                    const list = data.result;
                    if('200' === data.status && 0 < list.length) {
                        makeReplyElem(commentDiv, list);
                    }
                }, { nCommentSeq : item.ncommentSeq })


                /** 답변 ( 로그인 한 회원만 ) */
                if(0 < loginUserPk) {
                    const commentCtnt = commentDiv.querySelector('.comment-ctnt-wrap');
                    const createReplySpan = document.createElement('span');
                    createReplySpan.classList.add('comment-reply-write-btn');
                    createReplySpan.innerText = '답변';
                    commentCtnt.appendChild(createReplySpan);

                    /** 답변 클릭 이벤트 */
                    createReplySpan.addEventListener('click', () => {
                        const isReplyForm = document.querySelector('.reply-write');
                        if(isReplyForm) {
                            isReplyForm.remove();
                            return;
                        }
                        const createReplyFormDiv = document.createElement('div');
                        createReplyFormDiv.classList.add('reply-write');
                        createReplyFormDiv.innerHTML = `
                                <div>
                                    <i class="fa-solid fa-right-long"></i>
                                </div>
                                <div>
                                    <textarea class="reply-content"></textarea>
                                    <button class="btn btn-secondary reply-submit">답변달기</button>
                                </div>
                            `;
                        commentDiv.after(createReplyFormDiv);

                        /** 대댓글 답변달기 클릭 이벤트 */
                        const writeReplyButton = createReplyFormDiv.querySelector('.reply-submit');
                        writeReplyButton.addEventListener('click', () => {
                            const replyData = {
                                sContent : createReplyFormDiv.querySelector('.reply-content').value,
                                nReply : item.ncommentSeq,
                                fkBoardSeq : nBoardSeq
                            }
                            myFetch.post('/ajax/comment/reply', data => {
                                if('200' === data.status) {
                                    const list = [];
                                    list.push(data.result)
                                    createReplyFormDiv.remove();
                                    makeReplyElem(commentDiv, list);
                                }
                            }, replyData);
                        });
                    });
                }

                /** 수정, 삭제 ( 본인이 쓴 댓글만 ) */
                if(item.fkUserSeq === loginUserPk) {
                    const commentMenu = commentDiv.querySelector('.comment-menu');
                    commentMenu.innerHTML = `
                            <span class="comment-mod">수정</span>
				            <span class="comment-del">삭제</span>
                        `;
                    /** 수정 클릭 이벤트 */
                    const commentMod = commentMenu.querySelector('.comment-mod');
                    commentMod.addEventListener('click', () => {
                        const createModElem = makeModElem(commentDiv, item);
                        const saveButton = document.querySelector('.reply-submit');
                        saveButton.addEventListener('click', () => {
                            const sContent = document.querySelector('.reply-content').value;
                            const nCommentSeq = item.ncommentSeq;
                            const fkUserSeq = loginUserPk
                            const modData = {
                                sContent, nCommentSeq, fkUserSeq
                            }

                            myFetch.put('/ajax/comment', data => {
                                if ('200' === data.status) {
                                    createModElem.remove();
                                    commentDiv.querySelector('.comment-ctnt').innerText = sContent;
                                }
                            }, modData);
                        });
                    });

                    /** 삭제 클릭 이벤트 */
                    const commentDel = commentMenu.querySelector('.comment-del');
                    commentDel.addEventListener('click', () => {
                        console.log('comment del');
                    });
                }
            });


            /**
             * 대댓글 만들기
             * @param elem = 부모댓글
             * @param list = 대댓글 데이터
             */
            const makeReplyElem = (elem, list) => {
                list.forEach(item => {
                    const createReplyDiv = document.createElement('div');
                    createReplyDiv.classList.add('comment-content');
                    createReplyDiv.classList.add('comment-reply');
                    createReplyDiv.innerHTML = `
                                <div>
                                    <i class="fa-solid fa-right-long"></i>
                                    <span>${item.username}</span>
                                </div>
                                <div class="comment-ctnt-wrap">
                                    <span class="comment-ctnt">${item.scontent.replaceAll('\n', '<br/>')}</span>
                                </div>
                                <div>${item.dtCreateDate}</div>
                                <div class="comment-menu"></div>
                                <div class="user-data" data-iuser="${item.fkUserSeq}"></div>
                            `;
                    elem.after(createReplyDiv);

                    /** 대댓글 수정, 삭제 ( 본인이 쓴 대댓글만 ) */
                    if(item.fkUserSeq === loginUserPk) {
                        const commentMenu = createReplyDiv.querySelector('.comment-menu');
                        commentMenu.innerHTML = `
                                    <span class="comment-mod">수정</span>
                                    <span class="comment-del">삭제</span>
                                `;

                        /** 대댓글 수정 클릭 이벤트 */
                        const commentMod = commentMenu.querySelector('.comment-mod');
                        commentMod.addEventListener('click', () => {
                            const createModElem = makeModElem(createReplyDiv, item);
                            const saveButton = document.querySelector('.reply-submit');

                            /** 답변달기 클릭 이벤트 */
                            saveButton.addEventListener('click', () => {
                                const sContent = document.querySelector('.reply-content').value;
                                const nCommentSeq = item.ncommentSeq;
                                const fkUserSeq = loginUserPk
                                const modData = {
                                    sContent, nCommentSeq, fkUserSeq
                                }
                                myFetch.put('/ajax/comment', data => {
                                    if ('200' === data.status) {
                                        createModElem.remove();
                                        createReplyDiv.querySelector('.comment-ctnt').innerText = sContent;
                                    }
                                }, modData);
                            });
                        });

                        /** 대댓글 삭제 클릭 이벤트 */
                        const commentDel = commentMenu.querySelector('.comment-del');
                        commentDel.addEventListener('click', () => {
                            console.log('reply del');
                        });
                    }
                });
            }

            /**
             * 수정 메소드
             * @param elem
             * @param data
             */
            const makeModElem = (elem, data) => {
                const isReplyForm = document.querySelector('.reply-write');
                if(isReplyForm) {
                    isReplyForm.remove();
                    return;
                }
                const createReplyFormDiv = document.createElement('div');
                createReplyFormDiv.classList.add('reply-write');
                createReplyFormDiv.innerHTML = `
                            <div>
                                <i class="fa-solid fa-right-long"></i>
                            </div>
                            <div>
                                <textarea class="reply-content">${data.scontent}</textarea>
                                <button class="btn btn-secondary reply-submit">답변달기</button>
                            </div>
                        `;
                elem.after(createReplyFormDiv);

                return createReplyFormDiv;
            }

        }, {fkBoardSeq : nBoardSeq, page : page});
    }

    /**
     * 페이징
     */
    const getTotalPage = () => {
        myFetch.get('/ajax/comment/page/total', data => {
            getList();
            setPage(data.result);
        }, {fkBoardSeq : nBoardSeq, page : page});
    }

    const setPage = (data) => {

        const pageList = document.querySelector('.page-list');
        pageList.innerHTML = null;

        const isPrev = document.querySelector('.comment-prev');
        const isNext = document.querySelector('.comment-next');
        if (isPrev) {
            isPrev.remove();
        }
        if (isNext) {
            isNext.remove();
        }

        const commentPrev = document.createElement('li');
        commentPrev.className = 'page-item comment-prev';
        commentPrev.innerHTML = `
            <a class="page-link" href="#" aria-label="Previous">
                <span aria-hidden="true">&laquo;</span>
            </a>
        `;

        if (!data.previous) {
            commentPrev.classList.add('disabled');
        } else {
            commentPrev.addEventListener('click', () => {
                page = data.startPage - 1;
                getTotalPage();
            });
        }

        const commentNext = document.createElement('li');
        commentNext.className = 'page-item comment-next';
        commentNext.innerHTML = `
            <a class="page-link" href="#" aria-label="Next">
                <span aria-hidden="true">&raquo;</span>
            </a>
        `;

        if (!data.next) {
            commentNext.classList.add('disabled');
        } else {
            commentNext.addEventListener('click', () => {
                page = data.lastPage + 1;
                getTotalPage();
            });
        }

        pageList.before(commentPrev);
        pageList.after(commentNext);
        // const commentPrev = document.querySelector('.comment-prev');
        // const commentNext = document.querySelector('.comment-next');
        //
        // commentPrev.classList.remove('disabled');
        // commentPrev.classList.remove('disabled');
        //
        //
        // if (!data.previous){
        //     commentPrev.classList.add('disabled');
        // } else {
        //     commentPrev.addEventListener('click', () => {
        //         console.log('as');
        //         page = data.startPage - 1;
        //         getTotalPage();
        //     });
        // }
        //
        // if (!data.next) {
        //     commentNext.classList.add('disabled');
        // } else {
        //     commentNext.addEventListener('click', () => {
        //         console.log('as');
        //         page = data.lastPage + 1;
        //         getTotalPage();
        //     });
        // }

        for (var i=data.startPage; i<=data.lastPage; i++) {
            const li = document.createElement('li');
            li.classList.add('page-item');
            if (parseInt(page) === parseInt(i)) {
                li.classList.add('disabled');
            } else {
                li.classList.add('cur-pointer');
            }
            li.innerHTML = `
                            <a class="page-link">${i}</a>
                        `;
            pageList.appendChild(li);

            li.addEventListener('click', () => {
                page = li.querySelector('.page-link').innerText;
                getTotalPage();
            });
        }
    }

    let page = 1;
    /**
     * 게시글 출력
     */
    myFetch.get(`/ajax/board/detail`, data => {
        if (data.status === "200") {
            const item = data.result;
            title.innerText = item.stitle;
            userName.innerText = item.suserName;
            date.innerText = item.dtCreateDate;
            hit.innerText = item.nhitCount;
            content.innerHTML = item.scontent;

            /** 수정,삭제 버튼 ( 글쓴이만 보이게 ) */
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
                const likeActive = `<i class="fa-solid fa-heart"></i>`;
                const likedisabled = `<i class="fa-regular fa-heart"></i>`;

                /** data.result = true -> 좋아요 눌러져있음 */
                myFetch.get('/ajax/like/show', data => {
                    if (data.status === '200') {
                        if (data.result) {
                            boardLike.innerHTML = likeActive;
                        } else {
                            boardLike.innerHTML = likedisabled;
                        }

                        /** 좋아요 버튼 클릭 이벤트 */
                        boardLike.addEventListener('click', () => {
                            const data = {
                                fkboardSeq : item.nboardSeq
                            }

                            myFetch.post('/ajax/like/change', data => {
                                /** data.result = 1 -> 좋아요 활성화 */
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


            /**
             * 댓글쓰기 로그인한 회원만 보이게
             */
            if(0 < data.loginUserPk) {
                const commentWriteContainer = document.querySelector('.comment-write-container');
                commentWriteContainer.innerHTML = `
                    <div class="comment-write-wrap">
                        <div class="comment-write">
                            <textarea name="content"></textarea>
                            <div>
                                <button class="btn btn-secondary comment-save-button">댓글쓰기</button>
                            </div>
                        </div>
                    </div>
                `;

                /** 댓글쓰기 버튼 클릭 이벤트 */
                const commentWriteButton = commentWriteContainer.querySelector('.comment-save-button');
                commentWriteButton.addEventListener('click', () => {

                    /** 댓글 저장 데이터 */
                    const commentSaveData = {
                        sContent : commentWriteContainer.querySelector('textarea[name=content]').value,
                        fkBoardSeq : nBoardSeq
                    };

                    /** 댓글 저장 AJAX */
                    myFetch.post('/ajax/comment/', data => {
                        if ('200' === data.status) {
                            location.href = '/board/detail?nBoardSeq='+nBoardSeq;
                        } else if ('400' === data.status) {
                            alert('댓글 등록에 실패했습니다.');
                        }
                    }, commentSaveData)
                });
            }


            getTotalPage();
        }
    }, {nBoardSeq});
}