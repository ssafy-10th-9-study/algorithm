package bj.g2;

import java.io.*;

/**
 * @author 김영욱
 * @git
 * @performance
 * @category # 서로소 집합
 * @note 시간 : 0.5ms
 * 문제 이해에 도움이 된 블로그 https://velog.io/@ich0906/%EB%B0%B1%EC%A4%80-10775-%EA%B3%B5%ED%95%AD
 * 처음에는 진짜 문제가 무슨 말인지 몰랐다. 왜 입력이 이런데 출력이 저럴까
 * 빠른 판단으로 블로그를 찾았다( 잘한 것 같아 )
 * 저 블로그를 보고 문제를 깨달았다
 * 이 문제는 문제 해석과 이 문제를 보고 서로소 집합을 떠올리는게 가장 어려운 것 같다
 * 단순히 주어진 숫자와 가장 가까우면서 큰 수에 비행기를 집어넣는 방식으로 짠다면 N^2가 나오고
 * N이 100,000이니 이는 시간 초과가 날게 뻔하다
 *
 *
 * 대표자를 구해주는 Union&Find 집합에 코드를 살짝 변형해 주면 쉽게 풀 수 있는 문제였다
 * 2
 * 1 2 3 4
 * 1 1 3 4
 * 2
 * 1 2 3 4
 * 0 1 3 4 ( 여기서 2의 1은 추후에 find과정에서 어차피 0으로 바뀌게 된다 )
 * 3
 * 1 2 3 4
 * 0 0 0 4
 *
 * Union&Find를 복습하기 좋은 문제였다.
 * @see https://www.acmicpc.net/problem/10775
 * @since 2024. 07. 31
 */
public class BJ_G2_10775_공항 {

    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

    static int G, P, ans;
    static int[] parents;

    public static void main(String[] args) throws IOException {

        G = Integer.parseInt(input.readLine());
        P = Integer.parseInt(input.readLine());

        int planeNum = 0;
        boolean flag = true;
        make();
        for (int i = 0; i < P; i++) {
            planeNum = Integer.parseInt(input.readLine());
            int g = find(planeNum);
            if (g == 0) flag = false;
            if (flag) {
                ans++;
                union(g - 1, g);
            }
        }
        System.out.println(ans);
    }

    private static void make() {
        parents = new int[G + 1];
        for (int i = 0; i <= G; i++) {
            parents[i] = i;
        }
    }

    private static int find(int a) {
        if (parents[a] == a) return a;

        return parents[a] = find(parents[a]);
    }

    private static boolean union(int a, int b) { // 합칠거야
        int aRoot = find(a);
        int bRoot = find(b);
        if (aRoot == bRoot) return false; // 같은 집합이니까
        parents[bRoot] = aRoot;
        return true;
    }
}
