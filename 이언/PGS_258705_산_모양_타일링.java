import java.util.*;

/**
 * count
 *      /\
 *     /? \
 *    /____\
 *   /\    /\
 *  /  \  /  \
 * /____\/____\
 *
 * count_
 *      /\
 *     /? \
 *    /____\
 *   /\    /
 *  /  \  /
 * /____\/
 *
 * 두 가지 경우의 수를 계산하면서 dp
 * count_는 /_/ 이런 모양으로 연결할 때 사용
 */
class Solution {
    static final int MOD = 10007;

    public int solution(int n, int[] tops) {
        int[] count = new int[n + 1];
        int[] count_ = new int[n + 1];

        count[0] = 1;
        count_[0] = 1;

        for (int i = 1; i <= n; i++) {
            if (tops[i - 1] != 0) {
                count[i] = count[i - 1] * 3 + count_[i - 1] * 1;
                count[i] %= MOD;
                count_[i] = count[i - 1] * 2 + count_[i - 1] * 1;
                count_[i] %= MOD;
            } else {
                count[i] = count[i - 1] * 2 + count_[i - 1] * 1;
                count[i] %= MOD;
                count_[i] = count[i - 1] * 1 + count_[i - 1] * 1;
                count_[i] %= MOD;
            }
        }

        return count[n];
    }
}
