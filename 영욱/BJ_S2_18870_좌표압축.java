package bj.s2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * @author 김영욱
 * @git
 * @performance 2368ms
 * @category #세그먼트트리
 * @note
 * 좌표가 주어지면 가장 낮은 숫자부터 큰 숫자까지 0부터 숫자를 매겨 출력하는 문제다.
 * 나는 보자마자 Key:value에서 Map을, 정렬 + 중복 숫자 제거에서 TreeSet을 떠올렸고 바로 문제를 풀었다.
 * 근데 TC가 숫자가 커서 꽤 시간이 오래걸렸다.
 * 정답자들 보니 머지소트, 이진탐색 등을 활용하여 시간을 압축시키더라...
 * 뭐, 애초에 자료구조 연습삼아서 한거니 만족한다.
 * @see https://www.acmicpc.net/problem/18870
 * @since 2024. 07. 18
 */

public class BJ_S2_18870_좌표압축 {

    static StringTokenizer tokens;
    static StringBuilder builder = new StringBuilder();
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

    static HashMap<Integer, Integer> dict = new HashMap<>();
    static int N;
    static int[] arr;
    static TreeSet<Integer> set = new TreeSet<>();

    public static void main(String[] args) throws IOException {
        N = Integer.parseInt(input.readLine());
        arr = new int[N];

        int num = 0;
        tokens = new StringTokenizer(input.readLine());
        for (int i=0; i<N; i++) {
            num = Integer.parseInt(tokens.nextToken());
            arr[i] = num;
            set.add(num);
        }

        int temp = 0;

        for(int n: set) {
            dict.put(n,temp++);
        }

        for(int n: arr) {
            builder.append(dict.get(n)).append(" ");
        }

        System.out.println(builder);
    }
}
