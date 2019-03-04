package com.gjk.service;

import com.gjk.pojo.Order;
import com.gjk.pojo.OrderItem;

import java.util.List;

public interface OrderService {

    String DELETE = "delete";
    String WAIT_PAY = "waitPay";
    String WAIT_DELIVER = "waitDelivery";
    String WAIT_REVIEW = "waitReview";
    String WAIT_CONFIRM = "waitConfirm";
    String FINISH = "finish";
    /**
     * 根据uid获取订单
     * */
    List<Order> get(int uid);

    Order getById(int id);
    /**
     * 更新
     */
    boolean update(Order order);
    /**
     * 增加
     * @return：总金额
     * */
    float add(Order order, List<OrderItem> orderItems);
    /**
     * 查询有效订单，非删除的那种
     * */
    List<Order> getValid(int uid);
    /**
     * 填充OrderItem字段
     * */
    void fillOrderItems(List<Order> orders);
    /**
     * 填充total字段
     * */
    void fillTotalFields(List<Order> orders);
    /**
     * 填充User字段
     * */
    void filleUserFields(List<Order> orders);
    /**
     * 获取所有订单
     * */
    List<Order> getAllOrderList();
}
