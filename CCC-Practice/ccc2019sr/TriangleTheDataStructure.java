import java.util.*;

public class TriangleTheDataStructure {
	static Scanner scn = new Scanner(System.in);
	
	static ArrayList<Integer> values = new ArrayList<Integer>();
	static HashMap<Integer, List<Integer>> data = new HashMap<Integer, List<Integer>>();
	
	static int total = 0;
	static ArrayList<Integer> currentSubtriangleIndices = new ArrayList<>();
	
	public static void main(String[] args) {
		int size = scn.nextInt();
		int subtriangleSize = scn.nextInt();

		for (int i = 0 ; i < size ; i++) {
			for (int j = 0 ; j <= i ; j++) {
				values.add(scn.nextInt());
			}
		}
		scn.close();
		
	    buildStructure(size);
	    
	    for (int i = 0 ; i < values.size() ; i++) {
	    	getSubtriangleIndices(i, subtriangleSize);
	    	
	    	int maximum = 0;
	    	for (int index : currentSubtriangleIndices) {
	    		int value = values.get(index);
	    		if (value > maximum) maximum = value;
	    	}
	    	total += maximum;
	    	
	    	currentSubtriangleIndices.clear();
	    }
	    
		System.out.println(total);
	}
	
	public static void getSubtriangleIndices(int index, int subtriangleSize) {
		if (!currentSubtriangleIndices.contains(index)) currentSubtriangleIndices.add(index);
		
		if (subtriangleSize == 1) {
			return;
		}
		
		int firstIndex;
		int secondIndex;
		
		if (data.containsKey(index) && data.get(index).size() != 0) { //happens when a subtriangle stretches past the bounds of the main triangle
			firstIndex = data.get(index).get(0);
			secondIndex = data.get(index).get(1);
		} else {
			currentSubtriangleIndices.clear();
			return;
		}
		
		if (!currentSubtriangleIndices.contains(firstIndex)) currentSubtriangleIndices.add(firstIndex);
		if (!currentSubtriangleIndices.contains(secondIndex)) currentSubtriangleIndices.add(secondIndex);
		
		getSubtriangleIndices(firstIndex, subtriangleSize - 1);
		getSubtriangleIndices(secondIndex, subtriangleSize - 1);
	}
	
	public static void buildStructure(int levels) { //taken from GPT
        int totalNodes = levels * (levels + 1) / 2;
        int current = 0;

        for (int level = 1; level <= levels; level++) {
            for (int position = 0; position < level; position++) {
                List<Integer> children = new ArrayList<>();

                int leftChild = current + level;

                int rightChild = current + level + 1;

                if (leftChild < totalNodes) children.add(leftChild);
                if (rightChild < totalNodes) children.add(rightChild);

                data.put(current, children);
                current++;
            }
        }
    }
}
