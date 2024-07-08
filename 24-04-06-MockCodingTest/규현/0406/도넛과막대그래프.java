import java.util.*;
import java.util.Map.*;

class Solution {
    
    
    boolean[] visitedEdges;
    Map<Integer,Edge> fromMap;

    int d = 0;
    int l = 0;
    int e = 0;
    public int[] solution(int[][] edges) {

        int numberOfEdge = edges.length;
        fromMap = new HashMap<>();
        Set<Integer> toSet = new HashSet<>();
        visitedEdges = new boolean[numberOfEdge];
        Map<Integer,Integer> counter = new HashMap<>();
        for(int i = 0; i< numberOfEdge; i++){
            
            int from = edges[i][0];
            int to = edges[i][1];
            fromMap.put(from,new Edge(i,to,fromMap.get(from)));
            toSet.add(to);
            counter.put(from, counter.getOrDefault(from,0)+1);
        }
        
        // 그녀석 찾기
        int him = 0;
        for(Entry<Integer, Integer> e : counter.entrySet()){
            if(e.getValue() >= 2 && !toSet.contains(e.getKey())){
                him = e.getKey();
                break;
            }
        }
        

        for(Edge edge = fromMap.get(him); edge != null; edge = edge.next){
            findRoute(edge.to);
        }
                
        return new int[]{him, d, l, e};
    }
    
    public void findRoute(int target){
        
            Set<Integer> vertex = new HashSet<>();
            // 탐색 진행
            vertex.add(target);
            
            Queue<Edge> q = new ArrayDeque<>();
            q.add(new Edge(0, target, null));
            int edgeCount = 0;
            
            while(!q.isEmpty()){
                Edge poll = q.poll();
                
                for(Edge e = fromMap.get(poll.to); e != null; e = e.next){
                    if(visitedEdges[e.index]) continue;
                    visitedEdges[e.index] = true;
                    vertex.add(e.to);
                    q.add(new Edge(0, e.to, null));
                    edgeCount++;
                }
            }
        
            // 관계 파악
            calculateVE(vertex.size(), edgeCount);
    }
    
    private void calculateVE(int numberOfVertex, int numberOfEdge){
        
        if(numberOfVertex == numberOfEdge){
            d++;
        }else if(numberOfVertex -1 == numberOfEdge){
            l++;
        }else{
            e++;
        }
    }
    
    static class Edge{
        int index;
        int to;
        Edge next;
        
        public Edge(int i, int t, Edge n){
            index = i;
            to = t;
            next = n;
        }
    }
}