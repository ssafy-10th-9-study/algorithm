import java.util.*;

/**
 * 23:25 시작
 * 멍청하게 구현해도 50 * 50 * 1,000 * 10 (문자열 최대 길이) = 25,000,000
 * Union Find를 사용하면 같은 그룹인지 확인 가능
 * 모든 문자열에 intern()을 사용해서 레퍼런스로 비교한다.
 */
class Solution {
    static final String EMPTY = "".intern();
    static final int R = 50;
    static final int C = 50;

    public String[] solution(String[] commands) {
        int size = R * C;
        String[] table = new String[size];
        Arrays.fill(table, EMPTY);
        UnionFind uf = new UnionFind(size);
        String[] answer = new String[commands.length];
        int count = 0;

        for (String command : commands) {
            String[] tokens = command.split(" ");
            switch (tokens[0]) {
                case "UPDATE": {
                    if (tokens.length == 4) {
                        int r = Integer.parseInt(tokens[1]) - 1;
                        int c = Integer.parseInt(tokens[2]) - 1;
                        String value = tokens[3].intern();
                        // r, c 셀의 그룹을 찾아서 모두 변환
                        int group = uf.find(r * C + c);
                        for (int i = 0; i < size; i++) {
                            if (uf.find(i) == group) {
                                table[i] = value;
                            }
                        }
                    } else if (tokens.length == 3) {
                        String value1 = tokens[1].intern();
                        String value2 = tokens[2].intern();
                        for (int i = 0; i < size; i++) {
                            if (table[i] == value1) {
                                table[i] = value2;
                            }
                        }
                    }
                } break;
                case "MERGE": {
                    int r1 = Integer.parseInt(tokens[1]) - 1;
                    int c1 = Integer.parseInt(tokens[2]) - 1;
                    int r2 = Integer.parseInt(tokens[3]) - 1;
                    int c2 = Integer.parseInt(tokens[4]) - 1;
                    if (r1 == r2 && c1 == c2) continue;
                    // 두 r, c 셀의 그룹
                    int group1 = uf.find(r1 * C + c1);
                    int group2 = uf.find(r2 * C + c2);
                    // 값을 가진 셀로 설정
                    String value = EMPTY;
                    if (table[group1] != EMPTY) {
                        value = table[group1];
                    } else if (table[group2] != EMPTY) {
                        value = table[group2];
                    }
                    // 모든 셀을 일단 경로 압축
                    for (int i = 0; i < size; i++) {
                        uf.find(i);
                    }
                    // group2에 속한 셀을 모두 group1과 병합
                    for (int i = 0; i < size; i++) {
                        if (uf.find(i) == group2) {
                            uf.union(i, group1);
                        }
                    }
                    // 다시 모든 셀을 일단 경로 압축
                    for (int i = 0; i < size; i++) {
                        uf.find(i);
                    }
                    for (int i = 0; i < size; i++) {
                        // Queue, Stack 등으로 최적화 가능
                        int found = uf.find(i);
                        if (found == group1 || found == group2) {
                            table[i] = value;
                        }
                    }
                } break;
                case "UNMERGE": {
                    int r = Integer.parseInt(tokens[1]) - 1;
                    int c = Integer.parseInt(tokens[2]) - 1;
                    int group = uf.find(r * C + c);
                    String value = table[group];
                    // group에 속한 모든 셀을 일단 경로 압축하면서 초기값 입력
                    for (int i = 0; i < size; i++) {
                        if (uf.find(i) == group) {
                            table[i] = EMPTY;
                        }
                    }
                    for (int i = 0; i < size; i++) {
                        // Queue, Stack 등으로 최적화 가능
                        if (uf.find(i) == group) {
                            uf.reset(i);
                        }
                    }
                    // 병합을 해제하기 전 셀이 값을 가지고 있었을 경우 r, c에 값 입력
                    table[r * C + c] = value;
                } break;
                case "PRINT": {
                    int r = Integer.parseInt(tokens[1]) - 1;
                    int c = Integer.parseInt(tokens[2]) - 1;
                    String value = table[r * C + c];
                    if (value == EMPTY) {
                        answer[count++] = "EMPTY";
                    } else {
                        answer[count++] = value;
                    }
                } break;
            }
        }
        return Arrays.copyOf(answer, count);
    }

    static class UnionFind {
        private int[] parent;

        public UnionFind(int n) {
            this.parent = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
            }
        }

        public int find(int a) {
            if (a == parent[a]) return a;

            return parent[a] = find(parent[a]);
        }

        public void union(int a, int b) {
            a = find(a);
            b = find(b);

            if (a != b) {
                parent[b] = a;
            }
        }

        public void reset(int a) {
            parent[a] = a;
        }
    }
}
