package bj.g4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * @author 김영욱
 * @git
 * @performance
 * @category #
 * @note 첫째 줄에 N이 주어진다. N은 항상 3×2k 수이다. (3, 6, 12, 24, 48, ...) (0 ≤ k ≤ 10, k는 정수)
 * N = 12
 * N을 2씩 나누면서 3이 되었을 때 가운데가 비어있는 큰 삼각형을 출력
 * 바보라서 인터넷 보고 이해했음
 *
 * r = 0, c = N-1, N = 24
 *
 * cut = 6;
 * star(0,N-1,6)
 *
 * cut = 3;
 * star(0,N-1,3);
 * 삼각형 실행 -> 리턴
 *
 * star(0+3, 23-3, 3);
 * 삼각형 실행 -> 리턴
 *
 * star(0+3, 23+3, 3);
 * 삼각형 실행 -> 리턴
 *
 * 다시 cut=6;
 *
 * star(0+6, 23-6, 6);
 *
 * cut = 3;
 * star(6, 17, 3);
 * 삼각형 실행 -> 리턴
 *
 * star(6+3, 17-3, 3);
 * 삼각형 실행 -> 리턴
 *
 * star(6+3, 17+3, 3);
 * 삼각형 실행 리턴
 *
 * ...쭉쭉쭉
 *
 *
 * @see https://www.acmicpc.net/problem/2448
 * @since 2025. 01. 29.
 **/

public class BJ_G4_2448_별찍기11 {

    static StringBuilder builder = new StringBuilder();
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

    static char[][] stars;

    static int N;

    public static void main(String[] args) throws IOException {
        N = Integer.parseInt(input.readLine());

        stars = new char[N][2 * N - 1];
        for (int i = 0; i < N; i++) {
            Arrays.fill(stars[i], ' ');
        }

        star(0, N - 1, N);

        for(int i=0; i<N; i++) {
            for(int j=0; j<2*N-1; j++) {
                builder.append(stars[i][j]);
            }
            builder.append("\n");
        }
        System.out.println(builder);
    }

    private static void star(int r, int c, int N) {
        if (N == 3) {
            stars[r][c] = '*';//*
            stars[r + 1][c - 1] = stars[r + 1][c + 1] = '*';//* *
            stars[r + 2][c - 2] = stars[r + 2][c - 1] = stars[r + 2][c] = stars[r + 2][c + 1] = stars[r + 2][c + 2] = '*';//*****
        } else {
            int cut = N / 2;
            star(r, c, cut);// 제일 위에 삼각형
            star(r + cut, c - cut, cut);// 아래 왼쪽 삼각형
            star(r + cut, c + cut, cut);// 아래 오른쪽 삼각형
        }
    }

}
