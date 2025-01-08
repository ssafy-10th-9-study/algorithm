package bj.g3;

/**
 * @author 김영욱
 * @git
 * @performance 216ms
 * @category #
 * @note 시간 : 1초
 * 5 <= N, M <= 100
 * 치즈가 정사각형으로 모눈 종이 위에 놓여있고, 4변 중 2변 이상 외부 공기에 노출되어 있다면 치즈가 녹는다.( 모눈 종이 가장자리는 항상 비어있다 가정)
 * 치즈가 모두 녹아 없어지는 시간을 구해라
 * 주의점
 * 치즈가 '외부 공기'에 노출 되어야 녹는다.
 * 0 0 0 0 0 0 0 0 0
 * 0 0 0 0 0 0 0 0 0
 * 0 1 1 0 0 0 1 1 0
 * 0 1 0 1 1 1 0 1 0
 * 0 1 0 0 1 0 0 1 0
 * 0 1 0 1 1 1 0 1 0
 * 0 1 1 0 0 0 1 1 0
 * 0 0 0 0 0 0 0 0 0
 * 이 그림의 경우 어떻게 녹느냐
 * 녹는 치즈가 C
 * 내부 공기가 N
 * 0 0 0 0 0 0 0 0 0
 * 0 0 0 0 0 0 0 0 0
 * 0 C C 0 0 0 C C 0
 * 0 1 N 1 1 1 N 1 0
 * 0 1 N N 1 N N 1 0
 * 0 1 N 1 1 1 N 1 0
 * 0 C C 0 0 0 C C 0
 * 0 0 0 0 0 0 0 0 0
 * <p>
 * 이걸 어떻게 판별한담...흠...
 * 오 외부 공기에서 bfs로 미리 외부 공기인지 판별하는 거임
 * 그래서 외부 공기랑 2개 이상 닿는 놈들을 지우면 되겠다!
 * @see https://www.acmicpc.net/problem/2638
 * @since 2025. 01. 08
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class BJ_G3_2638_치즈 {

    static int N, M;
    static int[][] map;
    static int[][] deltas = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer tokens;

    public static void main(String[] args) throws IOException {
        tokens = new StringTokenizer(input.readLine());
        N = Integer.parseInt(tokens.nextToken());
        M = Integer.parseInt(tokens.nextToken());

        map = new int[N][M];

        Queue<int[]> cQ = new ArrayDeque<>();

        for (int i = 0; i < N; i++) {
            tokens = new StringTokenizer(input.readLine());
            for (int j = 0; j < M; j++) {
                map[i][j] = Integer.parseInt(tokens.nextToken());
                if (map[i][j] == 1) cQ.offer(new int[]{i, j});
            }
        }

        System.out.println(solution(cQ));

    }

    private static int solution(Queue<int[]> cQ) {
        int answer = 0;
        Queue<int[]> airQ = new ArrayDeque<>();
        while (!cQ.isEmpty()) {
            boolean[][] airVisited = new boolean[N][M];
            airQ.offer(new int[]{0, 0});
            airVisited[0][0] = true;
            while (!airQ.isEmpty()) { // 공기 큐 돌려
                int[] now = airQ.poll();
                int x = now[0];
                int y = now[1];

                for (int d = 0; d < 4; d++) {
                    int nx = x + deltas[d][0];
                    int ny = y + deltas[d][1];

                    if (isIn(nx, ny) && !airVisited[nx][ny] && map[nx][ny] == 0) {
                        airQ.offer(new int[]{nx, ny});
                        airVisited[nx][ny] = true;
                    }
                }
            }
            int cQSize = cQ.size();
            while (cQSize-- > 0) { // 지금 턴에 있는 치즈만큼
                int[] now = cQ.poll();
                int x = now[0];
                int y = now[1];

                int count = 0;

                for (int d = 0; d < 4; d++) {
                    int nx = x + deltas[d][0];
                    int ny = y + deltas[d][1];

                    if (isIn(nx, ny) && airVisited[nx][ny]) count++; // 외부 공기 접촉 횟수
                }
                if (count < 2) cQ.offer(new int[]{x, y}); // 외부 공기 접촉이 2미만이면
                else map[x][y] = 0; // 2 이상이면 맵에서 치즈 지워
            }
            answer++;
        }
        return answer;
    }

    private static boolean isIn(int nx, int ny) {
        return 0 <= nx && nx < N && 0 <= ny && ny < M;
    }
}
