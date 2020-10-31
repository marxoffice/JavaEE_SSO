package filter;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@WebServlet("/*")
public class UserFilter implements Filter {
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // get jwt in cookie
        HttpServletRequest httpReq = (HttpServletRequest) request;
        HttpServletResponse httpResp = (HttpServletResponse) response;
        Cookie[] cookies = httpReq.getCookies();
        String jwt = "";
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("jwt")) {
                jwt = cookie.getValue();
                break;
            }
        }
        boolean isLoginIn = true;
        // check jwt is wrong or null
        // isLoginIn = JWTHelper.checkJWT(jwt);
        if (isLoginIn) {
            // set cookie
            Cookie cookie = new Cookie("jwt", jwt);
            chain.doFilter(request, response);
        } else {
            // redirect to cas
            httpResp.sendRedirect("http://localhost:8080/cas/login");
        }
    }

    public void destroy() {

    }
}
