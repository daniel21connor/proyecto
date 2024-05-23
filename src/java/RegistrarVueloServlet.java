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
@WebServlet(urlPatterns = {"/RegistrarVueloServlet"})
public class RegistrarVueloServlet extends HttpServlet {

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
   String idVuelo = request.getParameter("id_vuelo");
        String numeroVuelo = request.getParameter("numero_vuelo");
        String fechaSalida = request.getParameter("fecha_salida");
        String fechaLlegada = request.getParameter("fecha_llegada");
        String origen = request.getParameter("origen");
        String destino = request.getParameter("destino");
        String estadoVuelo = request.getParameter("estado_vuelo");
        String idTripulacion = request.getParameter("id_tripulacion");
        int asientosDisponibles = Integer.parseInt(request.getParameter("asientos_disponibles"));
        String idAvion = request.getParameter("id_avion");

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        String jdbcDriver = "oracle.jdbc.driver.OracleDriver";
        String dbUrl = "jdbc:oracle:thin:@localhost:1521:xe";
        String dbUser = "USUARIO_CONNOR";
        String dbPassword = "1234";

        try {
            Class.forName(jdbcDriver);
            Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);

            String sql = "INSERT INTO VUELO (ID_VUELO, NUMERO_VUELO, FECHA_SALIDA, FECHA_LLEGADA, ORIGEN, DESTINO, ESTADO_VUELO, ID_TRIPULACION, ASIENTOS_DISPONIBLES, ID_AVION) VALUES (?, ?, TO_DATE(?, 'YYYY-MM-DD\"T\"HH24:MI'), TO_DATE(?, 'YYYY-MM-DD\"T\"HH24:MI'), ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, idVuelo);
            statement.setString(2, numeroVuelo);
            statement.setString(3, fechaSalida);
            statement.setString(4, fechaLlegada);
            statement.setString(5, origen);
            statement.setString(6, destino);
            statement.setString(7, estadoVuelo);
            statement.setString(8, idTripulacion);
            statement.setInt(9, asientosDisponibles);
            statement.setString(10, idAvion);

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                out.write("{\"status\":\"success\"}");
            } else {
                out.write("{\"status\":\"error\",\"message\":\"Error al insertar el vuelo.\"}");
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
