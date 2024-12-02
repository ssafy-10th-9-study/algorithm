package bj.g1;

import java.util.*;
import java.io.*;

/**
 * @author 김영욱
 * @git
 * @performance 64ms
 * @category #
 * @note 시간 : 2초
 * 문제
 * 스타트링크에서 판매하는 어린이용 장난감 중에서 가장 인기가 많은 제품은 구슬 탈출이다.
 * 구슬 탈출은 직사각형 보드에 빨간 구슬과 파란 구슬을 하나씩 넣은 다음, 빨간 구슬을 구멍을 통해 빼내는 게임이다.( R이 빨간 구슬, B가 파란 구슬 )
 * 보드의 세로 크기는 N, 가로 크기는 M
 * 가장 바깥 행과 열은 모두 막혀져 있고, 보드에는 구멍이 하나 있다. ( 0이 구멍 )
 * 빨간 구슬과 파란 구슬의 크기는 보드에서 1×1크기의 칸을 가득 채우는 사이즈이고, 각각 하나씩 들어가 있다.
 * 게임의 목표는 빨간 구슬을 구멍을 통해서 빼내는 것이다.
 * 이때, 파란 구슬이 구멍에 들어가면 안 된다.
 * <p>
 * 이때, 구슬을 손으로 건드릴 수는 없고, 중력을 이용해서 이리 저리 굴려야 한다.
 * 왼쪽으로 기울이기, 오른쪽으로 기울이기, 위쪽으로 기울이기, 아래쪽으로 기울이기와 같은 네 가지 동작이 가능하다.
 * (한 쪽으로 기울이면 모든 구슬이 그 방향 끝까지 이동하는 방식인듯)
 * <p>
 * 각각의 동작에서 공은 동시에 움직인다. 빨간 구슬이 구멍에 빠지면 성공이지만, 파란 구슬이 구멍에 빠지면 실패이다.
 * 빨간 구슬과 파란 구슬이 동시에 구멍에 빠져도 실패이다. 빨간 구슬과 파란 구슬은 동시에 같은 칸에 있을 수 없다.
 * 또, 빨간 구슬과 파란 구슬의 크기는 한 칸을 모두 차지한다. 기울이는 동작을 그만하는 것은 더 이상 구슬이 움직이지 않을 때 까지이다.
 * <p>
 * 보드의 상태가 주어졌을 때, 10번 이하로 빨간 구슬을 구멍을 통해 빼낼 수 있는지 구하는 프로그램을 작성하시오.
 * <p>
 * 입력
 * 첫 번째 줄에는 보드의 세로, 가로 크기를 의미하는 두 정수 N, M (3 ≤ N, M ≤ 10)이 주어진다.
 * 다음 N개의 줄에 보드의 모양을 나타내는 길이 M의 문자열이 주어진다.
 * 이 문자열은 '.', '#', 'O', 'R', 'B' 로 이루어져 있다.
 * '.'은 빈 칸을 의미하고, '#'은 공이 이동할 수 없는 장애물 또는 벽을 의미하며, 'O'는 구멍의 위치를 의미한다.
 * 'R'은 빨간 구슬의 위치, 'B'는 파란 구슬의 위치이다.
 * <p>
 * 입력되는 모든 보드의 가장자리에는 모두 '#'이 있다. 구멍의 개수는 한 개 이며, 빨간 구슬과 파란 구슬은 항상 1개가 주어진다.
 * <p>
 * 출력
 * 파란 구슬을 구멍에 넣지 않으면서 빨간 구슬을 10번 이하로 움직여서 빼낼 수 있으면 1을 없으면 0을 출력한다.
 * <p>
 * BFS처럼 4방향 모두 움직이는 방식으로 진행하되, 큐에 현재 시도 회수, 빨간 공 위치, 파랑 공 위치를 넣어야 함
 * 1. #을 만나면 멈춘다
 * 2. 빨간 공을 움직인 후 파란공을 움직인 다음 빨간 공이 계속 움직일 수 있다면 움직일 것
 * 3. 움직일 때 벽, 다른 공에 도달하면 그 전에 멈추고, GOAL에 도달한다면 GOAL 위치에 멈추도록
 * 4. 4차원 방문 배열을 활용해 두 빨간 공과 파란 공의 좌표에 동시에 도달한 적이 있다면 다시 볼 필요 X
 * - 첫 시도 때 이거 안해줘서 풀긴 했는데 440ms나왔음
 * @see https://www.acmicpc.net/problem/13459
 * @since 2024. 11. 14
 */

public class BJ_G1_13459_구슬탈출 {
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

            if (turn > 10) return 0;

            for (int d = 0; d < 4; d++) {
                int[] nr = findCord(rx, ry, bx, by, d);
                int[] nb = findCord(bx, by, nr[0], nr[1], d);
                nr = findCord(nr[0], nr[1], nb[0], nb[1], d);// 파란공 때문에 막혀서 빨간 공이 못갔었으면 이제 갈 수 있음

                if ((nr[0] == goalX && nr[1] == goalY) && (nb[0] != goalX || nb[1] != goalY)) return 1;
                else if (visited[nr[0]][nr[1]][nb[0]][nb[1]]) continue;
                else {
                    visited[nr[0]][nr[1]][nb[0]][nb[1]] = true;
                    q.offer(new int[]{turn + 1, nr[0], nr[1], nb[0], nb[1]});
                }
            }
        }
        return 0;


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
