package com.inventory_management.controller;

import com.inventory_management.DBConnection;
import com.inventory_management.dao.UserDAO;
import com.inventory_management.model.User;
import com.inventory_management.service.UserService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/user")
public class UserController extends HttpServlet {
    private UserService userService;

    @Override
    public void init() {
        userService = new UserService(new UserDAO(DBConnection.getConnection()));
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.getWriter().write("User Servlet Running!");
        response.setContentType("text/plain");
        response.getWriter().write("User Controller Working!");
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().write("User Servlet Running!");
        System.out.println(">>> UserController invoked!");
        BufferedReader reader = req.getReader();
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        System.out.println(">>> Request Data: " + sb.toString());
        JSONObject json = new JSONObject(sb.toString());
        String action = json.getString("action");

        if ("register".equals(action)) {
            User user = new User();
            user.setName(json.getString("name"));
            user.setEmail(json.getString("email"));
            user.setUsername(json.getString("username"));
            user.setPassword(json.getString("password"));
            user.setProfilePic(json.optString("profilePic", ""));

            boolean success = userService.registerUser(user);
            resp.getWriter().write(success ? "Registration Successful" : "User already exists");

        } else if ("login".equals(action)) {
            String username = json.getString("username");
            String password = json.getString("password");
            User user = userService.loginUser(username, password);

            if (user != null) {
                req.getSession().setAttribute("username", user.getUsername());
                req.getSession().setAttribute("name", user.getName());
                req.getSession().setAttribute("email", user.getEmail());
                resp.getWriter().write(new JSONObject(user).toString());
            } else {
                resp.getWriter().write("Invalid credentials");
            }
        } else if ("update".equals(action)) {
            User user = new User();
            user.setId(json.getInt("id"));
            user.setName(json.getString("name"));
            user.setEmail(json.getString("email"));
            user.setPassword(json.getString("password"));
            user.setProfilePic(json.optString("profilePic", ""));

            boolean success = userService.updateUser(user);
            resp.getWriter().write(success ? "Update Successful" : "Update Failed");
        }
    }
}
