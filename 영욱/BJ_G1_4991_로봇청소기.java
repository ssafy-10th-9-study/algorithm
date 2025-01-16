package bj.g1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * @author 김영욱
 * @git
 * @performance 648ms
 * @category # 순열, BFS
 * @note 시간 1초
 *
 * N이 최대값인 10이라면 순열 구하는데만 350만번
 * N이 11일때 11 * 20 * 20 == 4400번
 * 어느 좌표를 가는데 걸리는 시간을 구할게 아니라 그냥 N(쓰레기 위치)에서 다른 모든 좌표로 가는데 걸리는 시간을
 * flood fill로 채워놓고 순열 나오면 대입만 하면 끝
 *
 * @see https://www.acmicpc.net/problem/4991
 * @since 2025.01.17
 */

public class BJ_G1_4991_로봇청소기 {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer tokens;
    static StringBuilder builder = new StringBuilder();

    static int deltas[][] = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    static char[][] map;
    static int N, M;
    static ArrayList<int[]> dustCordList;
    static int[][][][] recordBoard;
    static int answer;
    static int[] indexList;

    public static void main(String[] args) throws IOException {
        while (true) {
            answer = Integer.MAX_VALUE;
            tokens = new StringTokenizer(input.readLine());
            M = Integer.parseInt(tokens.nextToken());
            N = Integer.parseInt(tokens.nextToken());
            map = new char[N][M];
            if (N == 0 && M == 0) break;
            for (int i = 0; i < N; i++) {
                String temp = input.readLine();
                map[i] = temp.toCharArray();
            }
            dustCordList = new ArrayList<>();
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < M; j++) {
                    if (map[i][j] == '*') {
                        dustCordList.add(new int[]{i, j});
                    } else if (map[i][j] == 'o') {
                        if (dustCordList.isEmpty()) dustCordList.add(new int[]{i, j});
                        else {
                            int[] temp = dustCordList.remove(0);
                            dustCordList.add(0, new int[]{i, j});
                            dustCordList.add(temp);
                        }
                    }
                }
            }
            recordBoard = new int[N][M][N][M];
            for(int[] cord: dustCordList) bfs(cord);// 각 먼지에서 다른 좌표로 이동할 때 생기는 시간
            indexList = new int[dustCordList.size()-1];// 청소기 하나 빼줌
            for(int i=0; i<indexList.length; i++) indexList[i] = i+1;// 청소기 0이니까 1부터 값넣어줌
            permutation(0,new int[indexList.length], new boolean[indexList.length]);
            if (answer == Integer.MAX_VALUE) builder.append("-1").append("\n");
            else builder.append(answer).append("\n");
        }
        System.out.println(builder);
    }

    private static void bfs(int[] startIndex) {
        int startX = startIndex[0];
        int startY = startIndex[1];
        boolean[][] visited = new boolean[N][M];
        Queue<int[]> q = new ArrayDeque<>();
        q.offer(new int[]{startX, startY, 0});
        visited[startX][startY] = true;

        while (!q.isEmpty()) {
            int[] now = q.poll();
            int x = now[0];
            int y = now[1];
            int count = now[2];
            for (int d = 0; d < 4; d++) {
                int nx = x + deltas[d][0];
                int ny = y + deltas[d][1];
                if (isIn(nx, ny) && !visited[nx][ny] && map[nx][ny] != 'x') {
                    visited[nx][ny] = true;
                    q.offer(new int[]{nx, ny, count+1});
                    recordBoard[startX][startY][nx][ny] = count + 1;
                }
            }
        }

    }

    private static void permutation(final int nthChoice, int[] choosed, boolean[] visited) {
        if (nthChoice == choosed.length) {
//            ToDo: 나온 순열 대로, 몇번째 먼지에서 몇번째 먼지로 가는 최소 거리를 map에서 찾으면 끝
            int[] before = dustCordList.get(0);
            int[] now = null;
            int nowPermutationTotal = 0;
            int time = 0;
            for(int destIndex: choosed) {
                now = dustCordList.get(destIndex);// 가야할 먼지 좌표
                time = recordBoard[before[0]][before[1]][now[0]][now[1]];// 출발 좌표부터 걸리는 시간
                if(time == 0) return;// 0이면 도달한적이 없으니까 리턴
                nowPermutationTotal += time;
                before = now;// 지금 좌표를 출발 좌표로 초기화
                time = 0;
            }
            answer = Math.min(answer, nowPermutationTotal);
            return;
        }
        for (int i = 0; i < indexList.length; i++) {
            if (!visited[i]) {
                visited[i] = true;
                choosed[nthChoice] = indexList[i];
                permutation(nthChoice + 1, choosed, visited);
                visited[i] = false;
            }
        }
    }

    private static boolean isIn(int row, int col) {
        return (row >= 0 && row < N) && (col >= 0 && col < M);
    }
}
