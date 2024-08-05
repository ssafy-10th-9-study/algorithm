package boj.gold5.BOJ_01647_도시분할계획;

/**

- @author 이병헌
- @since 7/30/24
- @see https://www.acmicpc.net/problem/1647
- @git https://github.com/Hunnibs
- @youtube
- @performance
- @category # Graph #
- @note

 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class BOJ_01647_prim {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        Graph graph = new Graph(N);

        for (int i = 0; i < M; i++){
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());

            graph.setGraph(a, b, c);
            graph.setGraph(b, a, c);
        }

        System.out.print(prim(N, graph));
    }

    private static long prim(int N, Graph graph){
        PriorityQueue<Info> pq = new PriorityQueue<>();
        boolean[] visited = new boolean[N + 1];
        pq.add(new Info(1,0));

        long sum = 0;
        int max = 0;
        while (!pq.isEmpty()) {
            Info cur = pq.poll();
            if (visited[cur.to]) continue;

            visited[cur.to] = true;
            sum += cur.weight;
            max = Math.max(max, cur.weight);

            List<Info> nextGroup = graph.getGraph(cur.to);
            for(Info next : nextGroup){
                if (visited[next.to]) continue;
                else pq.add(next);
            }
        }

        return sum - max;
    }

    private static class Graph{
        List<List<Info>> graph = new ArrayList<>();

        public Graph(int N){
            for(int i = 0; i <= N; i++){
                graph.add(new ArrayList<>());
            }
        }

        public void setGraph(int from, int to, int weight){
            graph.get(from).add(new Info(to, weight));
        }

        public List<Info> getGraph(int from){
            return graph.get(from);
        }
    }

    private static class Info implements Comparable<Info>{
        int to, weight;

        public Info(int to, int weight){
            this.to = to;
            this.weight = weight;
        }

        @Override
        public int compareTo(Info o) {
            return this.weight - o.weight;
        }
    }
}
