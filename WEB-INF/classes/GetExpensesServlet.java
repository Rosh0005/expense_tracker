import java.io.*;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class GetExpensesServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/expense_db",
                "root",
                "roshani" // same password
            );

          int userId = (int) request.getSession().getAttribute("userId");

String query = "SELECT * FROM expenses WHERE user_id=? ORDER BY id DESC";
PreparedStatement ps = con.prepareStatement(query);
ps.setInt(1, userId);

ResultSet rs = ps.executeQuery();

            StringBuilder json = new StringBuilder("[");
            boolean first = true;

            while (rs.next()) {
                if (!first) json.append(",");
                json.append("{")
                    .append("\"id\":").append(rs.getInt("id")).append(",")
                    .append("\"category\":\"").append(rs.getString("category")).append("\",")
                    .append("\"date\":\"").append(rs.getString("date")).append("\",")
                    .append("\"time\":\"").append(rs.getString("time")).append("\",")
                    .append("\"amount\":").append(rs.getDouble("amount"))
                    .append("}");
                first = false;
            }

            json.append("]");

            out.print(json.toString());
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}