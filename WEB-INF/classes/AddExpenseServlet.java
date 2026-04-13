import java.io.*;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class AddExpenseServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("Servlet hit!");

        String category = request.getParameter("category");
        String date = request.getParameter("date");
        String time = request.getParameter("time");
        String amount = request.getParameter("amount");

        try {
            // Load MySQL driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to database
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/expense_db",
                "root",
                "roshani"   // ⚠️ CHANGE THIS to your MySQL password
            );
            int userId = (int) request.getSession().getAttribute("userId");
            // SQL insert query
           String query = "INSERT INTO expenses (category, date, time, amount, user_id) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, category);
            ps.setString(2, date);
            ps.setString(3, time);
            ps.setDouble(4, Double.parseDouble(amount));
            ps.setInt(5, userId);
            int rows = ps.executeUpdate();

            System.out.println("Rows inserted: " + rows);
            System.out.println("Data inserted!");

            con.close();

            response.setContentType("text/plain");
            response.getWriter().println("Saved successfully ✅");

        } catch (Exception e) {
            System.out.println("ERROR OCCURRED:");
            e.printStackTrace();  // VERY IMPORTANT (prints error in CMD)
        }
    }
}