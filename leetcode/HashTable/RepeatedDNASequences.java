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
