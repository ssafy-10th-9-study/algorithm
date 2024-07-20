import java.util.*;

class PGS_250134_수레움직이기 {
    boolean[][] rVisited, bVisited;
    int rStaX, rStaY, bStaX, bStaY;
    int[] dx = new int[]{1, -1, 0, 0};
    int[] dy = new int[]{0, 0, 1, -1};
    int answer;

    public boolean isIn(int x, int y, int[][] arr) {
        return x >= 0 && x < arr.length && y >= 0 && y < arr[0].length;
    }

    public void solution(int rx, int ry, int bx, int by, int dep, int[][] maze) {
        if (maze[rx][ry] == 3 && maze[bx][by] == 4) {
            answer = Math.min(answer, dep);
            return ;
        }
        for (int i = 0; i < dx.length; i++) {
            int rnx = rx + dx[i];
            int rny = ry + dy[i];
            if (maze[rx][ry] == 3) { // 고정
                for (int j = 0; j < dy.length; j++) {
                    int bnx = bx + dx[j];
                    int bny = by + dy[j];
                    if (isIn(bnx, bny, maze) && !bVisited[bnx][bny] && (bnx != rx || bny != ry)
                            && maze[bnx][bny] != 5) {
                        bVisited[bnx][bny] = true;
                        solution(rx, ry, bnx, bny, dep + 1, maze);
                        bVisited[bnx][bny] = false;
                    }
                }
                break;
            } else if (isIn(rnx, rny, maze) && !rVisited[rnx][rny] && maze[rnx][rny] != 5) {
                rVisited[rnx][rny] = true;
                if (maze[bx][by] == 4 && (rnx != bx || rny != by)) {
                    solution(rnx, rny, bx, by, dep + 1, maze);
                } else {
                    for (int j = 0; j < dy.length; j++) {
                        int bnx = bx + dx[j];
                        int bny = by + dy[j];
                        if (isIn(bnx, bny, maze) && !bVisited[bnx][bny] && (bnx != rnx || bny != rny)
                                && !(bnx == rx && bny == ry && rnx == bx && rny == by) && maze[bnx][bny] != 5) {
                            bVisited[bnx][bny] = true;
                            solution(rnx, rny, bnx, bny, dep + 1, maze);
                            bVisited[bnx][bny] = false;
                        }
                    }
                }
                rVisited[rnx][rny] = false;
            }
        }
    }

    public int solution(int[][] maze) {
        answer = Integer.MAX_VALUE;
        rVisited = new boolean[maze.length][maze[0].length];
        bVisited = new boolean[maze.length][maze[0].length];
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                int state = maze[i][j];
                switch (state) {
                    case 1: // 빨 시작
                        rStaX = i;
                        rStaY = j;
                        break;
                    case 2: // 파 시작
                        bStaX = i;
                        bStaY = j;
                        break;
                    default:
                        break;
                }
            }
        }
        rVisited[rStaX][rStaY] = bVisited[bStaX][bStaY] = true;
        solution(rStaX, rStaY, bStaX, bStaY, 0, maze);
        if (answer == Integer.MAX_VALUE) {
            answer = 0;
        }
        return answer;
    }
}