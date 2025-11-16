import java.util.*;

public class RoboThieves { //taken from GPT and modified

    static int N, M;
    static char[][] grid;
    static boolean[][] watched;
    static int[][] dist;
    static int[] dr = {-1, 1, 0, 0};
    static int[] dc = {0, 0, -1, 1};

    static int NINF = -1;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        N = sc.nextInt();
        M = sc.nextInt();
        grid = new char[N][M];

        int sr = 0, scn = 0;

        for (int i = 0; i < N; i++) {
            String line = sc.next();
            for (int j = 0; j < M; j++) {
                grid[i][j] = line.charAt(j);
                if (grid[i][j] == 'S') {
                    sr = i;
                    scn = j;
                }
            }
        }

        watched = new boolean[N][M];
        markWatchedCells();

        dist = new int[N][M];
        for (int[] row : dist) Arrays.fill(row, NINF);

        bfs(sr, scn);

        // Output distances only for '.' in row-major order
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (grid[i][j] == '.') {
                    System.out.println(dist[i][j]);
                }
            }
        }
    }

    // Mark cells watched by cameras
    static void markWatchedCells() {
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < M; c++) {
                if (grid[r][c] == 'C') {
                    // Up
                    for (int nr = r - 1; nr >= 0; nr--) {
                        if (grid[nr][c] == 'W') break;
                        if (grid[nr][c] == '.') watched[nr][c] = true;
                    }
                    // Down
                    for (int nr = r + 1; nr < N; nr++) {
                        if (grid[nr][c] == 'W') break;
                        if (grid[nr][c] == '.') watched[nr][c] = true;
                    }
                    // Left
                    for (int nc = c - 1; nc >= 0; nc--) {
                        if (grid[r][nc] == 'W') break;
                        if (grid[r][nc] == '.') watched[r][nc] = true;
                    }
                    // Right
                    for (int nc = c + 1; nc < M; nc++) {
                        if (grid[r][nc] == 'W') break;
                        if (grid[r][nc] == '.') watched[r][nc] = true;
                    }
                }
            }
        }
    }

    // Follow conveyors until reaching a stable non-conveyor cell or invalid
    static int[] followConveyor(int r, int c) {
        boolean[][] visitedConv = new boolean[N][M];
        int cr = r, cc = c;

        while (true) {
            if (grid[cr][cc] != 'L' && grid[cr][cc] != 'R' && grid[cr][cc] != 'U' && grid[cr][cc] != 'D') {
                return new int[]{cr, cc}; // empty or S
            }
            if (visitedConv[cr][cc]) return null; // cycle ⇒ invalid
            visitedConv[cr][cc] = true;

            int nr = cr, nc = cc;
            switch (grid[cr][cc]) {
                case 'L': nc--; break;
                case 'R': nc++; break;
                case 'U': nr--; break;
                case 'D': nr++; break;
            }

            if (grid[nr][nc] == 'W' || grid[nr][nc] == 'C') return null;
            cr = nr;
            cc = nc;
        }
    }

    static void bfs(int sr, int sc) {
        Queue<int[]> q = new LinkedList<>();
        q.add(new int[]{sr, sc});
        dist[sr][sc] = 0;

        // If S is on a conveyor, follow it immediately (no cost)
        if (isConveyor(sr, sc)) {
            int[] end = followConveyor(sr, sc);
            if (end != null) {
                dist[end[0]][end[1]] = 0;
                q.add(end);
            }
        }

        while (!q.isEmpty()) {
            int[] cur = q.poll();
            int r = cur[0], c = cur[1];
            int curDist = dist[r][c];

            for (int d = 0; d < 4; d++) {
                int nr = r + dr[d], nc = c + dc[d];

                if (!valid(nr, nc)) continue;
                if (grid[nr][nc] == 'W' || grid[nr][nc] == 'C') continue;

                // Cannot step into watched empty cells
                if (grid[nr][nc] == '.' && watched[nr][nc]) continue;

                int nextDist = curDist + 1;

                // If stepping onto a conveyor, follow it
                if (isConveyor(nr, nc)) {
                    int[] end = followConveyor(nr, nc);
                    if (end == null) continue;

                    int er = end[0], ec = end[1];
                    if (watched[er][ec] && grid[er][ec] == '.') continue;

                    if (dist[er][ec] == NINF || dist[er][ec] > nextDist) {
                        dist[er][ec] = nextDist;
                        q.add(new int[]{er, ec});
                    }
                } else {
                    if (dist[nr][nc] == NINF || dist[nr][nc] > nextDist) {
                        dist[nr][nc] = nextDist;
                        q.add(new int[]{nr, nc});
                    }
                }
            }
        }
    }

    static boolean valid(int r, int c) {
        return r >= 0 && r < N && c >= 0 && c < M;
    }

    static boolean isConveyor(int r, int c) {
        char ch = grid[r][c];
        return ch == 'L' || ch == 'R' || ch == 'U' || ch == 'D';
    }
}