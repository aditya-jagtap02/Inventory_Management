package com.inventory_management.dao;

import com.inventory_management.model.Order;
import com.inventory_management.model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminDAO {
    private Connection conn;

    public AdminDAO(Connection conn) {
        this.conn = conn;
    }

    // Get all orders
    public List<Order> getAllOrders() throws SQLException {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT o.id, o.user_id, o.total_amount, o.order_date FROM orders o";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Order order = new Order();
                        order.setId(rs.getInt("id"));
                order.setUserId(rs.getInt("user_id"));
                        order.setTotalAmount(rs.getDouble("total_amount"));
                                order.setOrderDate(String.valueOf(rs.getTimestamp("order_date")));
                orders.add(order);
            }
        }
        return orders;
    }

    // Get all products and stock
    public List<Product> getAllProducts() throws SQLException {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT id, name, price, quantity FROM product";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Product product = new Product();

                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                        product.setPrice(rs.getDouble("price"));
                                product.setQuantity(rs.getInt("quantity"));
                products.add(product);
            }
        }
        return products;
    }
    public List<Product> getProductsByOrderId(int orderId) throws SQLException {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT p.id, p.name, p.price, op.quantity " +
                "FROM order_detail op " +
                "JOIN product p ON op.product_id = p.id " +
                "WHERE op.order_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, orderId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Product product = new Product();
                    product.setId(rs.getInt("id"));
                    product.setName(rs.getString("name"));
                    product.setPrice(rs.getDouble("price"));
                    product.setQuantity(rs.getInt("quantity"));

                    products.add(product);
                }
            }
        }
        return products;
    }
    public boolean updateProductQuantity(int productId, int newQuantity) throws SQLException {
        String sql = "UPDATE product SET quantity = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, newQuantity);
            stmt.setInt(2, productId);
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        }
    }
}
