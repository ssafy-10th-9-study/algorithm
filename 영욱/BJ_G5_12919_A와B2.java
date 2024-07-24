package bj.g5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author 김영욱
 * @git
 * @performance
 * @category #
 * @note 시간 2초
 * 문자열 S와 T가 주어진다.
 * S를 T로 바꿀 수 있으면 1, 없으면 0
 * 두 가지 연산만 가능
 * 연산 1 문자열의 뒤에 A를 추가한다.
 * 연산 2 문자열을 뒤집고 뒤에 B를 추가한다
 * <p>
 * 이미 완성된 답안부터 시작해서
 * 끝이 A이고 맨 앞이 B면 A만 빼거나 뒤집고 끝을 빼고
 * 맨 앞과 맨 뒤가 B면 B를 빼고 뒤집고
 * 끝이 A이고 맨 앞도 A면 A만 뺀다
 * @see https://www.acmicpc.net/problem/12919
 * @since 2024.07.20
 */

public class BJ_G5_12919_A와B2 {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static String S, T;
    static int ans;

    public static void main(String[] args) throws IOException {

        S = input.readLine();
        T = input.readLine();

        ans = 0;

        solution(T);

        System.out.println(ans);

    }
    private static void solution(String now) {
        if (now.length() == S.length()) {
            if (now.equals(S)) {
                ans = 1;
                return;
            } else {
                return;
            }
        }

        if (now.charAt(0) == 'A' && now.charAt(now.length() - 1) == 'B') return;

        if (now.charAt(0) == 'A') {
            solution(now.substring(0, now.length() - 1));
        } else if (now.charAt(0) == 'B') {
            String fliped = flipedString(now);
            solution(fliped);
            if (now.charAt(now.length() - 1) == 'A') {
                solution(now.substring(0, now.length() - 1));
            }
        }
    }

    private static String flipedString(String s) {
        StringBuilder fliped = new StringBuilder();
        for (int i = s.length() - 1; i > 0; i--) {
            fliped.append(s.charAt(i));
        }
        return fliped.toString();
    }
}
