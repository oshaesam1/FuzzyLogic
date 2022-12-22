import java.util.List;

public class Vars {
    String name;
    String type;
    List<Double> range;
    double centroid;

    Vars(String name, String type, List<Double> range) {
        this.name = name;
        this.type = type;
        this.range = range;
    }

    public void setRange(List<Double> range) {
        this.range = range;
    }


    public double GetCentroid() {
        int length = 0;
        centroid = 0;
        if (type.equals("tri"))
            length = 3;
        else length = 4;
        for (int i = 0; i < length; i++)
            centroid = centroid + range.get(i);
        centroid = centroid / length;
        return centroid;
    }
}
