import java.io.*;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class UpdateExpenseServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("Update servlet hit!");

        int id = Integer.parseInt(request.getParameter("id"));
        String category = request.getParameter("category");
        String date = request.getParameter("date");
        String time = request.getParameter("time");
        double amount = Double.parseDouble(request.getParameter("amount"));

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/expense_db",
                "root",
                "roshani" // 🔴 change this
            );
            int userId = (int) request.getSession().getAttribute("userId");

           String query = "UPDATE expenses SET category=?, date=?, time=?, amount=? WHERE id=? AND user_id=?";
            PreparedStatement ps = con.prepareStatement(query);

         ps.setString(1, category);
        ps.setString(2, date);
        ps.setString(3, time);
        ps.setDouble(4, amount);
        ps.setInt(5, id);
        ps.setInt(6, userId);

            int rows = ps.executeUpdate();
            System.out.println("Rows updated: " + rows);

            con.close();

            response.getWriter().println("Updated");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}