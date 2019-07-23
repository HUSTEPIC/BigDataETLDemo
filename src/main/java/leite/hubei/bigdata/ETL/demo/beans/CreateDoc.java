package leite.hubei.bigdata.ETL.demo.beans;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;

public class CreateDoc {
    private String tableName;
    private int indexNshards = 5;
    private int IndexNreplicas = 1;
    private String tableNamespace;
    private String tableCompression = "snappy";
    private JSONObject indexDoc;

    @JSONField(name = "table_name")
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
    @JSONField(name = "table_name")
    public String getTableName() {
        return tableName;
    }

    public void setIndexNshards(int indexNshards) {
        this.indexNshards = indexNshards;
    }
    @JSONField(name = "index_nshards")
    public int getIndexNshards() {
        return indexNshards;
    }

    @JSONField(name = "index_nreplicas")
    public void setIndexNreplicas(int indexNreplicas) {
        this.IndexNreplicas = indexNreplicas;
    }
    @JSONField(name = "index_nreplicas")
    public int getIndexNreplicas() {
        return IndexNreplicas;
    }

    public void setTableNamespace(String tableNamespace) {
        this.tableNamespace = tableNamespace;
    }
    @JSONField(name = "table_namespace")
    public String getTableNamespace() {
        return tableNamespace;
    }

    public void setTableCompression(String tableCompression) {
        this.tableCompression = tableCompression;
    }
    @JSONField(name = "table_compression")
    public String getTableCompression() {
        return tableCompression;
    }

    public void setIndexDoc(JSONObject indexDoc) {
        this.indexDoc = indexDoc;
    }
    @JSONField(name = "index_doc")
    public JSONObject getIndexDoc() {
        return indexDoc;
    }
}


