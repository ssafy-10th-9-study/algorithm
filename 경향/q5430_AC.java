/**
 * @author Hyang
 * @since 2024.07.15
 * @title AC
 * @see https://www.acmicpc.net/problem/5430
 * @performance 100, 528KB 700ms
 * @Category 구현, 자료 구조, 문자열, 파싱, 덱
 * @Note
 * - R: 수의 순서 뒤집기
 * - D: 첫 번째 수버리기(비어있는 경우 에러 발생)
 */
package BaekJoon.Gold;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;

public class q5430_AC {
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringBuilder output = new StringBuilder();

	static char[] p;
	static int N;
	static Deque<Integer> deque;

	public static void main(String[] args) throws IOException {
		int T = Integer.parseInt(input.readLine());
		while (T-- > 0) {
			initialize();
			output.append(solution()).append('\n');
		}
		System.out.print(output);
	}

	static void initialize() throws IOException {
		p = input.readLine().toCharArray();
		N = Integer.parseInt(input.readLine());
		deque = new ArrayDeque<>();
		String str = input.readLine();
		str = str.substring(1, str.length() - 1);
		if (!str.isEmpty()) {
			String[] numbers = str.split(",");
			for (String number : numbers) {
				deque.add(Integer.parseInt(number.trim()));
			}
		}
	}

	static String solution() {
		boolean isFirst = true;
		for (char c : p) {
			switch (c) {
				case 'R':
					isFirst = !isFirst;
					break;
				case 'D':
					if (deque.isEmpty()) {
						return "error";
					}

					if (isFirst) {
						deque.pollFirst();
					} else {
						deque.pollLast();
					}

					break;
			}
		}
		return dequeToString(isFirst);
	}

	static String dequeToString(boolean isFirst) {
		StringBuilder array = new StringBuilder("[");
		if (!deque.isEmpty()) {
			if (isFirst) {
				array.append(deque.pollFirst());
				while (!deque.isEmpty()) {
					array.append(",").append(deque.pollFirst());
				}
			} else {
				array.append(deque.pollLast());
				while (!deque.isEmpty()) {
					array.append(",").append(deque.pollLast());
				}
			}
		}
		array.append("]");
		return array.toString();
	}
}
