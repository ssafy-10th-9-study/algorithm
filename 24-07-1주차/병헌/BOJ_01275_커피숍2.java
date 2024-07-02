package boj.gold1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * - @author 이병헌
 * - @since 7/1/2024
 * - @see https://www.acmicpc.net/problem/1275
 * - @git https://github.com/Hunnibs
 * - @youtube
 * - @performance
 * - @category # segment tree
 * - @note
 */

public class BOJ_01275 {
    private static long[] tree;
    private static long[] nums;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int Q = Integer.parseInt(st.nextToken());

        nums = new long[N];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            nums[i] = Integer.parseInt(st.nextToken());
        }

        tree = new long[N * 4];
        init(0, N-1, 1);

        int a, b, x, y;
        for (int i = 0; i < Q; i++) {
            st = new StringTokenizer(br.readLine());
            x = Integer.parseInt(st.nextToken())-1;
            y = Integer.parseInt(st.nextToken())-1;
            a = Integer.parseInt(st.nextToken())-1;
            b = Integer.parseInt(st.nextToken());

            if (x > y){
                int tmp = x;
                x = y;
                y = tmp;
            }

            sb.append(sum(0, N-1, 1, x, y)).append("\n");
            update(0, N-1, 1, a, b - nums[a]);
            nums[a] = b;
        }
        System.out.println(sb);
    }

    private static long init(int start, int end, int index) {
        if (start == end) {
            tree[index] = nums[start];
            return tree[index];
        }

        int mid = (start + end) / 2;
        tree[index] = init(start, mid, index * 2) + init(mid + 1, end, index * 2 + 1);
        return tree[index];
    }

    private static long sum(int start, int end, int index, int left, int right) {
        if (left > end || right < start) {
            return 0;
        }
        if (left <= start && right >= end) {
            return tree[index];
        } else {
            int mid = (start + end) / 2;
            return sum(start, mid, index * 2, left, right) + sum(mid + 1, end, index * 2 + 1, left, right);
        }
    }

    private static void update(int start, int end, int index, int target, long value) {
        if (target < start || target > end) {
            return;
        }

        tree[index] += value;
        if (start == end) {
            return;
        }
        int mid = (start + end) / 2;
        update(start, mid, index * 2, target, value);
        update(mid + 1, end, index * 2 + 1, target, value);
    }
}
