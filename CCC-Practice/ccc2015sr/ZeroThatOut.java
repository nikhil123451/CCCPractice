import java.util.*;

public class ZeroThatOut {
	static Scanner scn = new Scanner(System.in);
	
	public static void main(String[] args) {
		int bossNumbers = scn.nextInt();
		ArrayList<Integer> numbers = new ArrayList<>();
		
		for (int i = 0 ; i < bossNumbers ; i++) {
			int number = scn.nextInt();
			
			if (number == 0) {
				numbers.removeLast();
			} else {
				numbers.add(number);
			}
		}
		
		int sum = 0;
		
		for (int number : numbers) {
			sum += number;
		}
		
		System.out.print(sum);
	}
}
