import java.util.*;
import java.math.BigInteger;

public class BalancedTrees {
    // memoization map: weight -> number of perfectly balanced trees (BigInteger)
    static HashMap<Long, BigInteger> memo = new HashMap<>();

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        long N = in.nextLong();
        in.close();

        memo.put(1L, BigInteger.ONE);
        BigInteger ans = countTrees(N);
        System.out.println(ans);
    }

    // returns number of perfectly balanced trees of weight w
    static BigInteger countTrees(long w) {
        if (memo.containsKey(w)) return memo.get(w);
        BigInteger res = BigInteger.ZERO;

        long l = 2;
        while (l <= w) {
            long q = w / l;               // floor(w / k) for k in [l..r]
            if (q == 0) break;            // should not happen because l<=w implies q>=1
            long r = w / q;               // largest k with floor(w/k) == q
            if (r > w) r = w;
            long cnt = r - l + 1;         // number of k values producing this q
            BigInteger waysQ = countTrees(q);
            res = res.add(waysQ.multiply(BigInteger.valueOf(cnt)));
            l = r + 1;
        }

        memo.put(w, res);
        return res;
    }
}