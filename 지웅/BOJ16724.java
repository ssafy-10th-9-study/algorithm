import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ16724 {
    static int[] dtx = {-1, 1, 0, 0};
    static int[] dty = {0, 0, -1, 1};
    static int[][] visit, map;
    static int total = 0;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        map = new int[n][m];
        visit = new int[n][m];
        for (int i = 0; i < n; i++) {
            char[] input = br.readLine().toCharArray();
            for (int j = 0; j < m; j++) {
                if (input[j] == 'U') map[i][j] = 0;
                if (input[j] == 'D') map[i][j] = 1;
                if (input[j] == 'L') map[i][j] = 2;
                if (input[j] == 'R') map[i][j] = 3;
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (visit[i][j] == 0) find(i, j);
            }
        }
        System.out.println(total);
    }

    static int find(int x, int y) {
        if (visit[x][y] == 0) {
            visit[x][y] = total + 1;
            int temp = find(x + dtx[map[x][y]], y + dty[map[x][y]]);
            if (temp != total + 1) visit[x][y] = temp;
        } else if (visit[x][y] == total + 1) ++total;
        return visit[x][y];
    }

}
