package bj.g2;

import java.io.*;
import java.util.*;

/**
 @author 김영욱
 @since 2024. 09. 14
 @see https://www.acmicpc.net/problem/1202
 @git
 @performance
 @category #
 @note
 문제
 세계적인 도둑 상덕이는 보석점을 털기로 결심했다.
 상덕이가 털 보석점에는 보석이 총 N개 있다. 각 보석은 무게 Mi와 가격 Vi를 가지고 있다.
 상덕이는 가방을 K개 가지고 있고, 각 가방에 담을 수 있는 최대 무게는 Ci이다.
 *가방에는 최대 한 개의 보석만 넣을 수 있다.*

 상덕이가 훔칠 수 있는 보석의 최대 가격을 구하는 프로그램을 작성하시오.

 입력
 첫째 줄에 N과 K가 주어진다. (1 ≤ N, K ≤ 300,000)
 다음 N개 줄에는 각 보석의 정보 Mi와 Vi가 주어진다. (0 ≤ Mi, Vi ≤ 1,000,000)
 다음 K개 줄에는 가방에 담을 수 있는 최대 무게 Ci가 주어진다. (1 ≤ Ci ≤ 100,000,000)
 모든 숫자는 양의 정수이다.
 출력
 첫째 줄에 상덕이가 훔칠 수 있는 보석 가격의 합의 최댓값을 출력한다.

 입력
 3 2
 1 65
 5 23
 2 99
 10
 2
 출력
 164

 1안
 일단 보석은 가장 값이 큰 순서대로 초기화해야해( PQ )
 가방은 제일 작아야 제일 무게가 작으면서 큰 놈들 순서대로 가방에 집어넣을 수 있어( sort )

 그래서 보석 돌면서 가장 작은 무게로 가장 큰 값을 넣을 수 있는 가방을 순회해
 근데 이런식으로 하면 최악에는 30만 * 30만이라 무조건 시간초과
 사실상 2중 반복문이라서..

 2안( 은서파님의 힌트를 1분 봤음 )
 일단 선택되면 없어져야해
 PQ를 두 개 써보자

 가방은 용량이 작은 순서대로 가는게 맞아( bags )
 보석은 이번에는 무게가 가장 작은 순서대로 넣어보자( jewels )
 그리고 가방에 들어갈 수 있는 보석들을 따로 PQ( choosenJewels 줄여서 CJ )에 담고 이놈들은 가장 가치가 높은 순서대로 정렬하자

 가방 돌면서 -> 보석 들어갈 수 있다면 poll로 빼고, CJ에 넣자
 CJ에서 가장 가치가 높은 놈을 정답으로 채택
 이러면 자연스럽게 다시 돌아 볼 보석 수가 줄어든다.

 jewels
 1 99
 2 65
 5 1000

 bags
 2
 10

 2 가방

 CJ
 1 99
 2 65

 a == 99

 10가방

 jewels
 5 1000

 CJ
 5 1000
 2 65

 a == 1099

 CJ는 가치로 정렬하고 한 놈만 뽑아 쓰는게 포인트...
 */

public class BJ_G2_1202_보석도둑 {

    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer tokens;

    static int N,K;
    static PriorityQueue<jewel> jewels;
    static PriorityQueue<Integer> choosenJewels;
    static int[] bags;
    static long ans;


    public static void main(String[] args) throws IOException {
        tokens = new StringTokenizer(input.readLine());
        N = Integer.parseInt(tokens.nextToken());
        K = Integer.parseInt(tokens.nextToken());

        jewels = new PriorityQueue<>();
        bags = new int[K];
        for(int i=0; i<N; i++) {
            tokens = new StringTokenizer(input.readLine());
            jewels.offer(new jewel(Integer.parseInt(tokens.nextToken()), Integer.parseInt(tokens.nextToken())));
        }
        for(int i=0; i<K; i++) {
            bags[i] = Integer.parseInt(input.readLine());
        }
        Arrays.sort(bags);

        choosenJewels = new PriorityQueue<>(Comparator.reverseOrder());// 내림차순

        for(int k=0; k<K; k++) {
            while(!jewels.isEmpty() && jewels.peek().weight <= bags[k]){
                choosenJewels.offer(jewels.poll().value);
            }
            if(!choosenJewels.isEmpty()) {
                ans += choosenJewels.poll();
            }
        }
        System.out.println(ans);


    }

    private static class jewel implements Comparable<jewel>{
        int weight;
        int value;

        @Override
        public String toString() {
            return "jewel{" +
                    "weight=" + weight +
                    ", value=" + value +
                    '}';
        }

        public jewel (int weight, int value) {
            this.weight = weight;
            this.value = value;
        }

        @Override
        public int compareTo(jewel o) {
            return Integer.compare(this.weight, o.weight);
        }
    }
}
