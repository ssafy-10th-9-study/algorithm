package bj.g3;

import java.io.*;
import java.util.*;

/**
 * @author 김영욱
 * @git
 * @performance
 * @category #
 * @note N×N 크기의 공간에 물고기 M마리와 아기 상어 1마리가 있다. 공간은 1×1 크기의 정사각형 칸으로 나누어져 있다.
 * 한 칸에는 물고기가 최대 1마리 존재한다. 아기 상어와 물고기는 모두 크기를 가지고 있고, 이 크기는 자연수이다.
 * 가장 처음에 아기 상어의 크기는 2이고, *아기 상어는 1초에 상하좌우로 인접한 한 칸씩 이동한다*.
 * <p>
 * 아기 상어는 자신의 크기보다 큰 물고기가 있는 칸은 지나갈 수 없고*, 나머지 칸은 모두 지나갈 수 있다.
 * 아기 상어는 자신의 크기보다 작은 물고기만 먹을 수 있다*. 따라서, 크기가 같은 물고기는 먹을 수 없지만, 그 물고기가 있는 칸은 지나갈 수 있다.
 * <p>
 * 아기 상어가 어디로 이동할지 결정하는 방법은 아래와 같다.
 * <p>
 * 더 이상 먹을 수 있는 물고기가 공간에 없다면 아기 상어는 엄마 상어에게 도움을 요청한다.
 * 먹을 수 있는 물고기가 1마리라면, 그 물고기를 먹으러 간다.
 * 먹을 수 있는 물고기가 1마리보다 많다면, 거리가 가장 가까운 물고기를 먹으러 간다.
 * 거리는 아기 상어가 있는 칸에서 물고기가 있는 칸으로 이동할 때, 지나야하는 칸의 개수의 최솟값이다.
 * 거리가 가까운 물고기가 많다면, 가장 위에 있는 물고기, 그러한 물고기가 여러마리라면, 가장 왼쪽에 있는 물고기를 먹는다.
 * 아기 상어의 이동은 1초 걸리고, 물고기를 먹는데 걸리는 시간은 없다고 가정한다.
 * 즉, 아기 상어가 먹을 수 있는 물고기가 있는 칸으로 이동했다면, 이동과 동시에 물고기를 먹는다. 물고기를 먹으면, 그 칸은 빈 칸이 된다.
 * <p>
 * 아기 상어는 자신의 크기와 같은 수의 물고기를 먹을 때 마다 크기가 1 증가한다.
 * 예를 들어, 크기가 2인 아기 상어는 물고기를 2마리 먹으면 크기가 3이 된다.
 * <p>
 * 공간의 상태가 주어졌을 때, 아기 상어가 몇 초 동안 엄마 상어에게 도움을 요청하지 않고 물고기를 잡아먹을 수 있는지 구하는 프로그램을 작성하시오.
 * <p>
 * 입력
 * 첫째 줄에 공간의 크기 N(2 ≤ N ≤ 20)이 주어진다.
 * <p>
 * 둘째 줄부터 N개의 줄에 공간의 상태가 주어진다. 공간의 상태는 0, 1, 2, 3, 4, 5, 6, 9로 이루어져 있고, 아래와 같은 의미를 가진다.
 * <p>
 * 0: 빈 칸
 * 1, 2, 3, 4, 5, 6: 칸에 있는 물고기의 크기
 * 9: 아기 상어의 위치
 * 아기 상어는 공간에 한 마리 있다.
 * <p>
 * 처음 아기 상어 크기 : 2
 * 물고기가 작거나 같은 크기면 지나갈 수 있음, 단 같은 크기면 먹지는 못함.
 * 더 작다면 먹으면서 지나갈 수 있음
 * <p>
 * 먹을 수 있는 물고기가 1마리라면, 그 물고기를 먹으러 감
 * 먹을 수 있는 물고기가 1마리보다 많다면, 거리가 가장 가까운 물고기를 먹으러 간다.
 * <p>
 * 아기 상어는 자기 자신의 크기와 같은 수의 물고기를 먹을 때마다 크기가 1 증가한다.
 * ex) 크기 : 2 => 2마리 => 크기 : 3, 크기 : 3 => 3마리 => 크기 : 4
 * 먹는 물고기의 크기랑은 상관 없음.
 * 우선순위 : 가장 가까운 물고기 > 가장 위에 있는 물고기 > 가장 왼쪽에 있는 물고기
 * 먹고 난 다음에 먹을게 있는지 어떻게 알려줘야 할까? 미리 알아야 더 헤메기 전에 엄마 부를꺼 아니야
 * bfs를 계속 돌아 -> 먹은 놈 저장할 객체 선언해놔( 이동시간은 특정 값으로 ) -> 이동하는 곳은 Queue에 담아 -> 먹은 곳은 bfs안에서 따로 저장해놔 -> 또 다른 곳을 먹게 된다면
 * 전에 먹었던 놈이랑 비교를해( 가장 가까운 > 가장 위에 > 가장 왼쪽에 ) -> 제일 적합한 놈 리턴
 * 리턴이 됐으면 리턴 된 놈의 이동시간이 특정 값이라면 더 이상 먹을 곳을 못찾은거고,
 * 제대로 값이 리턴 됐으면 그 놈이 최적의 물고기를 먹고 난 상어의 위치, 상태인거임
 * 계속 특정값을 리턴할 때 까지 bfs를 돌리고, 특정값이 나온다면 전에 저장되어 있던 상어의 이동시간이 최소 값이다
 * @see https://www.acmicpc.net/problem/16236
 * @since 2024. 07.02 , 07. 09( 2일 걸림 )
 */

public class BJ_G3_16236_아기상어 {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder builder = new StringBuilder();
    static StringTokenizer tokens;

    static int N, ans;
    static int[][] map;
    static int[][] deltas = {{-1, 0}, {0, -1}, {1, 0}, {0, 1}};
    static BabyShark answerShark;

    public static void main(String[] args) throws IOException {
        N = Integer.parseInt(input.readLine());
        ans = 0;
        map = new int[N][N];
        boolean nothingToEat = false;
        int startX = 0;
        int startY = 0;

        for (int i = 0; i < N; i++) {
            tokens = new StringTokenizer(input.readLine());
            for (int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(tokens.nextToken());
                if (map[i][j] == 9) {
                    startX = i;
                    startY = j;
                    map[i][j] = 0;
                } else if (map[i][j] != 0) nothingToEat = true;
            }
        }

        if (!nothingToEat) {
            System.out.println(0);
        } else {
            answerShark = new BabyShark(2, 0, startX, startY, 0);
            while (true) {
                BabyShark fullShark = letsEat(answerShark.weight, answerShark.fishCnt, answerShark.x, answerShark.y, answerShark.sec);
                if (fullShark.sec != Integer.MAX_VALUE) {
                    answerShark = fullShark;
                    map[fullShark.x][fullShark.y] = 0;
                }
                else break;
            }
            System.out.println(answerShark.sec);
        }
    }

    private static BabyShark letsEat(int weight, int fishCnt, int startX, int startY, int sec) {
        boolean[][] visited = new boolean[N][N];
        Queue<BabyShark> q = new ArrayDeque<>();
        q.offer(new BabyShark(weight, fishCnt, startX, startY, sec));
        visited[startX][startY] = true;
        BabyShark returnShark = new BabyShark(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE);

        while (!q.isEmpty()) {
            BabyShark now = q.poll();

            if(now.sec >= returnShark.sec) break;

            for (int i = 0; i < 4; i++) {
                int dx = now.x + deltas[i][0];
                int dy = now.y + deltas[i][1];
                BabyShark next = new BabyShark(now.weight, now.fishCnt, dx, dy, now.sec);
                if (isIn(dx, dy) && !visited[dx][dy]) {
                    int eatFlag = next.move(dx, dy);
                    if (eatFlag > 0) { // 먹었어
                        if (returnShark.sec > next.sec) {
                            returnShark = next;
                        } else if (returnShark.sec == next.sec) {
                            if (returnShark.x > next.x) {
                                returnShark = next;
                            } else if (returnShark.x == next.x) {
                                if (returnShark.y > next.y) {
                                    returnShark = next;
                                }
                            }
                        }
                    } else if (eatFlag == 0) { // 이동만 했어
                        q.offer(new BabyShark(next.weight, next.fishCnt, next.x, next.y, next.sec));
                        visited[next.x][next.y] = true;
                    }
                }
            }
        }
        return returnShark;
    }

    static private boolean isIn(int dx, int dy) {
        return 0 <= dx && dx < N && 0 <= dy && dy < N;
    }

    static class BabyShark {
        int weight;
        int fishCnt;
        int x;
        int y;
        int sec;

        @Override
        public String toString() {
            return "BabyShark{" +
                           "weight=" + weight +
                           ", fishCnt=" + fishCnt +
                           ", x=" + x +
                           ", y=" + y +
                           ", sec=" + sec +
                           '}';
        }

        public BabyShark(int weight, int fishCnt, int x, int y, int sec) {
            this.weight = weight;
            this.fishCnt = fishCnt;
            this.x = x;
            this.y = y;
            this.sec = sec;
        }

        public void eat() {
            fishCnt++;
            if (fishCnt == weight) {
                this.weight++;
                this.fishCnt = 0;
            }
        }

        public int move(int dx, int dy) {
            if (map[dx][dy] <= this.weight) { // 지나갈 수 있냐?
                this.sec++;
                if (map[dx][dy] > 0 && map[dx][dy] < this.weight) {// 먹을 수 있냐?
                    eat();
                    return 1;
                }
                return 0;
            }
            return -1;
        }
    }
}
