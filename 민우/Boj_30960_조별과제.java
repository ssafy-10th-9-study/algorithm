import java.io.*;
import java.util.*;
public class Main {
    static BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer token;
    public static void main(String[] args) throws IOException{
        int n = Integer.parseInt(bf.readLine());
        token = new StringTokenizer(bf.readLine());
        int [] A = new int [n];
        for(int i = 0; i < n ; i++){
            A[i] = Integer.parseInt(token.nextToken());
        }
        Arrays.sort(A);

        int[] diff = new int[n - 1];
        for (int i = 0; i < n - 1; i++) {
            diff[i] = A[i + 1] - A[i];
        }
        int [] prefixOdd = new int [n/2+1];
        int [] prefixEven = new int [n/2 + 1];

        for(int i = 0 ; i < n - 1; i += 2){
            prefixEven[i/2+1] = prefixEven[i/2] + diff[i];
        }
        for(int i = 1 ; i < n - 1; i += 2){
            prefixOdd[i/2+1] = prefixOdd[i/2] + diff[i];
        }

        int min = Integer.MAX_VALUE;
        for (int i = 0; i < n - 2; i += 2) {
            // 3칸짜리 시작 ~ 끝
            int sum = A[i+2] - A[i];
            sum += prefixEven[i/2];
            sum += prefixOdd[n/2] - prefixOdd[(i+3)/2];
            min = Math.min(sum,min);

        }

        System.out.println(min);

    }
}