package com.inventory_management.service;

import com.inventory_management.dao.ProductDao;
import com.inventory_management.model.Product;

import java.sql.SQLException;
import java.util.List;

public class ProductService {
    private ProductDao productDAO;

    public ProductService(ProductDao productDAO) {
        this.productDAO = productDAO;
    }

    // Add Product
    public boolean addProduct(Product product) {
        try {
            return productDAO.addProduct(product);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get All Products
    public List<Product> getAllProducts() {
        try {
            return productDAO.getAllProducts();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Update Product
    public boolean updateProduct(Product product) {
        try {
            return productDAO.updateProduct(product);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete Product
    public boolean deleteProduct(int id) {
        try {
            return productDAO.deleteProduct(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
