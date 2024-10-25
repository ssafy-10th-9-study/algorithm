/**

 - @author 이병헌
 - @since 7/25/24
 - @see https://www.acmicpc.net/problem/2467
 - @git https://github.com/Hunnibs
 - @youtube
 - @performance O(N)
 - @category # Two Pointer
 - @note
 1. 0이 되는 순간 무조건 최소이므로 더 이상 탐색은 불필요하다.
 2. 합이 음수일 경우는 left가 이동, 합이 양수일 경우는 right가 이동해야 둘 사이 합이 작아진다.

 */

import java.util.*;
import java.io.*;

public class BOJ_02467_용액_TwoPointer {
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int N = Integer.parseInt(br.readLine());
        st = new StringTokenizer(br.readLine());
        int[] flask = new int[N];
        for(int i = 0; i < N; i++){
            flask[i] = Integer.parseInt(st.nextToken());
        }

        int s = 0;
        int e = N-1;
        int left = 0;
        int right = 0;
        int minSum = Integer.MAX_VALUE;

        while(s < e){
            int abs = Math.abs(flask[s] + flask[e]);
            if (abs == 0){
                left = flask[s];
                right = flask[e];
                break;
            }

            if (abs < minSum){
                minSum = abs;
                left = flask[s];
                right = flask[e];
            }

            if(flask[s] + flask[e] < 0){
                s++;
            } else{
                e--;
            }
        }

        System.out.println(left + " " + right);
    }
}
