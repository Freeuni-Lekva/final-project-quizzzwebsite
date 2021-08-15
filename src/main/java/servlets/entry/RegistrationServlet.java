package servlets.entry;


import user.User;
import user.UserManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/RegistrationServlet")
public class RegistrationServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String passWord = req.getParameter("password");
        String userName = req.getParameter("username");
        RequestDispatcher rd;
        User user = null;
        try {
            user = UserManager.registerUser(userName, email, passWord);
        } catch(Exception e) {
            resp.sendRedirect("Error.jsp?id=Registration.jsp");
            return;
        }

        if(user == null) {
            rd = req.getRequestDispatcher("RegistrationFailed.jsp");
        } else {
            req.getSession().setAttribute(User.ATTRIBUTE_NAME, user);
            if(user.isAdmin()) {
                rd = req.getRequestDispatcher("adminPage.jsp");
            } else {
                rd = req.getRequestDispatcher("Home.jsp");
            }
        }
        rd.forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
