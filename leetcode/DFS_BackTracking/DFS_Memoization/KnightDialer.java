/**
 Refer to
 https://leetcode.com/problems/knight-dialer/
 A chess knight can move as indicated in the chess diagram below:
 you can jump from k to 8 positions including [a,b,c,d,e,f,g,h]
 
 *   *   *   *   *   *   *   *
 *   *   *   *   *   *   *   *
 *   *   b   *   c   *   *   *
 *   a   *   *   *   d   *   *
 *   *   *   k   *   *   *   *
 *   h   *   *   *   e   *   *
 *   *   g   *   f   *   *   *
 *   *   *   *   *   *   *   *

 1   2   3
 4   5   6
 7   8   9
 *   0   *
 
This time, we place our chess knight on any numbered key of a phone pad (indicated above), 
and the knight makes N-1 hops.  Each hop must be from one key to another numbered key.

Each time it lands on a key (including the initial placement of the knight), it presses 
the number of that key, pressing N digits total.

How many distinct numbers can you dial in this manner?

Since the answer may be large, output the answer modulo 10^9 + 7.

Example 1:
Input: 1
Output: 10

Example 2:
Input: 2
Output: 20

Example 3:
Input: 3
Output: 46

Note:
1 <= N <= 5000
*/

/**
 Analysis
 We can think of this problem as the total number of unique paths the knight can travel making n hops 
 because to dial distinct numbers, the path taken by the knight must be unique.

In this post I want to explain how I came up with a solution to this problem. This approach can be used 
to solve other similar problems such as Unique Paths, Minimum Path Sum etc.

Imagine an 8 x 8 chess board with Knight (k) sitting at some index (i, j). The board would look as follows,

*   *   *   *   *   *   *   *
*   *   *   *   *   *   *   *
*   *   b   *   c   *   *   *
*   a   *   *   *   d   *   *
*   *   *   k   *   *   *   *
*   h   *   *   *   e   *   *
*   *   g   *   f   *   *   *
*   *   *   *   *   *   *   *
[0] If k is at index (i, j), then in a single hop, k can move to 8 possible positions which are below.

a (i - 1, j - 2)
b (i - 2, j - 1)
c (i - 2, j + 1)
d (i - 1, j + 2)
e (i + 1, j + 2)
f  (i + 2, j + 1)
g (i + 2, j - 1)
h (i + 1, j - 2)
[1] Conversely, you can also say that in a single hop, there are 8 possible places (a,b,c,d,e,f,g,h) from 
which you can move to k.

Math behind the solution:

Consider a function paths(i, j, n) which calculates the total number of unique paths to reach index (i, j) 
for a given n, where n is the number of hops. From [0] or [1], we can recusively define paths(i, j, n) for 
all non-trivial (n > 1, that is, more than one hop) cases as follows,

paths(i, j, n) = paths(i - 1, j - 2, n - 1) + 
                 paths (i - 2, j - 1, n - 1) +
                 paths (i - 2, j + 1, n - 1) +
                 paths (i - 1, j + 2, n - 1) +
                 paths (i + 1, j + 2, n - 1) +
                 paths  (i + 2, j + 1, n - 1) +
                 paths (i + 2, j - 1, n - 1) +
                 paths (i + 1, j - 2, n - 1)
If we translate this to plain english, all we are saying is "the total number of unique paths to (i, j) for 
certain hops n is equal to the sum of total number of unique paths to each valid position from which (i, j) 
can be reached using n - 1 hops".

If you are confused why it is n - 1 hops, note that when we are at (i, j), we already made one hop and we 
have n - 1 hops more to take.

For the trivial case (n = 1, that is no hops), the problem states that this must be considered as one path. 
Therefore, paths(i, j, n) = 1, for n = 1.

A Sample Trace

If the above recursive equation or it's translation is not very enlightening, you can follow this sample 
trace to get a better understanding of the logic.

Our keypad is a 4 x 3 matrix which looks like below.

1   2   3
4   5   6
7   8   9
*   0   *
Note that in the code there is no need to use a matrix like this. This is just for explanation purpose.

We shall trace the recursion tree of paths(0, 0, 3) in this section, that is, all the possible unique paths 
from 1 (0, 0) in 3 hops. From 1 (0, 0), in a single hop, we have two possible places to jump to - 6 and 8.

Wait! Didn't I say that a knight can jump to 8 possible places in a single hop some where in this post?

..."in a single hop, k can move to 8 possible positions"...

Yes, a knight can jump to 8 possible places in a single hop and this is still true. However, 6 of the other 
hops will take you outside of the matrix. You will see later in the code how this is being handled as a part of base case.

A brief on notation:

I am representing each node in the recursion tree something like X (i, j, n). This means that we are at 
the call paths(0, 0, 3) and the knight is currently sitting on the number X with n - 1 hops remaining.

Below is the recursion tree for paths(0, 0, 3).

Note: The following tree diagram is not visually appealing with the old UI. I recommend to view this in 
the new UI for a proper visual representation.

                                                 1 (0,0,3)
			                   /		     \
				          /                    \
			  	  6 (1,2,2)                   8 (2,1,2)	
			   	/    |    \                   |        \
			      /	     |	   \		      |  	\
	              1 (0,0,1)  0 (3,1,1)  7 (2,0,1)      1 (0,0,1)  3 (0,2,1)
Since, 6 jumps are invalid, that leaves us with only two valid jumps e (i + 1, j + 2) and f (i + 2, j + 1) 
from 1 (0, 0). In other words, from 1 the knight can jump only to 8 and 6.

In fact, in this matrix, the maximum number of valid jumps you can make is 3 which is from 4, 6.

Explanation of this recursion tree is below,

From 1 (0, 0, 3), the knight can go to 6 (1, 2, 2) and 8 (2, 1, 2) in a single hop. As we go down the 
recursion tree, we pass the number of hops to make as 1 less than the current.

From 6 (1, 2, 2), it can go to 1 (0, 0, 1) , 0 (3, 1, 1) and 7 (2, 0, 1). We pass n as 1 as we are going down 
in the recursion. At this point, each of these calls return 1 (since n = 1 which is the trivial case).

Therefore, [2] 6 (1, 2, 2) = 1 + 1 + 1 = 3, which means that there are 3 unique paths from 6 when n = 2 and 
they are 61, 60, 67.

From 8 (2,1,2), it can go to 1 (0, 0, 1) and 3 (0, 2, 1). At this point, each of these calls return 1 (since n = 1).

Therefore, [3] 8 (2, 1, 2) = 1 + 1 = 2, which means that there are 2 unique paths from 8 when n = 2 and they are 81, 83.

Finally, 1 (0, 0, 3) = 6 (1, 2, 2) + 8 (2, 1, 2)

From [2], [3], we can write this as,

1 (0, 0, 3) = 3 + 2 = 5, which means that there are 5 unique paths from 1 when n = 3 and they are 161, 160, 167, 181, 183.

Naive Recursive Code


public static final int max = (int) Math.pow(10, 9) + 7;
	
public int knightDialer(int n) {
   long s = 0;
   //do n hops from every i, j index (the very requirement of the problem)
   for(int i = 0; i < 4; i++) {
      for(int j = 0; j < 3; j++) {
         s = (s + paths(i, j, n)) % max;
      }
   }
   return (int) s;
}

private long paths(int i, int j, int n) {
   // if the knight hops outside of the matrix or to * return 0 
   //as there are no unique paths from here
   if(i < 0 || j < 0 || i >= 4 || j >= 3 || (i == 3 && j != 1)) return 0;
   //trivial case
   if(n == 1) return 1;
   //non trivial case
   long s = paths(i - 1, j - 2, n - 1) % max + // jump to a
            paths(i - 2, j - 1, n - 1) % max + // jump to b
            paths(i - 2, j + 1, n - 1) % max + // jump to c
            paths(i - 1, j + 2, n - 1) % max + // jump to d
            paths(i + 1, j + 2, n - 1) % max + // jump to e
            paths(i + 2, j + 1, n - 1) % max + // jump to f
            paths(i + 2, j - 1, n - 1) % max + // jump to g
            paths(i + 1, j - 2, n - 1) % max; // jump to h
   return s;
}
If you run this code for n = 50 in your favorite programming language, you will realize that it takes at 
least an hour to get the answer.

This is because this problem not only has similar subproblems but each of those similar subproblems have 
overlapping subproblems. What does this mean? Let me explain with an example.

As seen in the above trace, a subproblem of 1 (0, 0, 3) is 8 (2, 1, 2).

A subproblem of 3 (0, 2, 3) is also 8 (2, 1, 2) because you can get from 3 to 8 in a single hop.

We have already computed the solution to 8 (2, 1, 2) while computing the solution to 1 (0, 0, 3) and there 
is no need to re-compute this solution if were to store the solution somewhere in memory. The above recursive 
solution re-computes the solutions to overlapping subproblems and therefore is highly inefficient (runs in 
the order of O(3 ^ n) I believe).

Top down Dynamic programming solution

We use dynamic programming and store the solution of each subproblem in M. M is a 3D array and each index 
of M corresponds to a solution of n. Each n is again stored as a 2D array for (i, j) values.

All this combined, M will store the solution of each paths(i, j, n) call.

Below is the code.

public static final int max = (int) Math.pow(10, 9) + 7;
	
public int knightDialer(int n) {
   // A 3D array to store the solutions to the subproblems
   long M[][][] = new long[n + 1][4][3];
   long s = 0;
   //do n hops from every i, j index (the very requirement of the problem)
   for(int i = 0; i < 4; i++) {
      for(int j = 0; j < 3; j++) {
         s = (s + paths(M, i, j, n)) % max;
      }
   }
   return (int) s;
}

private long paths(long[][][] M, int i, int j, int n) {
   // if the knight hops outside of the matrix or to * return 0 
   //as there are no unique paths from here
   if(i < 0 || j < 0 || i >= 4 || j >= 3 || (i == 3 && j != 1)) return 0;
   if(n == 1) return 1;
   //if the subproblem's solution is already computed, then return it
   if(M[n][i][j] > 0) return M[n][i][j];
   //else compute the subproblem's solution and save it in memory
   M[n][i][j] = paths(M, i - 1, j - 2, n - 1) % max + // jump to a
                paths(M, i - 2, j - 1, n - 1) % max + // jump to b
                paths(M, i - 2, j + 1, n - 1) % max + // jump to c
                paths(M, i - 1, j + 2, n - 1) % max + // jump to d
                paths(M, i + 1, j + 2, n - 1) % max + // jump to e
                paths(M, i + 2, j + 1, n - 1) % max + // jump to f
                paths(M, i + 2, j - 1, n - 1) % max + // jump to g
                paths(M, i + 1, j - 2, n - 1) % max; // jump to h
   return M[n][i][j];
}
Any questions or feedback is welcome!
*/



// Solution 1: Native solution TLE
// Refer to
// https://leetcode.com/problems/knight-dialer/discuss/190787/How-to-solve-this-problem-explained-for-noobs!!!
class Solution {
    int max = (int)Math.pow(10, 9) + 7;
    int[] dx = new int[]{1,2,2,1,-1,-2,-2,-1};
    int[] dy = new int[]{2,1,-1,-2,-2,-1,1,2};
    public int knightDialer(int N) {       
        long result = 0;
        for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 3; j++) {
                result = (result + helper(i, j, N)) % max;
            }
        }
        return (int)result;
    }
    
    private long helper(int i, int j, int N) {
        // If out of chess board or press on * will return
        if(i < 0 || i >= 4 || j < 0 || j >= 3 || (i == 3 && j != 1)) {
            return 0;
        }
        // Base case, when N is 1 means only press 1 digit only 1 choice
        if(N == 1) {
            return 1;
        }
        long result = 0;
        for(int k = 0; k < 8; k++) {
            result = (result + helper(i + dx[k], j + dy[k], N - 1)) % max;
        }
        return result;
    }
}

// Solution 2: Memoization
// Refer to
// https://leetcode.com/problems/knight-dialer/discuss/190787/How-to-solve-this-problem-explained-for-noobs!!!/256076
class Solution {
    int max = (int)Math.pow(10, 9) + 7;
    int[] dx = new int[]{1,2,2,1,-1,-2,-2,-1};
    int[] dy = new int[]{2,1,-1,-2,-2,-1,1,2};
    Map<String, Long> map = new HashMap<String, Long>();
    public int knightDialer(int N) {
        long result = 0;
        for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 3; j++) {
                result = (result + helper(i, j, N)) % max;
            }
        }
        return (int)result;
    }
    
    private long helper(int i, int j, int N) {
        // If out of chess board or press on * will return
        if(i < 0 || i >= 4 || j < 0 || j >= 3 || (i == 3 && j != 1)) {
            return 0;
        }
        // Base case, when N is 1 means only press 1 digit only 1 choice
        if(N == 1) {
            return 1;
        }
        long result = 0L;
        String str = i + "_" + j + "_" + N;
        if(map.containsKey(str)) {
            return map.get(str);
        }
        for(int k = 0; k < 8; k++) {
            result = (result + helper(i + dx[k], j + dy[k], N - 1)) % max;
        }
        map.put(i + "_" + j + "_" + N, result);
        return result;
    }
}
