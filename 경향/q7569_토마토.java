/**
 * @author Hyang
 * @since 2024.07.20
 * @title 토마토
 * @see https://www.acmicpc.net/problem/7569
 * @performance 122, 144KB 628ms
 * @Category 그래프 이론, 그래프 탐색, 너비 우선 탐색
 */
package BaekJoon.Gold;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class q7569_토마토 {
	static class Point {
		int x, y, z;

		public Point(int x, int y, int z) {
			this.x = x;
			this.y = y;
			this.z = z;
		}
	}

	// input
	static int M, N, H; // 가로, 세로, 높이
	static int[][][] tomatoes; // 토마토들의 정보

	static Queue<Point> ripeTomatoes; // 익은 토마토
	static int unripeTomatoesCnt; // 익지 않은 토마토의 수

	public static void main(String[] args) throws Exception {
		initialize();
		System.out.println(unripeTomatoesCnt == 0 ? 0 : solution());
	}

	static void initialize() throws IOException {
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer tokens = new StringTokenizer(input.readLine());
		M = Integer.parseInt(tokens.nextToken());
		N = Integer.parseInt(tokens.nextToken());
		H = Integer.parseInt(tokens.nextToken());

		tomatoes = new int[M][N][H];
		ripeTomatoes = new LinkedList<>();
		unripeTomatoesCnt = 0;
		for (int h = 0; h < H; h++) {
			for (int n = 0; n < N; n++) {
				tokens = new StringTokenizer(input.readLine());
				for (int m = 0; m < M; m++) {
					tomatoes[m][n][h] = Integer.parseInt(tokens.nextToken());
					if (tomatoes[m][n][h] == 1) {
						// 익은 토마토인 경우
						ripeTomatoes.add(new Point(m, n, h));
					} else if (tomatoes[m][n][h] == 0) {
						// 익지 않은 토마토인 경우
						unripeTomatoesCnt++;
					}
				}
			}
		}
	}

	static int solution() {
		int days = -1;
		int[] dx = {1, -1, 0, 0, 0, 0};
		int[] dy = {0, 0, 1, -1, 0, 0};
		int[] dz = {0, 0, 0, 0, 1, -1};

		while (!ripeTomatoes.isEmpty()) {
			int size = ripeTomatoes.size();
			for (int i = 0; i < size; i++) {
				Point current = ripeTomatoes.poll();
				for (int d = 0; d < 6; d++) { // 6방향: 위, 아래, 왼쪽, 오른쪽, 앞, 뒤
					int nx = current.x + dx[d];
					int ny = current.y + dy[d];
					int nz = current.z + dz[d];
					if (isIn(nx, ny, nz) && tomatoes[nx][ny][nz] == 0) {
						tomatoes[nx][ny][nz] = 1;
						ripeTomatoes.add(new Point(nx, ny, nz));
						unripeTomatoesCnt--;
					}
				}
			}
			days++;
		}
		return unripeTomatoesCnt == 0 ? days : -1;
	}

	static boolean isIn(int x, int y, int z) {
		return (0 <= x && x < M) && (0 <= y && y < N) && (0 <= z && z < H);
	}
}