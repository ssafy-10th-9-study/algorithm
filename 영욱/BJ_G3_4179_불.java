package bj.g3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 @author 김영욱
 @since 2025. 01. 07
 @see https://www.acmicpc.net/problem/4179
 @git
 @performance
 @category #
 @note
 지훈이는 미로에서 일한대. 근데 미로에서 불이 났대
 지훈이와 불은 1초에 수직이나 수평(4방향)으로 이동한대
 지훈이가 미로의 가장자리(끝)에 도달하면 탈출할 수 있는거야
 지훈이가 탈출할 수 있다면 가장 빠른 초를 구해

 지훈이가 불이 도달하기 전에 탈출할 수 없다면 IMPOSSIBLE을 출력해라

 유의점
 1. 지훈이가 먼저 이동하고 불이 번지는 듯
 2. while(true)로 하나 묶고, 지훈이 큐에는 턴,x,y를 넣고 만약 턴이 하나 증가했다면 그 while문을 나와서
 불번지는 bfs문으로 가야할듯
 3. 턴으로 이동하기 때문에 지훈이가 먼저 이동하고 불이 번진다고 가정하였습니다.
  - 여기서 틀린 것 같다.52%

 */
public class BJ_G3_4179_불 {

    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer tokens;

    static char[][] map;
    static int N, M;

    static int[][] deltas = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    static ArrayList<int[]> fireCord = new ArrayList<>();


    public static void main(String[] args) throws IOException {
        tokens = new StringTokenizer(input.readLine());
        N = Integer.parseInt(tokens.nextToken());
        M = Integer.parseInt(tokens.nextToken());
        int startX = 0, startY = 0;

        map = new char[N][M];

        for(int i=0; i<N; i++) {
            String temp = input.readLine();
            map[i] = temp.toCharArray();
            for(int j=0; j<M; j++) {
                if(map[i][j] == 'J') {
                    startX = i;
                    startY = j;
                    map[i][j] = '.';
                }
                else if(map[i][j] == 'F') fireCord.add(new int[] {i, j});
            }
        }

        int answer = bfs(startX, startY);
        if(answer == -1) System.out.println("IMPOSSIBLE");
        else System.out.println(answer);
    }

    private static int bfs(int startX, int startY) {
        boolean[][] visited = new boolean[N][M];
        Queue<int[]> manQ = new ArrayDeque<>();
        Queue<int[]> manTempQ = new ArrayDeque<>();
        Queue<int[]> fireQ = new ArrayDeque<>();
        Queue<int[]> fireTempQ = new ArrayDeque<>();

        for(int[] fire:fireCord) fireQ.offer(new int[] {fire[0], fire[1]});

        int startTurn = 1;

        manQ.offer(new int[] {startTurn, startX, startY});
        visited[startX][startY] = true;

        while(true) {
            while(!manQ.isEmpty()) { // 지훈이 이동하는 함수
                int[] now = manQ.poll();
                int turn = now[0];
                int x = now[1];
                int y = now[2];

                if(map[x][y] == 'F') continue; // 지훈이 현 위치에 불이 번졌다면 지금 위치는 불가능한 위치니까
                if(isEscape(x,y)) return turn;

                for(int d=0; d<4; d++) {
                    int nx = x + deltas[d][0];
                    int ny = y + deltas[d][1];
                    if(isIn(nx,ny) && !visited[nx][ny] && canGo(nx, ny)) {
                        visited[nx][ny] = true;
                        manTempQ.offer(new int[] {turn+1, nx, ny});
                    }
                }
            }
            while(!fireQ.isEmpty()) { // 불 번지는 함수
                int[] now = fireQ.poll();
                int x = now[0];
                int y = now[1];

                for(int d=0; d<4; d++) {
                    int nx = x + deltas[d][0];
                    int ny = y + deltas[d][1];
                    if(isIn(nx,ny) && canGo(nx, ny)) {
                        map[nx][ny] = 'F';
                        fireTempQ.offer(new int[] {nx, ny});
                    }
                }
            }
            if(manTempQ.isEmpty()) {
                return -1;
            }
            while(!manTempQ.isEmpty()) manQ.offer(manTempQ.poll()); // temp에 옮겨놓은거 다시 옮겨
            while(!fireTempQ.isEmpty()) fireQ.offer(fireTempQ.poll()); // temp에 옮겨놓은거 다시 옮겨

        }
    }

    private static boolean isIn (int nx, int ny) {
        return 0 <= nx && nx < N && 0 <= ny && ny < M;
    }
    private static boolean isEscape(int nx, int ny) {
        return nx == N - 1 || nx == 0 || ny == M - 1 || ny == 0;
    }

    private static boolean canGo(int nx, int ny) {
        return map[nx][ny] == '.';
    }
}
