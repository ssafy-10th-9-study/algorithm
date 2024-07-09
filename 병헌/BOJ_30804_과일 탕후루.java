package boj.silver2;

import java.util.*;
import java.io.*;

public class BOJ_30804 {
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        int[] fruits = new int[N];
        for(int i = 0; i < N; i++){
            int type = Integer.parseInt(st.nextToken());
            fruits[i] = type;
        }

        System.out.print(sol(N, fruits));
    }

    private static int sol(int N, int[] fruits) {
        int[] check = new int[10];
        int l = 0, r = 0, kind = 0, max = -1;
        while(l <= r && r < N){
            if (kind <= 2){
                if(check[fruits[r]] == 0){
                    kind++;
                }
                check[fruits[r]]++;
                r++;
            } else{
                check[fruits[l]]--;
                if (check[fruits[l]] == 0){
                    kind--;
                }
                l++;
            }

            if (kind <= 2){
                max = Math.max(max, r-l);
            }
        }

        return max;
    }
}
