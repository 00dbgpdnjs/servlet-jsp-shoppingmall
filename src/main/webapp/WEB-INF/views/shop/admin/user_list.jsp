<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- shop.jsp 과 같이 <%@ %> 가 외부 jsp 에 있으면 오류 있어도 안 뜸 --%>
<%--
  Created by IntelliJ IDEA.
  User: happy
  Date: 24. 10. 31.
  Time: 오후 8:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="container mt-5">
  <h1 class="text-center">회원 리스트</h1>

  <div class="mt-4">
    <ul class="list-group">
      <c:if test="${not empty userIds}">
        <c:forEach var="id" items="${userIds}">
          <li class="list-group-item d-flex justify-content-between align-items-center">
<%--              ${id}--%>
            <a href="userDetail.do?id=${id}" class="text-decoration-none">${id}</a>
          </li>
        </c:forEach>
      </c:if>
      <c:if test="${empty userIds}">
        <li class="list-group-item">회원이 없습니다.</li>
      </c:if>
    </ul>
  </div>
</div>