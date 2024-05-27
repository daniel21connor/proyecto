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

@WebServlet(urlPatterns = {"/RegistrarVueloServlet"})
public class RegistrarVueloServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        // Get form data from the request
        String codigoVuelo = request.getParameter("codigoVuelo");
        String numeroVuelo = request.getParameter("numeroVuelo");
        String fechaSalida = request.getParameter("fechaSalida");
        String fechaLlegada = request.getParameter("fechaLlegada");
        String origen = request.getParameter("origen");
        String destino = request.getParameter("destino");
        String estadoVuelo = request.getParameter("estadoVuelo");
        String idTripulacion = request.getParameter("idTripulacion");
        int asientosDisponibles = Integer.parseInt(request.getParameter("asientosDisponibles"));
        String idAvion = request.getParameter("idAvion");

        try {
            // Connect to your database
            String jdbcDriver = "oracle.jdbc.driver.OracleDriver";
            String dbUrl = "jdbc:oracle:thin:@localhost:1521:xe";
            String dbUser = "AVIONES";
            String dbPassword = "1234";

            Class.forName(jdbcDriver);
            Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);

            // Prepare SQL statement to insert data
            String sql = "INSERT INTO VUELO (ID_VUELO, NUMERO_VUELO, FECHA_SALIDA, FECHA_LLEGADA, ORIGEN, DESTINO, ESTADO_VUELO, ID_TRIPULACION, ASIENTOS_DISPONOBLES, ID_AVION) VALUES (?, ?, TO_DATE(?, 'YYYY-MM-DD'), TO_DATE(?, 'YYYY-MM-DD'), ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, codigoVuelo);
            statement.setString(2, numeroVuelo);
            statement.setString(3, fechaSalida);
            statement.setString(4, fechaLlegada);
            statement.setString(5, origen);
            statement.setString(6, destino);
            statement.setString(7, estadoVuelo);
            statement.setString(8, idTripulacion);
            statement.setInt(9, asientosDisponibles);
            statement.setString(10, idAvion);

            // Execute the SQL statement
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                // Registration successful
                out.write("{\"status\":\"success\",\"message\":\"¡Registro exitoso!\"}");
            } else {
                // Registration failed
                out.write("{\"status\":\"error\",\"message\":\"Error al registrar el vuelo.\"}");
            }

            // Clean-up environment
            statement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            out.write("{\"status\":\"error\",\"message\":\"Error de conexión a la base de datos.\"}");
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
