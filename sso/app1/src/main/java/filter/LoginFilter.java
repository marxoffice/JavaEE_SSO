package filter;

import JwtTools.JwtUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 为每一个页面添加一个验证登录
 */
@WebFilter(urlPatterns="/*")
public class LoginFilter implements Filter {
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        Cookie[] cookies = ((HttpServletRequest) request).getCookies();
        String user_id = null;
        if(cookies != null && cookies.length>0) {
            for (Cookie cookie : cookies) {
                if("JWT".equals(cookie.getName()))
                    user_id = JwtUtils.getPayload(cookie.getValue());
            }
        }
        if(user_id == null){ // 若user_id为空，则jwt中无信息，重定向到cas的登录页面，网址中带上App的网址信息，便于跳回
            httpResponse.sendRedirect("http://localhost:8080/cas/login.do" + "?"
                    + "LOCAL_SERVICE" + "="
                    + httpRequest.getRequestURL());
        }
    }

    public void destroy() {

    }
}
