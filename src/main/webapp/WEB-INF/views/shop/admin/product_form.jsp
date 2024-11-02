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
        <form method="post" action="productAdd.do">

            <h1 class="h3 mb-3 fw-normal">상품 등록</h1>

            <div class="form-floating">
                <input type="text" name="p_name" class="form-control" id="p_name" placeholder="상품명" required>
                <label for="p_name">상품명</label>
            </div>

            <div class="form-floating">
                <input type="number" name="p_price" class="form-control" id="p_price" placeholder="상품가격" required>
                <label for="p_price">상품가격</label>
            </div>

            <div class="form-floating">
                <input type="text" name="thumbnail_image" class="form-control" id="thumbnail_image" placeholder="썸네일">
                <label for="thumbnail_image">썸네일</label>
            </div>

            <div class="form-floating">
                <input type="text" name="detail_image" class="form-control" id="detail_image" placeholder="디테일 사진">
                <label for="detail_image">디테일 사진</label>
            </div>

            <c:forEach var="i" begin="1" end="3">
                <div class="form-floating">
                    <input type="text" name="category_name${i}" class="form-control" id="category_name${i}" placeholder="카테고리명${i}" <c:if test='${i == 1}'>required</c:if>>
                    <label for="category_name${i}">카테고리명${i}</label>
                </div>
            </c:forEach>

            <button class="w-100 btn btn-lg btn-primary mt-3" type="submit">등록</button>

            <p class="mt-5 mb-3 text-muted">© 2022-2024</p>

        </form>
    </div>
</div>