import java.util.*;

class Solution {
    public int[] solution(int[][] edges) {
        
        // 1개 이상의 정점, 단방향 간선으로 이뤄져 있는 그래프들
        // 크기가 n인 도넛모양 그래프는 n개의 정점과 n개의 간선
        // 도넛모양 그래프는 싹 방문하고 처음으로 되돌아온다
        
        // 크기가 n인 막대모양 그래프는 n개의 정점과 n-1개의 간선
        // 임의의 한 정점에서 출발해 간선을 따라가면 정점을 한번씩 방문
        
        // 크기가 n인 8자모양 그래프는 2n+1개의 정점과 2n+2개의 간선
        
        // 이 그래프들이 여러개있다.
        // 이때, 이 그래프들과 무관한 정점을 하나만 만든다.
        // 각 도넛모양/막대모양/8자모양 그래프의 임의의 정점 하나로 향하는 간선을 연결한다
        // 그 뒤 각 정점에 다른 번호를 매겼다.
        // 그래프의 간선정보가 주어지면, 생성한 정점의 번호와 정점을 생성하기 전 도넛모양 그래프 개수/막대그래프수/8자모양그래프 개수를 구하라
        
        // 간선정보가 담긴 edge가 2차원 정수배열로 주어진다
        
        // 생성한 정점은 자신에게 들어오는 정점이 없고 나가는 정점은 있어야 한다
        
        int[] in = new int[1000001];
        int[] out = new int[1000001];
        
        List<List<Integer>> newEdges = new ArrayList<>();
        for(int i = 0; i <= 1000000; i++){
            newEdges.add(new ArrayList<>());
        }
        
        for(int i = 0; i < edges.length; i++){
            int start = edges[i][0];
            int end = edges[i][1];
            in[end]++;
            out[start]++;
            newEdges.get(start).add(end);
        }
        
        int addedNode = -1;
        for(int i = 1; i <= 1000000; i++){
            if(out[i] > 1 && in[i] == 0){
                addedNode = i;
                break;
            }
        }
        
        // 추가한 노드를 구했다.
        // 추가한 노드의 간선 = 그래프의 개수이므로 각각의 간선에 대해 탐색한다.
        
        int donut = 0, mak = 0, eight = 0;
        
        Deque<Integer> deq = new ArrayDeque<>();
        boolean[] visited = new boolean[1000001];
        
        for(int i = 0; i < newEdges.get(addedNode).size(); i++){
            deq.add(newEdges.get(addedNode).get(i));
            visited[newEdges.get(addedNode).get(i)] = true;
            
            // 각 그래프마다 탐색을 진행한다.
            int n = 0, e = 0;
            while(!deq.isEmpty()){
                int now = deq.poll();
                n++;
                
                for(int j = 0; j < newEdges.get(now).size(); j++){
                    // 방문 안했으면 하러간다
                    e++;
                    if(!visited[newEdges.get(now).get(j)]){
                        deq.offer(newEdges.get(now).get(j));
                        visited[newEdges.get(now).get(j)] = true;
                    }
                }
            }
            
            // 방문이 끝나면 노드와 간선개수를 비교한다.
            if(n == e){
                donut++;
            } else if(n - 1 == e){
                mak++;
            } else {
                eight++;
            }
        }
        
        int[] answer = {addedNode, donut, mak, eight};
        return answer;
    }
}