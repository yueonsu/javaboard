<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<div id="iboard">
    <div id="iuser"></div>
</div>
    <div class="detail-container">

        <div class="content-wrap">
            <div class="top">
                <div class="title-wrap">
                    <div>
                        <h2 class="title">

                        </h2>
                    </div>
                    <div>
                        <%--           login user             --%>
                        <div class="auth-detail">
                            <a class="mod-button">수정</a>
                            <span class="del-button">삭제</span>
                        </div>
                        <%--           login user             --%>
                    </div>
                </div>
            </div>

            <div class="detail-content-wrap">
                <div class="board-info">
                    <div>
                        <%--            좋아요            --%>
                        <i class="fa-regular fa-heart like"></i>
                        <i class="fa-solid fa-heart like"></i>
                        <%--            좋아요            --%>
                    </div>
                    <div>
                        <span class="username">회원이름</span>

                        <span class="date"></span>

                        <span>조회: </span>
                        <span class="hit"></span>
                    </div>
                </div>
                <div class="content"></div>
            </div>
        </div>

        <div class="comment">
            <%--      login user      --%>
            <div class="comment-write-wrap">
                <form class="comment-write" method="post" action="">
                    <input type="hidden" name="iuser">
                    <input type="hidden" name="iboard">
                    <textarea name="content"></textarea>
                    <div>
                        <button class="btn btn-secondary">댓글쓰기</button>
                    </div>
                </form>
            </div>
            <%--      login user      --%>
        </div>
        <div class="comment-content-wrap">
            <div class="comment-list">

            </div>
        </div>
        <div class="comment-pagination">
            <nav aria-label="Page navigation example">
                <ul class="pagination">
                    <li class="page-item comment-prev">
                        <a class="page-link" href="#" aria-label="Previous">
                            <span aria-hidden="true">&laquo;</span>
                        </a>
                    </li>
                    <div style="display: flex;" class="page-list">

                    </div>
                    <li class="page-item comment-next">
                        <a class="page-link" href="#" aria-label="Next">
                            <span aria-hidden="true">&raquo;</span>
                        </a>
                    </li>
                </ul>
            </nav>
        </div>
    </div>
</body>
</html>