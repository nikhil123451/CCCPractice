import java.util.Scanner;

public class TimeToDecompress {
	static Scanner scn = new Scanner(System.in);
	
	public static void main(String[] args) {
		int lines = scn.nextInt();
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0 ; i < lines ; i++) {
			int amount = scn.nextInt();
			String symbol = scn.next();
			
			for (int j = 0 ; j < amount ; j++) {
				sb.append(symbol);
			}
			sb.append("\n");
			scn.nextLine(); //parsing through \n
		}
		
		System.out.println(sb.toString());
		scn.close();
	}
}
