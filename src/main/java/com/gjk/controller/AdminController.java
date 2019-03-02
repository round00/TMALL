package com.gjk.controller;

import com.gjk.pojo.User;
import com.gjk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class AdminController {
    @Autowired
    UserService userService;

    @RequestMapping(value = "admin_user_list", method = RequestMethod.GET)
    public String userPage(Model model){
        List<User> users = userService.getUserList();
        model.addAttribute("us", users);
        return "admin/listUser";
    }
}
