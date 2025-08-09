import java.util.Scanner;

public class NextInLine {
	static Scanner scn = new Scanner(System.in);
	
	public static void main(String[] args) {
		int youngest = scn.nextInt();
		int middle = scn.nextInt();
		
		int ageDifference = middle - youngest;
		
		int eldest = middle + ageDifference;
		
		System.out.println(eldest);
	}
}
