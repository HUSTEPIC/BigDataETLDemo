package leite.hubei.bigdata.ETL.demo;


import leite.hubei.bigdata.ETL.demo.beans.*;
import leite.hubei.bigdata.ETL.demo.utils.SqlServerDBUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Thread.sleep;

public class Main {
    public static void main(String args[]) throws Exception{
//        // 建表测试
//        CreateTable airTable = new CreateTable();
//        
//        /* 请先在业务数据库中查询好对应的表字段类型, 以便于指定相应类型
//         	例如: 在sqlserver 数据库中执行以下sql 查询视图  v_Samples的定义
//        	Select o.Name As ObjectsName , c.name As ColumnsName , t.name As ColumnsType , c.length As ColumnsLength From SysObjects As o , SysColumns As c , SysTypes As t Where o.type in  ('v') And o.id = c.id And c.xtype = t.xtype And o.Name = 'v_Samples'
//        
//        	+---------------+---------------+---------------+-----------------+
//        	| ObjectsName   | ColumnsName   | ColumnsType   | ColumnsLength   |
//        	|---------------+---------------+---------------+-----------------|
//        	| v_Samples     | SSamples      | int           | 4               |
//        	| v_Samples     | SMark         | int           | 4               |
//        	| v_Samples     | SValue        | float         | 8               |
//        	| v_Samples     | SSpan         | float         | 8               |
//        	| v_Samples     | SZero         | float         | 8               |
//        	| v_Samples     | HTTime        | bigint        | 8               |
//        	| v_Samples     | SID           | varchar       | 50              |
//        	| v_Samples     | SStation      | varchar       | 50              |
//        	| v_Samples     | SDateTime     | varchar       | 50              |
//        	| v_Samples     | SMID          | varchar       | 50              |
//        	| v_Samples     | DeviceCode    | varchar       | 50              |
//        	| v_Samples     | SMarkType     | varchar       | 100             |
//        	+---------------+---------------+---------------+-----------------+
//        */
//        
//        // 字段类型包括 keyword(对应于String)，integer，long, short, byte, double，date (date字段为ES的特殊格式)
//        Map<String, String> tableFeatures = new HashMap<String, String>();
//        tableFeatures.put("SSamples", "integer");
//        tableFeatures.put("SMark","integer");
//        tableFeatures.put("SValue", "float");
//        tableFeatures.put("SSpan", "float");
//        tableFeatures.put("SZero", "float");
//        tableFeatures.put("HTTime", "long");
//        tableFeatures.put("SID", "keyword");
//        tableFeatures.put("SStation","keyword");
//        tableFeatures.put("SDateTime", "date");
//        tableFeatures.put("SMID", "keyword");
//        tableFeatures.put("DeviceCode", "keyword");
//        tableFeatures.put("SMarkType", "keyword");
//        
//        airTable.initTable("airdata20190127", "v_samples", tableFeatures);
    	
    	
    	
    	if( "v_samples_insert".equals(args[0] )) {
    	
    		/*
    		 *  插入测试, 从业务系统视图 v_Samples 中 分页查询数据导入到大数据平台中
    		 */
    		System.out.println("----------------Insert (v_Samples_insert) begin----------------");
    		Statement stmt =  SqlServerDBUtils.getConnection().createStatement();
        
    		for(int i=0; i<20; i++) {
    		
    			int batchSize =1000; //每1000条数据发送一次消息
    			int pageSize = batchSize*100; //分页查询每次查询500*2笔数据
    			int offSet =0;
        
    			for (; offSet <1000000; offSet+=pageSize) {
    				etlInsertV_Samples(stmt, batchSize, pageSize, offSet);
    			}
        
    			System.out.println("----------------Insert (v_Samples_insert) finished: round" + i + "----------------");
    		
    		}
        
    	}
        
        
    	if("t_sstation_insert".equals(args[0])) {
    		
    		/*
    		 *  插入测试, 从业务系统视图 v_Samples 中 分页查询数据导入到大数据平台中
    		 */
    		System.out.println("----------------Insert begin----------------");
    		Statement stmt =  SqlServerDBUtils.getConnection().createStatement();
    		
    		String sql = "select * from dbo.t_SStation"; // 小表数据一次查询后插入        	
        	ResultSet rs = stmt.executeQuery(sql);                   	
        	InsertData insertData = new InsertData();           
            // 封装成列表
            List<V> dataList = new ArrayList<V>(); 
            int tab =0;
                        
            while(rs.next()) {
            	
            	Map<String, Object> indexFeatures = new HashMap<String, Object>();
            	indexFeatures.put("Id", rs.getInt("Id"));
            	indexFeatures.put("Sort", rs.getInt("Sort"));
            	indexFeatures.put("Status", rs.getInt("Status"));
            	indexFeatures.put("IsUpdated", rs.getShort("IsUpdated")); 
            	indexFeatures.put("IsRealSStation", rs.getShort("IsRealSStation"));
            	indexFeatures.put("IsShow", rs.getShort("IsShow"));
            	indexFeatures.put("IsPJD", rs.getShort("IsPJD"));
            	indexFeatures.put("IsPublish", rs.getShort("IsPublish"));
            	indexFeatures.put("SLatitude", rs.getBigDecimal("SLatitude"));
            	indexFeatures.put("SLongitude", rs.getBigDecimal("SLongitude"));
            	indexFeatures.put("GLatitude", rs.getBigDecimal("GLatitude"));
            	indexFeatures.put("GLongitude", rs.getBigDecimal("GLongitude"));
            	indexFeatures.put("SStation", rs.getString("SStation"));
            	indexFeatures.put("Area", rs.getString("Area"));
            	indexFeatures.put("SStationName", rs.getString("SStationName"));
            	indexFeatures.put("SStationType", rs.getString("SStationType")); //时间格式需要统一转化为 ES 规定的特殊格式
            	indexFeatures.put("SStationCode", rs.getString("SStationCode"));
            	indexFeatures.put("SSIPAddess", rs.getString("SSIPAddess"));
            	indexFeatures.put("DataExchangeMode", rs.getString("DataExchangeMode"));
            	indexFeatures.put("UniqueCode", rs.getString("UniqueCode"));
            	indexFeatures.put("City", rs.getString("City"));
            	indexFeatures.put("SSSqlServerName", rs.getString("SSSqlServerName"));
            	indexFeatures.put("SSLoginName", rs.getString("SSLoginName"));
            	indexFeatures.put("SSLoginPwd", rs.getString("SSLoginPwd"));
            	indexFeatures.put("AreaCode", rs.getString("AreaCode"));
            	indexFeatures.put("ParentSStation", rs.getString("ParentSStation"));
            	indexFeatures.put("RMURL", rs.getString("RMURL"));
            	indexFeatures.put("ZDCode", rs.getString("ZDCode"));
            	
            	Map<String,Object> nonIndexFeatures = new HashMap<String, Object>();
            	                
            	V v = new V();             
                v.setIndexDoc(JSONObject.parseObject(JSON.toJSONString(indexFeatures)));
                v.setTableDoc(JSONObject.parseObject(JSON.toJSONString(nonIndexFeatures)));              
                dataList.add(v);
                
                tab ++; 
                if(tab % 10 == 0)  {
               	 insertData.insertBatch("airdata20190127", "t_sstation", dataList);
               	 tab =0;
               	 dataList = new ArrayList<V>();
                }
            }
            
            if (dataList.size() > 0) {
            	insertData.insertBatch("airdata20190127", "t_sstation", dataList);
            }
            
        	System.out.println("Insert SQL finished: t_sstation");

    	}
    	
    	
    	
//    	Thread airETL1 = new AirETLThread("airETL1", 0, 10000000); 			airETL1.start();
//    	Thread airETL2 = new AirETLThread("airETL2", 10000000, 20000000);	airETL2.start();
//    	Thread airETL3 = new AirETLThread("airETL3", 20000000, 30000000);	airETL3.start();
//    	Thread airETL4 = new AirETLThread("airETL4", 30000000, 40000000);	airETL4.start();
    	
        
//        /*
//         *  查询测试
//         */
//        MessageSendDemo msd = new MessageSendDemo();
//        Meta m = new Meta();
//        
//        SingleExactQuery seq = new SingleExactQuery();
//        // 元数据信息填写：
//        m.setTimestamp(Long.toString(System.currentTimeMillis()));
//        // 必填：请求类型 单表准确查询
//        m.setType("exact_query");
//
//        // 查询条件
//        SingleExactQueryDoc seqd = new SingleExactQueryDoc();
//        seqd.setTableName("test05");
//        seqd.setTableNamespace("test");
//        Map<String,Object> query = new HashMap<String, Object>();
//        query.put("field2", 1234566556);
////        query.put("field3", 1.1);
//        seqd.setIndexDoc(JSONObject.parseObject(JSON.toJSONString(query)));
//
//        seq.setDoc(seqd);
//        seq.setMeta(m);
//        System.out.println(
//                JsonPost.post("http://192.168.12.140:8011/single/exact_query", JSON.toJSONString(seq)));
//
    }
    
    
    
    public static void etlInsertV_Samples(Statement stmt, int batchSize, int pageSize, int offSet) throws Exception {
    	
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
        
        int tab =0;
        
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
            
        	 V v = new V();             
             v.setIndexDoc(JSONObject.parseObject(JSON.toJSONString(indexFeatures)));
             v.setTableDoc(JSONObject.parseObject(JSON.toJSONString(nonIndexFeatures)));              
             dataList.add(v);
             
             tab ++; 
             if(tab % batchSize == 0)  {
            	 insertData.insertBatch("airdata20190127", "v_samples", dataList);
            	 tab =0;
            	 dataList = new ArrayList<V>();
             }
        }
        
        if (dataList.size() > 0) {
        	insertData.insertBatch("airdata20190127", "v_samples", dataList);
        }
               
    	System.out.println("Insert SQL Successful: Pagesize (" + pageSize + ") OffSet (" + offSet + ")");
    }
       
    
    
}
