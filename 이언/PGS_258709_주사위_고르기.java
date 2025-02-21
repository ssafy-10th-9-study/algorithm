import java.util.*;

class Solution {
    /**
     * n <= 10 (n = 2k)
     * 10C5 = 252
     * 2^10 = 1,024
     * 6^5 = 7,776
     * 6^10 = 60,466,176
     */
    public int[] solution(int[][] dice) {
        int n = dice.length;
        int size = 1 << n;

        List<Integer>[] sumsMap = new List[size];
        // Fill sumsMap
        for (int bits = 0; bits < size; bits++) {
            if (Integer.bitCount(bits) != n / 2) continue;

            sumsMap[bits] = new ArrayList<>(pow(6, n / 2));
            fillSums(sumsMap[bits], dice, bits, 0, 0, 0);
        }
        // Sort sumsMap
        for (List<Integer> sums : sumsMap) {
            if (sums != null) {
                sums.sort(Integer::compare);
            }
        }

        // Compare combinations
        int[] wins = new int[size];
        for (int aBits = 0; aBits < size; aBits++) {
            if (Integer.bitCount(aBits) != n / 2) continue;

            int bBits = (size - 1) ^ aBits;
            List<Integer> aSums = sumsMap[aBits];
            List<Integer> bSums = sumsMap[bBits];
            int ai = 0, bi = 0;
            int aWins = 0;
            while (ai < aSums.size() && bi < bSums.size()) {
                if (aSums.get(ai) < bSums.get(bi)) {
                    aWins += bi;
                    ai++;
                } else if (aSums.get(ai) > bSums.get(bi)) {
                    bi++;
                } else {
                    // Equal
                    int aFreq = 0, bFreq = 0;
                    int sum = aSums.get(ai);

                    while (ai < aSums.size() && aSums.get(ai) == sum) {
                        ai++;
                        aFreq++;
                    }
                    while (bi < bSums.size() && bSums.get(bi) == sum) {
                        bi++;
                        bFreq++;
                    }
                    aWins += aFreq * (bi - bFreq);
                }
            }
            aWins += (aSums.size() - ai) * bSums.size();
            wins[aBits] = aWins;
        }


        int maxWin = 0;
        int maxBits = 0;
        for (int bits = 0; bits < size; bits++) {
            if (Integer.bitCount(bits) != n / 2) continue;

            if (wins[bits] > maxWin) {
                maxWin = wins[bits];
                maxBits = bits;
            }
        }

        int[] answer = new int[n / 2];
        int cnt = 0;
        for (int i = 0; i < n; i++) {
            if ((maxBits & (1 << i)) != 0) {
                answer[cnt++] = i + 1;
            }
        }

        return answer;
    }

    private void fillSums(List<Integer> sums, int[][] dice, int bits, int index, int start, int sum) {
        if (index == dice.length / 2) {
            sums.add(sum);
            return;
        }

        for (int i = start; i < dice.length; i++) {
            if ((bits & (1 << i)) != 0) {
                for (int num : dice[i]) {
                    fillSums(sums, dice, bits, index + 1, i + 1, sum + num);
                }
            }
        }
    }

    private int pow(int a, int n) {
        int v = a;
        for (int i = 1; i < n; i++) {
            v *= a;
        }
        return v;
    }
}
