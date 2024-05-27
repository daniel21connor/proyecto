/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 @WebServlet(urlPatterns = {"/ObtenerPagosServlet"})
public class ObtenerPagosServlet extends HttpServlet {

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

        try {
            String jdbcDriver = "oracle.jdbc.driver.OracleDriver";
            String dbUrl = "jdbc:oracle:thin:@localhost:1521:xe";
            String dbUser = "AVIONES";
            String dbPassword = "1234";

            Class.forName(jdbcDriver);
            Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            String sql = "SELECT * FROM PAGOS";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            StringBuilder htmlBuilder = new StringBuilder();
            while (resultSet.next()) {
                htmlBuilder.append("<tr>");
                htmlBuilder.append("<td>").append(resultSet.getString("ID_PAGO")).append("</td>");
                htmlBuilder.append("<td>").append(resultSet.getString("MONTO")).append("</td>");
                htmlBuilder.append("<td>").append(resultSet.getString("FECHA_PAGO")).append("</td>");
                htmlBuilder.append("<td>").append(resultSet.getString("METODO_PAGO")).append("</td>");
                htmlBuilder.append("<td>").append(resultSet.getString("ID_RESERVA")).append("</td>");
                htmlBuilder.append("<td>");
                htmlBuilder.append("<button class='btn btn-warning btn-sm' onclick='editPago(\"").append(resultSet.getString("ID_PAGO")).append("\")'>Editar</button>");
                htmlBuilder.append("<button class='btn btn-danger btn-sm' onclick='deletePago(\"").append(resultSet.getString("ID_PAGO")).append("\")'>Borrar</button>");
                htmlBuilder.append("</td>");
                htmlBuilder.append("</tr>");
            }

            out.println(htmlBuilder.toString());
            
            resultSet.close();
            statement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            out.println("<tr><td colspan='6'>Error al obtener datos de pagos</td></tr>");
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
