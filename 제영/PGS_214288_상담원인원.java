import java.util.*;
/* 
    빈 곳에 가서 상담을 받거나
    그 사람만을 위한 상담사 추가하기
    이 때, 기다린 시간은 현재 상담 시작시간 - 이전 상담 종료시간 
    해당 값이 0보다 크거나 같을 경우, 안 기다렸다고 할 수 있음. 음수 값 만큼 기다림
    10 - 20
    16 - 26 4분
    27 - 32 4분
    28 - 38 4분
    
    기다려야하는 경우이고, n이 남아있을 경우
    현재 개수일 때 vs 하나 늘렸을 때 기다리는 시간을 각각 계산한 후 차이를 구함
    모든 유형에 대해 해당 작업을 수행해 검증
    
    현재 개수일 때 걸리는 지연시간이 0일 경우 해당 유형 제외, 해당 유형 멘토 고정
    
    차이가 제일 큰 유형에 멘토 삽입, 현재 개수일 때 걸리는 지연시간을 하나 늘렸을 때로 갱신
    멘토가 삽입 된 유형에서 하나늘렸을 때 기다리는 시간을 갱신
    나머진 유지
    n이 더 남아있을 경우, 차이를 계속해서 비교. n이 0이될 때 까지 동일 작업 수행
    
    n이 없을 경우, 현재 개수일 때 걸리는 지연시간을 모두 더해 답으로 출력.
    
    항상 멘토들 중 종료시간이 짧은 멘토부터 먼저 배정하면 최선임.
*/

class PGS_214288_상담원인원 {
    static class DelayTime{
        int index;
        int nowDelay; // 현재 개수일 때 지연시간
        int plusDelay; // 하나 더 생겼을 때 지연시간

        DelayTime(int index, int nowDelay, int plusDelay) {
            this.index = index;
            this.nowDelay = nowDelay;
            this.plusDelay = plusDelay;
        }
    }

    static class Mentor implements Comparable<Mentor>{
        int finTime;

        Mentor(int finTime) {
            this.finTime = finTime;
        }

        @Override
        public int compareTo(Mentor o1) {
            return Integer.compare(this.finTime, o1.finTime);
        }
    }
    public void calTargetDelayTime(ArrayList<ArrayList<int[]>> timeLines, DelayTime[] delayTimes, int index, int count) {
        ArrayList<int[]> targetTimeLines = timeLines.get(index);
        DelayTime targetDelayTime = delayTimes[index];
        int nowDelay = 0;
        int plusDelay = 0;
        PriorityQueue<Mentor> now = new PriorityQueue<>();
        PriorityQueue<Mentor> plus = new PriorityQueue<>();
        for (int i = 0; i < count; i++) {
            now.offer(new Mentor(0));
            plus.offer(new Mentor(0));
        }
        plus.offer(new Mentor(0));
        for (int[] timeLine : targetTimeLines) {
            int start = timeLine[0];
            int keep = timeLine[1];
            Mentor nowTarget = now.poll();
            Mentor plusTarget = plus.poll();

            if (nowTarget.finTime > start) {
                nowDelay += nowTarget.finTime - start;
                nowTarget.finTime = nowTarget.finTime + keep;
            } else {
                nowTarget.finTime = start + keep;
            }
            now.offer(nowTarget);

            if (plusTarget.finTime > start) {
                plusDelay += plusTarget.finTime - start;
                plusTarget.finTime = plusTarget.finTime + keep;
            } else {
                plusTarget.finTime = start + keep;
            }
            plus.offer(plusTarget);
        }
        targetDelayTime.nowDelay = nowDelay;
        targetDelayTime.plusDelay = plusDelay;
        targetDelayTime.index = index;
    }

    public void initDelayTimes(ArrayList<ArrayList<int[]>> timeLines, DelayTime[] delayTimes, int[] counts) {
        for (int i = 0; i < counts.length; i++) {
            calTargetDelayTime(timeLines, delayTimes, i, counts[i]);
        }
    }

    public int solution(int k, int n, int[][] reqs) {
        int answer = 0;
        int[] counts = new int[k];
        DelayTime[] delayTimes = new DelayTime[k];
        ArrayList<ArrayList<int[]>> timeLines = new ArrayList<>();

        n -= k;
        Arrays.fill(counts, 1);

        for (int i = 0; i < k; i++) {
            timeLines.add(new ArrayList<>());
            delayTimes[i] = new DelayTime(0, 0, 0);
        }
        for (int[] req : reqs) {
            int start = req[0];
            int keep = req[1];
            int type = req[2];
            timeLines.get(type - 1).add(new int[]{start, keep});
        }

        initDelayTimes(timeLines, delayTimes, counts);
        while (n > 0) {
            DelayTime maxDelayTime = null;
            for (DelayTime target : delayTimes) {
                if (target.nowDelay > 0) {
                    if (maxDelayTime == null) {
                        maxDelayTime = target;
                    } else {
                        maxDelayTime = target.nowDelay - target.plusDelay > maxDelayTime.nowDelay - maxDelayTime.plusDelay ? target : maxDelayTime;
                    }
                }
            }
            if (maxDelayTime == null) {
                break;
            } else {
                counts[maxDelayTime.index]++;
                calTargetDelayTime(timeLines, delayTimes, maxDelayTime.index, counts[maxDelayTime.index]);
                n--;
            }
        }

        for (DelayTime time : delayTimes) {
            answer += time.nowDelay;
        }
        return answer;
    }
}