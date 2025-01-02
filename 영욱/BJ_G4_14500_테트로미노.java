package bj.g4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * @author 김영욱
 * @git
 * @performance 612ms
 * @category #백트래킹
 * @note 시간 : 2초
 * 폴리오미노란 크기가 1×1인 정사각형을 여러 개 이어서 붙인 도형이며, 다음과 같은 조건을 만족해야 한다.
 * 정사각형은 서로 겹치면 안 된다.
 * 도형은 모두 연결되어 있어야 한다.
 * 정사각형의 변끼리 연결되어 있어야 한다. 즉, 꼭짓점과 꼭짓점만 맞닿아 있으면 안 된다.
 * 정사각형 4개를 이어 붙인 폴리오미노는 테트로미노라고 하며, 다음과 같은 5가지가 있다.
 * 아름이는 크기가 N×M인 종이 위에 테트로미노 하나를 놓으려고 한다.
 * 종이는 1×1 크기의 칸으로 나누어져 있으며, 각각의 칸에는 정수가 하나 쓰여 있다.
 * 테트로미노 하나를 적절히 놓아서 테트로미노가 놓인 칸에 쓰여 있는 수들의 합을 최대로 하는 프로그램을 작성하시오.
 * 테트로미노는 반드시 한 정사각형이 정확히 하나의 칸을 포함하도록 놓아야 하며, 회전이나 대칭을 시켜도 된다.
 * <p>
 * 세로 크기 N과 가로 크기 M이 주어진다. (4 ≤ N, M ≤ 500)
 * <p>
 * 둘째 줄부터 N개의 줄에 종이에 쓰여 있는 수가 주어진다.
 * i번째 줄의 j번째 수는 위에서부터 i번째 칸, 왼쪽에서부터 j번째 칸에 쓰여 있는 수이다.
 * 입력으로 주어지는 수는 1,000을 넘지 않는 자연수이다.
 * <p>
 * 그니까 종이 위에 있는 숫자들을 4칸 연속으로 잇고, 이어지는 4칸의 가장 큰 합을 구하는 문제이다.
 * 각 칸마다 모든 방향의 모든 도형 모형을 탐색하여 최대 값을 찾을 경우
 * N(4) * M(500) * 5(모양) * 4(방향) = 40000번 밖에 안되니 완전탐색을 하여도 될 것 같다.
 * <p>
 * 근데 이 모양을 만드는 배열을 만드려니 너무 귀찮아서 다른 방향이 있나 고민해보았다.(당연히 이 방법이 제일 빠를 것)
 * DFS로 상하좌우를 가게 만들면 ㅗ모양을 제외한 나머지 모양들이 나왔다.
 * 이 때 중요한 것은 visited로 방문처리를 해주고, 다시 돌아오면 false처리를 해주는 것이다.
 * ㅗ일 때를 어떻게 할까 고민하다가( ㅗ모양 배열을 따로 만들어서 처리하면 되긴 함 )
 * 근데 뭔가 더 참신한 방법이 없을까 30분 고민하다가 인터넷에 검색해 보았다.
 * 역시 DFS방법이 주 풀이 방법이였고, 제자리에서 현 위치를 다시 호출하는 방법(자세히 말하자면 현 위치에서 다음으로 향할 곳의 값을 미리 더해주고,
 * 그 곳은 방문처리하여 다시 방문하지 않게끔 처리, 그럼 자연스레 오른쪽이나 왼쪽으로 꺾이게 되있다)이 ㅗ모양 처리 방법이였다.
 * 재귀의 이해도가 떨어져서 떠올리지 못했었는데 감탄하고 문제를 풀었다.
 * @see https://www.acmicpc.net/problem/14500
 * @since 2025. 01. 03
 */
public class BJ_G4_14500_테트로미노 {

    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer tokens;

    static int[][] map;
    static int N, M;

    static int[][] deltas = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    static boolean[][] visited;
    static int answer;


    public static void main(String[] args) throws IOException {
        tokens = new StringTokenizer(input.readLine());
        N = Integer.parseInt(tokens.nextToken());
        M = Integer.parseInt(tokens.nextToken());

        map = new int[N][M];
        visited = new boolean[N][M];

        for (int i = 0; i < N; i++) {
            tokens = new StringTokenizer(input.readLine());
            for (int j = 0; j < M; j++) {
                map[i][j] = Integer.parseInt(tokens.nextToken());
            }
        }
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                visited[i][j] = true;
                dfs(i, j, 0, map[i][j]);
                visited[i][j] = false;
            }
        }
        System.out.println(answer);
    }

    private static void dfs(int x, int y, int depth, int sum) {
//        기저 조건
        if (depth == 3) {
            answer = Math.max(answer, sum);
            return;
        }

        for(int d=0; d<4; d++) {
            int nx = x + deltas[d][0];
            int ny = y + deltas[d][1];
            if(isIn(nx, ny) && !visited[nx][ny]) {
                if(depth == 1) { // 2번째 칸이면 ㅗ 모양을 위해 현재 위치에서 재귀 한번 더 호출
                    visited[nx][ny] = true;
                    dfs(x, y, depth + 1, sum + map[nx][ny]);// 미리 앞에 나아갈 곳을 더해주고,
//                    방문 배열 처리를 해놨으니 다음 재귀에서는 저 곳을 접근할 일이 없다. 그럼 자연스레 오른쪽이나 왼쪽으로 꺾겠지
//                    그럼 ㅗ모양 완성
                    visited[nx][ny] = false;
                }
                visited[nx][ny] = true;
                dfs(nx, ny, depth + 1, sum + map[nx][ny]);
                visited[nx][ny] = false;
            }
        }
    }

    private static boolean isIn(int nx, int ny) {
        return 0 <= nx && nx < N && 0 <= ny && ny < M;
    }
}
