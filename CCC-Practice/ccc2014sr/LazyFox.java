import java.util.*;

public class LazyFox {
	static Scanner scn = new Scanner(System.in);
	
	static int[] currentCoordinates = {0, 0};
	static double largestDistanceTravelled = 0;
	static boolean travelled = false;
	
	public static void main(String[] args) {
		int neighbours = scn.nextInt();
		
		List<int[]> allCoordinates = new ArrayList<>();
		
		for (int i = 0 ; i < neighbours ; i++) {
			allCoordinates.add(new int[2]); //2 coordinates x and y
		}
		
		for (int i = 0 ; i < neighbours ; i++) {
			int xCoordinate = scn.nextInt();
			int yCoordinate = scn.nextInt();
			
			allCoordinates.get(i)[0] = xCoordinate;
			allCoordinates.get(i)[1] = yCoordinate;
		}
		
		int treats = 0;
		boolean treatStatus = true;
		
		do {
			if (findLargestDistance(allCoordinates)) {
				treats++;
				continue;
			} else {
				treatStatus = false;
			}
		} while(treatStatus);
		
		System.out.println(treats);
		scn.close();
	}
	
	public static boolean findLargestDistance(List<int[]> allCoordinates) {
		double largestDistance = 0;
		int currentX = currentCoordinates[0];
		int currentY = currentCoordinates[1];
		
		int largestIndex = 0;
		
		for (int i = 0 ; i < allCoordinates.size() ; i++) {
			
			int x2 = allCoordinates.get(i)[0];
			int y2 = allCoordinates.get(i)[1];
			
			if (x2 == currentX && y2 == currentY) {
				continue;
			}
			
			int deltaX = x2 - currentX;
			int deltaY = y2 - currentY;
			
			double distance = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2)); //distance formula
			
			if (distance > largestDistance) {
				if (!travelled) {
					largestDistance = distance;
					largestIndex = i;
				} else {
					if (distance < largestDistanceTravelled) {
						largestDistance = distance;
						largestIndex = i;
					}
				}
			}
		}
		
		if (!travelled) {
			travelled = true;
			largestDistanceTravelled = largestDistance;
			currentCoordinates[0] = allCoordinates.get(largestIndex)[0];
			currentCoordinates[1] = allCoordinates.get(largestIndex)[1];
			return true;
		} else {
			if (largestDistance < largestDistanceTravelled) {
				largestDistanceTravelled = largestDistance;
				currentCoordinates[0] = allCoordinates.get(largestIndex)[0];
				currentCoordinates[1] = allCoordinates.get(largestIndex)[1];
				return true;
			} else {
				return false;
			}
		}
	}
}
