import java.util.ArrayList;
import java.util.Random;

public class Main {
    
    static int iters = 1000;
    static char[] V = new char[]{'B', 'L', 'Z', 'C'};
    static Random r = new Random();
    static ArrayList<Integer> results = new ArrayList<>(iters);
    
    public static void main(String[] args) {
        for (int i=0; i<iters; i++) {
            test();
        }
        System.out.println("Mean: " + results.stream().sorted(Integer::compareTo).mapToInt(i -> i).sum() / iters);
        System.out.println("Median: " + results.get(iters/2));
    }
    
    public static void test() {
        int coins = 10;
        int plays = 0;
        for (; coins > 0; plays++) {
            char[] spin = spin();
            if (allEquals(spin)) {
                if (spin[0] == 'B') coins += 20;
                else if (spin[0] == 'Z') coins += 15;
                else if (spin[0] == 'L') coins += 5;
                else coins += 3;
            } else if (spin[0] == 'C'){
                if (spin[1] == 'C') coins += 2;
                else coins++;
            }
            coins--;
        }
        results.add(plays);;
    }
    
    public static boolean allEquals(char[] spin) {
        for (int i=0; i<spin.length-1; i++) {
            if (spin[i] != spin[i+1]) return false;
        }
        return true;
    }
    
    public static char[] spin() {
        char[] result = new char[3];
        for (int i=0; i<3; i++) result[i] = V[r.nextInt(4)];
        return result;
    }
}
