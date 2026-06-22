package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;


import java.io.*;
import java.sql.*;
import dao.DBConnection;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String role = req.getParameter("role");

        try {
            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(
                "SELECT * FROM users WHERE username=? AND password=? AND role=?"
            );

            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, role);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                if(role.equals("faculty")) {
                	res.sendRedirect(req.getContextPath() + "/faculty/home.jsp");
                } else if(role.equals("student")) {
                	res.sendRedirect(req.getContextPath() + "/student/home.jsp");
                } else {
                	res.sendRedirect(req.getContextPath() + "/admin/home.jsp");
                }

            } else {
                res.getWriter().println("Invalid Login");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}