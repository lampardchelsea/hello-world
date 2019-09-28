/**
 Explaination of why we can degrade 2D to 1D array and why we need loop backwards
 Refer to
 https://leetcode.com/problems/partition-equal-subset-sum/discuss/90592/01-knapsack-detailed-explanation
 https://leetcode.com/problems/partition-equal-subset-sum/discuss/90592/01-knapsack-detailed-explanation/241664
Yes, the magic is observation from the induction rule/recurrence relation!
For this problem, the induction rule:
If not picking nums[i - 1], then dp[i][j] = dp[i-1][j]
if picking nums[i - 1], then dp[i][j] = dp[i - 1][j - nums[i - 1]]
You can see that if you point them out in the matrix, it will be like:

			  j
	. . . . . . . . . . . . 
	. . . . . . . . . . . .  
	. . ? . . ? . . . . . .  ?(left): dp[i - 1][j - nums[i], ?(right): dp[i - 1][j]
i	. . . . . # . . . . . .  # dp[i][j]
	. . . . . . . . . . . . 
	. . . . . . . . . . . . 
	. . . . . . . . . . . . 
	. . . . . . . . . . . . 
	. . . . . . . . . . . . 
 
Optimize to O(2*n): you can see that dp[i][j] only depends on previous row, so you can 
optimize the space by only using 2 rows instead of the matrix. Let's say array1 and array2. 
Every time you finish updating array2, array1 have no value, you can copy array2 to array1 
as the previous row of the next new row.
Optimize to O(n): you can also see that, the column indices of dp[i - 1][j - nums[i]] and 
dp[i - 1][j] are <= j. The conclusion you can get is: the elements of previous row whose 
column index is > j(i.e. dp[i - 1][j + 1 : n - 1]) will not affect the update of dp[i][j] 
since we will not touch them:

			  j
	. . . . . . . . . . . . 
	. . . . . . . . . . . .  
	. . ? . . ? x x x x x x  you will not touch x for dp[i][j]
i	. . . . . # . . . . . .  # dp[i][j]
	. . . . . . . . . . . . 
	. . . . . . . . . . . . 
	. . . . . . . . . . . . 
	. . . . . . . . . . . . 
	. . . . . . . . . . . . 
 
Thus if you merge array1 and array2 to a single array array, if you update array backwards, 
all dependencies are not touched!

    (n represents new value, i.e. updated)
	. . ? . . ? n n n n n n n
              #  
However if you update forwards, dp[j - nums[i - 1]] is updated already, you cannot use it:

    (n represents new value, i.e. updated)
	n n n n n ? . . . . . .  where another ? goes? Oops, it is overriden, we lost it :(
              #  
Conclusion:
So the rule is that observe the positions of current element and its dependencies in the matrix. 
Mostly if current elements depends on the elements in previous row(most frequent case)/columns, 
you can optimize the space.
*/
