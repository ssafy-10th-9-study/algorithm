package bj.g5;

import java.io.*;
import java.util.*;

public class BJ_G5_14503_로봇청소기 {

    static StringTokenizer tokens;
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static int N, M;

    static int[][] map;
    static int[][] deltas = { {-1, 0}, {0, 1}, {1, 0}, {0, -1}}; // 북, 동, 남, 서

    public static void main(String[] args) throws IOException {

        tokens = new StringTokenizer(input.readLine());

        N = Integer.parseInt(tokens.nextToken());
        M = Integer.parseInt(tokens.nextToken());

        map = new int[N][M];

        tokens = new StringTokenizer(input.readLine());

        int x = Integer.parseInt(tokens.nextToken());
        int y = Integer.parseInt(tokens.nextToken());
        int d = Integer.parseInt(tokens.nextToken());

        Robot robot = new Robot(x, y, d);

        for (int i = 0; i < N; i++) {
            tokens = new StringTokenizer(input.readLine());
            for (int j = 0; j < M; j++) {
                map[i][j] = Integer.parseInt(tokens.nextToken());
            }
        }
        System.out.println(simulation(robot));
    }

    private static int simulation(Robot robot) {
        /*
        * 현재 칸이 아직 청소되지 않은 경우, 현재 칸을 청소한다.
        현재 칸의 주변
        4칸 중 청소되지 않은 빈 칸이 없는 경우,
            바라보는 방향을 유지한 채로 한 칸 후진할 수 있다면 한 칸 후진하고 1번으로 돌아간다.
            바라보는 방향의 뒤쪽 칸이 벽이라 후진할 수 없다면 작동을 멈춘다.
        현재 칸의 주변 4칸 중 청소되지 않은 빈 칸이 있는 경우,
            무조건!!!!!!!!!! 일단 반시계 방향으로 90도 회전한다.
            바라보는 방향을 기준으로 앞쪽 칸이 청소되지 않은 빈 칸인 경우 한 칸 전진한다.
            1번으로 돌아간다.
        * */
        int count = 0;
        boolean movedFlag;
        int check = -1;


        while (true) {
            movedFlag = false;

            int x = robot.x;
            int y = robot.y;
            int delta = robot.d;

            if (map[x][y] == 0) {
                map[x][y] = check--; // -1이 청소 했다는 뜻
                count++; // 청소한 카운트 올려
            }
            for (int i = 0; i < 4; i++) {
                int nx = x + deltas[delta][0];
                int ny = y + deltas[delta][1];
                if (isIn(nx, ny) && map[nx][ny] == 0) { // 앞에 치울꺼 있으면 반시계 방향으로 무조건 돌려야해
                    int nd = (robot.d - 1);
                    if(nd == -1) nd = 3;
                    nx = x + deltas[nd][0];
                    ny = y + deltas[nd][1];
                    if (isIn(nx, ny) && map[nx][ny] == 0) {
                        robot.setX(nx);
                        robot.setY(ny);
                        robot.setD(nd);
                        movedFlag = true;
                        break;
                    } else { // 어쨋든 90도로 꺾었으니까
                        robot.setD(nd);
                        movedFlag = true;
                        break;
                    }
                } else delta = (delta + 1) % 4;
            }
            if (movedFlag) continue;
//            4방향 다 돌았는데 청소 칸이 없어, 바라보는 방향 뒤로 후진
            int backDelta = (robot.d + 2) % 4;
            int nx = x + deltas[backDelta][0];
            int ny = y + deltas[backDelta][1];

            if(isIn(nx, ny) && map[nx][ny] != 1) {
                robot.setX(nx);
                robot.setY(ny);
                robot.setD(robot.d);// 뱡향은 유지하니까
            } else break;
        }

        return count;

    }

    private static boolean isIn(int nx, int ny) {
        return 0 <= nx && nx < N && 0 <= ny && ny < M;
    }

    private static class Robot {
        int x;
        int y;
        int d;

        public void setX(int x) {
            this.x = x;
        }

        public void setY(int y) {
            this.y = y;
        }

        public void setD(int d) {
            this.d = d;
        }

        public Robot(int x, int y, int d) {
            this.y = y;
            this.d = d;
            this.x = x;
        }
    }
}
