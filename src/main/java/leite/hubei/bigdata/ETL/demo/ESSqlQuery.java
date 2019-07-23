package leite.hubei.bigdata.ETL.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ESSqlQuery {
	
	String address = "jdbc:es://http://192.168.12.147:9200";     
	static String  driver = "org.elasticsearch.xpack.sql.jdbc.jdbc.JdbcDriver";
	
	public static Properties connectionProperties(){
        Properties properties = new Properties();
        properties.put("user", "system");
		properties.put("password", "x-pack-test-password");
        return properties;
    }
	
	public void queryTest() throws Exception {
		
		long startTime = System.currentTimeMillis();
		
		Properties properties = connectionProperties();
		
		
		Connection connection = DriverManager.getConnection(address, properties);
		
		Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(
              "   SELECT * "
            + "     FROM airdata20190127v_samples "
            + " where SStation= 'SS4201002' and HTTime < 1486836000 and HTTime>1486800000 "
            + " ORDER BY HTTime DESC"
            + "    LIMIT 10");
        
        while(rs.next()) {
        	       	
        	System.out.println("|" + rs.getString("SID") + "|" + rs.getLong("HTTime") + "|" + rs.getString("SStation") + "|" + rs.getString("SDateTime") + "|");
        	
        }
        
        long endTime = System.currentTimeMillis();
        
        System.out.println("ES SQL Query Successful: (" + (endTime-startTime) + ") ms ");
        
	}
}
