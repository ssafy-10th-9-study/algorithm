package boj.gold5;

import java.util.*;
import java.io.*;
import sun.reflect.generics.tree.Tree;

/**

 - @author 이병헌
 - @since 7/3/2024
 - @see https://www.acmicpc.net/problem/2251
 - @git https://github.com/Hunnibs
 - @youtube
 - @performance
 - @category # BFS
 - @note */

public class BOJ_02251 {
    private static Set<Integer> answer = new TreeSet<>();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();

        int a = Integer.parseInt(st.nextToken());
        int b = Integer.parseInt(st.nextToken());
        int c = Integer.parseInt(st.nextToken());

        answer.add(c);

        bfs(a, b, c);
        for(Integer ans : answer){
            sb.append(ans + " ");
        }
        System.out.print(sb);
    }

    private static void bfs(int a, int b, int c){
        Queue<Info> queue = new ArrayDeque<>();
        HashMap<Info, Boolean> visited = new HashMap<>();
        Info cur = new Info(0, 0, c);
        queue.add(cur);
        visited.put(cur, true);

        while(!queue.isEmpty()){
            cur = queue.poll();
            if (cur.a == 0){
                answer.add(cur.c);
            }

            if(cur.a != 0){
                if (b - cur.b < cur.a && !visited.containsKey(new Info(cur.a - (b - cur.b), b, cur.c))) {
                    Info next = new Info(cur.a - (b - cur.b), b, cur.c);
                    queue.add(next);
                    visited.put(next, true);
                } else if (b - cur.b >= cur.a && !visited.containsKey(new Info(0, cur.b + cur.a, cur.c))) {
                    Info next = new Info(0, cur.b + cur.a, cur.c);
                    queue.add(next);
                    visited.put(next, true);
                }

                if (c - cur.c < cur.a && !visited.containsKey(new Info(cur.a - (c - cur.c), cur.b, c))) {
                    Info next = new Info(cur.a - (c - cur.c), cur.b, c);
                    queue.add(next);
                    visited.put(next, true);
                } else if (c - cur.c >= cur.a && !visited.containsKey(new Info(0, cur.b, cur.c + cur.a))) {
                    Info next = new Info(0, cur.b, cur.c + cur.a);
                    queue.add(next);
                    visited.put(next, true);
                }
            }

            if (cur.b != 0){
                if (a - cur.a < cur.b && !visited.containsKey(new Info(a, cur.b - (a - cur.a), cur.c))) {
                    Info next = new Info(a, cur.b - (a - cur.a), cur.c);
                    queue.add(next);
                    visited.put(next, true);
                } else if (a - cur.a >= cur.b && !visited.containsKey(new Info(cur.b + cur.a, 0, cur.c))) {
                    Info next = new Info(cur.b + cur.a, 0, cur.c);
                    queue.add(next);
                    visited.put(next, true);
                }

                if (c - cur.c < cur.b && !visited.containsKey(new Info(cur.a, cur.b - (c - cur.c), c))) {
                    Info next = new Info(cur.a, cur.b - (c - cur.c), c);
                    queue.add(next);
                    visited.put(next, true);
                } else if (c - cur.c >= cur.b && !visited.containsKey(new Info(cur.a, 0, cur.c + cur.b))) {
                    Info next = new Info(cur.a, 0, cur.c + cur.b);
                    queue.add(next);
                    visited.put(next, true);
                }
            }

            if (cur.c != 0){
                if (a - cur.a < cur.c && !visited.containsKey(new Info(a, cur.b, cur.c - (a - cur.a)))) {
                    Info next = new Info(a, cur.b, cur.c - (a - cur.a));
                    queue.add(next);
                    visited.put(next, true);
                } else if (a - cur.a >= cur.c && !visited.containsKey(new Info(cur.a + cur.c, cur.b, 0))) {
                    Info next = new Info(cur.a + cur.c, cur.b, 0);
                    queue.add(next);
                    visited.put(next, true);
                }

                if (b - cur.b <= cur.c && !visited.containsKey(new Info(cur.a, b, cur.c - (b - cur.b)))) {
                    Info next = new Info(cur.a, b, cur.c - (b - cur.b));
                    queue.add(next);
                    visited.put(next, true);
                } else if (b - cur.b > cur.c && !visited.containsKey(new Info(cur.a, cur.b + cur.c, 0))) {
                    Info next = new Info(cur.a, cur.b + cur.c, 0);
                    queue.add(next);
                    visited.put(next, true);
                }
            }
        }
    }

    private static class Info{
        int a, b, c;

        Info(int a, int b, int c){
            this.a = a;
            this.b = b;
            this.c = c;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Info info = (Info) o;
            return a == info.a && b == info.b && c == info.c;
        }

        @Override
        public int hashCode() {
            return Objects.hash(a, b, c);
        }
    }
}
