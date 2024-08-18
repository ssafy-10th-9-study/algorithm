package bj.g5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class BJ_G5_7569_토마토 {

    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder builder = new StringBuilder();
    static StringTokenizer tokens;

    static int N, M, F, cnt, ans;
    static int[][] deltas = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    static int[][][] map;
    static Queue<int[]> q = new ArrayDeque<>();


    public static void main(String[] args) throws IOException {
        tokens = new StringTokenizer(input.readLine());
        N = Integer.parseInt(tokens.nextToken());
        M = Integer.parseInt(tokens.nextToken());
        F = Integer.parseInt(tokens.nextToken());

        map = new int[F][M][N];
        cnt = 0;
        int minus = 0;

        for (int h = 0; h < F; h++) {
            for (int i = 0; i < M; i++) {
                tokens = new StringTokenizer(input.readLine());
                for (int j = 0; j < N; j++) {
                    map[h][i][j] = Integer.parseInt(tokens.nextToken());
                    if (map[h][i][j] == 1) {
                        q.offer(new int[]{h, i, j});
                        cnt++;
                    }
                    else if(map[h][i][j] == -1) {
                        minus++;
                    }
                }
            }
        }
        if (cnt == (N * M * F) - minus) System.out.println(0);
        else {
            bfs();
            if (cnt == (N * M * F) - minus) System.out.println(ans - 1);
            else System.out.println(-1);
        }
    }

    private static void bfs() {
        while (!q.isEmpty()) {
            int[] now = q.poll();
            int f = now[0];
            int x = now[1];
            int y = now[2];

//            아래층 전염
            if (f > 0 && map[f-1][x][y] == 0) {
                q.offer(new int[]{f - 1, x, y});
                map[f-1][x][y] = map[f][x][y]+1;
                cnt++;
            }
//            윗층 전염
            if (f < F - 1 && map[f+1][x][y] == 0) {
                q.offer(new int[]{f + 1, x, y});
                map[f+1][x][y] = map[f][x][y]+1;
                cnt++;
            }

            for (int d = 0; d < 4; d++) {
                int nx = x + deltas[d][0];
                int ny = y + deltas[d][1];

                if (isIn(nx, ny) && map[f][nx][ny] == 0) {
                    q.offer(new int[]{f, nx, ny});
                    map[f][nx][ny] = map[f][x][y]+1;
                    cnt++;
                }
            }
            ans = Math.max(ans, map[f][x][y]);
        }
    }

    private static boolean isIn(int nx, int ny) {
        return 0 <= nx && nx < M && 0 <= ny && ny < N;
    }
}
