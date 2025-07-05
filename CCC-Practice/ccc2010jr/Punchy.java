import java.util.*;

public class Punchy {
	static Scanner scn = new Scanner(System.in);
	static int input;
	static Map<String, Integer> variables = new HashMap<>();
	
	public static void main(String args[]) {
        variables.put("variableA", 0);
        variables.put("variableB", 0);
        String x;
        String y;
        
		while (input != 7) {
			System.out.print("");
			input = scn.nextInt();
			switch (input) {
			case 1:
				variables.put("variable" + scn.next(), scn.nextInt());
				break;
			case 2:
				System.out.println(variables.get("variable" + scn.next()));
				break;
			case 3:
				x = "variable" + scn.next();
				y = "variable" + scn.next();
				Integer sum = variables.get(x) + variables.get(y);
				variables.put(x, sum);
				break;
			case 4:
				x = "variable" + scn.next();
				y = "variable" + scn.next();
				Integer product = variables.get(x) * variables.get(y);
				variables.put(x, product);
				break;
			case 5:
				x = "variable" + scn.next();
				y = "variable" + scn.next();
				Integer difference = variables.get(x) - variables.get(y);
				variables.put(x, difference);
				break;
			case 6:
				x = "variable" + scn.next();
				y = "variable" + scn.next();
				Integer quotient = variables.get(x) / variables.get(y);
				variables.put(x, quotient);
				break;
			case 7:
				break;
			}
			
		}
	}
}
