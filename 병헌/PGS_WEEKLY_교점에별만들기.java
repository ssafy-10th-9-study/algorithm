package boj.gold4;

/**
 *
 제한사항
 line의 세로(행) 길이는 2 이상 1,000 이하인 자연수입니다.
 line의 가로(열) 길이는 3입니다.
 line의 각 원소는 [A, B, C] 형태입니다.
 A, B, C는 -100,000 이상 100,000 이하인 정수입니다.
 무수히 많은 교점이 생기는 직선 쌍은 주어지지 않습니다.
 A = 0이면서 B = 0인 경우는 주어지지 않습니다.
 정답은 1,000 * 1,000 크기 이내에서 표현됩니다.
 별이 한 개 이상 그려지는 입력만 주어집니다.
 */

import java.util.*;

public class PGS_WEEKLY_교점에별만들기 {
    private static int maxY = Integer.MIN_VALUE;
    private static int minY = Integer.MAX_VALUE;
    private static int maxX = Integer.MIN_VALUE;
    private static int minX = Integer.MAX_VALUE;


    private static class Pos {
        int r, c;

        public Pos(int r, int c) {
            this.r = r;
            this.c = c;
        }
    }

    private static void check(int[][] line, HashSet<Pos> set, int cur, int next) {
        long A = line[cur][0];
        long B = line[cur][1];
        long E = line[cur][2];
        long C = line[next][0];
        long D = line[next][1];
        long F = line[next][2];

        long denominator = A * D - B * C;
        if (denominator == 0) return;

        long numeratorX = B * F - E * D;  // x의 분자
        long numeratorY = E * C - A * F;  // y의 분자

        if (numeratorX % denominator == 0 && numeratorY % denominator == 0) {  // 정수 좌표만 포함
            int x = (int) (numeratorX / denominator);
            int y = (int) (numeratorY / denominator);
            set.add(new Pos(y, x));

            // 좌표 최대부터 최소를 알기 위해 설정
            maxX = Math.max(maxX, x);
            maxY = Math.max(maxY, y);
            minX = Math.min(minX, x);
            minY = Math.min(minY, y);
        }
    }

    public String[] solution(int[][] line) {
        HashSet<Pos> set = new HashSet<>();
        for (int i = 0; i < line.length; i++) {
            for (int j = i + 1; j < line.length; j++) {
                check(line, set, i, j);
            }
        }

        int x = maxX - minX + 1;  // x 좌표 총 길이 (좌우 합 + 1)
        int y = maxY - minY + 1;  // y 좌표 총 길이 (좌우 합 + 1)
        String[] answer = new String[y];
        Arrays.fill(answer, ".".repeat(x));

        for(Pos pos : set){
            // y축은 양수 -> 음수, x축은 음수 -> 양수
            int r = maxY - pos.r;
            int c = pos.c - minX;

            StringBuilder sb = new StringBuilder(answer[r]);
            sb.setCharAt(c, '*');
            answer[r] = sb.toString();
        }

        return answer;
    }
}
