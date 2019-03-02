package com.gjk.interceptor;

import com.gjk.pojo.User;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CheckLoginInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
/*
        String serverName = request.getServerName();System.out.println("serverName," +serverName);
        String loacalName = request.getLocalName();System.out.println("loacalName," +loacalName);
        int serverPort = request.getServerPort();System.out.println("serverPort," +serverPort);
        int localPort = request.getLocalPort();System.out.println("localPort," +localPort);
        int remotePort = request.getRemotePort();System.out.println("remotePort," +remotePort);
        String contextPath = request.getContextPath();System.out.println("contextPath," +contextPath);
        String ServletPath = request.getServletPath();System.out.println("ServletPath," +ServletPath);
        String uri = request.getRequestURI();System.out.println("uri," +uri);
        String url = request.getRequestURL().toString();System.out.println("url," +url);
*/
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        if(user != null){
            return true;
        }

        String contextPath = request.getContextPath();
        String uri = request.getRequestURI();
        uri = StringUtils.remove(uri, contextPath + "/");
        String checkList[] = new String[]{
                "forebought",
                "forecart",
                "forechangeOrderItem",
                "foredeleteOrderItem",
                "foredeleteOrder",
                "forecreateOrder",
                "forebuy",
                "forealipay",
                "forepayed"
        };
        for(String s : checkList){
            if(uri.startsWith(s)){
                response.sendRedirect("loginPage");
                return false;
            }
        }

        return true;
    }
}
