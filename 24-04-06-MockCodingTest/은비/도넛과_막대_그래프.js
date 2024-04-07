function solution(edges) {
  const answer = new Array(4).fill(0);
  // 노드 번호: [나가는 간선 수, 들어오는 간선 수]
  const map = new Map();

  for (const [a, b] of edges) {
    if (map.has(a)) {
      map.get(a)[0]++;
    } else {
      map.set(a, [1, 0]);
    }

    if (map.has(b)) {
      map.get(b)[1]++;
    } else {
      map.set(b, [0, 1]);
    }
  }

  for (const [key, val] of map) {
    const [o, i] = val;

    if (o >= 2 && i == 0) {
      answer[0] = key;
    }

    if (o >= 2 && i >= 2) {
      answer[3]++;
    }

    if (o == 0) {
      answer[2]++;
    }
  }

  answer[1] = map.get(answer[0])[0] - answer[2] - answer[3];

  return answer;
}
