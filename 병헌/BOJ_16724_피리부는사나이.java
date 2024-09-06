package boj.gold5;

/**

 - @author 이병헌
 - @since 8/25/24
 - @see https://www.acmicpc.net/problem/16724
 - @git https://github.com/Hunnibs
 - @youtube
 - @performance  1sec 256MB
 - @category # BFS
 - @note
 1_000_000 size array
 시작점마다 DFS로 체크, Visited 활용 중복체크 제거
 순환 시점에 반례가 발생, BFS를 이용한 역추적 방식 활용

 3 4
 DLLL
 DRLU
 RRRU

 3 4
 DDDD
 LLLL
 RRRR
 */

import java.util.*;
import java.io.*;

public class BOJ_16724_피리부는사나이 {
    private static int[][] delta = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        char[][] map = new char[N][M];
        for(int i = 0; i < N; i++){
            String s = br.readLine();
            for(int j = 0; j < M; j++){
                map[i][j] = s.charAt(j);
            }
        }

        boolean[][] visited = new boolean[N][M];
        int answer = 0;
        for(int i = 0; i < N; i++){
            for (int j = 0; j < M; j++) {
                if (visited[i][j]){
                    continue;
                } else{
                    bfs(N, M, i, j, map, visited);
                    answer++;
                }
            }
        }

        System.out.print(answer);
    }

    private static void bfs(int N, int M, int r, int c, char[][] map, boolean[][] visited){
        Queue<Pos> queue = new ArrayDeque<>();
        queue.add(new Pos(r, c));
        visited[r][c] = true;

        while(!queue.isEmpty()){
            Pos cur = queue.poll();
            int nr = cur.r, nc = cur.c;

            switch(map[cur.r][cur.c]){
                case 'U':
                    nr -= 1;
                    break;
                case 'R':
                    nc += 1;
                    break;
                case 'D':
                    nr += 1;
                    break;
                case 'L':
                    nc -= 1;
                    break;
            }

            if (isIn(N, M, nr, nc) && !visited[nr][nc]){
                queue.add(new Pos(nr, nc));
                visited[nr][nc] = true;
            }

            for (int i = 0; i < 4; i++){
                nr = cur.r + delta[i][0];
                nc = cur.c + delta[i][1];

                if (isIn(N, M, nr, nc) && !visited[nr][nc]){
                    if (check(i, nr, nc, map)) {
                        queue.add(new Pos(nr, nc));
                        visited[nr][nc] = true;
                    }
                }
            }
        }
    }

    /*
        상하좌우 기준 역전으로 생각
        예를 들어, 상 방향이면 D를 가지고 있어야 된다.
     */
    private static boolean check(int i, int nr, int nc, char[][] map){
        switch(i){
            case 0:  // 상
                if (map[nr][nc] == 'D') return true;
                else return false;
            case 1:
                if (map[nr][nc] == 'L') return true;
                else return false;
            case 2:
                if (map[nr][nc] == 'U') return true;
                else return false;
            case 3:
                if (map[nr][nc] == 'R') return true;
                else return false;
        }

        return false;
    }

    private static boolean isIn(int N, int M, int r, int c){
        return r >= 0 && r < N && c >= 0 && c < M;
    }

    private static class Pos{
        int r, c;

        public Pos(int r, int c) {
            this.r = r;
            this.c = c;
        }
    }
}

