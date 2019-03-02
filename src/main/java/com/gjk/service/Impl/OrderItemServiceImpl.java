package com.gjk.service.Impl;

import com.gjk.mapper.OrderItemMapper;
import com.gjk.pojo.OrderExample;
import com.gjk.pojo.OrderItem;
import com.gjk.pojo.OrderItemExample;
import com.gjk.pojo.Product;
import com.gjk.service.OrderItemService;
import com.gjk.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemServiceImpl implements OrderItemService {
    @Autowired
    OrderItemMapper orderItemMapper;
    @Autowired
    ProductService productService;

    @Override
    public int getSaleCount(int pid) {
        OrderItemExample orderItemExample = new OrderItemExample();
        orderItemExample.createCriteria().andPidEqualTo(pid);
        List<OrderItem> orderItems = orderItemMapper.selectByExample(orderItemExample);
        int total = 0;
        for(OrderItem oi : orderItems){
            total += oi.getNumber();
        }
        return total;

    }

    @Override
    public List<OrderItem> getOrderItemsByUid(int uid) {
        OrderItemExample orderItemExample = new OrderItemExample();
        orderItemExample.createCriteria().andUidEqualTo(uid)
                .andOidIsNull();
        return orderItemMapper.selectByExample(orderItemExample);
    }

    @Override
    public OrderItem getOrderItemByUidAndPid(int uid, int pid) {
        OrderItemExample orderItemExample = new OrderItemExample();
        orderItemExample.createCriteria().andUidEqualTo(uid)
                .andPidEqualTo(pid)
                .andOidIsNull();
        List<OrderItem> orderItems = orderItemMapper.selectByExample(orderItemExample);
        if(orderItems.isEmpty()){
            return null;
        }
        return orderItems.get(0);
    }

    @Override
    public void update(OrderItem oi) {
        orderItemMapper.updateByPrimaryKeySelective(oi);
    }

    @Override
    public void add(OrderItem oi) {
        orderItemMapper.insert(oi);
    }

    @Override
    public OrderItem get(int oiid) {
        return orderItemMapper.selectByPrimaryKey(oiid);
    }

    @Override
    public boolean delete(int oiid) {
        int res = orderItemMapper.deleteByPrimaryKey(oiid);
        if(res != 1){
            return false;
        }
        return true;
    }

    @Override
    public void setProductField(OrderItem orderItem) {
        Product product = productService.getProductByPid(orderItem.getPid());
        productService.setFirstProductImage(product);
        orderItem.setProduct(product);
    }

    @Override
    public List<OrderItem> getOrderItemsByOid(int oid) {
        OrderItemExample orderItemExample = new OrderItemExample();
        orderItemExample.createCriteria().andOidEqualTo(oid);
        return orderItemMapper.selectByExample(orderItemExample);
    }
}
