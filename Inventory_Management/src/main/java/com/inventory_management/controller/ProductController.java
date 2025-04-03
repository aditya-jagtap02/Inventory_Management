package com.inventory_management.controller;

import com.inventory_management.DBConnection;
import com.inventory_management.dao.ProductDao;
import com.inventory_management.model.Product;
import com.inventory_management.service.ProductService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@WebServlet("/product")
public class ProductController extends HttpServlet {
    private ProductService productService;

    @Override
    public void init() {
        productService = new ProductService(new ProductDao(DBConnection.getConnection()));
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

        if ("add".equals(action)) {
            Product product = new Product();
            product.setName(json.getString("name"));
            product.setPrice(json.getDouble("price"));
            product.setQuantity(json.getInt("quantity"));
            product.setImageUrl(json.optString("imageUrl", ""));

            boolean success = productService.addProduct(product);
            resp.getWriter().write(success ? "Product Added Successfully" : "Failed to Add Product");

        } else if ("update".equals(action)) {
            Product product = new Product();
            product.setId(json.getInt("id"));
            product.setName(json.getString("name"));
            product.setPrice(json.getDouble("price"));
            product.setQuantity(json.getInt("quantity"));
            product.setImageUrl(json.optString("imageUrl", ""));

            boolean success = productService.updateProduct(product);
            resp.getWriter().write(success ? "Product Updated Successfully" : "Failed to Update Product");

        } else if ("delete".equals(action)) {
            int id = json.getInt("id");
            boolean success = productService.deleteProduct(id);
            resp.getWriter().write(success ? "Product Deleted Successfully" : "Failed to Delete Product");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Product> products = productService.getAllProducts();
        if (products != null) {
            resp.getWriter().write(new JSONObject().put("products", products).toString());
        } else {
            resp.getWriter().write("Failed to Fetch Products");
        }
    }
}
