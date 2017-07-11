import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
public class RepeatedDNASequences {
	// Solution 1: Use Map and Set
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
    
    // Solution 2: Use Map and Bit
    /**
     * Refer to
     * https://discuss.leetcode.com/topic/8894/clean-java-solution-hashmap-bits-manipulation/10?page=1
     * The key point is that it is not doing hash, it is doing the exact coding of a 10-letter 
     * sequence into a 4-bytes number, which is simply not possible for any generic string, 
     * but is possible for strings in this problem because they can have only 4 different characters.
     * In more detail:
     * If two objects have same hash it means that they may or may not be equal (though two equal objects 
     * are required to have same hash). So hashing is not enough here (like calling just "AACCCCCGGG".
     * hashCode() and storing it in the map), because there can be another (different) string with 
     * same hash and the program will output wrong result.
     * We also cannot store the 10-letter substrings themselves because they consume too much memory 
     * and the program will exceed memory limit.
     * So, instead of hashing or storing strings themselves the solution converts 10 letter string into 
     * 4-bytes integer (which is much smaller than string in terms of consumed memory). This would not 
     * be possible if the string could contain all 26 letters of English alphabet for example. 
     * But it is possible for our case, because there can be only 'A', 'C', 'G' and 'T' letters.
     * So we have only 4 possible letters, and we can use as little bits as possible to store each character 
     * of our 10-letter string. We really need only 2 bits (bits, not bytes) for this. 
     * Specifically the solution uses the following coding:

		0 = 00 (bits in binary number system) = 'A'
		
		1 = 01 (bits in binary number system) = 'C'
		
		2 = 10 (bits in binary number system) = 'G'
		
		3 = 11 (bits in binary number system) = 'T'

     * Note that since there 10 letters and each letter requires only 2 bits, we will need only 10 * 2= 20 
     * bits to code the string (which is less then size of integer in java (as well as in all other popular 
     * languages), which is 4 bytes = 32 bits).
     * For example, this is how "AACCTCCGGT" string will be coded:
     * A A C C T C C G G T
     * 00 00 01 01 11 01 01 10 10 11 = 00000101110101101011 (binary) = 23915 (decimal)
     */
    public List<String> findRepeatedDnaSequences2(String s) {
        List<String> result = new ArrayList<String>();
        Set<Integer> repeatOnce = new HashSet<Integer>();
        Set<Integer> repeatMore = new HashSet<Integer>();
        if(s == null || s.length() == 0) {
            return result;
        }
        int[] map = new int[26];
        map['A' - 'A'] = 0;
        map['C' - 'A'] = 1;
        map['G' - 'A'] = 2;
        map['T' - 'A'] = 3;
        for(int i = 0; i < s.length() - 9; i++) {
            int v = 0;
            // E.g "AACCTCCGGT", i = 0, j = 0, v = 0
            // Loop 1: v <<= 2 -> v = 00
            //         v |= map['A' - 'A'] -> 00
            // Loop 2: v <<= 2 -> v = 0000
            //         v |= map['A' - 'A'] -> 0000
            // Loop 3: v <<= 2 -> v = 000000
            //         v |= map['C' - 'A'] -> 000001
            // Loop 4: v <<= 2 -> v = 00000100
            //         v |= map['C' - 'A'] -> 00000101
            // ...... Finally, the value should be v = 23915
            for(int j = i; j < i + 10; j++) {
                v <<= 2;
                v |= map[s.charAt(j) - 'A'];
            }
            // Refer to
            // https://discuss.leetcode.com/topic/8894/clean-java-solution-hashmap-bits-manipulation/15?page=1
            // The first words.add(v) is to make sure it's duplicated 10-char sequence, doubleWords.add(v) 
            // is to make sure it's not duplicated more than 3 times.
            if(!repeatOnce.add(v) && repeatMore.add(v)) {
                result.add(s.substring(i, i + 10));
            }
        }
        return result;
    }
    
    // Solution 3: Improvement on holding fixed size window
    // Refer to
    // https://discuss.leetcode.com/topic/8894/clean-java-solution-hashmap-bits-manipulation/16?page=1
    public List<String> findRepeatedDnaSequences3(String s) {
    	List<String> result = new ArrayList<String>();
    	Set<Integer> repeatOnce = new HashSet<Integer>();
    	Set<Integer> repeatMore = new HashSet<Integer>();
    	int[] map = new int[26];
    	map['A' - 'A'] = 0;
    	map['C' - 'A'] = 1;
    	map['G' - 'A'] = 2;
    	map['T' - 'A'] = 3;
    	int v = 0;
    	// Be careful: the length NO need to -9 as loop through the whole string
    	for(int i = 0; i < s.length(); i++) {
    		//int v = 0; --> Move to outside
    		v <<= 2;
    		v |= map[s.charAt(i) - 'A'];
    		// E.g 
    		// Line 1: move v left 2 bit, for example 01 after moving 0100
    		// Line 2: Append 2 bits, 0100 | 11 => 0111
    		// Line 3: Have Oxfffff = 11...1 (20 entry of 1), aka Window of 10 * 2bits, 
    		//         the bits exceed 20 bits will be 0 (in each loop)
    		v &= 0xfffff;
    		if(i < 9) {
    			continue;
    		}
    		// Be careful, the substring change to substring(i - 9, i + 1) to suit
    		// if(i - 9) {continue;}
    		if(!repeatOnce.add(v) && repeatMore.add(v)) {
    			result.add(s.substring(i, i + 10));
    		}
    	}
    	return result;
    }
}
