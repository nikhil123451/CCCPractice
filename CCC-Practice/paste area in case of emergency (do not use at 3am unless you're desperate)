import java.util.*;

public class MaximumStrategicSavings { //taken from GPT and modified
    static int[] pCity, szCity;
    static int[] pPlanet, szPlanet;

    static int findCity(int x) {
        while (pCity[x] != x) {
            pCity[x] = pCity[pCity[x]];
            x = pCity[x];
        }
        return x;
    }

    static int findPlanet(int x) {
        while (pPlanet[x] != x) {
            pPlanet[x] = pPlanet[pPlanet[x]];
            x = pPlanet[x];
        }
        return x;
    }

    static boolean unionCity(int a, int b) {
        a = findCity(a);
        b = findCity(b);
        if (a == b) return false;
        if (szCity[a] < szCity[b]) { int t = a; a = b; b = t; }
        pCity[b] = a;
        szCity[a] += szCity[b];
        return true;
    }

    static boolean unionPlanet(int a, int b) {
        a = findPlanet(a);
        b = findPlanet(b);
        if (a == b) return false;
        if (szPlanet[a] < szPlanet[b]) { int t = a; a = b; b = t; }
        pPlanet[b] = a;
        szPlanet[a] += szPlanet[b];
        return true;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int N = in.nextInt(); // planets
        int M = in.nextInt(); // cities per planet
        int P = in.nextInt(); // flight types per planet
        int Q = in.nextInt(); // portal types per city

        int[] a = new int[P];
        int[] b = new int[P];
        long[] c = new long[P];
        long sumFlights = 0;
        for (int i = 0; i < P; ++i) {
            a[i] = in.nextInt() - 1; // 0-based city index
            b[i] = in.nextInt() - 1;
            c[i] = in.nextLong();
            sumFlights += c[i];
        }

        int[] x = new int[Q];
        int[] y = new int[Q];
        long[] z = new long[Q];
        long sumPortals = 0;
        for (int j = 0; j < Q; ++j) {
            x[j] = in.nextInt() - 1; // 0-based planet index
            y[j] = in.nextInt() - 1;
            z[j] = in.nextLong();
            sumPortals += z[j];
        }

        // total sum of all edges
        // each flight-type appears on every planet -> multiplied by N
        // each portal-type appears on every city  -> multiplied by M
        long totalSum = sumFlights * (long)N + sumPortals * (long)M;

        // Prepare sorting order for flight-types and portal-types
        Integer[] idxF = new Integer[P];
        for (int i = 0; i < P; ++i) idxF[i] = i;
        Arrays.sort(idxF, (i1, i2) -> Long.compare(c[i1], c[i2]));

        Integer[] idxP = new Integer[Q];
        for (int j = 0; j < Q; ++j) idxP[j] = j;
        Arrays.sort(idxP, (i1, i2) -> Long.compare(z[i1], z[i2]));

        // init DSUs
        pCity = new int[M];
        szCity = new int[M];
        for (int i = 0; i < M; ++i) { pCity[i] = i; szCity[i] = 1; }
        pPlanet = new int[N];
        szPlanet = new int[N];
        for (int i = 0; i < N; ++i) { pPlanet[i] = i; szPlanet[i] = 1; }

        int ptrF = 0, ptrP2 = 0;
        long mstCost = 0;
        int cityComp = M;
        int planetComp = N;

        while ((ptrF < P || ptrP2 < Q) && (cityComp > 1 || planetComp > 1)) {
            long nextF = (ptrF < P) ? c[idxF[ptrF]] : Long.MAX_VALUE;
            long nextP = (ptrP2 < Q) ? z[idxP[ptrP2]] : Long.MAX_VALUE;

            if (nextF <= nextP) {
                int id = idxF[ptrF++];
                int u = a[id], v = b[id];
                if (findCity(u) != findCity(v)) {
                    // merging city components; cost multiplied by current number of planet components
                    unionCity(u, v);
                    mstCost += nextF * (long)planetComp;
                    cityComp--;
                }
            } else {
                int id = idxP[ptrP2++];
                int u = x[id], v = y[id];
                if (findPlanet(u) != findPlanet(v)) {
                    // merging planet components; cost multiplied by current number of city components
                    unionPlanet(u, v);
                    mstCost += nextP * (long)cityComp;
                    planetComp--;
                }
            }
        }

        long answer = totalSum - mstCost;
        System.out.println(answer);
    }
}