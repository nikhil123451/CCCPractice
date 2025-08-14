import java.util.*;

public class ChancesOfWinning { //taken from GPT and modified
	
    static int favoriteTeam; //value representing the favorite team
    static int[] teamPoints = new int[5]; //an array for each of the 4 team's points (+1 cuz java is 0-indexed)
    static boolean[][] played = new boolean[5][5]; //making a 2d array for every team match up (+1 cuz java is 0-indexed)
    static List<int[]> remainingMatches = new ArrayList<>(); //making a list of int[] arrays to keep track of the remaining matches between teams that need to be played
    static int winningOutcomes = 0; //setting winning outcomes to 0 initially
    static Scanner scn = new Scanner(System.in); //initializing a scanner

    public static void main(String[] args) { //main method
    	
        favoriteTeam = scn.nextInt(); //setting the favorite team
        int gamesPlayed = scn.nextInt(); //getting the amount of games already played
        
        for (int i = 0; i < gamesPlayed; i++) { //looping through every game already played
            int teamA = scn.nextInt(); //getting the first team's number
            int teamB = scn.nextInt(); //getting the second team's number
            int scoreA = scn.nextInt(); //getting the first team's score
            int scoreB = scn.nextInt(); //getting the second team's score

            played[teamA][teamB] = played[teamB][teamA] = true; //adding the 2 teams to the played 2d array
            if (scoreA > scoreB) { //if the first team won
                teamPoints[teamA] += 3; //add 3 points to their point total
            } else if (scoreA < scoreB) { //if the second team won
                teamPoints[teamB] += 3; //add 3 points to their point total
            } else { //if the 2 teams tied
                teamPoints[teamA] += 1; //add one point to the first team
                teamPoints[teamB] += 1; //add one point to the second team
            }
        }

        for (int i = 1; i <= 4; i++) { //looping through every team
            for (int j = i + 1; j <= 4; j++) { //looping through every team again so that we can make match ups
                if (!played[i][j]) { //if the 2 teams haven't played yet
                	remainingMatches.add(new int[]{i, j}); //add the 2 teams to the remaining matches list
                }
            }
        }

        calculateWinningOutcomes(0); //calling a helper function to calculate the winning outcomes for the favorite team, starting with an index of 0

        System.out.println(winningOutcomes); //print out the result after the calculation
        scn.close(); //close the scanner
    }

    static void calculateWinningOutcomes(int index) {
        if (index == remainingMatches.size()) { //if the match is the last match
            for (int team = 1; team <= 4; team++) { //looping through every team
                if (team == favoriteTeam) { //if the current team is the favorite
                	continue; //keep going in the for loop
                }
                if (teamPoints[favoriteTeam] <= teamPoints[team]) { //if the points of the favorite team is less than or equal to the current team's points
                	return; //stop the depth-first search for this branch
                }
            }
            winningOutcomes++; //found a scenario where the favorite team won, so we count it
            return; //stop the depth-first search for this branch
        }

        int[] game = remainingMatches.get(index); //getting the next match to be processed
        int teamA = game[0]; //getting the first team from the game
        int teamB = game[1]; //getting the second team from the game

        teamPoints[teamA] += 3; //assuming the first team won
        calculateWinningOutcomes(index + 1); //trying the next game in this branch
        teamPoints[teamA] -= 3; //reverting the change

        teamPoints[teamB] += 3; //assuming the second team won
        calculateWinningOutcomes(index + 1); //trying the next game in this branch
        teamPoints[teamB] -= 3; //reverting the change

        teamPoints[teamA] += 1; //add 1 point to first team assuming the 2 teams tied
        teamPoints[teamB] += 1; //add 1 point to second team assuming the 2 teams tied
        calculateWinningOutcomes(index + 1); //trying the next game in this branch
        teamPoints[teamA] -= 1; //reverting the change on the first team
        teamPoints[teamB] -= 1; //reverting the change on the second team
    }
}