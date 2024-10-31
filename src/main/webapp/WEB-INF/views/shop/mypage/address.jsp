<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: happy
  Date: 24. 10. 30.
  Time: 오후 4:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="container mt-5">
    <h1 class="text-center">주소 목록</h1>

    <div class="mt-4">
        <ul class="list-group">
            <c:if test="${not empty addresses}">
                <c:forEach var="address" items="${addresses}">
                    <li class="list-group-item d-flex justify-content-between align-items-center">
                            ${address}
                        <div>
                            <a href="editAddress.do?address=${address}" class="btn btn-sm btn-warning">수정</a>
                            <a href="deleteAddress.do?address=${address}" class="btn btn-sm btn-danger">삭제</a>
                        </div>
                    </li>
                </c:forEach>
            </c:if>
            <c:if test="${empty addresses}">
                <li class="list-group-item">주소가 없습니다.</li>
            </c:if>
        </ul>
    </div>

    <div class="text-center mt-4">
        <a href="addAddress.do" class="btn btn-primary">주소 등록</a>
    </div>
</div>