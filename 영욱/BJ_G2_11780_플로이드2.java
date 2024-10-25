package bj.g2;

import java.io.*;
import java.util.*;

/**
 * @author 김영욱
 * @git
 * @performance 556ms
 * @category # 플로이드 워셜 + 경로 추적
 * @note N개의 도시가 있다( N<=100 )
 * M개의 도시와 도시를 잇는 도로가 있다
 * 각 도시마다 각 도시에 도달할 수 있는 최단 거리를 구해라
 * 시작 도시와 도착 도시를 연결하는 노선은 하나가 아닐 수 있다.
 * <p>
 * 각 노드마다 다른 모든 노드에 대한 최단 거리를 구해야하고, N이 100밖에 안되므로 N^3의 시간 복잡도를 가진 플로이드 워셜이라도
 * 충분히 사용할 수 있다.
 * <p>
 * 0 2 3 1 4
 * 12 0 15 2 5
 * 8 5 0 1 1
 * 10 7 13 0 3
 * 7 4 10 6 0
 * 0
 * 2 1 2
 * 2 1 3
 * 2 1 4
 * 3 1 3 5
 * 4 2 4 5 1
 * 0
 * 5 2 4 5 1 3
 * 2 2 4
 * 3 2 4 5
 * 2 3 1
 * 3 3 5 2
 * 0
 * 2 3 4
 * 2 3 5
 * 3 4 5 1
 * 3 4 5 2
 * 4 4 5 1 3
 * 0
 * 2 4 5
 * 2 5 1
 * 2 5 2
 * 3 5 1 3
 * 3 5 2 4
 * 0
 * <p>
 * 그냥 플로이드와 다른 점은 출력이 이런식으로 어지러운데 블로그를 참고하고 계속 문제를 보니 이해되었다.
 * i == j일 경우 0을 출력하고
 * 1 -> 2로 가는 경로일 경우 처음에는 경로 사이즈( 거치는 정점의 개수 )
 * 그리고 경로를 출력하는 거다.
 * <p>
 * 스택을 사용해서 백트래킹을 하는 방식도 있지만(이게 좀 더 빠르다) List를 사용해서 경로를 저장하는게 더 직관적이고
 * 이해하기 쉬워서 List를 사용하는 방법을 참고하였다.
 * https://loosie.tistory.com/603
 * @see https://www.acmicpc.net/problem/11780
 * @since 2024. 07. 27
 */
public class BJ_G2_11780_플로이드2 {

    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer tokens;
    static StringBuilder builder = new StringBuilder();

    static int N, M;
    static int INF = 100000 * 99 + 1; // 최대 비용이 10만이지만 10만을 100번거쳐간게 최단거리일 수도 있기 때문에 값을 100,000 * (100-1 (자기자신빼)) + 1


    static int[][] dist;
    static List<Integer>[][] route;

    public static void main(String[] args) throws IOException {

        N = Integer.parseInt(input.readLine());
        M = Integer.parseInt(input.readLine());

        dist = new int[N + 1][N + 1];
        route = new ArrayList[N + 1][N + 1];

        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                route[i][j] = new ArrayList<>();
                if (i == j) dist[i][j] = 0;
                else dist[i][j] = INF;
            }
        }

        int start, end, weight;

        for (int i = 0; i < M; i++) {
            tokens = new StringTokenizer(input.readLine());
            start = Integer.parseInt(tokens.nextToken());
            end = Integer.parseInt(tokens.nextToken());
            weight = Integer.parseInt(tokens.nextToken());

            dist[start][end] = Math.min(dist[start][end], weight);
        }

        for (int k = 1; k <= N; k++) {
            for (int i = 1; i <= N; i++) {
                for (int j = 1; j <= N; j++) {
                    if (dist[i][k] + dist[k][j] < dist[i][j]) {// 경로 초기화가 일어날 경우
                        dist[i][j] = dist[i][k] + dist[k][j];
                        saveRoute(i, k, j);
                    }
                    dist[i][j] = Math.min(dist[i][k] + dist[k][j], dist[i][j]);
                }
            }
        }

        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                if (dist[i][j] == INF) builder.append("0 ");
                else builder.append(dist[i][j]).append(" ");
            }
            builder.append("\n");
        }

        printRoute();

        System.out.println(builder);


    }

    private static void printRoute() {
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {

                if (i == j || dist[i][j] == INF) {
                    builder.append("0\n");
                    continue;
                }
//                StringBuilder가 + 대신 append를 권장하는 이유
//                    성능: StringBuilder는 내부적으로 문자열을 변경할 때마다 새로운 문자열 객체를 생성하지 않기 때문에,
//                    append 메서드를 사용하면 메모리 효율이 더 좋아집니다.
//                    + 연산자를 사용할 경우, 매번 새로운 문자열 객체가 생성되므로 성능이 저하될 수 있습니다.
                builder.append(route[i][j].size() + 2).append(" ").append(i).append(" "); // 시작, 끝은 무조건 있으니 +2
                for (int num : route[i][j]) {
                    builder.append(num).append(" ");
                }
                builder.append(j).append("\n");
            }
        }
    }

    private static void saveRoute(int i, int k, int j) {// i부터 j까지 k를 거쳐 가는게 더 빠른 경우
        route[i][j].clear();// 경로 초기화
        for (int num : route[i][k]) {// i부터 k까지 가는 경로를 저장
            route[i][j].add(num);
        }
        route[i][j].add(k);// k 저장
        for (int num : route[k][j]) {// k부터 j까지 가는 경로를 저장
            route[i][j].add(num);
        }
    }
}
