/**
 * Refer to
 * http://wiki.jikexueyuan.com/project/kmp-algorithm/violent-match.html
 * 
 * 假设现在我们面临这样一个问题：有一个文本串 S，和一个模式串 P，现在要查找 P 在 S 中的位置，怎么查找呢？
 * 如果用暴力匹配的思路，并假设现在文本串 S 匹配到 i 位置，模式串 P 匹配到 j 位置，则有：
 * 如果当前字符匹配成功（即 S[i] == P[j]），则 i++，j++，继续匹配下一个字符；
 * 如果失配（即 S[i]! = P[j]），令 i = i - (j - 1)，j = 0。相当于每次匹配失败时，i 回溯，j 被置为0。
 * 举个例子，如果给定文本串 S“BBC ABCDAB ABCDABCDABDE”，和模式串 P“ABCDABD”，现在要拿模式串 P 
 * 去跟文本串 S 匹配，整个过程如下所示：
 * 1.S[0] 为 B，P[0] 为 A，不匹配，执行第 ② 条指令：“如果失配（即 S[i]! = P[j]），令 i = i - (j - 1)，j = 0”，
 *   S[1] 跟 P[0] 匹配，相当于模式串要往右移动一位（i=1，j=0）
 * 2.S[1] 跟 P[0] 还是不匹配，继续执行第 ② 条指令：“如果失配（即 S[i]! = P[j]），令 i = i - (j - 1)，j = 0”，
 *   S[2] 跟 P[0] 匹配（i=2，j=0），从而模式串不断的向右移动一位（不断的执行“令 i = i - (j - 1)，j = 0”，i 
 *   从 2 变到 4，j 一直为 0）
 * 3.直到 S[4] 跟 P[0] 匹配成功（i=4，j=0），此时按照上面的暴力匹配算法的思路，转而执行第 ① 条指令：“如果当前字
 *   符匹配成功（即 S[i] == P[j]），则 i++，j++”，可得 S[i] 为 S[5]，P[j] 为 P[1]，即接下来 S[5] 跟 P[1] 匹配
 *   （i=5，j=1）
 * 4.S[5] 跟 P[1] 匹配成功，继续执行第 ① 条指令：“如果当前字符匹配成功（即 S[i] == P[j]），则 i++，j++”，得到
 *   S[6] 跟 P[2] 匹配（i=6，j=2），如此进行下去
 * 5.直到 S[10] 为空格字符，P[6] 为字符 D（i=10，j=6），因为不匹配，重新执行第 ② 条指令：“如果失配（即 S[i]! = P[j]），
 *   令 i = i - (j - 1)，j = 0”，相当于 S[5] 跟 P[0] 匹配（i=5，j=0）
 * 6.至此，我们可以看到，如果按照暴力匹配算法的思路，尽管之前文本串和模式串已经分别匹配到了 S[9]、P[5]，但因为 
 *   S[10] 跟 P[6] 不匹配，所以文本串回溯到 S[5]，模式串回溯到 P[0]，从而让 S[5] 跟 P[0] 匹配。
 * 
 * 而 S[5] 肯定跟 P[0] 失配。为什么呢？因为在之前第 4 步匹配中，我们已经得知 S[5] = P[1] = B，而 P[0] = A，
 * 即 P[1] != P[0]，故 S[5] 必定不等于 P[0]，所以回溯过去必然会导致失配。那有没有一种算法，让 i 不往回退，只需
 * 要移动 j 即可呢？
 * 答案是肯定的。这种算法就是本文的主旨 KMP 算法，它利用之前已经部分匹配这个有效信息，保持 i 不回溯，通过修改 j 的位置，
 * 让模式串尽量地移动到有效的位置。
 */
public class FindSubstringPositionBruteMatch {
	/**
	 * @param s text string
	 * @param p string model need to find in text string
	 * @return first occurrence start position of p in s 
	 */
	public static int findSubstringPositionBruteMatch(String s, String p) {
		int slength = s.length();
		int plength = p.length();

		int i = 0;
		int j = 0;
		
		while(i < slength && j < plength) {
			if(s.charAt(i) == p.charAt(j)) {
				i++;
				j++;
			} else {
				i = i - (j - 1);
				j = 0;
			}
		}
		
		if(j == plength) {
			return i - j;
		} else {
			return -1;
		}
	}
	
	public static void main(String[] args) {
		String s = "BBC ABCDAB ABCDABCDABDE";
		String p = "ABCDABD";
		int result = findSubstringPositionBruteMatch(s, p);
		System.out.println(result);
	}
}
