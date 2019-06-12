package org.csu.mypetstore.service.impl;

import org.csu.mypetstore.domain.Order;
import org.csu.mypetstore.persistence.OrderMapper;
import org.csu.mypetstore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public void insertOrder(Order order) {
        orderMapper.insertOrder(order);
    }

    @Override
    public Order getOrder(int orderId) {
        return orderMapper.getOrder(orderId);
    }

    @Override
    public List<Order> getOrdersByUsername(String username) {
        return orderMapper.getOrdersByUsername(username);
    }

    @Override
    public int getNextId(String name) {
        return 0;
    }

    @Override
    public void setOrderId(Order order) {
        orderMapper.setOrderId(order);
    }
}
