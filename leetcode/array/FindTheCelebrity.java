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
 *  
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
     public int findCelebrity(int n) {
         
     }
}
