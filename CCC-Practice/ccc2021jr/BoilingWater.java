import java.util.Scanner;

public class BoilingWater {
	static Scanner scn = new Scanner(System.in);
	static final int PRESSURE_AT_SEA_LEVEL = 100;
	
	public static void main(String[] args) {
		int temperature = scn.nextInt();
		scn.close();
		
		int pressure = (5 * temperature) - 400; //formula from problem
		System.out.println(pressure);
		
		if (pressure < PRESSURE_AT_SEA_LEVEL) {
			System.out.println(1);
		} else if (pressure == PRESSURE_AT_SEA_LEVEL) {
			System.out.println(0);
		} else if (pressure > PRESSURE_AT_SEA_LEVEL) {
			System.out.println(-1);
		} else {
			return; //should never reach here
		}
	}
}
