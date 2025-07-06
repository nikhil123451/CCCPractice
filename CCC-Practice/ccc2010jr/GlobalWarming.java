import java.util.*;

public class GlobalWarming {
	static Scanner scn = new Scanner(System.in);
	
	public static void main(String args[]) {
		
		while (true) {
			System.out.print("");
			int numberOfTemperatures = scn.nextInt();
			
			if (numberOfTemperatures >= 1 && numberOfTemperatures <= 20) {
				int[] temperatures = new int[numberOfTemperatures];
				ArrayList<Integer> differences = new ArrayList<Integer>();
				
				for (int i = 0 ; i < temperatures.length ; i++) {
					temperatures[i] = scn.nextInt();
				}
				
				for (int i = 0 ; i < temperatures.length - 1 ; i++) {
					int difference = temperatures[i+1] - temperatures[i];
					if (!(differences.contains(difference))) {
						differences.add(difference);
					} else {
						break;
					}
				}
				System.out.println(differences.size());
				
			} else {
				if (numberOfTemperatures == 0) {
					break;
				}
			}
		}
	}
}
