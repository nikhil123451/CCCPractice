import java.util.*;

public class HighTideLowTide {
	static Scanner scn = new Scanner(System.in);
	
	public static void main(String[] args) {
		int meas = scn.nextInt();
		int[] arr = new int[meas];
		
		for (int i = 0 ; i < meas ; i++) {
			arr[i] = scn.nextInt();
		}
		Arrays.sort(arr);
		
		int[] lowM = new int[(meas + 1) / 2]; //setting the low measurement amount to account for one extra measurement
		int[] highM = new int[meas / 2];
		
		for (int i = 0 ; i < lowM.length ; i++) {
			lowM[i] = arr[lowM.length - 1 - i]; //arrange in decreasing order
		}
		
		for (int i = 0 ; i < highM.length ; i++) {
			highM[i] = arr[lowM.length + i];
		}
		
		for (int i = 0 ; i < meas ; i++) {
			if (i % 2 == 0) {
				System.out.print(lowM[i / 2] + " ");
			} else {
				System.out.print(highM[i / 2] + " ");
			}
		}
		
		scn.close();
	}
}
