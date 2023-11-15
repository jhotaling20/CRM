package program.software;

import java.util.*;

public class Fields {
    public List<String> fields = new LinkedList<>();
    public String field;
    public Fields(String field){
        this.field = field;
    }
    public void addField(String field){
        fields.add(field);
    }
    public List<String> getFields(){
        return fields;
    }

}
