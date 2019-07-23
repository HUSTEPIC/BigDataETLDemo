package leite.hubei.bigdata.ETL.demo.beans;


import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * Auto-generated: 2019-06-29 20:41:12
 */
public class V {

    private String tablePrimary;
    private JSONObject indexDoc;
    private JSONObject tableDoc;
    public void setTablePrimary(String tablePrimary) {
        this.tablePrimary = tablePrimary;
    }
    @JSONField(name = "table_primary")
    public String getTablePrimary() {
        return tablePrimary;
    }

    public void setIndexDoc(JSONObject indexDoc) {
        this.indexDoc = indexDoc;
    }
    @JSONField(name = "index_doc")
    public JSONObject getIndexDoc() {
        return indexDoc;
    }

    public void setTableDoc(JSONObject tableDoc) {
        this.tableDoc = tableDoc;
    }
    @JSONField(name = "table_doc")
    public JSONObject getTableDoc() {
        return tableDoc;
    }

}