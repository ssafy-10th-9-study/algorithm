package bj.g5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * @author 김영욱
 * @git
 * @performance
 * @category #구현
 * @note 1초
 * 문제
 * 1층부터 N층까지 이용이 가능한 엘리베이터가 있다.
 * 엘리베이터의 층수를 보여주는 디스플레이에는 K 자리의 수가 보인다.
 * 수는 0으로 시작할 수도 있다.
 * 0부터 9까지의 각 숫자가 디스플레이에 보이는 방식은 아래와 같다.
 * 각 숫자는 7개의 표시등 중의 일부에 불이 들어오면서 표현된다.
 * <p>
 * K = 4인 경우에 1680층과 501층은 1680 0501로 보여진다
 * <p>
 * 빌런 호석은 치르보기 빌딩의 엘리베이터 디스플레이의 LED 중에서
 * 최소 1개, 최대 P개를 반전시킬 계획을 세우고 있다.
 * 반전이란 켜진 부분은 끄고, 꺼진 부분은 켜는 것을 의미한다. 예를 들어
 * 숫자 1을 2로 바꾸려면 총 5개의 LED를 반전시켜야 한다.
 * <p>
 * 또한 반전 이후에 디스플레이에 올바른 수가 보여지면서 1 이상 N이하가 되도록
 * 바꿔서 사람들을 헷갈리게 할 예정이다.
 * 현재 엘리베이터가 실제로는 X층에 멈춰있을 때, 호석이가 반전시킬
 * LED를 고를 수 있는 경우의 수를 계산해보자.
 * <p>
 * N,K,P,X가 주어진다.
 * 입력 9 1 2 5( 9층까지 이용 가능, 1자리 수, 2개 반전, 현재 5층 )
 * 출력 4
 * LED를 2개까지 바꿀 수 있을 때, 5층에서 3층, 6층, 8층 그리고 9층으로 바꿔버릴 수 있다.
 * <p>
 * 0 : 1(4), 2(3), 3(2), 4(4), 5(3), 6(2), 7(3), 8(1), 9(2)
 * 1 : 0(4), 2(5), 3(3), 4(2), 5(5), 6(6), 7(1), 8(5), 9(4)
 * 2 : 0(3), 1(5), 3(2), 4(5), 5(4), 6(3), 7(4), 8(2), 9(3)
 * 3 : 0(3), 1(3), 2(2), 4(3), 5(2), 6(3), 7(2), 8(2), 9(1)
 * 4 : 4, 2, 5, 3, 0, 3, 4, 3, 3, 2
 * 5 : 3, 5, 4, 2, 3, 0, 1, 4, 2, 1
 * 6 : 2, 6, 3, 3, 4, 1, 0, 5, 1, 2
 * 7 : 3, 1, 4, 2, 3, 4, 5, 0, 4, 3
 * 8 : 1, 5, 2, 2, 3, 2, 1, 4, 0, 1
 * 9 : 2, 4, 3, 1, 2, 1, 2, 3, 1, 0
 * <p>
 * K번 인덱스 까지 돌아
 * 각 인덱스 숫자마다 0~9까지 돌면서
 * N층 이하면서 X층일 때 P번 이하로 반전 시킬 수 있는 숫자들을 찾아서 카운트
 * <p>
 * 0405
 * 4 0 5
 * 1. 바꾼 숫자가 N보다 작거나 같은가
 * 2. 바꿀 숫자의 반전 수가 P보다 작거나 같은가
 * <p>
 * 첫 아이디어
 * 0~N까지 다 돌면서 나온 숫자마다 배열로 바꿔서 체크한다
 * <p>
 * 근데 맨 오른쪽(1의 자리)부터 돌면서 바꿀 수 있는 숫자를 체크했다 치자
 * 그럼 다음 2의 자리를 체크할 때는 1의 자리 체크할 필요 없지 않나?
 * 그냥 2의 자리 체크한거 + 1의 자리 체크한 값이 P보다 작으면 되잖아( 중복되니까 )
 * 그러면 10^6 도는거 그냥 60번만 돌면 되는데?
 * @see https://www.acmicpc.net/problem/22251
 * @since 2024. 09. 05
 **/

public class BJ_G5_22251_빌런호석 {

    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer tokens;

    static int N, K, P, X, ans;
    static int[][] arr =
            {{0, 4, 3, 3, 4, 3, 2, 3, 1, 2},
                    {4, 0, 5, 3, 2, 5, 6, 1, 5, 4},
                    {3, 5, 0, 2, 5, 4, 3, 4, 2, 3},
                    {3, 3, 2, 0, 3, 2, 3, 2, 2, 1},
                    {4, 2, 5, 3, 0, 3, 4, 3, 3, 2},
                    {3, 5, 4, 2, 3, 0, 1, 4, 2, 1},
                    {2, 6, 3, 3, 4, 1, 0, 5, 1, 2},
                    {3, 1, 4, 2, 3, 4, 5, 0, 4, 3},
                    {1, 5, 2, 2, 3, 2, 1, 4, 0, 1},
                    {2, 4, 3, 1, 2, 1, 2, 3, 1, 0}};

    static int[] curDigital;

    public static void main(String[] args) throws IOException {
        tokens = new StringTokenizer(input.readLine());

        N = Integer.parseInt(tokens.nextToken());// 9층까지 이용 가능
        K = Integer.parseInt(tokens.nextToken());// 1자리 수
        P = Integer.parseInt(tokens.nextToken());// 2개 반전
        X = Integer.parseInt(tokens.nextToken());// 현재 5층

        curDigital = numToDigital(X);
        int[] targetDigital;

        for (int i = 1; i <= N; i++) {
            if (i == X) continue;
            targetDigital = numToDigital(i);
            int temp = 0;
            for (int j = 0; j < targetDigital.length; j++) {
                temp += arr[curDigital[j]][targetDigital[j]];
            }
            if (temp <= P) {
                ans++;
            }
        }
        System.out.println(ans);
    }

    private static int[] numToDigital(int x) {
        int[] tempDigital = new int[K];
        for (int k = K - 1; k >= 0; k--) {
            tempDigital[k] = x % 10;
            x /= 10;
        }
        return tempDigital;
    }

    private static int digitalToNum(int len) {
        int result = 0;
        for (int digit : curDigital) {
            int pow = (int) Math.pow(10, len - 1);
            result += pow * digit;
            len -= 1;
        }
        return result;
    }
}
