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
                        <div class="auth-detail">
                        </div>
                    </div>
                </div>
            </div>

            <div class="detail-content-wrap">
                <div class="board-info">
                    <div class="board-like">
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

        <div class="comment-write-container">
        </div>
        <div class="comment-content-wrap">
            <div class="comment-list">

            </div>
        </div>
        <div class="comment-pagination">
            <nav aria-label="Page navigation example">
                <ul class="pagination">
                    <div style="display: flex;" class="page-list">

                    </div>
                </ul>
            </nav>
        </div>
    </div>
</body>
</html>
