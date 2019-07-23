package leite.hubei.bigdata.ETL.demo.beans;

public class Insert {
    private InsertDoc doc;
    private Meta meta;

    public void setDoc(InsertDoc doc) {
        this.doc = doc;
    }
    public InsertDoc getDoc(){
        return this.doc;
    }

    public void setMeta(Meta meta){
        this.meta = meta;
    }
    public Meta getMeta(){
        return this.meta;
    }
}
