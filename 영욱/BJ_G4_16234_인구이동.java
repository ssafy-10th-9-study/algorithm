package bj.g4;

import java.io.*;
import java.util.*;

/**
 * @author 김영욱
 * @git
 * @performance
 * @category #
 * @note N*N 나라들의 인구가 주어진다.
 * 국경에 맞다아 있는 나라의 인구수 차이가 L<=X<=R 이라면 국경을 개방한다.
 * 이렇게 국경이 개방 되어 있는 나라들을 연합이라고 했을 때, (연합 인구수 / 연합 나라 수 )의 만큼 인구가 나눠진다( 인구 이동 )
 * 인구 이동이 몇일동안 이루어 지는가?
 *
 * 특별한 점 1 국경을 찾는 bfs메서드(openBorder)에서 새로운 Queue를 생성하여 연결되어 있는 좌표를 담아둔 것( 시간 단축 )
 * 특별한 점 2 openBorder에서 사용할 visited배열을 static으로 빼서 main문에서도 방문체크를 해준 것( 시간 단축 )
 * 특별한 점 3 openBorder에서 방문만 한 것이 아닌, 연합이라고 확정할 때만 방문 체크를 해주는 것( 반례 )
 * ex) 4 10 50
 * 10 100 20 90
 * 80 100 60 70
 * 70 20 30 40
 * 50 20 100 10
 * 출처: https://mygumi.tistory.com/338 [마이구미의 HelloWorld:티스토리]
 * @see https://www.acmicpc.net/problem/16234
 * @since 2024. 07. 24
 */

public class BJ_G4_16234_인구이동 {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer tokens;

    static int L, R, N;

    static int[][] contries;
    static
    boolean[][] visited;
    static boolean isChange;
    static int[][] unions;
    static int[][] deltas = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};


    public static void main(String[] args) throws IOException {

        tokens = new StringTokenizer(input.readLine());

        N = Integer.parseInt(tokens.nextToken());
        L = Integer.parseInt(tokens.nextToken());
        R = Integer.parseInt(tokens.nextToken());

        contries = new int[N][N];
        unions = new int[N][N];

        for (int i = 0; i < N; i++) {
            tokens = new StringTokenizer(input.readLine());
            for (int j = 0; j < N; j++) {
                contries[i][j] = Integer.parseInt(tokens.nextToken());
                unions[i][j] = -1;
            }
        }
        int ans = 0;
        isChange = true;
        while(isChange) {
            isChange = false;
            visited = new boolean[N][N];
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if (!visited[i][j]) {
                        openBorder(i, j);// 연합 찾아
                    }
                }
            }
            if(isChange) {
                for (int i = 0; i < N; i++) {
                    for (int j = 0; j < N; j++) {
                        if (unions[i][j] > -1) { // 연합의 인구 수가 메모가 되었다면
                            contries[i][j] = unions[i][j]; // 복사
                            unions[i][j] = -1; // 다시 초기화
                        }
                    }
                }
                ans++; // 정답 늘려줘
            }
        }
        System.out.println(ans);
    }

    private static void openBorder(int startX, int startY) {
        int unionLen = 1;
        int unionSum = contries[startX][startY];
        Queue<int[]> unionCord = new ArrayDeque<>();
        Queue<int[]> q = new ArrayDeque<>();


        unionCord.offer(new int[]{startX, startY});
        q.offer(new int[]{startX, startY});
        visited[startX][startY] = true;

        while (!q.isEmpty()) {
            int[] now = q.poll();
            int x = now[0];
            int y = now[1];

            for (int i = 0; i < 4; i++) {
                int nx = x + deltas[i][0];
                int ny = y + deltas[i][1];

                if (isIn(nx, ny) && !visited[nx][ny]) {
                    int diff = Math.abs(contries[x][y] - contries[nx][ny]);
                    if (L <= diff && diff <= R) { // 연합이라면
                        visited[nx][ny] = true;
                        q.offer(new int[]{nx, ny});
                        unionCord.offer(new int[]{nx, ny});// 연합 좌표큐에 추가
                        isChange = true; // 연합 찾았다!
                        unionLen++;// 연합 길이 늘어나고
                        unionSum += contries[nx][ny];// 연합 총 인구수 추가
                    }
                }
            }
        }

        if (isChange) { // 연합 구성 완료
            int newPeopleCnt = unionSum / unionLen;
            while (!unionCord.isEmpty()) {
                int[] now = unionCord.poll();
                unions[now[0]][now[1]] = newPeopleCnt; // 메모에 바뀐 연합 인구 수 추가
            }
        }
    }

    private static boolean isIn(int x, int y) {
        return 0 <= x && x < N && 0 <= y && y < N;
    }

}
