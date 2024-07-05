import java.util.*;

class Solution {
    public int[] solution(int[][] edges) {
        int[] answer = {0, 0, 0, 0};

        Graph graph = new Graph();
        int[] innerTrace = new int[1_000_001];
        int[] outerTrace = new int[1_000_001];
        for(int i = 0; i < edges.length; i++){
            graph.setGraph(edges[i][0], edges[i][1]);
            innerTrace[edges[i][1]]++;
            outerTrace[edges[i][0]]++;
        }

        for(int i = 0; i < 1_000_001; i++){
            if(outerTrace[i] >= 2 && innerTrace[i] == 0){
                answer[0] = i;
            } else if (outerTrace[i] == 2 && innerTrace[i] != 0){
                answer[3]++;
            } else if (outerTrace[i] == 0 && innerTrace[i] >= 1){
                answer[2]++;
            }
        }

        List<Integer> startVertex = graph.getGraph(answer[0]);
        answer[1] = startVertex.size() - answer[2] - answer[3];

        return answer;
    }

    static class Graph{
        List<List<Integer>> graph = new ArrayList<>();

        public Graph(){
            for(int i = 0; i < 1_000_001; i++){
                graph.add(new ArrayList<>());
            }
        }

        public void setGraph(int from, int to){
            graph.get(from).add(to);
        }

        public List<Integer> getGraph(int from){
            return graph.get(from);
        }
    }
}