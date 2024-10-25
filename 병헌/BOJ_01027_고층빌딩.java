package boj.gold4;

import java.io.*;
import java.util.*;

/**

 - @author 이병헌
 - @since 10/23/2024
 - @see https://www.acmicpc.net/problem/1027
 - @git https://github.com/Hunnibs
 - @youtube
 - @performance 2sec 128MB
 - @category # Implementation # Math
 - @note

 */


public class BOJ_01027 {
    static int MAX_HEIGHT;
    static int MIN_HEIGHT;
    static double MAX_SLOPE = Double.POSITIVE_INFINITY;
    static double MIN_SLOPE = Double.NEGATIVE_INFINITY;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());
        int[] building = new int[N];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++){
            building[i] = Integer.parseInt(st.nextToken());
            MAX_HEIGHT = Math.max(building[i], MAX_HEIGHT);
            MIN_HEIGHT = Math.min(building[i], MIN_HEIGHT);
        }

        int[] answer = new int[N];
        double canSeeUp, canSeeDown;
        boolean flag;
        for (int i = 0; i < N; i++){
            canSeeUp = MIN_SLOPE;
            canSeeDown = MIN_SLOPE;
            flag = true;
            for (int j = i+1; j < N; j++){
                double slope = getSlope(building, i, j);

                // 볼 수 있는 건물이 더 이상 존재하지 않는 경우
                if (canSeeUp > MAX_HEIGHT && canSeeDown >= building[i]){
                    break;
                }

//                if (max_idx - i > 0) canSeeUp = max + (double) ((max - building[i]) * (j - max_idx) / (max_idx - i));
//                if (min_idx - i > 0) canSeeDown = min - (double) ((building[i] - min) * (j - min_idx) / (min_idx - i));

                // 건물의 높이가 같은 경우는 한 번만 옥상을 볼 수 있고 그 이후로 그보다 낮은 층의 건물은 볼 수 없다
                if (building[i] == building[j] && flag){
                    answer[i]++;
                    answer[j]++;
                    flag = false;
                }

                // 건물의 높이가 더 높은 경우
                if (building[j] > building[i] && slope > canSeeUp){
                    answer[i]++;
                    answer[j]++;
                    canSeeUp = slope;
                    flag = false;
                }

                // 건물의 높이가 더 낮은 경우
                if (building[j] < building[i] && flag) {
                    if (slope > canSeeDown) {
                        answer[i]++;
                        answer[j]++;
                        canSeeDown = slope;
                    }
                }
            }
        }

        int output = 0;
        for (int i = 0; i < N; i++) {
            output = Math.max(output, answer[i]);
        }

        System.out.print(output);
    }

    // i번째 건물에서 j번째 건물까지의 기울기 구하기
    private static double getSlope(int[] building, int i, int j) {
        return (double) (building[j] - building[i]) / (j - i);
    }
}
