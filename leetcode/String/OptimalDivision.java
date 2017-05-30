/**
 * Refer to
 * https://leetcode.com/problems/optimal-division/#/description
 * Given a list of positive integers, the adjacent integers will perform the float division. 
 * For example, [2,3,4] -> 2 / 3 / 4.
 * However, you can add any number of parenthesis at any position to change the priority of operations. 
 * You should find out how to add parenthesis to get the maximum result, and return the corresponding 
 * expression in string format. Your expression should NOT contain redundant parenthesis.
   Example:
    Input: [1000,100,10,2]
    Output: "1000/(100/10/2)"
    Explanation:
    1000/(100/10/2) = 1000/((100/10)/2) = 200
    However, the bold parenthesis in "1000/((100/10)/2)" are redundant, 
    since they don't influence the operation priority. So you should return "1000/(100/10/2)". 

    Other cases:
    1000/(100/10)/2 = 50
    1000/(100/(10/2)) = 50
    1000/100/10/2 = 0.5
    1000/100/(10/2) = 2

    Note:
    The length of the input array is [1, 10].
    Elements in the given array will be in range [2, 1000].
    There is only one optimal division for each test case.
 * 
 * Solution
 * https://leetcode.com/articles/optimal-division/
 * Approach #3 Using some Math [Accepted]
 * Algorithm
 * Using some simple math we can find the easy solution of this problem. 
 * Consider the input in the form of [a,b,c,d], now we have to set priority of operations 
 * to maximize a/b/c/d. We know that to maximize fraction p/qp/qp/q, qqq(denominator) should 
 * be minimized. So, to maximize a/b/c/da/b/c/da/b/c/d we have to first minimize b/c/d. 
 * Now our objective turns to minimize the expression b/c/d.
 * There are two possible combinations of this expression, b/(c/d) and (b/c)/d.

    b/(c/d)        (b/c)/d = b/c/d
    (b*d)/c        b/(d*c)
    d/c            1/(d*c)

    Obviously, d/c>1/(d∗c)d/c > 1/(d*c)d/c>1/(d∗c) for d>1d>1d>1.

 * You can see that second combination will always be less than first one for numbers 
 * greater than 111. So, the answer will be a/(b/c/d). Similarly for expression like 
 * a/b/c/d/e/f... answer will be a/(b/c/d/e/f...).
*/









