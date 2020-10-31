package servlet;

import JwtTools.JwtUtils;
import Dao.userDao;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * cas的login页面servlet
 */
@WebServlet(value = "/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String LOCAL_SERVICE=req.getParameter("LOCAL_SERVICE");
        String user_id = null;
        String JWT = null;
        Cookie[] cookies = ((HttpServletRequest) req).getCookies();
        if(cookies != null && cookies.length>0) {
            for (Cookie cookie : cookies) {
                if("JWT".equals(cookie.getName()))
                    JWT = cookie.getValue();
                    user_id = JwtUtils.getPayload(cookie.getValue());
            }
        }
        if(user_id != null ){  // user_id不为空，则说明jwt验证成功
            if(LOCAL_SERVICE != null){  // 不为空，则有来源地址LOCAL_SERVICE
                // 返回源地址，加上参数JWT
                resp.sendRedirect(LOCAL_SERVICE+"?jwt_cookie="+JWT);
            }
            else{
                return;
            }
        } else { // user_id 为空 无jwt或验证失败
            req.getRequestDispatcher("/WEB-INF/login.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String LOCAL_SERVICE=req.getParameter("LOCAL_SERVICE");
        String username = req.getParameter("id");
        String password = req.getParameter("pwd");
        boolean result = false;
        // 验证数据库
        try {
            result = userDao.checkLogin(username, password);
        }catch(Exception e) {
            e.printStackTrace();
        }
        if(result == true){  // 存在用户，生成jwt
            String JWT = JwtUtils.createToken(username);
            Cookie jwt_cookie = new Cookie("JWT", JWT);
            jwt_cookie.setMaxAge(60*5);
            jwt_cookie.setPath("/");
            resp.addCookie(jwt_cookie);
            if (LOCAL_SERVICE != null && !LOCAL_SERVICE.equals("")) // 有来源地址
            {
                System.out.print("登录成功 CAS LOCAL SERVICE来源:");
                System.out.println(LOCAL_SERVICE);
                // 返回源地址，加上参数JWT
                resp.sendRedirect(LOCAL_SERVICE+"?jwt_cookie="+JWT);
            } else  // 无来源地址，返回index界面
                System.out.println("登陆成功 无来源");
                resp.sendRedirect(req.getContextPath()+"/index.jsp");
        }
        else{  // 登录失败
            return;
        }

    }
}
