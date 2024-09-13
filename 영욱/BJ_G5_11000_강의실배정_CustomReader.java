package bj.g5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 @author 김영욱
 @since 2024. 08. 21.
 @see https://www.acmicpc.net/problem/11000
 @git
 @performance 384ms (2등 ㅎㅎ)
 @category #그리디
 @note 1초
 문제
 수강신청의 마스터 김종혜 선생님에게 새로운 과제가 주어졌다.

 김종혜 선생님한테는 Si에 시작해서 Ti에 끝나는 N개의 수업이 주어지는데, 최소의 강의실을 사용해서 모든 수업을 가능하게 해야 한다.

 참고로, 수업이 끝난 직후에 다음 수업을 시작할 수 있다. (즉, Ti ≤ Sj 일 경우 i 수업과 j 수업은 같이 들을 수 있다.)

 수강신청 대충한 게 찔리면, 선생님을 도와드리자!

 입력
 첫 번째 줄에 N이 주어진다. (1 ≤ N ≤ 200,000)

 이후 N개의 줄에 Si, Ti가 주어진다. (0 ≤ Si < Ti ≤ 109)

 출력
 강의실의 개수를 출력하라.

 예제 입력 1
 3
 1 3
 2 4
 3 5
 예제 출력 1
 2


 **/

public class BJ_G5_11000_강의실배정_CustomReader {

    static Reader in = new Reader();

    static int N, ans;
    static PriorityQueue<classRoomStart> startPQ = new PriorityQueue<>();
    static PriorityQueue<classRoomEnd> endPQ = new PriorityQueue<>();

    public static void main(String[] args) throws Exception {
        N = in.nextInt();

        for(int i=0; i<N; i++) {
            int start = in.nextInt();
            int end = in.nextInt();

            startPQ.offer(new classRoomStart(start,end));
        }

        while(!startPQ.isEmpty()) {
            classRoomStart now = startPQ.poll();
            endPQ.offer(new classRoomEnd(now.start, now.end));
            while (!endPQ.isEmpty() && now.start >= endPQ.peek().end) {
                endPQ.poll();
            }
            ans = Math.max(ans, endPQ.size());
        }
        System.out.println(Math.max(ans, endPQ.size()));
    }

    private static class classRoomEnd implements Comparable<classRoomEnd>{
        int start;
        int end;

        public classRoomEnd(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public int compareTo(classRoomEnd o) {
            return Integer.compare(this.end, o.end);
        }
    }

    private static class classRoomStart implements Comparable<classRoomStart>{
        int start;
        int end;

        public classRoomStart(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public int compareTo(classRoomStart o) {
            return Integer.compare(this.start, o.start);
        }
    }

    static class Reader {
        final int SIZE = 1 << 13;
        byte[] buffer = new byte[SIZE];
        int index, size;

        int nextInt() throws Exception {
            int n = 0;
            byte c;
            while ((c = read()) <= 32);
            do n = (n << 3) + (n << 1) + (c & 15);
            while (isNumber(c = read()));
            return n;
        }

        boolean isNumber(byte c) {
            return 47 < c && c < 58;
        }

        byte read() throws Exception {
            if (index == size) {
                size = System.in.read(buffer, index = 0, SIZE);
                if (size < 0)
                    buffer[0] = -1;
            }
            return buffer[index++];
        }
    }

}
