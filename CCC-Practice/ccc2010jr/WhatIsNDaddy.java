import java.util.Scanner;

public class WhatIsNDaddy {
	static Scanner scn = new Scanner(System.in);
	static final int[] NUMBERS_PER_HAND= {1,2,3,4,5};
	
	public static void main(String args[]) {
		System.out.print("");
		int inputNumber = scn.nextInt();
		if (inputNumber >= 1 && inputNumber <= 10) {
			int total = 0;
			for (int i = 0 ; i < NUMBERS_PER_HAND.length ; i++) {
				int currentFirstNumber = NUMBERS_PER_HAND[i];
				for (int j = 0 ; j < NUMBERS_PER_HAND.length ; j++) {
					int currentSecondNumber = NUMBERS_PER_HAND[j];
					if (currentFirstNumber + currentSecondNumber == inputNumber) {
						total++;
					}
				}
			}
			System.out.println(total);
		}
	}
}
