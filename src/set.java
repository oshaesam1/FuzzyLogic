import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class set {
    String name;
    String type;
    int lower;
    int upper;
    ArrayList<vars> variables = new ArrayList<>();

    set(String name, String type, int lower, int upper) {
        this.name = name;
        this.type = type;
        this.lower = lower;
        this.upper = upper;

    }

    void addVar(vars v) {
        variables.add(v);
    }

    HashMap<String, Double> Fuzzification(double crispValue, ArrayList<vars> values) {
        HashMap<String, Double> memebershipDegree = new HashMap<>();
        ArrayList<Double> TRAP = new ArrayList<>(Arrays.asList(0.0, 1.0, 1.0, 0.0));
        ArrayList<Double> TRI = new ArrayList<>(Arrays.asList(0.0, 1.0, 0.0));

        double x1 = 0.0, x2 = 0.0, y1 = 0.0, y2 = 0.0, slope = 0.0, intercept = 0.0, memebership = 0.0;
        for (int i = 0; i < values.size(); i++) {
            if (crispValue <= values.get(i).range.get(values.get(i).range.size() - 1)
                    && crispValue >= values.get(i).range.get(0)) {
                for (int j = 0; j < values.get(i).range.size(); j++) {
                    if (crispValue <= values.get(i).range.get(j)) {
                        x2 = values.get(i).range.get(j);
                        int p = j - 1;
                        if (j == 0) {
                            p = 1;
                        }
                        x1 = values.get(i).range.get(p);
                        if (variables.get(0).type.equals("tri")) {
                            y1 = TRI.get(p);
                            y2 = TRI.get(j);
                        } else if ((variables.get(0).type.equals("trap"))) {
                            y1 = TRAP.get(p);
                            y2 = TRAP.get(j);
                        }
                        slope = (y2 - y1) / (x2 - x1);
                        intercept = y1 - (slope * x1);
                        memebership = (crispValue * intercept) + intercept;
                        memebershipDegree.put(values.get(i).name, memebership);
                        break;

                    }
                }

            } else if ((crispValue > values.get(i).range.get(values.get(i).range.size() - 1) || crispValue < values.get(i).range.get(0))) {
                memebershipDegree.put(values.get(i).name, 0.0);
            }

        }
        return memebershipDegree;
    }
}
