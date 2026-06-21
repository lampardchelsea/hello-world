https://leetcode.com/problems/count-good-meals/description/
A good meal is a meal that contains exactly two different food items with a sum of deliciousness equal to a power of two.
You can pick any two different foods to make a good meal.
Given an array of integers deliciousness where deliciousness[i] is the deliciousness of the i​​​​​​th​​​​​​​​ item of food, return the number of different good meals you can make from this list modulo 109 + 7.
Note that items with different indices are considered different even if they have the same deliciousness value.
 
Example 1:
Input: deliciousness = [1,3,5,7,9]
Output: 4
Explanation: The good meals are (1,3), (1,7), (3,5) and, (7,9).
Their respective sums are 4, 8, 8, and 16, all of which are powers of 2.

Example 2:
Input: deliciousness = [1,1,1,3,3,3,7]
Output: 15
Explanation: The good meals are (1,1) with 3 ways, (1,3) with 9 ways, and (1,7) with 3 ways.

Constraints:
1 <= deliciousness.length <= 10^5
0 <= deliciousness[i] <= 2^20
--------------------------------------------------------------------------------
Attempt 1: 2024-01-12
Solution 1: Hash Table + Bit Manipulation (180min)
Wrong Solution (71/72)
Failed on input is an array contains one hundred thousand 32
Input: deliciousness = [32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,
32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,
32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,
32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,
32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,32,
32,32,32,32,32,32,32,32,32,32,32,...32]
The deliciousness array contains 100000's 32
Output: 499949968
Expect: 999949972

class Solution {
    public int countPairs(int[] deliciousness) {
        int MOD = (int)1e9 + 7;
        Map<Integer, Integer> freq = new HashMap<>();
        for(int num : deliciousness) {
            freq.put(num, freq.getOrDefault(num, 0) + 1);
        }
        long count = 0;
        for(int key : freq.keySet()) {
            for(int i = 0; i < 22; i++) {
                int sum = 1 << i;
                //for(int key : freq.keySet()) {
                if(freq.containsKey(sum - key)) {
                    if(sum - key == key) {
                        count += (long)freq.get(key) * (freq.get(key) - 1) % MOD;
                    } else {
                        count += (long)freq.get(key) * freq.get(sum - key) % MOD;
                    }
                }
            }
        }
        count >>= 1;
        return (int)count % MOD;
    }
}

A correct version from chatGPT
The issue with your code above might be related to the bit manipulation and handling powers of two. In particular, the condition if (freq.containsKey(sum - key)) might be problematic for certain inputs.
I suggest modifying the code to handle the case where sum - key is a power of two, considering the constraints provided in the problem. Here's an updated version:
import java.util.HashMap;
import java.util.Map;

class Solution {
    public int countPairs(int[] deliciousness) {
        int MOD = (int) 1e9 + 7;
        Map<Integer, Integer> freq = new HashMap<>();

        // Find the maximum deliciousness value
        int maxDeliciousness = 0;
        for (int num : deliciousness) {
            maxDeliciousness = Math.max(maxDeliciousness, num);
            freq.put(num, freq.getOrDefault(num, 0) + 1);
        }

        long count = 0;
        for (int key : freq.keySet()) {
            for (int i = 1; i <= maxDeliciousness * 2; i <<= 1) {
                int complement = i - key;
                if (complement >= key && freq.containsKey(complement)) {
                    if (complement == key) {
                        count = (count + (long) freq.get(key) * (freq.get(key) - 1) / 2) % MOD;
                    } else {
                        count = (count + (long) freq.get(key) * freq.get(complement)) % MOD;
                    }
                }
            }
        }

        return (int) count;
    }
}

This version ensures that the condition if (freq.containsKey(sum - key)) considers all powers of two within the given constraints. Additionally, it uses (freq.get(key) * (freq.get(key) - 1) / 2) to calculate combinations for pairs with the same deliciousness.

Correct Solution
Copy from https://algo.monster/liteproblems/1711, but seems not much different than above wrong solution, the most suspect part is 
below section, relate to % MOD operation, but hard to debug, even switch the wrong solution inner and outer for loop order to make it same as correct solution, still failed on same test case
// The MOD calculation suspect as most potential error out part
if(sum - key == key) {
    count += (long)freq.get(key) * (freq.get(key) - 1) % MOD;
} else {
    count += (long)freq.get(key) * freq.get(sum - key) % MOD;
}
Full code
class Solution {
    // Define the modulus value for large numbers to avoid overflow
    private static final int MOD = (int) 1e9 + 7;

    // Method to count the total number of pairs with power of two sums
    public int countPairs(int[] deliciousness) {
        // Create a hashmap to store the frequency of each value in the deliciousness array
        Map<Integer, Integer> frequencyMap = new HashMap<>();
        for (int value : deliciousness) {
            frequencyMap.put(value, frequencyMap.getOrDefault(value, 0) + 1);
        }

        long pairCount = 0; // Initialize the pair counter to 0

        // Loop through each power of 2 up to 2^21 (because 2^21 is the closest power of 2 to 10^9)
        for (int i = 0; i < 22; ++i) {
            int sum = 1 << i; // Calculate the sum which is a power of two
            for (var entry : frequencyMap.entrySet()) {
                int firstElement = entry.getKey();   // Key in the map is a part of the deliciousness pair
                int firstCount = entry.getValue();   // Value in the map is the count of that element
                int secondElement = sum - firstElement;   // Find the second element of the pair

                // Check if the second element exists in the map
                if (!frequencyMap.containsKey(secondElement)) {
                    continue; // If it doesn't, continue to the next iteration
                }

                // If the second element exists, increment the pair count
                // If both elements are the same, we must avoid counting the pair twice
                pairCount += (long) firstCount * (firstElement == secondElement ? firstCount - 1 : frequencyMap.get(secondElement));
            }
        }

        // Divide the result by 2 because each pair has been counted twice
        pairCount >>= 1;

        // Return the result modulo MOD to get the answer within the range
        return (int) (pairCount % MOD);
    }
}

Time Complexity: O(N)
Space Complexity: O(N)

Refer to
https://algo.monster/liteproblems/1711
Problem Description
In this problem, we are given an array called deliciousness where each element represents the deliciousness level of a specific food item. We are tasked with finding combinations of exactly two different food items such that their total deliciousness equals a power of two. These combinations are called "good meals". To clarify, two food items are considered different if they are at different indices in the array, even if their deliciousness values are identical.
The output should be the number of good meals that we can create from the given list, and because this number could be very large, we are instructed to return it modulo 10^9 + 7. A modular result is a standard requirement in programming challenges to avoid overflow issues with high numbers.
Intuition
To solve this problem, we can use a hash map (in Python, this is a dictionary) to store the frequency of each deliciousness value. We iterate over all possible powers of two (up to the 21st power since the input constraint is 2^20), and within this iteration, we check each unique deliciousness value. For each of these values, say a, we look for another value b such that a + b equals the current power of two we're checking against. This value b must be 2^i - a.
Here's the step-by-step breakdown of our approach:
1.Initialize a Counter for the array deliciousness to keep track of the number of occurrences of each value of deliciousness.
2.Initialize a variable ans to keep track of the total number of good meals.
3.Loop through all the powers of two up to 2^21. This covers the range of possible sums of the two deliciousness values.
4.For each deliciousness value a found in the hash map we created, calculate b = 2^i - a.
5.If b is also in the hash map and a != b, then we have found a pair of different food items whose deliciousness sums to a power of two.
- In this case, we add to ans the product of the number of times a appears and the number of times b appears.
6.If a == b, we have found a pair of the same food items, and we add to ans the product of the number of times a appears with m - 1 because you cannot count the same pair twice.
7.Since each pair will be counted twice during this process (once for each element as a and once as b), we must divide the total answer by 2 to get the correct count.
8.Finally, take the modulo of the count by 10^9 + 7 to get our answer within the required range.
It is important to note that we use a bit manipulation trick—1 << i—to quickly find the i-th power of two, which greatly reduces the time complexity.
Solution Approach
The implementation uses a Counter from the Python collections module, which is essentially a hash map or dictionary designed to count the occurrences of each element in an iterable. This data structure is ideal for keeping track of the frequency of deliciousness in the given deliciousness array.
Here's a step-by-step guide to how the algorithm and data structures are used in the solution:
1.First, a Counter object named cnt is created to store the frequency of each value of deliciousness from the array.
2.We set ans to 0 as an accumulator for the total number of good meals.
3.We loop through all possible powers of two up to 2^21 (specified as 22 in the range(22) because range goes up to but does not include the end value in Python). We need to cover 2^20 since it's the maximum sum according to the problem constraints regarding deliciousness.
4.Inside this loop, we calculate s = 1 << i, which is a bit manipulation operation that left-shifts the number 1 by i places, effectively calculating 2^i.
5.With each power of two, we iterate over the items in the Counter object, where a is a deliciousness value from the array, and m is its frequency (the number of times it appears).
6.We then calculate b = s - a, to find the complementary deliciousness value that would make a sum of s with a.
7.We check if b is present in our Counter. If it is, we have a potential good meal. However, we must be mindful of counting pairs correctly:
- If a equals b, then we increment ans by m * (m - 1) because we can't use the same item twice, hence we consider the combinations without repetition.
- If a does not equal b, then we increment ans by m * cnt[b], considering all combinations between the occurrences of a and b.
8.After the loop, since every pair is counted twice (once for each of its two items, as both a and b), we divide ans by 2 to obtain the actual number of good meals.
9.Lastly, we apply modulo 10^9 + 7 to our result to handle the large numbers and prevent integer overflow issues as per the problem's requirement.
By utilizing a hash map (Counter) and iterating over the powers of two, the solution effectively pairs up food items while avoiding nested loops that would significantly increase the time complexity. This allows for an efficient solution to the problem.
Example Walkthrough
Let's walk through a small example to illustrate the solution approach using the array deliciousness = [1, 3, 5, 7, 9].
1.We first create a Counter object from the deliciousness array, which will count the frequency of each value. In this case, all values are unique and appear once, so our counter (cnt) would look like this: {1:1, 3:1, 5:1, 7:1, 9:1}.
2.We initialize ans to 0 to begin counting the number of good meals.
3.Now, we loop through all possible powers of two up to 2^21. For simplicity, consider that we just check up to 2^3 (or 8) for this example. Our powers of two are therefore [1, 2, 4, 8].
4.For the power of 2 (say s = 2^i), we loop through the Counter object items. Let's first choose s = 4 and consider the entries in our counter. We have a as the key and m as the frequency (always 1 in this case).
5.For each a, we calculate b = s - a to find the complementary deliciousness value.
6.We check if b exists in our counter. If it does, and a is not equal to b, then we found a good meal pair and increment ans by the product of their frequencies (since m is always 1, it would just be incremented by 1).
7.However, if a equals b, then we increment ans by m * (m - 1) / 2 which is zero in this case, as there's only one of each item.
8.We continue this process for all powers of two. Given our example and s = 4, we notice that pairs (1, 3) and (3, 1) form good meals because 1+3=4, which is a power of two. Both pairs are counted separately, so ans is incremented twice.
9.At the end of the loop, assuming we found no other pairs for other powers of two, ans would be 2 (since we found the (1, 3) pair twice). We then divide it by 2 to correct for the double counting, leaving us with a final ans of 1.
10.Lastly, we apply modulo 10^9 + 7 to our result. Since our ans is much less than 10^9 + 7, it remains unchanged.
Java Solution
class Solution {
    public int countPairs(int[] deliciousness) {
        int MOD = (int)1e9 + 7;
        Map<Integer, Integer> freq = new HashMap<>();
        for(int num : deliciousness) {
            freq.put(num, freq.getOrDefault(num, 0) + 1);
        }
        long count = 0;
        for(int key : freq.keySet()) {
            for(int i = 0; i < 22; i++) {
                int sum = 1 << i;
                //for(int key : freq.keySet()) {
                if(freq.containsKey(sum - key)) {
                    if(sum - key == key) {
                        count += (long)freq.get(key) * (freq.get(key) - 1) % MOD;
                    } else {
                        count += (long)freq.get(key) * freq.get(sum - key) % MOD;
                    }
                }
            }
        }
        count >>= 1;
        return (int)count % MOD;
    }
}
Time and Space Complexity
Time Complexity
The provided code has two nested loops. The outer loop is constant, iterating 22 times corresponding to powers of two up to 2^21, as any pair of meals should have a sum that is a power of two for a maximum possible pair value of 2^(20+20) = 2^40, and the closest power of two is 2^41.
The inner loop iterates through every element a in the deliciousness list once. So, if n is the length of deliciousness, the inner loop has a time complexity of O(n).
The if condition inside the inner loop checks if b exists in cnt, which is a Counter (essentially a dictionary), and this check is O(1) on average. The increment of ans is also O(1).
So, multiplying the constant 22 by the O(n) complexity of the inner loop gives the total time complexity:
T(n) = 22 * O(n) = O(n)
Space Complexity
The cnt variable is a Counter that stores the occurrences of each item in deliciousness. At worst, if all elements are unique, cnt would be the same size as deliciousness, so the space used by cnt is O(n) where n is the length of deliciousness.
S(n) = O(n)
There is a negligible additional space used for the loop indices, calculations, and single-item b, which does not depend on the size of deliciousness and thus does not affect the overall space complexity.
--------------------------------------------------------------------------------
Refer to Deepseek
以下是 LeetCode 1711 "Count Good Meals" 的 Java 解法，采用哈希表统计，时间复杂度 O(n·22)，空间 O(n)。
class Solution {
    public int countPairs(int[] deliciousness) {
        final int MOD = 1_000_000_007;
        Map<Integer, Integer> freq = new HashMap<>();
        long ans = 0;
        
        // 最大可能的两数之和: 2^20 + 2^20 = 2^21，但保险枚举到 2^22 也可
        // 因为 deliciousness[i] <= 2^20，所以最大和为 2^21，枚举 1,2,4,...,2^21
        for (int x : deliciousness) {
            // 枚举所有可能的2的幂
            for (int sum = 1; sum <= (1 << 21); sum <<= 1) {
                int complement = sum - x;
                ans += freq.getOrDefault(complement, 0);
            }
            // 将当前数加入频率表，供后续元素配对
            freq.put(x, freq.getOrDefault(x, 0) + 1);
        }
        return (int)(ans % MOD);
    }
}
思路简介：
- 遍历数组，对于当前数 x，所有能与它组成“好餐”的前序数 y 都必须满足 x + y = 2^k。
- 2 的幂的范围：因为 deliciousness[i] ≤ 2^20，所以最大和不超过 2^21。枚举所有 k 从 0 到 21，即 sum = 1, 2, 4, ..., 2^21。
- 用哈希表记录之前出现的每个数字及其频次，累加 complement = sum - x 的出现次数即可。
- 最后取模 1_000_000_007。
复杂度：
- 时间复杂度：O(n·22) ≈ O(n)，因为 22 是常数。
- 空间复杂度：O(n)。

Refer to Kimi
Here is the Java solution for LeetCode 1711. Count Good Meals.
Approach: HashMap + Power-of-Two Enumeration
A "good meal" requires the sum of two deliciousness values to be a power of two. Since deliciousness[i] ≤ 2^20, the maximum possible sum is 2^20 + 2^20 = 2^21. Thus, we only need to check 22 possible target sums (from 2^0 to 2^21).
For each number, we check how many previously seen numbers can pair with it to form each power of two.
Time Complexity: $O(n \cdot 22) = O(n)$
Space Complexity: $O(n)$
import java.util.*;

class Solution {
    public int countPairs(int[] deliciousness) {
        final int MOD = 1_000_000_007;
        Map<Integer, Integer> freq = new HashMap<>();
        long ans = 0;
        
        // Maximum possible sum is 2^20 + 2^20 = 2^21
        int maxPower = 1 << 21;  // 2^21
        
        for (int num : deliciousness) {
            // Check all powers of two from 2^0 to 2^21
            for (int sum = 1; sum <= maxPower; sum <<= 1) {
                int complement = sum - num;
                ans += freq.getOrDefault(complement, 0);
            }
            
            // Add current number to the frequency map
            freq.put(num, freq.getOrDefault(num, 0) + 1);
        }
        
        return (int) (ans % MOD);
    }
}

Why it works
StepActionIterate each numFor every possible power-of-two sum, compute complement = sum - numCount pairsIf complement was seen before, each occurrence forms a valid pair with current numUpdate mapAdd current num to the map so it can pair with future numbers
The inner loop runs at most 22 times (fixed constant), making the overall complexity linear. ans uses long to prevent integer overflow before the final modulo.
