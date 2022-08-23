<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="board-container">
    <div class="board-list">
        <table class="table">
            <thead>
            <tr>
                <th>번호</th>
                <th>제목</th>
                <th>작성자</th>
                <th>날짜</th>
                <th>좋아요</th>
                <th>조회수</th>
                <th>댓글</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="list" items="${boardList}">
                <tr>
                    <td>${list.getNBoardSeq()}</td>
                    <td><c:out value="${list.getSTitle()}"/></td>
                    <td><c:out value="${list.getSUserName()}"/></td>
                    <td><c:out value="${list.getDtCreateDate()}"/></td>
                    <td><c:out value="${list.getNLikeCount()}"/></td>
                    <td><c:out value="${list.getNHitCount()}"/></td>
                    <td><c:out value="${list.getNCommentCount()}"/></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

    <sec:authorize access="isAuthenticated()">
        <div class="write-wrap">
            <a href="/board/write"><strong>글쓰기</strong></a>
        </div>
    </sec:authorize>
    <div class="search-container">
        <form method="get" action="/board/list">
            <div>
                <input type="hidden" value="1" name="page">
                <select class="form-select" name="sel" style="width: 150px;" aria-label="Default select example">
                    <option value="1">전체검색</option>
                    <option value="2">제목</option>
                    <option value="3">작성자</option>
                    <option value="4">내용</option>
                </select>
            </div>
            <div>
                <div class="input-group mb-3">
                    <input type="text" value="" class="form-control" name="text" placeholder="검색" aria-label="Recipient's username" aria-describedby="button-addon2">
                    <button class="btn btn-outline-secondary" id="button-addon2">검색</button>
                </div>
            </div>
        </form>
    </div>
    <c:set var="pagination" value="${pageData}"/>
    <div class="pagination-wrap">
        <nav aria-label="Page navigation example">
            <ul class="pagination">
                <li class="page-item">
                    <%--          이전 페이지 버튼          --%>
                    <a class="page-link ${pagination.isPrevious() ? "disabled" : null}" href="/board/list?page=${pagination.getStartPage() - 1}" aria-label="Previous">
                        <span aria-hidden="true">&laquo;</span>
                    </a>
                </li>
                <%--          페이지 번호          --%>
                <c:forEach var="num" items="${pagination.getPageArr()}">
                    <li class="page-item"><a class="page-link" href="/board/list?page=${num}">${num}</a></li>
                </c:forEach>
                <li class="page-item">
                    <%--          다음 페이지 버튼          --%>
                    <a class="page-link ${pagination.isNext() ? "disabled" : null}" href="/board/list?page=${pagination.getLastPage() + 1}" aria-label="Next">
                        <span aria-hidden="true">&raquo;</span>
                    </a>
                </li>
            </ul>
        </nav>
    </div>
</div>
