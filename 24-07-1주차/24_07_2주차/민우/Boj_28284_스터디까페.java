package ps;
import java.io.*;
import java.util.*;
public class Boj_28284_스터디카페 {
    static BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer token;
    static StringBuilder sb = new StringBuilder();

    static class Day implements Comparable<Day>{
        long day;
        boolean flag;
        Day(long day, boolean flag){
            this.day = day;
            this.flag = flag;
        }
        public int compareTo (Day d){
            return Long.compare(this.day, d.day);
        }
    }

    public static void main(String[] args) throws IOException{
        token = new StringTokenizer(bf.readLine());
        int n ,m;
        n = Integer.parseInt(token.nextToken());
        m = Integer.parseInt(token.nextToken());

        long [] prefix = new long [n+1];
        token = new StringTokenizer(bf.readLine());
        List<Long> list = new ArrayList<>();
        for(int i = 1 ; i <= n ; i++){
            list.add(Long.parseLong(token.nextToken()));
        }
        Collections.sort(list);
        for(int i = 1; i <=n ;i++){
            prefix[i] = prefix[i-1] + list.get(i-1);
        }
        // size 인원수 체크
        // min  += prefix[size]
        // max  += prefix[n] - prefix[n-size]
        PriorityQueue<Day> pq = new PriorityQueue<>();
        for(int i = 0; i < m; i++){
            token = new StringTokenizer(bf.readLine());
            long in;
            long out;
            in = Integer.parseInt(token.nextToken());
            out = Integer.parseInt(token.nextToken());
            pq.offer(new Day(in, true));
            pq.offer(new Day(out+1, false));
        }
        int size = 0;
        long min = 0;
        long max = 0;
        long now = 0;


        while(!pq.isEmpty()){
            Day cur = pq.poll();
            long dayDiff = cur.day - now;
            min += dayDiff*prefix[size];
            max += dayDiff*(prefix[n] - prefix[n-size]);
            now = cur.day;
            if(cur.flag) size++;
            else size--;
        }

        System.out.println(min + " " + max);


    }

}
