package boj.gold2;

import java.io.*;
import java.util.*;
/**

 - @author 이병헌
 - @since 7/11/2024
 - @see https://www.acmicpc.net/problem/28284
 - @git https://github.com/Hunnibs
 - @youtube
 - @performance
 - @category # Greedy # Heap
 - @note
 1. A 좌석 요금이 오름차순 혹은 내림차순으로 내려준다는 조건은 없다.
 */

public class BOJ_28284 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        long[] A = new long[N];
        st = new StringTokenizer(br.readLine());
        for(int i = 0; i < N; i++){
            A[i] = Long.parseLong(st.nextToken());
        }
        Arrays.sort(A);

        PriorityQueue<Info> heap = new PriorityQueue<>(new Comparator<Info>() {
            @Override
            public int compare(Info o1, Info o2) {
                return Long.compare(o1.day, o2.day);
            }
        });
        for(int i = 0; i < M; i++){
            st = new StringTokenizer(br.readLine());
            heap.add(new Info(Integer.parseInt(st.nextToken()), true));
            heap.add(new Info(Integer.parseInt(st.nextToken())+1, false));
        }

        sol(N, M, A, heap);
    }

    private static void sol(int N, int M, long[] A, PriorityQueue<Info> heap){
        long lowSum = 0;
        long highSum = 0;

        long[] prefixSumA = new long[N+1];
        long[] reversePrefixSumA = new long[N+1];
        prefixSumA[1] = A[0];
        reversePrefixSumA[1] = A[N-1];
        for(int i = 2; i <= N; i++){
            reversePrefixSumA[i] = reversePrefixSumA[i-1] + A[N-i];
            prefixSumA[i] = prefixSumA[i-1] + A[i-1];
        }

        int idx = 1;
        int lastDate = heap.poll().day;

        while(!heap.isEmpty()){
            Info cur = heap.poll();

            lowSum += (cur.day - lastDate) * prefixSumA[idx];
            highSum += (cur.day - lastDate) * reversePrefixSumA[idx];

            lastDate = cur.day;

            if (cur.check){
                idx++;
            } else{
                idx--;
            }
        }

        System.out.print(lowSum + " " + highSum);
    }

    private static class Info{
        int day;
        boolean check;

        public Info(int day, boolean check) {
            this.day = day;
            this.check = check;
        }
    }
}