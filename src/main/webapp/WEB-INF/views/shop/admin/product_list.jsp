<%--
  Created by IntelliJ IDEA.
  User: happy
  Date: 24. 11. 2.
  Time: 오전 2:40
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div>
  <h1>상품 목록</h1>

  <div>
    <a href="productAdd.do" class="btn btn-primary">등록</a>
  </div>

  <table border="1">
    <thead>
      <tr>
        <th>상품 이름</th>
        <th>가격</th>
        <th>썸네일</th>
        <th>상세 페이지용 이미지</th>
        <th>카테고리</th>
      </tr>
    </thead>
    <tbody>
      <c:forEach var="product" items="${products}">
        <tr>
          <td>${product.pName}</td>
          <td>${product.pPrice}</td>
          <td>
<%--              ${product.thumbnailImage}--%>
            <c:choose>
              <c:when test="${not empty product.thumbnailImage}">
                ${product.thumbnailImage}
              </c:when>
              <c:otherwise>
                no-image
              </c:otherwise>
            </c:choose>
          </td>
          <td>
              <c:choose>
                <c:when test="${not empty product.detailImage}">
                  ${product.detailImage}
                </c:when>
                <c:otherwise>
                  no-image
                </c:otherwise>
              </c:choose>
          </td>
          <td>
            <c:forEach var="category" items="${product.categoryNames}" varStatus="status">
              ${category}<c:if test="${!status.last}">, </c:if> <!-- 카테고리 구분자 -->
            </c:forEach>
          </td>

          <td>
            <a href="productUpdate.do?product_id=${product.pId}" class="btn btn-sm btn-warning">수정</a>
          </td>

          <td>
            <form action="productDelete.do" method="post" onsubmit="return confirm('삭제하시겠습니까?');">
              <input type="hidden" name="product_id" value="${product.pId}">
              <button type="submit" class="btn btn-sm btn-danger">삭제</button>
            </form>
          </td>
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
          <strong>${i}</strong> <!-- 현재 페이지 강조 -->
        </c:when>
        <c:otherwise>
          <a href="?page=${i}">${i}</a> <!-- 다른 페이지 링크 -->
        </c:otherwise>
      </c:choose>
    </c:forEach>

    <c:if test="${page < pageCnt}">
      <a href="?page=${page + 1}">다음</a>
    </c:if>
  </div>
</div>