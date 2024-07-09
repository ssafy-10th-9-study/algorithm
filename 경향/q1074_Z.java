/**
 * @author Hyang
 * @since 2024.07.04
 * @title Z
 * @see https://www.acmicpc.net/problem/1074
 * @performance 11, 500KB 80ms
 * @Category 분할 정복, 재귀
 */
package BaekJoon.Silver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class q1074_Z {
	static int N, r, c;

	public static void main(String[] args) throws IOException {
		initialize();
		System.out.println(solution(0, 0, (1 << N)));
	}

	static void initialize() throws IOException {
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer tokens = new StringTokenizer(input.readLine());
		N = Integer.parseInt(tokens.nextToken());
		r = Integer.parseInt(tokens.nextToken());
		c = Integer.parseInt(tokens.nextToken());
	}

	static int solution(int x, int y, int size) {
		if (size == 1) {
			return 0;
		}

		int half = size / 2;
		int cnt = half * half;

		if (r < x + half && c < y + half) {
			return solution(x, y, half);
		} else if (r < x + half && c >= y + half) {
			return cnt + solution(x, y + half, half);
		} else if (r >= x + half && c < y + half) {
			return cnt * 2 + solution(x + half, y, half);
		} else {
			return cnt * 3 + solution(x + half, y + half, half);
		}
	}
}
