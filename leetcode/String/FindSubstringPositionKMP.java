/**
 * Refer to
 * http://wiki.jikexueyuan.com/project/kmp-algorithm/define.html
*/
public class FindSubstringPositionKMP {
	public static int KmpSearch(String s, String p, int[] next) {
		int i = 0;
		int j = 0;
		int sLen = s.length();
		int pLen = p.length();
		
		while(i < sLen && j < pLen) {
			if(j == -1 || s.charAt(i) == p.charAt(j)) {
				i++;
				j++;
			} else {
				j = next[j];
			}
		}
		
		if(j == pLen) {
			return i - j;
		} else {
			return -1;
		}
	}
	
	/**
	 * Refer to
	 * http://wiki.jikexueyuan.com/project/kmp-algorithm/define.html
	 * How to find next[j + 1] ?
	 * 
	(1) p[k] == p[j]
		p: A B C D A B C E
		               j j+1
		j = 6
		j+1 = 7
			
		next[j] = k，相当于在不包含 pj 的模式串中有最大长度为 k 的相同前缀后缀
		对于ABCDAB （不包含pj = p6 = C）
		前缀 A AB ABC ABCD ABCDA
		后缀 B AB DAB CDAB BCDAB
		
		AB -> k = 2
		
		p[k] = p[2] = C
		p[j] = p[6] = C
		p[k] == p[j] -> next[j+1] = next[7] = k+1 = 2+1 =3
		
	(2) p[k] != p[j]
		例子1：
		p: A B C D A B D E
		               j j+1
		j = 6
		j+1 = 7
		
		p[k] = p[2] = C
		p[j] = p[6] = D
		p[k] != p[j]
		
		那为何递归前缀索引k = next[k]，就能找到长度更短的相同前缀后缀呢？这又归根到 next 
		数组的含义。我们拿前缀 p0 pk-1 pk 去跟后缀 pj-k pj-1 pj 匹配，如果 pk 跟 pj 失配，
		下一步就是用 p[next[k]] 去跟 pj 继续匹配，如果 p[ next[k] ]跟 pj 还是不匹配，
		则需要寻找长度更短的相同前缀后缀，即下一步用 p[ next[ next[k] ] ] 去跟 pj 匹配。
		
		尝试 k = next[k]
		p[k] = p[next[k]] = p[next[2]]
		对于AB (不包含pj = p2 = C)
		前缀A
		后缀B
		No equal -> k = 0
		   
		p[next[2]] = p[0] = A
		p[j] = p[6] = D 
		p[next[k]] != p[j] -> next[j+1] = next[7] = 0
		
		例子2：
		p: D A B C D A B D E
		                 j j+1
		j = 7
		j+1 = 8
		
		next[j] = k，相当于在不包含 pj 的模式串中有最大长度为 k 的相同前缀后缀
		对于DABCDAB
		前缀D DA DAB DABC DABCD DABCDA
		后缀B AB DAB CDAB BCDAB ABCDAB
		
		DAB -> k = 3
		
		p[k] = p[3] = C
		p[j] = p[7] = D
		p[k] != p[j]
		
		尝试 k = next[k]
		p[k] = p[next[k]] = p[next[3]]
		对于DAB (不包含pj = p3 = C)
		前缀D DA
		后缀B AB 
		No equal -> k = 0
		
		p[next[3]] = p[0] = D
		p[j] = p[7] = D
		p[next[k]] == p[j] -> next[j+1] = next[8] = 1
	 */
	public static void getNext(String p, int[] next) {
		int pLen = p.length();
		next[0] = -1;
		int k = -1;
		int j = 0;
		
		while(j < pLen - 1) {
			if(k == -1 || p.charAt(j) == p.charAt(k)) {
				++k;
				++j;
				next[j] = k;
			} else {
				k = next[k];
			}
		}
	}
	
	public static void main(String[] args) {
		String s = "BBC ABCDAB ABCDABCDABDE";
		String p = "ABCDABD";
		int[] next = new int[p.length()];
		getNext(p, next);
		int result = KmpSearch(s, p, next);
		System.out.println(result);
	}
}

