<%--
  Created by IntelliJ IDEA.
  User: happy
  Date: 24. 10. 31.
  Time: 오전 10:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div style="margin: auto; width: 400px;">
  <div class="p-2">
    <form method="post" action="addAddressAction.do">

      <h1 class="h3 mb-3 fw-normal">주소 작성</h1>

      <div class="form-floating">
        <input type="text" name="address" class="form-control" id="address" placeholder="주소" required>
        <label for="address">주소</label>
      </div>

      <button class="w-100 btn btn-lg btn-primary mt-3" type="submit">등록</button>

      <p class="mt-5 mb-3 text-muted">© 2022-2024</p>

    </form>
  </div>
</div>