package bj.g4;

import java.util.*;
import java.io.*;

/**
 * @author 김영욱
 * @git
 * @performance 420ms
 * @category #
 * @note 조건 세가지
 * 1. 노드의 왼쪽 서브트리에 있는 모든 노드의 키는 노드의 키보다 작다.
 * 2. 노드의 오른쪽 서브트리에 있는 모든 노드의 키는 노드의 키보다 크다.
 * 3. 왼쪽, 오른쪽 서브트리도 이진 검색 트리이다.
 * <p>
 * 노드, 트리 클래스 만든다.
 * 트리는 루트만 갖고 있어도 될듯
 * 넣을 때 루트가 null이면 그냥 넣고 아니라면 넣을 자리를 찾는다.( 중위 순회한 값을 입력으로 주니까 이렇게 해도 될듯 )
 * 넣을 값이 루트보다 작으면 왼쪽을 탐색하기 위해 Queue에 넣는다.
 * 크면 오른쪽을 Queue에 넣는다.
 * now의 왼쪽이나 오른족이 빌 떄까지 진행
 * <p>
 * 후위 탐색은 간단하게 재귀로 구현( 왼쪽 끝까지 재귀로 가고 오른쪽 끝까지 재귀로 간다음 마지막에 출력하면 됨)
 * 입력의 길이나 끝을 몰라서 입력은 인터넷을 참고하였다.
 *
 * 두 조건문이 논리적으로는 동일해 보이지만, 실제로는 다르게 작동할 수 있는 이유는 short-circuit 평가 때문입니다.
 * 자바에서 논리 연산자 || (OR)는 왼쪽 피연산자가 true인 경우 오른쪽 피연산자를 평가하지 않는 성질을 가지고 있습니다.
 *
 * if (line == null || line.isEmpty()) { break; }
 * 이 조건문의 경우, line이 null이면 line.isEmpty()를 평가하지 않습니다. 따라서 NullPointerException이 발생하지 않습니다.
 * if (line.isEmpty() || line == null) { break; }
 * 이 조건문의 경우, line이 null이면 line.isEmpty()를 평가하려고 시도하고, 이로 인해 NullPointerException이 발생합니다.
 * 따라서 첫 번째 조건문에서는 line이 null일 때 안전하게 break 문을 실행할 수 있지만,
 * 두 번째 조건문에서는 NullPointerException이 발생할 수 있습니다.
 *
 * 이러한 이유로 null 체크를 먼저 하는 것이 일반적으로 안전한 방법입니다.
 * @see https://www.acmicpc.net/problem/5639
 * @since 2024. 07. 10
 */

public class BJ_G4_5639_이진검색트리 {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder builder = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BinaryTree tree = new BinaryTree();
        String line;
        while (true) {
            line = input.readLine();
            if (line == null || line.isEmpty()) {
                break;
            } else {
                tree.insert(new Node(Integer.parseInt(line)));
            }
        }
        tree.postOrder(tree.root);
        System.out.println(builder);
    }

    private static class Node {
        int name;
        Node left;
        Node right;

        public Node(int name) {
            this.name = name;
            this.left = null;
            this.right = null;
        }
    }

    private static class BinaryTree {
        Node root;

        void insert(Node node) {
            if (this.root == null) {
                root = node;
            } else {
                Node head = root;
                Queue<Node> q = new ArrayDeque<>();
                q.offer(head);

                while (!q.isEmpty()) {
                    Node now = q.poll();
                    if (now.name > node.name) {
                        if (now.left == null) {
                            now.left = node;
                            break;
                        }
                        q.offer(now.left);
                    } else {
                        if (now.right == null) {
                            now.right = node;
                            break;
                        }
                        q.offer(now.right);
                    }
                }
            }
        }

        void postOrder(Node node) {// 후위 순회 왼쪽 -> 오른쪽 -> 루트
            if (node.left != null) postOrder(node.left);
            if (node.right != null) postOrder(node.right);
            builder.append(node.name).append("\n");
        }
    }

}
