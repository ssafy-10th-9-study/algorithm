package bj.g4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/**
 * @author 김영욱
 * @git
 * @performance
 * @category #플로이드 워셜
 * @note 2초
 * 출발지가 정해져 있지 않고 최단 거리 사이클을 구해야 하기 떄문에 플로이드 워셜을 사용해야 한다.
 * V <= 400이고, 400^3 == 64,000,000 이므로
 * 2초안에 충분히 구할 수 있다.
 *
 * 주의점 1 INF값을 잘 정하자 : 거리 최댓값 10000( 10000 * 400 + 1 )
 * 주의점 2 사이클이므로 마지막에 갔다가 돌아오는 제일 작은 값을 정답으로 출력해야 한다.
 * 주의점 3 사이클이 없으면 -1을 출력해야 한다.
 * @see https://www.acmicpc.net/problem/1956
 * @since 2024. 07. 23
 */

public class BJ_G4_1956_운동 {

    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer tokens;

    static int V, E;
    static int INF = 40000001;
    static int[][] dist;
    static int ans;
    public static void main(String[] args) throws IOException {
        tokens = new StringTokenizer(input.readLine());

        V = Integer.parseInt(tokens.nextToken());
        E = Integer.parseInt(tokens.nextToken());

        ans = INF;

        dist = new int[V+1][V+1];

        for(int i=1; i<=V; i++) {
            for(int j=1; j<=V; j++) {
                if(i == j) dist[i][j] = 0;
                else dist[i][j] = INF;
            }
        }

        for(int i=1; i<=E; i++) {
            tokens = new StringTokenizer(input.readLine());
            int start = Integer.parseInt(tokens.nextToken());
            int end = Integer.parseInt(tokens.nextToken());
            int weight = Integer.parseInt(tokens.nextToken());

            dist[start][end] = weight;
        }

        for(int k=1; k<=V; k++) {
            for(int i=1; i<=V; i++) {
                for(int j=1; j<=V; j++) {
                    if(i == j) continue;
                    dist[i][j] = Math.min(dist[i][k] + dist[k][j] , dist[i][j]);
                }
            }
        }
        for(int i=1; i<V; i++) {
            for(int j=i+1; j<=V; j++) {
                if (dist[i][j] != INF && dist[j][i] != INF) {
                    ans = Math.min(dist[i][j] + dist[j][i], ans);
                }
            }
        }
        System.out.println(ans >= INF ? -1 : ans);
    }
}
