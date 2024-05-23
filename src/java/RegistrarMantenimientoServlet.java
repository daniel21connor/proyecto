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
@WebServlet(urlPatterns = {"/RegistrarMantenimientoServlet"})
public class RegistrarMantenimientoServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
     // Establecer la respuesta como JSON
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        try {
            // Obtener los parámetros del formulario
            String idMantenimiento = request.getParameter("id_mantenimiento");
            String tipoMantenimiento = request.getParameter("tipo_mantenimiento");
            String fechaInicio = request.getParameter("fecha_inicio");
            String fechaFin = request.getParameter("fecha_fin");
            String descripcion = request.getParameter("descripcion");
            double costo = Double.parseDouble(request.getParameter("costo"));
            String estado = request.getParameter("estado");
            String idAvion = request.getParameter("id_avion");

            // Conectar a la base de datos
            String jdbcDriver = "oracle.jdbc.driver.OracleDriver";
            String dbUrl = "jdbc:oracle:thin:@localhost:1521:xe";
            String dbUser = "USARIO_CONNOR";
            String dbPassword = "1234";
            Class.forName(jdbcDriver);
            Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);

            // Preparar la consulta SQL
            String sql = "INSERT INTO MANTENIMIENTO (ID_MANTENIMIENTO, TIPO_MANTENIMIENTO, FECHA_INICIO, FECHA_FIN, DESCRIPCION, COSTO, ESTADO, ID_AVION) VALUES (?, ?, TO_DATE(?, 'YYYY-MM-DD'), TO_DATE(?, 'YYYY-MM-DD'), ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, idMantenimiento);
            statement.setString(2, tipoMantenimiento);
            statement.setString(3, fechaInicio);
            statement.setString(4, fechaFin);
            statement.setString(5, descripcion);
            statement.setDouble(6, costo);
            statement.setString(7, estado);
            statement.setString(8, idAvion);

            // Ejecutar la consulta
            int rowsAffected = statement.executeUpdate();

            // Comprobar si se insertó correctamente y enviar la respuesta
            if (rowsAffected > 0) {
                out.write("{\"status\":\"success\"}");
            } else {
                out.write("{\"status\":\"error\",\"message\":\"Error al insertar el mantenimiento.\"}");
            }

            // Cerrar la conexión y liberar recursos
            statement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            out.write("{\"status\":\"error\",\"message\":\"Error de conexion a la base de datos.\"}");
        } catch (NumberFormatException e) {
            e.printStackTrace();
            out.write("{\"status\":\"error\",\"message\":\"Error en el formato del costo.\"}");
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
