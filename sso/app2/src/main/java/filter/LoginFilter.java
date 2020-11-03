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
 * 若已经存在App2_JWT cookie，则登录成功；
 * 若不存在本地APP2_JWT cookie，
 * 首先判断网址链接是否带有JWT属性，若有，则是cas发送过来的JWT信息，保存为本地cookie APP2_JWT
 * 若网址链接无JWT属性，则重定向到cas登录界面进行登录验证
 */
@WebFilter("/*")
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
                if("APP2_JWT".equals(cookie.getName()))
                    user_id = JwtUtils.getPayload(cookie.getValue());
            }
        }
        // 获得网址的参数JWT
        String JWT = httpRequest.getParameter("jwt_cookie");
        if(JWT != null){  // 网址存在JWT，是cas已完成登录验证的返回
            // 设置本地cookie
            Cookie cookie = new Cookie("APP2_JWT", JWT);
            cookie.setMaxAge(60*5);
            cookie.setPath("/");
            httpResponse.addCookie(cookie);
            // hide jwt message in url
            httpResponse.sendRedirect(String.valueOf(httpRequest.getRequestURL()));
//            chain.doFilter(httpRequest, httpResponse);
        }
        else if(user_id == null){ // 若user_id为空，则jwt中无信息，重定向到cas的登录页面，网址中带上App的网址信息，便于跳回
            httpResponse.sendRedirect("http://localhost:8080/cas/login" + "?"
                    + "LOCAL_SERVICE" + "="
                    + httpRequest.getRequestURL());
        } else {  // 已有JWT，执行网页
            request.setAttribute("username", user_id);
            chain.doFilter(request, response);
        }
    }

    public void destroy() {

    }
}
