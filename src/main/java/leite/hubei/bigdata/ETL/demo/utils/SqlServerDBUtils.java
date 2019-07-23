package leite.hubei.bigdata.ETL.demo.utils;

import java.sql.*;

public class SqlServerDBUtils {

    //这里可以设置数据库名称
    private final static String URL = "jdbc:sqlserver://192.168.12.229:1433;DatabaseName=HBAirData20190127";
    private static final String USER="llz";
    private static final String PASSWORD="llz@123";
    
    private static Connection conn=null;
    //静态代码块（将加载驱动、连接数据库放入静态块中）
    static{
        try {
            //1.加载驱动程序
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            //2.获得数据库的连接
            conn=(Connection)DriverManager.getConnection(URL,USER,PASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    //对外提供一个方法来获取数据库连接
    public static Connection getConnection(){
        return conn;
    }
    
    
    //测试用例
    public static void main(String[] args) throws Exception{
        
        //3.通过数据库的连接操作数据库，实现增删改查
        Statement stmt = conn.createStatement();
        //ResultSet executeQuery(String sqlString)：执行查询数据库的SQL语句   ，返回一个结果集（ResultSet）对象。
        ResultSet rs = stmt.executeQuery("select top 20 * from dbo.v_Samples where HTTime not in(select top 20 HTTime from dbo.v_Samples)");
        
        // 注意: 此处取值时指定的get方法一定要与视图或表定义的类型匹配
        while(rs.next()){//如果对象中有数据，就会循环打印出来
            System.out.println(rs.getString("SID")+","+rs.getLong("HTTime")+","+rs.getString("SStation") +","+
            rs.getString("SDateTime")+","+rs.getFloat("SValue")+","+rs.getFloat("SSpan")+","+
            rs.getFloat("SZero")+","+rs.getString("SMID")+","+rs.getInt("SSamples")+","+
            rs.getInt("SMark")+","+rs.getString("DeviceCode")+","+rs.getString("SMarkType"));
        }
    }

}
