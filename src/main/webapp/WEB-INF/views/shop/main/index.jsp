<%--
  Created by IntelliJ IDEA.
  User: nhn
  Date: 2023/11/08
  Time: 10:20 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<c:choose>
    <c:when test="${not empty param.product_name}">
        <p>검색명: ${param.product_name}</p>
        <c:choose>
            <c:when test="${not empty product}">
                <div class="col">
                    <div class="card shadow-sm">
                        <img src="${product.thumbnailImage != null ? product.getThumbnailImagePath() : 'resources/no-image.png'}" class="bd-placeholder-img card-img-top" width="100%" height="225" alt="Thumbnail">

                        <div class="card-body">
                            <div class="d-flex justify-content-between align-items-center">
                                <h5 class="card-title">${product.pName}</h5>
                                <p class="card-text">${product.pPrice} 원</p>
                                <a href="productDetail.do?product_id=${product.pId}" class="btn btn-primary">상세 보기</a>
                            </div>
                        </div>
                    </div>
                </div>
            </c:when>
            <c:otherwise>
                <p>${product_name} 상품은 존재하지 않습니다.</p>
            </c:otherwise>
        </c:choose>

    </c:when>
    <c:otherwise>
        <!-- 카테고리 선택 -->
        <form method="get" action="index.do">
                <%--드롭다운 선택 박스
                    - category라는 이름으로 서버에 전송
                    - onchange="this.form.submit()": 사용자가 드롭다운에서 카테고리를 선택할 때마다 자동으로 폼이 제출
                --%>
            <select name="category" onchange="this.form.submit()">
                    <%--기본 선택 옵션으로 "전체 상품"을 표시--%>
                <option value="">전체 상품</option>
                <c:forEach var="category" items="${categories}">
                    <%-- value: 각 카테고리의 ID를 값으로 설정합니다. 사용자가 이 옵션을 선택하면 해당 ID가 서버에 전송--%>
                    <option value="${category}" <c:if test="${param.category == category}">selected</c:if>>${category}</option>
                </c:forEach>
            </select>
        </form>

        <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3">

            <%--?? </c:forEach> 위치 이상 --%>
            <c:forEach var="product" items="${products}">
            <div class="col">
                <div class="card shadow-sm">
                        <%--                <svg class="bd-placeholder-img card-img-top" width="100%" height="225" xmlns="http://www.w3.org/2000/svg" role="img" aria-label="Placeholder: Thumbnail" preserveAspectRatio="xMidYMid slice" focusable="false"><title>Placeholder</title><rect width="100%" height="100%" fill="#55595c"></rect><text x="50%" y="50%" fill="#eceeef" dy=".3em">Thumbnail</text></svg>--%>
                        <%--                <img src="${product.thumbnailImage != null ? product.thumbnailImage : 'resources/no-image.png'}" class="bd-placeholder-img card-img-top" width="100%" height="225" alt="Thumbnail">--%>
                    <img src="${product.thumbnailImage != null ? product.getThumbnailImagePath() : 'resources/no-image.png'}" class="bd-placeholder-img card-img-top" width="100%" height="225" alt="Thumbnail">

                    <div class="card-body">
                        <div class="d-flex justify-content-between align-items-center">
                            <h5 class="card-title">${product.pName}</h5>
                            <p class="card-text">${product.pPrice} 원</p>
                            <a href="productDetail.do?product_id=${product.pId}" class="btn btn-primary">상세 보기</a>
                        </div>
                    </div>
                </div>
                </c:forEach>
            </div>


                <%--    <div class="row">--%>
                <%--        <c:forEach var="product" items="${products}">--%>
                <%--            <div class="col-md-4">--%>
                <%--                <div class="card">--%>
                <%--                    <img src="${product.thumbnailImage != null ? product.thumbnailImage : 'no-image.jpg'}" class="card-img-top" alt="${product.pName}">--%>
                <%--                    <div class="card-body">--%>
                <%--                        <h5 class="card-title">${product.pName}</h5>--%>
                <%--                        <p class="card-text">${product.pPrice} 원</p>--%>
                <%--                        <a href="productDetail.jsp?id=${product.pId}" class="btn btn-primary">상세 보기</a>--%>
                <%--                    </div>--%>
                <%--                </div>--%>
                <%--            </div>--%>
                <%--        </c:forEach>--%>
                <%--    </div>--%>

            <!-- 페이징 처리 -->
            <div class="pagination">
                <c:if test="${page > 1}">
                    <a href="index.do?page=${page - 1}&category=${param.category}">이전</a>
                </c:if>
                <c:forEach var="i" begin="1" end="${pageCnt}">
                    <a href="index.do?page=${i}&category=${param.category}" <c:if test="${i == page}">class="active"</c:if>>${i}</a>
                </c:forEach>
                <c:if test="${page < pageCnt}">
                    <a href="index.do?page=${page + 1}&category=${param.category}">다음</a>
                </c:if>
            </div>
        </div>
    </c:otherwise>
</c:choose>


