import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer token;
    static StringBuilder sb = new StringBuilder();
    public static void main(String[] args) throws IOException{
        token = new StringTokenizer(bf.readLine());
        int N = Integer.parseInt(token.nextToken());
        int K = Integer.parseInt(token.nextToken());
        int maxNumber = (1 << N) - 1;
        int box = 1 << K;

        int pairNum = (1<<N)/box/2;
        int val = 0;

        for (int i = 0; i < box; i++) {
            for (int j = 0; j < pairNum; j++) {
                sb.append(val).append(" ").append(maxNumber - val).append(" ");
                val++;
            }
            sb.append("\n");
        }
        System.out.println(sb.toString());
    }


}