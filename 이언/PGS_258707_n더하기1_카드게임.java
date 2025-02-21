import java.util.*;

/**
 * n < 1000 (n = 6k)
 * coin <= n
 *
 * 손에 카드 n/3장, 동전 coin개로 시작
 * [라운드 시작]
 * - 덱이 비었다면 게임 종료
 * - 덱에서 2장 뽑음
 * - 카드 1장 당 동전 1개 소모해서 각각 가져올지 결정
 * - 손에서 합이 n+1이 되는 두 카드가 있으면 내고 다음 라운드로 진행, 없으면 게임 종료
 *
 * 문제: 최대 도달 가능한 라운드는?
 *
 * 기본적으로 코인을 아끼기 위해 다음 라운드로 진행할 수 없을 경우에만 코인을 사용한다.
 * 만약 이전 라운드에서 구매를 했어야만 짝이 맞는데 안샀다면 과거로 돌아가서 해당 카드를 샀다고 생각하고, 코인을 2개 사용한다. 각 카드는 반드시 하나의 짝을 가지므로 어떤 카드를 구매하든 결과에 영향 X
 * 이렇게 라운드를 진행하며 더 이상 짝을 만들 수 없는 라운드를 찾는다.
 *
 * 어떤 라운드에 카드에는 두 종류가 있다.
 * 1. 이미 손에 있는 카드
 * 2. 코인을 사용해서 가져올 수 있는/있었던 모든 카드 (이전 라운드 포함)
 * 여기서 coin개의 코인으로 최대 몇 개의 짝을 만들 수 있는지를 매 라운드마다 계산해야 한다.
 *
 * 이것을 빠르게 검색하기 위해 매 라운드 마다 카드를 종류에 따라 분류하자. (n < 1000이라 안해도 충분할 듯)
 * - 이미 손에 있는 카드
 *     (=짝이 있는 카드, 짝이 없는 카드로 분리)
 * - 코인 1개를 사용해서 가져오면 손에 있는 카드와 짝이 맞는 카드
 * - 코인 2개를 사용해서 가져오면 서로 짝이 맞는 카드
 * - 아직 짝이 없는 카드
 * 이렇게 분류하면 해시셋 추가/삭제가 O(1)일 경우 O(n) 시간 안에 정답을 찾을 수 있다.
 * 이런 최적화 구현은 CardHelper 내부에 추상화되어 있다.
 */
class Solution {
    static final int CARD_PER_ROUND = 2;

    public int solution(int coin, int[] cards) {
        int n = cards.length;
        int initCount = n / 3;
        CardHelper helper = new CardHelper(n);
        for (int i = 0; i < initCount; i++) {
            helper.addToHand(cards[i]);
        }
        int round, index;
        for (round = 1, index = initCount; index < n; round++, index += CARD_PER_ROUND) {
            // 라운드 시작
            for (int i = index; i < index + CARD_PER_ROUND; i++) {
                helper.addToPool(cards[i]);
            }
            // 짝이 없는 경우 코인 사용
            if (helper.getPairs() == 0) {
                if (coin >= 1 && helper.useOneCoin()) {
                    coin -= 1;
                } else if (coin >= 2 && helper.useTwoCoins()) {
                    coin -= 2;
                }
            }
            // 다시 짝이 있는지 검사
            if (helper.getPairs() > 0) {
                helper.removePair();
            } else {
                // 이래도 짝이 없으면 게임 종료
                break;
            }
        }
        return round;
    }

    /**
     * addToHand는 다른 메서드 호출 전에만 호출해야 함.
     */
    static class CardHelper {
        public final int n;

        /** 이미 손에 있는 짝이 없는 카드 */
        private final Set<Integer> hand;
        /** 가져올 수 있는, 아직 짝이 없는 카드 */
        private final Set<Integer> pool;
        /** 손에 있는 짝 개수 */
        private int pairs;
        /** 코인 1개를 사용해서 가져오면 손에 있는 카드와 짝이 맞는 카드 개수 */
        private final Set<Integer> oneCoinCards;
        /** 코인 2개를 사용해서 가져오면 짝이 되는 카드의 짝 개수 (카드 개수의 절반) */
        private int twoCoins;

        public CardHelper(int n) {
            this.n = n;
            hand = new HashSet<>();
            pool = new HashSet<>();
            oneCoinCards = new HashSet<>();
        }

        public int getPairs() {
            return pairs;
        }

        public void removePair() {
            if (pairs == 0) {
                throw new RuntimeException();
            }
            pairs--;
        }

        public void addToHand(int card) {
            int other = n + 1 - card;
            if (hand.contains(other)) {
                // 손에 있는 카드의 짝일 경우
                hand.remove(other);
                pairs++;
            } else {
                // 아직 짝이 없을 경우
                hand.add(card);
            }
        }

        public void addToPool(int card) {
            int other = n + 1 - card;
            if (hand.contains(other)) {
                // 손에 있는 카드의 짝일 경우
                oneCoinCards.add(card);
            } else if (pool.contains(other)) {
                // pool에 있는 카드의 짝일 경우
                pool.remove(other);
                twoCoins++;
            } else {
                // 아직 짝이 없을 경우
                pool.add(card);
            }
        }

        public boolean useOneCoin() {
            if (oneCoinCards.isEmpty()) return false;

            int card = oneCoinCards.iterator().next();
            int other = n + 1 - card;
            oneCoinCards.remove(card);
            hand.remove(other);
            pairs++;
            return true;
        }

        public boolean useTwoCoins() {
            if (twoCoins == 0) return false;

            twoCoins--;
            pairs++;
            return true;
        }
    }
}
