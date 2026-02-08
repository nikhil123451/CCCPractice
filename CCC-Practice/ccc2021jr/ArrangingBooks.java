import java.util.Scanner;

public class ArrangingBooks {
	static Scanner scn = new Scanner(System.in);
	
	public static void main(String[] args) {
		String line = scn.nextLine();
		int[] lineValues = new int[line.length()];
		
		String[] splitLine = line.split("");
		for (int i = 0 ; i < splitLine.length ; i++) {
			if (splitLine[i].equals("L")) {
				lineValues[i] = 2;
			} else if (splitLine[i].equals("M")) {
				lineValues[i] = 1;
			} else if (splitLine[i].equals("S")) {
				lineValues[i] = 0;
			} else {
				return; //invalid input
			}
		}
		
		int temp, swaps = 0;
		boolean swapped;
		
		for (int i = 0 ; i < lineValues.length - 1 ; i++) {
			swapped = false;
			
			for (int j = 0 ; j < lineValues.length - i - 1 ; j++) {
				if (lineValues[j] < lineValues[j + 1]) {
					temp = lineValues[j];
					lineValues[j] = lineValues[j + 1];
					lineValues[j + 1] = temp;
					swapped = true;
					swaps++;
				}
			}
			
			if (!swapped) {
				break;
			}
		}
		
		System.out.println(swaps);
	}
}
