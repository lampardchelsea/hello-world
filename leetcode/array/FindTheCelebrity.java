/**
 * Refer to
 * http://www.cnblogs.com/grandyang/p/5310649.html
 * Suppose you are at a party with n people (labeled from 0 to n - 1) and among them, there may exist one celebrity. 
   The definition of a celebrity is that all the other n - 1people know him/her but he/she does not know any of them.
   Now you want to find out who the celebrity is or verify that there is not one. The only thing you are allowed 
   to do is to ask questions like: "Hi, A. Do you know B?" to get information of whether A knows B. You need to 
   find out the celebrity (or verify there is not one) by asking as few questions as possible (in the asymptotic sense).
   You are given a helper function bool knows(a, b) which tells you whether A knows B. Implement a function 
   int findCelebrity(n), your function should minimize the number of calls to knows.
   Note: There will be exactly one celebrity if he/she is in the party. Return the celebrity's label if there is a 
   celebrity in the party. If there is no celebrity, return -1.
 *
 * Solution
 * http://www.cnblogs.com/grandyang/p/5310649.html
 * https://discuss.leetcode.com/topic/23511/straight-forward-c-solution-with-explaination
 * https://discuss.leetcode.com/topic/23534/java-solution-two-pass
 * https://discuss.leetcode.com/topic/23534/java-solution-two-pass/14
*/
public class Solution extends Relation {
    // Solution 1: Brute force 
    // Refer to
    // http://www.cnblogs.com/grandyang/p/5310649.html
    /**
     * 这道题让我们在一群人中寻找名人，所谓名人就是每个人都认识他，他却不认识任何人，限定了只有1个或0个名人，
     * 给定了一个API函数，输入a和b，用来判断a是否认识b，让我们尽可能少的调用这个函数，来找出人群中的名人。
     * 我最先想的方法是建立个一维数组用来标记每个人的名人候选状态，开始均初始化为true，表示每个人都是名人候
     * 选人，然后我们一个人一个人的验证其是否为名人，对于候选者i，我们遍历所有其他人j，如果i认识j，
     * 或者j不认识i，说明i不可能是名人，那么我们标记其为false，然后验证下一个候选者，反之如果i不认识j，
     * 或者j认识i，说明j不可能是名人，标记之。对于每个候选者i，如果遍历了一圈而其候选者状态仍为true，
     * 说明i就是名人，返回即可，如果遍历完所有人没有找到名人，返回-1
    */
    public int findCelebrity(int n) {
        boolean[] candidate = new boolean[n];
        for(int i = 0; i < n; i++) {
            candidate[i] = true;
        }		
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                if(candidate[i] && i != j) {
	            if(knows(i, j) || !knows(j, i)) {
	                candidate[i] = false;
		        break;
                    } else {
                        // Contains 2 major conditions
	                // (1) if(!knows(i, j)) -> If i don't know j, j must not be celebrity
	                // (2) if(knows(j, i)) -> If j know i, j must not be celebrity
	                candidate[j] = false;
                    }
                }
	    }
	    if(candidate[i]) {
	        return i;
	    }
	    return -1;
         }
     }
     
     // Solution 2:
     // Refer to
     // https://discuss.leetcode.com/topic/23534/java-solution-two-pass
     // http://www.cnblogs.com/grandyang/p/5310649.html
     /**
      * 下面这种方法是网上比较流行的一种方法，设定候选人res为0，原理是先遍历一遍，对于遍历到的人i，若候选人res认识i，
      * 则将候选人res设为i，完成一遍遍历后，我们来检测候选人res是否真正是名人，我们如果判断不是名人，则返回-1，
      * 如果并没有冲突，返回res
     */
     // https://discuss.leetcode.com/topic/23511/straight-forward-c-solution-with-explaination
     /**
      * First, if person A knows person B, then B could be the candidate of being a celebrity, 
      * A must not be a celebrity. We iterate all the n persons and we will have a candidate 
      * that everyone knows this candidate.
      * Second, we check two things after we get this candidate. 
      * 1. If this candidate knows other person in the group, if the candidate knows anyone 
      * in the group, then the candidate is not celebrity, return -1; 
      * 2. if everyone knows this candidate, if anyone does not know the candidate, return -1;
     */
     public int findCelebrity2(int n) {
         int candidate = 0;
	 for(int i = 1; i < n; i++) {
	     if(knows(candidate, i)) {
	         candidate = i;
	     }
	 }
	 for(int i = 0; i < n; i++) {
	     if(i != candidate && (knows(candidate, i) || !knows(i, candidate))) {
	          return -1;
	     }
	 }
	 return candidate;
     }
	
     // Solution 3: Optimization on Solution 2
     // https://discuss.leetcode.com/topic/23534/java-solution-two-pass/14
     /**
      * The first loop is to find the candidate as the author explains. In detail, suppose the 
      * candidate after the first for loop is person k, it means 0 to k-1 cannot be the celebrity, 
      * because they know a previous or current candidate. Also, since k knows no one between k+1 
      * and n-1, k+1 to n-1 can not be the celebrity either. Therefore, k is the only possible 
      * celebrity, if there exists one.
      * The remaining job is to check if k indeed does not know any other persons and all other 
      * persons know k.
      * To this point, I actually realize that we can further shrink the calling of knows method. 
      * For example, we don't need to check if k knows k+1 to n-1 in the second loop, because the 
      * first loop has already done that.
     */
     public int findCelebrity3(int n) {
         int candidate = 0;
	 for(int i = 1; i < n; i++) {
	     if(knows(candidate, i)) {
	         candidate = i;
	     }
	 }
	 for(int i = 0; i < n; i++) {
	     if(i < candidate && knows(candidate, i)) {
	         return -1;
	     }
             if(i > candidate && !knows(i, candidate)) {
	         return -1;
	     }
	 }
	 return candidate;
     }
}
