/**
 * Refer to
 * https://segmentfault.com/a/1190000003906621
 * Given two strings S and T, determine if they are both one edit distance apart.
 * 
 * Solution
 * https://segmentfault.com/a/1190000003906621
 * 比较长度法
 * 复杂度
 * 时间 O(N) 空间 O(1)
 * 思路
 * 虽然我们可以用Edit Distance的解法，看distance是否为1，但Leetcode中会超时。这里我们可以利用只有
 * 一个不同的特点在O(N)时间内完成。如果两个字符串只有一个编辑距离，则只有两种情况：
 * (1)两个字符串一样长的时候，说明有一个替换操作，我们只要看对应位置是不是只有一个字符不一样就行了
 * (2)一个字符串比另一个长1，说明有个增加或删除操作，我们就找到第一个对应位置不一样的那个字符，如果较长
 * 字符串在那个字符之后的部分和较短字符串那个字符及之后的部分是一样的，则符合要求
 * 如果两个字符串长度差距大于1，肯定不对
 */
public class OneEditDistance {
	public boolean isOneEditDistance(String s, String t) {
		boolean case1 = false;
		boolean case2 = false;
		if(Math.abs(s.length() - t.length()) > 1) {
			return false;
		} else {
			if(s.length() == t.length()) {
				case1 = isOneDigitDifference(s, t);
			} else {
				case2 = isOneDigitDeletion(s, t);
			}
		}
		return case1 || case2;
	}
	
	public boolean isOneDigitDifference(String s, String t) {
		int i = 0;
		int count = 0;
		while(i < s.length()) {
			if(s.charAt(i) != t.charAt(i)) {
				count++;
				if(count > 1) {
					return false;
				}
			}
			i++;
		}
		if(count != 1) {
			return false;
		}
		return true;
	}
	
	public boolean isOneDigitDeletion(String s, String t) {
		int i = 0;
		int diff_idx = -1;
		int minLen = Math.min(s.length(), t.length());
		String longer = "";
		String shorter = "";
		if(s.length() == minLen) {
			longer = t;
			shorter = s;
		} else {
			longer = s;
			shorter = t;
		}
		//int maxLen = minLen + 1;
		while(i < minLen) {
			if(s.charAt(i) != t.charAt(i)) {
				diff_idx = i;
				break;
			}
			i++;
		}
		// If compare until last index of shorter string is
		// still same as longer string, then the last index
		// of longer string character is the different one,
		// if delete it will get same string as shorter one,
		// directly return true
		if(diff_idx == -1) {
			return true;
		} else {
			// If diff_idx changed, it means between position 
			// of [0, minLen - 1] in shorter and longer string
			// already encounter one character difference, need
			// to find if more differences or not, if not will
			// return true, if yes, return false
// Wrong way: To test above wrong case use: s = "abc", t = "dabce"
//			while(diff_idx < minLen) {
//				// Skip the different character on longer string by plus 1
//				if(shorter.charAt(diff_idx) != longer.charAt(diff_idx + 1)) {
//					return false;
//				}
//				diff_idx++;
//			}
			if(!shorter.substring(diff_idx).equals(longer.substring(diff_idx + 1))) {
				return false;
			}
			return true;
		}
	}
	
	public static void main(String[] args) {
		OneEditDistance oed = new OneEditDistance();
		String s = "abc";
		//String t = "abcd";
		String t = "adbce";
		//boolean result = oed.isOneDigitDeletion(s, t);
		boolean result = oed.isOneEditDistance(s, t);
		System.out.println(result);
	}
}
