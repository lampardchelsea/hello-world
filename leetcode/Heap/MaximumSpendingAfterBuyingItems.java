https://leetcode.com/problems/maximum-spending-after-buying-items/description/
You are given a 0-indexed m * n integer matrix values, representing the values of m * n different items in m different shops. Each shop has n items where the jth item in the ith shop has a value of values[i][j]. Additionally, the items in the ith shop are sorted in non-increasing order of value. That is, values[i][j] >= values[i][j + 1] for all 0 <= j < n - 1.
On each day, you would like to buy a single item from one of the shops. Specifically, On the dth day you can:
- Pick any shop i.
- Buy the rightmost available item j for the price of values[i][j] * d. That is, find the greatest index j such that item j was never bought before, and buy it for the price of values[i][j] * d.
Note that all items are pairwise different. For example, if you have bought item 0 from shop 1, you can still buy item 0 from any other shop.
Return the maximum amount of money that can be spent on buying all m * n products.
 
Example 1:
Input: values = [[8,5,2],[6,4,1],[9,7,3]]
Output: 285
Explanation: 
On the first day, we buy product 2 from shop 1 for a price of values[1][2] * 1 = 1.
On the second day, we buy product 2 from shop 0 for a price of values[0][2] * 2 = 4.
On the third day, we buy product 2 from shop 2 for a price of values[2][2] * 3 = 9.
On the fourth day, we buy product 1 from shop 1 for a price of values[1][1] * 4 = 16.
On the fifth day, we buy product 1 from shop 0 for a price of values[0][1] * 5 = 25.
On the sixth day, we buy product 0 from shop 1 for a price of values[1][0] * 6 = 36.
On the seventh day, we buy product 1 from shop 2 for a price of values[2][1] * 7 = 49.
On the eighth day, we buy product 0 from shop 0 for a price of values[0][0] * 8 = 64.
On the ninth day, we buy product 0 from shop 2 for a price of values[2][0] * 9 = 81.
Hence, our total spending is equal to 285.
It can be shown that 285 is the maximum amount of money that can be spent buying all m * n products. 

Example 2:
Input: values = [[10,8,6,4,2],[9,7,5,3,2]]
Output: 386
Explanation: 
On the first day, we buy product 4 from shop 0 for a price of values[0][4] * 1 = 2.
On the second day, we buy product 4 from shop 1 for a price of values[1][4] * 2 = 4.
On the third day, we buy product 3 from shop 1 for a price of values[1][3] * 3 = 9.
On the fourth day, we buy product 3 from shop 0 for a price of values[0][3] * 4 = 16.
On the fifth day, we buy product 2 from shop 1 for a price of values[1][2] * 5 = 25.
On the sixth day, we buy product 2 from shop 0 for a price of values[0][2] * 6 = 36.
On the seventh day, we buy product 1 from shop 1 for a price of values[1][1] * 7 = 49.
On the eighth day, we buy product 1 from shop 0 for a price of values[0][1] * 8 = 64
On the ninth day, we buy product 0 from shop 1 for a price of values[1][0] * 9 = 81.
On the tenth day, we buy product 0 from shop 0 for a price of values[0][0] * 10 = 100.
Hence, our total spending is equal to 386.
It can be shown that 386 is the maximum amount of money that can be spent buying all m * n products.
 
Constraints:
- 1 <= m == values.length <= 10
- 1 <= n == values[i].length <= 104
- 1 <= values[i][j] <= 106
- values[i] are sorted in non-increasing order.
--------------------------------------------------------------------------------
Attempt 1: 2026-02-08
Solution 1: Priority Queue (10 min)
class Solution {
    public long maxSpending(int[][] values) {
        int m = values.length;
        int n = values[0].length;
        // Create min-heap with [value, shop_index, item_index]
        PriorityQueue<int[]> minPQ = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        // Initialize with the last item from each shop (cheapest in each shop)
        for(int i = 0; i < m; i++) {
            minPQ.offer(new int[] {values[i][n - 1], i, n - 1});
        }
        // Add to total: value * day
        long total = 0;
        int day = 1;
        while(!minPQ.isEmpty()) {
            int[] cur = minPQ.poll();
            int value = cur[0];
            int shop = cur[1];
            int index = cur[2];
            total += (long) value * day;
            day++;
            // If there are more items in this shop, add the next one
            if(index - 1 >= 0) {
                minPQ.offer(new int[] {values[shop][index - 1], shop, index - 1});
            }
        }
        return total;
    }
}

Time Complexity: O(m*n log m) - Each insertion/removal is O(log m)
Space Complexity: O(m) or O(m*n)

Solution 2: Flatten + Sorting (10 min)
class Solution {
    public long maxSpending(int[][] values) {
        PriorityQueue<Integer> minPQ = new PriorityQueue<>();
        // Flatten the 2D array into a 1D list
        for(int[] value : values) {
            // Items in each shop are already sorted in non-increasing order
            // But we want to buy cheapest first (to maximize total spending)
            // So we need to traverse each shop from last to first
            for(int price : value) {
                minPQ.add(price);
            }
        }
        long total = 0;
        int day = 1;
        while(!minPQ.isEmpty()) {
            int price = minPQ.poll();
            total += (long) price * day;
            day++;
        }
        return total;
    }
}

Time Complexity: O(mn log(mn)) - For sorting all items
Space Complexity: O(m) or O(m*n)

Refer to Deepseek
Here's the Java solution for LeetCode 2931 - Maximum Spending After Buying Items:
Problem Summary
We have m shops, each with n items sorted in non-increasing order. Each day we can buy exactly one item from one shop. The cost on day t is item_price * t. We want to maximize the total spending.
Solution
Approach 1: Min-Heap (Priority Queue)
class Solution {
    public long maxSpending(int[][] values) {
        int m = values.length;
        int n = values[0].length;
        
        // Create min-heap with [value, shop_index, item_index]
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        
        // Initialize with the last item from each shop (cheapest in each shop)
        for (int i = 0; i < m; i++) {
            pq.offer(new int[]{values[i][n - 1], i, n - 1});
        }
        
        long total = 0;
        int day = 1;
        
        while (!pq.isEmpty()) {
            int[] current = pq.poll();
            int value = current[0];
            int shop = current[1];
            int itemIndex = current[2];
            
            // Add to total: value * day
            total += (long) value * day;
            day++;
            
            // If there are more items in this shop, add the next one
            if (itemIndex > 0) {
                pq.offer(new int[]{values[shop][itemIndex - 1], shop, itemIndex - 1});
            }
        }
        
        return total;
    }
}
Approach 2: Flatten and Sort
class Solution {
    public long maxSpending(int[][] values) {
        int m = values.length;
        int n = values[0].length;
        
        // Flatten the 2D array into a 1D list
        List<Integer> allItems = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            // Items in each shop are already sorted in non-increasing order
            // But we want to buy cheapest first (to maximize total spending)
            // So we need to traverse each shop from last to first
            for (int j = n - 1; j >= 0; j--) {
                allItems.add(values[i][j]);
            }
        }
        
        // Sort all items in ascending order
        Collections.sort(allItems);
        
        // Calculate total spending
        long total = 0;
        for (int i = 0; i < allItems.size(); i++) {
            total += (long) allItems.get(i) * (i + 1);
        }
        
        return total;
    }
}
Approach 3: Direct Simulation (Most Efficient)
class Solution {
    public long maxSpending(int[][] values) {
        int m = values.length;
        int n = values[0].length;
        
        // For each shop, track the current index (starting from the last item)
        int[] indices = new int[m];
        for (int i = 0; i < m; i++) {
            indices[i] = n - 1; // Start from last item in each shop
        }
        
        long total = 0;
        
        // We need to buy m*n items total
        for (int day = 1; day <= m * n; day++) {
            // Find the minimum value among current available items
            int minValue = Integer.MAX_VALUE;
            int minShop = -1;
            
            for (int i = 0; i < m; i++) {
                if (indices[i] >= 0) { // If shop still has items
                    if (values[i][indices[i]] < minValue) {
                        minValue = values[i][indices[i]];
                        minShop = i;
                    }
                }
            }
            
            // Buy the minimum value item
            total += (long) minValue * day;
            
            // Move to next item in that shop
            indices[minShop]--;
        }
        
        return total;
    }
}
Approach 4: Optimized with 2D to 1D Conversion and Counting Sort
class Solution {
    public long maxSpending(int[][] values) {
        int m = values.length;
        int n = values[0].length;
        
        // Since values are in range [1, 10^6], we can use counting
        // But simpler: just collect all values
        int[] flattened = new int[m * n];
        int idx = 0;
        
        // Collect all values in reverse order for each shop
        for (int i = 0; i < m; i++) {
            for (int j = n - 1; j >= 0; j--) {
                flattened[idx++] = values[i][j];
            }
        }
        
        // Sort the array
        Arrays.sort(flattened);
        
        // Calculate total
        long total = 0;
        for (int i = 0; i < flattened.length; i++) {
            total += (long) flattened[i] * (i + 1);
        }
        
        return total;
    }
}
Explanation
Key Insight:
To maximize total spending sum(value * day), we want:
- Cheapest items on early days (small day number)
- Most expensive items on later days (large day number)
Since items in each shop are already sorted in non-increasing order (highest to lowest), we should buy from the end of each shop first (cheapest items first).
Time Complexity:
- Approach 1 (Min-Heap): O(m*n log m) - Each insertion/removal is O(log m)
- Approach 2 (Flatten & Sort): O(mn log(mn)) - For sorting all items
- Approach 3 (Direct Simulation): O((m*n) * m) - For each day, we check m shops
Space Complexity: O(m) or O(m*n)
Example Walkthrough
Input: values = [[8,5,2],[6,4,1],[9,7,3]]
Shops are already sorted: [8,5,2], [6,4,1], [9,7,3]

Strategy: Buy cheapest available item each day
Day 1: Cheapest is 1 (from shop 2) → total = 1*1 = 1
Day 2: Cheapest is 2 (from shop 0) → total = 1 + 2*2 = 5
Day 3: Cheapest is 3 (from shop 2) → total = 5 + 3*3 = 14
Day 4: Cheapest is 4 (from shop 1) → total = 14 + 4*4 = 30
Day 5: Cheapest is 5 (from shop 0) → total = 30 + 5*5 = 55
Day 6: Cheapest is 6 (from shop 1) → total = 55 + 6*6 = 91
Day 7: Cheapest is 7 (from shop 2) → total = 91 + 7*7 = 140
Day 8: Cheapest is 8 (from shop 0) → total = 140 + 8*8 = 204
Day 9: Cheapest is 9 (from shop 2) → total = 204 + 9*9 = 285

Output: 285
Alternative Thinking:
We can think of this as taking all items, sorting them in ascending order, then multiplying each by its position (day).
All items: [8,5,2,6,4,1,9,7,3]
Sorted: [1,2,3,4,5,6,7,8,9]
Total = 1*1 + 2*2 + 3*3 + 4*4 + 5*5 + 6*6 + 7*7 + 8*8 + 9*9 = 285
Edge Cases
// Test Cases:
1. Single shop: values = [[3,2,1]] → Total = 1*1 + 2*2 + 3*3 = 14
2. Single item per shop: values = [[5],[3],[4]] → Total = 3*1 + 4*2 + 5*3 = 26
3. All same values: values = [[2,2],[2,2]] → Total = 2*1 + 2*2 + 2*3 + 2*4 = 20
4. Large values: Should use long to prevent overflow
The min-heap approach (Approach 1) is the most efficient for this problem because it avoids sorting all items at once and uses the fact that items in each shop are already sorted.

Refer to QWEN
Here's an efficient and well-commented Java solution for LeetCode 2931: Maximum Total Price of Items After Buying:
import java.util.*;

class Solution {
    public long maxSpending(int[][] values) {
        int m = values.length;      // number of shops
        int n = values[0].length;   // items per shop
        
        // Extract all item prices into a single list
        List<Integer> prices = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                prices.add(values[i][j]);
            }
        }
        
        // Sort prices in ascending order
        // Why? To maximize total spending: 
        // - Cheapest items should be bought EARLY (small day multipliers)
        // - Most expensive items should be bought LATE (large day multipliers)
        Collections.sort(prices);
        
        // Calculate maximum total spending
        // Day d (0-indexed) has multiplier (d + 1)
        long total = 0;
        for (int day = 0; day < prices.size(); day++) {
            total += (long) prices.get(day) * (day + 1);
        }
        
        return total;
    }
}
Problem Understanding
- You have m shops, each with n items (total m × n items)
- On day d (0-indexed), buying an item with price p costs: p × (d + 1)
- You must buy exactly one item per day for m × n days
- Goal: Maximize total spending (not minimize!)
Key Insight: Greedy Strategy
To maximize Σ(price × multiplier) where multipliers increase over time:
- ✅ Buy cheapest items FIRST (apply small multipliers to small prices)
- ✅ Buy most expensive items LAST (apply large multipliers to large prices)
This is the rearrangement inequality in action: to maximize the sum of products, pair smallest elements with smallest multipliers and largest with largest.
Example Walkthrough
Input:
values = [
  [8, 5, 2],
  [6, 4, 1],
  [9, 7, 3]
]
Processing:
All prices: [8,5,2,6,4,1,9,7,3]
Sorted:     [1,2,3,4,5,6,7,8,9]

Day multipliers: [1,2,3,4,5,6,7,8,9]

Total = 1×1 + 2×2 + 3×3 + 4×4 + 5×5 + 6×6 + 7×7 + 8×8 + 9×9
      = 1 + 4 + 9 + 16 + 25 + 36 + 49 + 64 + 81
      = 285
Why not reverse?
If we bought expensive items early: 9×1 + 8×2 + ... + 1×9 = 165 (much smaller!)
Edge Cases Handled
CaseExampleBehaviorSingle shop[[1,2,3]]Sort → [1,2,3] → 1×1 + 2×2 + 3×3 = 14Single item per shop[[5],[3],[7]]Sort → [3,5,7] → 3×1 + 5×2 + 7×3 = 34All same prices[[4,4],[4,4]]Any order → 4×(1+2+3+4) = 40Large valuesPrices up to 10⁵, grid up to 10⁴ cellsUse long to prevent overflow
Complexity Analysis
- Time Complexity: O(m × n log (m × n))
- Extracting all prices: O(m × n)
- Sorting: O(m × n log (m × n)) — dominates
- Space Complexity: O(m × n) for storing all prices
Optimization Note
For very large grids, you could use a min-heap to avoid storing all prices at once:
public long maxSpending(int[][] values) {
    PriorityQueue<Integer> minHeap = new PriorityQueue<>();
    for (int[] shop : values) {
        for (int price : shop) {
            minHeap.offer(price);
        }
    }
    
    long total = 0;
    int day = 1;
    while (!minHeap.isEmpty()) {
        total += (long) minHeap.poll() * day++;
    }
    return total;
}
However, the sorting approach is simpler and equally efficient for the problem constraints (grid size ≤ 10⁴).
Why This Works
✅ Mathematically optimal: Rearrangement inequality guarantees this greedy strategy maximizes the sum
✅ Overflow-safe: Uses long for accumulation (prices up to 10⁵ × days up to 10⁴ → max ~10¹³)
✅ Simple & readable: Clear sorting-based approach with intuitive logic
This solution efficiently computes the maximum possible spending by strategically ordering purchases to leverage increasing day multipliers. 💰



Refer to
L23.P15.1.Merge k Sorted Lists
L1423.Maximum Points You Can Obtain from Cards (Ref.L1658,L2091,L2379)
