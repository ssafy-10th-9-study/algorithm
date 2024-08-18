package ps;
import java.io.*;
import java.util.*;
public class Boj_15824_너봄에는캡사이신이맛있단다 {
    static BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer token;
    static private final long mod = 1_000_000_007L;
    public static void main(String[] args) throws IOException{
        int n = Integer.parseInt(bf.readLine());
        token = new StringTokenizer(bf.readLine());
        long [] arr = new long [n];
        long [] pow = new long [n+1];
        for(int i = 0 ; i < n ; i++){
            arr[i] = Long.parseLong(token.nextToken());
        }
        pow[0] = 1;

        for(int i = 1; i < n ; i++){
            pow[i] = (pow[i-1]*2)%mod;
        }
        Arrays.sort(arr);
        long ans = 0;

        for(int i = 0; i < n; i++){
            ans = (ans + (arr[i] % mod * (pow[i] - 1)) % mod) % mod;
        }
        for(int i = 0 ; i < n; i++){
            ans = (ans - (arr[i] % mod * (pow[n - i - 1] - 1)) % mod + mod) % mod;
        }


        System.out.println(ans);

    }
}
