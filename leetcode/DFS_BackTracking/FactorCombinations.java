/**
 * Refer to
 * http://www.cnblogs.com/grandyang/p/5332722.html
 * Numbers can be regarded as product of its factors. For example,

    8 = 2 x 2 x 2;
      = 2 x 4.
    Write a function that takes an integer n and return all possible combinations of its factors.

    Note: 

    Each combination's factors must be sorted ascending, for example: The factors of 2 and 6 is [2, 6], not [6, 2].
    You may assume that n is always positive.
    Factors should be greater than 1 and less than n.


    Examples: 
    input: 1
    output: 

    []
    input: 37
    output: 

    []
    input: 12
    output:

    [
      [2, 6],
      [2, 2, 3],
      [3, 4]
    ]
    input: 32
    output:

    [
      [2, 16],
      [2, 2, 8],
      [2, 2, 2, 4],
      [2, 2, 2, 2, 2],
      [2, 4, 4],
      [4, 8]
    ]
 *
 * Solution
 * https://discuss.leetcode.com/topic/21082/my-recursive-dfs-java-solution?page=1
 * http://www.cnblogs.com/grandyang/p/5332722.html
 * http://www.cnblogs.com/airwindow/p/4822572.html
 * https://stackoverflow.com/questions/33879434/java-arraylist-initialization
 * https://web.stanford.edu/class/archive/cs/cs108/cs108.1082/106a-java-handouts/HO49ArrayList.pdf
 * https://github.com/lampardchelsea/hello-world/blob/master/leetcode/DFS_BackTracking/Document/HO49ArrayList.pdf
*/

public class FactorCombinations {
public List<List<Integer>> getFactors(int n) {
		List<List<Integer>> result = new ArrayList<List<Integer>>();
		// As requirement, the Factors should be greater than 1 and less than n.
		// So, initial range start from 2 to n.
//		helper(result, new ArrayList<Integer>(), 2, n);
		helper2(result, new ArrayList<Integer>(), 2, n, (int)Math.sqrt(n));
		return result;
	}
	
	// Solution 1:
	/**
	 * Refer to
	 * 
	 * 这道题给了我们一个正整数n，让我们写出所有的因子相乘的形式，而且规定了因子从小到大的顺序排列，
	 * 那么对于这种需要列出所有的情况的题目，通常都是用回溯法来求解的，由于题目中说明了1和n本身
	 * 不能算其因子，那么我们可以从2开始遍历到n，如果当前的数i可以被n整除，说明i是n的一个因子，
	 * 我们将其存入一位数组out中，然后递归调用n/i，此时不从2开始遍历，而是从i遍历到n/i，停止的
	 * 条件是当n等于1时，如果此时out中有因子，我们将这个组合存入结果res中
	 * 
	 * 
	 * 
	 * Refer to
	 * 
	 * This problem is not hard, could be easily solved through DFS method. However, I came up with a 
	 * very complicated method initially.
		Initial Idea: each factor must be consisted through multi prime numbers, thus we could dissect 
		the "n" into a collection of prime numbers. 
		Step 1: compute the collection of prime numbers for n. (which must be unique).
		Step 2: use the elements in the collection to generate various combinations.
		
		The above idea is right, but it is too complex. According to problem, as long as the factor is 
		not 1, we could include it. Thus, we not we do this?
		Considering the process of dissecting a number:
		(((2) * 3 ) * 5 )* 6 = 180
		
		Suppose our target is 180, we can first chop out factor 2. then our target becomes 90.
		Then we can continue the process, until our target reachs 1. (which means we fully factorize it). 
		Since this problem asks us to compute all such combinations, we should also try to chop out 
		factor "((2) * 3 )" "(((2) * 3 ) * 5 )". This process could be elegantly achieved through: 
		<At present, we are in "helper(ret, item, n, i)">
		for (int i = start; i <= n; i++) {
		    if (n % i == 0) {
		        ...
		        helper(ret, item, n/i, i);
		        ...
		    }
		}
		So elegantly, right?
		a. for (int i = start; i <= n; i++), searches through all possible numbers through start to n.
		Note: you may ask since "1" is not allowed in the combination, why we allow i <= n. Even n*1 is 
		allowed, but in the recursive process, it no longer the n as the initial one. 2(current n) of 
		4(inital n), apparently we should allow i to be n, otherwise, we would never reach the base case.
		-------------------------------------------------------
		if (n == 1) {
		    if (item.size() > 1) {
		        ret.add(new ArrayList<Integer> (item));
		    }
		    return;
		}
		-------------------------------------------------------
		Note the case of "n * 1" would never include in to the ret set, since we require "item.size()" 
		must larger than 1. 
		So elegant and smart? Right!!!! 
		Also take advantage of item's size!!!! Great Programming skill!
		
		
		b. (n % i == 0), guarantees the current i is a factor. 
		
		c. helper(ret, item, n/i, i), makes us to continue the search with updated target "n/i".
		Note: since the same factor could appears repeatedlly, we should start from "i" rather than "i+1".
	 */
    private void helper(List<List<Integer>> result, List<Integer> tmp, int start, int end) {
		// Base condition: end drop to 1 and current list has more
        // than 1 item
		// the tricky part is if current list has less than 1 item
		// E.g n = 1 or n = 37 -> size = 0
		// and if n = 12 and set tmp.size() > 0 -> {12} will add into result
		//if(end == 1 && tmp.size() > 0) {
		if(end == 1 && tmp.size() > 1) {
			// You CANNOT directly add original passed in 'tmp' ArrayList
			// in backtracking strategy
			/**
			 * Refer to
			 * Java ArrayList initialization
			 * https://stackoverflow.com/questions/33879434/java-arraylist-initialization
			 * So your question concerns these two alternatives:

				// works
				res.add(new ArrayList<Integer>(item));
				
				// won't work, results in empty lists
				res.add(item);
				The purpose of new ArrayList<Integer>(item) is to create a new list with the same 
				content as the original, effectively cloning the original.
				
				If you don't clone the original, it will stay empty. The first time dfs is called, 
				item is empty, and then look at this loop:
				
				for (int i = start; i <= n; i++) {
				    item.add(i);
				    dfs(n, k, i + 1, item, res);
				    item.remove(item.size() - 1);
				}
				Every element added to item will be later removed. This is why you end up with empty 
				lists without the cloning step. Without cloning, not only you get a list of empty 
				lists, all of the empty lists are actually the same original ArrayList that you 
				created in combine, before the first call to dfs.
			 * 
			 */
			//result.add(tmp);
			result.add(new ArrayList<Integer>(tmp));
			return;
		}
		
		// Backtracking
		for(int i = start; i <= end; i++) {
			if(end % i == 0) {
				tmp.add(i);
				helper(result, tmp, i, end / i);
				tmp.remove(tmp.size() - 1);
			}
		}
	}
    
    // Solution 2:
	/**
	 * Refer to
	 * http://www.cnblogs.com/grandyang/p/5332722.html
	 * 下面这种方法用了个小trick，我们仔细观察题目中给的两个例子的结果，可以发现每个组合的第一个数字都没有超过n的平方根，
	 * 这个也很好理解，由于要求序列是从小到大排列的，那么如果第一个数字大于了n的平方根，而且n本身又不算因子，那么后面那个
	 * 因子也必然要与n的平方根，这样乘起来就必然会超过n，所以不会出现这种情况。那么我们刚开始在2到n的平方根之间进行遍历
	 * 
	 * Refer to
	 * https://discuss.leetcode.com/topic/21082/my-recursive-dfs-java-solution/5?page=1
	 * https://discuss.leetcode.com/topic/21082/my-recursive-dfs-java-solution/8?page=1
	 * Actually, factors of an integer n (except for 1 and n) are always between 1 and sqrt(n), so you 
	 * do not have to check those numbers between sqrt(n) and n. However, in your method, we need to 
	 * check n, so I added a check, when i is greater than sqrt(n), i will jump directly to n. 
	 * This little change improved a lot
	 */
	private void helper2(List<List<Integer>> result, List<Integer> tmp, int start, int end, int upper) {
		if(end == 1 && tmp.size() > 1) {
			result.add(new ArrayList<Integer>(tmp));
			return;
		}
		for(int i = start; i <= end; i++) {
		        // Special Case: Don't miss the possibility in internal dfs level,
			// then 'start' may larger than 'upper', need to set i directly to 'end'
			// e.g n = 12
			// dfs level 1 -> start = 2, end = 12, upper = 3
			//                i = 2 -> tmp.add(2)
			//                         dfs level 2 -> start = 2, end = 12/2 = 6, upper = 2
			//                                        i = 2 -> tmp.add(2)
			//                                                 dfs level 3 -> start = 2, end = 6/2 = 3, upper = 1
			//                                                                i = 2 > upper = 1 --> i = 3
			//                                                                on dfs level 3 need to set i as 'end'
			if(i > upper) {
				i = end;
			}
			if(end % i == 0) {
				tmp.add(i);
				helper2(result, tmp, i, end / i, (int)Math.sqrt(end / i));
				tmp.remove(tmp.size() - 1);
			}
		}
	}
    
    public static void main(String[] args) {
		FactorCombinations f = new FactorCombinations();
		int n = 12;
		List<List<Integer>> result = f.getFactors(n);
		for(List<Integer> a : result) {
			for(Integer b : a) {
				System.out.print(b + " ");
			}
			System.out.println("--------");
		}
	}
    
    

}



// New try
// Original Solution 1: DFS backtracking
// Refer to
// http://www.cnblogs.com/airwindow/p/4822572.html
public class Solution {
    /**
     * @param n: a integer
     * @return: return a 2D array
     */
    public List<List<Integer>> getFactors(int n) {
        List<List<Integer>> ret = new ArrayList<List<Integer>> ();
        helper(ret, new ArrayList<Integer> (), n, 2);
        return ret;
    }
    
    private void helper(List<List<Integer>> ret, List<Integer> item, int n, int start) {
        if (n == 1) {
            if (item.size() > 1) { //the original input is not counted in
                ret.add(new ArrayList<Integer> (item));
            }
            return;
        }
        for (int i = start; i <= n; i++) {
            if (n % i == 0) {
                item.add(i);
                helper(ret, item, n/i, i);
                item.remove(item.size()-1);
            }
        }
    }
}

// Solution 2: It can be promote to below style, beats 84% on lintcode
// Refer to
// https://segmentfault.com/a/1190000005801106
public class Solution {
    /**
     * @param n: a integer
     * @return: return a 2D array
     */
    public List<List<Integer>> getFactors(int n) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> list = new ArrayList<>();
        helper(n, 2, list, result);
        return result;
    }
    
    public void helper(int n, int start, List<Integer> list, List<List<Integer>> result) {
        if (n == 1) {
            if (list.size() > 1) {  //the original input is not counted in
                result.add(new ArrayList<Integer>(list));
            }
            return;
        }
        for (int i = start; i <= Math.sqrt(n); i++) { //这里只要到根号n就好了 -> promotion against solution 1
            if (n % i == 0) {
                list.add(i);
                helper(n / i, i, list, result);
                list.remove(list.size() - 1);
            }
        }
        list.add(n); //把n加进去 -> since changed the range in previous for loop, not include n naturally, need to add manually
        helper(1, n, list, result);
        list.remove(list.size() - 1);
    }
}

