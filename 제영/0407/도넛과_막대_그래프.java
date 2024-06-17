import java.util.*;
class Solution {
    static int maxSize = 1_000_000;
    static int maxEdge;
    static List<List<Integer>> adjList;
    public static int findStart(int[][] edges) {
        boolean[] visited = new boolean[maxSize + 1];
        int[] edgeCount = new int[maxSize + 1];
        for (int[] edge : edges) {
            int start = edge[0];
            int end = edge[1];
            maxEdge = Math.max(maxEdge, start);
            maxEdge = Math.max(maxEdge, end);
            visited[end] = true;
            edgeCount[start]++;
        }
        for (int i = 1; i <= maxEdge; i++) {
            if (edgeCount[i] >= 2 && !visited[i]) {
                return i;
            }
        }
        return -1;
    }

    public static void makeAdjList(int[][] edges) {
        for (int i = 0; i <= maxEdge; i++) {
            adjList.add(new ArrayList<>());
        }
        for (int[] edge : edges) {
            int start = edge[0];
            int end = edge[1];
            adjList.get(start).add(end);
        }
    }

    public static int[] findSolution(int start) {
        int[] answer = new int[]{start, 0, 0, 0};
        boolean[] visited = new boolean[maxEdge + 1];

        outLoop:
        for (int graphStart : adjList.get(start)) { // 각 그래프의 시작점부터 시작
            Queue<Integer> queue = new ArrayDeque<>();
            int now = graphStart;
            visited[graphStart] = true;
            queue.offer(now);
            while (!queue.isEmpty()) {
                now = queue.poll();
                List<Integer> target = adjList.get(now);
                if (target.size() == 2) {
                    answer[3]++; // 8자
                    continue outLoop;
                }
                for (int next : target) {
                    if (visited[next]) {
                        answer[1]++; // 도넛
                        continue outLoop;
                    }
                    visited[next] = true;
                    queue.offer(next);
                }
            }
            answer[2]++; // 막대
        }
        return answer;
    }
    public int[] solution(int[][] edges) {
        int[] answer = {};
        maxEdge = 0;
        adjList = new ArrayList<>();
        int start = findStart(edges);
        makeAdjList(edges);
        return findSolution(start);
    }
}