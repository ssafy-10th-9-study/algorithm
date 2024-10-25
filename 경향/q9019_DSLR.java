/**
 * @author Hyang
 * @since 2024.07.22
 * @title DSLR
 * @see https://www.acmicpc.net/problem/9019
 * @performance 297, 176KB 3,392ms
 * @Category 그래프 이론, 그래프 탐색, 너비 우선 탐색
 */
package BaekJoon.Gold;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class q9019_DSLR {
	static class Register {
		int num;
		String commands;

		Register(int num, String commands) {
			this.num = num;
			this.commands = commands;
		}
	}

	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static char[] commands = {'D', 'S', 'L', 'R'};

	static int A, B;

	public static void main(String[] args) throws Exception {
		StringBuilder output = new StringBuilder();
		int T = Integer.parseInt(input.readLine());
		while (T-- > 0) {
			initialize();
			output.append(solution()).append('\n');
		}
		System.out.print(output);
	}

	static void initialize() throws IOException {
		StringTokenizer tokens = new StringTokenizer(input.readLine());
		A = Integer.parseInt(tokens.nextToken());
		B = Integer.parseInt(tokens.nextToken());
	}

	static String solution() {
		boolean[] isVisited = new boolean[10000]; // 0-9999;
		isVisited[A] = true;

		Queue<Register> queue = new LinkedList<>();
		queue.add(new Register(A, ""));
		while (!queue.isEmpty()) {
			Register current = queue.poll();
			if (current.num == B) {
				return current.commands;
			}
			for (char command : commands) {
				calculate(current, command, isVisited, queue);
			}
		}
		return "";
	}

	static void calculate(Register current, char command, boolean[] isVisited, Queue<Register> queue) {
		int nextNum = 0;
		switch (command) {
			case 'D':
				nextNum = (current.num * 2) % 10_000;
				break;
			case 'S':
				nextNum = current.num == 0 ? 9999 : current.num - 1;
				break;
			case 'L':
				nextNum = ((current.num % 1_000) * 10) + (current.num / 1_000);
				break;
			case 'R':
				nextNum = ((current.num % 10) * 1_000) + (current.num / 10);
				break;
		}
		if (!isVisited[nextNum]) {
			isVisited[nextNum] = true;
			queue.add(new Register(nextNum, current.commands + command));
		}
	}
}

