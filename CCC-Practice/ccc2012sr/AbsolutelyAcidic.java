import java.util.*;

public class AbsolutelyAcidic { //taken from GPT and modified
	
    static Scanner scn = new Scanner(System.in);
	
    public static void main(String[] args) {
        
        int N = scn.nextInt();
        int[] freq = new int[1001]; // readings 1..1000
        
        for (int i = 0; i < N; i++) {
            int reading = scn.nextInt();
            freq[reading]++;
        }

        // Find the top two frequencies
        int maxFreq = 0, secondMaxFreq = 0;
        for (int f : freq) {
            if (f > maxFreq) {
                secondMaxFreq = maxFreq;
                maxFreq = f;
            } else if (f > secondMaxFreq && f < maxFreq) {
                secondMaxFreq = f;
            }
        }

        // Collect all readings with max and second max frequencies
        List<Integer> maxFreqReadings = new ArrayList<>();
        List<Integer> secondMaxFreqReadings = new ArrayList<>();
        for (int r = 1; r <= 1000; r++) {
            if (freq[r] == maxFreq) maxFreqReadings.add(r);
            else if (freq[r] == secondMaxFreq) secondMaxFreqReadings.add(r);
        }

        int result = 0;
        if (maxFreqReadings.size() >= 2) {
            // Multiple readings with the highest frequency
            result = maxFreqReadings.get(maxFreqReadings.size() - 1)
                   - maxFreqReadings.get(0);
        } else {
            // Single reading with highest frequency
            int highestReading = maxFreqReadings.get(0);
            for (int r : secondMaxFreqReadings) {
                result = Math.max(result, Math.abs(highestReading - r));
            }
        }

        System.out.println(result);
        scn.close();
    }
}