package leite.hubei.bigdata.ETL.demo;


import leite.hubei.bigdata.ETL.demo.beans.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateTable {

	
	public void initTable(String dbName, String tableName, Map<String, String> indexFeatures) {
	// 创建连接
    MessageSendDemo msd = new MessageSendDemo();
    Meta m = new Meta();

    /*
     *  测试建表
     */
    // 请求体
    Create c = new Create();

    // 元数据信息填写：
    m.setTimestamp(Long.toString(System.currentTimeMillis()));
    // 必填：请求类型
    m.setType("create");
    // 选填，便于调试
    m.setPass("123");
    m.setUser("test");

    // 设置请求文档填写
    CreateDoc d = new CreateDoc();
    // 表名
    d.setTableName(tableName); //test05
    // 库名
    d.setTableNamespace(dbName); //test
    // 数据表类型定义，仅支持elasticsearch和json同时支持的基本类型    
    d.setIndexDoc(JSONObject.parseObject(JSON.toJSONString(indexFeatures)));

    // 封装
    c.setDoc(d);
    c.setMeta(m);
    // 发送
    msd.send("create", c);
    
	}
	
}
