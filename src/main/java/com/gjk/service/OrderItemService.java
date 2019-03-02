package com.gjk.service;

import com.gjk.pojo.OrderItem;

import java.util.List;

public interface OrderItemService {
    /**
     * 获取指定产品的销量
     * */
    int getSaleCount(int pid);
    /**
     * 获得指定用户的购物车列表
     * */
    List<OrderItem> getOrderItemsByUid(int uid);
    /**
     *  根据uid和pid获得购物车订单
     * */
    OrderItem getOrderItemByUidAndPid(int uid, int pid);
    /**
     * 更新OrderItem
     * */
    void update(OrderItem oi);
    /**
     * 增加OrderItem
     * */
    void add(OrderItem oi);
    /**
     * 查找
     * */
    OrderItem get(int oiid);
    /**
     * 删除
     * */
    boolean delete(int oiid);
    /**
     * 设置product字段
     * */
    void setProductField(OrderItem orderItem);
    /**
     * 根据订单ID查找
     * */
    List<OrderItem> getOrderItemsByOid(int oid);
}
