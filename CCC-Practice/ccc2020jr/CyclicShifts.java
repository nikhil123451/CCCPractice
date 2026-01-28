import java.util.*;

public class CyclicShifts {
	static Scanner scn = new Scanner(System.in);
	
	public static void main(String[] args) {
		String inputString = scn.nextLine();
		String substring = scn.nextLine();
		scn.close();
		
		boolean found = false;
		
		for (String shift : getAllShifts(substring)) {
			if (inputString.contains(shift)) {
				found = true;
				break;
			}
		}
		
		if (found) {
			System.out.println("yes");
		} else {
			System.out.println("no");
		}
	}
	
	public static ArrayList<String> getAllShifts(String string) {
		ArrayList<String> shifts = new ArrayList<>();
		shifts.add(string);
		
		String shift = shift(string);
		shifts.add(shift);
		
		for (int i = 0 ; i < string.length() - 2 ; i++) { //accounted for 2 of them already
			shift = shift(shift);
			shifts.add(shift);
		}
		
		return shifts;
	}
	
	public static String shift(String string) {
		String[] characters = string.split("");
		String newString = "";
		
		for (int i = 1 ; i < characters.length + 1 ; i++) {
			if (i != characters.length) {
				newString = newString + characters[i];
			} else {
				newString = newString + characters[0];
			}
		}
		
		return newString;
	}
}
