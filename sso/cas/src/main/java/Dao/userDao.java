package Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class userDao {
    private static final String driverClass = "com.mysql.cj.jdbc.Driver";
    private static final String jdbcURL = "jdbc:mysql://localhost:3306/userlogin?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone = GMT&allowPublicKeyRetrieval=true";   // 数据库名字叫 userlogin，  由于在本地上，所以为loacalhost，否则为服务器的ip地址
    private static final String user = "java";
    private static final String pwd = "123456";

    /*
        获取数据库连接
     */
    public static Connection getConnection() throws Exception {
        Class.forName(driverClass);  // 官方给的驱动加载
        Connection conn = DriverManager.getConnection(jdbcURL, user, pwd);
        return conn;
    }

    /*
     * check login
     * @name 用户名
     * @password 用户密码
     * @retrun true 存在用户 false 不存在用户
     */
    public Boolean checkLogin(String name, String password) throws Exception {
        Connection conn = getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement("select * from user where ID=?");
            ps.setString(1, name);
            ps.execute();
            ResultSet rs = ps.getResultSet();   // 用ResultSet类型来接收
            String pass_mysql = null;
            if (rs.next())  // 用next函数来一行一行读， 由于这里只有一行，所以用if
            {
                pass_mysql = rs.getString("password");
            }
            if (pass_mysql.equals(password))
                return true;
            else
                return false;
        } catch (Exception e) {
            throw e;
        } finally {
            conn.close();
        }
    }
}
