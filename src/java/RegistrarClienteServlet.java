/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
import conexion.Conexion;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
     
        // Get form data from the request
        String dpi = request.getParameter("dpi");
        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String fechaNacimiento = request.getParameter("fechaNacimiento");
        String genero = request.getParameter("genero");
        String nacionalidad = request.getParameter("nacionalidad");

        // Connect to Oracle database
        try {
            // Replace with your Oracle database connection parameters
            String jdbcDriver = "oracle.jdbc.driver.OracleDriver";
            String dbUrl = "jdbc:oracle:thin:@localhost:1521:xe";
            String dbUser = "USARIO_CONNOR";
            String dbPassword = "1234";

            Class.forName(jdbcDriver);
            Connection conexion = DriverManager.getConnection(dbUrl, dbUser, dbPassword);

            // Prepare and execute SQL statement to insert data
             String sql = "INSERT INTO PASAJERO (ID_PASAJERO, NOMBRE, APELLIDO, FECHA_NACIMIENTO, GENERO, NACIONALIDAD) VALUES (?, ?, ?, TO_DATE(?, 'YYYY-MM-DD'), ?, ?)";
            PreparedStatement statement = conexion.prepareStatement(sql);
            statement.setString(1, dpi);
            statement.setString(2, nombre);
            statement.setString(3, apellido);
            statement.setDate(4, java.sql.Date.valueOf(fechaNacimiento)); // Convert String to SQL Date
            statement.setString(5, genero);
            statement.setString(6, nacionalidad);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                // Registration successful
                response.setContentType("text/html");
                PrintWriter out = response.getWriter();
                out.println("<html><body>");
                out.println("<h3>¡Registro exitoso!</h3>");
                out.println("<p>Los datos del cliente se han registrado correctamente en la base de datos.</p>");
                out.println("<a href=\"index.html\">Regresar al formulario</a>");
                out.println("</body></html>");
            } else {
                // Registration failed
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al registrar cliente.");
            }

            statement.close();
            conexion.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error de conexión a la base de datos.");
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
