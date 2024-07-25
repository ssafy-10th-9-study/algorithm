import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

/**
 * - @author 이병헌
 * - @since 2023-10-04
 * - @see
 * - @git https://github.com/Hunnibs
 * - @youtube
 * - @performance
 * - @category #
 * - @note
 */

public class Main {
    static class Graph {
        List<List<Integer>> graph = new ArrayList<>();

        public Graph(int N) {
            for (int i = 0; i < N + 1; i++) {
                graph.add(new ArrayList<>());
            }
        }

        public void setGraph(int a, int b) {
            graph.get(a).add(b);
        }

        public List<Integer> getGraph(int a) {
            return graph.get(a);
        }
    }

    static int N, M;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        Graph graph = new Graph(N);
        Graph reverseGraph = new Graph(N);
        int a, b;
        int[] people = new int[N + 1];
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            a = Integer.parseInt(st.nextToken());
            b = Integer.parseInt(st.nextToken());

            graph.setGraph(a, b);
            reverseGraph.setGraph(b, a);
        }

        for (int i = 1; i < N + 1; i++) {
            boolean[] visited = new boolean[N + 1];
            visited[i] = true;
            bfs(graph, i, people, visited);
            visited = new boolean[N + 1];
            visited[i] = true;
            bfs(reverseGraph, i, people, visited);
        }
        System.out.println(check(people));
    }

    private static int check(int[] people) {
        int answer = 0;
        for (int i = 1; i < N + 1; i++) {
            if (people[i] == N - 1) {
                answer++;
            }
        }
        return answer;
    }

    private static void bfs(Graph graph, int idx, int[] people, boolean[] visited) {
        List<Integer> person = graph.getGraph(idx);
        for (int i = 0; i < person.size(); i++) {
            int next = person.get(i);
            if (!visited[next]) {
                visited[next] = true;
                people[next]++;
                bfs(graph, next, people, visited);
            }
        }
    }
}