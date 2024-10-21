import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String word = br.readLine();

        StringBuilder sb = new StringBuilder();

        boolean hasC = false; // true인데 ) 만나면 1 박기
        boolean hasNumber = false; // true인 상황에서 C 만나면 + 박기 그리고 false로 바꿈

        for(char c : word.toCharArray()){
            if(c == '('){
                if(hasNumber){
                    sb.append('+');
                    hasNumber = false;
                }
                hasC = true;
                sb.append(c);
                continue;
            }

            // )

            if(hasC){
                sb.append(1);
                hasC = false;
                hasNumber = true;
            }
            sb.append(c);
        }
        System.out.println(sb);
    }
}