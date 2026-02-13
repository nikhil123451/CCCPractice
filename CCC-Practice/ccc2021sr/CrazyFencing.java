import java.util.Scanner;

public class CrazyFencing {
	static Scanner scn = new Scanner(System.in);
	
	public static void main(String[] args) {
		int fences = scn.nextInt();
		
		double[] fenceHeights = new double[fences + 1]; //left and right heights
		double[] fenceWidths = new double[fences];
		
		for (int i = 0 ; i <= fences ; i++) fenceHeights[i] = scn.nextDouble();
		for (int i = 0; i < fences ; i++) fenceWidths[i] = scn.nextDouble();
		
		double totalArea = 0;
		for (int i = 0 ; i < fenceHeights.length - 1 ; i++) {
			double leftHeight = fenceHeights[i];
			double rightHeight = fenceHeights[i + 1];
			double width = fenceWidths[i];
			
			totalArea += (Math.abs(rightHeight - leftHeight) * width) / 2.0; 
			totalArea += Math.min(leftHeight, rightHeight) * width;
		}
		
		if ((totalArea * 10) % 10 == 0) {
			System.out.println((int) totalArea);
		} else {
			System.out.println(totalArea);
		}
		scn.close();
	}
}
