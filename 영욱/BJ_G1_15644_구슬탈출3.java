package bj.g1;

import java.util.*;
import java.io.*;

/**
 * @author 김영욱
 * @git
 * @performance
 * @category #
 * @note 시간 : 2초
 * 큐에 여태 움직였던 방향을 추가해서 출력해주면 끝..!
 * 그럼 여태 int배열로 하던거 클래스로 묶어줘야 할듯..?
 * @see https://www.acmicpc.net/problem/15644
 * @since 2024. 11. 15
 */
public class BJ_G1_15644_구슬탈출3 {

    static class recordSheet {
        int turn;
        int[] positions;
        String directionHistory;


        public recordSheet(int turn, int[] positions, String directionHistory) {
            this.turn = turn;
            this.positions = positions;
            this.directionHistory = directionHistory;
        }
    }

    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer tokens;

    static int N, M;
    static char[][] map;
    static boolean[][][][] visited;
    static int deltas[][] = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};// 상,하,좌,우
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
        bfs(redCord, blueCord);
    }

    private static void bfs(int[] redCord, int[] blueCord) {
        Queue<recordSheet> q = new ArrayDeque<>();
        recordSheet first = new recordSheet(1, new int[]{redCord[0], redCord[1], blueCord[0], blueCord[1]}, "");
        q.offer(first);// 시도 횟수, 빨간 공, 파란 공
        visited[redCord[0]][redCord[1]][blueCord[0]][blueCord[1]] = true;

        while (!q.isEmpty()) {
            recordSheet now = q.poll();
            int turn = now.turn;
            int rx = now.positions[0];
            int ry = now.positions[1];
            int bx = now.positions[2];
            int by = now.positions[3];

            if (turn > 10) {
                System.out.println(-1);
                return;
            }

            for (int d = 0; d < 4; d++) {
                String dir = switch (d) {
                    case 0 -> "U";
                    case 1 -> "D";
                    case 2 -> "L";
                    case 3 -> "R";
                    default -> throw new IllegalStateException("Unexpected value: " + d);
                };

                int[] nr = findCord(rx, ry, bx, by, d);
                int[] nb = findCord(bx, by, nr[0], nr[1], d);
                nr = findCord(nr[0], nr[1], nb[0], nb[1], d);// 파란공 때문에 막혀서 빨간 공이 못갔었으면 이제 갈 수 있음

                if ((nr[0] == goalX && nr[1] == goalY) && (nb[0] != goalX || nb[1] != goalY)) {
                    System.out.println(turn);
                    System.out.println(now.directionHistory + dir);
                    return;
                }
                else if (visited[nr[0]][nr[1]][nb[0]][nb[1]]) continue;
                else {
                    visited[nr[0]][nr[1]][nb[0]][nb[1]] = true;
                    q.offer(new recordSheet(turn + 1, new int[]{nr[0], nr[1], nb[0], nb[1]}, now.directionHistory + dir));
                }
            }
        }
        System.out.println(-1);
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
