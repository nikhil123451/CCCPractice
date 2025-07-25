import java.util.Scanner;

public class IconScaling { //taken from GPT and modified
	
    static Scanner scn = new Scanner(System.in); //initializing a scanner
	
    public static void main(String[] args) { //main method
        int scaleFactor = scn.nextInt();  //getting the scaleFactor from input
        scn.close(); //closing the scanner
        if (scaleFactor < 25) { //if the input meets input specs
        	String[] icon = { //the original icon to be scaled
                    "*x*", //top row
                    " xx", //middle row
                    "* *" //bottom row
            };

            for (String row : icon) { //looping through every row in the icon
                StringBuilder scaledRow = new StringBuilder(); //creating a new string builder to scale up every row
                for (char character : row.toCharArray()) { //looping through every character in the original row
                    for (int i = 0; i < scaleFactor; i++) { //repeats scale factor times
                        scaledRow.append(character); //add the character again
                    }
                }

                for (int i = 0; i < scaleFactor; i++) { //repeats scale factor times
                    System.out.println(scaledRow.toString()); //print out the scaled row for vertical scaling
                }
            }
        }
    }
}
