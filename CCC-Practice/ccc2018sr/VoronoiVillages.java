import java.util.*;

public class VoronoiVillages {
	static Scanner scn = new Scanner(System.in);
	
	public static void main(String args[]) {
		int villages = scn.nextInt();
		int[] villagePositions = new int[villages];
		
		for (int i = 0 ; i < villages ; i++) {
			villagePositions[i] = scn.nextInt();
		}
		scn.close();
		
		Arrays.sort(villagePositions);
		ArrayList<Double> neighbourLengths = new ArrayList<Double>();
		
		for (int i = 1 ; i < villages - 1 ; i++) { //not including the endpoints because they will have infinite lengths for neighbours 
			double lowerMidpoint = (villagePositions[i] + villagePositions[i - 1])/2.0; //midpoint formula
			double higherMidpoint = (villagePositions[i] + villagePositions[i + 1])/2.0; //midpoint formula (again)
			double neighbourLength = higherMidpoint - lowerMidpoint;
			neighbourLengths.add(neighbourLength);
		}
		
		double minimum = neighbourLengths.get(0);
		for (double length : neighbourLengths) {
			minimum = Math.min(length, minimum);
		}
		
		System.out.println(String.format("%.1f", minimum));
	}
}
