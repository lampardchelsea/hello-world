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
 * https://stackoverflow.com/questions/33879434/java-arraylist-initialization
 * https://web.stanford.edu/class/archive/cs/cs108/cs108.1082/106a-java-handouts/HO49ArrayList.pdf
*/
