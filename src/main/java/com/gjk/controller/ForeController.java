package com.gjk.controller;

import com.gjk.pojo.Category;
import com.gjk.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class ForeController {

    @Autowired
    CategoryService categoryService;

    @RequestMapping(value = "/forehome", method = RequestMethod.GET)
    public String home(Model model){
        List<Category> categories = categoryService.getCategoryList();
        categoryService.fillProductFields(categories);
        categoryService.fillProductFieldsByRow(categories);
        model.addAttribute("cs", categories);
        return "fore/home";
    }
}
