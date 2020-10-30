package filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/*")
public class LogoutFilter implements Filter {

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpReq = (HttpServletRequest) req;
        HttpServletResponse httpResp = (HttpServletResponse) resp;
        String logout = httpReq.getParameter("logout");
        Cookie[] cookies = httpReq.getCookies();
        if (logout != null) {
            // remove cookie cookies["jwt"] = null
            Cookie cookie = new Cookie("jwt", null);
            // cas reset cookie
            httpResp.sendRedirect("http://localhost:8080/cas/login?logout=logout");
        } else {
            chain.doFilter(req, resp);
        }
    }

    public void destroy() {

    }
}
