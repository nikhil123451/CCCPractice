import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ChancesOfWinning2 {
	
    static int favoriteTeam;
    static int[] teamPoints = new int[5];
    static boolean[][] played = new boolean[5][5];
    static List<int[]> remainingMatches = new ArrayList<>();
    static int winningOutcomes = 0;
    static Scanner scn = new Scanner(System.in);

    public static void main(String[] args) {
    	
        favoriteTeam = scn.nextInt();
        int gamesPlayed = scn.nextInt();
        
        for (int i = 0; i < gamesPlayed; i++) {
            int teamA = scn.nextInt();
            int teamB = scn.nextInt();
            int scoreA = scn.nextInt();
            int scoreB = scn.nextInt();

            played[teamA][teamB] = played[teamB][teamA] = true;
            if (scoreA > scoreB) {
                teamPoints[teamA] += 3;
            } else if (scoreA < scoreB) {
                teamPoints[teamB] += 3;
            } else {
                teamPoints[teamA] += 1;
                teamPoints[teamB] += 1;
            }
        }

        for (int i = 1; i <= 4; i++) {
            for (int j = i + 1; j <= 4; j++) {
                if (!played[i][j]) {
                	remainingMatches.add(new int[]{i, j});
                }
            }
        }

        calculateWinningOutcomes(0);

        System.out.println(winningOutcomes);
        scn.close();
    }

    static void calculateWinningOutcomes(int index) {
        if (index == remainingMatches.size()) {
            for (int team = 1; team <= 4; team++) {
                if (team == favoriteTeam) {
                	continue;
                }
                if (teamPoints[favoriteTeam] <= teamPoints[team]) {
                	return;
                }
            }
            winningOutcomes++;
            return;
        }

        int[] game = remainingMatches.get(index);
        int teamA = game[0];
        int teamB = game[1];

        teamPoints[teamA] += 3;
        calculateWinningOutcomes(index + 1);
        teamPoints[teamA] -= 3;

        teamPoints[teamB] += 3;
        calculateWinningOutcomes(index + 1);
        teamPoints[teamB] -= 3;

        teamPoints[teamA] += 1;
        teamPoints[teamB] += 1;
        calculateWinningOutcomes(index + 1);
        teamPoints[teamA] -= 1;
        teamPoints[teamB] -= 1;
    }
}