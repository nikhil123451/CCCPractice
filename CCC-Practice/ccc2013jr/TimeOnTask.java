import java.util.Scanner;
import java.util.Arrays;

public class TimeOnTask {
	static Scanner scn = new Scanner(System.in);
	
	public static void main(String[] args) {
		int totalTime = scn.nextInt();
		int chores = scn.nextInt();
		
		if (totalTime >= 0 && totalTime <= 100000 && chores >= 0 && chores <= 100) {
			int[] choreTimes = new int[chores];
			
			for (int i = 0; i < chores ; i++) {
				int choreTime = scn.nextInt();
				choreTimes[i] = choreTime;
			}
			
			Arrays.sort(choreTimes);
			
			int timeElapsed = 0;
			int choreCount = 0;
			
			for (int choreTime : choreTimes) {
				timeElapsed += choreTime;
				if (timeElapsed > totalTime) {
					timeElapsed -= choreTime;
				} else if (timeElapsed == totalTime) {
					choreCount++;
					break;
				} else if (timeElapsed < totalTime) {
					choreCount++;
					continue;
				} else { //program broke
					return;
				}
			}
			
			System.out.println(choreCount);
		}
	}
}
