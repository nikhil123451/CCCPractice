import java.util.*;

public class WaitTime { //taken from GPT and modified
	
	static Scanner scn = new Scanner(System.in); //initializing a scanner
	
    public static void main(String[] args) { //main method
        int numberOfMessages = scn.nextInt(); //getting the number of messages
        scn.nextLine(); //parsing through \n

        List<String[]> messages = new ArrayList<>(); //making a list for every message
        for (int i = 0; i < numberOfMessages; i++) { //looping through every message
            String[] messageParts = scn.nextLine().trim().split("\\s+"); //trimming and splitting the line into the appropriate parts
            messages.add(messageParts); //adding the parts to the messages arrayList
        }
        
        List<int[]> events = new ArrayList<>(); //making a list for every event in the form {typeCharacter, friend ID, time}
        int time = 0; //setting an initial time of 0
        int i = 0; //setting an iterator, i, to 0 initially
        
        while (i < messages.size()) { //looping through every message
            String[] currrentMessage = messages.get(i); //getting the current message
            char messageType = currrentMessage[0].charAt(0); //getting the message type
            int friendID = Integer.parseInt(currrentMessage[1]); //getting the ID of the person who sent it
            if (messageType == 'R' || messageType == 'S') { //if the message was sent or received
                events.add(new int[]{messageType, friendID, time}); //add the information to the events list
                // Look ahead for W
                if (i + 1 < messages.size() && messages.get(i + 1)[0].equals("W")) { //if there is a next message and the next message is a waiting period
                    time += Integer.parseInt(messages.get(i + 1)[1]); //increase time accordingly
                    i += 2; //increment i by 2 as we've accounted for the waiting period in the next message
                } else { //if there isn't a waiting period next
                    time += 1; //increment time by 1 second
                    i++; //increment i
                }
            } else { //we have a W event that is just a waiting period
                time += friendID; //the friend ID would be the time in this case
                i++; //increment i
            }
        }

        TreeMap<Integer, Long> accumulatedWaitTimes = new TreeMap<>(); //keeps track of the total wait times for every friend
        HashMap<Integer, Integer> pendingMessages = new HashMap<>(); //keeps track of unreplied messages
        HashSet<Integer> invalidFriends = new HashSet<>(); //keeps track of friends who didn't follow etiquette or never got all their replies and will therefore be getting a -1 for time
        HashSet<Integer> friends = new HashSet<>(); //keeps track of all the friends who have gotten a message

        for (int[] event : events) { //looping through every event
            char type = (char) event[0]; //getting the message type
            int friendID = event[1]; //getting the friend's ID
            int currentTime = event[2]; //getting the time for the event

            if (type == 'R') { //if the message is a reply
                friends.add(friendID); //add the friend to the friend set
                accumulatedWaitTimes.putIfAbsent(friendID, 0L); //putting a wait time of 0 initially as a long
                if (pendingMessages.containsKey(friendID)) { //if the friend has a pending message
                    invalidFriends.add(friendID); //add them to the invalid friends set
                }
                pendingMessages.put(friendID, currentTime); //place the friend in the pending messages map
            } else if (type == 'S') { //if the message was sent
                accumulatedWaitTimes.putIfAbsent(friendID, 0L); //putting a wait time of 0 initially as a long
                if (!pendingMessages.containsKey(friendID)) { //if the friend has no pending messages
                    invalidFriends.add(friendID); //add them to the invalid friends set
                } else { //if they do have a pending message
                    int receiveTime = pendingMessages.remove(friendID); //gets the time from the pending message and removes it from the map
                    if (!invalidFriends.contains(friendID)) { //if the friend is not invalid
                        accumulatedWaitTimes.put(friendID, accumulatedWaitTimes.get(friendID) + (currentTime - receiveTime)); //adds up the total time the friend has waited an adds it to the accumulated wait time map
                    }
                }
            }
        }

        for (int friendID : pendingMessages.keySet()) { //for every friend in the pending messages map
            invalidFriends.add(friendID); //set them as invalid
        }

        for (int friendID : accumulatedWaitTimes.keySet()) { //looping through the accumulatedWaitTimes map
            if (!friends.contains(friendID)) { //if the id in the map is not a friend in the set
            	continue; //keep going through the map
            }
            
            long totalTime = invalidFriends.contains(friendID) ? -1 : accumulatedWaitTimes.get(friendID); //if the friend is invalid, the total time is -1. Otherwise, the total time is the value in the accumulatedWaitTime map
            System.out.println(friendID + " " + totalTime); //print out the ID along with their total time
        }
        
        scn.close(); //close the scanner
    }
}