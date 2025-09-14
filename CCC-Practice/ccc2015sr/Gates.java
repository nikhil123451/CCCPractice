import java.util.Scanner;

public class Gates {
	static Scanner scn = new Scanner(System.in);
	
	public static void main(String[] args) {
		int gates = scn.nextInt();
		int planes = scn.nextInt();
		
		int[] planePreferences = new int[planes + 1]; //+1 cuz java is 0-indexed
		
		for (int i = 1 ; i <= planes ; i++) {
			int maximumGate = scn.nextInt();
			planePreferences[i] = maximumGate;
		}
		
		scn.close();
		
		boolean[] occupiedGates = new boolean[gates + 1]; //+1 cuz java is 0-indexed
		int planesDocked = 0;
		
		planeLoop:
		for (int i = 1 ; i <= planes ; i++) {
			int gatePreference = planePreferences[i];
			
			for (int j = 0 ; j < gatePreference ; j++) {
				int possibleGate = gatePreference - j;
				if (!occupiedGates[possibleGate]) {
					occupiedGates[possibleGate] = true;
					planesDocked++;
					continue planeLoop;
				}
			}
			
			//could not find an available gate
			System.out.println(planesDocked);
			return;
		}
		
		System.out.println(planesDocked);
	}
}
