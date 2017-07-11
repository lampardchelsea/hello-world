/**
 * Refer to
 * https://leetcode.com/problems/repeated-dna-sequences/#/description
 * All DNA is composed of a series of nucleotides abbreviated as A, C, G, and T, 
 * for example: "ACGAATTCCG". When studying DNA, it is sometimes useful to identify 
 * repeated sequences within the DNA.
 * Write a function to find all the 10-letter-long sequences (substrings) that occur 
 * more than once in a DNA molecule.
 * For example,
 * Given s = "AAAAACCCCCAAAAACCCCCCAAAAAGGGTTT",
 * Return:
 * ["AAAAACCCCC", "CCCCCAAAAA"].
 * 
 * Solution
 * https://discuss.leetcode.com/topic/8894/clean-java-solution-hashmap-bits-manipulation 
 *
 * https://discuss.leetcode.com/topic/27517/7-lines-simple-java-o-n
 *
 * https://segmentfault.com/a/1190000003922976
 * 哈希表法
 * 复杂度
 * 时间 O(N) 空间 O(N)
 * 思路
 * 最简单的做法，我们可以把位移一位后每个子串都存入哈希表中，如果哈希表中已经有这个子串，而且是第一次重复，
 * 则加入结果中。如果已经遇到多次，则不加入结果中。如果哈希表没有这个子串，则把这个子串加入哈希表中。
 *
 * 编码法
 * 复杂度
 * 时间 O(N) 空间 O(N)
 * 思路
 * 实际上我们的哈希表可以不用存整个子串，因为我们知道子串只有10位，且每一位只可能有4种不同的字母，那我们
 * 可以用4^10个数字来表示每种不同的序列，因为4^10=2^20<2^32所以我们可以用一个Integer来表示。具体的
 * 编码方法是用每两位bit表示一个字符。
*/
public class Solution {
    public List<String> findRepeatedDnaSequences(String s) {
        List<String> result = new ArrayList<String>();
        if(s == null || s.length() == 0) {
            return result;
        }
        Set<String> set = new HashSet<String>();
        Map<String, Integer> map = new HashMap<String, Integer>();
        for(int i = 10; i <= s.length(); i++) {
            String sub = s.substring(i - 10, i);
            if(map.containsKey(sub)) {
                if(map.get(sub) == 1) {
                    // If not using set to filter, in case of 
                    // 'AAAAAAAAAAA' -> 'AAAAAAAAAA' + 'AAAAAAAAAA'
                    // they are exactly same and duplicate
                    set.add(sub);
                } else {
                    map.put(sub, 2);
                }
            } else {
                map.put(sub, 1);
            }
        }
        if(set.size() > 0) {
            for(String a : set) {
                result.add(a);
            }
        }
        return result;
    }
}
