/**
 * @author Hyang
 * @since 2024.07.23
 * @title 테트로미노
 * @see https://www.acmicpc.net/problem/14500
 * @performance 31, 812KB 732ms
 * @Category 구현, 브루트포스
 */
package BaekJoon.Gold;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class q14500_테트로미노 {
	static int N, M, board[][];

	static int maxScore = 0;

	static boolean[][] visited;
	static int[] dx = {-1, 1, 0, 0};
	static int[] dy = {0, 0, -1, 1};

	public static void main(String[] args) throws Exception {
		initialize();
		solution();
		System.out.println(maxScore);
	}

	static void initialize() throws IOException {
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer tokens = new StringTokenizer(input.readLine());
		N = Integer.parseInt(tokens.nextToken());
		M = Integer.parseInt(tokens.nextToken());
		board = new int[N][M];
		visited = new boolean[N][M];
		for (int row = 0; row < N; row++) {
			tokens = new StringTokenizer(input.readLine());
			for (int col = 0; col < M; col++) {
				board[row][col] = Integer.parseInt(tokens.nextToken());
			}
		}
	}

	static void solution() {
		for (int row = 0; row < N; row++) {
			for (int col = 0; col < M; col++) {
				// 모든 위치를 기준으로 하여 테트로미노 생성
				visited[row][col] = true;
				makeTetromino(row, col, 0, 0);
				visited[row][col] = false;
				makeTShape(row, col);
			}
		}
	}

	/**
	 * 테트로미노(T모양 제외)를 만들어 테트로미노가 놓인 칸에 쓰여 있는 수들의 합을 계산함
	 * @param row 행
	 * @param col 열
	 * @param polyominoCnt 폴리오미노의 개수
	 * @param score 현재까지 폴리오미노가 놓여진 칸에 쓰여 있는 수들의 합
	 */
	static void makeTetromino(int row, int col, int polyominoCnt, int score) {
		if (polyominoCnt == 4) {
			// 테트로미노를 완성한 경우
			maxScore = Math.max(maxScore, score);
			return;
		}
		for (int d = 0; d < 4; d++) {
			int nx = row + dx[d];
			int ny = col + dy[d];
			if (isIn(nx, ny) && !visited[nx][ny]) {
				visited[nx][ny] = true;
				makeTetromino(nx, ny, polyominoCnt + 1, score + board[nx][ny]);
				visited[nx][ny] = false;
			}
		}
	}

	/**
	 * T모양의 테트로미노를 만들어 테트로미노가 놓인 칸에 쓰여 있는 수들의 합을 계산함
	 * 입력받은 값을 중심으로, 상하좌우의 테트로미노를 활용하여 T모양을 만듬
	 * @param row 행
	 * @param col 열
	 */
	static void makeTShape(int row, int col) {
		int score = board[row][col];
		int polyominoCnt = 0;
		int minPolymino = Integer.MAX_VALUE;

		for (int d = 0; d < 4; d++) {
			int nx = row + dx[d];
			int ny = col + dy[d];
			if (isIn(nx, ny)) {
				polyominoCnt++;
				score += board[nx][ny];
				minPolymino = Math.min(minPolymino, board[nx][ny]);
			}
		}

		if (polyominoCnt == 4) {
			// 4방향 모두 가능한 경우, 가장 작은 값의 폴리오미노 제외
			score -= minPolymino;
		}

		if (polyominoCnt >= 3) {
			// 테트로미노가 만들어진 경우
			maxScore = Math.max(maxScore, score);
		}
	}

	static boolean isIn(int x, int y) {
		return (0 <= x && x < N) && (0 <= y && y < M);
	}
}
