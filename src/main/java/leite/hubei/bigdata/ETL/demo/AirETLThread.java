package leite.hubei.bigdata.ETL.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import leite.hubei.bigdata.ETL.demo.beans.V;
import leite.hubei.bigdata.ETL.demo.utils.SqlServerDBUtils;

public class AirETLThread extends Thread {

	private String name;
	private int startOffSet;
	private int endOffSet;
	
	 private final static String URL = "jdbc:sqlserver://192.168.12.229:1433;DatabaseName=HBAirData20190127";
	 private static final String USER="llz";
	 private static final String PASSWORD="llz@123";
	
	
	public AirETLThread(String name, int startOffSet, int endOffSet) {
        this.name = name;
        this.startOffSet = startOffSet;
        this.endOffSet = endOffSet;
    }
	
	public void run() {
		
		try {
			
			System.out.println("----------------Multi Thread Insert begin:<"+ name +">----------------");
	        
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			Connection conn= (Connection)DriverManager.getConnection(URL,USER,PASSWORD);
			
			Statement stmt =  SqlServerDBUtils.getConnection().createStatement();			
			int pageSize = 500; //分页查询每次查询500*20笔数据
	        
			
			for ( ;startOffSet < endOffSet; startOffSet+=pageSize) {
	        	etlInsert(stmt, pageSize, startOffSet);
	        }
			
			System.out.println("----------------Multi Thread Insert finished:<"+ name + ">----------------");
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
		
	}
	
	
	public void etlInsert(Statement stmt, int pageSize, int offSet) throws Exception {
    	
    	String sql = "select top "+ pageSize +" * from dbo.v_Samples where HTTime not in (select top "+ offSet +" HTTime from dbo.v_Samples)";
    	
    	ResultSet rs = stmt.executeQuery(sql);
    	
    	//ResultSet rs = stmt.executeQuery("select top 20 * from dbo.v_Samples where HTTime not in(select top 20 HTTime from dbo.v_Samples)");
        
        // 注意: 此处取值时指定的get方法一定要与视图或表定义的类型匹配
        /*   while(rs.next()){//如果对象中有数据，就会循环打印出来
               System.out.println(rs.getString("SID")+","+rs.getLong("HTTime")+","+rs.getString("SStation") +","+
               rs.getString("SDateTime")+","+rs.getFloat("SValue")+","+rs.getFloat("SSpan")+","+
               rs.getFloat("SZero")+","+rs.getString("SMID")+","+rs.getInt("SSamples")+","+
               rs.getInt("SMark")+","+rs.getString("DeviceCode")+","+rs.getString("SMarkType"));
           } */
    	
    	InsertData insertData = new InsertData();           
        // 封装成列表
        List<V> dataList = new ArrayList<V>();        
        
        int tab = 0;
        
        
        while(rs.next()) {
        	
        	Map<String, Object> indexFeatures = new HashMap<String, Object>();
        	indexFeatures.put("SID", rs.getString("SID"));
        	indexFeatures.put("HTTime", rs.getLong("HTTime"));
        	indexFeatures.put("SStation", rs.getString("SStation"));
        	indexFeatures.put("SDateTime", rs.getString("SDateTime")); //时间格式需要统一转化为 ES 规定的特殊格式
        	indexFeatures.put("SValue", rs.getFloat("SValue"));
        	indexFeatures.put("SSpan", rs.getFloat("SSpan"));
        	indexFeatures.put("SZero", rs.getFloat("SZero"));
        	indexFeatures.put("SMID", rs.getString("SMID"));
        	indexFeatures.put("SSamples", rs.getInt("SSamples"));
        	indexFeatures.put("SMark", rs.getInt("SMark"));
        	indexFeatures.put("DeviceCode", rs.getString("DeviceCode"));
        	indexFeatures.put("SMarkType", rs.getString("SMarkType"));
            
        	Map<String,Object> nonIndexFeatures = new HashMap<String, Object>();
        	nonIndexFeatures.put("xml", "<--------------------------------resource xml------------------------------------------->");
            
            insertData.insertSingle("airdata20190127", "v_samples", indexFeatures, nonIndexFeatures);                        
        }
               
    	System.out.println("Insert SQL Successful: Pagesize (" + pageSize + ") OffSet (" + offSet + ")");
    }
	
	
}
