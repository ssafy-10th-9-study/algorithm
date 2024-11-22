package bj.g3;

import java.io.*;
import java.util.*;
/**
 * @author 김영욱
 * @git
 * @performance 396ms
 * @category # dfs + dp
 * @note 시간 : 2초
 * 판다는 욕심쟁이라 지금 먹은 곳보다 대나무가 더 많은 곳으로 움직인다.
 * 이 문제는 dp로 현 위치에서 할 일만 잘 정해주면 풀 수 있다.
 * 1. 현 위치 dp값 + 1이 다음 위치 dp값보다 크고, 내 현위치 대나무 값보다 다음 위치 대나무 값이 더 크다면 큐에 넣고
 * dp 값 초기화
 * 이 문제는 dp값으로 갈 곳을 판별하기 때문에 별도의 visited배열은 필요 없다( 오히려 있으면 정답을 구할 수 없음 )
 * 1안으로 하니까 34퍼에서 시간초과 났음...
 * 2. 생각해보니 memo라는 배열을 따로 만들고 그 자리에 이미 값이 있으면 +1한 값을 저장하면 될 것 같았음
 * 그리고 다른 방향은 똑같이 bfs를 돌리는 방식으로..
 * 2안은 2퍼에서 컷났음..
 * 3. BFS 큐에 스택을 넣어서 경로를 저장하는 방법도 생각해 봤는데 생각해보니까
 * 스택을 쓸거면 그냥 dfs를 쓰는게 훨씬 효율적이라는 것이 떠올라버림
 * 고집을 꺾고 dfs를 받아들이기로 했음. dfs로 구현하니 여태 왔던 경로에 이쁘게 갈 수 있는 최대 경로가 저장되었음
 * ex) 9 11 15면 3 2 1로 현 위치에서 살 수 있는 최대 경로가 이쁘게 정리되어서 1,2안보다 더 경로 탐색을 할 필요가 없음
 *
 * 재귀랑 dfs 이해도가 너무 많이 줄은 것 같아서 의식적으로 그 유형의 문제를 더 풀어야 겠다
 * @see https://www.acmicpc.net/problem/1937
 * @since 2024. 11. 22
 */

public class BJ_G3_1937_욕심쟁이판다 {

    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer tokens;

    static int N;
    static int[][] map, memo;
    static int deltas[][] = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    public static void main(String[] args) throws IOException {
        N = Integer.parseInt(input.readLine());
        map = new int[N][N];
        memo = new int[N][N];

        for (int i = 0; i < N; i++) {
            tokens = new StringTokenizer(input.readLine());
            for (int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(tokens.nextToken());
            }
        }
        int answer = 0;

        for(int i=0; i<N; i++) {
            for(int j=0; j<N; j++) {
                answer = Math.max(answer, bamboo(i,j));
            }
        }
        for(int i=0; i<N; i++) {
            for(int j=0; j<N; j++) {
                System.out.print(memo[i][j] + " ");
            }
            System.out.println();
        }

        System.out.println(answer);
    }

    private static int bamboo(int x, int y) {
        // 기저 조건
        if(memo[x][y] != 0) return memo[x][y];

        memo[x][y] = 1;

        for(int d=0; d<4; d++) {
            int nx = x + deltas[d][0];
            int ny = y + deltas[d][1];

            if(isIn(nx, ny) && map[nx][ny] > map[x][y] ) {
                memo[x][y] = Math.max(memo[x][y] , bamboo(nx, ny) + 1);
            }
        }
        return memo[x][y];

    }

    private static boolean isIn(int nx, int ny) {
        return 0 <= nx && nx < N && 0 <= ny && ny < N;
    }
}
