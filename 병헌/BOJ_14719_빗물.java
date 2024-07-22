package boj.gold5;

import java.util.*;
import java.io.*;

/**
 * - @author 이병헌
 * - @since 7/19/2024
 * - @see https://www.acmicpc.net/problem/14719
 * - @git https://github.com/Hunnibs
 * - @youtube
 * - @performance O(N)
 * - @category # Brute force
 * - @note
 */


public class BOJ_14719 {
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static StringTokenizer st;

    public static void main(String[] args) throws Exception {
        // H : 높이, W : 너비
        st = new StringTokenizer(br.readLine());
        int H = Integer.parseInt(st.nextToken());
        int W = Integer.parseInt(st.nextToken());

        // 너비 단위 1당 높이
        int[] heights = new int[W];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < W; i++) {
            heights[i] = Integer.parseInt(st.nextToken());
        }

        // solution
        Queue<Integer> tmp = new ArrayDeque<>();
        int answer = 0;
        // 벽 선정
        for (int i = 1; i < W-1; i++) {
            int l = -1;
            int r = -1;

            for (int j = 0; j < i; j++){
                l  = Math.max(heights[j], l);
            }

            for (int j = i+1; j < W; j++){
                r = Math.max(heights[j], r);
            }

            if (heights[i] < l && heights[i] < r) {
                answer += Math.min(l, r) - heights[i];
            }
        }

        System.out.print(answer);
    }

    private static int sum(int l, int[] heights, Queue<Integer> queue) {
        int sum = 0;

        while (!queue.isEmpty()) {
            sum += (heights[l] - queue.poll());
        }

        return sum;
    }
}
