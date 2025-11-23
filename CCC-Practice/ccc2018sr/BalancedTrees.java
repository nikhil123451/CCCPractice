import java.util.*;
import java.math.BigInteger;

public class BalancedTrees { //taken from GPT and modified
    static HashMap<Long, BigInteger> memory = new HashMap<>(); //hashMap to store previously calculated trees
    
    static Scanner scn = new Scanner(System.in); //initializing a scanner
    
    public static void main(String[] args) { //main method
        long weight = scn.nextLong(); //getting the weight
        scn.close(); //closing the scanner

        memory.put(1L, BigInteger.ONE); //mapping a weight of one to one tree with that weight
        BigInteger trees = countTrees(weight); //counting the amount of trees with the given weight
        System.out.println(trees); //printing out the result
    }

    static BigInteger countTrees(long weight) { //method that gets the number of trees with a given weight
        if (memory.containsKey(weight)) { //if the number of trees for the weight has already been calculated
        	return memory.get(weight); //return the pre-calculated value
        }
        BigInteger trees = BigInteger.ZERO; //initially setting the amount to 0

        long length = 2; //setting our initial length to be calculated as 2 (as given in the problem)
        while (length <= weight) { //while our tree length is less than or equal to the given weight
            long group = weight / length; //diving the weight into even groups
            if (group == 0) { //if weight is somehow less than the length
            	break; //break the loop
            }
            long range = weight / group; //getting the range of distributed groups
            if (range > weight) { //if the range exceeds the weight
            	range = weight; //cap it at the weight
            }
            long count = range - length + 1; //getting the amount of integers from the value of the length to the value of the range
            BigInteger treesOfGroup = countTrees(group); //getting the amount of trees for the group (which is now its own weight)
            trees = trees.add(treesOfGroup.multiply(BigInteger.valueOf(count))); //adding the amount of trees for the group times the amount they show up
            length = range + 1; //going to the next block to be processed
        }

        memory.put(weight, trees); //putting the amount of trees for the given weight
        return trees; //returning the final amount of trees
    }
}