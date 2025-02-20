import java.util.Arrays;

/**
 * Refer to
 * https://leetcode.com/problems/h-index/#/description
 *  Given an array of citations (each citation is a non-negative integer) of a researcher, 
 *  write a function to compute the researcher's h-index.
 *  According to the definition of h-index on Wikipedia: "A scientist has index h if h of 
 *  his/her N papers have at least h citations each, and the other N − h papers have no 
 *  more than h citations each."
 *  For example, given citations = [3, 0, 6, 1, 5], which means the researcher has 5 papers 
 *  in total and each of them had received 3, 0, 6, 1, 5 citations respectively. Since the 
 *  researcher has 3 papers with at least 3 citations each and the remaining two with no 
 *  more than 3 citations each, his h-index is 3.
 *  Note: If there are several possible values for h, the maximum one is taken as the h-index. 
 *  
 * https://en.wikipedia.org/wiki/H-index
 * 
 * Solution
 * https://discuss.leetcode.com/topic/23307/my-o-n-time-solution-use-java
 * 
 * https://discuss.leetcode.com/topic/40765/java-bucket-sort-o-n-solution-with-detail-explanation
 * This type of problems always throw me off, but it just takes some getting used to. The idea behind 
 * it is some bucket sort mechanisms. First, you may ask why bucket sort. Well, the h-index is defined 
 * as the number of papers with reference greater than the number. So assume n is the total number of 
 * papers, if we have n+1 buckets, number from 0 to n, then for any paper with reference corresponding 
 * to the index of the bucket, we increment the count for that bucket. The only exception is that for 
 * any paper with larger number of reference than n, we put in the n-th bucket.
 * Then we iterate from the back to the front of the buckets, whenever the total count exceeds the 
 * index of the bucket, meaning that we have the index number of papers that have reference greater 
 * than or equal to the index. Which will be our h-index result. The reason to scan from the end of 
 * the array is that we are looking for the greatest h-index. For example, given array [3,0,6,5,1], 
 * we have 6 buckets to contain how many papers have the corresponding index. 
 * Hope to image and explanation help.
 * 
 * [3, 0, 6, 5, 1]
 *                                     <------------ count
 *    1       1                 1                 2 
 * bucket0  bucket1  bucket2  bucket3  bucket4  bucket5  (index)
 *
 * https://segmentfault.com/a/1190000003794831
 * 复杂度
 * 时间 O(NlogN) 空间 O(1)
 * 思路
 * 先将数组排序，我们就可以知道对于某个引用数，有多少文献的引用数大于这个数。对于引用数citations[i]，大于该引用数
 * 文献的数量是citations.length - i，而当前的H-Index则是Math.min(citations[i], citations.length - i)，
 * 我们将这个当前的H指数和全局最大的H指数来比较，得到最大H指数。
 * 
 * 数组映射法
 * 复杂度
 * 时间 O(N) 空间 O(N)
 * 思路
 * 也可以不对数组排序，我们额外使用一个大小为N+1的数组stats。stats[i]表示有多少文章被引用了i次，这里如果一篇文章
 * 引用大于N次，我们就将其当为N次，因为H指数不会超过文章的总数。为了构建这个数组，我们需要先将整个文献引用数组遍历一遍，
 * 对相应的格子加一。统计完后，我们从N向1开始遍历这个统计数组。如果遍历到某一个引用次数时，大于或等于该引用次数的文章
 * 数量，大于引用次数本身时，我们可以认为这是H指数。之所以不用再向下找，因为我们要取最大的H指数。那如何求大于或等于某个
 * 引用次数的文章数量呢？我们可以用一个变量，从高引用次的文章数累加下来。因为我们知道，如果有x篇文章的引用大于等于3次，
 * 那引用大于等于2次的文章数量一定是x加上引用次数等于2次的文章数量。
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
	
	// Solution 2:
	public int hIndex2(int[] citations) {
        int n = citations.length;
        int[] buckets = new int[n+1];
        // 统计各个引用次数对应多少篇文章
        for(int c : citations) {
            if(c >= n) {
                buckets[n]++;
            } else {
                // c = citations[i] -> buckets[citations[i]]
                buckets[c]++;
            }
        }
        int count = 0;
        // 找出最大的H指数
        for(int i = n; i >= 0; i--) {
        	// 引用大于等于i次的文章数量，等于引用大于等于i+1次的文章数量，加上引用等于i次的文章数量 
            count += buckets[i];
            // 如果引用大于等于i次的文章数量，大于引用次数i，说明是H指数
            if(count >= i) {
                return i;
            }
        }
        return 0;
	}
	
}
