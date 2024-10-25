package bj.g2;

import java.io.*;
import java.util.*;

/**
 * @author 김영욱
 * @git
 * @performance
 * @category #위상정렬 + 우선순위 큐
 * @note 조건
 * 문제의 수 N (1<= N <= 32,000)
 * 정보의 개수 M (1<= M <= 100,000)
 * 1~N문제가 있는데 1번 문제가 가장 쉬운 문제이고 N번 문제가 가장 어려운 문제이다.
 * 순서쌍 (A,B) A번 문제는 B번 문제보다 먼저 푸는 것이 좋다는 뜻이다.
 * <p>
 * a) N개의 문제를 모두 풀어야 한다.  → 그래프 완전 탐색
 * b) 먼저 푸는 것이 좋은 문제가 있는 문제는, 먼저 푸는 것이 좋은 문제를 반드시 먼저 풀어야 한다. → 위상 정렬
 * c) 가능하면 쉬운 문제부터 풀어야 한다.  → 우선순위 큐
 * <p>
 * 평범한 위상정렬과는 다르게 가능하면 쉬운 문제( 숫자가 낮은 문제 )를 먼저 풀어야 하기 때문에
 * 그냥 큐가 아닌 우선순위 큐를 사용해야 한다.
 * <p>
 * 예를 들어서 네 개의 문제가 있다고 하자. 4번 문제는 2번 문제보다 먼저 푸는 것이 좋고, 3번 문제는 1번 문제보다 먼저 푸는 것이 좋다고 하자.
 * 만일 4-3-2-1의 순서로 문제를 풀게 되면 조건 1과 조건 2를 만족한다. 하지만 조건 3을 만족하지 않는다.
 * 4보다 3을 충분히 먼저 풀 수 있기 때문이다. 따라서 조건 3을 만족하는 문제를 풀 순서는 3-1-4-2가 된다.
 * <p>
 * inDegree 1 2 3 4   input : 4 2
 * 1
 * inDegree 1 2 3 4   input : 3 1
 * 1 1
 * <p>
 * graph 4 -> 2 , 3 -> 1
 * <p>
 * 그냥 큐를 사용할 경우 3 -> 4 -> 1 -> 2
 * 우선 순위 큐를 사용할 경우 3 -> 1 -> 4 -> 2
 * <p>
 * <p>
 * 위상정렬을 다 까먹어서 원래 풀었었던 '줄 세우기' 문제의 코드를 참고해서 풀었음
 * https://loosie.tistory.com/247 -> 설명을 위해 참고한 블로그
 * @see https://www.acmicpc.net/problem/1766
 * @since 2024. 07. 27
 */

public class BJ_G2_1766_문제집 {

    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer tokens;
    static StringBuilder builder = new StringBuilder();

    static int[] inDegree;

    static ArrayList<ArrayList<Integer>> graph;
    static PriorityQueue<Integer> pq = new PriorityQueue<>();
    static int N, M;

    public static void main(String[] args) throws IOException {

        tokens = new StringTokenizer(input.readLine());
        N = Integer.parseInt(tokens.nextToken());
        M = Integer.parseInt(tokens.nextToken());

        inDegree = new int[N + 1];
        graph = new ArrayList<>();

        for (int i = 0; i <= N; i++) {
            graph.add(new ArrayList<>());
        }

        for (int i = 0; i < M; i++) {

            tokens = new StringTokenizer(input.readLine());
            int from = Integer.parseInt(tokens.nextToken());
            int to = Integer.parseInt(tokens.nextToken());

            graph.get(from).add(to);
            inDegree[to]++;
        }

        for (int i = 1; i <= N; i++) {
            if (inDegree[i] == 0) {
                pq.offer(i);
            }
        }

        bfs();
        System.out.println(builder);
    }

    private static void bfs() {
        while (!pq.isEmpty()) {
            int now = pq.poll();
            builder.append(now).append(" ");
            for (int quiz : graph.get(now)) {
                inDegree[quiz] -= 1;
                if (inDegree[quiz] == 0) {
                    pq.offer(quiz);
                }
            }
        }
    }
}
