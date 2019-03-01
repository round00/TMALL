package com.gjk.service.Impl;

import com.gjk.mapper.OrderItemMapper;
import com.gjk.pojo.OrderItemExample;
import com.gjk.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderItemServiceImpl implements OrderItemService {
    @Autowired
    OrderItemMapper orderItemMapper;

    @Override
    public int getSaleCount(int pid) {
        OrderItemExample orderItemExample = new OrderItemExample();
        orderItemExample.createCriteria().andPidEqualTo(pid);
        return (int)orderItemMapper.countByExample(orderItemExample);
    }
}
