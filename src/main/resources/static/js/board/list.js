const goDetailArr = document.querySelectorAll('.go-detail');
if (goDetailArr.length > 0) {
    goDetailArr.forEach(item => {
        item.addEventListener('click', () => {
            const nBoardSeq = item.dataset.nboardseq;
            location.href = `/board/detail?nBoardSeq=${nBoardSeq}`;
        });
    });
}

const boardContainer = document.querySelector('.board-container');
if (boardContainer) {
    const boardTableBody = boardContainer.querySelector('.board-table-body');
    const showBoardButton = boardContainer.querySelector('.show-board-button');
    const pagination = boardContainer.querySelector('.pagination');

    let page = 1;
    let text = '';
    let sel = '';


    /**
     * 조회버튼
     * 페이지 1로 초기화
     * @type {Element}
     */
    if (boardTableBody) {
        showBoardButton.addEventListener('click', () => {
            text = boardContainer.querySelector('.search-input').value;
            sel = boardContainer.querySelector('.search-select');
            sel = sel.options[sel.selectedIndex].value;
            page = 1;

            getBoardList(page, sel, text);
        });
    }

    /**
     * 게시글 리스트 출력
     * @param list = BoardVo 리스트
     */
    const setBoardList = (list) => {
        boardTableBody.innerHTML = null;
        list.forEach(item => {
            const tr = document.createElement('tr');
            const replaceTitle = item.stitle.replaceAll('<', '&lt;').replaceAll('>', '&gt;');
            const fotmatedDate = new Date("");
            tr.innerHTML = `
                <td>${item.rowNumber}</td>
                <td>${replaceTitle}</td>
                <td>${item.suserName}</td> 
                <td>${item.dtCreateDate}</td> 
                <td>${item.nlikeCount}</td> 
                <td>${item.nhitCount}</td> 
                <td>${item.ncommentCount}</td> 
            `;
            boardTableBody.appendChild(tr);
            
            /**
             * 디테일 게시판으로 이동 이벤트
             */
            tr.addEventListener('click', () => {
                location.href = `/board/detail?nBoardSeq=${item.nboardSeq}`;
            });
        });
    }

    /**
     * 페이징 요청
     * @param page
     * @param sel
     * @param text
     */
    const getBoardPageList = (page, sel, text) => {
        myFetch.get('/ajax/board/page', data => {
            if(data.status === '200') {
                setBoardPage(data.result);
            }
        }, {page, sel, text});
    }

    /**
     * 페이징 출력
     * @param pageData
     */
    const setBoardPage = (pageData) => {
        pagination.innerHTML = null;

        /**
         *  페이지 이전페이지 버튼
         */
        const prevLi = document.createElement('li');
        prevLi.classList.add('page-item');
        prevLi.innerHTML = `
             <a class="page-link previous" aria-label="Previous">
                <span aria-hidden="true">&laquo;</span>
             </a>
        `;
        /**
         * 첫 번째 페이지일 때 이전페이지 버튼 비활성화
         */
        if (!pageData.previous) {
            prevLi.querySelector('.previous').classList.add('disabled');
        }
        /**
         * 이전페이지 버튼 클릭 이벤트
         */
        prevLi.addEventListener('click', () => {
            page = (1 === pageData.startPage) ? 1 : pageData.startPage-1;
            getBoardList(page, sel, text);
        });
        pagination.appendChild(prevLi);

        /**
         * 페이징 번호 출력
         */
        pageData.pageArr.forEach(item => {
            const pageLi = document.createElement('li');
            pageLi.classList.add('page-item');
            pageLi.innerHTML = `
                <li class="page-item"><a class="page-link page-num">${item}</a></li>
            `;
            pageLi.addEventListener('click', () => {
                page = item;
                getBoardList(page, sel, text);
            });
            /**
             * 현재 페이지 강조
             */
            if (page === item) {
                const currentPageElem = pageLi.querySelector('.page-num');
                currentPageElem.classList.add('current-page');
                currentPageElem.classList.add('disabled');
            }
            pagination.appendChild(pageLi);
        });
        
        /**
         * 페이지 다음 페이지 버튼
         */
        const nextLi = document.createElement('li');
        nextLi.classList.add('page-item');
        nextLi.innerHTML = `
            <a class="page-link next" aria-label="Next">
                <span aria-hidden="true">&raquo;</span>
            </a>
        `;
        /**
         * 다음 페이지 버튼 클릭 이벤트
         */
        nextLi.addEventListener('click', () => {
            page = (pageData.lastPage < pageData.maxPage) ? pageData.lastPage+1 : pageData.maxPage;
            getBoardList(page, sel, text);
        });
        /**
         * 마지막 페이지일 때 다음 페이지 버튼 비활성화
         */
        if (!pageData.next) {
            nextLi.querySelector('.next').classList.add('disabled');
        }
        pagination.appendChild(nextLi);
    }


    /**
     * 게시글 리스트 요청
     * @param page = 페이지 번호
     * @param sel = 검색종류
     * @param text - 검색어
     */
    const getBoardList = (page, sel, text) => {
        myFetch.get('/ajax/board/list', data => {
            console.log(data);
            if(data.status === "200") {
                setBoardList(data.result);
                getBoardPageList(page, sel, text);
            }
        }, {page,sel, text});
    }
}