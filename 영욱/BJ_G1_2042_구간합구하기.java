package bj.g1;
/**
 * @author 김영욱
 * @git
 * @performance
 * @category #세그먼트트리
 * @note 세그먼트 트리를 쓰는 문제다.
 * 미리 원소들의 합을 구해놓으면 그 구간의 합을 log(N)의 속도로 계산할 수 있다
 * 원소들 * 4의 크기의 배열을 미리 선언해 놓고, 미리 초기화 해놓는다.
 * @see https://www.acmicpc.net/problem/2042
 * @since 2024. 07. 14
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BJ_G1_2042_구간합구하기 {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder builder = new StringBuilder();
    static StringTokenizer tokens;

    static int N, M, K;
    static long[] nums;
    static long[] tree;

    public static void main(String[] args) throws IOException {
        tokens = new StringTokenizer(input.readLine());

        N = Integer.parseInt(tokens.nextToken());
        M = Integer.parseInt(tokens.nextToken());
        K = Integer.parseInt(tokens.nextToken());

        nums = new long[N];
        tree = new long[N * 4];

        for (int i = 0; i < N; i++) {
            nums[i] = Long.parseLong(input.readLine());
        }
        init(0, N - 1, 1);// index 1부터 시작해야 index*2:왼쪽 자식노드, index*2+1: 오른쪽 자식노드
//		이렇게 딱 떨어진다.
//		수의 변경 or 구간합
        for (int i = 0; i < M + K; i++) {
            tokens = new StringTokenizer(input.readLine());
            int flag = Integer.parseInt(tokens.nextToken());

//			수의 변경
//			a번째 수를 b로 바꿔라
            if (flag == 1) {
                int a = Integer.parseInt(tokens.nextToken()) - 1;
                long b = Long.parseLong(tokens.nextToken());

//				원래 기록해 놨던 것과 바꿀 수의 차를 미리 구해놓고
//				기록되어있는 수들에 전부 -해주면 됨
                long diff = nums[a] - b;
//                이 이후에 같은 인덱스에 있는 값을 바꿀 경우 에러가 남
                nums[a] = b;

                update(0, N - 1, 1, a, diff);
            }
//			a번째 부터 b번째까지의 구간합
            else if (flag == 2) { // 구간합
                int left = Integer.parseInt(tokens.nextToken()) - 1;
                int right = Integer.parseInt(tokens.nextToken()) - 1;

				builder.append(sum(0, N-1, 1, left, right)).append("\n");
            }
        }
		System.out.println(builder);
    }

    private static long init(int start, int end, int index) {
//        시작과 끝이 같다면 끝까지 내려온거야
        if (start == end) {
            tree[index] = nums[start];
            return tree[index];
        }

        int mid = (start + end) / 2;

//        왼쪽 자식과 오른쪽 자식들의 합을 현재 노드에 넣어줌
        tree[index] = init(start, mid, index * 2) + init(mid + 1, end, index * 2 + 1);
        return tree[index];
    }

    private static void update(int start, int end, int index, int target, long diff) {
        if (target < start || target > end) return;

        tree[index] -= diff;

        if (start == end) return;

        int mid = (start + end) / 2;

        update(start, mid, index * 2, target, diff);
        update(mid + 1, end, index * 2 + 1, target, diff);
    }

    private static long sum(int start, int end, int index, int left, int right) {
//		범위 바깥인 경우
        if (left > end || right < start) return 0L;
//		범위 안인 경우
        if (left <= start && right >= end) return tree[index];
//		그 외의 경우라면 찾아
        int mid = (start + end) / 2;
        return sum(start, mid, index * 2, left, right) +
                       sum(mid + 1, end, index * 2 + 1, left, right);
    }
}
