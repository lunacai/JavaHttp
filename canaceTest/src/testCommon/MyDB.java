package testCommon;


import java.sql.*;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MyDB {
    public static Connection con;
    public static String driver ="com.mysql.cj.jdbc.Driver";
    public static String url = "jdbc:mysql://xxx:xxx/jellyfish_user?serverTimezone=UTC";// URL指向要访问的数据库
    public static String user = "jfread";// MySQL配置时的用户名
    public static String password = "jfread";// MySQL配置时的密码

    public static Connection getCon(){
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url,user,password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }

    public static String excelSQL(Connection con,String sql) {
        String result=null;
        try {
            Statement smt = con.createStatement();
            ResultSet rset = smt.executeQuery(sql);
            Map<String,String> maps = new HashMap<String,String>();
            ResultSetMetaData meta = rset.getMetaData();
            //表列数量
            int columeCount = meta.getColumnCount();
            List<String> list = new ArrayList<String>();
            for (int i=1;i<=columeCount;i++)
            {
                list.add(meta.getColumnName(i));
            }
            while (rset.next()){
                for(String keys:list){
                    System.out.print(rset.getString(keys)+",");
                }
                System.out.println();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static void main(String[] args){
        String sql="select * from jellyfish_user.chushou_user_profile limit 10";
        Connection con = getCon();
        excelSQL(con,sql);
    }
}
