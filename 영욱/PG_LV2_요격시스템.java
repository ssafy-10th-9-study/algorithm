package programmers;

import java.util.*;

public class PG_LV2_요격시스템 {

    static int[][] targets = {{4,5},{4,8},{10,14},{11,13},{5,12},{3,7},{1,4}};

    public static void main(String[] args) {
        int answer = 1;
        int s = 0;
        int e = 0;

        Arrays.sort(targets, (o1, o2) -> o1[1] - o2[1]);

        int oldE = targets[0][1];

        for(int i=1; i < targets.length; i++) {
            s = targets[i][0];
            e = targets[i][1];

            if(s >= oldE) {
                answer++;
                oldE = e;
            }
        }
        System.out.println(answer);
    }
}
