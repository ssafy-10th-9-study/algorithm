package bj.g2;

import java.io.*;
import java.util.*;

/**
 * @author 김영욱
 * @git
 * @performance
 * @category #수학
 * @note 시간 : 1초, N : 300000
 * 주헌이는 매운 음식을 좋아한다. 주헌이가 매운 맛을 느끼는 방식은 상당히 독특한데,
 * [5, 2, 8]이라는 스코빌 지수를 가지고 있다면 최댓값 8 - 최솟값 2 = 6의 매운 맛을 느낀다.
 * 주헌이가 음식점에 갔을 때 모든 조합(두 가지 조합 이상)의 음식을 맛보고, 그 매운 맛의 합을 구하려 한다.
 *
 * ex) [5, 2, 8]
 * 합계 : 18[ 3(2,5), 6(2,8), 3(5,8), 6(2,5,8) ]
 *
 * 모든 부분 집합을 구한다면 최악의 경우 2^3000이니까 무한대에 빠진다.
 * 근데 어차피 어떤 조합이던 간에 조합에 속해 있는 최솟값 최댓값은 겹치기 마련이다.
 *
 * [ 1, 3, 6, 10, 15]
 * (1,3), (1,6), (1,10), (1,15)
 * 1,3은 그냥 1,3 : 1개
 * 1,6 : 1,3,6   1,6 : 2개
 * 1,10 : 1,10   1,3,10   1,6,10   1,3,6,10 : 4개
 * 1,15 : 1,15   1,3,15   1,6,15   1,10,15   1,3,6,15   1,6,10,16   1,3,10,15  1,3,6,10,15  : 8개
 * 즉, 두 숫자 사이에 숫자 개수 만큼 부분 집합(2^n)이 나오는 개수 만큼 곱해주면 된다
 * (3, 6), (3,10), (3,15)
 * 이렇게 하나씩 늘려가며 부배열을 구하면 됨
 *
 * [1, 4, 5, 5, 6, 10]
 * 1,4 1,5 1,5 1,6 1,10 => [3*1 + 4*2 + 4*4 + 5*8 + 9*16] + [1*1 + 1*2 + 2*4 + 6*8] + [0*1 + 1*2 + 5*4] + [1*1 + 5*2] + [4*1]
 * = 307
 *
 * 근데 N이 30만이다... 말이 안되네 지금 보니까...
 *
 * 접근3. 모든 조합의 (최댓값 - 최솟값)의 합은 (모든 조합의 최댓값의 합 - 모든 조합의 최솟값의 합)과 같습니다.
 * 길이가 N인 정렬된 배열의 i번째 값에 대해 자기보다 작은 i-1개의 값이 존재하고, 자기보다 큰 N-i개의 값이 존재합니다.
 * 이들과 모두 조합했을 때 i번째 값은 최댓값으로 pow(2,i-1)번 사용되고, 최솟값으로 pow(2,N-i)번 사용됩니다.
 * i번째 값은 전체 스코필 지수에 대해 (arr[i] * pow(2, i)) - (arr[i] * pow(2, N-i))만큼 기여합니다.
 * N이 최댓값인 300_000일 때 Math.pow(2,300_000)은 inf값이 나오며 연산 또한 느립니다.
 * 분할정복을 이용한 거듭제곱을 직접 구현하고 제곱을 진행하는 과정마다 모듈러 연산(%)을 진행하면 정답을 구할 수 있습니다.
 *
 * https://velog.io/@qwerty1434/%EB%B0%B1%EC%A4%80-15824%EB%B2%88-%EB%84%88-%EB%B4%84%EC%97%90%EB%8A%94-%EC%BA%A1%EC%82%AC%EC%9D%B4%EC%8B%A0%EC%9D%B4-%EB%A7%9B%EC%9E%88%EB%8B%A8%EB%8B%A4
 *
 * customPow 설명글
 * https://sskl660.tistory.com/76
 *
 *
 * @see https://www.acmicpc.net/problem/15824
 * @since 2024. 08. 09
 */

public class BJ_G2_15824_너봄에는캡사이신이맛있단다 {
    static StringTokenizer tokens;
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

    static int N;
    static long[] scoville;
    static long ans;
    static final int remainder = 1000000007;

    public static void main(String[] args) throws IOException{
        N = Integer.parseInt(input.readLine());
        tokens = new StringTokenizer(input.readLine());

        scoville = new long[N];

        for(int i=0; i<N; i++) {
            scoville[i] = Long.parseLong(tokens.nextToken());
        }

        Arrays.sort(scoville);

        for(int i=0; i<N; i++) {
            ans += scoville[i] * customPow(2, i);
            ans -= scoville[i] * customPow(2, N-i-1);
            ans %= remainder;
        }
        System.out.println(ans);

    }

    private static long customPow(int base, int x) {
//        지수가 0인 경우 종료(기저조건)
        if(x == 0) return 1L;
//        반으로 나눈 거듭 제곱 계산
        long half = customPow(base, x/2);
//        지수가 짝수일 때
        if(x%2 == 0) return half*half % remainder;
//        지수가 홀수일 때
        return half*half*base % remainder;
    }
}
