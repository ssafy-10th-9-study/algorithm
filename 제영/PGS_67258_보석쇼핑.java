import java.util.*;
class PGS_67258_보석쇼핑 {
    /* 
        구간 시작, 구간 끝, 보석 종류
        길이 기준 오름차순, 길이 같으면 구간시작 기준 오름차순
        되면 정답에 넣고 왼쪽 줄이고, 안 되면 오른쪽 늘리고
        안 될 때 더 이상 늘릴 오른쪽이 없으면 포기
    */
    static class JewRange implements Comparable<JewRange> {
        int start;
        int end;

        JewRange(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public int compareTo(JewRange o1) {
            int myDist = end - start + 1;
            int compDist = o1.end - o1.start + 1;
            if (myDist != compDist) {
                return Integer.compare(myDist, compDist);
            } else {
                return Integer.compare(start, o1.start);
            }
        }
    }

    public int[] solution(String[] gems) {
        int[] answer = {};
        HashMap<String, Integer> jewMap = new HashMap<>();
        for (int i = 0; i < gems.length; i++) {
            String jew = gems[i];
            if (!jewMap.containsKey(jew)) {
                jewMap.put(jew, 0);
            }
        }
        // jewMap의 size를 이용해 총 보석 개수 세기 가능
        /* 
            jews의 size를 이용해 현재 구간의 보석개수 세기 가능. 빠질 때 마다 jewMap에서 value
            빼주고, 0이 되면 set에서 지우는 방식으로 유지
        */
        Set<String> jews = new HashSet<>();
        TreeSet<JewRange> ansSet = new TreeSet<>();
        int start = 0;
        int end = -1;
        while (start < gems.length && end < gems.length) {
            if (jews.size() != jewMap.size()) {
                end++;
                if (end == gems.length) {
                    break;
                }
                String target = gems[end];
                jews.add(target);
                jewMap.put(target, jewMap.get(target) + 1);
            } else {
                ansSet.add(new JewRange(start, end));
                String target = gems[start];
                jewMap.put(target, jewMap.get(target) - 1);
                if (jewMap.get(target) == 0) {
                    jews.remove(target);
                }
                start++;
                if (start == gems.length) {
                    break;
                }
            }
        }
        JewRange ans = ansSet.first();
        return new int[]{ans.start + 1, ans.end + 1};
    }
}