import java.util.Scanner;
import java.util.ArrayList;

public class SecretInstructions {
	static Scanner scn = new Scanner(System.in);
	
	static ArrayList<String> inst = new ArrayList<String>();
	
	public static void main(String[] args) {
		String seq = scn.nextLine();
		int seqNum = 0;
		int sum = 0;
		
		while (true) {
			String previousSeqDir = "";
			if (seqNum != 0) {
				seq = scn.nextLine();
				previousSeqDir = (sum % 2 == 1) ? "left" : "right";
			}
			
			if (seq.equals("99999")) break;
			
			String instruction = "";
			String[] digits = seq.split("");
			
			sum = Integer.parseInt(digits[0]) + Integer.parseInt(digits[1]);
			
			if (sum % 2 == 1) {
				instruction += "left";
			} else {
				if (sum != 0) {
					instruction += "right";
				} else {
					instruction += previousSeqDir;
				}
			}
			
			instruction += " " + digits[2] + digits[3] + digits[4];
			inst.add(instruction);
			
			seqNum++;
		}
		
		for (String instruction : inst) {
			System.out.println(instruction);
		}
	}
}
