package com.inventory_management.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.inventory_management.DBConnection;
import com.inventory_management.dao.AdminDAO;

@WebServlet("/admin/update-product")
public class UpdateProductController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {



        BufferedReader reader = request.getReader();
        JsonObject json = new Gson().fromJson(reader, JsonObject.class);
        int productId = json.get("productId").getAsInt();
        int newQuantity = json.get("newQuantity").getAsInt();

        try (Connection conn = DBConnection.getConnection()) {
            AdminDAO adminDAO = new AdminDAO(conn);
            boolean success = adminDAO.updateProductQuantity(productId, newQuantity);

            if (success) {
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}

