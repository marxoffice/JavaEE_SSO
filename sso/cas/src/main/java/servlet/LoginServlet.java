package servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(value = "/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // User = req.getParameter("user");
        String username = "";
        // username = User.getUsername();
        Cookie[] cookies = req.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(username)) {
                String usernameFromJwt = "";
                // usernameFromJwt = JWTHelper.getUsernameFromJwt();
                if (username.equals(usernameFromJwt)) {
                    resp.sendRedirect(req.getContextPath()+"/app");
                } else {
                    resp.setContentType("text.html; charset=UTF-8");
                    resp.getWriter().println("please login");
                    resp.sendRedirect("http://localhost:8080/cas/login");
                }
                break;
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        Cookie[] cookies = req.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(username)) {
                String usernameFromJwt = "";
                // usernameFromJwt = JWTHelper.getUsernameFromJwt();
                if (username.equals(usernameFromJwt)) {
                    resp.sendRedirect(req.getContextPath()+"/app");
                }
                break;
            }
        }
        // check username and password
        boolean isUserValid = true;
        // isUserValid = UserController.check(username, password);
        if (isUserValid) {
            String jwt = "";
            // jwt = JWTHelper.generate(username);
            Cookie cookie = new Cookie(username, jwt);
            // send cookie to app1, app2;
            // ....
        } else {
            resp.setContentType("text.html; charset=UTF-8");
            resp.getWriter().println("login failed");
            resp.sendRedirect("http://localhost:8080/cas/login");
        }
    }
}
