package leite.hubei.bigdata.ETL.demo.beans;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * Auto-generated: 2019-06-29 20:21:39
 */
public class InsertDoc {

    private String tableName;
    private String indexName;
    private String tableNamespace;
    private List<V> v;

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
    @JSONField(name = "table_name")
    public String getTableName() {
        return tableName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }
    @JSONField(name = "index_name")
    public String getIndexName() {
        return indexName;
    }

    public void setTableNamespace(String tableNamespace) {
        this.tableNamespace = tableNamespace;
    }
    @JSONField(name = "table_namespace")
    public String getTableNamespace() {
        return tableNamespace;
    }

    public void setV(List<V> v) {
        this.v = v;
    }
    public List<V> getV() {
        return v;
    }

}
