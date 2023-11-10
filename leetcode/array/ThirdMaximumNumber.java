import java.util.HashSet;
import java.util.Set;

/**
 * Refer to
 * https://leetcode.com/problems/third-maximum-number/#/description
 * Given a non-empty array of integers, return the third maximum number in this array. 
 * If it does not exist, return the maximum number. The time complexity must be in O(n).
	
	Example 1:
	Input: [3, 2, 1]
	
	Output: 1
	
	Explanation: The third maximum is 1.
	Example 2:
	Input: [1, 2]
	
	Output: 2
	
	Explanation: The third maximum does not exist, so the maximum (2) is returned instead.
	Example 3:
	Input: [2, 2, 3, 1]
	
	Output: 1
	
  * Explanation: Note that the third maximum here means the third maximum distinct number.
  * Both numbers with value 2 are both considered as second maximum.
 * 
 */
public class ThirdMaximumNumber {
	public int thirdMax(int[] nums) {
        Set<Integer> set = new HashSet<Integer>();
        for(int i = 0; i < nums.length; i++) {
            set.add(nums[i]);
        }
        MaxPQ pq = new MaxPQ(set.size());
        for(Integer a : set) {
            pq.insert(a);
        }
        int result = 0;
        int size = pq.size();
        if(size < 3) {
            result = pq.delMax();
        } else {
            int k = 2;
            while(k-- > 0) {
                pq.delMax();
            }
            result = pq.delMax();
        }
        return result;
    }
    
    private class MaxPQ {
        int[] pq;
        int n;
        public MaxPQ(int initialCapacity) {
            pq = new int[initialCapacity + 1];
            n = 0;
        }
        
        public void insert(int x) {
            pq[++n] = x;
            swim(n);
        }
        
        public int delMax() {
            int max = pq[1];
            exch(1, n--);
            sink(1);
            return max;
        }
        
        public void swim(int k) {
            while(k > 1 && less(k/2, k)) {
                exch(k/2, k);
                k = k/2;
            }
        }
        
        public void sink(int k) {
            while(k <= n/2) {
                int j = 2 * k;
                if(j < n && less(j, j + 1)) {
                    j++;
                }
                if(!less(k, j)) {
                    break;
                }
                exch(k, j);
                k = j;
            }
        }
        
//        public boolean isEmpty() {
//            return n == 0;
//        }
        
        public int size() {
            return n;
        }
        
        /**
         * Refer to
         * http://stackoverflow.com/questions/12535095/java-integers-min-value-negative-then-compare
         * The change on compare format is really tricky, the exception can be test by
         * {-2147483648, 1, 1}, the first number -2147483648 actually the Integer.MIN_VALUE,
         * if we use pq[v] - pq[w] = -2147483648 - 1, the result is 2147483647, which is
         * Integer.MAX_VALUE, not satisfy < 0, which we expected. We need to change compare
         * format to pq[v] < pq[w] without 'minus' operation
         */
        public boolean less(int v, int w) {
//            return pq[v] - pq[w] < 0;
        	return pq[v] < pq[w];
        }
        
        public void exch(int v, int w) {
            int temp = pq[v];
            pq[v] = pq[w];
            pq[w] = temp;
        }
    }
   
    public static void main(String[] args) {
    	int[] nums = {-2147483648, 1, 1};
    	ThirdMaximumNumber t = new ThirdMaximumNumber();
    	int result = t.thirdMax(nums);
    	System.out.println(result);
    }
}


// Refer to
// https://discuss.leetcode.com/topic/63715/java-neat-and-easy-understand-solution-o-n-time-o-1-space
public class Solution {
    public int thirdMax(int[] nums) {
        Integer max1 = null;
        Integer max2 = null;
        Integer max3 = null;
        for(Integer n : nums) {
            if(n.equals(max1) || n.equals(max2) || n.equals(max3)) {
                continue;
            }
            if(max1 == null || n > max1) {
                max3 = max2;
                max2 = max1;
                max1 = n;
            } else if(max2 == null || n > max2) {
                max3 = max2;
                max2 = n;
            } else if(max3 == null || n > max3) {
                max3 = n;
            }
        }
        return max3 == null ? max1 : max3;
    }
}



























































































https://leetcode.com/problems/third-maximum-number/description/

Given an integer array nums, return the third distinct maximum number in this array. If the third maximum does not exist, return the maximum number.

Example 1:
```
Input: nums = [3,2,1]
Output: 1
Explanation:
The first distinct maximum is 3.
The second distinct maximum is 2.
The third distinct maximum is 1.
```

Example 2:
```
Input: nums = [1,2]
Output: 2
Explanation:
The first distinct maximum is 2.
The second distinct maximum is 1.
The third distinct maximum does not exist, so the maximum (2) is returned instead.
```

Example 3:
```
Input: nums = [2,2,3,1]
Output: 1
Explanation:
The first distinct maximum is 3.
The second distinct maximum is 2 (both 2's are counted together since they have the same value).
The third distinct maximum is 1.
```

Constraints:
- 1 <= nums.length <= 104
- -231 <= nums[i] <= 231 - 1
 
Follow up: Can you find anO(n)solution?
---
Attempt 1: 2023-11-09

Solution 1: Priority Queue (60 min)
Note: To find Kth largest element always use MinPQ, to find Kth smallest element always use MaxPQ, refer L378/P14.2/P15.2.Kth Smallest Element in a Sorted Matrix (use MaxPQ with size limit) & L703/P14.7.Kth Largest Element in a Stream (use MinPQ with size limit)

Style 1: Set + PriorityQueue (capacity limit as 3, no more number will add on element, only update one of these 3 numbers when condition match) +  No need remove number from set
```
class Solution {
    public int thirdMax(int[] nums) {
        PriorityQueue<Integer> minPQ = new PriorityQueue<>();
        Set<Integer> set = new HashSet<>();
        int max = Integer.MIN_VALUE;
        for(int num : nums) {
            max = Math.max(max, num);
            if(!set.contains(num)) {
                if(minPQ.size() < 3) {
                    minPQ.offer(num);
                    set.add(num);
                } else if(minPQ.peek() < num) {
                    // Any number obsolete from minPQ even still in set
                    // will have no chance to add to minPQ again, so no
                    // need to remove number from set
                    //set.remove(minPQ.poll());
                    minPQ.poll();
                    minPQ.offer(num);
                    set.add(num);
                }
            }
        }
        return minPQ.size() == 3 ? minPQ.peek() : max;
    }
}

Time Complexity: O(N), not O(N*logN) because minPQ size not exceed 3, log3 can be treated as constant
Space Complexity: O(N)
```

Style 2: Only PriorityQueue (capacity limit as 3, no more number will add on element, only update one of these 3 numbers when condition match)
```
class Solution {
    public int thirdMax(int[] nums) {
        PriorityQueue<Integer> minPQ = new PriorityQueue<>();
        int max = Integer.MIN_VALUE;
        for(int num : nums) {
            max = Math.max(max, num);
            if(!minPQ.contains(num)) {
                if(minPQ.size() < 3) {
                    minPQ.offer(num);
                } else if(minPQ.peek() < num) {
                    // Any number obsolete from minPQ even still in set
                    // will have no chance to add to minPQ again, so no
                    // need to remove number from set
                    //set.remove(minPQ.poll());
                    minPQ.poll();
                    minPQ.offer(num);
                }
            }
        }
        return minPQ.size() == 3 ? minPQ.peek() : max;
    }
}

Time Complexity: O(N) ~ O(N*3), not O(N*logN) because minPQ size not exceed 3, log3 can be treated as constant, but multiply 3 because of PriorityQueue contains() method cost O(N) and N = 3 here, but O(3) is also a constant
Space Complexity: O(N)
```

Style 3: Set + PriorityQueue (capacity NOT limit as 3, 4th number will add on queue, always need to update one of these 3 numbers by removing operation, which means Style 3 significantly slower than Style 1) +  No need remove number from set
```
class Solution {
    public int thirdMax(int[] nums) {
        PriorityQueue<Integer> minPQ = new PriorityQueue<>();
        Set<Integer> set = new HashSet<>();
        int max = Integer.MIN_VALUE;
        for(int num : nums) {
            max = Math.max(max, num);
            if(!set.contains(num)) {
                minPQ.offer(num);
                set.add(num);                
                if(minPQ.size() > 3) {
                    // No need to remove from set since set only
                    // used to filter unique key which should add
                    // onto minPQ, even it store previous number
                    // and not removed won't impact how minPQ store
                    // --------------------------------------------
                    // Example 1: nums = {1,2,5,3,1}
                    // minPQ = {1,2,5}, set = {1,2,5}, 
                    // when 3 coming, minPQ = {1,2,3,5} -> then move 
                    // out 1 because minPQ.size() > 3, minPQ = {2,3,5}
                    // two choices for set: not move or move 1 out
                    // (1) set = {1,2,3,5} if not move out 1 
                    // (2) set = {2,3,5} if also move out 1
                    // Then if another 1 coming, minPQ will change
                    // to {1,2,3,5}, now for set will be
                    // (1) set = {1,2,3,5} no change because still 
                    // have previous 1 and not add new coming 1
                    // (2) set = {1,2,3,5} add new coming 1 because
                    // we move previous 1 out
                    // Then because of minPQ.size() > 3, we will move new
                    // coming 1 out of minPQ again, and set will repeat
                    // previous two choices as either remove 1 or not
                    // We can see the obsolete {1} from set will have no
                    // chance to exist on minPQ at any case, even if any new
                    // coming 1 won't add onto minPQ anymore as 2 is
                    // the threshold as existing third maximum number on minPQ,
                    // no matter {1} on or off the set, it obsolete from
                    // minPQ forever
                    // --------------------------------------------
                    // Example 2: nums = {1,2,5,3,3}
                    // when first 3 coming, minPQ = {2,3,5} -> move out 1, now
                    // (1) set = {1,2,3,5} if not move out 1 
                    // (2) set = {2,3,5} if also move out 1
                    // Then if another 3 coming, minPQ will not change by
                    // not adding 3 because set already contains 3 in both
                    // (1) and (2) scenarios
                    // We can see the obsolete {1} from set will have no
                    // chance to exist on minPQ at any case, even if any new
                    // coming 1 won't add onto minPQ anymore as 2 is
                    // the threshold as existing third maximum number on minPQ,
                    // no matter {1} on or off the set, it obsolete from
                    // minPQ forever
                    // --------------------------------------------
                    // Example 3: nums = {1,2,5,3,4}
                    // when 3 coming, minPQ = {1,2,3,5} -> then move 
                    // out 1 because minPQ.size() > 3, minPQ = {2,3,5}
                    // two choices for set: not move or move 1 out
                    // (1) set = {1,2,3,5} if not move out 1 
                    // (2) set = {2,3,5} if also move out 1
                    // Then if 4 coming, minPQ will change to {2,3,4,5}-> then move 
                    // out 2 because minPQ.size() > 3, minPQ = {3,4,5}
                    // two choices for set: not move or move 2 out
                    // (1) set = {1,2,3,4,5} if not move out 2
                    // (2) set = {3,4,5} if also move out 2
                    // We can see the obsolete {1,2} from set will have no
                    // chance to exist on minPQ at any case, even if any new
                    // coming 1 or 2 won't add onto minPQ anymore as 3 is
                    // the threshold as existing third maximum number on minPQ,
                    // no matter {1,2} on or off the set, it obsolete from
                    // minPQ forever
                    // --------------------------------------------
                    // Conclusion: 
                    // Any number obsolete from minPQ even still in set
                    // will have no chance to add to minPQ again, so no
                    // need to remove number from set
                    //int val = minPQ.poll();
                    //set.remove(val);
                    minPQ.poll();
                }
            }
        }
        return minPQ.size() == 3 ? minPQ.peek() : max;
    }
}

Time Complexity: O(N), not O(N*logN) because minPQ size not exceed 4, log4 can be treated as constant 
Space Complexity: O(N)
```

Style 4: Only PriorityQueue (capacity NOT limit as 3, 4th number will add on queue, always need to update one of these 3 numbers by removing operation, which means Style 3 significantly slower than Style 1)
```
class Solution {
    public int thirdMax(int[] nums) {
        PriorityQueue<Integer> minPQ = new PriorityQueue<>();
        int max = Integer.MIN_VALUE;
        for(int num : nums) {
            max = Math.max(max, num);
            if(!minPQ.contains(num)) {
                minPQ.offer(num);             
                if(minPQ.size() > 3) {
                    minPQ.poll();
                }
            }
        }
        return minPQ.size() == 3 ? minPQ.peek() : max;
    }
}

Time Complexity: O(N) ~ O(N*4), not O(N*logN) because minPQ size not exceed 4, log4 can be treated as constant, but multiply 4 because of PriorityQueue contains() method cost O(N) and N = 4 here, but O(4) is also a constant 
Space Complexity: O(N)
```

Refer to
https://leetcode.com/problems/third-maximum-number/solutions/90190/java-priorityqueue-o-n-o-1/comments/94679
The PriorityQueue has a contains method, so you don't need an extra Set. Since the pq's is no more than 3, the actual time complexity for the contains method is O(1) in our case. But Priority Queue contains method take o(n) time while set.contains take O(1).

Normally in case where k is not fixed it takes logk to insert an element into heap and total running complexity is O(nlogk) and space complexity is O(k)but here we are maintaining max 4 elements in heap which O(log4) to add one element and total would be O(nlog4) ~ O(n) and space O(4) which is constant.

This is general approach to find kth largest element in a array. You can also solve it using quickselect algo which would be O(n ) again.
---
Solution 2: One Pass (10 min)
```
class Solution {
    public int thirdMax(int[] nums) {
        // Why change to long ?
        // Test out by nums = {1,2,-2147483648}
        long max = Long.MIN_VALUE;
        long second_max = Long.MIN_VALUE;
        long third_max = Long.MIN_VALUE;
        for(int num : nums) {
            if((long)num > max) {
                third_max = second_max;
                second_max = max;
                max = num;
            } else if((long)num > second_max && (long)num != max) {
                third_max = second_max;
                second_max = num;
            } else if((long)num > third_max && (long)num != max && (long)num != second_max) {
                third_max = num;
            }
        }
        return (int)(third_max == Long.MIN_VALUE ? max : third_max);
    }
}

Time Complexity: O(N)
Space Complexity: O(1)
```

