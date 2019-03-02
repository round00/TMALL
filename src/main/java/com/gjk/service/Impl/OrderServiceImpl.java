package com.gjk.service.Impl;

import com.gjk.mapper.OrderMapper;
import com.gjk.pojo.Order;
import com.gjk.pojo.OrderExample;
import com.gjk.pojo.OrderItem;
import com.gjk.pojo.Product;
import com.gjk.service.OrderItemService;
import com.gjk.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    OrderItemService orderItemService;
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

    ///这个应该用上事务管理,现在对事务怎么用还不太熟， 先不写了
    @Override
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
}
