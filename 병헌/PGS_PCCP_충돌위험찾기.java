import java.util.*;

class Solution {
    public int solution(int[][] points, int[][] routes) {
        int collision = 0;

        int[][][] visited = new int[15000][101][101];

        Queue<Pos> queue = new ArrayDeque<>();
        for (int i = 0; i < routes.length; i++) {
            int route = routes[i][0] - 1;

            int r = points[route][0];
            int c = points[route][1];

            if (visited[0][r][c] == 1) {
                collision++;
                visited[0][r][c] = -1;
            } else if (visited[0][r][c] != -1) {
                visited[0][r][c] = 1;
            }

            Pos pos = new Pos(r, c, 0, 0);
            for (int j = 1; j < routes[i].length; j++) {
                route = routes[i][j] - 1;
                pos.setPoints(points[route][0], points[route][1]);
            }

            queue.offer(pos);
        }

        while(!queue.isEmpty()) {
            Pos cur = queue.poll();
            Info via = cur.getPoint(cur.point);
            int dr = via.r;
            int dc = via.c;

            if (cur.r != dr) {
                if (cur.r < dr) cur.r += 1;
                else cur.r -= 1;
            } else if (cur.c != dc) {
                if (cur.c < dc) cur.c += 1;
                else cur.c -= 1;
            }

            cur.move += 1;

            if (visited[cur.move][cur.r][cur.c] == 1) {
                collision++;
                visited[cur.move][cur.r][cur.c] = -1;
            } else if (visited[cur.move][cur.r][cur.c] == 0) {
                visited[cur.move][cur.r][cur.c] = 1;
            }

            if (isFinish(cur.r, cur.c, dr, dc)) {
                if (cur.point == cur.getPointSize()-1) continue;
                else {
                    cur.point += 1;
                    queue.offer(cur);
                }
            }
            else queue.offer(cur);
        }

        return collision;
    }

    private static boolean isFinish(int r, int c, int dr, int dc){
        return r == dr && c == dc;
    }

    private static class Pos{
        int r;
        int c;
        int move;
        int point;
        List<Info> points = new ArrayList<>();

        public Pos(int r, int c, int move, int point){
            this.r = r;
            this.c = c;
            this.move = move;
            this.point = point;
        }

        public void setPoints(int r, int c) {
            points.add(new Info(r, c));
        }

        public Info getPoint(int via) {
            return points.get(via);
        }

        public int getPointSize() {
            return points.size();
        }
    }

    private static class Info{
        int r;
        int c;

        public Info(int r, int c) {
            this.r = r;
            this.c = c;
        }
    }
}