import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main2 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;

    public static void main(String[] args) throws Exception {
        // 코드를 작성해주세요
        st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        // n
        if (k == 1) {
            int sqrt = (int) Math.sqrt(n);
            int turn = sqrt * 2 -1;
            n -= sqrt * sqrt;
            turn += n/sqrt;
            if (n % sqrt > 0) {
                turn++;
            }
            System.out.println(turn);
        } else {
            int target = 0;
            for(int i = 1; i <= n; i++) {
                int acc = calc2(i);

                if (n < acc) {
                    target = i-1;
                    break;
                }
            }
            int turn = target;
            int h = calc2(target);
            n -= h;

            int d = n/target;
            int mod = d%target;

            if(mod > 0)
                turn++;
            turn+=d;

            System.out.println(turn);
        }
    }

    static int calc2(int k){
        int index = k/2;
        return k * index + (index+1);
    }
}
