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
@WebServlet(urlPatterns = {"/EditarMantenimientoServlet"})
public class EditarMantenimientoServlet extends HttpServlet {

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
   PrintWriter out = response.getWriter();

    String idMantenimiento = request.getParameter("idMantenimiento");
String tipoMantenimiento = request.getParameter("tipoMantenimiento");
String fechaInicio = request.getParameter("fechaInicio");
String fechaFin = request.getParameter("fechaFin");
String descripcion = request.getParameter("descripcion");
String costo = request.getParameter("costo");
String estado = request.getParameter("estado");

        try {
            String jdbcDriver = "oracle.jdbc.driver.OracleDriver";
            String dbUrl = "jdbc:oracle:thin:@localhost:1521:xe";
            String dbUser = "AVIONES";
            String dbPassword = "1234";

            Class.forName(jdbcDriver);
            Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            String sql = "UPDATE MANTENIMIENTO SET TIPO_MANTENIMIENTO = ?, FECHA_INICIO = TO_DATE(?, 'YYYY-MM-DD'), FECHA_FIN = TO_DATE(?, 'YYYY-MM-DD'), DESCRIPCION = ?, COSTO = ?, ESTADO = ? WHERE ID_MANTENIMIENTO = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, tipoMantenimiento);
            statement.setString(2, fechaInicio);
            statement.setString(3, fechaFin);
            statement.setString(4, descripcion);
            statement.setString(5, costo);
            statement.setString(6, estado);
            statement.setString(7, idMantenimiento);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                out.write("{\"status\":\"success\",\"message\":\"Datos de mantenimiento actualizados exitosamente.\"}");
            } else {
                out.write("{\"status\":\"error\",\"message\":\"No se encontró el registro de mantenimiento.\"}");
            }

            statement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            out.write("{\"status\":\"error\",\"message\":\"Error al actualizar los datos de mantenimiento.\"}");
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
