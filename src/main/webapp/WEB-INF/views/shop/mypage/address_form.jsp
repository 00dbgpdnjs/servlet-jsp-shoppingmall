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
<%--    <form method="post" action="addAddressAction.do">--%>
    <form action="${not empty param.address ? 'updateAddress.do' : 'addAddressAction.do'}" method="post">


      <%--      <h1 class="h3 mb-3 fw-normal">주소 작성</h1>--%>
      <h1 class="h3 mb-3 fw-normal">${not empty param.address ? '주소 수정' : '주소 작성'}</h1>

      <div class="form-floating">
        <input type="text" name="address" class="form-control"
               value="${param.address != null ? param.address : ''}"
               id="address" placeholder="주소" required>
        <label for="address">주소</label>
      </div>

      <c:if test="${not empty param.address}">
          <input type="hidden" name="existingAddr" value="${param.address}">
      </c:if>

<%--      <button class="w-100 btn btn-lg btn-primary mt-3" type="submit">등록</button>--%>
      <button type="submit" class="w-100 btn btn-lg btn-primary mt-3">
        ${not empty param.address ? '수정' : '등록'}
      </button>

      <p class="mt-5 mb-3 text-muted">© 2022-2024</p>

    </form>
  </div>
</div>