package bj.g3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.StringTokenizer;

/**
 * @author 김영욱
 * @git
 * @performance
 * @category #
 * @note 두 배열 A랑 B가 주어지고 목표 숫자인 T가 주어진다
 * A,B 배열에 있는 요소를 합쳐서 목표 숫자인 T가 되는 경우의 수를 구해라
 * <p>
 * T(5) = A[1] + B[1] + B[3] 이게 한 경우 (인덱스 아닌 그냥 요소를 나타냈음 합이 5인거)
 * <p>
 * 첫 번째 든 생각 : 각 배열에 관하여 세그먼트 트리로 구간합을 만들어 놓고 해보자!
 * 대 실패
 * 세그먼트 트리는 구간의 합이니까 이 문제와 맞지 않는다.
 * 바로 검색 -> 부배열?과 투포인터
 * 부배열은 단순 무식하게 구하는 수 밖에 없고, 여기서 까먹은 시간 복잡도를 투포인터로 커버하는 느낌의 문제였다.
 * 이번에는 보고 풀었으니 나중에 다시 풀어보자
 * @see https://www.acmicpc.net/problem/2143
 * @since 2024. 08. 03
 */

public class BJ_G3_2143_두배열의합 {

    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer tokens;

    static int T, N, M;
    static long ans;
    static int[] A, B;
    static ArrayList<Long> aSum;
    static ArrayList<Long> bSum;


    public static void main(String[] args) throws IOException {
        T = Integer.parseInt(input.readLine());

        N = Integer.parseInt(input.readLine());
        A = new int[N + 1];
        tokens = new StringTokenizer(input.readLine());
        for (int i = 1; i <= N; i++) {
            A[i] = Integer.parseInt(tokens.nextToken());
        }

        M = Integer.parseInt(input.readLine());
        B = new int[M + 1];
        tokens = new StringTokenizer(input.readLine());
        for (int i = 1; i <= M; i++) {
            B[i] = Integer.parseInt(tokens.nextToken());
        }

        aSum = new ArrayList<>();
        bSum = new ArrayList<>();

        calArraySum(aSum, A);
        calArraySum(bSum, B);

        Collections.sort(aSum);
        Collections.sort(bSum);

        int left = 0;
        int right = bSum.size() - 1;

        while (left < aSum.size() && right >= 0) { // 모든 포인터를 훑고 지나가야해서
            long sum = aSum.get(left) + bSum.get(right);

            if (sum == T) {
                long strickA = aSum.get(left);
                long strickB = bSum.get(right);
                long aCnt = 0, bCnt = 0;
                while (left < aSum.size() && aSum.get(left) == strickA) { // 전에 있던 숫자와 같은 숫자라면
                    aCnt++;
                    left++;
                }
                while (right >= 0 && bSum.get(right) == strickB) { // 전에 있던 숫자와 같은 숫자라면
                    bCnt++;
                    right--;
                }
                ans += aCnt * bCnt;
            }
            else if(sum > T) {
                right--;
            }
            else {
                left++;
            }
        }
        System.out.println(ans);
    }

    private static void calArraySum(ArrayList<Long> sumList, int[] nums) {
        for (int i = 1; i < nums.length; i++) {
            long sum = 0;
            for (int j = i; j < nums.length; j++) {
                sum += nums[j];
                sumList.add(sum);
            }
        }
    }
//    private static long treeInit(int start, int end, int index, long[] tree, int[] nums) {
//        if (start == end) {
//            tree[index] = nums[start];
//            return tree[index];
//        }
//
//        int mid = (start + end) / 2;
//
//        tree[index] = treeInit(start, mid, index * 2, tree, nums) + treeInit(mid + 1, end, index * 2 + 1, tree, nums);
//        return tree[index];
//    }
}
