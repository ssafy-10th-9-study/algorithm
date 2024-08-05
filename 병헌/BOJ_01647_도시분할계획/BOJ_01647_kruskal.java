package boj.gold5.BOJ_01647_도시분할계획;

/**

- @author 이병헌
- @since 8/5/24
- @see https://www.acmicpc.net/problem/01647
- @git https://github.com/Hunnibs
- @youtube
- @performance
- @category # kruskal # union-find
- @note

 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class BOJ_01647_kruskal {
    private static int[] parent;

    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        List<Info> graph = new ArrayList<>();

        for (int i = 0; i < M; i++){
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());

            graph.add(new Info(a, b, c));
        }

        Collections.sort(graph);

        parent = new int[N+1];
        for (int i = 0; i <= N; i++) {
            parent[i] = i;
        }

        long answer = 0;
        int max = 0;
        for(Info info : graph){
            if (find(info.from) != find(info.to)){
                answer += info.weight;
                union(info.from, info.to);
                max = Math.max(max, info.weight);
            }
        }

        System.out.print(answer - max);
    }

    private static void union(int x, int y){
        x = find(x);
        y = find(y);

        if (x != y){
            if (x > y) {
                parent[y] = x;
            } else{
                parent[x] = y;
            }
        }
    }

    private static int find(int x){
        if (x == parent[x]){
            return x;
        }

        return parent[x] = find(parent[x]);
    }

    private static class Info implements Comparable<Info>{
        int from, to, weight;

        public Info(int from, int to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }

        @Override
        public int compareTo(Info o) {
            return Integer.compare(weight, o.weight);
        }
    }
}
