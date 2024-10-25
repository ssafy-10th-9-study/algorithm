package bj.g4;

import java.io.*;
import java.util.*;
/**
 @author 김영욱
 @since 2024.07.26
 @see https://www.acmicpc.net/problem/3190
 @git
 @performance 88ms
 @category #시뮬레이션, 덱
 @note
 시간 1초

 뱀이 돌아다니면서 사과를 먹으면 몸이 늘어난다.
 벽이나 지 몸에 부딪히면 게임 종료
 게임 종료시 시간을 출력하면 된다.

 한 칸씩 늘어나고 줄어드는 과정이 있어야 하다 보니 리스트 맨 앞, 맨 뒤 값 입력 출력이 원활한 Deque를 생각하게 되었다
 그리고 시키는대로 하면 되는데.. 세세하게 틀린 점이 있다보니 오류를 많이 범하였다

 주의점 1. 충돌 체크하기 전 머리를 늘리고 그 머리를 큐에 넣고 충돌 검사를 했어야 했는데
 이 과정이 잘못되어 디버깅에 시간이 오래 걸렸다.
 주의점 2. 입력을 다 받은 후에도 계속해서 이동할 수 있다면 이동해야하기에 while문을 추가해주었다.

 */

public class BJ_G4_3190_뱀 {

    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer tokens;

    static int[][] map;

    static int N, K, L;
    static boolean gameFlag;

    public static void main(String[] args) throws IOException {
        N = Integer.parseInt(input.readLine());
        map = new int[N + 1][N + 1];
        K = Integer.parseInt(input.readLine());
        gameFlag = true;

        int row, col;
        for (int i = 0; i < K; i++) {
            tokens = new StringTokenizer(input.readLine());
            row = Integer.parseInt(tokens.nextToken());
            col = Integer.parseInt(tokens.nextToken());

            map[row][col] = 1;
        }
        map[1][1] = 2; // 뱀의 위치
        L = Integer.parseInt(input.readLine());

        char direction = ' ';
        int sec = 0;

        Snake snake = new Snake();
        for (int i = 0; i < L; i++) {
            tokens = new StringTokenizer(input.readLine());
            sec = Integer.parseInt(tokens.nextToken());
            direction = tokens.nextToken().charAt(0);


            while(gameFlag && snake.sec < sec) {
                snake.sec++;
                snake.move();
            }
            snake.changeDirection(direction);
        }
        while(gameFlag) {
            snake.sec++;
            snake.move();
        }
        System.out.println(snake.sec);
    }

    static class Snake {
        int x, y, sec;
        char direction;
        ArrayDeque<int []> q;

        Snake() {
            this.x = 1;
            this.y = 1;
            this.sec = 0;
            this.direction = 'R';
            this.q = new ArrayDeque<>();
            q.offer(new int[] {this.x, this.y});
        }

        public void move() {
            if(this.direction == 'R') {
                this.y++;
            }
            else if(this.direction == 'L') {
                this.y--;
            }
            else if(this.direction == 'T') {
                this.x--;
            }
            else if(this.direction == 'D') {
                this.x++;
            }
            q.addFirst(new int[]{this.x, this.y});

            if(isIn(this.x, this.y)) {
                if(!isCrash()) {
                    if(isStretch()) {
                        map[this.x][this.y] = 0;
                    }
                    else {
                        map[this.x][this.y] = 0;
                        q.pollLast();
                    }
                }
                else {
                    gameFlag = false;
                }
            }
            else { // 벽에 대가리 박음
                gameFlag = false;
            }
        }

        public void changeDirection(char direction) {
            if(direction == 'L') {
                if(this.direction == 'R') {
                    this.direction = 'T';
                }
                else if(this.direction == 'L') {
                    this.direction = 'D';
                }
                else if(this.direction == 'T') {
                    this.direction = 'L';
                }
                else if(this.direction == 'D') {
                    this.direction = 'R';
                }
            }
            else if(direction == 'D') {
                if(this.direction == 'R') {
                    this.direction = 'D';
                }
                else if(this.direction == 'L') {
                    this.direction = 'T';
                }
                else if(this.direction == 'T') {
                    this.direction = 'R';
                }
                else if(this.direction == 'D') {
                    this.direction = 'L';
                }
            }
        }

        public boolean isCrash() {
            int[] head = q.poll();
            for(int [] now: q) {
                if(this.x == now[0] && this.y == now[1]) {
                    return true;
                }
            }
            q.addFirst(head);
            return false;
        }

        public boolean isStretch() {
            return map[this.x][this.y] == 1;
        }

        public boolean isIn(int x, int y) {
            return 0 < x && x <= N && 0 < y && y <= N;
        }
    }
}
