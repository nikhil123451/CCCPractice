import java.util.Scanner;

public class ArrivalTime {
	static Scanner scn = new Scanner(System.in);
	static final int INITIAL_MORNING_RUSH_HOUR_TIME = 7;
	static final int FINAL_MORNING_RUSH_HOUR_TIME = 10;
	static final int INITIAL_AFTERNOON_RUSH_HOUR_TIME = 15;
	static final int FINAL_AFTERNOON_RUSH_HOUR_TIME = 19;
	
	static final int MINUTES_IN_AN_HOUR = 60;
	
	public static void main(String[] args) {
		String departureTime = scn.nextLine();
		String[] hourAndMinute = departureTime.split(":");
		int hour = Integer.parseInt(hourAndMinute[0]);
		int minute = Integer.parseInt(hourAndMinute[1]);
		
		int timeTaken = 2; //from problem
		
		double finalHour = 00;
		int finalMinute = minute;
		
		if (hour >= INITIAL_MORNING_RUSH_HOUR_TIME && hour <= FINAL_MORNING_RUSH_HOUR_TIME) {
			
			timeTaken *= 2; //from problem
			finalHour = hour + timeTaken;
			int timeTakenNotInRushHour = timeTaken - (FINAL_MORNING_RUSH_HOUR_TIME - INITIAL_MORNING_RUSH_HOUR_TIME);
			double properTimeTakenNotInRushHour = timeTakenNotInRushHour * 0.5; //reverting back to the original time taken
			finalHour -= properTimeTakenNotInRushHour;
			
		} else if (hour >= INITIAL_AFTERNOON_RUSH_HOUR_TIME && hour <= FINAL_AFTERNOON_RUSH_HOUR_TIME) { 
			
			timeTaken *= 2; //from problem
			finalHour = hour + timeTaken;
			int timeTakenNotInRushHour = timeTaken - (FINAL_AFTERNOON_RUSH_HOUR_TIME - INITIAL_AFTERNOON_RUSH_HOUR_TIME);
			double properTimeTakenNotInRushHour = timeTakenNotInRushHour * 0.5; //reverting back to the original time taken
			finalHour -= properTimeTakenNotInRushHour;
			
		} else {
			finalHour = hour + timeTaken;
		}
		
		double addedMinute = finalHour % 1; //identifying the decimal
		finalHour -= finalHour % 1; //and removing it
		finalMinute += addedMinute * MINUTES_IN_AN_HOUR;
		
		int addedHour = finalMinute / MINUTES_IN_AN_HOUR;
		finalMinute -= addedHour * MINUTES_IN_AN_HOUR;
		finalHour = (finalHour + addedHour) % 24; //convert to military time if above 24h
		
		int integerFinalHour = (int) finalHour;
		String finalHourString = Integer.toString(integerFinalHour);
		String finalMinuteString = Integer.toString(finalMinute);
		
		if (finalHour < 10) {
			finalHourString = "0" + finalHourString;
		}
		
		if (finalMinute < 10) {
			finalMinuteString = "0" + finalMinuteString;
		}
		
		System.out.println(finalHourString + ":" + finalMinuteString);
	}
}
