package bj.g4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * @author 김영욱
 * @git
 * @performance
 * @category #
 * @note N개의 도시가 있다( N<=100 )
 * M개의 도시와 도시를 잇는 도로가 있다
 * 각 도시마다 각 도시에 도달할 수 있는 최단 거리를 구해라
 * <p>
 * 각 노드마다 다른 모든 노드에 대한 최단 거리를 구해야하고, N이 100밖에 안되므로 N^3의 시간 복잡도를 가진 플로이드 워셜이라도
 * 충분히 사용할 수 있다.
 * @see https://www.acmicpc.net/problem/11404
 * @since 2024. 07. 13
 */

public class BJ_G4_11404_플로이드 {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer tokens;
    static StringBuilder builder = new StringBuilder();

    static int INF = 100000 * 99 + 1; // 최대 비용이 10만이지만 10만을 100번거쳐간게 최단거리일 수도 있기 때문에 값을 100,000 * (100-1 (자기자신빼)) + 1

    static int N, M;
    static int dist[][];

    public static void main(String[] args) throws IOException {
        N = Integer.parseInt(input.readLine());
        M = Integer.parseInt(input.readLine());

//        플로이드 워셜에서 한 노드가 각 지점으로 가는 최단 거리를 구해야 하므로
//        2차원 배열로 구현
        dist = new int[N + 1][N + 1];
//        모든 거리는 최대로 초기화
        for (int i = 1; i <= N; i++) {
            Arrays.fill(dist[i], INF);
        }
//        내가 내 자신을 가는 비용은 0으로 초기화
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                if (i == j) {
                    dist[i][i] = 0;
                }
            }
        }
//        간선 정보를 받아준다
        for (int i = 0; i < M; i++) {
            tokens = new StringTokenizer(input.readLine());
            int start = Integer.parseInt(tokens.nextToken());
            int end = Integer.parseInt(tokens.nextToken());
            int cost = Integer.parseInt(tokens.nextToken());

//            도시로 가는 경로가 하나 이상이기 때문에 더 작은 값을 넣어놓는다.
            dist[start][end] = Math.min(dist[start][end], cost);
        }
        for (int k = 1; k <= N; k++) {
            for (int i = 1; i <= N; i++) {
                for (int j = 1; j <= N; j++) {
//                    현재 AtoB의 거리보다 경유지를 거쳐가는게 더 짧다면
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

        System.out.println(builder);

    }
}
