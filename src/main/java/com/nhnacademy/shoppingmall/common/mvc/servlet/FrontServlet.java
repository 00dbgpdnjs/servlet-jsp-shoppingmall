package com.nhnacademy.shoppingmall.common.mvc.servlet;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.common.mvc.view.ViewResolver;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.common.mvc.controller.ControllerFactory;

import lombok.extern.slf4j.Slf4j;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@Slf4j
@WebServlet(name = "frontServlet",urlPatterns = {"*.do"})
public class FrontServlet extends HttpServlet {
    private ControllerFactory controllerFactory;
    private ViewResolver viewResolver;

    /* Q. servlet이 다시 호출하면 필드는 초기화 되는가
        서블릿이 다시 호출될 때, 서블릿 클래스의 인스턴스 필드는 초기화되지 않습니다. 대신, 서블릿 인스턴스는 웹 애플리케이션의 생애주기 동안 하나의 인스턴스만 생성되어 여러 요청을 처리하게 됩니다. 이 점은 서블릿의 작동 방식에서 매우 중요합니다.

        주요 사항
            서블릿 인스턴스의 공유:
                서블릿 컨테이너는 서블릿의 인스턴스를 한 번만 생성하고, 여러 사용자의 요청에 대해 그 인스턴스를 공유합니다. 이는 성능을 향상시키고 메모리 사용을 줄이는 데 도움이 됩니다.
            필드의 상태:
                요청이 들어올 때마다 서블릿의 인스턴스가 동일하게 유지되므로, 필드에 저장된 값은 요청 간에 지속됩니다. 따라서 이전 요청에서 필드에 설정한 값이 다음 요청에 영향을 미칠 수 있습니다.
            초기화:
                서블릿이 처음 로드될 때 init() 메서드가 호출되어 초기화 작업을 수행합니다. 그러나 이후의 요청에서 서블릿 인스턴스가 다시 호출되더라도, 필드는 초기화되지 않습니다.
            스레드 안전성:
                여러 요청이 동시에 서블릿 인스턴스에 접근할 수 있기 때문에, 필드에 상태를 저장하는 것은 위험할 수 있습니다. 한 요청이 필드 값을 변경하면, 다른 요청이 그 값을 읽을 때 예기치 않은 동작이 발생할 수 있습니다. 이러한 문제를 방지하려면 스레드 안전한 방법으로 상태를 관리해야 합니다.
        요약
            서블릿 인스턴스의 필드는 요청 간에 유지되며, 다시 호출되더라도 초기화되지 않습니다. 여러 요청이 동일한 서블릿 인스턴스를 공유하기 때문에, 필드에 저장된 값은 서로에게 영향을 미칠 수 있습니다. 따라서 필드를 사용하여 상태를 유지하는 것은 권장되지 않으며, 세션이나 요청 속성을 사용하는 것이 더 안전한 방법입니다.
     */
    @Override
    public void init() throws ServletException {
        //todo#7-1 controllerFactory를 초기화 합니다.
        this.controllerFactory = (ControllerFactory) getServletContext().getAttribute(ControllerFactory.CONTEXT_CONTROLLER_FACTORY_NAME);

        //todo#7-2 viewResolver를 초기화 합니다.
        this.viewResolver = new ViewResolver();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp){
        try{
            //todo#7-3 Connection pool로 부터 connection 할당 받습니다. connection은 Thread 내에서 공유됩니다.
            DbConnectionThreadLocal.initialize();

            BaseController baseController = (BaseController) controllerFactory.getController(req);
            String viewName = baseController.execute(req,resp);

            if(viewResolver.isRedirect(viewName)){
                String redirectUrl = viewResolver.getRedirectUrl(viewName);
                log.debug("redirectUrl:{}",redirectUrl);
                //todo#7-6 redirect: 로 시작하면  해당 url로 redirect 합니다.
                resp.sendRedirect(viewResolver.getRedirectUrl(viewName));
            }else {
                String layout = viewResolver.getLayOut(viewName);
                log.debug("viewName:{}", viewResolver.getPath(viewName));
                req.setAttribute(ViewResolver.LAYOUT_CONTENT_HOLDER, viewResolver.getPath(viewName));
                RequestDispatcher rd = req.getRequestDispatcher(layout);
                rd.include(req, resp);
            }
        }catch (Exception e){
            log.error("error:{}",e);
            DbConnectionThreadLocal.setSqlError(true);
            //todo#7-5 예외가 발생하면 해당 예외에 대해서 적절한 처리를 합니다.
//            req.setAttribute("exception", ex);
//            req.setAttribute("status_code", 500);
//            req.setAttribute("exception_type", ex.getClass().getName());
//            req.setAttribute("message", ex.getMessage());
//            req.setAttribute("request_uri", req.getRequestURI());

//            try {
//                req.getRequestDispatcher("/error.jsp").forward(req, resp);
//            } catch (ServletException | IOException ex) {
//                throw new RuntimeException(ex);
//            }
        }finally {
            //todo#7-4 connection을 반납합니다.
            DbConnectionThreadLocal.reset();
        }
    }

}