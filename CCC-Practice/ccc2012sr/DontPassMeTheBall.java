import java.util.Scanner;

public class DontPassMeTheBall { //taken from GPT and modified
	
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int J = sc.nextInt();
        sc.close();
        
        if (J >= 1 && J <= 99) {
        	if (J < 4) {
                System.out.println(0);
                return;
            }

            long n = J - 1; // total players before scorer
            long combinations = (n * (n - 1) * (n - 2)) / 6;
            System.out.println(combinations);
        }
    }
}