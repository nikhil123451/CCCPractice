import java.util.*;

public class TintedGlassWindow {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        long T = sc.nextLong();

        int[] xl = new int[N], yt = new int[N], xr = new int[N], yb = new int[N];
        long[] ti = new long[N];

        ArrayList<Integer> xsList = new ArrayList<>();
        ArrayList<Integer> ysList = new ArrayList<>();

        for (int i = 0; i < N; i++) {
            xl[i] = sc.nextInt();
            yt[i] = sc.nextInt();
            xr[i] = sc.nextInt();
            yb[i] = sc.nextInt();
            ti[i] = sc.nextLong();

            xsList.add(xl[i]);
            xsList.add(xr[i]);
            ysList.add(yt[i]);
            ysList.add(yb[i]);
        }
        sc.close();

        int[] xs = uniqueSorted(xsList);
        int[] ys = uniqueSorted(ysList);

        // map coordinate -> compressed index
        Map<Integer,Integer> xIndex = new HashMap<>();
        for (int i = 0; i < xs.length; i++) xIndex.put(xs[i], i);
        Map<Integer,Integer> yIndex = new HashMap<>();
        for (int i = 0; i < ys.length; i++) yIndex.put(ys[i], i);

        // event lists for each x
        List<long[]>[] events = new ArrayList[xs.length];
        for (int i = 0; i < xs.length; i++) events[i] = new ArrayList<>();

        for (int i = 0; i < N; i++) {
            int xi1 = xIndex.get(xl[i]);
            int xi2 = xIndex.get(xr[i]);
            int yi1 = yIndex.get(yt[i]);
            int yi2 = yIndex.get(yb[i]);
            events[xi1].add(new long[]{yi1, yi2, ti[i]});    // add
            events[xi2].add(new long[]{yi1, yi2, -ti[i]});   // remove
        }

        long[] cur = new long[ys.length - 1]; // tint for each y-interval
        long area = 0;

        for (int xi = 0; xi < xs.length - 1; xi++) {
            // apply events at this vertical line
            for (long[] e : events[xi]) {
                int y1 = (int) e[0], y2 = (int) e[1];
                long d = e[2];
                for (int y = y1; y < y2; y++) {
                    cur[y] += d;
                }
            }

            // measure length in y with tint ≥ T
            long coveredLen = 0;
            for (int y = 0; y < ys.length - 1; y++) {
                if (cur[y] >= T) {
                    coveredLen += ys[y+1] - ys[y];
                }
            }

            long width = xs[xi+1] - xs[xi];
            area += coveredLen * width;
        }

        System.out.println(area);
    }

    private static int[] uniqueSorted(ArrayList<Integer> list) {
        Collections.sort(list);
        ArrayList<Integer> uniq = new ArrayList<>();
        Integer prev = null;
        for (int v : list) {
            if (prev == null || v != prev) {
                uniq.add(v);
                prev = v;
            }
        }
        int[] arr = new int[uniq.size()];
        for (int i = 0; i < uniq.size(); i++) arr[i] = uniq.get(i);
        return arr;
    }
}