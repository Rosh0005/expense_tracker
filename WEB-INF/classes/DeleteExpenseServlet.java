import java.io.*;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class DeleteExpenseServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/expense_db",
                "root",
                "roshani" // your password
            );
            int userId = (int) request.getSession().getAttribute("userId");

            String query = "DELETE FROM expenses WHERE id=? AND user_id=?";
            PreparedStatement ps = con.prepareStatement(query);
           ps.setInt(1, id);
           ps.setInt(2, userId);

            int rows = ps.executeUpdate();
            System.out.println("Deleted rows: " + rows);

            con.close();

            response.getWriter().println("Deleted");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}