import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Main {
    static List<set> allSets = new ArrayList<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter num of Sets : ");
        int numOfSets = sc.nextInt();
        for (int k = 0; k < numOfSets; k++) {
            System.out.println("Enter namr , type , Range for this Set :");
            String name = sc.next();
            String type = sc.next();
            int st = sc.nextInt();
            int end = sc.nextInt();
            set s = new set(name, type, st, end);

            FuzzyLogic f = new FuzzyLogic();
            f.addSet(s);
            System.out.println("Enter num of Variables for this set");
            int numofVar = sc.nextInt();
            System.out.println("Enter name , type (tri,trap) , range");
            for (int i = 0; i < numofVar; i++) {
                int numIt;
                String varName = sc.next();
                String varType = sc.next();
                List<Integer> range = new ArrayList<>();
                if (varType.equalsIgnoreCase("tri")) {
                    numIt = 3;
                } else {
                    numIt = 4;
                }
                for (int j = 0; j < numIt; j++) {
                    int in = sc.nextInt();
                    range.add(in);
                }
                var v = new var(varName, varType, range);
                s.addVar(v);
                if(varType.equalsIgnoreCase("in")){
                    System.out.println("Enter Crisp value :");
                    int crisp=sc.nextInt();
                }
                System.out.println(v.name + " " + v.type + " " + v.range);
            }

            allSets.add(s);
        }
//        System.out.println("Enter crisp values :");
//        for(int i=0;i<allSets.size()-1;i++){
//        int size=sc.nextInt();
//        }
//        for (int i=0;i<size;i++){
//
//        }
//        for (int i = 0; i < allSets.size(); i++) {
//            System.out.println(allSets.get(i).name);
//        }
    }

    HashMap<String, Double> Fuzzification(double e, ArrayList<set> array, ArrayList<Double> values) {
        HashMap<String, Double> r = new HashMap<>();
        Boolean w = false;
        double y1 = 0.0, y2 = 0.0, x1 = 0.0, x2 = 0.0, a1 = 0.0, b1 = 0.0, y1Total = 0.0;
        for (int i = 0; i < array.size(); i++) {
            //System.out.println("ss "+array.get(i).arr.get(array.get(i).arr.size() - 1));
            if (e <= array.get(i).arr.get(array.get(i).arr.size() - 1)
                    && e >= array.get(i).arr.get(0)) {

                for (int j = 0; j < array.get(i).arr.size(); j++) {
                    if (e <= array.get(i).arr.get(j)) {
                        x2 = array.get(i).arr.get(j);
                        int p = j - 1;
                        if (j == 0) {
                            p = 1;
                        }
                        x1 = array.get(i).arr.get(p);
                        y2 = values.get(j);
                        y1 = values.get(p);

                        a1 = (y2 - y1) / (x2 - x1);
                        b1 = y1 - (a1 * x1);

                        y1Total = (e * a1) + b1;
                        //System.out.println(array.get(i).x+" "+y1Total);
                        r.put(array.get(i).x, y1Total);
                        //r.add(new pairs(array.get(i).x,y1Total));
                        break;

                    }
                }

            } else if ((e > array.get(i).arr.get(array.get(i).arr.size() - 1) || e < array.get(i).arr.get(0))) {
                r.put(array.get(i).x, 0.0);
            }

        }
        return r;
    }
}
