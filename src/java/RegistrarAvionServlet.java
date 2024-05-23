/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author PC GAMING
 */
@WebServlet(urlPatterns = {"/RegistrarAvionServlet"})
public class RegistrarAvionServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    private static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String DB_USER = "USARIO_CONNOR";
    private static final String DB_PASSWORD = "1234";
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

               String codigoAvion = request.getParameter("codigo_avion");
        String modelo = request.getParameter("modelo");
        int capacidadAsientos = Integer.parseInt(request.getParameter("capacidad_asientos"));
        String estado = request.getParameter("estado");
        int capacidadEquipaje = Integer.parseInt(request.getParameter("capacidad_equipaje"));

        // Establecer la respuesta como JSON
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        // Realizar la conexión y la inserción en la base de datos
        try {
            Class.forName(JDBC_DRIVER);
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // Preparar la consulta SQL para insertar un nuevo avión
            String sql = "INSERT INTO AVION (ID_AVION, MODELO, CAPACIDAD_ASIENTOS, ESTADO, CAPACIDAD_EQUIPAJE) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, codigoAvion);
            statement.setString(2, modelo);
            statement.setInt(3, capacidadAsientos);
            statement.setString(4, estado);
            statement.setInt(5, capacidadEquipaje);

            // Ejecutar la consulta
            int rowsAffected = statement.executeUpdate();

            // Comprobar si se insertó correctamente y enviar la respuesta
            if (rowsAffected > 0) {
                out.write("{\"status\":\"success\"}");
            } else {
                out.write("{\"status\":\"error\",\"message\":\"Error al insertar el avipn.\"}");
            }

            // Cerrar la conexión y liberar recursos
            statement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            out.write("{\"status\":\"error\",\"message\":\"Error de conexion a la base de datos.\"}");
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
