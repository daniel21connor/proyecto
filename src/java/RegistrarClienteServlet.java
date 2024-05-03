/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
/**
 *
 * @author PC GAMING
 */
@WebServlet(urlPatterns = {"/RegistrarClienteServlet"})
public class RegistrarClienteServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private static final long serialVersionUID = 1L;
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        
        // Obtener parámetros del formulario
        String dpi = request.getParameter("dpi");
        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String fechaNacimiento = request.getParameter("fechaNacimiento");
        String genero = request.getParameter("genero");
        String nacionalidad = request.getParameter("nacionalidad");
        
        // Establecer conexión a la base de datos Oracle
        String jdbcUrl = "jdbc:oracle:thin:@localhost:1521:ex"; // Reemplaza con tu URL de conexión
        String usuario = "USARIO_CONNOR"; // Reemplaza con tu usuario de Oracle
        String contraseña = "1234"; // Reemplaza con tu contraseña de Oracle
        
        try (Connection conn = DriverManager.getConnection(jdbcUrl, usuario, contraseña)) {
            
            // Preparar la sentencia SQL para la inserción
            String sql = "INSERT INTO pasajero (id_pasajero, nombre, apellido, fecha_nacimiento, genero, nacionalidad) " +
                         "VALUES (?, ?, ?, TO_DATE(?, 'YYYY-MM-DD'), ?, ?)";
            
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                // Establecer valores en la sentencia preparada
                pstmt.setString(1, dpi);
                pstmt.setString(2, nombre);
                pstmt.setString(3, apellido);
                pstmt.setString(4, fechaNacimiento);
                pstmt.setString(5, genero);
                pstmt.setString(6, nacionalidad);
                
                // Ejecutar la inserción
                int filasInsertadas = pstmt.executeUpdate();
                
                if (filasInsertadas > 0) {
                    // Inserción exitosa
                    response.sendRedirect("exito.html"); // Redirigir a página de éxito
                } else {
                    // Inserción fallida
                    response.sendRedirect("error.html"); // Redirigir a página de error
                }
                
            } catch (SQLException e) {
                // Error al preparar o ejecutar la sentencia SQL
                e.printStackTrace();
                response.sendRedirect("error.html"); // Redirigir a página de error
            }
            
        } catch (SQLException e) {
            // Error al conectar a la base de datos
            e.printStackTrace();
            response.sendRedirect("error.html"); // Redirigir a página de error
        
    }
    }
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
