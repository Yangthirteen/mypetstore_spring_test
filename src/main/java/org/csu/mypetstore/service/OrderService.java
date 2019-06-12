package org.csu.mypetstore.service;

import org.csu.mypetstore.domain.Order;
import java.util.List;

public interface OrderService {

    public void insertOrder(Order order);

    public Order getOrder(int orderId);

    public List<Order> getOrdersByUsername(String username);

    public int getNextId(String name);

    public void setOrderId(Order order);
}
