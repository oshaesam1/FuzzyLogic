import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Rules {
    static List<Double> values = new ArrayList();

    public static HashMap<String, Double> finalRes = new HashMap<>();

    public static HashMap<String, Double> inferenceRule(ArrayList<String> Rules, List<set> allSets) {

        for (int i = 0; i < Rules.size(); i++) {
            String operator;
            double res = 0.0;
            int l = 0;
            double var1 = 0.0;
            double var2 = 0.0;
            String[] splitted = Rules.get(i).split(" ");
            String curr = splitted[0];

            for (int j = 0; j < allSets.size(); j++) {
                if (allSets.get(j).name.equals(curr)) {
                    curr = splitted[l++];
                    for (int k = 0; k < allSets.get(j).variables.size(); k++) {
                        if (allSets.get(j).variables.get(k).name.equals(curr)) {
                            //values.add(allSets.get(j).fuzzy.get(curr));
                            //System.out.println("curr "+(curr));
                            var1 = allSets.get(j).fuzzy.get(curr);
                            //System.out.println("curr value "+var1);
                            curr = splitted[l++];
                            break;
                        }
                    }

                }
            }
            while (!curr.equals("=>")) {
                operator = curr;
             //   System.out.println("operator " + (curr));

                curr = splitted[l++];
                for (int j = 0; j < allSets.size(); j++) {
                    if (allSets.get(j).name.equals(curr)) {
                        curr = splitted[l++];
                        for (int k = 0; k < allSets.get(j).variables.size(); k++) {
                            if (allSets.get(j).variables.get(k).name.equals(curr)) {
                                values.add(allSets.get(j).fuzzy.get(curr));
                                // System.out.println("curr "+(curr));
                                var2 = allSets.get(j).fuzzy.get(curr);
                                // System.out.println("curr value "+var2);
                                curr = splitted[l++];
                                break;
                            }
                        }
                        break;
                    }
                }

                res = operators(operator, var1, var2);


            }

            String OutName = splitted[l++];
            //System.out.println("out " + OutName);
            for (int j = 0; j < allSets.size(); j++) {
                if (allSets.get(j).name.equals(OutName)) {
                    String outvalue = splitted[l];
                    for (int k = 0; k < allSets.get(j).variables.size(); k++) {
                        if (allSets.get(j).variables.get(k).name.equals(outvalue)) {
                          //  System.out.println("value " + outvalue);
                            if (getDegree(outvalue, finalRes) == -1.0) {
                                finalRes.put(outvalue, res);
                                //System.out.println("normal1 " + getDegree(outvalue, finalRes));
                            } else {
                                //System.out.println("normal2 " + res);
                                finalRes.replace(outvalue, Math.max(getDegree(outvalue, finalRes), res));
                                //System.out.println("normal3 " + Math.max(getDegree(outvalue, finalRes), res));
                               // System.out.println("normal4 " + getDegree(outvalue, finalRes));
                            }

                        }
                    }
                }
            }
        }
        return finalRes;
    }

    public static double getDegree(String name, HashMap<String, Double> map) {
        if (map.get(name) != null) return map.get(name);
        else return -1.0;
    }

    public static double operators(String op, double val1, double val2) {
        //input1 high and input2 low => output low
        double retVal = 0;
        if (op.equals("or")) {
            retVal = Math.max(val1, val2);

        }
        if (op.equals("and")) {
            retVal = Math.min(val1, val2);
        }
        if (op.equals("and_not")) {
            retVal =  Math.min(val1, 1-val2);

        }
        if (op.equals("or_not")) {
            retVal = Math.max(val1,1- val2);

        }
        return retVal;
    }
}
