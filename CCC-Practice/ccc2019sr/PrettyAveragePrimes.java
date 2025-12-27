import java.util.Scanner;

public class PrettyAveragePrimes {
	static Scanner scn = new Scanner(System.in);
	static int[] mN;
	
	public static void main(String[] args) {
		int trials = scn.nextInt();
		mN = new int[trials];
		
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0 ; i < trials ; i++) {
			mN[i] = scn.nextInt();
		}
		
		for (int i = 0 ; i < trials ; i++) {
			int cur = mN[i];
			if (isPrime(cur)) {
				sb.append(Integer.toString(cur) + " " + Integer.toString(cur));
				sb.append("\n");
				continue;
			}
			
			boolean found = false;
			int dist = 1;
			
			while (!found) {
				int low = cur - dist;
				int up = cur + dist;
				
				if (isPrime(low) && isPrime(up)) {
					sb.append(Integer.toString(low) + " " + Integer.toString(up));
					found = true;
				} else {
					dist++;
				}
			}
			sb.append("\n");
		}
		
		System.out.println(sb.toString());
		scn.close();
	}
	
	public static boolean isPrime(int number) {
		int divisor = 2;
		while (divisor < number) {
			if (number % divisor != 0) {
				divisor++;
				continue;
			} else {
				return false;
			}
		}
		return true;
	}
}
