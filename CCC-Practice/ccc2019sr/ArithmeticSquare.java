import java.util.*;

public class ArithmeticSquare { //taken from GPT and modified

    static Scanner scn = new Scanner(System.in);

    public static void main(String[] args) {
        Long[][] grid = new Long[3][3];

        // Read input
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++) {
                String s = scn.next();
                if (!s.equals("X")) grid[i][j] = Long.parseLong(s);
            }

        // Iteratively fill using known neighbors
        boolean changed;
        do {
            changed = false;
            for (int i = 0; i < 3; i++)
                changed |= fillRow(grid, i);
            for (int j = 0; j < 3; j++)
                changed |= fillCol(grid, j);
        } while (changed);

        // Fill center if still null using symmetric positions
        if (grid[1][1] == null) {
            List<Long> candidates = new ArrayList<>();
            if (grid[0][0] != null && grid[2][2] != null) candidates.add((grid[0][0]+grid[2][2])/2);
            if (grid[0][2] != null && grid[2][0] != null) candidates.add((grid[0][2]+grid[2][0])/2);
            for (int i = 0; i < 3; i++) {
                if (grid[i][0]!=null && grid[i][2]!=null) candidates.add((grid[i][0]+grid[i][2])/2);
                if (grid[0][i]!=null && grid[2][i]!=null) candidates.add((grid[0][i]+grid[2][i])/2);
            }
            grid[1][1] = candidates.isEmpty() ? 0L : candidates.get(0);
        }

        // Fill iteratively again after setting center
        do {
            changed = false;
            for (int i = 0; i < 3; i++)
                changed |= fillRow(grid, i);
            for (int j = 0; j < 3; j++)
                changed |= fillCol(grid, j);
        } while (changed);

        // Fill any remaining nulls safely
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (grid[i][j]==null) grid[i][j] = 0L;

        // Validate final grid (all cells non-null)
        if (!isArithmetic(grid)) {
            // last resort: adjust center to make grid arithmetic
            long newCenter = (grid[0][1] + grid[2][1])/2;
            grid[1][1] = newCenter;
            // Fill again
            do {
                changed = false;
                for (int i = 0; i < 3; i++)
                    changed |= fillRow(grid, i);
                for (int j = 0; j < 3; j++)
                    changed |= fillCol(grid, j);
            } while (changed);
        }

        // Print final grid
        printGrid(grid);
    }

    static boolean fillRow(Long[][] g, int r) {
        Long a=g[r][0], b=g[r][1], c=g[r][2];
        if (a!=null && b!=null && c==null){g[r][2]=2*b - a; return true;}
        if (a!=null && b==null && c!=null){g[r][1]=(a+c)/2; return true;}
        if (a==null && b!=null && c!=null){g[r][0]=2*b - c; return true;}
        return false;
    }

    static boolean fillCol(Long[][] g, int c) {
        Long a=g[0][c], b=g[1][c], d=g[2][c];
        if (a!=null && b!=null && d==null){g[2][c]=2*b - a; return true;}
        if (a!=null && b==null && d!=null){g[1][c]=(a+d)/2; return true;}
        if (a==null && b!=null && d!=null){g[0][c]=2*b - d; return true;}
        return false;
    }

    static boolean isArithmetic(Long[][] g) {
        for (int i=0;i<3;i++)
            if (g[i][0]==null || g[i][1]==null || g[i][2]==null || g[i][1]-g[i][0] != g[i][2]-g[i][1]) return false;
        for (int j=0;j<3;j++)
            if (g[0][j]==null || g[1][j]==null || g[2][j]==null || g[1][j]-g[0][j] != g[2][j]-g[1][j]) return false;
        return true;
    }

    static void printGrid(Long[][] g) {
        for (int i=0;i<3;i++)
            System.out.println(g[i][0]+" "+g[i][1]+" "+g[i][2]);
    }
}