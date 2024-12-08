/**
Refer to
https://leetcode.com/problems/minimum-number-of-days-to-make-m-bouquets/
Given an integer array bloomDay, an integer m and an integer k.

We need to make m bouquets. To make a bouquet, you need to use k adjacent flowers from the garden.

The garden consists of n flowers, the ith flower will bloom in the bloomDay[i] and then can be used in exactly one bouquet.

Return the minimum number of days you need to wait to be able to make m bouquets from the garden. If it is impossible to make m bouquets return -1.

Example 1:
Input: bloomDay = [1,10,3,10,2], m = 3, k = 1
Output: 3
Explanation: Let's see what happened in the first three days. x means flower bloomed and _ means flower didn't bloom in the garden.
We need 3 bouquets each should contain 1 flower.
After day 1: [x, _, _, _, _]   // we can only make one bouquet.
After day 2: [x, _, _, _, x]   // we can only make two bouquets.
After day 3: [x, _, x, _, x]   // we can make 3 bouquets. The answer is 3.

Example 2:
Input: bloomDay = [1,10,3,10,2], m = 3, k = 2
Output: -1
Explanation: We need 3 bouquets each has 2 flowers, that means we need 6 flowers. We only have 5 flowers so it is impossible to get the needed bouquets and we return -1.

Example 3:
Input: bloomDay = [7,7,7,7,12,7,7], m = 2, k = 3
Output: 12
Explanation: We need 2 bouquets each should have 3 flowers.
Here's the garden after the 7 and 12 days:
After day 7: [x, x, x, x, _, x, x]
We can make one bouquet of the first three flowers that bloomed. We cannot make another bouquet from the last three flowers that bloomed because they are not adjacent.
After day 12: [x, x, x, x, x, x, x]
It is obvious that we can make two bouquets in different ways.

Example 4:
Input: bloomDay = [1000000000,1000000000], m = 1, k = 1
Output: 1000000000
Explanation: You need to wait 1000000000 days to have a flower ready for a bouquet.

Example 5:
Input: bloomDay = [1,10,2,9,3,8,4,7,5,6], m = 4, k = 2
Output: 9

Constraints:
bloomDay.length == n
1 <= n <= 10^5
1 <= bloomDay[i] <= 10^9
1 <= m <= 10^6
1 <= k <= n
*/

// Solution 1: Binary Search
// Refer to
// https://leetcode.com/problems/minimum-number-of-days-to-make-m-bouquets/discuss/686316/JavaC%2B%2BPython-Binary-Search
/**
Intuition
If m * k > n, it impossible, so return -1.
Otherwise, it's possible, we can binary search the result.
left = 1 is the smallest days,
right = 1e9 is surely big enough to get m bouquests.
So we are going to binary search in range [left, right].

Explanation
Given mid days, we can know which flowers blooms.
Now the problem is, given an array of true and false,
find out how many adjacent true bouquest in total.

If bouq < m, mid is still small for m bouquest.
So we turn left = mid + 1

If bouq >= m, mid is big enough for m bouquest.
So we turn right = mid

Complexity
Time O(Nlog(maxA))
Space O(1)

Note that the result must be one A[i],
so actually we can sort A in O(NlogK),
Where K is the number of different values.
and then binary search the index of different values.

Though I don't thik worth doing that.

Java:
    public int minDays(int[] A, int m, int k) {
        int n = A.length, left = 1, right = (int)1e9;
        if (m * k > n) return -1;
        while (left < right) {
            int mid = (left + right) / 2, flow = 0, bouq = 0;
            for (int j = 0; j < n; ++j) {
                if (A[j] > mid) {
                    flow = 0;
                } else if (++flow >= k) {
                    bouq++;
                    flow = 0;
                }
            }
            if (bouq < m) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return left;
    }
*/
class Solution {
    public int minDays(int[] bloomDay, int m, int k) {
        int n = bloomDay.length;
        if(m * k > n) {
            return -1;
        }
        int lo = 1;
        int hi = (int)1e9;
        while(lo < hi) {
            int flower = 0;
            int bouquet = 0;
            int mid = lo + (hi - lo) / 2;
            for(int i = 0; i < n; i++) {
                // flower not able to bloom at 'mid' day
                if(bloomDay[i] > mid) {
                    flower = 0;
                // flower able to bloom at 'mid' day
                } else {
                    flower++;
                    // k adjacent flower detected
                    if(flower >= k) {
                        bouquet++;
                        // after making one bouquet set flower count back to 0
                        // prepare for making next bouquet
                        flower = 0;
                    }
                }
            }
            // bouquet number smaller than required number means 'mid' days not enough
            if(bouquet < m) {
                lo = mid + 1; // use bloomDay = [7,7,7,7,12,7,7], m = 2, k = 3 this input to check when lo = 1, hi = 15 it will flow into this logic branch
            } else {
                hi = mid;
            }
        }
        return lo;
    }
}

// Style 2:
// Refer to
// https://leetcode.com/discuss/general-discussion/786126/python-powerful-ultimate-binary-search-template-solved-many-problems
/**
1482. Minimum Number of Days to Make m Bouquets [Medium]
Given an integer array bloomDay, an integer m and an integer k. We need to make m bouquets. To make a bouquet, you need to use k adjacent flowers from the garden. The garden consists of n flowers, the ith flower will bloom in the bloomDay[i] and then can be used in exactly one bouquet. Return the minimum number of days you need to wait to be able to make m bouquets from the garden. If it is impossible to make m bouquets return -1.

Examples:

Input: bloomDay = [1,10,3,10,2], m = 3, k = 1
Output: 3
Explanation: Let's see what happened in the first three days. x means flower bloomed and _ means flower didn't bloom in the garden.
We need 3 bouquets each should contain 1 flower.
After day 1: [x, _, _, _, _]   // we can only make one bouquet.
After day 2: [x, _, _, _, x]   // we can only make two bouquets.
After day 3: [x, _, x, _, x]   // we can make 3 bouquets. The answer is 3.
Input: bloomDay = [1,10,3,10,2], m = 3, k = 2
Output: -1
Explanation: We need 3 bouquets each has 2 flowers, that means we need 6 flowers. We only have 5 flowers so it is impossible to get the needed bouquets and we return -1.
Now that we've solved three advanced problems above, this one should be pretty easy to do. The monotonicity of this problem is very clear: if we can make m bouquets after waiting for d days, then we can definitely finish that as well if we wait for more than d days.

def minDays(bloomDay: List[int], m: int, k: int) -> int:
    def feasible(days) -> bool:
        bonquets, flowers = 0, 0
        for bloom in bloomDay:
            if bloom > days:
                flowers = 0
            else:
                bonquets += (flowers + 1) // k
                flowers = (flowers + 1) % k
        return bonquets >= m

    if len(bloomDay) < m * k:
        return -1
    left, right = 1, max(bloomDay)
    while left < right:
        mid = left + (right - left) // 2
        if feasible(mid):
            right = mid
        else:
            left = mid + 1
    return left
*/
class Solution {
    public int minDays(int[] bloomDay, int m, int k) {
        int lo = 1;
        int hi = 0;
        int n = bloomDay.length;
        if(n < m * k) {
            return -1;
        }
        for(int bd : bloomDay) {
            hi = Math.max(hi, bd);
        }
        while(lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if(totalBouquets(bloomDay, mid, k) >= m) {
                hi = mid;
            } else {
                lo = mid + 1;
            }
        }
        return lo;
    }
    
    private int totalBouquets(int[] bloomDay, int days, int k) {
        int flower = 0;
        int bouquets = 0;
        for(int bd : bloomDay) {
            if(bd > days) {
                flower = 0;
            } else {
                //bouquets += (flower + 1) / k;
                //flower = (flower + 1) % k;
                flower++;
                if(flower >= k) {
                    bouquets++;
                    flower = 0;
                }
            }
        }
        return bouquets;
    }
}




































































https://leetcode.com/problems/minimum-number-of-days-to-make-m-bouquets/description/
You are given an integer array bloomDay, an integer m and an integer k.
You want to make m bouquets. To make a bouquet, you need to use k adjacent flowers from the garden.
The garden consists of n flowers, the ith flower will bloom in the bloomDay[i] and then can be used in exactly one bouquet.
Return the minimum number of days you need to wait to be able to make m bouquets from the garden. If it is impossible to make m bouquets return -1.

Example 1:
Input: bloomDay = [1,10,3,10,2], m = 3, k = 1
Output: 3
Explanation: Let us see what happened in the first three days. x means flower bloomed and _ means flower did not bloom in the garden.
We need 3 bouquets each should contain 1 flower.
After day 1: [x, _, _, _, _]   // we can only make one bouquet.
After day 2: [x, _, _, _, x]   // we can only make two bouquets.
After day 3: [x, _, x, _, x]   // we can make 3 bouquets. 
The answer is 3.

Example 2:
Input: bloomDay = [1,10,3,10,2], m = 3, k = 2
Output: -1
Explanation: We need 3 bouquets each has 2 flowers, that means we need 6 flowers. We only have 5 flowers so it is impossible to get the needed bouquets and we return -1.

Example 3:
Input: bloomDay = [7,7,7,7,12,7,7], m = 2, k = 3
Output: 12
Explanation: We need 2 bouquets each should have 3 flowers.
Here is the garden after the 7 and 12 days:
After day 7: [x, x, x, x, _, x, x]
We can make one bouquet of the first three flowers that bloomed. We cannot make another bouquet from the last three flowers that bloomed because they are not adjacent.
After day 12: [x, x, x, x, x, x, x]
It is obvious that we can make two bouquets in different ways.
 
Constraints:
- bloomDay.length == n
- 1 <= n <= 10^5
- 1 <= bloomDay[i] <= 10^9
- 1 <= m <= 10^6
- 1 <= k <= n
--------------------------------------------------------------------------------
Attempt 1: 2024-12-07
Solution 1: Binary Search + Greedy (10 min)
class Solution {
    public int minDays(int[] bloomDay, int m, int k) {
        // Add new test case, (int m = 89945, k = 32127),
        // the older solution may cannot pass all test cases,
        // have to convert m * k to long type
        if((long) m * k > bloomDay.length) {
            return -1;
        }
        int lo = (int)(1e9 + 1);
        int hi = 0;
        for(int bd : bloomDay) {
            lo = Math.min(lo, bd);
            hi = Math.max(hi, bd);
        }
        while(lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if(canMakeBouquets(bloomDay, m, k, mid)) {
                hi = mid - 1;
            } else {
                lo = mid + 1;
            }
        }
        return lo;
    }

    private boolean canMakeBouquets(int[] bloomDay, int m, int k, int minWaitDays) {
        int bouquets = 0;
        // Adajacent flower's count whose bloom day no more than minimum wait days
        int flowers = 0;
        // Greedy loop all flowers one by one as adjacent only strategy
        for(int bd : bloomDay) {
            if(bd <= minWaitDays) {
                // Flower can be used
                flowers++;
                // Form a bouquet since all flowers used are adjacent
                if(flowers == k) {
                    // Form a bouquet
                    bouquets++;
                    // Reset count for next section
                    flowers = 0;
                }
                // Make required number of bouquets
                if(bouquets >= m) {
                    return true;
                }
            } else {
                // Reset count if the current flower can't bloom in 'day' days
                flowers = 0;
            }
        }
        return false;
    }
}

Time Complexity: O(nlogn)
Space Complexity: O(1)

Refer to chatGPT
Key Idea:
This problem can be solved using binary search:
1.The minimum possible day is min(bloomDay), and the maximum possible day is max(bloomDay).
2.Use binary search to find the smallest day such that it's possible to make m bouquets with k flowers each.
class Solution {
    public int minDays(int[] bloomDay, int m, int k) {
        int n = bloomDay.length;
        // If total flowers are less than required for m bouquets, return -1
        if (m * k > n) {
            return -1;
        }

        // Binary search bounds
        int left = Integer.MAX_VALUE;
        int right = Integer.MIN_VALUE;

        // Find the range of bloom days
        for (int day : bloomDay) {
            left = Math.min(left, day);
            right = Math.max(right, day);
        }

        // Binary search to find the minimum day
        while (left <= right) {
            int mid = left + (right - left) / 2;

            // Check if we can make m bouquets in `mid` days
            if (canMakeBouquets(bloomDay, m, k, mid)) {
                right = mid - 1; // Try for an earlier day
            } else {
                left = mid + 1; // Need more days
            }
        }

        return left;
    }

    private boolean canMakeBouquets(int[] bloomDay, int m, int k, int day) {
        int bouquets = 0;
        int flowers = 0;

        // Iterate through the bloomDay array
        for (int bloom : bloomDay) {
            if (bloom <= day) {
                flowers++; // Flower can be used
                if (flowers == k) {
                    bouquets++; // Form a bouquet
                    flowers = 0; // Reset flowers
                }
            } else {
                flowers = 0; // Reset if the current flower can't bloom in `day` days
            }

            if (bouquets >= m) {
                return true; // Already formed required bouquets
            }
        }

        return false; // Not enough bouquets
    }
}
Explanation:
1.Binary Search:
- The range of possible days is [min(bloomDay), max(bloomDay)].
- Perform binary search to determine the smallest day d such that m bouquets can be made.
2.canMakeBouquets Function:
- Traverse the bloomDay array.
- Count consecutive flowers that bloom on or before d.
- If k consecutive flowers are found, form a bouquet and reset the count.
- Stop early if m bouquets are formed.
3.Return Result:
- If binary search completes, left contains the minimum day that works.



Refer to
L410.Split Array Largest Sum (Ref.L1011,L704,L1482)
L1011.Capacity To Ship Packages Within D Days (Ref.L410,L1482)
L704.Binary Search
