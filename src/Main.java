import java.io.*;
import java.util.*;

public class Main {
    static List<Set> allSets = new ArrayList<>();
    static ArrayList<String> fuzzyRules = new ArrayList<>();
    public static HashMap<String, Double> fuzzy = new HashMap<>();
    public static HashMap<String, Double> out = new HashMap<>();

    public static String deffuzification(HashMap<String, Double> fuzzifiedValues, ArrayList<Vars> allvars, String name) {
        double predicted;
        String value;
        double sumFuzzified = 0.0, multiply = 0.0;
        HashMap<String, Double> centroids = new HashMap<>();
        for (Vars var : allvars) {
            centroids.put(var.name, var.GetCentroid());
        }
        for (Map.Entry<String, Double> entry : fuzzifiedValues.entrySet()) {
            double fuzzified = entry.getValue();
            sumFuzzified += fuzzified;
            multiply += fuzzified * centroids.get(entry.getKey());
        }
        predicted = multiply / sumFuzzified;

        String max = getMaxVariable(out);

        return "The predicted " + name + " is " + max + " " + predicted;
    }

    public static String getMaxVariable(HashMap<String, Double> out) {
        double maximum = Collections.max(out.values());
        String max = "";
        for (Map.Entry<String, Double> entry : out.entrySet()) {
            if (entry.getValue() == maximum)
                max = entry.getKey();
        }
        return max;
    }

    public static void main(String[] args) throws IOException {

        File file = new File("example.txt");
        File outFile = new File("output.txt");
        Scanner scanner = new Scanner(file);
        BufferedWriter writer = new BufferedWriter(new FileWriter(outFile));
        String output = "";
        int outCounter = 0;

        while (scanner.hasNextLine()) {
            int numOfSets = scanner.nextInt();
            for (int k = 0; k < numOfSets; k++) {
                String name = scanner.next();
                String type = scanner.next();
                int st = scanner.nextInt();
                int end = scanner.nextInt();
                Set s = new Set();
                int numVars = scanner.nextInt();
                for (int i = 0; i < numVars; i++) {
                    int numIt;
                    String varName = scanner.next();
                    String varType = scanner.next();
                    List<Double> range = new ArrayList<>();
                    if (varType.equalsIgnoreCase("tri")) {
                        numIt = 3;
                    } else {
                        numIt = 4;
                    }
                    for (int j = 0; j < numIt; j++) {
                        double in = scanner.nextDouble();
                        range.add(in);
                    }
                    Vars v = new Vars(varName, varType, range);
                    s.addVar(v);
                    s.setSet(name, type, st, end);

                }

                if (s.type.equals("in")) {
                    double crisp = scanner.nextDouble();
                    fuzzy = s.Fuzzification(crisp, s.variables);
                }
                if (s.type.equals("out")) outCounter++;
                allSets.add(s);

            }
            for (int k = outCounter; k > 0; k--) {
                int rulesNum = scanner.nextInt();
                scanner.nextLine();
                for (int i = 0; i < rulesNum; i++) {
                    String str = scanner.nextLine();
                    fuzzyRules.add(str);

                }

                out = Rules.inferenceRule(fuzzyRules, allSets);
                out.entrySet().forEach(entry -> {
                    System.out.println(entry.getKey() + " => " + entry.getValue());
                });
                output = deffuzification(out, allSets.get(allSets.size() - k).variables, allSets.get(allSets.size() - k).name);
                System.out.println(output);
                writer.write(output);
            }
        }

        writer.close();


}    }




