package bj.g5;

import java.util.*;
import java.io.*;

public class BJ_G5_1916_최소비용구하기 {

    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer tokens;

    static int N, M;
    static ArrayList<ArrayList<Node>> graph = new ArrayList<>();
    static int INF = 987654321;

    public static void main(String[] args) throws IOException {

        N = Integer.parseInt(input.readLine());
        M = Integer.parseInt(input.readLine());


        for (int i = 0; i <= N; i++) {
            graph.add(new ArrayList<>());
        }

        int start, end, weight;

        for (int i = 0; i < M; i++) {
            tokens = new StringTokenizer(input.readLine());
            start = Integer.parseInt(tokens.nextToken());
            end = Integer.parseInt(tokens.nextToken());
            weight = Integer.parseInt(tokens.nextToken());

            graph.get(start).add(new Node(end, weight));
        }

        tokens = new StringTokenizer(input.readLine());
        start = Integer.parseInt(tokens.nextToken());
        end = Integer.parseInt(tokens.nextToken());

        int ans = Dijkstra(start, end);

        System.out.println(ans);


    }

    private static int Dijkstra(int start, int end) {
        boolean[] visited = new boolean[N + 1];
        int[] dist = new int[N + 1];
        PriorityQueue<Node> pq = new PriorityQueue<>();

        Arrays.fill(dist, INF);
        pq.offer(new Node(start, 0));
        dist[start] = 0;

        while (!pq.isEmpty()) {
            Node now = pq.poll();
            if (!visited[now.index]) {
                visited[now.index] = true;
            }

            if (now.index == end) {
                break;
            }

            for (Node next : graph.get(now.index)) {
                if (!visited[next.index] && dist[next.index] > now.weight + next.weight) {
                    dist[next.index] = now.weight + next.weight;
                    pq.offer(new Node(next.index, dist[next.index]));
                }

            }
        }
        return dist[end];
    }

    private static class Node implements Comparable<Node> {
        int index;
        int weight;

        Node(int index, int weight) {
            this.index = index;
            this.weight = weight;
        }

        @Override
        public int compareTo(Node o) {
            return this.weight - o.weight;
        }
    }
}