<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
            <tbody class="board-table-body">

            </tbody>
        </table>
    </div>

    <%--  로그인 된 유저만 표시  --%>
    <sec:authorize access="isAuthenticated()">
        <div class="write-wrap">
            <a href="/board/write"><strong>글쓰기</strong></a>
        </div>
    </sec:authorize>

    <%--  검색  --%>
    <div class="search-container">
        <div class="search-div">
            <div>
                <select class="form-select search-select" name="sel" style="width: 150px;" aria-label="Default select example">
                    <option value="1">전체검색</option>
                    <option value="2">제목</option>
                    <option value="3">작성자</option>
                    <option value="4">내용</option>
                </select>
            </div>
            <div>
                <div class="input-group mb-3">
                    <input type="text" value="${param.text}" class="form-control search-input" name="text" placeholder="검색" aria-label="Recipient's username" aria-describedby="button-addon2">
                    <button class="btn btn-outline-secondary show-board-button" id="button-addon2">조회</button>
                </div>
            </div>
        </div>
    </div>

    <%--  페이징  --%>
    <div class="pagination-wrap">
        <nav aria-label="Page navigation example">
            <ul class="pagination">

            </ul>
        </nav>
    </div>
</div>
