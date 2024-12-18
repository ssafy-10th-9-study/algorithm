import java.util.*;

class Solution {
    public String solution(String video_len, String pos, String op_start, String op_end, String[] commands) {
        String answer = "";

        String[] times = video_len.split(":");
        int hour = Integer.parseInt(times[0]);
        int minute = Integer.parseInt(times[1]);
        String[] tmp_pos = pos.split(":");
        int cur_hour = Integer.parseInt(tmp_pos[0]);
        int cur_minute = Integer.parseInt(tmp_pos[1]);
        String[] op_start_times = op_start.split(":");
        int op_start_hour = Integer.parseInt(op_start_times[0]);
        int op_start_minute = Integer.parseInt(op_start_times[1]);
        String[] op_end_times = op_end.split(":");
        int op_end_hour = Integer.parseInt(op_end_times[0]);
        int op_end_minute = Integer.parseInt(op_end_times[1]);

        if (op_start_hour == op_end_hour) {
            if (cur_hour == op_start_hour && op_start_minute <= cur_minute && cur_minute <= op_end_minute) cur_minute = op_end_minute;
        } else if (op_start_hour < op_end_hour) {
            if (cur_hour == op_start_hour){
                if (cur_minute >= op_start_minute) {
                    cur_hour = op_end_hour;
                    cur_minute = op_end_minute;
                    System.out.print(1);
                }
            } else if (op_start_hour < cur_hour && cur_hour < op_end_hour) {
                cur_hour = op_end_hour;
                cur_minute = op_end_minute;
            } else if (cur_hour == op_end_hour && cur_minute <= op_end_minute) {
                cur_minute = op_end_minute;
            }
        }

        for (String command : commands){
            switch(command) {
                case "next" :
                    cur_minute += 10;
                    if (cur_minute >= 60) {
                        cur_minute -= 60;
                        cur_hour += 1;
                    }

                    if (cur_hour > hour) {
                        cur_hour = hour;
                        cur_minute = minute;
                    }

                    if (cur_hour == hour && cur_minute > minute) {
                        cur_minute = minute;
                    }
                    break;
                case "prev":
                    cur_minute -= 10;
                    if (cur_minute < 0) {
                        cur_minute += 60;
                        cur_hour -= 1;
                    }

                    if (cur_hour < 0) {
                        cur_minute = 0;
                        cur_hour = 0;
                    }
                    break;
            }
            if (op_start_hour == op_end_hour) {
                if (cur_hour == op_start_hour && op_start_minute <= cur_minute && cur_minute <= op_end_minute) cur_minute = op_end_minute;
            } else if (op_start_hour < op_end_hour) {
                if (cur_hour == op_start_hour){
                    if (cur_minute >= op_start_minute) {
                        cur_hour = op_end_hour;
                        cur_minute = op_end_minute;
                    }
                } else if (op_start_hour < cur_hour && cur_hour < op_end_hour) {
                    cur_hour = op_end_hour;
                    cur_minute = op_end_minute;
                } else if (cur_hour == op_end_hour && cur_minute <= op_end_minute) {
                    cur_minute = op_end_minute;
                }
            }
        }

        List<String> cur_time = new ArrayList<>();
        if (cur_hour < 10) cur_time.add("0" + cur_hour);
        else cur_time.add(Integer.toString(cur_hour));

        if (cur_minute < 10) cur_time.add("0" + cur_minute);
        else cur_time.add(Integer.toString(cur_minute));

        answer = String.join(":", cur_time);

        return answer;
    }
}