package programmers;

import java.util.*;
import java.io.*;

public class PG_LV3_야근지수 {
    static int n = 4;
    static int[] works = {4, 3, 3};//12
//    static int n = 1;
//    static int[] works = {2,1,2};//6
    static long answer;

    public static void main(String[] args) {
        System.out.println(solution(n, works));
    }

    private static long solution(int n, int[] works) {
        PriorityQueue<Integer> pq = new PriorityQueue<>(Comparator.reverseOrder());
        for (int num : works) pq.offer(num);

        while (!pq.isEmpty() && n > 0) {
            int work = pq.poll();
            work -= 1;
            n -= 1;
            if (work == 0) continue;
            pq.offer(work);
        }
        long answer = 0;
        if (pq.isEmpty()) return answer;
        while (!pq.isEmpty()) {
            int work = pq.poll();
            answer += Math.pow(work, 2);
        }
        return answer;
    }
}
