import java.util.Scanner;

public class Art {
	static Scanner scn = new Scanner(System.in);
	
	static int[] x;
	static int[] y;
	
	public static void main(String[] args) {
		int points = scn.nextInt();
		
		x = new int[points];
		y = new int[points];
		
		scn.nextLine(); //parsing through \n
		
		for (int i = 0 ; i < points ; i++) {
			String coordinates = scn.nextLine();
			String[] splitCoordinates = coordinates.split(",");
			x[i] = Integer.parseInt(splitCoordinates[0]);
			y[i] = Integer.parseInt(splitCoordinates[1]);
		}
		
		int maxX = 0;
		int maxY = 0;
		int minX = Integer.MAX_VALUE;
		int minY = Integer.MAX_VALUE;
		
		for (int i = 0 ; i < points ; i++) {
			int currX = x[i];
			int currY = y[i];
			
			maxX = Math.max(maxX, currX);
			maxY = Math.max(maxY, currY);
			minX = Math.min(minX, currX);
			minY = Math.min(minY, currY);
		}
		
		//points on the frame aren't counted, so expand each point by 1 to encapsulate every point
		maxX++;
		maxY++;
		minX--;
		minY--;
		
		System.out.println(Integer.toString(minX) + "," + Integer.toString(minY));
		System.out.println(Integer.toString(maxX) + "," + Integer.toString(maxY));
		scn.close();
	}
}
