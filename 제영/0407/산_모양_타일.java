import java.util.Arrays;
class Solution {
    static int init = -1;
    static int[][] dp;
    static int mod = 10_007;

    public static int find(int[] tops, int depth) {
        if (depth >= dp.length - 1) { // depth == dp.length 할 경우, depth + 2 할 때 넘어감
            return 1;
        }

        if (depth % 2 == 1) { // 1, 3일 때 위에 삼각형이 있을 수 있음
            if (tops[depth / 2] != 1) { // 삼각형 안 올려져있으면, 위로 가기 불가능
                dp[depth][1] = 0;
            }
        } else dp[depth][1] = 0;

        if (dp[depth][0] == init) {
            dp[depth][0] = (find(tops, depth + 1)) % mod;
        }
        if (dp[depth][1] == init) {
            dp[depth][1] = (find(tops, depth + 1)) % mod;
        }
        if (dp[depth][2] == init) {
            dp[depth][2] = (find(tops, depth + 2)) % mod;
        }

        return ((dp[depth][0] + dp[depth][1] + dp[depth][2]) % mod);
    }

    public int solution(int n, int[] tops) {
        dp = new int[n][2];
        // for (int[] line : dp) {
        //     Arrays.fill(line, init);
        // }
        // find(tops, 0); 재귀 호출 시, 재귀 깊이가 10만이 넘어가 RuntimeError 발생
        dp[0][0] = 1;
        if (tops[0] == 1) {
            dp[0][1] = 3;
        } else {
            dp[0][1] = 2;
        }
        for (int i = 1; i < n; i++) {
            if (tops[i] == 1) {
                dp[i][0] = (dp[i - 1][0] + dp[i - 1][1]) % mod;
                dp[i][1] = (dp[i - 1][0] * 2 + dp[i - 1][1] * 3) % mod;
            } else {
                dp[i][0] = (dp[i - 1][0] + dp[i - 1][1]) % mod;
                dp[i][1] = (dp[i - 1][0] + dp[i - 1][1] * 2) % mod;
            }
        }
        return (dp[n - 1][0] + dp[n - 1][1]) % mod;
    }
}