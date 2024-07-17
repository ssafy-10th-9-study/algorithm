package bj.g1;

import java.io.*;
import java.util.*;

/**
 * @author 김영욱
 * @git
 * @performance
 * @category #세그먼트트리
 * @note 세그먼트 트리를 쓰는 문제다.
 * 다만 기존과 달리 합대신 곱을 써야한다.
 * <p>
 * 기존 트리의 값이 바뀌게 된다면
 * 2 -> 8
 * ex )0 120 6 20 2 3 4 5 1 2 -> 8로바꿔
 * 0 120 6 20 2 3 4 5 1 8
 * 0 120 6 20 8 3 4 5 1 8
 * 0 120 24 20 8 3 4 5 1 8
 * 0 480 24 20 8 3 4 5 1 8
 * <p>
 * 바꿀 숫자를 원래 숫자로 나눈 만큼 다른 것들도 곱해주면 됨
 * <p>
 * 2 -> 1
 * ex )0 120 6 20 2 3 4 5 1 1
 * 0 120 6 20 1 3 4 5 1 1
 * 0 120 3 20 1 3 4 5 1 1
 * 0 60 3 20 1 3 4 5 1 1
 * * 3 -> 7
 * * ex )0 120 14 20 2 7 4 5 1 2
 * * 0 120 18 20 1 3 4 5 1 1
 * * 0 120 3 20 1 3 4 5 1 1
 * * 0 60 3 20 1 3 4 5 1 1
 * 반례가 있네
 * <p>
 * 그냥 단순히 밑에서 부터 숫자 바꾸고 다시 갱신해야할듯
 * https://www.youtube.com/watch?v=NoeQkthS57s&ab_channel=%EA%B7%B1%EA%B7%B1
 * 세그먼트 트리 설명 잘되어 있는 영상
 * @see https://www.acmicpc.net/problem/11505
 * @since 2024. 07. 15
 */
public class BJ_G1_11505_구간곱구하기 {

    static StringTokenizer tokens;
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder builder = new StringBuilder();

    static int N, M, K;
    static long[] tree;
    static int[] nums;
    static int MOD = 1000000007;

    public static void main(String[] args) throws IOException {
        tokens = new StringTokenizer(input.readLine());
        N = Integer.parseInt(tokens.nextToken());
        M = Integer.parseInt(tokens.nextToken());
        K = Integer.parseInt(tokens.nextToken());

        nums = new int[N + 1];
        tree = new long[N * 4];

        for (int i = 1; i <= N; i++) {
            nums[i] = Integer.parseInt(input.readLine());
        }
        init(1, N, 1);

        for (int i = 1; i <= M + K; i++) {
            tokens = new StringTokenizer(input.readLine());
            int flag = Integer.parseInt(tokens.nextToken());

            if (flag == 1) {
                int index = Integer.parseInt(tokens.nextToken());
                int changeNum = Integer.parseInt(tokens.nextToken());

                update(1, N, 1, index, changeNum);
                nums[index] = changeNum;

            } else if (flag == 2) {
                int left = Integer.parseInt(tokens.nextToken());
                int right = Integer.parseInt(tokens.nextToken());

                builder.append(mul(1, N, 1, left, right)).append("\n");
            }
        }

        System.out.println(builder);

    }

    private static long init(int start, int end, int index) {
        if (start == end) {
            tree[index] = nums[start];
            return tree[index];
        }

        int mid = (start + end) / 2;
        tree[index] = init(start, mid, index * 2) * init(mid + 1, end, index * 2 + 1) % MOD;
        return tree[index];
    }

    private static long update(int start, int end, int index, int target, long diff) {
        if (target < start || target > end) {
            return tree[index];
        }
        if (start == end) {
            tree[index] = diff;
            return tree[index];
        }
        int mid = (start + end) / 2;

        tree[index] = update(start, mid, index * 2, target, diff) * update(mid + 1, end, index * 2 + 1, target, diff) % MOD;
        return tree[index];

    }

    private static long mul(int start, int end, int index, int left, int right) {
        if(left > end || right < start) return 1;

        if(left <= start && right >= end){
            return tree[index];
        }
        int mid = (start + end) / 2;

        return (mul(start, mid, index * 2, left, right) * mul(mid + 1, end, index * 2 + 1, left, right) % MOD);
    }
}
