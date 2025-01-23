/**
 * - @author 이병헌
 * - @since 1/16/2025
 * - @see https://www.acmicpc.net/problem/3954
 * - @git https://github.com/Hunnibs
 * - @youtube
 * - @performance 7sec 128MB
 * - @category # Bruteforce
 * - @note
 * <p>
 * Brainf**k 인터프리터는 정수를 담는 하나의 배열(unsigned 8-bit 정수)과, 그 배열의 칸 하나를 가리키는 포인터로 이루어져 있다.
 * -> 배열 한 칸에 저장 가능한 값이 0 ~ 2 ^ 8 - 1로 overflow와 underflow 계산
 * <p>
 * ```
 * -	포인터가 가리키는 수를 1 감소시킨다. (modulo 2^8)
 * +	포인터가 가리키는 수를 1 증가시킨다. (modulo 2^8)
 * <	포인터를 왼쪽으로 한 칸 움직인다.
 * >	포인터를 오른쪽으로 한 칸 움직인다.
 * [	만약 포인터가 가리키는 수가 0이라면, [ 와 짝을 이루는 ] 의 다음 명령으로 점프한다.
 * ]	만약 포인터가 가리키는 수가 0이 아니라면, ] 와 짝을 이루는 [ 의 다음 명령으로 점프한다.
 * .	포인터가 가리키는 수를 출력한다.
 * ,	문자 하나를 읽고 포인터가 가리키는 곳에 저장한다. 입력의 마지막(EOF)인 경우에는 255를 저장한다.
 * ```
 */

import java.util.*;
import java.io.*;

public class BOJ_03954_BrainfuckInterpreter {
    private static final int UNSIGNED_BIT = 255;
    private static final int MAX_LOOP = 50_000_000;
    private static int charTracker;
    private static int pointer;

    private static StringBuilder solution(int sm, int sc, int si, String program, String programInput) {
        int[] brainFuck = new int[sm];
        int[] loopOpenPoint = new int[sc];
        int[] loopClosePoint = new int[sc];

        loopPointPair(program, sc, loopOpenPoint, loopClosePoint);

        int loopCounter = 0;
        int command = 0;
        pointer= 0;
        charTracker = 0;

        while (loopCounter++ <= MAX_LOOP) {
            if (command == sc) break;  // 탈출 조건

            command = brainFuckProgram(command, brainFuck, program, programInput, si, loopOpenPoint, loopClosePoint);
        }

        StringBuilder sb = new StringBuilder();
        if (loopCounter > MAX_LOOP && command != sc) {
            int minBracket = command;
            int maxBracket = command;
            int cnt = 0;
            while (cnt++ <= MAX_LOOP) {
                command = brainFuckProgram(command, brainFuck, program, programInput, si, loopOpenPoint, loopClosePoint);
                minBracket = Math.min(minBracket, command);
                maxBracket = Math.max(maxBracket, command);
            }

            sb.append("Loops ").append(minBracket-1).append(" ").append(loopOpenPoint[minBracket-1]);
        } else {
            sb.append("Terminates");
        }

        return sb;
    }

    private static int brainFuckProgram(int commandPos, int[] brainFuck, String program, String programInput, int si, int[] loopOpenPoint, int[] loopClosePoint) {
        char command = program.charAt(commandPos);
        switch (command) {
            case '-':
                brainFuck[pointer]--;
                if (brainFuck[pointer] < 0) brainFuck[pointer] = UNSIGNED_BIT;
                break;
            case '+':
                brainFuck[pointer]++;
                if (brainFuck[pointer] > UNSIGNED_BIT) brainFuck[pointer] = 0;
                break;
            case '<':
                pointer--;
                if (pointer < 0) pointer = brainFuck.length - 1;
                break;
            case '>':
                pointer++;
                if (pointer == brainFuck.length) pointer = 0;
                break;
            case '[':
                if (brainFuck[pointer] == 0) commandPos = loopOpenPoint[commandPos];
                break;
            case ']':
                if (brainFuck[pointer] != 0) commandPos = loopClosePoint[commandPos];
                break;
            case '.':
//                System.out.print(brainFuck[pointer]);  출력 조건은 무시한다.
                break;
            case ',':
                if (charTracker == si) brainFuck[pointer] = 255;  // EOF 경우 255
                else brainFuck[pointer] = programInput.charAt(charTracker++);
                break;
        }

        return commandPos + 1;
    }

    private static void loopPointPair(String program, int sc, int[] loopOpenPoint, int[] loopClosePoint) {
        Deque<Integer> stack = new ArrayDeque<>();

        for (int i = 0; i < sc; i++) {
            if (program.charAt(i) == '[') {
                stack.addLast(i);
            }

            if (program.charAt(i) == ']') {
                int open = stack.pollLast();
                loopClosePoint[i] = open;
                loopOpenPoint[open] = i;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        int testCase = Integer.parseInt(br.readLine());
        for (int i = 0; i < testCase; i++) {
            st = new StringTokenizer(br.readLine());
            int sm = Integer.parseInt(st.nextToken());
            int sc = Integer.parseInt(st.nextToken());
            int si = Integer.parseInt(st.nextToken());

            String program = br.readLine();
            String programInput = br.readLine();
            sb.append(solution(sm, sc, si, program, programInput) + "\n");
        }

        System.out.print(sb);
    }
}