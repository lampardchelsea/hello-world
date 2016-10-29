/**
 * A strobogrammatic number is a number that looks the same when rotated 180 degrees (looked at upside down).
 * Find all strobogrammatic numbers that are of length = n.
 * For example, Given n = 2, return ["11","69","88","96"].
 *
 * 中间插入法
 * 复杂度
 * 时间 O(N) 空间 O(1)
 * 思路
 * 找出所有的可能，必然是深度优先搜索。但是每轮搜索如何建立临时的字符串呢？因为数是“对称”的，
 * 我们插入一个字母就知道对应位置的另一个字母是什么，所以我们可以从中间插入来建立这个临时的字符串。
 * 这样每次从中间插入两个“对称”的字符，之前插入的就被挤到两边去了。这里有几个边界条件要考虑：
 * 如果是第一个字符，即临时字符串为空时进行插入时，不能插入'0'，因为没有0开头的数字
 * 如果n=1的话，第一个字符则可以是'0'
 * 如果只剩下一个带插入的字符，这时候不能插入'6'或'9'，因为他们不能和自己产生映射，翻转后就不是自己了
 * 这样，当深度优先搜索时遇到这些情况，则要相应的跳过
 * 注意
 * 为了实现从中间插入，我们用StringBuilder在length/2的位置insert就行了
*/
import java.util.ArrayList;
import java.util.List;


public class Solution {
	private static List<String> result;	
	private static char[] table = {'0', '1', '6', '8', '9'};
	
	public static List<String> isStrobogrammatic(int n) {
		result = new ArrayList<String>();
		build(n, "");
		return result;
	}
	
	/**
	 * The design logic of build() method must following DFS structure
	 * as void return, and recursively add tmp string into result list.
	 * In each cycle call build() method once, and return a new tmp
	 * string based on append of previous tmp string.
	 * 
	 * @param n Given number of digits need to satisfy
	 * @param tmp Temporary string constructed following strobogrammatic logic
	 */
	public static void build(int n, String tmp) {
		if(tmp.length() == n) {
			result.add(tmp);
			
			/**
			 * Return here is critical, which exactly interpret how DFS work,
			 * E.g when we find the first combination as 101 and add to array
			 * list result, we can see it as vertex has distance = 3(tmp = 
			 * "101", tmp.length() = 3) away from source (tmp = ""(tmp.length() = 0, 
			 * empty string)), also represent as build() method been called nested 
			 * as third time, now as condition n = distance = 3, we need to 
			 * "travel back" to vertex has distance = 2(tmp = "11", tmp.length() = 2), 
			 * also represent as return build() method when called nested as 
			 * second time, and we can add new char between tmp = "11".
			 * The critical "travel back" option is implement as "return" here.
			 */
			return;
		}
		
		boolean lastOnePosition = n - tmp.length() == 1;
		
		for(int i = 0; i < table.length; i++) {
			char c = table[i];
			/**
			 * Note: When any of these two case happens, we should avoid insert onto tmp string
			 *       the implementation of "avoid" is skip current for loop by "continue".
	         * 1. When start with empty tmp string, do not put 0 as a pair if required
	         *    n > 1, only if required n = 1, 0 can be put on initially
	         * 2. If only left one position, do not put 6 or 9 on this position,
	         *    as 6 and 9 only satisfy strobogrammatic as a pair
			 */
			if((n != 1 && tmp.length() == 0 && c == '0') || (lastOnePosition && (c == '6' || c == '9'))) {
				continue;
			}
			
			/**
			 *  Constructs a string builder initialized to the contents of the
             *  specified string. The initial capacity of the string builder is
             *  <code>16</code> plus the length of the string argument.
             *  @param   str   the initial contents of the buffer.
             *  @throws  NullPointerException if <code>str</code> is <code>null</code>
			 * 
			 *  public StringBuilder(String str) {
			 *     super(str.length() + 16);
			 *     append(str);
			 *  }
			 */
			StringBuilder sb = new StringBuilder(tmp);
			
			// Insert corresponding char of char c
			append(sb, c, lastOnePosition);
			
			// Call build() method recursively as DFS, but with updated 
			// parameter tmp = "" -> tmp = sb.toString()
			build(n, sb.toString());
		}
	}
	
	/**
	 * StringBuilder.insert(int offset, String str)
	 * Inserts the string into this character sequence. 
	 * The characters of the String argument are inserted, in order, into this sequence at the indicated offset, 
	 * moving up any characters originally above that position and increasing the length of this sequence by the 
	 * length of the argument. If str is null, then the four characters "null" are inserted into this sequence. 
	 * 
	 * The character at index k in the new character sequence is equal to: 
	 * the character at index k in the old character sequence, if k is less than offset 
	 * the character at index k-offset in the argument str, if k is not less than offset but is less than offset+str.length() 
	 * the character at index k-str.length() in the old character sequence, if k is not less than offset+str.length() 
	 * The offset argument must be greater than or equal to 0, and less than or equal to the length of this sequence.
	 * 
	 * Overrides: insert(...) in AbstractStringBuilder
	 * Parameters: offset the offset. str a string.
	 * Returns: a reference to this object.
	 * 
	 * @param sb
	 * @param c
	 * @param lastOnePosition
	 */
	public static void append(StringBuilder sb, char c, boolean lastOnePosition) {
		if(c == '6') {
			sb.insert(sb.length()/2, "69");
		} else if(c == '9') {
			sb.insert(sb.length()/2, "96");
		} else {
			// If only left one position to insert char, just insert 
			sb.insert(sb.length()/2, lastOnePosition ? c : "" + c + c);
		}
	}
	
	
	public static void main(String[] args) {
		int n = 3;
		result = isStrobogrammatic(n);
		for(String s : result) {
			StdOut.print(s);
			StdOut.println();
		}
	}
}
