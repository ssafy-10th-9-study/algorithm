import java.util.*;

class Solution {
    public int solution(int n, int[] stations, int w) {
        int answer = 0;
        int now = 1;

        for(int station : stations){
            int diff = station - w - now;
            if(diff < 0 ) diff = 0;
            answer += Math.ceil(1.0 * diff / (w * 2 +1 ));

            now = station + w + 1;
        }
        int diff = n + 1 - now;
        if(diff < 0 ) diff = 0;
        answer += Math.ceil(1.0 * diff / (w * 2 + 1 ));



        return answer;
    }
}