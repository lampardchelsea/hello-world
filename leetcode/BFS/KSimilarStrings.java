/**
 Refer to
 https://leetcode.com/problems/k-similar-strings/
 Strings A and B are K-similar (for some non-negative integer K) if we can swap the positions of two letters 
 in A exactly K times so that the resulting string equals B.

Given two anagrams A and B, return the smallest K for which A and B are K-similar.

Example 1:
Input: A = "ab", B = "ba"
Output: 1

Example 2:
Input: A = "abc", B = "bca"
Output: 2

Example 3:
Input: A = "abac", B = "baca"
Output: 2

Example 4:
Input: A = "aabc", B = "abca"
Output: 2

Note:
1 <= A.length == B.length <= 20
A and B contain only lowercase letters from the set {'a', 'b', 'c', 'd', 'e', 'f'}
*/

// Solution 1: BFS + Optimization when to switch
// Refer to
// https://leetcode.com/problems/k-similar-strings/discuss/140099/JAVA-BFS-32-ms-cleanconciseexplanationwhatever
// https://leetcode.com/problems/k-similar-strings/discuss/140099/JAVA-BFS-32-ms-cleanconciseexplanationwhatever/176099
// https://www.cnblogs.com/grandyang/p/11343552.html
/**
 这道题说是当字符串A通过交换自身的字符位置K次能得到字符串B的话，就说字符串A和B的相似度为K。现在给了两个异构词A和B，问最小的相似度是多少。
 换一种说法就是，最少交换多少次可以将字符串A变为B，在另一道题目 [Snakes and Ladders](https://www.cnblogs.com/grandyang/p/11342652.html) 
 中提到了求最小值还有一大神器，广度优先搜索 BFS，最直接的应用就是在迷宫遍历的问题中，求从起点到终点的最少步数，也可以用在更 general 的场景，
 只要是存在确定的状态转移的方式，可能也可以使用。这道题就是更 general 的应用，起点状态就是A，目标状态是B，状态转移的方式就是进行字符交换，
 博主开始想的是对当前状态遍历所有的交换可能，产生的新状态若不在 visited 集合中就加入队列继续遍历，可是这种 Naive 的思路最终超时了 
 Time Limit Exceeded。为什么呢？因为对于每个状态都遍历所有都交换可能，则每一个状态都有平方级的复杂度，整个时间复杂度就太大了，
 虽然有很多重复的状态不会加入队列中，但就算是交换字符，HashSet 查重这些操作也够编译器喝一壶的了。所以必须要进行优化，而且是大幅度的优化。
 首先来想，为啥要限定A和B是异构词，这表明A和B中的字符的种类及其个数都相同，就是排列顺序不同，则A经过交换是一定能变为B的，而且交换的次数
 在区间 [0, n-1] 内，n是A的长度。再来想，是不是A中的每个字符都需要交换呢？答案是否定的，当A中某个位置i上的字符和B中对应位置的字符相等，
 即 A[i]=B[i] 时，就不需要交换，这样就可以用一个 while 循环，找到第一个不相等的i。交换的第一个字符确定了，就可以再往后遍历，去找第二个
 字符了，同理，第二个字符位置j，不能存在 A[j]=B[j]，比如 ab 和 bb，交换之后变为 ba 和 bb，还是不相等，最好是存在 A[j]=B[i]，比如 ab 
 和 ba，这样交换之后就变为 ba 和 ba，完美 match 了。找到了i和j之后，就可以进行交换了，然后判断新状态不在 visited 中的话，加入 visited 
 集合，同时加入队列 queue，之后还要交换i和j还原状态，每一层遍历结束后，结果 res 自增1即可
*/
class Solution {
    public int kSimilarity(String A, String B) {
        if(A.equals(B)) {
            return 0;
        }
        Set<String> visited = new HashSet<String>();
        Queue<String> q = new LinkedList<String>();
        q.offer(A);
        int step = 0;
        while(!q.isEmpty()) {
            int size = q.size();
            for(int i = 0; i < size; i++) {
                String cur = q.poll();
                if(cur.equals(B)) {
                    return step;
                }
                // Find the first different char need to switch
                int j = 0;
                while(j < B.length() && cur.charAt(j) == B.charAt(j)) {
                    j++;
                }
                // Find the second different char for switch
                for(int k = j + 1; k < B.length(); k++) {
                    // Current kth char is not the same as final kth char (since no need to change) 
                    // and current kth char is the target of final jth char then we swap
                    if(cur.charAt(k) != B.charAt(k) && cur.charAt(k) == B.charAt(j)) {
                        String next = swap(cur, k, j);
                        if(!visited.contains(next)) {
                            visited.add(next);
                            q.offer(next);
                        }
                    }
                }
            }
            step++;
        }
        return step;
    }
    
    private String swap(String s, int x, int y) {
        char[] chars = s.toCharArray();
        char c = chars[x];
        chars[x] = chars[y];
        chars[y] = c;
        return new String(chars);
    }
}
