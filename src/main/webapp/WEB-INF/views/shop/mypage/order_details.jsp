<%--
  Created by IntelliJ IDEA.
  User: happy
  Date: 24. 11. 4.
  Time: 오전 1:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div>
  <h1>주문 명세 조회</h1>

  <table border="1">
    <thead>
    <tr>
      <th>주문번호</th>
      <th>상품ID</th>
      <th>수량</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="order" items="${orderList}">
      <tr>
        <td>${order.orderId}</td>
        <td>${order.pId}</td>
        <td>${order.quantity}</td>
      </tr>
    </c:forEach>
    </tbody>
  </table>

  <div class="pagination">
    <c:if test="${page > 1}">
      <a href="?page=${page - 1}">이전</a>
    </c:if>

    <c:forEach begin="1" end="${pageCnt}" var="i">
      <c:choose>
        <c:when test="${i == page}">
          <strong>${i}</strong>
        </c:when>
        <c:otherwise>
          <a href="?page=${i}">${i}</a>
        </c:otherwise>
      </c:choose>
    </c:forEach>

    <c:if test="${page < pageCnt}">
      <a href="?page=${page + 1}">다음</a>
    </c:if>
  </div>
</div>