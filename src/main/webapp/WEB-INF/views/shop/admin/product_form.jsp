<%--
  Created by IntelliJ IDEA.
  User: happy
  Date: 24. 10. 31.
  Time: 오후 4:00
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div style="margin: auto; width: 400px;">
    <div class="p-2">
<%--        <form method="post" action="productAdd.do">--%>
        <form method="post" action="${not empty param.product_id ? 'productUpdate.do' : 'productAdd.do'}" >

            <h1 class="h3 mb-3 fw-normal">${not empty param.product_id ? '상품 수정' : '상품 등록'}</h1>

            <c:if test="${not empty param.product_id}">
                <input type="hidden" name="product_id" value="${product.pId}">
            </c:if>

            <div class="form-floating">
                <input type="text" name="p_name" class="form-control"
                       value="${not empty param.product_id ? product.pName : ''}"
                       id="p_name" placeholder="상품명" required>
                <label for="p_name">상품명</label>
            </div>

            <div class="form-floating">
                <input type="number" name="p_price" class="form-control"
                       value="${not empty param.product_id ? product.pPrice : ''}"
                       id="p_price" placeholder="상품가격" required>
                <label for="p_price">상품가격</label>
            </div>

            <div class="form-floating">
                <input type="text" name="thumbnail_image" class="form-control"
                       value="${not empty param.product_id ? product.thumbnailImage : ''}"
                       id="thumbnail_image" placeholder="썸네일">
                <label for="thumbnail_image">썸네일</label>
            </div>

            <div class="form-floating">
                <input type="text" name="detail_image" class="form-control"
                       value="${not empty param.product_id ? product.detailImage : ''}"
                       id="detail_image" placeholder="디테일 사진">
                <label for="detail_image">디테일 사진</label>
            </div>

<%--                JSP에서는 중괄호를 중첩해서 사용x--%>
            <c:forEach var="i" begin="1" end="3">

                <div class="form-floating">
                    <input type="text" name="category_name${i}" class="form-control"
                           value="${not empty param.product_id ? product.categoryNames[i - 1] : ''}"
                           id="category_name${i}" placeholder="카테고리명${i}" <c:if test='${i == 1}'>required</c:if>>
                    <label for="category_name${i}">카테고리명${i}</label>

<%--                    <c:choose>--%>
<%--                        <c:when test="${not empty param.product_id}">--%>
<%--                            <input type="text" name="category_name${i}" class="form-control"--%>
<%--                                   value="product.categoryNames[${i-1}]"--%>
<%--                                   id="category_name${i}" placeholder="카테고리명${i}" <c:if test='${i == 1}'>required</c:if>>--%>
<%--                        </c:when>--%>
<%--                        <c:otherwise>--%>
<%--                            <input type="text" name="category_name${i}" class="form-control"--%>
<%--                                   id="category_name${i}" placeholder="카테고리명${i}" <c:if test='${i == 1}'>required</c:if>>--%>
<%--                        </c:otherwise>--%>
<%--                        <label for="category_name${i}">카테고리명${i}</label>--%>
<%--                    </c:choose>--%>

                </div>
            </c:forEach>

            <button class="w-100 btn btn-lg btn-primary mt-3" type="submit">
                ${not empty param.product_id ? '수정' : '등록'}
            </button>

            <p class="mt-5 mb-3 text-muted">© 2022-2024</p>

        </form>
    </div>
</div>