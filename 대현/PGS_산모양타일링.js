function solution(n, tops) {
    const MOD = 10007;
    
    let dp = [1, 0]; // 최초 정방향 삼각형에 대한 초기값
    let next;
    
    for (let i = 0; i < n; i++) {
        next = [0, 0];
        if (tops[i]) {
            // 위에 정삼각형이 붙어있으면
            next[0] = (dp[0] * 3 + dp[1] * 2) % MOD; // 정방향 삼각형까지 채워넣는 경우의 수
            next[1] = (dp[0] + dp[1]) % MOD; // 남기는 경우의 수
        } else {
            // 안 붙어있으면
            next[0] = (dp[0] * 2 + dp[1]) % MOD;
            next[1] = (dp[0] + dp[1]) % MOD;
        }
        dp = next;
    }
    
    return (dp[0] + dp[1]) % MOD;
}
