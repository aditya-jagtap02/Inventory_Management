package com.inventory_management.controller;

import com.google.gson.Gson;
import com.inventory_management.DBConnection;
import com.inventory_management.dao.AdminDAO;
import com.inventory_management.model.Product;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet("/admin/order-details")
public class AdminOrderDetails extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String orderIdParam = request.getParameter("orderId");
        if (orderIdParam == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
            int orderId = Integer.parseInt(orderIdParam);

            try (Connection conn = DBConnection.getConnection()) {
                AdminDAO adminDAO = new AdminDAO(conn);
                List<Product> products = adminDAO.getProductsByOrderId(orderId);

                String json = new Gson().toJson(products);
                response.getWriter().write(json);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}