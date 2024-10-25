package programmers;

import java.util.*;
import java.io.*;

/**
 * @author 김영욱
 * @git
 * @performance
 * @category #DP
 * @note 처음에는 최소값을 2차원 배열에 저장해서 BFS를 진행했었다.
 * 테케는 전부 통과했지만 이는 반례가 있었다.
 * 지금 당장은 최소값이여도 다음 이동할 곳을 생각했을때는 최소값이 아닌 문제가 있었다.
 * 결국 DP문제였고 4방향에 따른 최소값을 전부 구해줘야했다.
 * 그래서 3차원배열로 값을 저장하여 4방향의 N-1, N-1 좌표의 값 중 최소인 값을 출력하도록 구현하였다.
 * 검색을 통해 알게되었다.
 * https://blogshine.tistory.com/595
 * @see https://school.programmers.co.kr/learn/courses/30/lessons/67259
 * @since 2024. 09. 17
 */
public class PG_LV3_경주로건설 {

    static int[][] deltas = {{0, 1}, {1, 0}, {-1, 0}, {0, -1}};// 우하상좌
    static int[][][] map;
    static int N, M;
    static int[][] board = {
            {0, 0, 0, 0, 0, 0, 0, 0},
            {1, 0, 1, 1, 1, 1, 1, 0},
            {1, 0, 0, 1, 0, 0, 0, 0},
            {1, 1, 0, 0, 0, 1, 1, 1},
            {1, 1, 1, 1, 0, 0, 0, 0},
            {1, 1, 1, 1, 1, 1, 1, 0},
            {1, 1, 1, 1, 1, 1, 1, 0},
            {1, 1, 1, 1, 1, 1, 1, 0}
    };

    public static void main(String[] args) {
        N = board.length;
        M = board[0].length;
        map = new int[4][N][M];
        for (int d = 0; d < 4; d++) {
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < M; j++) {
                    map[d][i][j] = 10000000;
                }
            }
        }
        for (int i = 0; i < 4; i++) {
            map[i][0][0] = 0;
        }

        int answer = bfs(0, 0, board);
        System.out.println(answer);
    }

    private static int bfs(int startX, int startY, int[][] board) {
        Queue<int[]> q = new ArrayDeque<>();
        q.offer(new int[]{0, startX, startY});
        q.offer(new int[]{1, startX, startY});

        while (!q.isEmpty()) {
            int[] now = q.poll();
            int direction = now[0];
            int x = now[1];
            int y = now[2];

            for (int d = 0; d < 4; d++) {
                int nx = x + deltas[d][0];
                int ny = y + deltas[d][1];
                int cost = 0;

                if (direction == d) { // 직선
                    cost = map[direction][x][y] + 100;
                } else { // 코너
                    cost = map[direction][x][y] + 600;
                }

                if (isIn(nx, ny)
                            && board[nx][ny] == 0 && cost <= map[d][nx][ny]) {
                    // 다음에 갈 곳이 내가 온방향으로 갈때의 값보다 작다면
                    map[d][nx][ny] = cost;
                    q.offer(new int[]{d, nx, ny});
                }
            }
        }
        int result = Integer.MAX_VALUE;
        for (int d = 0; d < 4; d++) {
            result = Math.min(result, map[d][N - 1][N - 1]);
        }
        return result;
    }

    private static boolean isIn(int nx, int ny) {
        return 0 <= nx && nx < N && 0 <= ny && ny < M;
    }
}
