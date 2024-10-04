import java.util.*;

class Solution {

    public long solution(int n, int[] works) {
        Arrays.sort(works);
        int last = works.length - 1;

        while(n > 0 ){
            if(works[last] == 0 ) break;
            int lb = lowerBound(works, works[last]);
            if(lb == -1 )break;

            int cnt = last - lb + 1;


            int diff = works[last];

            if(lb > 0 ) {
                diff -= works[lb-1];
            }

            n -= diff * cnt;
            if(n > 0) {
                for(int i = lb; i < last + 1; i++){
                    works[i] -= diff;
                }
            }
            else {
                n += diff * cnt;
                for(int i = lb; i < last + 1; i++ ) {
                    works[i] -= n/cnt;
                }
                n = n%cnt;
                for(int i = lb ; n > 0; n--, i++){
                    works[i] --;
                }
            }
        }

        long answer = 0;
        for( int w : works){
            answer += w*w;
        }

        return answer;
    }

    public int lowerBound (int [] works , int target){
        int left = 0;
        int right = works.length;

        while(left < right){
            int mid = (left + right) / 2;
            if(works[mid] < target){
                left = mid + 1;
            }
            else{
                right = mid;
            }
        }
        return left;
    }
}