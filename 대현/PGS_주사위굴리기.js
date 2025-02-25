function solution(dice) {
    const n = dice.length;
    const half = n / 2;
    
    // 주사위 경우의 수 미리 모두 계산
    function getSumCount(diceArr) {
        let sumMap = new Map([[0, 1]]);
        
        for (const diceIdx of diceArr) {
            const newMap = new Map();
            for (const [sum, count] of sumMap) {
                for (const num of dice[diceIdx]) {
                    const newSum = sum + num;
                    newMap.set(newSum, (newMap.get(newSum) || 0) + count);
                }
            }
            sumMap = newMap;
        }
        
        return sumMap;
    }
    
    let maxWinRate = 0;
    let answer = [];
    
    // 비트마스킹으로 모든 조합 확인
    for (let i = 0; i < (1 << n); i++) {
        if (countBits(i) != half) continue;
        
        // a,b 주사위 분배
        const aArr = [], bArr = [];
        for (let j = 0; j < n; j++) {
            if (i & (1 << j)) aArr.push(j);
            else bArr.push(j);
        }
        
        // 합 계산
        const aSum = getSumCount(aArr);
        const bSum = getSumCount(bArr);
        
        // 승리 확률 계산
        let wins = 0;
        let total = 0;
        
        for (const [aValue, aCount] of aSum) {
            for (const [bValue, bCount] of bSum) {
                const cases = aCount * bCount;
                if (aValue > bValue) wins += cases;
                total += cases;
            }
        }
        
        const winRate = wins / total;
        if (winRate > maxWinRate) {
            maxWinRate = winRate;
            answer = aArr.map(x => x + 1).sort((a, b) => a - b);
        }
    }
    
    return answer;
}

function countBits(n) {
    let count = 0;
    while (n) {
        count += n & 1;
        n >>= 1;
    }
    return count;
}
