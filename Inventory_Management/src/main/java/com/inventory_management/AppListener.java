package com.inventory_management;



import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class AppListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println(">>>>> web.xml loaded successfully!");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println(">>>>> Application stopped.");
    }
}

