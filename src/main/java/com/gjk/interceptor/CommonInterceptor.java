package com.gjk.interceptor;

import com.gjk.pojo.Category;
import com.gjk.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class CommonInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    CategoryService categoryService;
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        HttpSession session = request.getSession();
        List<Category> categories = categoryService.getCategoryList();
        session.setAttribute("cs", categories);

        String contextPath = "forehome";//request.getServletContext().getContextPath();
        session.setAttribute("contextPath", contextPath);

    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
