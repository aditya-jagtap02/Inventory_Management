package com.inventory_management.controller;

import com.inventory_management.DBConnection;
import com.inventory_management.dao.OrderDAO;
import com.inventory_management.dao.OrderDetailDAO;
import com.inventory_management.model.Order;
import com.inventory_management.model.OrderDetail;
import com.inventory_management.service.OrderService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet("/order")
public class OrderController extends HttpServlet {
    private OrderService orderService;

    @Override
    public void init() {
        orderService = new OrderService(
                new OrderDAO(DBConnection.getConnection()),
                new OrderDetailDAO(DBConnection.getConnection())
        );
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        BufferedReader reader = req.getReader();
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }

        JSONObject json = new JSONObject(sb.toString());
        String action = json.getString("action");

        if ("create".equals(action)) {
            Order order = new Order();
            order.setUserId(1);
            order.setTotalAmount(json.getDouble("totalAmount"));
            order.setOrderDate(new Date().toString());

            List<OrderDetail> orderDetails = new ArrayList<>();
            json.getJSONArray("orderDetails").forEach(item -> {
                JSONObject detail = (JSONObject) item;
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setProductId(detail.getInt("productId"));
                orderDetail.setQuantity(detail.getInt("quantity"));
                orderDetails.add(orderDetail);
            });

            boolean success = orderService.createOrder(order, orderDetails);
            resp.getWriter().write(success ? "Order Placed Successfully" : "Failed to Place Order");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int userId = Integer.parseInt(req.getParameter("userId"));
        List<Order> orders = orderService.getOrdersByUserId(userId);
        if (orders != null) {
            resp.getWriter().write(new JSONObject().put("orders", orders).toString());
        } else {
            resp.getWriter().write("Failed to Fetch Orders");
        }
    }
}
