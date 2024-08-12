import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer token;
    public static void main(String[] args) throws IOException{

        int n = Integer.parseInt(bf.readLine());

        int [][] map = new int [n][n];
        boolean [] visitedRow = new boolean[21];
        boolean [] visitedCol = new boolean[21];
        for(int i = 0 ; i < n ; i++){
            token = new StringTokenizer(bf.readLine());
            for(int j = 0; j < n ; j++ ){
                map[i][j] = Integer.parseInt(token.nextToken());
            }
        }
        int ans = 0;
        max = 0;
        dfs(map,0,0,visitedRow,visitedCol,true);
        ans += max;

        max = 0;
        dfs(map,0,0,visitedRow,visitedCol,false);

        ans += max;
        System.out.println(ans);
    }
    private static int max = Integer.MIN_VALUE;
    private static void dfs(int [][] map, int pos, int count, boolean [] visitedRow , boolean [] visitedCol, boolean color){
        int size = map.length;
        if(pos >= size*size) return;

        for(int i = pos; i < size*size; i++){
            int x = i%size;
            int y = i/size;
            if(color && (x+y)%2 != 0)continue;
            if(!color && (x+y)%2 == 0) continue;

            if(map[y][x] == 0) continue;
            if(visitedRow[x+y])continue;
            if(visitedCol[y-x+size]) continue;
            visitedRow[x + y] = true;
            visitedCol[y-x + size] = true;
            max = Math.max(max, count + 1);
            dfs(map,i + 1 , count + 1,visitedRow,visitedCol, color);
            visitedRow[x + y] = false;
            visitedCol[y - x + size] = false;


        }
    }

}