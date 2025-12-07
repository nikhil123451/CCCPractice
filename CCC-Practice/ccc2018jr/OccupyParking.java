import java.util.Scanner;

public class OccupyParking {
	static Scanner scn = new Scanner(System.in);
	
	public static void main(String[] args) {
		int spots = scn.nextInt();
		scn.nextLine(); //parsing through \n
		String todaySpotsString = scn.nextLine();
		String yesterdaySpotsString = scn.nextLine();
		
		String[] todaySpots = new String[spots];
		String[] yesterdaySpots = new String[spots];
		
		for (int i = 0 ; i < spots ; i++) {
			todaySpots[i] = Character.toString(todaySpotsString.charAt(i));
			yesterdaySpots[i] = Character.toString(yesterdaySpotsString.charAt(i));
		}
		
		int occupiedBothDays = 0;
		for (int i = 0 ; i < spots ; i++) {
			if (todaySpots[i].contains(yesterdaySpots[i]) && todaySpots[i].contains("C")) {
				occupiedBothDays++;
			}
		}
		
		System.out.println(occupiedBothDays);
	}
}
