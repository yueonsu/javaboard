<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<body>
<div>
    <a href="/board/list"><strong>메인</strong></a>
</div>

<div class="header-user">
    <sec:authorize access="isAnonymous()">
        <a href="/user/login"><strong>로그인</strong></a>
        <a href="/user/join"><strong>회원가입</strong></a>
    </sec:authorize>

    <sec:authorize access="isAuthenticated()">
        <sec:authentication property="principal.member.sName" var="user"/>
        <div>
            <span>${user} 님</span>
        </div>
        <a href="/user/logout"><strong>로그아웃</strong></a>
    </sec:authorize>

</div>


</body>
</html>
