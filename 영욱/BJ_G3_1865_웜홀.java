package bj.g3;

import java.util.*;
import java.io.*;

/**
 * @author 김영욱
 * @git
 * @performance
 * @category #
 * @note 월드나라에는 N개의 지점이 있고 N개의 지점 사이에는 M개의 도로와 W개의 웜홀이 있다.
 * (단 도로는 방향이 없으며 웜홀은 방향이 있다.) 웜홀은 시작 위치에서 도착 위치로 가는 하나의 경로인데,
 * 특이하게도 도착을 하게 되면 시작을 하였을 때보다 시간이 뒤로 가게 된다. 웜홀 내에서는 시계가 거꾸로 간다고 생각하여도 좋다.
 * ( 도로는 양방향이고 양수, 웜홀은 단방향이고 음수 -> 벨만포드 )
 *
 * 시간 여행을 매우 좋아하는 백준이는 한 가지 궁금증에 빠졌다.
 * 한 지점에서 출발을 하여서 시간여행을 하기 시작하여 다시 출발을 하였던 위치로 돌아왔을 때,
 * 출발을 하였을 때보다 시간이 되돌아가 있는 경우가 있는지 없는지 궁금해졌다.
 *
 * 첫 번째 줄에는 테스트케이스의 개수 TC(1 ≤ TC ≤ 5)가 주어진다.
 * 첫 번째 줄에는 지점의 수 N(1 ≤ N ≤ 500), 도로의 개수 M(1 ≤ M ≤ 2500), 웜홀의 개수 W(1 ≤ W ≤ 200)이 주어진다.
 * 두 번째 줄부터 M+1번째 줄에 도로의 정보가 주어지는데 각 도로의 정보는 S, E, T 세 정수로 주어진다.
 * S와 E는 연결된 지점의 번호, T는 이 도로를 통해 이동하는데 걸리는 시간을 의미한다.
 * 그리고 M+2번째 줄부터 M+W+1번째 줄까지 웜홀의 정보가 S, E, T 세 정수로 주어지는데 S는 시작 지점, E는 도착 지점, T는 줄어드는 시간을 의미한다. T는 10,000보다 작거나 같은 자연수 또는 0이다.
 *
 * 두 지점을 연결하는 도로가 한 개보다 많을 수도 있다. 지점의 번호는 1부터 N까지 자연수로 중복 없이 매겨져 있다.
 *
 * 일단 두 지점을 연결하는 도로가 한 개보다 많을 수 있기 때문에 최단 거리를 구해줘야해.
 * 그리고 웜홀도 여러개일 수 있기 때문에, 그리고 웜홀이 바로 시작위치로 간다는 보장이 없다.
 * 그리고 한 지점이라는 말을 보았을 때 모든 지점을 출발 위치로 잡아서 다시 출발 지점으로 돌아갔을 때 시간이 줄어들었는지를 계산하는거다.
 *
 * @see https://www.acmicpc.net/problem/1865
 * @since 2024. 07. 11
 */
public class BJ_G3_1865_웜홀 {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder builder = new StringBuilder();
    static StringTokenizer tokens;

    static int[] dist;
    static final int INF = 1000000000;
    static List<List<Edge>> edges;

    static int N, M, W;

    public static void main(String[] args) throws IOException {
        int T = Integer.parseInt(input.readLine());

        for (int t = 0; t < T; t++) {
            tokens = new StringTokenizer(input.readLine());
            N = Integer.parseInt(tokens.nextToken());
            M = Integer.parseInt(tokens.nextToken());
            W = Integer.parseInt(tokens.nextToken());
            edges = new ArrayList<>();
            for(int i=0; i<=N; i++) {
                edges.add(new ArrayList<>());
            }
            // 도로
            for (int i = 0; i < M; i++) {
                tokens = new StringTokenizer(input.readLine());
                int start = Integer.parseInt(tokens.nextToken());
                int end = Integer.parseInt(tokens.nextToken());
                int weight = Integer.parseInt(tokens.nextToken());

                edges.get(start).add(new Edge(end, weight));
                edges.get(end).add(new Edge(start, weight));
            }
            // 웜홀
            for(int i=0; i<W; i++) {
                tokens = new StringTokenizer(input.readLine());
                int start = Integer.parseInt(tokens.nextToken());
                int end = Integer.parseInt(tokens.nextToken());
                int weight = Integer.parseInt(tokens.nextToken());

                edges.get(start).add(new Edge(end, -weight));
            }

            boolean ans = false;
//        모든 정점을 출발점으로
            for(int i=1; i<=N; i++) {
                if(bellmanFord(i)) {
                    ans = true;
                    builder.append("YES\n");
                    break;
                }
            }
            if(!ans) {
                builder.append("NO\n");
            }
        }
        System.out.println(builder);
    }

    private static boolean bellmanFord(int start) {
        dist = new int[N+1];
        Arrays.fill(dist, INF);
        dist[start] = 0;
//        최단 거리가 갱신 됐는지 확인하는 flag
        boolean update = false;

//        벨만 포드는 최단 거리를 구할 때 N-1을 거친다고 가정을 세우고 시작함
//        만약 이 이상의 경로를 거친다면 음수 사이클이 있다고 판단한다.
        for(int i=1; i<N; i++) {
            update = false;
//            3중 for문 같지만 그냥 해당하는 노드에서 출발하는 간선을 찾는거라 보면 됨
//            즉 모든 간선을 도는 것 뿐임
            for(int j=1; j<=N; j++) {
//                j번 노드에서 출발하는 모든 간선을 탐색
                for(Edge edge: edges.get(j)) {
                    if(dist[j] != INF && dist[edge.end] > dist[j] + edge.weight) {
                        update = true;
                        dist[edge.end] = dist[j] + edge.weight;
                    }
                }
            }
            // 모든 노드를 돌았을 때 더 이상 최단거리 초기화가 일어나지 않았을 경우 반복문을 종료.
            if (!update) {
                break;
            }
        }
//        N-1번을 돌았는데도 계속 최단 거리가 갱신된다면 한 번 더 최단 거리를 찾고
//        N번을 돌렸는데 최단 거리가 갱신된다면 이는 음수 사이클이 있다고 간주한다.
        if(update) {
            for(int i=1; i<=N; i++) {
                for(Edge edge: edges.get(i)) {
                    if(dist[i] != INF && dist[edge.end] > dist[i] + edge.weight) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


    private static class Edge {
        int end;
        int weight;

        public Edge(int end, int weight) {
            this.end = end;
            this.weight = weight;
        }
    }
}
