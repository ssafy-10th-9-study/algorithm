class Solution {
    public int solution(int[] diffs, int[] times, long limit) {
        int answer = 0;

        int left = Integer.MAX_VALUE, right = Integer.MIN_VALUE;
        for (int diff : diffs) {
            left = Math.min(left, diff);
            right = Math.max(right, diff);
        }

        int size = diffs.length;
        while (left <= right) {
            int mid = (left + right) / 2;
            long sum = times[0];
            for (int i = 1; i < size; i++) {
                sum += expression(diffs, times, i, mid);
            }

            if (sum <= limit) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        return left;
    }

    private static int expression(int[] diffs, int[] times, int cur, int level) {
        if (diffs[cur] - level > 0) {
            return (diffs[cur] - level) * (times[cur] + times[cur - 1]) + times[cur];
        } else {
            return times[cur];
        }
    }
}