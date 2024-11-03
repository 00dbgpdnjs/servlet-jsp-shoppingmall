<%--
  Created by IntelliJ IDEA.
  User: happy
  Date: 24. 11. 3.
  Time: 오후 12:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:choose>
  <c:when test="${not empty product}">
    <div class="card">
      <img src="${product.thumbnailImage != null ? product.getDetailImagePath() : 'resources/no-image.png'}" class="card-img-top" alt="상품 이미지">
      <div class="card-body">
        <h5 class="card-title">${product.pName}</h5>
        <p class="card-text">가격: ${product.pPrice} 원</p>
        <a href="order.jsp?product_id=${product.pId}" class="btn btn-success">주문하기</a>
        <form action="cart/cartAdd.do" method="post">
          <input type="hidden" name="product_id" value="${product.pId}">
          <button type="submit" class="btn btn-sm btn-danger">장바구니 담기</button>
        </form>
      </div>
    </div>
  </c:when>

  <c:otherwise>
    <p>존재하지 않는 상품 페이지 입니다.</p>
  </c:otherwise>
</c:choose>