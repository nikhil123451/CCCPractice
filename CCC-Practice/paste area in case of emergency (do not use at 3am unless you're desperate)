import java.util.*;

public class WaitTime { //taken from GPT and modified
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) return;
        int M = sc.nextInt();
        sc.nextLine();

        // Read all entries
        List<String[]> entries = new ArrayList<>();
        for (int i = 0; i < M; i++) {
            String[] parts = sc.nextLine().trim().split("\\s+");
            entries.add(parts);
        }

        // Build list of events with absolute times
        List<int[]> events = new ArrayList<>(); 
        // each event = [typeChar, friendId, time]
        // typeChar: 'R' or 'S'
        int time = 0;
        int i = 0;
        while (i < entries.size()) {
            String[] cur = entries.get(i);
            char type = cur[0].charAt(0);
            int x = Integer.parseInt(cur[1]);
            if (type == 'R' || type == 'S') {
                events.add(new int[]{type, x, time});
                // Look ahead for W
                if (i + 1 < entries.size() && entries.get(i + 1)[0].equals("W")) {
                    time += Integer.parseInt(entries.get(i + 1)[1]);
                    i += 2;
                } else {
                    time += 1;
                    i += 1;
                }
            } else { // just a waiting period, not attached to event
                time += x;
                i++;
            }
        }

        // Track wait times
        TreeMap<Integer, Long> totalWait = new TreeMap<>();
        HashMap<Integer, Integer> pending = new HashMap<>();
        HashSet<Integer> invalid = new HashSet<>();
        HashSet<Integer> friends = new HashSet<>();

        for (int[] e : events) {
            char type = (char) e[0];
            int id = e[1];
            int t = e[2];

            if (type == 'R') {
                friends.add(id);
                totalWait.putIfAbsent(id, 0L);
                if (pending.containsKey(id)) {
                    invalid.add(id);
                }
                pending.put(id, t);
            } else if (type == 'S') {
                totalWait.putIfAbsent(id, 0L);
                if (!pending.containsKey(id)) {
                    invalid.add(id);
                } else {
                    int recvTime = pending.remove(id);
                    if (!invalid.contains(id)) {
                        totalWait.put(id, totalWait.get(id) + (t - recvTime));
                    }
                }
            }
        }

        // Unreplied messages → invalid
        for (int id : pending.keySet()) {
            invalid.add(id);
        }

        // Output in ascending friend order
        for (int id : totalWait.keySet()) {
            if (!friends.contains(id)) continue;
            long total = invalid.contains(id) ? -1 : totalWait.get(id);
            System.out.println(id + " " + total);
        }
    }
}