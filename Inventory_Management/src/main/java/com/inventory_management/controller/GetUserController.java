package com.inventory_management.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/getUser")
public class GetUserController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            String username = (String) session.getAttribute("username");
            String name = (String) session.getAttribute("name");
            String email = (String) session.getAttribute("email");

            if (username != null) {
                // Create a JSON response
                String jsonResponse = String.format("{\"username\":\"%s\", \"name\":\"%s\", \"email\":\"%s\"}",
                        username, name != null ? name : "", email != null ? email : "");

                response.setContentType("application/json");
                response.getWriter().write(jsonResponse);
                return;
            }
        }
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
}