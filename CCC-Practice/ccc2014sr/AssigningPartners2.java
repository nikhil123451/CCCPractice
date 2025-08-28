import java.util.Scanner;
import java.util.TreeMap;

public class AssigningPartners2 {
	static Scanner scn = new Scanner(System.in);
	
	static TreeMap<String, String> studentPairs = new TreeMap<>();
	
	public static void main(String[] args) {
		int students = scn.nextInt();
		
		String[] firstStudentLine = new String[students];
		String[] secondStudentLine = new String[students];
		
		for (int i = 0 ; i < students * 2 ; i++) { //account for both lines of students
			String student = scn.next();
			if (i < students) { //assigning the first line
				firstStudentLine[i] = student;
			} else { //assigning the second line
				secondStudentLine[i-students] = student; //subtract students to loop back to 0
			}
		}
		scn.close();
		
		for (int i = 0 ; i < firstStudentLine.length ; i++) {
			String firstCurrentStudent = firstStudentLine[i];
			String secondCurrentStudent = secondStudentLine[i];
			
			if (firstCurrentStudent.contains(secondCurrentStudent)) {
				System.out.println("bad");
				return;
			}
			
			if (!(studentPairs.containsKey(firstCurrentStudent) || studentPairs.containsKey(secondCurrentStudent) || studentPairs.containsValue(firstCurrentStudent) || studentPairs.containsValue(secondCurrentStudent))) {
				studentPairs.put(firstCurrentStudent, secondCurrentStudent);
			} else {
				if (studentPairs.get(secondCurrentStudent).contains(firstCurrentStudent)) {
					continue;
				} else {
					System.out.println("bad");
					return;
				}
			}
		}
		
		System.out.println("good");
	}
}