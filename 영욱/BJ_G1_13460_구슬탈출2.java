package bj.g1;

import java.util.*;
import java.io.*;

/**
 * @author 김영욱
 * @git
 * @performance 
 * @category #
 * @note 시간 : 2초
 * 구슬 탈출1과 거의 똑같으나 최소한의 초만 구해주면 된다.
 * 투~이지
 * @see https://www.acmicpc.net/problem/13460
 * @since 2024. 11. 15
 */
public class BJ_G1_13460_구슬탈출2 {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer tokens;

    static int N, M;
    static char[][] map;
    static boolean[][][][] visited;
    static int deltas[][] = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    static int goalX, goalY;


    public static void main(String[] args) throws IOException {
        tokens = new StringTokenizer(input.readLine());
        N = Integer.parseInt(tokens.nextToken());
        M = Integer.parseInt(tokens.nextToken());

        map = new char[N][M];

        int[] redCord = new int[2];
        int[] blueCord = new int[2];
        visited = new boolean[N][M][N][M];

        for (int i = 0; i < N; i++) {
            String str = input.readLine();
            for (int j = 0; j < M; j++) {
                if (str.charAt(j) == 'R') {
                    redCord[0] = i;
                    redCord[1] = j;
                    map[i][j] = '.';
                } else if (str.charAt(j) == 'B') {
                    blueCord[0] = i;
                    blueCord[1] = j;
                    map[i][j] = '.';
                } else if (str.charAt(j) == 'O') {
                    goalX = i;
                    goalY = j;
                    map[i][j] = str.charAt(j);
                } else map[i][j] = str.charAt(j);
            }
        }
        System.out.println(bfs(redCord, blueCord));
    }

    private static int bfs(int[] redCord, int[] blueCord) {
        Queue<int[]> q = new ArrayDeque<>();
        q.offer(new int[]{1, redCord[0], redCord[1], blueCord[0], blueCord[1]});// 시도 횟수, 빨간 공, 파란 공
        visited[redCord[0]][redCord[1]][blueCord[0]][blueCord[1]] = true;

        while (!q.isEmpty()) {
            int[] now = q.poll();
            int turn = now[0];
            int rx = now[1];
            int ry = now[2];
            int bx = now[3];
            int by = now[4];

            if (turn > 10) return -1;

            for (int d = 0; d < 4; d++) {
                int[] nr = findCord(rx, ry, bx, by, d);
                int[] nb = findCord(bx, by, nr[0], nr[1], d);
                nr = findCord(nr[0], nr[1], nb[0], nb[1], d);// 파란공 때문에 막혀서 빨간 공이 못갔었으면 이제 갈 수 있음

                if ((nr[0] == goalX && nr[1] == goalY) && (nb[0] != goalX || nb[1] != goalY)) return turn;
                else if (visited[nr[0]][nr[1]][nb[0]][nb[1]]) continue;
                else {
                    visited[nr[0]][nr[1]][nb[0]][nb[1]] = true;
                    q.offer(new int[]{turn + 1, nr[0], nr[1], nb[0], nb[1]});
                }
            }
        }
        return -1;


    }

    private static int[] findCord(int x, int y, int obx, int oby, int d) {
        if (x == goalX && y == goalY) return new int[]{x, y};
        while (true) {
            int nx = x + deltas[d][0];
            int ny = y + deltas[d][1];
            if (isIn(nx, ny)) {
                if (map[nx][ny] == 'O') return new int[]{nx, ny};
                if (map[nx][ny] != '#' && (nx != obx || ny != oby)) {
                    x = nx;
                    y = ny;
                } else break;
            } else break;
        }
        return new int[]{x, y};
    }

    private static boolean isIn(int row, int col) {
        return (row >= 0 && row < N) && (col >= 0 && col < M);
    }
}
