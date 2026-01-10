import java.util.*;

public class SearchingForStrings {
	static Scanner scn = new Scanner(System.in);
	
	public static void main(String[] args) {
		String needle = scn.nextLine();
		String haystack = scn.nextLine();
		
		String[] needlePermutations = getPermutations(needle);
		ArrayList<String> usedPermutations = new ArrayList<>();
		
		int uniquePermumations = 0;
		
		for (String permutation : needlePermutations) {
			int permutationLength = permutation.length();
			for (int i = 0 ; i < haystack.length() - (permutationLength - 1); i++) {
				if (haystack.substring(i, i + permutationLength).contains(permutation) && 
						!usedPermutations.contains(permutation)){
					uniquePermumations++;
					usedPermutations.add(permutation);
				}
			}
		}
		
		System.out.println(uniquePermumations);
		scn.close();
	}
	
	public static String[] getPermutations(String needle) { //method to get the amount of permutations of a string
        char[] characters = needle.toCharArray(); //getting all the characters
        Arrays.sort(characters); //sorting all the characters from a to z

        boolean[] used = new boolean[characters.length]; //keeping track of used characters through a used[] boolean array
        List<String> permutations = new ArrayList<>(); //creating a list for the all the permutations accumulated

        findPermutations(characters, used, new StringBuilder(), permutations); //adding all the permutations

        return permutations.toArray(new String[0]); //return the final permutations found
    }

    private static void findPermutations(char[] characters, boolean[] used, StringBuilder currentPermutation, List<String> permutations) { //method that finds and adds every permutation of the string

        if (currentPermutation.length() == characters.length) { //if we've found a valid permutation that's complete
            permutations.add(currentPermutation.toString()); //add the current one to the main list
            return; //stop searching this branch
        }

        for (int i = 0; i < characters.length; i++) { //looping through every character

            if (used[i]) continue; //if this character has already been used

            if (i > 0 && characters[i] == characters[i - 1] && !used[i - 1]) continue; // go to the next character if the previous character is the same as the current one and if the previous one hasn't been used

            used[i] = true; //set this character as used
            currentPermutation.append(characters[i]); //add the character to the current permutation

            findPermutations(characters, used, currentPermutation, permutations); //find the permutations from this permutation

            currentPermutation.deleteCharAt(currentPermutation.length() - 1); //remove this character to try the next one
            used[i] = false; //make this one unused again
        }
    }
}
