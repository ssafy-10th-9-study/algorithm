import java.util.*;
import java.io.*;

public class Main {
    static class Node {
        int data,height;
        public Node(int data){
            this.data = data;
        }
    }
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws Exception{
        int n = Integer.parseInt(br.readLine());
        List<Integer> list = new ArrayList<>();
        long [] count = new long [300001];
        Node [] tree = new Node [300001];
        long ans = 0;


        while(n-- > 0){
            int num = Integer.parseInt(br.readLine());

            // root처리
            if(list.isEmpty())  {
                list.add(num);
                tree[num] = new Node(num);
                count[num] = 0;
                ans = count[num];
                sb.append(ans).append("\n");
                continue;
            }

            // 양쪽 서치,
            int idx = binarySearchLeft(list, num);
            if(idx == list.size()){
                // 가장 큰 값
                // 노드 갱신
                int nodeNo = list.get(idx - 1);
                Node pNode = tree[nodeNo];
                tree[num] =  new Node(num);
                tree[num].height = pNode.height + 1;
                count[num] = count[nodeNo] + 1;

                list.add(num);

            }

            else if(idx == 0){
                int nodeNo = list.get(0);
                Node pNode = tree[nodeNo];
                tree[num] =  new Node(num);
                tree[num].height = pNode.height + 1;
                count[num] = count[nodeNo] + 1;

                list.add(0,num);
            }

            else{
                Node leftPNode = tree[list.get(idx-1)];
                Node rightPNode = tree[list.get(idx)];
                if(leftPNode.height > rightPNode.height){
                    tree[num] = new Node(num);
                    tree[num].height = leftPNode.height + 1;
                    count[num] = count[list.get(idx-1)] + 1;
                }
                else{
                    tree[num] = new Node(num);
                    tree[num].height = rightPNode.height + 1;
                    count[num] = count[list.get(idx)] + 1;
                }



                list.add(idx,num);
            }
            ans += count[num];
            sb.append(ans).append("\n");


        }
        System.out.println(sb.toString());
    }
    private static int binarySearchLeft(List<Integer> list, int target){
        int left = 0;
        int right = list.size();
        while(left < right){
            int mid = (left + right) / 2;
            if(list.get(mid) < target){
                left = mid + 1;
            }
            else{
                right = mid;
            }
        }

        return left;
    }
}