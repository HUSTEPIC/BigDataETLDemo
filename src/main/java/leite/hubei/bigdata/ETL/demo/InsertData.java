package leite.hubei.bigdata.ETL.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import leite.hubei.bigdata.ETL.demo.beans.Insert;
import leite.hubei.bigdata.ETL.demo.beans.InsertDoc;
import leite.hubei.bigdata.ETL.demo.beans.Meta;
import leite.hubei.bigdata.ETL.demo.beans.V;

public class InsertData {

	public void insertSingle(String dbName, String tableName, Map<String, Object> indexFeatures, Map<String, Object> nonIndexFeatures) {
		
		MessageSendDemo msd = new MessageSendDemo();
        Meta m = new Meta();
    	
        Insert i = new Insert();

        // 元数据信息填写：
        m.setTimestamp(Long.toString(System.currentTimeMillis()));
        // 必填：请求类型
        m.setType("insert");

        //
        InsertDoc id = new InsertDoc();
        id.setTableName(tableName);
        id.setTableNamespace(dbName);

        // 封装插入数据
        V v = new V();
        
        v.setIndexDoc(JSONObject.parseObject(JSON.toJSONString(indexFeatures)));
        v.setTableDoc(JSONObject.parseObject(JSON.toJSONString(nonIndexFeatures)));
        
        // 封装成列表
        List<V> datalist = new ArrayList<V>();
        datalist.add(v);
        id.setV(datalist);

        // 封装
        i.setDoc(id);
        i.setMeta(m);
        // 发送
        msd.send("insert", i);		
	}
	
	
	public void insertBatch(String dbName, String tableName, List<V> datalist) {
		
		MessageSendDemo msd = new MessageSendDemo();
        Meta m = new Meta();
    	
        Insert i = new Insert();

        // 元数据信息填写：
        m.setTimestamp(Long.toString(System.currentTimeMillis()));
        // 必填：请求类型
        m.setType("insert");

        //
        InsertDoc id = new InsertDoc();
        id.setTableName(tableName);
        id.setTableNamespace(dbName);       
        id.setV(datalist);

        // 封装
        i.setDoc(id);
        i.setMeta(m);
        // 发送
        msd.send("insert", i);
	}
	
	
}
