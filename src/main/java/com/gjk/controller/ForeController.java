package com.gjk.controller;

import com.gjk.pojo.Category;
import com.gjk.pojo.Product;
import com.gjk.pojo.PropertyValue;
import com.gjk.pojo.User;
import com.gjk.service.CategoryService;
import com.gjk.service.ProductService;
import com.gjk.service.PropertyValueService;
import com.gjk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
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
        model.addAttribute("p", product)
                .addAttribute("pvs", pvs);
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
}
