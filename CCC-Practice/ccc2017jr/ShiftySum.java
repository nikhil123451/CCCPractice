import java.util.Scanner;

public class ShiftySum {
	static Scanner scn = new Scanner(System.in);
	
	public static void main(String[] args) {
		int startingNumber = scn.nextInt();
		int shiftAmount = scn.nextInt();
		
		int sum = startingNumber;
		
		for (int i = 1 ; i <= shiftAmount ; i++) {
			sum += startingNumber * Math.pow(10, i); //making the shift number by multiplying by powers of 10
		}
		
		System.out.println(sum);
	}
}
