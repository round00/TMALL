package com.gjk.service.Impl;

import com.gjk.mapper.OrderMapper;
import com.gjk.pojo.*;
import com.gjk.service.OrderItemService;
import com.gjk.service.OrderService;
import com.gjk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    OrderItemService orderItemService;
    @Autowired
    UserService userService;
    @Override
    public List<Order> get(int uid) {
        OrderExample orderExample = new OrderExample();
        orderExample.createCriteria().andUidEqualTo(uid);
        return orderMapper.selectByExample(orderExample);
    }

    @Override
    public Order getById(int id) {
        return orderMapper.selectByPrimaryKey(id);
    }

    @Override
    public boolean update(Order order) {
        int res = orderMapper.updateByPrimaryKeySelective(order);
        if(res != 1){
            return false;
        }
        return true;
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackForClassName = "Exception")
    public float add(Order order, List<OrderItem> orderItems) {
        orderMapper.insertSelective(order);
        float total = 0;
        for(OrderItem orderItem : orderItems){
            orderItem.setOid(order.getId());
            orderItemService.update(orderItem);
            total += orderItem.getNumber() * orderItem.getProduct().getPromotePrice();
        }
        return total;
    }

    @Override
    public List<Order> getValid(int uid) {
        OrderExample orderExample = new OrderExample();
        orderExample.createCriteria().andUidEqualTo(uid)
                .andStatusNotEqualTo(OrderService.DELETE);
        return orderMapper.selectByExample(orderExample);
    }

    @Override
    public void fillOrderItems(List<Order> orders) {
        for(Order order : orders){
            List<OrderItem> orderItems = orderItemService.getOrderItemsByOid(order.getId());
            for(OrderItem orderItem : orderItems){
                orderItemService.setProductField(orderItem);
            }
            order.setOrderItems(orderItems);
        }
    }

    @Override
    public void fillTotalFields(List<Order> orders) {
        for(Order order : orders){
            List<OrderItem> orderItems = order.getOrderItems();
            int totalNumber = 0;
            float totalMoney = 0;
            for(OrderItem orderItem : orderItems){
                Product product = orderItem.getProduct();
                totalNumber += orderItem.getNumber();
                totalMoney += product.getPromotePrice() * orderItem.getNumber();
            }

            order.setTotal(totalMoney);
            order.setTotalNumber(totalNumber);
        }
    }

    @Override
    public void filleUserFields(List<Order> orders) {
        for(Order order : orders){
            User user = userService.getUserById(order.getUid());
            order.setUser(user);
        }
    }

    @Override
    public List<Order> getAllOrderList() {
        OrderExample orderExample = new OrderExample();
        orderExample.setOrderByClause("id desc");
        return orderMapper.selectByExample(orderExample);
    }
}
