package global;

public class Recoomendataion {
    String type;
    String detail;
    public Recoomendataion(String h, String detail){
        type = h;
        this.detail = detail;
    }
    public String getTitle(){
        return type;
    }
    public String getDetail(){
        return detail;
    }

}
