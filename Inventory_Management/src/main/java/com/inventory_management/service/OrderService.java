package com.inventory_management.service;

import com.inventory_management.dao.OrderDAO;
import com.inventory_management.dao.OrderDetailDAO;
import com.inventory_management.model.Order;
import com.inventory_management.model.OrderDetail;

import java.sql.SQLException;
import java.util.List;

public class OrderService {

    private OrderDAO orderDAO;
    private OrderDetailDAO orderDetailDAO;

    public OrderService(OrderDAO orderDAO, OrderDetailDAO orderDetailDAO) {
        this.orderDAO = orderDAO;
        this.orderDetailDAO = orderDetailDAO;
    }

    // Create Order

    public boolean createOrder(Order order, List<OrderDetail> orderDetails) {
        try {
            int orderId = orderDAO.createOrder(order,orderDetails);
            if (orderId > 0) {
                for (OrderDetail detail : orderDetails) {
                    detail.setOrderId(orderId);
                    orderDetailDAO.addOrderDetail(detail);
                }
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get Orders by User ID
    public List<Order> getOrdersByUserId(int userId) {
        try {
            return orderDAO.getOrdersByUserId(userId);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Get Order Details by Order ID
    public List<OrderDetail> getOrderDetailsByOrderId(int orderId) {
        try {
            return orderDetailDAO.getOrderDetailsByOrderId(orderId);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
