import java.io.*;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

         System.out.println("Username: " + username);
System.out.println("Password: " + password);
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/expense_db",
                "root",
                "roshani"
            );

            String query = "SELECT * FROM users WHERE username=? AND password=?";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, username);
            ps.setString(2, password);

           ResultSet rs = ps.executeQuery();

if (rs.next()) {
    System.out.println("USER FOUND");  // ✅ ADD HERE

    HttpSession session = request.getSession();
    session.setAttribute("userId", rs.getInt("id"));

    response.getWriter().println("success");
} else {
    System.out.println("USER NOT FOUND"); // ✅ ALSO ADD THIS (very useful)

    response.getWriter().println("fail");
}

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
       
    }
}