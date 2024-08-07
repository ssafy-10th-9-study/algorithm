package bj.g3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 @author 김영욱
 @since 2024. 08. 07
 @see
 @git
 @performance
 @category #
 @note
 시간 : 1초
 n <= 100, 1<=M<=10,000,000
 새로운 앱을 실행시키기 위해 필요한 메모리가 부족해지면
 스마트폰의 운영체제는 활성화 되어 있는 앱들 중 몇 개를 선택하여 메모리로부터 삭제하는 수밖에 없다.
 이를 앱의 '비활성화'라고 하고, 나는 비활성화 문제를 해결하기 위한 프로그램을 작성해야한다.

 현재 N개의 앱이 활성화 되어있다. 이들 앱은 각각 M바이트만큼의 메모리를 사용하고 있다.
 앱을 비활성화한 후에 다시 실행하고자 할 경우, 추가적으로 들어가는 비용을 C라고 한다.
 사용자가 새로운 앱 B를 실행하고자 하여, 추가로 M바이트의 메모리가 필요하다고 하자.
 즉 현재 활성화 되어 있는 앱(A)들 중에서 몇 개를 비활성화하여 M바이트 이상의 메모리를
 추가로 확보해야 하는 것이다. 이 중에서 비활성화 했을 경우 비용 C의 합을 최소화하여
 필요한 메모리 M바이트를 확보하는 방법을 찾아야 한다.

 메모리 조건에 부합하면서 최소 가치의 합을 찾는 문제이다.
 이 문제는 메모리를 쪼갤 수 없기 때문에 0-1 KanpSack을 사용할 수 있다.

 알고리즘의 큰 틀은 다음과 같다.
 이번 i번째의 메모리를 비활성화 하냐 안하냐
 2가지 선택지 밖에 없다.

 또한 Dp는 부분 문제 해의 합들이 결국 문제의 답이 되는 특징이 있다.
 하지만 이 문제는 최소의 비용을 구하는 문제이기 때문에 얼핏 헷갈릴 수 있다.(내가 그랬음)
 배낭 문제를 접해본적이 없어서 최소 비용을 비교하며 DP테이블을 채울 수 있다고 생각했지만
 그런 식으로 하면 완탐이나 다름 없는 로직이 나왔다.

 반복문 구조
 for(입력 받은 모든 앱 순회)
 for(앱들을 비활성화 하는데 드는 비용의 합만큼 순회)

 dp[현재 앱][비용] = 메모리

 현재 앱의 cost가 j보다 클 경우 끄지 못하니까 활성화시켜둔다
 dp[i][j] = dp[i-1][j]

 그렇지 않다면 현재 앱을 끈 뒤의 byte와 그렇지 않을 경우의 byte중 큰 값을 dp에 입력한다.
 dp[i][j] = max(byte + dp[i-1][j-cost], dp[i-1][j])


 반복문을 돌면서 현재 dp가 필요한 메모리보다 값이 크거나 같다면
 현재 드는 비용(j)과 ans를 비교해서 더 작은 값을 ans에 넣어준다.

 입력 N과 M => 앱의 개수N, 추가로 필요한 M바이트의 메모리 M
 각 앱A가 사용 중인 메모리 바이트 N개
 비활성화했을 경우 비용 C들 N개

 메모리를 가장 크게, 비용을 가장 적게 채워넣고
 60M 이상 중에 가장 비용이 작은 놈을 ans에 넣고 출력

 주의할 점 1. 비활성화 비용이 0인 놈도 고려해야 하기 때문에 2중 for문 시 j(cost)를 0부터 시작해야 한다.
 주의할 점 2. 가장 최소 비용을 뽑는다고 dp테이블에 비용을 메인으로 비교해서는 안된다.
 ( j를 cost로 두고 제일 큰 메모리를 저장하는 방식이 합리적 )
 */

public class BJ_G3_7579_앱_2차시기 {

    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer tokens;

    static int M;
    static int N;
    static int ans = Integer.MAX_VALUE;
    static int[] memory, cost;
    static int dp[][];

    public static void main(String[] args) throws IOException {
        tokens = new StringTokenizer(input.readLine());
        N = Integer.parseInt(tokens.nextToken());
        M = Integer.parseInt(tokens.nextToken());

        memory = new int[N+1];
        cost = new int[N+1];

        tokens = new StringTokenizer(input.readLine());
        int sum = 0;
        for(int i=1; i<=N; i++) {
            memory[i] = Integer.parseInt(tokens.nextToken());
        }

        tokens = new StringTokenizer(input.readLine());
        for(int i=1; i<=N; i++) {
            cost[i] = Integer.parseInt(tokens.nextToken());
            sum += cost[i];
        }

        dp = new int[N+1][sum+1];

        for(int i=1; i<=N; i++) {
            for(int j=0; j<=sum; j++) {
                if(cost[i] > j) { // 현재 앱을 종료하는 비용보다 내가 가진 비용이 적다면
                    dp[i][j] = dp[i-1][j];
                }
                else { // 현재 앱을 종료하는 비용보다 내가 가진 비용이 같거나 크다면
                    // 지금 이 가격에 전에 물건까지 샀을 때의 비용과,
                    // 현재 앱을 종료하고 남은 cost 중에서 살 수 있는 가장 큰 값을 더한거 중에 뭐가 더 크냐??
                    dp[i][j] = Math.max(dp[i-1][j] , memory[i] + dp[i-1][j-cost[i]]);
                }
                if(dp[i][j] >= M) {
                    ans = Math.min(ans, j);
                }
            }
        }
        System.out.println(ans);
    }

}
