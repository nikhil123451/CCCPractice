import java.util.*;

public class CircleOfLife {
	static Scanner scn = new Scanner(System.in);
	static final int ALIVE = 1;
	static final int DEAD = 0;
	
	public static void main(String[] args) {
		int cells = scn.nextInt();
		long generations = scn.nextLong();
		scn.nextLine(); //parsing through \n
		
		String initialState = scn.nextLine();
		scn.close();
		
		String[] initialStateCells = initialState.split("");
		
		int[] cellStates = new int[cells];
		for (int i = 0 ; i < cells ; i++) {
			cellStates[i] = Integer.parseInt(initialStateCells[i]);
		}
		
		for (int i = 0 ; i < generations ; i++) {
			int[] newCellStates = cellStates.clone();
			for (int j = 0 ; j < cellStates.length ; j++) {
				if (j == 0) {
					if (cellStates[cellStates.length - 1] == ALIVE ^ cellStates[j + 1] == ALIVE) {
						newCellStates[j] = ALIVE;
					} else {
						newCellStates[j] = DEAD;
					}
				} else if (j == cellStates.length - 1) {
					if (cellStates[j - 1] == ALIVE ^ cellStates[0] == ALIVE) {
						newCellStates[j] = ALIVE;
					} else {
						newCellStates[j] = DEAD;
					}
				} else {
					if (cellStates[j - 1] == ALIVE ^ cellStates[j + 1] == ALIVE) {
						newCellStates[j] = ALIVE;
					} else {
						newCellStates[j] = DEAD;
					}
				}
			}
			cellStates = newCellStates;
		}
		
		String finalState = "";
		for (int cellState : cellStates) {
			finalState = finalState.concat(Integer.toString(cellState));
		}
		
		System.out.println(finalState);
	}
}
