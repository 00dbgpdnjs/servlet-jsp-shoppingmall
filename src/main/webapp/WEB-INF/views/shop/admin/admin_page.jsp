<%--
  Created by IntelliJ IDEA.
  User: happy
  Date: 24. 10. 31.
  Time: 오후 4:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="container mt-5">
  <h1 class="text-center">관리자 페이지</h1>

  <nav class="nav justify-content-center">
    <a class="nav-link" href="category.do">상품 카테고리 관리</a>
    <a class="nav-link" href="productList.do">상품 관리</a>
    <a class="nav-link" href="userList.do">일반 회원 목록</a>
    <a class="nav-link" href="userList.do?role=ROLE_ADMIN">관리자 목록</a>
  </nav>
</div>
