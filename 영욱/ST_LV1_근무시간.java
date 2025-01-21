package softeer;

import java.io.*;
import java.util.*;

public class ST_LV1_근무시간 {

    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer tokens;

    static int answer;
    static int[][] employeeTime;
    public static void main(String[] args) throws IOException{
        employeeTime = new int[5][2];
        for(int i=0; i<5; i++) {
            tokens = new StringTokenizer(input.readLine());
            String start = tokens.nextToken();
            String end = tokens.nextToken();
            String[] startSplit = start.split(":");
            String[] endSplit = end.split(":");
            int mul = 100;
            for(int j = 0; j<startSplit.length; j++) {
                String num = startSplit[j];
                employeeTime[i][0] += Integer.parseInt(num) * mul;
                mul /= 100;
            }
            mul = 100;
            for(int j = 0; j<endSplit.length; j++) {
                String num = endSplit[j];
                employeeTime[i][1] += Integer.parseInt(num) * mul;
                mul /= 100;
            }

            int startHour = employeeTime[i][0] /100;
            int endHour = employeeTime[i][1] / 100;
            int startMin = employeeTime[i][0] - (startHour * 100);
            int endMin = employeeTime[i][1] - (endHour * 100);

            int workedTime = 0;

            workedTime += (endHour - startHour) * 60;


            if(startMin <= endMin) {
                workedTime += (endMin - startMin);
            } else {
                workedTime += (60 - startMin) + endMin;
                workedTime -= 60;
            }
            answer += workedTime;
        }
        System.out.println(answer);
    }
}
