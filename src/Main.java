import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static List<set> allSets = new ArrayList<>();
    static ArrayList<String> fuzzyRules = new ArrayList<>();
    public static HashMap<String, Double> fuzzy = new HashMap<>();
    public static HashMap<String, Double> out = new HashMap<>();

    public static double deffuzification(HashMap<String, Double> fuzzifiedValues, ArrayList<vars> allvars) {
        double predicted;
        //ArrayList <Double> centroids = new ArrayList<>();
        HashMap<String, Double> centroids = new HashMap<>();
        for (vars var : allvars) {
            centroids.put(var.name, var.GetCentroid());
        }

        double sumFuzzifiedValues = 0.0, numer = 0.0;

        for (Map.Entry<String, Double> entry : fuzzifiedValues.entrySet()) {
            double fuzzifiedValue = entry.getValue();
            sumFuzzifiedValues += fuzzifiedValue;
            numer += fuzzifiedValue * centroids.get(entry.getKey());
        }

        predicted = numer / sumFuzzifiedValues;

        //System.out.println(predicted);
        return predicted;
    }


    public static String getMaxi(HashMap<String, Double> out) {
        double maximum = Collections.max(out.values());
        String max = "";
        for (Map.Entry<String, Double> entry : out.entrySet()) {
            if (entry.getValue() == maximum)
                max = entry.getKey();
        }
        return max;
    }

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter num of Sets : ");
        int numOfSets = sc.nextInt();
        for (int k = 0; k < numOfSets; k++) {
            System.out.println("Enter name , type , Range for this Set :");
            System.out.println("Enter num of Variables for this set");
            System.out.println("Enter name , type (tri,trap) , range");

            String name = sc.next();
            String type = sc.next();
            int st = sc.nextInt();
            int end = sc.nextInt();
            set s = new set(name, type, st, end);
            allSets.add(s);
            int numVars = sc.nextInt();
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
                fuzzy = s.Fuzzification(crisp, s.variables);
                fuzzy.entrySet().forEach(entry -> {
                    System.out.println(entry.getKey() + " => " + entry.getValue());
                });
            }
        }
        System.out.println("Enter rules num :");
        int rulesNum = sc.nextInt();
        BufferedReader bfn = new BufferedReader(
                new InputStreamReader(System.in));

        // String reading internally
        System.out.println("Enter the Rule :");
        for (int i = 0; i < rulesNum; i++) {
            String str = bfn.readLine();
            fuzzyRules.add(str);
        }

        out = Rules.inferenceRule(fuzzyRules, allSets);
        String max = getMaxi(out);
        double prediction = deffuzification(out, allSets.get(allSets.size() - 1).variables);
        System.out.println("The predicted risk  is " + max + " " + prediction);
    }

}



