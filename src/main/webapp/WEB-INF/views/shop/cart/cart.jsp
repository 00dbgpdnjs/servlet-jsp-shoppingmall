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
          <form action="/orderAction.do" method="post">
            <input type="hidden" name="product_id" value="<%= item.getProduct().getpId() %>">
            <input type="hidden" name="quantity" value="<%= item.getQuantity() %>">
            <button type="submit" class="btn btn-sm btn-success">주문</button>
          </form>
        </td>
        <td>
          <form action="cartQuantityUpdate.do" method="post" style="display:inline;">
            <input type="hidden" name="product_id" value="<%= item.getProduct().getpId() %>">
            <input type="number" name="quantity" value="<%= item.getQuantity() %>" min="1" style="width: 60px;">
            <button type="submit" class="btn btn-primary">수량 변경</button>
          </form>
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