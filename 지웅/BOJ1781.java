import java.io.*;
import java.util.*;

public class BOJ1781 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        StringTokenizer st;
        List<List<Integer>> list = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            list.add(new ArrayList<>());
        }
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            list.get(a).add(b);
        }
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for (int i = 1; i <= n; i++) {
            for (int j : list.get(i)) {
                pq.offer(j);
                if (pq.size() > i) pq.poll();
            }
        }
        int total = 0;
        while (!pq.isEmpty()) {
            total += pq.poll();
        }
        System.out.println(total);
    }
}
