package filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/*")
public class LoginFilter implements Filter {
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String username = httpRequest.getParameter("username");
        String password = httpRequest.getParameter("password");
        boolean isUserValid = true;
        // check if user is in database
        if (isUserValid) {
            String jwt = "";
            // jwt = JWTHelper.generate(username);
            Cookie cookie = new Cookie("jwt", jwt);
            // set cookie on cas
            httpResponse.sendRedirect("http://localhost:8080/cas/login?username=" + username
                    + "&password=" + password);
        } else {
            httpResponse.setContentType("text/html; charset=UTF-8");
            httpResponse.getWriter().println("登录失败");
            httpResponse.sendRedirect("http://localhost:8080/cas/login");
        }
    }

    public void destroy() {

    }
}
