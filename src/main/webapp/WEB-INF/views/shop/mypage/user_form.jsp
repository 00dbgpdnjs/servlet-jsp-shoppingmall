<%--
  Created by IntelliJ IDEA.
  User: happy
  Date: 24. 10. 31.
  Time: 오후 4:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div style="margin: auto; width: 400px;">
    <div class="p-2">
        <form method="post" action="updateUser.do">

            <h1 class="h3 mb-3 fw-normal">회원 정보 수정</h1>

            <div class="form-floating">
                <input type="text" name="user_name" class="form-control" id="user_name" value="${user.userName}" placeholder="회원 이름" required>
                <label for="user_name">회원이름</label>
            </div>

            <div class="form-floating">
                <input type="password" name="user_password" class="form-control" id="user_password" value="${user.userPassword}" placeholder="비밀번호" required>
                <label for="user_password">비밀번호</label>
            </div>

            <div class="form-floating">
                <input type="text" name="user_birth" class="form-control" id="user_birth" value="${user.userBirth}" placeholder="생년월일" required>
                <label for="user_birth">생년월일</label>
            </div>

            <button class="w-100 btn btn-lg btn-primary mt-3" type="submit">수정</button>

            <p class="mt-5 mb-3 text-muted">© 2022-2024</p>

        </form>
    </div>
</div>