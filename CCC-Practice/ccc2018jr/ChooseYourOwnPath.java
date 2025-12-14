import java.util.*;

public class ChooseYourOwnPath {
	static Scanner scn = new Scanner(System.in);
	
	static ArrayList<ArrayList<Integer>> con = new ArrayList<>();
	static ArrayList<Integer> ends = new ArrayList<Integer>();
	
	static boolean[] reachable;
	static boolean[] searched;
	static int[] distances;
	
	public static void main(String[] args) {
		int pages = scn.nextInt();
		reachable = new boolean[pages + 1]; //+1 cuz java is 0-indexed
		searched = new boolean[pages + 1]; //+1 cuz java is 0-indexed
		
		distances = new int[pages + 1]; //+1 cuz java is 0-indexed
		
		Arrays.fill(reachable, false);
		Arrays.fill(searched, false);
		Arrays.fill(distances, Integer.MAX_VALUE);
		
		reachable[1] = true; //page one is where we start
		
		for (int i = 0 ; i < pages ; i++) {
			int cons = scn.nextInt();
			con.add(new ArrayList<Integer>());
			if (cons != 0) {
				for (int j = 0 ; j < cons ; j++) {
					int pageCon = scn.nextInt();
					con.get(i).add(pageCon);
				}
			} else {
				ends.add(i + 1); //+1 cuz java is 0-indexed
			}
		}
		
		System.out.println(searchBook(1));
		Arrays.fill(searched, false);
		
		calculateDistances(1,1); //start at page 1, has an initial distance of 1 (inclusive)
		
		int minimum = Integer.MAX_VALUE;
		for (int i = 0 ; i < distances.length ; i++) {
			if (distances[i] < minimum) {
				minimum = distances[i];
			}
		}
		System.out.println(minimum);
	}
	
	public static boolean allReachable() {
		for (int i = 1 ; i < reachable.length ; i++) {
			if (!reachable[i]) {
				return false;
			}
		}
		return true;
	}
	
	public static String searchBook(int startPage) {
		if (!searched[startPage]) {
			searched[startPage] = true;
			for (int page = startPage ; page <= con.size() ; page++) {
				for (int i = 0 ; i < con.get(page - 1).size() ; i++) { //-1 cuz java is 0-indexed
					int pageCon = con.get(page - 1).get(i);
					reachable[pageCon] = true;
					
					if (allReachable()) {
						return "Y";
					}
					
					if (con.get(pageCon - 1).size() != 0) {
						searchBook(pageCon);
					}
				}
			}
			
			if (!allReachable()) {
				return "N";
			} else {
				return "Y";
			}
		} else {
			searchBook(startPage + 1);
		}
		
		return ""; //should never reach here
	}
	
	public static void calculateDistances(int startPage, int curD) {
		if (!searched[startPage]) {
			searched[startPage] = true;
			for (int page = startPage ; page <= con.size() ; page++) {
				for (int i = 0 ; i < con.get(page - 1).size() ; i++) { //-1 cuz java is 0-indexed
					int pageCon = con.get(page - 1).get(i);
					curD++;
					
					if (con.get(pageCon - 1).size() != 0) {
						calculateDistances(pageCon, curD);
					} else {
						if (curD < distances[pageCon]) {
							distances[pageCon] = curD;
						}
					}
				}
			}
		}
	}
}
