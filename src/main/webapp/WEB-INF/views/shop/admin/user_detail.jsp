<%--
  Created by IntelliJ IDEA.
  User: happy
  Date: 24. 10. 31.
  Time: 오후 9:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="card mt-4">
    <div class="card-header">
        <h3> 회원 ID: ${user.userId} </h3>
    </div>
    <div class="card-body">
        <p class="card-text">이름: ${user.userName}</p>
        <p class="card-text">비밀번호: ${user.userPassword}</p>
        <p class="card-text">생년월일: ${user.userBirth}</p>
        <p class="card-text">권한: ${user.userAuth}</p>
        <p class="card-text">포인트: ${user.userPoint}</p>
        <p class="card-text">가입 일자: ${user.createdAt}</p>
        <p class="card-text">마지막 로그인 일자: ${user.latestLoginAt}</p>
    </div>
</div>