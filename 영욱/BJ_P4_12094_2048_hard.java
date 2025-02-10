package bj.p4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
 * 최대 10번 이동했을 때 만들 수 있는 가장 큰 수를 출력한다.
 * easy와 다르게 최대 10번이라 시간을 줄일 방법을 찾아야 한다.
 * 우선 Queue를 이용해서 블록을 합쳤는데 보기에는 더 직관적이지만 2배 작업량이 더 많은 것을 알 수 있다.
 * (움직이는 방향으로 한 번 싹 훑고, 그 다음 큐를 N만큼 또 뿌리기 때문)
 * 이것부터 줄여보자
 * 굳이 큐를 쓸 필요 없이 index를 두고,
 * 1. 현재 블록이 0이라면
 * 그냥 건너감
 * 2. 현재 블록이 전에 숫자랑 같다면
 * index로 찾아 전에 숫자와 합친 후 index+1 or index-1을 해준다
 * 3. 현재 블록이 전에 숫자랑 다르다면
 * index+1이나 -1자리에 지금 숫자를 살포시 얹고, index를 수정한다
 *
 * 이 과정이면 큐를 사용 안해도 합치는 과정을 한 번에 해결할 수 있다.
 *
 * 그리고 더 가지를 칠 수도 있다
 * @see https://www.acmicpc.net/problem/12094
 * @since 2025. 02. 11
 */

public class BJ_P4_12094_2048_hard {


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
        if (count == 10) {
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
        if (deltaFlag == 0) { // 상
            for (int j = 0; j < N; j++) {
                int prevBlock = 0;
                int index = 0;
                for(int i=0; i<N; i++) {
                    if(map[i][j] != 0) {
                        if(prevBlock == map[i][j]) {
                            map[index-1][j] = prevBlock * 2;
                            map[i][j] = 0;
                            prevBlock = 0;
                        }
                        else {
                            prevBlock = map[i][j];
                            map[i][j] = 0;
                            map[index][j] = prevBlock;
                            index++;
                        }
                    }
                }
            }
        } else if (deltaFlag == 1) { // 하
            for (int j = 0; j < N; j++) {
                int prevBlock = 0;
                int index = N-1;
                for(int i=N-1; i>=0; i--) {
                    if(map[i][j] != 0) {
                        if(prevBlock == map[i][j]) {
                            map[index+1][j] = prevBlock * 2;
                            map[i][j] = 0;
                            prevBlock = 0;
                        }
                        else {
                            prevBlock = map[i][j];
                            map[i][j] = 0;
                            map[index][j] = prevBlock;
                            index--;
                        }
                    }
                }
            }
        } else if (deltaFlag == 2) { // 좌
            for (int i = 0; i < N; i++) {
                int prevBlock = 0;
                int index = 0;
                for (int j = 0; j < N; j++) {
                    if(map[i][j] != 0) {
                        if(prevBlock == map[i][j]) {
                            map[i][index-1] = prevBlock * 2;
                            map[i][j] = 0;
                            prevBlock = 0;
                        }
                        else {
                            prevBlock = map[i][j];
                            map[i][j] = 0;
                            map[i][index] = prevBlock;
                            index++;
                        }
                    }
                }
            }
        } else if (deltaFlag == 3) { // 우
            for (int i = 0; i < N; i++) {
                int prevBlock = 0;
                int index = N-1;
                for (int j = N - 1; j >= 0; j--) {
                    if(map[i][j] != 0) {
                        if(prevBlock == map[i][j]) {
                            map[i][index+1] = prevBlock * 2;
                            map[i][j] = 0;
                            prevBlock = 0;
                        }
                        else {
                            prevBlock = map[i][j];
                            map[i][j] = 0;
                            map[i][index] = prevBlock;
                            index--;
                        }
                    }
                }
            }
        }
    }
}
