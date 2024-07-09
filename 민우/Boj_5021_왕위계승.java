import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer token;
    static StringBuilder sb = new StringBuilder();
    static class Person implements Comparable<Person>{
        String name;
        double percent;
        Person(String name, double percent){
            this.name = name;
            this.percent = percent;
        }

        public int compareTo(Person p){
            return -Double.compare(this.percent,p.percent);
        }
    }

    public static void main(String[] args) throws IOException{
        token = new StringTokenizer(bf.readLine());
        int n, m ;
        n = Integer.parseInt(token.nextToken());
        m = Integer.parseInt(token.nextToken());
        Map<String,Integer> indegree = new HashMap<>();
        Map<String,Double> blood = new HashMap<>();
        Map<String,List<String>> graph = new HashMap<>();
        String king = bf.readLine();
        blood.put(king,1.0);

        for(int i = 0; i < n ; i++){
            token = new StringTokenizer(bf.readLine());
            String child = token.nextToken();
            String parentA = token.nextToken();
            String parentB = token.nextToken();
            if(!graph.containsKey(parentA)){
                graph.put(parentA, new ArrayList<>());
            }
            if(!graph.containsKey(parentB)){
                graph.put(parentB, new ArrayList<>());
            }
            if(!indegree.containsKey(parentA)){
                indegree.put(parentA,0);
            }
            if(!indegree.containsKey(parentB)){
                indegree.put(parentB,0);
            }
            indegree.put(child,2);
            blood.put(child,0.0);
            graph.get(parentA).add(child);
            graph.get(parentB).add(child);
        }
        Queue<String> q = new ArrayDeque<>();
        for(String name : indegree.keySet()){
            if(indegree.get(name)== 0) q.offer(name);
        }
        while(!q.isEmpty()){
            String cur = q.poll();
            if(!graph.containsKey(cur)) continue;
            for(String next : graph.get(cur)){
                double half = blood.get(cur) != null? blood.get(cur)/2 : 0.0;
                blood.put(next, blood.get(next) + half);
                indegree.put(next,indegree.get(next) - 1);
                if(indegree.get(next) == 0){
                    q.offer(next);
                }
            }
        }

        List<Person> list = new ArrayList<>();
        for(int i = 0 ; i < m ; i++){
            String name = bf.readLine();
            if(blood.containsKey(name)){
                list.add(new Person(name, blood.get(name)));
            }
        }
        Collections.sort(list);
        System.out.println(list.get(0).name);

    }

}