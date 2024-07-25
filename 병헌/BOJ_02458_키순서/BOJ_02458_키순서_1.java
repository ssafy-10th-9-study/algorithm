/**

 - @author 이병헌
 - @since 7/23/2024
 - @see https://www.acmicpc.net/problem/2458
 - @git https://github.com/Hunnibs
 - @youtube
 - @performance
 - @category # Floyd-Warshall
 - @note

 */

import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        long[][] dp = new long[N+1][N+1];
        for (int i = 0; i <= N; i++) {
            Arrays.fill(dp[i], Integer.MAX_VALUE);
        }

        for(int i = 0; i < M; i++){
            st = new StringTokenizer(br.readLine());
            int in = Integer.parseInt(st.nextToken());
            int out = Integer.parseInt(st.nextToken());

            dp[in][out] = 1;
        }

        for (int k = 1; k <= N; k++) {
            for (int i = 1; i <= N; i++) {
                for (int j = 1; j <= N; j++) {
                    if (dp[i][j] > dp[i][k] + dp[k][j]){
                        dp[i][j] = dp[i][k] + dp[k][j];
                    }
                }
            }
        }

        int answer = 0;
        for (int i = 1; i <= N; i++) {
            int cnt = 0;
            for (int j = 1; j <= N; j++) {
                if (dp[i][j] < Integer.MAX_VALUE || dp[j][i] < Integer.MAX_VALUE) cnt++;
            }
            if (cnt == N-1){
                answer++;
            }
        }

        System.out.println(answer);
    }
}