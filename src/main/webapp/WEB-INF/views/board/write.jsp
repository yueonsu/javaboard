<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<div>
  <form action="/board/write" method="post">
    <div class="mb-3">
      <label class="form-label">제목</label>
      <input type="text" class="form-control title" name="sTitle" id="title exampleFormControlInput" value="">
    </div>
    <textarea id="editor" name="sContent" class="content"></textarea>
    <div class="button-wrap">
      <button id="write-save-button" class="btn btn-secondary">저장</button>
    </div>
  </form>
</div>
</html>
