<%--
  Created by IntelliJ IDEA.
  User: happy
  Date: 24. 11. 4.
  Time: 오전 12:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="container mt-5">
    <h1 class="text-center">포인트 내역</h1>

    <div class="mt-4">
        <ul class="list-group">
            <c:if test="${not empty pointHistory}">
                <c:forEach var="point" items="${pointHistory}">
                    <li class="list-group-item d-flex justify-content-between align-items-center">
                        ${point.pointsUsed}
                    </li>
                </c:forEach>
            </c:if>
            <c:if test="${empty pointHistory}">
                <li class="list-group-item">포인트 내역이 없습니다.</li>
            </c:if>
        </ul>
    </div>


    <c:if test="${not empty pointHistory}">
        <div class="mt-4">
            <nav>
                <ul class="pagination justify-content-center">
                    <c:forEach begin="1" end="${pageCnt}" var="i">
                        <li class="page-item <c:if test='${i == page}'>active</c:if>">

                            <a class="page-link" href="?page=${i}">${i}</a>
                        </li>
                    </c:forEach>
                </ul>
            </nav>
        </div>
    </c:if>
</div>