import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Main {



    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        int[][] matrix = new int[n + 1][n + 1];

        for (int[] ints : matrix) {
            Arrays.fill(ints, 10_0000_0000);
        }

        for (int i = 1; i <= n ; i++) {
            matrix[i][i] = 0;
        }

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int f = Integer.parseInt(st.nextToken());
            int t = Integer.parseInt(st.nextToken());

            matrix[f][t] = 1;

        }

        for (int k = 1; k <= n ; k++) {
            for (int i = 1; i <= n ; i++) {
                for (int j = 1; j <= n; j++) {
                    if(matrix[i][j] > matrix[i][k] + matrix[k][j]) {
                        matrix[i][j] = matrix[i][k] + matrix[k][j];
                    }
                }
            }
        }

        int ans = 0;
        for (int i = 1; i <= n ; i++) {
            int cnt = 0;
            for (int j = 1; j <= n; j++) {
                if(matrix[i][j] < 10_0000_0000)
                    cnt++;
                if(matrix[j][i] < 10_0000_0000)
                    cnt++;
            }
            if(cnt == n+1)
                ans++;
        }
        System.out.println(ans);



    }




}