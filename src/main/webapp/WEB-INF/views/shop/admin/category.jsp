<%--
  Created by IntelliJ IDEA.
  User: happy
  Date: 24. 11. 1.
  Time: 오전 3:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="container mt-5">
  <h1 class="text-center">상품 카테고리 관리</h1>

  <div class="mt-4">
    <ul class="list-group">
<%--      ${not empty userIds}: null이 아니고, 컬렉션의 경우 요소가 하나 이상 있는지--%>
      <c:if test="${not empty categories}">
        <c:forEach var="category" items="${categories}">
          <li class="list-group-item d-flex justify-content-between align-items-center">
<%--            <a href="userDetail.do?id=${id}" class="text-decoration-none">--%>
                ${category}
<%--            </a>--%>
          </li>
        </c:forEach>
      </c:if>
      <c:if test="${empty categories}">
        <li class="list-group-item">카테고리가 없습니다.</li>
      </c:if>
    </ul>
  </div>
</div>