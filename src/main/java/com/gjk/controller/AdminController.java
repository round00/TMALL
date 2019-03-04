package com.gjk.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gjk.pojo.*;
import com.gjk.service.*;
import com.gjk.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

@Controller
public class AdminController {
    @Autowired
    UserService userService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    OrderService orderService;
    @Autowired
    PropertyService propertyService;
    @Autowired
    ProductService productService;
    @Autowired
    PropertyValueService propertyValueService;

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String adminHome(){
        return "redirect:admin_category_list";
    }

    /**
     * 用户
     * */
    @RequestMapping(value = "/admin_user_list", method = RequestMethod.GET)
    public String userPage(Model model, Page page){
        PageHelper.offsetPage(page.getStart(), page.getCount());
        List<User> users = userService.getUserList();

        int total = (int)new PageInfo<>(users).getTotal();
        page.setTotal(total);

        model.addAttribute("us", users)
                .addAttribute("page", page);
        return "admin/listUser";
    }

    /**
     * 分类
     */
    @RequestMapping(value = "/admin_category_list", method = RequestMethod.GET)
    public String categoryPage(Model model, Page page){
        PageHelper.offsetPage(page.getStart(), page.getCount());
        List<Category> categories = categoryService.getCategoryList();
        int total = (int)new PageInfo<>(categories).getTotal();
        page.setTotal(total);
        model.addAttribute("cs", categories);
        model.addAttribute("page", page);
        return "admin/listCategory";
    }
    @RequestMapping(value = "/admin_category_delete",method = RequestMethod.GET)
    public String deleteCategory(int id){
        return "redirect:admin_category_list";
    }

    /**
     * 订单
     */
    @RequestMapping(value = "/admin_order_list", method = RequestMethod.GET)
    public String orderPage(Model model, Page page){
        PageHelper.offsetPage(page.getStart(), page.getCount());
        List<Order> orders = orderService.getAllOrderList();

        int total = (int)new PageInfo<>(orders).getTotal();
        page.setTotal(total);
        orderService.fillOrderItems(orders);
        orderService.fillTotalFields(orders);
        orderService.filleUserFields(orders);

        model.addAttribute("os", orders);
        model.addAttribute("page", page);
        return "admin/listOrder";
    }
    @RequestMapping(value = "/admin_order_delivery", method = RequestMethod.GET)
    public String orderDelivery(int id, int start){
        Order order = orderService.getById(id);
        order.setDeliveryDate(new Date());
        order.setStatus(orderService.WAIT_CONFIRM);
        orderService.update(order);

        return "redirect:admin_order_list?start="+start;
    }

    /**
     * 属性
     */
    @RequestMapping(value = "/admin_property_list",method = RequestMethod.GET)
    public String propertyPage(int cid, Model model,Page page){
        Category category = categoryService.getCategoryById(cid);
        PageHelper.offsetPage(page.getStart(), page.getCount());
        List<Property> properties = propertyService.getPropertyList(cid);
        int total = (int)new PageInfo<>(properties).getTotal();
        page.setTotal(total);
        page.setParam("&cid="+cid);
        model.addAttribute("ps", properties)
                .addAttribute("c", category)
                .addAttribute("page", page);
        return "admin/listProperty";
    }
    @RequestMapping(value = "/admin_property_edit", method = RequestMethod.GET)
    public String editPropertyPage(int id,Model model){
        Property property = propertyService.getPropertyById(id);
        propertyService.fillCategoryField(property);

        model.addAttribute("p", property);
        return "admin/editProperty";
    }
    @RequestMapping(value = "/admin_property_update", method = RequestMethod.POST)
    public String updateProperty(Property property){
        propertyService.update(property);
        return "redirect:admin_property_list?cid="+property.getCid();
    }
    @RequestMapping(value = "/admin_property_add", method = RequestMethod.POST)
    public String addProperty(Property property){
        propertyService.add(property);
        return "redirect:admin_property_list?cid="+property.getCid();
    }
    @RequestMapping(value = "/admin_property_delete", method = RequestMethod.GET)
    public String delProperty(int id){
        Property property = propertyService.getPropertyById(id);
        propertyService.delete(property);
        return "redirect:/admin_property_list?cid="+ property.getCid();
    }

    /**
     * 商品
     */
    @RequestMapping(value = "/admin_product_list",method = RequestMethod.GET)
    public String productPage(int cid, Model model,Page page){
        Category category = categoryService.getCategoryById(cid);
        PageHelper.offsetPage(page.getStart(), page.getCount());
        List<Product> products = productService.getProductListByCid(cid);
        int total = (int)new PageInfo<>(products).getTotal();
        page.setTotal(total);
        page.setParam("&cid="+cid);
        for(Product product : products){
            productService.setFirstProductImage(product);
        }
        model.addAttribute("ps", products)
                .addAttribute("c", category)
                .addAttribute("page", page);
        return "admin/listProduct";
    }
    @RequestMapping(value = "/admin_product_edit", method = RequestMethod.GET)
    public String editProductPage(int id, Model model){
        Product product = productService.getProductByPid(id);
        model.addAttribute("p", product);
        return "admin/editProduct";
    }
    @RequestMapping(value = "/admin_product_update", method = RequestMethod.POST)
    public String updateProduct(Product product){
        productService.update(product);
        return "redirect:admin_product_list";
    }
    @RequestMapping(value = "/admin_product_add", method = RequestMethod.POST)
    public String addProduct(Product product){
        productService.update(product);
        return "redirect:admin_product_list";
    }
    @RequestMapping(value = "/admin_product_delete", method = RequestMethod.GET)
    public String deleteProduct(int id){
        Product product = productService.getProductByPid(id);
        productService.delete(product);
        return "redirect:admin_product_list?cid="+product.getCid();
    }
    /**
     * 属性值
     * */
    @RequestMapping(value = "/admin_propertyValue_edit", method = RequestMethod.GET)
    public String editPropertyValuePage(int pid, Model model){
        Product product = productService.getProductByPid(pid);
        propertyValueService.init(product);
        List<PropertyValue> propertyValues = propertyValueService.getPVSByProductId(pid);
        model.addAttribute("p", product)
                .addAttribute("pvs", propertyValues);
        return "admin/editPropertyValue";
    }
    @RequestMapping(value = "/admin_propertyValue_update", method = RequestMethod.POST)
    @ResponseBody
    public String updatePropertyValue(int id, String value) {
        PropertyValue propertyValue = propertyValueService.get(id);
        if(propertyValue == null)
            return "fail";
        propertyValue.setValue(value);
        propertyValueService.update(propertyValue);
        return "success";
    }

}
