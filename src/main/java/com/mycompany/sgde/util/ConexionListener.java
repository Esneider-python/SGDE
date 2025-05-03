package com.mycompany.sgde.util;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import java.sql.Connection;

@WebListener
public class ConexionListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent sce) {
        Connection conexion = Conexion.getConexion(); // usa tu clase existente
        if (conexion != null) {
            sce.getServletContext().setAttribute("conexion", conexion);
        } else {
            System.out.println("❌ No se pudo establecer la conexión al iniciar la aplicación.");
        }
    }

    public void contextDestroyed(ServletContextEvent sce) {
        Connection conexion = (Connection) sce.getServletContext().getAttribute("conexion");
        if (conexion != null) {
            try {
                conexion.close();
                System.out.println("✔ Conexión cerrada correctamente.");
            } catch (Exception e) {
                System.out.println("❌ Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }
}
