import java.util.*;

public class BridgeTransport {
	static Scanner scn = new Scanner(System.in);
	
	static final int MAXIMUM_CARS_ON_BRIDGE = 4;
	
	public static void main(String[] args) {
		int maximumBridgeWeight = scn.nextInt();
		int railCars = scn.nextInt();
		int[] weights = new int[railCars + 1]; //+1 cuz java is 0-indexed
		
		if (maximumBridgeWeight >= 1 && maximumBridgeWeight <= 100000 && railCars >= 1 && railCars <= 100000) {
			for (int i = 1 ; i <= railCars ; i++) {
				int carWeight = scn.nextInt();
				if (carWeight >= 1 && carWeight <= 100000) {
					weights[i] = carWeight;
				}
			}
			
			int safeCars = 0;
			int totalBridgeWeight = 0;
			
			for (int i = 1 ; i <= weights.length ; i++) {
				int carWeight = weights[i];
				
				if (i > MAXIMUM_CARS_ON_BRIDGE) {
					totalBridgeWeight -= weights[i-4]; //removing the first car ahead on the bridge
				}
				
				totalBridgeWeight += carWeight;
				
				if (totalBridgeWeight > maximumBridgeWeight) {
					break; //literally
				} else {
					safeCars++;
				}
			}
			
			System.out.println(safeCars);
		}
	}
}
