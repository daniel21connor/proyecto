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
@WebServlet(urlPatterns = {"/PagoServlet"})
public class PagoServlet extends HttpServlet {

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
       // Obtener los parámetros del formulario
        double monto = Double.parseDouble(request.getParameter("monto"));
        String fechaPago = request.getParameter("fechaPago");
        String metodoPago = request.getParameter("metodoPago");
        int idReserva = Integer.parseInt(request.getParameter("idReserva"));

        // Establecer la respuesta como JSON
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        // Realizar la conexión y la inserción en la base de datos
        try {
            String jdbcDriver = "oracle.jdbc.driver.OracleDriver";
            String dbUrl = "jdbc:oracle:thin:@localhost:1521:xe";
            String dbUser = "USARIO_CONNOR";
            String dbPassword = "1234";

            Class.forName(jdbcDriver);
            Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);

            // Preparar la consulta SQL para insertar un nuevo pago
            String sql = "INSERT INTO PAGOS (MONTO, FECHA_PAGO, METODO_PAGO, ID_RESERVA) VALUES (?, TO_DATE(?, 'YYYY-MM-DD'), ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setDouble(1, monto);
            statement.setString(2, fechaPago);
            statement.setString(3, metodoPago);
            statement.setInt(4, idReserva);

            // Ejecutar la consulta
            int rowsAffected = statement.executeUpdate();

            // Comprobar si se insertó correctamente y enviar la respuesta
            if (rowsAffected > 0) {
                out.write("{\"status\":\"success\"}");
            } else {
                out.write("{\"status\":\"error\",\"message\":\"Error al insertar el pago.\"}");
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
