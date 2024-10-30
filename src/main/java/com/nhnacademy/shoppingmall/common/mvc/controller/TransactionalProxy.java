package com.nhnacademy.shoppingmall.common.mvc.controller;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.transaction.Transactional;
import java.util.Objects;

public class TransactionalProxy implements BaseController{
    private final BaseController baseController;

    public TransactionalProxy(BaseController baseController) {
        this.baseController = baseController;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        Transactional transactional = baseController.getClass().getAnnotation(Transactional.class);
        if(Objects.isNull(transactional)) {
            return baseController.execute(req, resp);
        }

        String viewName = "";
        try {
            DbConnectionThreadLocal.initialize();
            viewName =  baseController.execute(req, resp);
        } catch (Exception e) {
            DbConnectionThreadLocal.setSqlError(true);
            throw e;
        } finally {
            DbConnectionThreadLocal.reset();
        }

        return viewName;
    }
}
