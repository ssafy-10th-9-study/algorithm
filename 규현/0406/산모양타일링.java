import java.util.*;

class Solution {
    
    boolean[][] matrix;
    int[][] dp;
    int last;
    final int init = -1;
    final int mod = 10_007;
    public int solution(int n, int[] tops) {
        int answer = 0;
        last = n * 2 +1;
     
       matrix = new boolean[2][2*n+1];
        dp = new int [2*n +1][3];
        
        Arrays.fill(matrix[0], true);
 
        for(int i = 0; i< n; i++){
            if(tops[i] == 1)
                matrix[1][ i * 2 + 1] = true;
        }
    
        
        for(int [] d : dp)
            Arrays.fill(d, init);

        answer = find(0);
     
        
        return answer;
    }
    
    
    private int find(int depth){
        
        if(depth >= last -1){
            return 1;
        }
            
        
        // 0, 1, 2 케이스 구하기
        
        if(!matrix[1][depth]){
            dp[depth][1] = 0;
        }
        if(depth == last -1){
            dp[depth][2] = 0;
        }
        // 0
        int result = 0;
        if(dp[depth][0] == init)
            dp[depth][0] = (find(depth+1) ) % mod ;
        if(dp[depth][1] == init ){
            dp[depth][1] = (find(depth+1) ) % mod;
        }
        if(dp[depth][2] == init){
            dp[depth][2] = (find(depth+2) ) % mod;
        }
        
        return (dp[depth][0] + dp[depth][1] + dp[depth][2]) % mod;
    }
}