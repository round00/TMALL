package com.gjk.interceptor;

import com.gjk.pojo.Category;
import com.gjk.pojo.OrderItem;
import com.gjk.pojo.User;
import com.gjk.service.CategoryService;
import com.gjk.service.OrderItemService;
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
    @Autowired
    OrderItemService orderItemService;

    /**
     * 在业务处理器处理请求之前被调用
     * 如果返回false
     *     从当前的拦截器往回执行所有拦截器的afterCompletion(),再退出拦截器链
     * 如果返回true
     *    执行下一个拦截器,直到所有的拦截器都执行完毕
     *    再执行被拦截的Controller
     *    然后进入拦截器链,
     *    从最后一个拦截器往回执行所有的postHandle()
     *    接着再从最后一个拦截器往回执行所有的afterCompletion()
     */
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        return super.preHandle(request, response, handler);
    }
    /**
     * 在业务处理器处理请求执行完成后,生成视图之前执行的动作
     * 可在modelAndView中加入数据，比如当前时间
     */
    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        //用于搜索栏下的几个分类按钮
        HttpSession session = request.getSession();
        List<Category> categories = categoryService.getCategoryList();
        session.setAttribute("cs", categories);

        //用于一些按钮返回主页
        String contextPath = "forehome";//request.getServletContext().getContextPath();
        session.setAttribute("contextPath", contextPath);

        //购物车中商品的 件数
        User user = (User) session.getAttribute("user");
        int totalCount = 0;
        if(user != null){
            List<OrderItem> orderItems = orderItemService.getOrderItemsByUid(user.getId());
            for(OrderItem oi : orderItems){
                totalCount += oi.getNumber();
            }
        }
        session.setAttribute("cartTotalItemNumber", totalCount);
    }
    /**
     * 在DispatcherServlet完全处理完请求后被调用,可用于清理资源等
     *
     * 当有拦截器抛出异常时,会从当前拦截器往回执行所有的拦截器的afterCompletion()
     */
    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
