class PGS_138475_억억단을외우자 {
    /*
        6
        1 2 3 6
        A : 약수의 개수 / 2 = 두 조합으로 해당 수를 만들 수 있는 경우의 수
        A * 2 -> 해당 두 수로 억억단에 등장하는 횟수
        결국 약수의 개수 = 억억단에 등장하는 횟수.
        start의 개수는 100_000을 넘지 않는다.
        원소는 e를 넘지 않음.
        12
        2 2 3
        2^2 * 3
        3 * 2 = 6개
        1 2 3 4 6 12
        5백만개 약수개수 다 세서 몇 번 등장하는지 어떻게 알아냈다 치자. 그럼 starts~e까지 누가 최댄지 또 구간 최대 구해야한다.
        세그먼트 트리 쓰기
    */

    static class Node {
        int val;
        int idx;
        Node (int val, int idx) {
            this.val = val;
            this.idx = idx;
        }
    }

    Node init_seg(int[] divs, Node[] tree, int start, int end, int node) {
        if (start == end) {
            return tree[node] = new Node(divs[start], start);
        }
        else {
            int mid = (start + end) / 2;
            Node left = init_seg(divs, tree, start, mid, node * 2);
            Node right = init_seg(divs, tree, mid + 1, end, node * 2 + 1);
            if (left.val == right.val) {
                tree[node] = left.idx < right.idx ? left : right;
            } else {
                tree[node] = left.val > right.val ? left : right;
            }
            return tree[node];
        }
    }

    Node query(int[] divs, Node[] tree, int start, int end, int left, int right, int node) {
        if (right < start || end < left) {
            return new Node(Integer.MIN_VALUE, -1);
        }
        if (left <= start && end <= right) {
            return tree[node];
        }

        int mid = (start + end) / 2;
        Node leftNode = query(divs, tree, start, mid, left, right, node * 2);
        Node rightNode = query(divs, tree, mid + 1, end, left, right, node * 2 + 1);
        if (leftNode.val == rightNode.val) {
            return leftNode.idx < rightNode.idx ? leftNode : rightNode;
        } else {
            return leftNode.val > rightNode.val ? leftNode : rightNode;
        }
    }

    public int[] solution(int e, int[] starts) {
        int[] answer = new int[starts.length];
        int[] divs = new int[e + 1];
        int height = (int)Math.ceil(Math.log(e) / Math.log(2));
        Node[] tree = new Node[(1 << (height + 1))];
        for (int i = 1; i < divs.length; i++) {
            for (int j = 1; j <= e / i; j++)
            {
                divs[i * j]++;
            }
        }
        init_seg(divs, tree, 1, e, 1);
        for (int i = 0; i < starts.length; i++) {
            int start = starts[i];
            Node ans = query(divs, tree, 1, e, start, e, 1);
            answer[i] = ans.idx;
        }
        return answer;
    }
}