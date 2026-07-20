https://leetcode.com/problems/best-poker-hand/description/
You are given an integer array ranks and a character array suits. You have 5 cards where the ith card has a rank of ranks[i] and a suit of suits[i].
The following are the types of poker hands you can make from best to worst:
1."Flush": Five cards of the same suit.
2."Three of a Kind": Three cards of the same rank.
3."Pair": Two cards of the same rank.
4."High Card": Any single card.
Return a string representing the best type of poker hand you can make with the given cards.
Note that the return values are case-sensitive.
 
Example 1:
Input: ranks = [13,2,3,1,9], suits = ["a","a","a","a","a"]
Output: "Flush"
Explanation: The hand with all the cards consists of 5 cards with the same suit, so we have a "Flush".

Example 2:
Input: ranks = [4,4,2,4,4], suits = ["d","a","a","b","c"]
Output: "Three of a Kind"
Explanation: The hand with the first, second, and fourth card consists of 3 cards with the same rank, so we have a "Three of a Kind".
Note that we could also make a "Pair" hand but "Three of a Kind" is a better hand.
Also note that other cards could be used to make the "Three of a Kind" hand.

Example 3:
Input: ranks = [10,10,2,12,9], suits = ["a","b","c","a","d"]
Output: "Pair"
Explanation: The hand with the first and second card consists of 2 cards with the same rank, so we have a "Pair".
Note that we cannot make a "Flush" or a "Three of a Kind".
 
Constraints:
- ranks.length == suits.length == 5
- 1 <= ranks[i] <= 13
- 'a' <= suits[i] <= 'd'
- No two cards have the same rank and suit.
--------------------------------------------------------------------------------
Attempt 1: 2026-07-19
Solution 1: Math (10 min)
class Solution {
    public String bestHand(int[] ranks, char[] suits) {
        // 1. 检查 Flush（同花）：5张牌花色是否全等
        boolean isFlush = true;
        for(int i = 1; i < suits.length; i++) {
            if(suits[i] != suits[0]) {
                isFlush = false;
            }
        }
        if(isFlush) {
            return "Flush";
        }
        // 2. 统计点数出现次数
        int[] freq = new int[14]; // 点数范围 2~14
        for(int r : ranks) {
            freq[r]++;
        }
        // 3. 检查 Three of a Kind（三条） 和 Pair（对子）
        boolean hasPair = false;
        for(int f : freq) {
            if(f >= 3) {
                return "Three of a Kind";
            }
            if(f == 2) {
                hasPair = true;
            }
        } 
        if(hasPair) {
            return "Pair";
        }
        // 4. 都不是则返回 High Card（高牌）
        return "High Card";
    }
}

Time Complexity: O(n)
Space Complexity: O(1)

Refer to Deepseek
Leetcode 2347 的这道题本身逻辑很简单，但很多初次接触扑克牌（Poker）的同学容易被英文术语搞晕。这里我为你详细拆解这道题涉及的所有扑克牌名词，以及它们在这道题里的具体判定逻辑。
--------------------------------------------------------------------------------
1. 基础元素（输入数组）
题目给了你两个数组：ranks（点数）和 suits（花色）。
- Ranks（点数 / 牌面值）：代表这张牌是几。在扑克里，通常包括 2, 3, 4, 5, 6, 7, 8, 9, 10, J, Q, K, A。在本题中，它们以字符串形式给出（例如 "10", "J", "A"）。通俗理解：牌面上的数字或字母。
- Suits（花色）：代表这张牌属于哪个花色。标准的扑克牌有 4 种花色，本题用单个字母表示：
- "H" = Hearts（红桃）
- "D" = Diamonds（方块）
- "C" = Clubs（梅花 / 草花）
- "S" = Spades（黑桃）通俗理解：牌背面的颜色分类（红/黑），决定了是否“同花”。
--------------------------------------------------------------------------------
2. 牌型（Hand Types）—— 这是本题的核心判定顺序
题目要求你从 5 张牌中找出 “最好的” 牌型。扑克牌型的强弱通常有固定等级，本题提取了其中 4 种，判定优先级从高到低如下：
① Flush（同花）
- 含义：5 张牌的花色全部相同。
- 本题逻辑：检查 suits 数组，如果 5 个字母全部一样（比如全是 "H"），直接返回 "Flush"。
- 注意：在标准扑克中，同花比三条大，所以代码中优先判断花色全等。
② Three of a Kind（三条 / 三张相同）
- 含义：5 张牌中，有 3 张牌的点数完全相同。
- 本题逻辑：统计 ranks 中每个点数出现的次数。如果某个点数出现次数 >= 3，返回 "Three of a Kind"。
- 注意：即使出现了 4 张相同的（四条），由于 4 >= 3，代码也会判为“三条”（因为题目没给“四条”这个选项，三条是这里最高的计数牌型）。
③ Pair（对子）
- 含义：5 张牌中，有 2 张牌的点数完全相同。
- 本题逻辑：如果没有任何点数出现 3 次，但某个点数出现了 2 次（或者有两对），返回 "Pair"。
- 注意：题目不区分“一对”还是“两对”，只要存在一对就算 "Pair"。
④ High Card（高牌 / 散牌）
- 含义：5 张牌既不同花，也没有任何两张点数相同（即全是单张）。
- 本题逻辑：如果以上三种情况都不符合，直接返回 "High Card"。
- 通俗理解：这手牌啥组合都没有，只能比谁的点数最大（但在本题中不需要比大小，直接返回这个字符串即可）。
--------------------------------------------------------------------------------
3. 关于解题的特别提醒（隐含的判定陷阱）
很多人在写这道题时会犯一个顺序错误：
- 必须先把“Flush（同花）”判掉，再去统计点数次数。
- 为什么？ 因为如果 5 张牌花色全同，但点数各不相同（比如 2, 4, 6, 8, 10 全是红桃），统计点数时它会被判成 High Card。但在扑克规则里，Flush 比 High Card 大，所以必须先判断花色，否则答案就错了。
--------------------------------------------------------------------------------
4. 代码逻辑映照（极简版）
只要理清上面的名词，代码就是按照这个顺序写的：
class Solution {
    public String bestHand(int[] ranks, char[] suits) {
        // 1. 检查 Flush（同花）：5张牌花色是否全等
        boolean isFlush = true;
        for (int i = 1; i < suits.length; i++) {
            if (suits[i] != suits[0]) isFlush = false;
        }
        if (isFlush) return "Flush";

        // 2. 统计点数出现次数
        int[] count = new int[14]; // 点数范围 2~14
        for (int r : ranks) {
            count[r]++;
        }

        // 3. 检查 Three of a Kind（三条） 和 Pair（对子）
        boolean hasPair = false;
        for (int c : count) {
            if (c >= 3) return "Three of a Kind";
            if (c == 2) hasPair = true;
        }
        if (hasPair) return "Pair";

        // 4. 都不是则返回 High Card（高牌）
        return "High Card";
    }
}
--------------------------------------------------------------------------------
总结（术语速记表）
英文术语中文译名判定标准（本题）Ranks点数牌的数字/字母（如 10, J, A）Suits花色牌的花色（H, D, C, S）Flush同花5 张牌花色全都一样Three of a Kind三条某个点数出现了 3 次或以上Pair对子某个点数出现了恰好 2 次High Card高牌 / 散牌以上条件都不满足
这些术语在后续刷 LeetCode 其他扑克题（如 2260, 2465 等）时也会高频出现，理解后就能举一反三了。

Refer to
L2525.Categorize Box According to Criteria (Ref.L1275)
