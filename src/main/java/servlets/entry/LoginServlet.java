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

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String passWord = req.getParameter("password");
        User user = null;
        try {
            user = UserManager.getUser(email, passWord);
        } catch (Exception e) {
            resp.sendRedirect("Error.jsp?id=Welcome.jsp");
        }
        RequestDispatcher rd;
        if(user == null) {
            rd = req.getRequestDispatcher("loginFailed.jsp");
        } else {
            req.getSession().setAttribute(User.ATTRIBUTE_NAME, user);
            if(user.isAdmin()) {
                rd = req.getRequestDispatcher("adminHomePage.jsp");
            } else {
                rd = req.getRequestDispatcher("Home.jsp");
            }
        }
        rd.forward(req, resp);
    }
}
