package bj.g5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.StringTokenizer;

/**
 * @author 김영욱
 * @git
 * @performance 1280ms
 * @category #시뮬레이션
 * @note 시간 : 1초
 * 길이 N인 컨베이어 벨트가 있고, 길이 2N짜리 벨트가 위아래로 덮고 있다
 * 1에 로봇을 올리고, N에 로봇이 하차한다.
 * 로봇은 벨트 위에서 스스로 움직일 수 있다.
 *
 * 1. 벨트가 각 칸 위에 있는 로봇과 함께 한 칸 회전한다.
 * 2. 가장 먼저 벨트에 올라간 로봇부터, 벨트가 회전하는 방향으로 한 칸 이동할 수 있다면 이동한다.
        만약 이동할 수 없다면 가만히 있는다.
        1.로봇이 이동하기 위해서는 이동하려는 칸에 로봇이 없으며, 그 칸의 내구도가 1 이상 남아 있어야 한다.
 * 3. 올리는 위치에 있는 칸의 내구도가 0이 아니면 올리는 위치에 로봇을 올린다.
 * 4. 내구도가 0인 칸의 개수가 K개 이상이라면 과정을 종료한다. 그렇지 않다면 1번으로 돌아간다.
 *
 * 구현 문제는 이해가 참 어려운 것 같다... 인터넷의 도움을 살짝 받았다.
 *
 * 처음에는 덱을 생각했으나 N의 인덱스에서 로봇이 내려야 하기 때문에 인덱스 접근이 불가능해서 실패.
 * 원형버퍼를 생각했으나 구현하기 귀찮아서 그냥 연결리스트로 구현하였다.
 * 자바에서 연결리스트를 사용하는 것은 처음이여서 인터넷 코드를 좀 참조하였다.
 *
 *
 * @see https://www.acmicpc.net/problem/20055
 * @since 2024. 08. 05
 */

public class BJ_G5_20055_컨베이어벨트위의로봇 {

    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer tokens;

    static int N, K;
    static int step, zeroCnt;
    static LinkedList<Belt> belt = new LinkedList<>();
    public static void main(String[] args) throws IOException {
        tokens = new StringTokenizer(input.readLine());
        N = Integer.parseInt(tokens.nextToken());
        K = Integer.parseInt(tokens.nextToken());

        tokens = new StringTokenizer(input.readLine());
        for(int i=0; i<2*N; i++) {
            int health = Integer.parseInt(tokens.nextToken());
            belt.add(i, new Belt(health));
        }

        while(zeroCnt < K) {
            step++;
            moveBelt();
            moveRobot();
            if(belt.get(0).health > 0) belt.get(0).layupRobot();

//            printBelt();
        }
        System.out.println(step);
    }

    private static void printBelt() {
        for(Belt now: belt) {
            if (now.isRobot) {
                System.out.print("(" + now.health + ") ");
            }
            else {
                System.out.print(now.health + " ");
            }
        }
        System.out.print(" zeroCnt : " + zeroCnt);
        System.out.println();
    }

    private static void moveBelt() {
        Belt lastOne = belt.removeLast();
        belt.add(0, lastOne);
        if(belt.get(N-1).isRobot) {
            belt.get(N-1).isRobot = false;
        }
    }

    private static void moveRobot() {
//        0부터 정방향으로 로봇을 옮긴다면 벨트 위 로봇의 유뮤, 내구성이 영향을 받게됨
        for(int i=N-2; i>=0; i--) {
//            현재 벨트에 로봇 없으면
            if(!belt.get(i).isRobot) continue;
//            다음 벨트의 내구도가 0이거나 다음 벨트에 로봇이 이미 있다면
            if(belt.get(i+1).health <= 0 || belt.get(i+1).isRobot) continue;

            belt.get(i).isRobot = false;
            belt.get(i+1).layupRobot();

//            N이면 내려
            if(i + 1 == N-1) belt.get(i+1).isRobot = false;
        }
    }


    private static class Belt{
        int health;
        boolean isRobot;
        boolean zeroCheck;

        public Belt(int health) {
            this.health = health;
            isRobot = false;
            zeroCheck = false;
        }

        public void layupRobot() {
            isRobot = true;
            health--;
            if(this.health <= 0) {
                zeroCnt++;
                this.zeroCheck = true;
            }
        }

    }
}
