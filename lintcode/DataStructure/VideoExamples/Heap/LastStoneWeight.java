/**
  Refer to
  https://leetcode.com/contest/weekly-contest-137/problems/last-stone-weight/
  We have a collection of rocks, each rock has a positive integer weight.

Each turn, we choose the two heaviest rocks and smash them together.  
Suppose the stones have weights x and y with x <= y.  
The result of this smash is:

If x == y, both stones are totally destroyed;
If x != y, the stone of weight x is totally destroyed, and the stone of weight y has new weight y-x.
At the end, there is at most 1 stone left.  
Return the weight of this stone (or 0 if there are no stones left.)

Example 1:
Input: [2,7,4,1,8,1]
Output: 1
Explanation: 
We combine 7 and 8 to get 1 so the array converts to [2,4,1,1,1] then,
we combine 2 and 4 to get 2 so the array converts to [2,1,1,1] then,
we combine 2 and 1 to get 1 so the array converts to [1,1,1] then,
we combine 1 and 1 to get 0 so the array converts to [1] then that's the value of last stone.

Note:
1 <= stones.length <= 30
1 <= stones[i] <= 1000
*/
// Solution 1: PQ
// Refer to
// https://leetcode.com/problems/last-stone-weight/discuss/294993/Java-easy-code-using-PriorityQueue-w-brief-explanation-and-analysis.
/**
 Sort stones descendingly in PriorityQueue, then pop out pair by pair, compute the difference 
 between them and add back to PriorityQueue.
 Note: since we already know the first poped out is not smaller, it is not necessary to use Math.abs().
 Time: O(nlogn), space: O(n), where n = stones.length.
 Q & A:
 Q: If not adding zeroes in the queue when polling out two elements are equal, is the result same as the above code?
 A: Yes. 0s are always at the end of the PriorityQueue. No matter a positive deduct 0 or 0 deduct 0, the result 
    is same as NOT adding 0s into the PriorityQueue.
*/
class Solution {
    public int lastStoneWeight(int[] stones) {
        PriorityQueue<Integer> pq = new PriorityQueue<Integer>((a, b) -> b - a);
        for(int stone : stones) {
            pq.offer(stone);
        }
        while(pq.size() > 1) {
            pq.offer(pq.poll() - pq.poll());
        }
        return pq.peek();
    }
}


// Solution 2: DFS
class Solution {
    int result = 0;
    public int lastStoneWeight(int[] stones) {
        List<Integer> list = new ArrayList<Integer>();
        for(int i = 0; i < stones.length; i++) {
            list.add(stones[i]);
        }
        Collections.sort(list);
        helper(list);
        return result;
    }
    
    private void helper(List<Integer> list) {
        if(list.size() == 1) {
            result = list.get(0);
            return;
        }
        List<Integer> curList = new ArrayList<Integer>(list);
        int size = curList.size();
        if(size >= 2) {
            int a = curList.remove(size - 1);
            int b = curList.remove(size - 2);
            curList.add(a - b);
        }
        Collections.sort(curList);
        helper(curList);
    }
}
