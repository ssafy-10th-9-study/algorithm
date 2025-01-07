package bj.g5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.StringTokenizer;

public class BJ_G5_2212_센서 {

    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer tokens;

    static int N, K;
    static int[] sensors;
    static Integer[] sensorsDiff;

    public static void main(String[] args) throws IOException {
        N = Integer.parseInt(input.readLine());
        K = Integer.parseInt(input.readLine());

        sensors = new int[N];
        sensorsDiff = new Integer[N - 1];

        if (K >= N) {
            System.out.println(0);
            return;
        }
        tokens = new StringTokenizer(input.readLine());
        for (int i = 0; i < N; i++) {
            sensors[i] = Integer.parseInt(tokens.nextToken());
        }
        Arrays.sort(sensors);
        for (int i = 0; i < N - 1; i++) {
            sensorsDiff[i] = sensors[i + 1] - sensors[i];
        }
        Arrays.sort(sensorsDiff);
        Arrays.sort(sensorsDiff, Collections.reverseOrder());


        int ans = 0;
        for (int i = K-1; i < N-1; i++) {
            ans += sensorsDiff[i];
        }
        System.out.println(ans);


    }

}
