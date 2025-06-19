import java.util.Arrays;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;

public class ComputerPurchase {
	static Scanner scn = new Scanner(System.in);
	
	public static void main(String args[]) {
		int numberOfComputers;
		System.out.print("");
		numberOfComputers = scn.nextInt();
		
		if (numberOfComputers >= 0 && numberOfComputers <= 10000) {
			if (!(numberOfComputers == 0)) {
				String computerName = "";
				String[] computers = new String[numberOfComputers];
				int availableRam = 0;
				int cpuSpeed = 0;
				int diskDriveSpace = 0;
				ArrayList<Integer> machineValues = new ArrayList<Integer>();
				
				for (int i = 0 ; i <= numberOfComputers ; i++) {
					System.out.print("");
					String computerData = scn.nextLine();
					String[] splitComputerData = computerData.split(" ");
					if (i != 0) {
						if (splitComputerData[0].length() < 20) {
							computerName = splitComputerData[0];
							computers[i-1] = computerName;
						}
						if (Integer.parseInt(splitComputerData[1]) >= 1 && Integer.parseInt(splitComputerData[1]) <= 128) {
							availableRam = Integer.parseInt(splitComputerData[1]);
						}
						if (Integer.parseInt(splitComputerData[2]) >= 1 && Integer.parseInt(splitComputerData[2]) <= 4000) {
							cpuSpeed = Integer.parseInt(splitComputerData[2]);
						}
						if (Integer.parseInt(splitComputerData[3]) >= 1 && Integer.parseInt(splitComputerData[3]) <= 3000) {
							diskDriveSpace = Integer.parseInt(splitComputerData[3]);
						}
						
						int currentMachineValue = (2 * availableRam) + (3 * cpuSpeed) + diskDriveSpace; //formula
						machineValues.add(currentMachineValue);
					}
				}
				if (numberOfComputers > 1) {
					String firstLargest = computers[findTwoLargestIndexes(machineValues)[0]];
					String secondLargest = computers[findTwoLargestIndexes(machineValues)[1]];
					if (findTwoLargestIndexes(machineValues)[2] == 0) {
						System.out.println(firstLargest + "\n" + secondLargest);
					} else {
						String[] valuableComputers = {firstLargest, secondLargest};
						Arrays.sort(valuableComputers);
						for (String computer : valuableComputers) {
							System.out.println(computer);
						}
					}
				} else {
					System.out.println(computers[findLargestIndex(machineValues)]);
				}
			}
		}
	}
	
	private static int findLargestIndex(ArrayList<Integer> array) {
		int largestMachineValue = 0;
		int largestMachineValueCode = 0;
		for (int i = 0 ; i < array.size() ; i++) {
			int machineValue = array.get(i);
			if (machineValue > largestMachineValue) {
				largestMachineValue = machineValue;
				largestMachineValueCode = i;
			}
		}
		return largestMachineValueCode;
	}
	private static int[] findTwoLargestIndexes(ArrayList<Integer> array) {
		int largestMachineValue = 0;
		int secondLargestMachineValue = 0;
		int largestMachineValueCode = 0;
		int secondLargestMachineValueCode = 0;
		int[] codes = new int[3];
		for (int i = 0 ; i < array.size() ; i++) {
			int machineValue = array.get(i);
			if (machineValue > largestMachineValue) {
                secondLargestMachineValue = largestMachineValue;
    			secondLargestMachineValueCode = largestMachineValueCode;
                largestMachineValue = machineValue;
                largestMachineValueCode = i;
            } else if (machineValue > secondLargestMachineValue) {
                secondLargestMachineValue = machineValue;
    			secondLargestMachineValueCode = i;
            }
		}
		
		codes[0] = largestMachineValueCode;
		codes[1] = secondLargestMachineValueCode;
		if (largestMachineValue == secondLargestMachineValue) {
			codes[2] = 1;
		} else {
			codes[2] = 0;
		}
		return codes;
	}
}