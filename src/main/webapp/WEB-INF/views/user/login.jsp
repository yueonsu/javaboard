<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
    <div class="login-container">
        <div class="login-bg">
            <h1>로그인</h1>
            <form action="/user/login" method="post">
                <input type="hidden" name="prevUrl" value="">
                <div class="form-floating mb-3">
                    <input type="text" class="form-control" name="sId" id="floatingInput" placeholder="Id" value="user1">
                    <label for="floatingInput">Email address</label>
                </div>
                <div class="form-floating">
                    <input type="password" class="form-control" name="sPassword" id="floatingPassword" placeholder="Password" value="asd123!">
                    <label for="floatingPassword">Password</label>
                </div>
                <div class="error-msg dis-none">
                </div>
                <div class="text-center">
                    <div>
                        <button type="submit" class="btn btn-secondary btn-sm p-3 w-25 mt-3">로그인</button>
                    </div>
                    <div>
                        <a href="/user/join"><button type="button" class="btn btn-secondary btn-sm p-3 w-25 mt-3">회원가입</button></a>
                    </div>
                    <div>
                        <a href="/"><button type="button" class="btn btn-secondary btn-sm p-3 w-25 mt-3">메인</button></a>
                    </div>
                </div>
            </form>
        </div>

        <div class="modal-disabled id-modal">
            <div class="modal-content">
                <div>
                    <input type="text" class="form-control" name="nm" id="floatingName" placeholder="이름">
                </div>
                <div>
                    <input type="text" class="form-control" name="email" id="floatingEmail" placeholder="이메일">
                </div>
                <div>
                    <button class="find-id-submit-btn btn btn-secondary">확인</button>
                    <button class="find-id-cancel-btn btn btn-secondary">취소</button>
                </div>
            </div>
        </div>

        <div class="modal-disabled pw-modal">
            <div class="modal-content">
                <div>
                    <input type="text" class="form-control" name="id" id="floatingModalId" placeholder="아이디">
                </div>
                <div>
                    <input type="text" class="form-control" name="email" id="floatingModalEmail" placeholder="이메일">
                </div>
                <div class="msg-wrap">
                    <strong class="msg"></strong>
                </div>
                <div>
                    <button class="find-pw-submit-btn btn btn-secondary">확인</button>
                    <button class="find-pw-cancel-btn btn btn-secondary">취소</button>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
