package programmers;

import java.util.*;
import java.io.*;

public class PG_LV2_리코쳇로봇 {
    static int[][] deltas = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    static int[] startPos;
    static int[] endPos;
    static int[][] map;
    static int N, M;
    static final int GOAL = -100;

    static String[] board = {".D.R", "....", ".G..", "...D"};
    public static void main(String[] args) {
        startPos = new int[2];
        endPos = new int[2];
        N = board.length;
        M = board[0].length();
        map = new int[N][M];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (board[i].charAt(j) == 'R') {
                    startPos[0] = i;
                    startPos[1] = j;
                } else if (board[i].charAt(j) == 'G') {
                    endPos[0] = i;
                    endPos[1] = j;
                    map[i][j] = GOAL;
                }
                if (board[i].charAt(j) == 'D') {
                    map[i][j] = -1;
                }
            }
        }

        int answer = bfs();
        System.out.println(answer);
    }
    private static int bfs() {
        Queue<int[]> q = new ArrayDeque<>();

        q.offer(new int[]{startPos[0], startPos[1], 1});

        while (!q.isEmpty()) {
            int[] now = q.poll();
            int x = now[0];
            int y = now[1];
            int currentMove = now[2];

            for (int d = 0; d < 4; d++) {
                int[] next = move(x, y, deltas[d][0], deltas[d][1]);
                if(endPos[0] == next[0] && endPos[1] == next[1]) return currentMove;
                if(map[next[0]][next[1]] == 0 || map[next[0]][next[1]] > currentMove ) {
                    q.offer(new int[]{next[0], next[1], currentMove + 1});
                    map[next[0]][next[1]] = currentMove;
                }
            }
        }
        return -1;
    }

    private static int[] move(int sx, int sy, int dx, int dy) {
        int x = sx;
        int y = sy;
        while (true) {
            int nx = x + dx;
            int ny = y + dy;

            if (isIn(nx, ny)) {
                x = nx;
                y = ny;
            } else break;
        }
        int[] result = {x, y};
        return result;
    }

    private static boolean isIn(int x, int y) {
        return 0 <= x && x < N && 0 <= y && y < M && map[x][y] != -1;
    }
}
