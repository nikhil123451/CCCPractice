import java.util.*;

public class EscapeRoom {
	static Scanner scn = new Scanner(System.in);
	
	static int[][] grid;
	static int rows;
	static int columns;
	static boolean[][] checked;
	static boolean foundPath = false;
	
	public static void main(String[] args) {
		rows = scn.nextInt();
		columns = scn.nextInt();
		
		grid = new int[rows + 1][columns + 1]; //+1 cuz java is 0-indexed
		checked = new boolean[rows + 1][columns + 1]; //+1 cuz java is 0-indexed
		
		for (int i = 1 ; i < grid.length ; i++) {
			for (int j = 1 ; j < grid[i].length ; j++) {
				grid[i][j] = scn.nextInt();
			}
		}
		scn.close();
		
		findPath(1,1); //starting at (1,1)
		if (foundPath) {
			System.out.println("yes");
		} else {
			System.out.println("no");
		}
	}
	
	public static void findPath(int startingRow, int startingColumn) {
		if (startingRow == rows && startingColumn == columns) {
			foundPath = true;
			return;
		}
		
		int value = grid[startingRow][startingColumn];
		List<int[]> pairs = getFactorPairs(value);
		
		for (int[] pair : pairs) {
			int pairRow = pair[0];
			int pairColumn = pair[1];
			
			if (pairRow <= rows && pairColumn <= columns && !checked[pairRow][pairColumn]) {
				checked[pairRow][pairColumn] = true;
				findPath(pairRow, pairColumn);
			}
		}
	}
	
	public static List<int[]> getFactorPairs(int number) {
        List<int[]> pairs = new ArrayList<>();

        for (int i = 1; i <= number; i++) {
            if (number % i == 0) {
                pairs.add(new int[]{i, number / i});
            }
        }

        return pairs;
    }
}
