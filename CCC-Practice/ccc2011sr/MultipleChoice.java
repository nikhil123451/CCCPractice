import java.util.Scanner;

public class MultipleChoice {
	static Scanner scn = new Scanner(System.in);
	
	public static void main(String args[]) {
		int questions = scn.nextInt();
		
		if (questions > 0 && questions < 10000) {
			int correctAnswers = 0;
			String[] studentAnswers = new String[questions], answerKey = new String[questions];
			
			for (int i = 0 ; i < questions ; i++) {
				studentAnswers[i] = scn.next();
			}
			for (int i = 0 ; i < questions ; i++) {
				answerKey[i] = scn.next();
			}
			
			for (int i = 0 ; i < questions ; i++) {
				if (studentAnswers[i].contains(answerKey[i])) {
					correctAnswers++;
				}
			}
			
			System.out.println(correctAnswers);
		}
	}
}
