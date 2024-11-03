<%--
  Created by IntelliJ IDEA.
  User: happy
  Date: 24. 11. 3.
  Time: 오후 1:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.nhnacademy.shoppingmall.controller.cart.Cart" %>
<%@ page import="com.nhnacademy.shoppingmall.controller.cart.CartItem" %>
<%@ page import="java.util.List" %>
<div class="container mt-5">
  <h1>장바구니</h1>

  <%
    Cart cart = (Cart) session.getAttribute("cart");
    if (!cart.getItems().isEmpty()) {
  %>
    <table class="table">
      <thead>
      <tr>
        <th>상품명</th>
        <th>가격</th>
        <th>수량</th>
        <th></th>
      </tr>
      </thead>
      <tbody>
      <%
        for (CartItem item : cart.getItems()) {
      %>
      <tr>
        <td><%= item.getProduct().getpName() %></td>
        <td><%= item.getProduct().getpPrice() %> 원</td>
        <td><%= item.getQuantity() %></td>
        <td>
          <a href="cart?action=remove&productId=<%= item.getProduct().getpId() %>" class="btn btn-danger">삭제</a>
        </td>
      </tr>
      <%
        }
      %>
      </tbody>
    </table>
  <%
  } else {
  %>
  <p>장바구니에 상품이 없습니다.</p>
  <%
    }
  %>
</div>