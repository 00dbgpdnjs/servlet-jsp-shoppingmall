<%-- session
     해당 JSP 페이지에서 HTTP 세션을 사용하지 않겠다
     - 세션 비활성화: 이 지시어를 설정하면 JSP 페이지에서 세션 객체를 사용할 수 없습니다.
        즉, request.getSession() 메서드를 호출할 수 없으며, 세션과 관련된 기능을 사용할 수 없습니다.
     - 메모리 절약: 세션을 사용하지 않으면 서버 메모리를 절약할 수 있고, 클라이언트와의 상태를 유지하지 않기 때문에 더 간단한 요청 처리 모델을 사용할 수 있습니다.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" session="false" %>

<div style="margin: auto; width: 400px;">
    <div class="p-2">
        <form method="post" action="/loginAction.do">

            <h1 class="h3 mb-3 fw-normal">Please sign in</h1>

            <div class="form-floating">
                <input type="text" name="user_id" class="form-control" id="user_id" placeholder="회원 아이디" required>
                <label for="user_id">회원아이디</label>
            </div>

            <div class="form-floating">
                <input type="password" name="user_password" class="form-control" id="user_password" placeholder="비밀번호" required>
                <label for="user_password">비밀번호</label>
            </div>

            <button class="w-100 btn btn-lg btn-primary mt-3" type="submit">Sign in</button>

            <p class="mt-5 mb-3 text-muted">© 2022-2024</p>

        </form>
    </div>
</div>