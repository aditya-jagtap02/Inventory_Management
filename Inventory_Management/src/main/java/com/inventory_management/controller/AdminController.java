package com.inventory_management.controller;

import com.google.gson.Gson;
import com.inventory_management.DBConnection;
import com.inventory_management.dao.AdminDAO;
import com.inventory_management.model.Order;
import com.inventory_management.model.Product;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;

import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@WebServlet("/admin")
public class AdminController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");


        try (Connection conn = DBConnection.getConnection()) {
            AdminDAO adminDAO = new AdminDAO(conn);
            List<Order> orders = adminDAO.getAllOrders();
            List<Product> products = adminDAO.getAllProducts();

            // Prepare data for JSON response
            Map<String, Object> data = new HashMap<>();
            data.put("orders", orders);
            data.put("products", products);

            String json = new Gson().toJson(data);
            response.getWriter().write(json);
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
