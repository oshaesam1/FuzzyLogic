import java.util.*;

public class Main {
    static List<set> allSets = new ArrayList<>();
    static List<Double> values = new ArrayList();
   static HashMap<String,Double>finalRes=new HashMap();
    public static HashMap<String, Double> inferenceRule(List<set> allSets, String[] splitted) {
        //input1 high and input2 low => output low
        for (int i = 0; i < splitted.length; i++) {
            for (int j = 0; j < allSets.size(); j++) {
                if (allSets.get(j).name.equals(splitted[i])) {
                    for (int k = 0; k < allSets.get(j).variables.size(); k++) {
                        if (allSets.get(j).variables.get(k).name == splitted[i + 1]) {
                            values.add(allSets.get(j).fuzzy.get(splitted[i + 1]));
                        }
                    }
                }
            }
        }
      double result= operators(splitted);
        finalRes.put(splitted[splitted.length-1],result);
        return finalRes;
    }

    public static double operators(String[] splitted) {
        //input1 high and input2 low => output low
        double retVal = 0;
        int j = 0;
        while (j < values.size()) {
            for (int i = 0; i < splitted.length; i++) {
                if (splitted[i].equals("or")) {
                    retVal = Math.max(values.get(j), values.get(j + 1));
                    values.add(j+1,retVal);
                    j++;
                }
                if (splitted[i].equals("and")) {
                    retVal = Math.min(values.get(j), values.get(j + 1));
                    values.add(j+1,retVal);
                    j++;
                }
                if (splitted[i].equals("and_not")) {
                    retVal = 1-Math.min(values.get(j), values.get(j + 1));
                    values.add(j+1,retVal);
                    j++;
                }
                if (splitted[i].equals("or_not")) {
                    retVal = 1-Math.max(values.get(j), values.get(j + 1));
                    values.add(j+1,retVal);
                    j++;
                }
            }
        }
        return retVal;
    }
    //return

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter num of Sets : ");
        int numOfSets = sc.nextInt();
        for (int k = 0; k < numOfSets; k++) {
            System.out.println("Enter name , type , Range for this Set :");
            String name = sc.next();
            String type = sc.next();
            int st = sc.nextInt();
            int end = sc.nextInt();
            set s = new set(name, type, st, end);
            allSets.add(s);
            System.out.println("Enter num of Variables for this set");
            int numVars = sc.nextInt();
            System.out.println("Enter name , type (tri,trap) , range");
            for (int i = 0; i < numVars; i++) {
                int numIt;
                String varName = sc.next();
                String varType = sc.next();
                List<Double> range = new ArrayList<>();
                if (varType.equalsIgnoreCase("tri")) {
                    numIt = 3;
                } else {
                    numIt = 4;
                }
                for (int j = 0; j < numIt; j++) {
                    double in = sc.nextDouble();
                    range.add(in);
                }
                vars v = new vars(varName, varType, range);
                s.addVar(v);

                System.out.println(v.name + " " + v.type + " " + v.range);
            }
            allSets.add(s);
            if (s.type.equals("in")) {
                System.out.println("Enter Crisp value :");
                double crisp = sc.nextDouble();
                s.Fuzzification(crisp, s.variables);
                s.Fuzzification(crisp, s.variables).entrySet().forEach(entry -> {
                    System.out.println(entry.getKey() + " => " + entry.getValue());
                });
            }
        }
        System.out.println("Enter rules num :");
        int rulesNum = sc.nextInt();
        for (int i = 0; i < rulesNum; i++) {
            System.out.println("Enter the Rule :");
            String str = sc.nextLine();
            String[] splited = str.split("\\s+");
            inferenceRule(allSets, splited);
        }
        //System.out.println(finalRes.get("low"));


    }


}
