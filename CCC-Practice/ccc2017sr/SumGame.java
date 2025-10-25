import java.util.Scanner;
import java.util.ArrayList;

public class SumGame {
	static Scanner scn = new Scanner(System.in);
	
	public static void main(String[] args) {
		int days = scn.nextInt();
		
		ArrayList<Integer> swiftRuns = new ArrayList<Integer>();
		ArrayList<Integer> semaphoreRuns = new ArrayList<Integer>();
		
		for (int i = 0 ; i < days ; i++) {
			swiftRuns.add(scn.nextInt());
		}
		for (int i = 0 ; i < days ; i++) {
			semaphoreRuns.add(scn.nextInt());
		}
		scn.close();
		
		int largestDaySum = swiftRuns.size();
		
		for (int i = 0 ; i < swiftRuns.size() ; i++) {
			int swiftSum = 0;
			int semaphoreSum = 0;
			
			for (int j = 0 ; j < largestDaySum ; j++) {
				swiftSum += swiftRuns.get(j);
			}
			for (int j = 0 ; j < largestDaySum ; j++) {
				semaphoreSum += semaphoreRuns.get(j);
			}
			
			if (swiftSum == semaphoreSum) {
				System.out.println(largestDaySum);
				return;
			} else {
				largestDaySum--;
			}
		}
		
		System.out.println(0); //printing zero if there is no largest sum
	}
}
