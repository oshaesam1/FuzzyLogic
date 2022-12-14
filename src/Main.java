import java.util.*;

public class Main {
    static List<set> allSets = new ArrayList<>();

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
                if (varType.equalsIgnoreCase("in")) {
                    System.out.println("Enter Crisp value :");
                    double crisp = sc.nextDouble();
                    s.Fuzzification(crisp, s.variables);

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


}
