import java.util.*;

public class CombiningRiceballs {
	static Scanner scn = new Scanner(System.in);
	
	public static void main(String[] args) {
		int riceballs = scn.nextInt();
		ArrayList<Integer> sizes = new ArrayList<Integer>();
		
		for (int i = 0 ; i < riceballs ; i++) {
			sizes.add(scn.nextInt());
		}
		
		System.out.println(getLargestRiceball(sizes));
		scn.close();
	}
	
	public static int getLargestRiceball(ArrayList<Integer> sizes) {
		boolean adjacentExists = true;
		boolean middleAdjacentExists = true;
		ArrayList<Integer> adjacentIndexes;
		ArrayList<Integer> middleAdjacentIndexes;
		
		while (adjacentExists || middleAdjacentExists) {
			middleAdjacentIndexes = findMiddleAdjacentPairs(sizes);
			
			if (middleAdjacentIndexes.size() != 0) {
				
				for (int i = 0 ; i < middleAdjacentIndexes.size() ; i++) {
					int index = middleAdjacentIndexes.get(i);
					
					int newSize = sizes.get(index - 2*i) + sizes.get(index + 1 - 2*i) + sizes.get(index + 2 - 2*i);
					
					for (int j = 0 ; j < 3 ; j++) {
						sizes.remove(index - 2*i);
					}
					sizes.add(index - 2*i, newSize);
				}
				
				continue;
			} else {
				middleAdjacentExists = false;
			}
			
			adjacentIndexes = findAdjacentPairs(sizes);
			
			if (adjacentIndexes.size() != 0) {
				
				for (int i = 0 ; i < adjacentIndexes.size() ; i++) {
					int index = adjacentIndexes.get(i);
					
					int newSize = sizes.get(index - i) + sizes.get(index + 1 - i);
					
					for (int j = 0 ; j < 2 ; j++) {
						sizes.remove(index - i);
					}
					sizes.add(index - i, newSize);
				}
			} else {
				adjacentExists = false;
			}
		}
		
		int largestSize = sizes.getFirst();
		for (int size : sizes) {
			largestSize = Math.max(largestSize, size);
		}
		
		return largestSize;
	}
	
	public static ArrayList<Integer> findAdjacentPairs(ArrayList<Integer> sizes) {
		ArrayList<Integer> adjacentIndexes = new ArrayList<Integer>();
		
		for (int i = 0 ; i < sizes.size() - 1 ; i++) {
			if (sizes.get(i) == sizes.get(i + 1)) {
				adjacentIndexes.add(i);
			}
		}
		
		return adjacentIndexes;
	}
	
	public static ArrayList<Integer> findMiddleAdjacentPairs(ArrayList<Integer> sizes) {
		ArrayList<Integer> middleAdjacentIndexes = new ArrayList<Integer>();
		
		for (int i = 0 ; i < sizes.size() - 2 ; i++) {
			if (sizes.get(i) == sizes.get(i + 2)) {
				middleAdjacentIndexes.add(i);
			}
		}
		
		return middleAdjacentIndexes;
	}
	
}
