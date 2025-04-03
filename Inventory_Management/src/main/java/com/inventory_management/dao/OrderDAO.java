package com.inventory_management.dao;

import com.inventory_management.model.Order;
import com.inventory_management.model.OrderDetail;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {

    private Connection connection;

    public OrderDAO(Connection connection) {
        this.connection = connection;
    }

    // Create Order
    public int createOrder(Order order, List<OrderDetail> orderDetails) throws SQLException {
        String query = "INSERT INTO orders (user_id, total_amount) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, order.getUserId());
            stmt.setDouble(2, order.getTotalAmount());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                int orderId = rs.getInt(1);  // Get generated order ID
                updateProductQuantities(orderDetails);  // Update product stock
                return orderId;
            }
        }
        return -1;
    }

    // Update product quantities in the database
    private void updateProductQuantities(List<OrderDetail> orderItems) throws SQLException {
        for (OrderDetail item : orderItems) {
            String updateQuery = "UPDATE product SET quantity = quantity - ? WHERE id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(updateQuery)) {
                stmt.setInt(1, item.getQuantity());  // Reduce stock by the quantity ordered
                stmt.setInt(2, item.getProductId()); // Find product by product ID
                stmt.executeUpdate();
            }
        }
    }

    // Get Orders by User ID
    public List<Order> getOrdersByUserId(int userId) throws SQLException {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM orders WHERE user_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Order order = new Order();
                order.setId(rs.getInt("id"));
                order.setUserId(rs.getInt("user_id"));
                order.setTotalAmount(rs.getDouble("total_amount"));
                order.setOrderDate(rs.getString("order_date"));
                orders.add(order);
            }
        }
        return orders;
    }
}
