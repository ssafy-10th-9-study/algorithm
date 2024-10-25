/**

 - @author 이병헌
 - @since 7/29/24
 - @see https://www.acmicpc.net/problem/11735
 - @git https://github.com/Hunnibs
 - @youtube
 - @performance O(Q)
 - @category # Math
 - @note
 n * n 배열 문제라고 해서 n * n 배열을 만들 필요는 없다.
 1 line 총합 = num * n + {n * (n+1)} / 2
 arrayList를 활용해 row, col visited 체크
 */

import java.util.*;
import java.io.*;

public class BOJ_11735_정산소 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int Q = Integer.parseInt(st.nextToken());

        int rowCnt = 0;
        int colCnt = 0;
        long checkedRows = 0;
        long checkedCols = 0;
        boolean[] visitedRows = new boolean[N + 1];
        boolean[] visitedCols = new boolean[N + 1];

        for (int i = 0; i < Q; i++){
            st = new StringTokenizer(br.readLine());
            char type = st.nextToken().charAt(0);
            int num = Integer.parseInt(st.nextToken());

            long sum = (long)num * N + ((long)N * (N + 1)) / 2;

            switch(type){
                case 'C':
                    if (visitedCols[num]){
                        sum = 0;
                        break;
                    }

                    sum -= ((long)num * rowCnt + checkedRows);

                    colCnt++;
                    checkedCols += num;
                    visitedCols[num] = true;
                    break;
                case 'R':
                    if (visitedRows[num]){
                        sum = 0;
                        break;
                    }

                    sum -= ((long)num * colCnt + checkedCols);

                    rowCnt++;
                    checkedRows += num;
                    visitedRows[num] = true;
                    break;
            }

            sb.append(sum + "\n");
        }

        System.out.print(sb);
    }
}
