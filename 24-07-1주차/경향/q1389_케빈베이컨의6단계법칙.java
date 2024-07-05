/**
 * @author Hyang
 * @since 2024.07.05
 * @title 케빈 베이컨의 6단계 법칙
 * @see https://www.acmicpc.net/problem/1389
 * @performance 11, 680KB 88ms
 * @Category 그래프 이론, 그래프 탐색, 너비 우선 탐색, 최단 경로, 플로이드-워셜
 */
package BaekJoon.Silver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class q1389_케빈베이컨의6단계법칙 {
	static int N, M;
	static int[][] dist;

	public static void main(String[] args) throws IOException {
		initialize();
		System.out.println(solution());
	}

	static void initialize() throws IOException {
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer tokens = new StringTokenizer(input.readLine());
		N = Integer.parseInt(tokens.nextToken());
		M = Integer.parseInt(tokens.nextToken());
		dist = new int[N + 1][N + 1]; // 1 base

		for (int i = 1; i <= N; i++) {
			for (int j = 1; j <= N; j++) {
				if (i != j) {
					dist[i][j] = 10_000;
				}
			}
		}

		for (int m = 0; m < M; m++) {
			tokens = new StringTokenizer(input.readLine());
			int A = Integer.parseInt(tokens.nextToken());
			int B = Integer.parseInt(tokens.nextToken());
			dist[A][B] = dist[B][A] = 1;
		}
	}

	static int solution() {
		floydWarshall();

		int minIdx = 1;
		int minValue = Integer.MAX_VALUE;
		for (int i = 1; i <= N; i++) {
			int sum = 0;
			for (int j = 1; j <= N; j++) {
				sum += dist[i][j];
			}
			if (sum < minValue) {
				minIdx = i;
				minValue = sum;
			}
		}

		return minIdx;
	}

	static void floydWarshall() {
		for (int i = 1; i <= N; i++) {
			for (int j = 1; j <= N; j++) {
				for (int k = 1; k <= N; k++) {
					if (dist[j][k] > dist[j][i] + dist[i][k]) {
						dist[j][k] = dist[j][i] + dist[i][k];
					}
				}
			}
		}
	}
}
