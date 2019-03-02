package com.gjk.controller;

import com.gjk.pojo.*;
import com.gjk.service.*;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class ForeController {

    @Autowired
    CategoryService categoryService;
    @Autowired
    ProductService productService;
    @Autowired
    PropertyValueService propertyValueService;
    @Autowired
    UserService userService;
    @Autowired
    OrderItemService orderItemService;
    @Autowired
    OrderService orderService;
    @Autowired
    ReviewService reviewService;

    @RequestMapping(value = "/forehome", method = RequestMethod.GET)
    public String home(Model model){
        List<Category> categories = categoryService.getCategoryList();
        categoryService.fillProductFields(categories);
        categoryService.fillProductFieldsByRow(categories);
        model.addAttribute("cs", categories);
        return "fore/home";
    }

    @RequestMapping(value = "/foreproduct", method = RequestMethod.GET)
    public String getProductPage(@RequestParam("pid")int pId, Model model){
        Product product = productService.getProductByPid(pId);
        productService.setProductImages(product);
        productService.setSaleAndReviewCount(product);

        List<PropertyValue> pvs = propertyValueService.getPVSByProductId(pId);
        List<Review> reviews = reviewService.getReviewList(pId);
        model.addAttribute("p", product)
                .addAttribute("pvs", pvs)
                .addAttribute("reviews", reviews);
        return "fore/product";
    }

    @RequestMapping(value = "/forecategory", method = RequestMethod.GET)
    public String getCategoryPage(@RequestParam("cid")int cid, Model model){
        Category category = categoryService.getCategoryById(cid);
        categoryService.fillProductFields(category);
        model.addAttribute("c",category);
        return "fore/category";
    }

    @RequestMapping(value = "/foresearch", method = RequestMethod.POST)
    public String getSearchPage(String keyword, Model model){
        List<Product> ps = productService.searchByName(keyword);
        model.addAttribute("ps", ps);
        return "fore/searchResult";
    }

    @RequestMapping(value = "/forelogin", method = RequestMethod.POST)
    public String doLogin(String name, String password, Model model, HttpSession httpSession){
        User user = userService.getUserByNameAndPass(name, password);
        if(user == null){
            model.addAttribute("msg", "账号密码错误");
            return "fore/login";
        }
        httpSession.setAttribute("user", user);
        return "redirect:forehome";
    }

    @RequestMapping(value = "/forelogout",method = RequestMethod.GET)
    public String doLogout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:forehome";
    }

    @RequestMapping(value = "/foreregister",method = RequestMethod.POST)
    public String doRegister(String name, String password, HttpSession session, Model model){
        User user = userService.getUserByName(name);
        if(user != null){
            model.addAttribute("msg", "用户名已存在");
            return "fore/register";
        }
        User newUser = new User();
        newUser.setName(name);
        newUser.setPassword(password);
        if(!userService.addUser(newUser)){
            model.addAttribute("msg", "创建用户失败");
            return "fore/register";
        }

        return "fore/registerSuccess";
    }

    @RequestMapping(value = "/forecheckLogin", method = RequestMethod.GET)
    @ResponseBody
    public String checkLoginStatus(HttpSession session){
        User user = (User)session.getAttribute("user");
        if(user == null){
            return "fail";
        }
        return "success";
    }

    @RequestMapping(value = "/foreloginAjax", method = RequestMethod.GET)
    @ResponseBody
    public String doLoginAjax(String name, String password, HttpSession session){
        User user = userService.getUserByNameAndPass(name, password);
        if(user == null){
            return "fail";
        }
        session.setAttribute("user", user);
        return "success";
    }

    @RequestMapping(value = "/forebuyone", method = RequestMethod.GET)
    public String buyNow(int pid, int num, HttpSession session){
        User user = (User) session.getAttribute("user");
        int orderItemId = 0;
        OrderItem orderItem = orderItemService.getOrderItemByUidAndPid(user.getId(), pid);

        if(orderItem != null){
            orderItem.setNumber(orderItem.getNumber() + num);
            orderItemService.update(orderItem);
            orderItemId = orderItem.getId();
        }else{
            OrderItem oi = new OrderItem();
            oi.setNumber(num);
            oi.setPid(pid);
            oi.setUid(user.getId());
            orderItemService.add(oi);
            orderItemId = oi.getId();
        }

        return "redirect:forebuy?oiid=" + orderItemId;
    }

    @RequestMapping(value = "/forebuy", method = RequestMethod.GET)
    public String buy(String[] oiid, Model model, HttpSession session){
        List<OrderItem> orderItems = new ArrayList<>();
        float total = 0;
        for(String strOIId : oiid){
            int oiId = Integer.valueOf(strOIId);
            OrderItem orderItem = orderItemService.get(oiId);
            orderItemService.setProductField(orderItem);
            orderItems.add(orderItem);
            total += orderItem.getNumber()*orderItem.getProduct().getPromotePrice();
        }

        session.setAttribute("ois", orderItems);
        model.addAttribute("ois", orderItems)
                .addAttribute("total", total);
        return "fore/buy";
    }

    @RequestMapping(value = "/forebought", method = RequestMethod.GET)
    public String bought(Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        List<Order> os = orderService.getValid(user.getId());
        orderService.fillOrderItems(os);
        orderService.fillTotalFields(os);

        model.addAttribute("os", os);
        return "fore/bought";
    }

    @RequestMapping(value = "/forecart", method = RequestMethod.GET)
    public String cart(Model model, HttpSession session){
        User user = (User)session.getAttribute("user");
        List<OrderItem> orderItems = orderItemService.getOrderItemsByUid(user.getId());
        for(OrderItem orderItem : orderItems){
            orderItemService.setProductField(orderItem);
        }

        model.addAttribute("ois", orderItems);
        return "fore/cart";
    }

    @RequestMapping(value = "/foredeleteOrderItem", method = RequestMethod.POST)
    @ResponseBody
    public String deleteOrderItem(int oiid,  HttpSession session){
        User user = (User)session.getAttribute("user");
        if(user == null){
            return "fail";
        }
        if(!orderItemService.delete(oiid)){
            return "fail";
        }
        return "success";
    }

    @RequestMapping(value = "/forechangeOrderItem", method = RequestMethod.POST)
    @ResponseBody
    public String changeOrderItem(int pid, int number, HttpSession session){
        User user = (User)session.getAttribute("user");
        if(user == null){
            return "fail";
        }
        OrderItem orderItem = orderItemService.getOrderItemByUidAndPid(user.getId(), pid);
        if(orderItem == null){
            return "fail";
        }
        orderItem.setNumber(number);
        orderItemService.update(orderItem);
        return "success";
    }

    @RequestMapping(value = "/foredeleteOrder", method = RequestMethod.POST)
    @ResponseBody
    public String deleteOrder(int oid, HttpSession session){
        User user = (User)session.getAttribute("user");
        Order order = orderService.getById(oid);
        if(user == null || order == null){
            return "fail";
        }
        order.setStatus(OrderService.DELETE);
        if(!orderService.update(order)){
            return "fail";
        }
        return "success";
    }

    @RequestMapping(value = "/forecreateOrder", method = RequestMethod.POST)
    public String createOrder(Order order, HttpSession session){
        User user = (User)session.getAttribute("user");
        Date createDate = new Date();
        String orderCode = new SimpleDateFormat("yyyymmddhhmmssSSS").format(createDate) +
                RandomUtils.nextInt(10000);
        order.setOrderCode(orderCode);
        order.setCreateDate(createDate);
        order.setUid(user.getId());
        order.setStatus(OrderService.WAIT_PAY);
        //这个设计太不合理了， 应该由页面把oderItem的id发过来
        List<OrderItem> orderItems = (List<OrderItem>)session.getAttribute("ois");

        float total = orderService.add(order, orderItems);
        return "redirect:forealipay?oid="+order.getId() + "&total=" + total;
    }
    @RequestMapping(value = "/forealipay", method = RequestMethod.GET)
    public String alipay(){
        return "fore/alipay";
    }

    @RequestMapping(value = "/forepayed", method = RequestMethod.GET)
    public String confirmPayed(int oid, float total){
        Order order = orderService.getById(oid);
        order.setStatus(OrderService.WAIT_DELIVER);
        orderService.update(order);
        return "fore/payed";
    }

    @RequestMapping(value = "/foreconfirmPay", method = RequestMethod.GET)
    public String confirmGetProduct(int oid, Model model){
        Order order = orderService.getById(oid);
        List<Order> orders = new ArrayList<>();
        orders.add(order);
        orderService.fillOrderItems(orders);
        orderService.fillTotalFields(orders);

        model.addAttribute("o", orders.get(0));
        return "fore/confirmPay";
    }
    @RequestMapping(value = "/foreorderConfirmed", method = RequestMethod.GET)
    public String orderConfirmed(int oid, Model model){
        Order order = orderService.getById(oid);
        order.setStatus(OrderService.WAIT_REVIEW);
        orderService.update(order);

        return "fore/orderConfirmed";
    }

    @RequestMapping(value = "/forereview", method = RequestMethod.GET)
    public String review(int oid, Model model){
        Order order = orderService.getById(oid);
        List<OrderItem> orderItems = orderItemService.getOrderItemsByOid(order.getId());
        Product product = productService.getProductByPid(orderItems.get(0).getPid());
        productService.setFirstProductImage(product);
        productService.setSaleAndReviewCount(product);

        List<Review> reviews = reviewService.getReviewList(product.getId());
        reviewService.fillUser(reviews);

        model.addAttribute("o", order)
                .addAttribute("p", product)
                .addAttribute("reviews", reviews);
        return "fore/review";
    }
    @RequestMapping(value = "/foredoreview", method = RequestMethod.POST)
    public String commitReview(String content, int oid, int pid, HttpSession session){
        User user = (User)session.getAttribute("user");
        Review review = new Review();
        review.setContent(content);
        review.setPid(pid);
        review.setUid(user.getId());
        review.setCreateDate(new Date());
        reviewService.add(review);

        Order order = orderService.getById(oid);
        order.setStatus(OrderService.FINISH);
        orderService.update(order);

        return "redirect:forereview?oid="+oid +"&showonly=true";
    }

}
