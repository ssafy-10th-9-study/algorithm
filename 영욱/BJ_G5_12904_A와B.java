package bj.g5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

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
 * 거꾸로하면 쉬운 문제다
 * T의 끝이 B라면 모두 뒤집고 마지막 B를 제거하고, A라면 끝에 A만 빼면 된다.
 * 이런식으로 S와T의 길이가 같을때 까지 반복 후 두 개가 같다면 1, 다르면 0을 출력하면 되는 문제
 * <p>
 * 아 뒤집는게 A->B, B->A가 아니라 그냥 처음부터 끝까지 뒤집는거였음 ㅋㅋ
 * @see https://www.acmicpc.net/problem/12904
 * @since 2024.06.19
 */
public class BJ_G5_12904_A와B {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static String S, T;

    public static void main(String[] args) throws IOException {

        S = input.readLine();
        T = input.readLine();

        while (S.length() != T.length()) {
            if (T.charAt(T.length() - 1) == 'A') {
                T = subA(T);
            } else {
                T = flipSAndSubB(T);
            }
        }

        if (S.equals(T)) {
            System.out.println(1);
        } else {
            System.out.println(0);
        }
    }

    private static String flipSAndSubB(String s) {
        StringBuilder temp = new StringBuilder();
        for (int i = s.length() - 2; i >= 0; i--) {
            temp.append(s.charAt(i));
        }
        return temp.toString();
    }

    private static String subA(String s) {
        return s.substring(0, s.length() - 1);
    }
}
