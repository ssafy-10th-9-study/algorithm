package boj.gold3;

import java.util.*;
import java.io.*;

/**

 - @author 이병헌
 - @since 6/28/2024
 - @see https://www.acmicpc.net/problem/18437
 - @git https://github.com/Hunnibs
 - @youtube
 - @performance
 - @category # Dynamic Programming
 - @note
 1. 주어지는 막대 길이는 정렬된 순서로 주어지는가? -> 아마 X인것으로 추정
 */

public class BOJ_28437 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        int[] nums = new int[100_001];
        boolean[] A = new boolean[100_001];

        int N = Integer.parseInt(br.readLine());
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            A[Integer.parseInt(st.nextToken())] = true;
        }

        int Q = Integer.parseInt(br.readLine());
        int[] L = new int[Q];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < Q; i++) {
            L[i] = Integer.parseInt(st.nextToken());
        }

        for (int i = 0; i < 100_001; i++) {
            if (A[i]) nums[i]++;

            if (nums[i] == 0){
                continue;
            }

            for (int j = 2; i * j < 100_001; j++){
                nums[j * i] += nums[i];
            }
        }

        for (int i = 0; i < Q; i++) {
            sb.append(nums[L[i]]).append(" ");
        }

        System.out.print(sb);
    }
}
