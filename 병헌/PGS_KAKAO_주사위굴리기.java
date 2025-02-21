package boj.gold4;

import java.util.*;

public class PGS_KAKAO_주사위굴리기 {

    class Solution {
        int[] answer = {};
        int maxRate = 0;

        public int[] solution(int[][] dice) {
            int n = dice.length;

            combination(0, 0, n, new int[n/2], dice);
            for (int i = 0; i < n / 2; i++) {
                answer[i]++;
            }

            return answer;
        }

        // A가 고를 수 있는 주사위의 경우의 수 (같은 주사위를 고르지 못한다)
        private void combination(int depth, int cur, int n, int[] choices, int[][] dice) {
            // 기저 조건
            if (depth == n / 2) {
                int winRate = winRate(n, choices, dice);
                if(maxRate < winRate) {
                    maxRate = winRate;
                    answer = choices.clone();
                }

                return;
            }

            if (cur == n) return;

            choices[depth] = cur;
            combination(depth + 1, cur + 1, n, choices, dice);
            combination(depth, cur + 1, n, choices, dice);
        }

        private int winRate(int n, int[] choices, int[][] dice){
            int[] bChoices = findNotChoices(n, choices);

            Map<Integer, Integer> aScore = new HashMap<>();
            Map<Integer, Integer> bScore = new HashMap<>();

            maxScore(0, n / 2, choices, dice, 0, aScore);
            maxScore(0, n / 2, bChoices, dice, 0, bScore);

            return match(aScore, bScore);
        }

        private int match(Map<Integer, Integer> aScore, Map<Integer, Integer> bScore) {
            Set<Integer> aScoreKey = aScore.keySet();
            Set<Integer> bScoreKey = bScore.keySet();

            int winRate = 0;
            for (int a : aScoreKey) {
                for (int b : bScoreKey) {
                    if (a > b) {
                        winRate += (aScore.get(a) * bScore.get(b));
                    }
                }
            }

            return winRate;
        }

        private void maxScore(int depth, int k, int[] choices, int[][] dice, int sum, Map<Integer, Integer> score) {
            if (depth == k) {
                if (score.containsKey(sum)) score.put(sum, score.get(sum) + 1);
                else score.put(sum, 1);

                return;
            }

            for (int i = 0; i < 6; i++) {
                maxScore(depth + 1, k, choices, dice, sum + dice[choices[depth]][i], score);
            }
        }

        private int[] findNotChoices(int n, int[] choices) {
            int[] notChoices = new int[n/2];
            boolean[] nums = new boolean[n];

            for (int choice : choices) {
                nums[choice] = true;
            }

            int idx = 0;
            for (int i = 0; i < n; i++) {
                if (!nums[i]) notChoices[idx++] = i;
            }

            return notChoices;
        }
    }
}
