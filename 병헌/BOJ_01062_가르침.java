package boj.gold4;

/**
 * - @author 이병헌
 * - @since 10/28/2024
 * - @see https://www.acmicpc.net/problem/1062
 * - @git https://github.com/Hunnibs
 * - @youtube
 * - @performance
 * - @category # Hash
 * - @note
 */

import java.util.*;
import java.io.*;

public class BOJ_01062_가르침 {
    private static int answer;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());

        HashSet<Character> alphabet = new HashSet<>();
        ArrayList<HashSet<Character>> table = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            String s = br.readLine();
            table.add(new HashSet<>());
            for (int j = 4; j < s.length() - 4; j++) {
                Character c = s.charAt(j);
                switch (c) {
                    case 'a':
                    case 'n':
                    case 't':
                    case 'i':
                    case 'c':
                        break;
                    default:
                        table.get(i).add(c);
                        alphabet.add(s.charAt(j));
                        break;
                }
            }
        }

        if (K - 5 >= 0) {
            sol(N, K - 5, alphabet, table);
            System.out.print(answer);
        } else{
            System.out.println(0);
        }
    }

    private static void sol(int N, int alphabetCnt, HashSet<Character> alphabet, ArrayList<HashSet<Character>> table) {
        // 배울 수 있는 단어 개수가 배워야 할 단어 개수보다 많다면 return
        if (alphabet.size() <= alphabetCnt) {
            answer = N;
            return;
        }

        ArrayList<Character> toLearn = new ArrayList<>();
        Iterator<Character> iter = alphabet.iterator();
        while (iter.hasNext()) {
            Character c = iter.next();
            toLearn.add(c);
        }

        boolean[] alpha = new boolean[26];

        dfs(toLearn, alpha, 0, 0, alphabetCnt, table);
    }

    private static int dfs(ArrayList<Character> toLearn, boolean[] alpha, int depth, int cur, int max, ArrayList<HashSet<Character>> table) {
        //기저 조건
        if (depth > max){
            return 0;
        }

        if (cur == toLearn.size()) {
            int sum = 0;
            for (int i = 0; i < table.size(); i++) {
                HashSet<Character> word = table.get(i);
                boolean flag = false;

                Iterator<Character> iter = word.iterator();
                while (iter.hasNext()) {
                    Character c = iter.next();
                    if (!alpha[c - 97]) {
                        flag = true;
                        break;
                    }
                }

                if (!flag) {
                    sum++;
                }
            }

            return sum;
        }

        Character c = toLearn.get(cur);
        alpha[c - 97] = true;
        answer = Math.max(dfs(toLearn, alpha, depth + 1, cur + 1, max, table), answer);
        alpha[c - 97] = false;
        answer = Math.max(dfs(toLearn, alpha, depth, cur + 1, max, table), answer);

        return 0;
    }
}
