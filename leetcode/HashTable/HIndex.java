import java.util.Arrays;

/**
 * Refer to
 * https://leetcode.com/problems/h-index/#/description
 * 
 * https://en.wikipedia.org/wiki/H-index
 * 
 * Solution
 * https://discuss.leetcode.com/topic/23307/my-o-n-time-solution-use-java
 * 
 * https://discuss.leetcode.com/topic/40765/java-bucket-sort-o-n-solution-with-detail-explanation
 *
 * https://segmentfault.com/a/1190000003794831
 * 复杂度
 * 时间 O(NlogN) 空间 O(1)
 * 思路
 * 先将数组排序，我们就可以知道对于某个引用数，有多少文献的引用数大于这个数。对于引用数citations[i]，大于该引用数
 * 文献的数量是citations.length - i，而当前的H-Index则是Math.min(citations[i], citations.length - i)，
 * 我们将这个当前的H指数和全局最大的H指数来比较，得到最大H指数。
 * 
 * 
 * http://www.cnblogs.com/grandyang/p/4781203.html
 * 这道题让我们求H指数，这个质数是用来衡量研究人员的学术水平的质数，定义为一个人的学术文章有n篇分别被引用了n次，
 * 那么H指数就是n。而且wiki上直接给出了算法，可以按照如下方法确定某人的H指数：1、将其发表的所有SCI论文按被引次数
 * 从高到低排序；2、从前往后查找排序后的列表，直到某篇论文的序号大于该论文被引次数。所得序号减一即为H指数。
 * 
 */
public class HIndex {
	// Solution 1:
	public int hIndex(int[] citations) {
        // 排序
		// Refer to
		// https://stackoverflow.com/questions/22571586/will-arrays-sort-increase-time-complexity-and-space-time-complexity
		// The Arrays.sort() will consume NlogN time complexity
        Arrays.sort(citations);
        int h = 0;
        for(int i = 0; i < citations.length; i++){
            // 得到当前的H指数
        	// Refer to
        	// https://en.wikipedia.org/wiki/H-index
        	// Formally, if f is the function that corresponds to the number of citations for each publication, 
        	// we compute the h index as follows. 
        	// First we order the values of f from the largest to the lowest value. Then, we look for the last 
        	// position in which f is greater than or equal to the position (we call h this position). 
        	// For example, if we have a researcher with 5 publications A, B, C, D, and E with 10, 8, 5, 4, 
        	// and 3 citations, respectively, the h index is equal to 4 because the 4th publication has 4 
        	// citations and the 5th has only 3. In contrast, if the same publications have 25, 8, 5, 3, 
        	// and 3, then the index is 3 because the fourth paper has only 3 citations.
            // f(A)=10, f(B)=8, f(C)=5, f(D)=4, f(E)=3　→ h-index=4
            // f(A)=25, f(B)=8, f(C)=5, f(D)=3, f(E)=3　→ h-index=3 
        	// Come to the way as how to find the current H index, because Arrays.sort will sort
        	// citations as national order, refer to above example, 
        	// Arrays.sort(f(A)=25, f(B)=8, f(C)=5, f(D)=3, f(E)=3) -> (f(E)=3, f(D)=3, f(C)=5, f(B)=8, f(A)=25)
        	// Compare 'citations[1] = f(D) = 3' and 'citations.length - i = 5 - 1 = 4',
        	// we can only take smaller value as 3
            int currH = Math.min(citations[i], citations.length - i);
            // And compare to all H index to find the largest one
            if(currH > h){
                h = currH;
            }
        }
        return h;
    }
}

