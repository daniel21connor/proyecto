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
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author PC GAMING
 */
@WebServlet(urlPatterns = {"/ReservacionServlet"})
public class ReservacionServlet extends HttpServlet {

      protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String nit = request.getParameter("nit");
        String dpi = request.getParameter("dpi");
        String fechaReserva = request.getParameter("fechaReserva");
        String estadoReserva = request.getParameter("estadoReserva");

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        try {
            String jdbcDriver = "oracle.jdbc.driver.OracleDriver";
            String dbUrl = "jdbc:oracle:thin:@localhost:1521:xe";
            String dbUser = "USARIO_CONNOR";
            String dbPassword = "1234";

            Class.forName(jdbcDriver);
            Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);

            String sql = "INSERT INTO RESERVA (ID_RESERVA, ID_PASAJERO, FECHA_RESERVA, ESTADO_RESERVA) VALUES (?, ?, TO_DATE(?, 'YYYY-MM-DD'), ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, nit);
            statement.setString(2, dpi);
            statement.setDate(3, java.sql.Date.valueOf(fechaReserva));
            statement.setString(4, estadoReserva);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                out.write("{\"status\":\"success\",\"message\":\"¡Registro exitoso!\"}");
            } else {
                out.write("{\"status\":\"error\",\"message\":\"Error al registrar la reserva.\"}");
            }

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
