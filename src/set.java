import java.util.ArrayList;
import java.util.List;

public class set {
    String name;
    String type;
    int lower;
    int upper;
    List<var>variables=new ArrayList<>();

    set(String name, String type, int lower, int upper ) {
        this.name=name;
        this.type=type;
        this.lower=lower;
        this.upper=upper;

    }
    void addVar(var v){
        variables.add(v);
    }
}
