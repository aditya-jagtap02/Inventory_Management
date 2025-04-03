package com.inventory_management.service;

import com.inventory_management.dao.UserDAO;
import com.inventory_management.model.User;

import java.sql.SQLException;

public class UserService {
    private UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    // Register User
    public boolean registerUser(User user) {
        try {
            if (userDAO.loginUser(user.getUsername(), user.getPassword()) == null) {
                return userDAO.registerUser(user);
            }
            return false; // User already exists
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Login User
    public User loginUser(String username, String password) {
        try {
            return userDAO.loginUser(username, password);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Update User Profile
    public boolean updateUser(User user) {
        try {
            return userDAO.updateUser(user);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
