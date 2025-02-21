package boj.gold4;

public class PGS_KAKAO_산모양타일링 {
    class Solution {
        final int MOD = 10007;

        public int solution(int n, int[] tops) {
            int answer = 0;

            // 산을 일차원 배열로 편다 - 홀수 인덱스는 아랫 타일, 짝수 인덱스는 윗 타일이 된다.
            int m = 2 * n + 1;
            boolean[] tiles = new boolean[m];
            for (int i = 0; i < n ;i++) {
                if (tops[i] == 1) tiles[i * 2 + 1] = true;
            }

            // 0번 인덱스는 패딩으로 활용하고 dp[1]이 첫 타일이라고 생각한다.
            int[] dp = new int[m+1];
            dp[0] = 1;
            dp[1] = 1;
            for (int i = 2; i <= m; i++) {
                dp[i] = (dp[i - 1] + dp[i - 2]) % MOD;  // 타일은 마름모를 추가하는 경우와 삼각형을 추가하는 경우를 더해준다.
                if (i % 2 == 0 && tiles[i-1]) {  // 만약 위에 덧붙힌 삼각형이 있다면 경우의 수를 하나 더 추가해준다.
                    dp[i] = (dp[i] + dp[i - 1]) % MOD;
                }
            }

            return dp[m];
        }
    }
}
