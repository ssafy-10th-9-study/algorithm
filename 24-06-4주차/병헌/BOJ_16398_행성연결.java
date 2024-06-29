package boj.gold4;

import java.util.*;
import java.io.*;

/**

 - @author 이병헌
 - @since 6/24/2024
 - @see https://www.acmicpc.net/problem/
 - @git https://github.com/Hunnibs
 - @youtube
 - @performance
 - @category #
 - @note */

public class BOJ_16398 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int N = Integer.parseInt(br.readLine());
        List<List<Info>> cost = new ArrayList<>();
        for(int i = 0; i < N; i++){
            cost.add(new ArrayList<>());
        }

        for(int i = 0; i < N; i++){
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j < N; j++){
                if (i == j) {
                    st.nextToken();
                    continue;
                }
                cost.get(i).add(new Info(j, Integer.parseInt(st.nextToken())));
            }
        }

        System.out.print(prim(cost));
    }

    private static long prim(List<List<Info>> cost){
        PriorityQueue<Info> pq = new PriorityQueue<>();
        boolean[] visited = new boolean[cost.size()];
        pq.add(new Info(0, 0));

        long sum = 0;
        while(!pq.isEmpty()){
            Info cur = pq.poll();
            if(visited[cur.to]) continue;

            visited[cur.to] = true;
            sum += cur.weight;

            for(Info next : cost.get(cur.to)){
                if (visited[next.to]) continue;
                else {
                    pq.add(next);
                }
            }
        }

        return sum;
    }

    private static class Info implements Comparable<Info>{
        int to, weight;

        public Info(int to, int weight) {
            this.to = to;
            this.weight = weight;
        }

        @Override
        public int compareTo(Info o) {
            return this.weight - o.weight;
        }
    }
}
