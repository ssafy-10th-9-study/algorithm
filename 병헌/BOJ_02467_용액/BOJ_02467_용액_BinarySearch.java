package boj.gold5.BOJ_02467_용액;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ_02467_용액_BinarySearch {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int N = Integer.parseInt(br.readLine());
        st = new StringTokenizer(br.readLine());
        int[] flask = new int[N];
        for(int i = 0; i < N; i++){
            flask[i] = Integer.parseInt(st.nextToken());
        }

        int[] answer = sol(N, flask);
        System.out.println(answer[0] + " " + answer[1]);
    }

    private static int[] sol(int N, int[] flask){
        int[] answer = new int[2];
        int minSum = Integer.MAX_VALUE;
        for (int i = 0; i < N-1; i++) {
            int left = i+1;
            int right = N-1;
            while(left <= right){
                int mid = (left + right) / 2;
                int abs = Math.abs(flask[i] + flask[mid]);

                if (abs == 0){
                    answer[0] = flask[i];
                    answer[1] = flask[mid];
                    return answer;
                }

                if (abs < minSum){
                    minSum = abs;
                    answer[0] = flask[i];
                    answer[1] = flask[mid];
                }

                if (flask[i] * (-1) >= flask[mid]){
                    left = mid+1;
                } else{
                    right = mid-1;
                }
            }
        }
        return answer;
    }
}
