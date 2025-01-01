package bj.g1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

/**
 * @author 김영욱
 * @git
 * @performance
 * @category #
 * @note 시간 : 1초
 * 이 게임에서 한 번의 이동은 보드 위에 있는 전체 블록을 상하좌우 네 방향 중 하나로 이동시키는 것이다.
 * 이때, 같은 값을 갖는 두 블록이 충돌하면 두 블록은 하나로 합쳐지게 된다.
 * 한 번의 이동에서 이미 합쳐진 블록은 또 다른 블록과 다시 합쳐질 수 없다.
 * <p>
 * 2 4 8 2
 * 2 4 2
 * 2
 * 2
 * 위로 이동 하면
 * <p>
 * 4 8 8 2
 * 4   2
 * <그림 12>의 경우에 위로 블록을 이동시키면 <그림 13>의 상태가 되는데,
 * 그 이유는 한 번의 이동에서 이미 합쳐진 블록은 또 합쳐질 수 없기 때문이다.
 * <p>
 * 2
 * 2 2
 * 2
 * 위로 이동 후
 * 4 2
 * 2
 * 마지막으로, 똑같은 수가 세 개가 있는 경우에는 이동하려고 하는 쪽의 칸이 먼저 합쳐진다.
 * 예를 들어, 위로 이동시키는 경우에는 위쪽에 있는 블록이 먼저 합쳐지게 된다.
 * <p>
 * 합칠 때 그 방향으로 검사해줘야 하는 것들이 꽤 있는 까다로운 문제이다.
 * <그림 14>의 경우에 위로 이동하면 <그림 15>를 만든다.
 * <p>
 * 최대 5번 이동했을 때 만들 수 있는 가장 큰 수를 출력한다.
 * <p>
 * 상하좌우 블록을 옮기는 함수
 * 좌면 왼쪽부터, 우면 오른쪽부터 이런식으로 큐에 넣고 들어갈 때 마다 합쳐질 수 있는지 확인( 0이면 큐에 넣지 않는다 )
 * 합쳐지면 앞에꺼 없애고 다시 넣으면 그만
 * 합쳐진거 확인하는 방법은 클래스를 하나 만들어서 boolean으로 판별해야 할듯
 * <p>
 * 4^5만큼의 경우의 수를 만들어내는 문제 같은데, 이 경우의 수를 줄일 수 있는 방법은 없을까?
 * @see https://www.acmicpc.net/problem/12100
 * @since 2025. 01. 01
 */

public class BJ_G1_12100_2048 {


    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer tokens;

    static int N;
    static int[][] map;
    static int answer;


    public static void main(String[] args) throws IOException {
        N = Integer.parseInt(input.readLine());
        map = new int[N][N];
        for (int i = 0; i < N; i++) {
            tokens = new StringTokenizer(input.readLine());
            for (int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(tokens.nextToken());
            }
        }

        dfs(0);
        System.out.println(answer);

    }

    private static void dfs(int count) {
//        기저 조건
        if (count == 5) {
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    answer = Math.max(answer, map[i][j]);
                }
            }
            return;
        }
        int[][] copyMap = new int[N][N];// 현재 map상태를 저장하는 변수
        // 재귀 끝에 다다르고 다른 방향으로 가야 할 때 바로 전 map 상태가 필요하기 때문

        for (int i = 0; i < N; i++) {
            copyMap[i] = map[i].clone();
        }

        for (int d = 0; d < 4; d++) {
            moveBlock(d);
            dfs(count + 1);
            for (int i = 0; i < N; i++) {
                map[i] = copyMap[i].clone();
            }
        }
    }

    private static void moveBlock(int deltaFlag) {
        Deque<block> q = new ArrayDeque<>();

        if (deltaFlag == 0) { // 상
            for (int j = 0; j < N; j++) {
                q.addLast(new block(map[0][j], false));
                for (int i = 1; i < N; i++) checkCanMerge(q, i, j);

                for (int i = 0; i < N; i++) blockRelease(q, i, j);
            }
        } else if (deltaFlag == 1) { // 하
            for (int j = 0; j < N; j++) {
                q.addLast(new block(map[N - 1][j], false));
                for (int i = N - 2; i >= 0; i--) checkCanMerge(q, i, j);

                for (int i = N - 1; i >= 0; i--) blockRelease(q, i, j);

            }
        } else if (deltaFlag == 2) { // 좌
            for (int i = 0; i < N; i++) {
                q.addLast(new block(map[i][0], false));
                for (int j = 1; j < N; j++) checkCanMerge(q, i, j);

                for (int j = 0; j < N; j++) blockRelease(q, i, j);
            }
        } else if (deltaFlag == 3) { // 우
            for (int i = 0; i < N; i++) {
                q.addLast(new block(map[i][N - 1], false));
                for (int j = N - 2; j >= 0; j--) checkCanMerge(q, i, j);

                for (int j = N - 1; j >= 0; j--) blockRelease(q, i, j);
            }
        }
    }

    private static void checkCanMerge(Deque<block> q, int i, int j) {
        if (!q.isEmpty()) {
            if (map[i][j] == 0) return;  // 지금 움직일 map의 값이 0이면 걍 안넣어도 되니까
            block preBlock = q.removeLast();
            if (preBlock.num == 0) {
                q.addLast(new block(map[i][j], false));
            } else if (preBlock.num == map[i][j] && !preBlock.isMerged) { // 앞에꺼랑 같다면 합쳐
                q.addLast(new block(preBlock.num + map[i][j], true));
            } else {
                q.addLast(preBlock);
                q.addLast(new block(map[i][j], false));
            }
        } else {
            System.out.println("큐가 비어있는데?ㅋㅋ");
        }
    }

    private static void blockRelease(Deque<block> q, int i, int j) {
        if (!q.isEmpty()) {
            block b = q.removeFirst();
            map[i][j] = b.num;
        } else {
            map[i][j] = 0;
        }
    }



    static class block {
        int num;
        boolean isMerged;

        public block(int num, boolean isMerged) {
            this.num = num;
            this.isMerged = isMerged;
        }

    }
}
