package leite.hubei.bigdata.ETL.demo.beans;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;

public class SingleExactQueryDoc {
    private String tableName;

    private String tableNamespace;
    private String scrollId;
    private String scrollSize;

    private JSONObject indexDoc;
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
    @JSONField(name = "table_name")
    public String getTableName() {
        return tableName;
    }

    public void setTableNamespace(String tableNamespace) {
        this.tableNamespace = tableNamespace;
    }
    @JSONField(name = "table_namespace")
    public String getTableNamespace() {
        return tableNamespace;
    }

    public void setIndexDoc(JSONObject indexDoc) {
        this.indexDoc = indexDoc;
    }
    @JSONField(name = "index_doc")
    public JSONObject getIndexDoc() {
        return indexDoc;
    }

}
