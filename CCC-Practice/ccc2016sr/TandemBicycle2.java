import java.util.Arrays;
import java.util.Scanner;

public class TandemBicycle2 {
    static Scanner scn = new Scanner(System.in);
    
    public static void main(String[] args) {
    	int question = scn.nextInt();
    	int citizens = scn.nextInt();
    	
    	int[] dmojistanCitizenSpeeds = new int[citizens], peglandCitizenSpeeds = new int[citizens];
    	
    	for (int i = 0 ; i < citizens ; i++) {
    		dmojistanCitizenSpeeds[i] = scn.nextInt();
    	}
    	for (int i = 0 ; i < citizens ; i++) {
    		peglandCitizenSpeeds[i] = scn.nextInt();
    	}
    	scn.close();
    	
    	Arrays.sort(dmojistanCitizenSpeeds);
    	Arrays.sort(peglandCitizenSpeeds);
    	
    	int totalSpeed = 0;
    	
    	if (question == 1) { //minimum speed
    		for (int i = 0 ; i < citizens ; i++) {
    			totalSpeed += Math.max(dmojistanCitizenSpeeds[i], peglandCitizenSpeeds[i]); //pairing the smallest with the smallest
    		}
    	} else if (question == 2) { //maximum speed
    		for (int i = 0 ; i < citizens ; i++) {
    			totalSpeed += Math.max(dmojistanCitizenSpeeds[i], peglandCitizenSpeeds[citizens - 1 - i]); //pairing the smallest with the largest
    		}
    	} else { //invalid question
    		return;
    	}
    	
    	System.out.println(totalSpeed);
    }
}