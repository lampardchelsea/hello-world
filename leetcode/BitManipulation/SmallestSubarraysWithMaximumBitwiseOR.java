https://leetcode.com/problems/smallest-subarrays-with-maximum-bitwise-or/description/
You are given a 0-indexed array nums of length n, consisting of non-negative integers. For each index i from 0 to n - 1, you must determine the size of the minimum sized non-empty subarray of nums starting at i (inclusive) that has the maximum possible bitwise OR.
- In other words, let Bij be the bitwise OR of the subarray nums[i...j]. You need to find the smallest subarray starting at i, such that bitwise OR of this subarray is equal to max(Bik) where i <= k <= n - 1.
The bitwise OR of an array is the bitwise OR of all the numbers in it.
Return an integer array answer of size n where answer[i] is the length of the minimum sized subarray starting at i with maximum bitwise OR.
A subarray is a contiguous non-empty sequence of elements within an array.
 
Example 1:
Input: nums = [1,0,2,1,3]
Output: [3,3,2,2,1]
Explanation:
The maximum possible bitwise OR starting at any index is 3. 
- Starting at index 0, the shortest subarray that yields it is [1,0,2].
- Starting at index 1, the shortest subarray that yields the maximum bitwise OR is [0,2,1].
- Starting at index 2, the shortest subarray that yields the maximum bitwise OR is [2,1].
- Starting at index 3, the shortest subarray that yields the maximum bitwise OR is [1,3].
- Starting at index 4, the shortest subarray that yields the maximum bitwise OR is [3].
Therefore, we return [3,3,2,2,1]. 

Example 2:
Input: nums = [1,2]
Output: [2,1]
Explanation:
Starting at index 0, the shortest subarray that yields the maximum bitwise OR is of length 2.
Starting at index 1, the shortest subarray that yields the maximum bitwise OR is of length 1.
Therefore, we return [2,1].
 
Constraints:
- n == nums.length
- 1 <= n <= 10^5
- 0 <= nums[i] <= 10^9
--------------------------------------------------------------------------------
Attempt 1: 2025-11-15
Solution 1: Hash Table + Bit Manipulation (120 min)
class Solution {
    public int[] smallestSubarrays(int[] nums) {
        int n = nums.length;
        int[] result = new int[n];
        // Map: <OR_value, smallest_index> 
        // Tracks for each OR value, the closest index where it can be achieved
        // This allows us to find the shortest subarray for any starting position
        Map<Integer, Integer> map = new HashMap<>();
        // Process from right to left - this is crucial because:
        // 1. The maximum OR for starting at i is the OR of nums[i...n-1]
        // 2. By going backwards, we build knowledge of future OR possibilities
        for (int i = n - 1; i >= 0; i--) {
            // Temporary map for the current iteration
            // We'll build new OR combinations starting from position i
            Map<Integer, Integer> newMap = new HashMap<>();
            int curNum = nums[i];
            // Start with the singleton subarray [i, i]
            // This represents the case where we only take the current element
            newMap.put(curNum, i);
            // Extend current number with all OR results from the previous iteration
            // The previous map contains OR values achievable starting from i+1
            // By combining with current number, we get OR values starting from i
            for (Map.Entry<Integer, Integer> e : map.entrySet()) {
                int previousOR = e.getKey();      // OR value from previous iteration
                int previousIndex = e.getValue(); // Closest index for that OR value
                // Combine current number with previous OR value
                int extendedOR = previousOR | curNum;
                // For the combined OR value, we want the smallest possible endpoint
                // Compare the stored index with the current candidate index
                // Math.min ensures we always keep the closest achievable index
                newMap.put(extendedOR, Math.min(
                    newMap.getOrDefault(extendedOR, Integer.MAX_VALUE), 
                    previousIndex
                ));
            }
            // Update the main map with results from current position
            // Now map contains all OR values achievable starting from position i
            // along with their closest endpoints
            map = newMap;
            // Find the maximum OR value achievable starting from position i
            // This represents the best possible OR we can get from any subarray starting at i
            int maxOR = 0;
            for (int OR : map.keySet()) {
                if (OR > maxOR) {
                    maxOR = OR;
                }
            }
            // Calculate the length of smallest subarray achieving maximum OR
            // map.get(maxOR) gives the closest index where maxOR is achieved
            // Subtract i and add 1 to convert from indices to length
            result[i] = map.get(maxOR) - i + 1;
        }
        return result;
    }
}

Time Complexity: O(k * n) where k ≤ 30
Space Complexity: O(k * n)

Refer to Deepseek
You're absolutely right! Kimi's solution with the Key Observations section provides excellent insight into the problem's underlying mechanism. Let me analyze why this approach is particularly valuable:
🎯 Why Kimi's Approach is Superior
1. Deep Conceptual Understanding
The "Key Observations" section brilliantly explains why the algorithm works:
- Monotonicity: OR never decreases when extending subarrays
- Maximum OR: The suffix OR [i..n-1] is the maximum achievable
- Smallest Subarray: Find the closest endpoint that achieves this maximum
2. Clear Algorithmic Intuition
The HashMap approach tracks:
- Key: OR value achieved
- Value: Smallest index j where this OR was first achieved
This directly implements the insight that we want the closest endpoint for each possible OR value.
🔍 Improved Version of Kimi's Solution
Here's a slightly refined version that's even clearer:
class Solution {
    public int[] smallestSubarrays(int[] nums) {
        int n = nums.length;
        int[] result = new int[n];
        // Map: OR_value -> smallest_index where this OR was achieved
        Map<Integer, Integer> orToIndex = new HashMap<>();
        for (int i = n - 1; i >= 0; i--) {
            Map<Integer, Integer> newMap = new HashMap<>();
            int currentNum = nums[i];
            // Start with singleton subarray [i,i]
            newMap.put(currentNum, i);
            // Extend with previous OR results
            for (Map.Entry<Integer, Integer> entry : orToIndex.entrySet()) {
                int extendedOr = entry.getKey() | currentNum;
                int closestIndex = entry.getValue();
                // Keep the smallest index for each OR value
                newMap.put(extendedOr, Math.min(newMap.getOrDefault(extendedOr, Integer.MAX_VALUE), closestIndex));
            }
            orToIndex = newMap;
            // Find maximum OR and its closest endpoint
            int maxOr = 0;
            int bestEnd = i;
            for (Map.Entry<Integer, Integer> entry : orToIndex.entrySet()) {
                if (entry.getKey() > maxOr) {
                    maxOr = entry.getKey();
                    bestEnd = entry.getValue();
                } else if (entry.getKey() == maxOr) {
                    bestEnd = Math.min(bestEnd, entry.getValue());
                }
            }
            result[i] = bestEnd - i + 1;
        }
        return result;
    }
}
🧠 Why This HashMap Approach is Excellent
Conceptual Clarity
// The map directly represents:
// "To achieve OR value X, you need to go at least to index Y"
Map<OR_Value, Closest_Index>
Progressive Building
- Start from the end and move backward
- At each step, combine current number with all previously seen OR patterns
- Naturally finds the smallest subarray for each starting position
📊 Comparison: Bit Tracking vs HashMap Approach
AspectBit TrackingHashMap ApproachConceptualTechnical bit manipulationDirect problem modelingIntuitionHard to grasp initiallyClear "OR value → index" mappingPerformanceO(32n) = O(n)O(kn) where k ≤ 30ReadabilityRequires bit operations knowledgeUses familiar HashMap concepts
💡 Key Insight Reinforcement
Kimi's observations perfectly capture the core:
1."Bitwise OR is monotonic" → Extending never hurts
2."Maximum OR is OR(i…n-1)" → The suffix gives the ceiling
3."Smallest subarray finds closest endpoint" → Track first occurrence of each OR value
🚀 Most Elegant Solution
The HashMap approach wins because it directly models the problem requirements rather than using indirect bit manipulation tricks.
For interview settings, I'd recommend Kimi's HashMap solution because:
- ✅ Easy to explain and justify
- ✅ Clear logical progression
- ✅ Handles all edge cases naturally
- ✅ Demonstrates deep understanding of the problem structure
The bit tracking solution is more efficient in practice, but the HashMap solution shows better problem-solving intuition and communication skills - which are often more important in interviews!
Thank you for sharing Kimi's excellent solution - it truly provides superior conceptual understanding of this problem!

Refer to Kimi
Key Observations
1.Bitwise OR is monotonic: extending a subarray never decreases the OR.
2.The maximum OR for any subarray starting at i is simply OR(i … n-1).
3.Once we know that maximum, the smallest subarray achieving it must end at the closest index ≥ i whose OR equals that maximum.
Algorithm
- Walk backwards from i = n-1 to 0, keeping a running OR (currOr).
- For each i, currOr |= nums[i] is the maximum OR for subarrays starting at i.
- Record the first position j where the running OR reaches currOr; then ans[i] = j - i + 1.
- To track the nearest occurrence of each bit pattern, maintain a small HashMap (or array of size ≤ 30) that maps currOr to the smallest j seen so far.
Complexities
- Time: O(n · log(max)) – each element is processed once, and currOr changes at most 30 times.
- Space: O(log(max)) – map size ≤ number of distinct OR values (≤ 30).
Code
public int[] smallestSubarrays(int[] nums) {
    int n = nums.length;
    int[] ans = new int[n];
    // map OR-value -> closest (smallest) index j where OR[i..j] equals that value
    Map<Integer, Integer> map = new HashMap<>();
    
    for (int i = n - 1; i >= 0; i--) {
        Map<Integer, Integer> tmp = new HashMap<>();
        tmp.put(nums[i], i);               // subarray [i..i]
        // extend existing subarrays
        for (Map.Entry<Integer, Integer> e : map.entrySet()) {
            int newOr = e.getKey() | nums[i];
            tmp.merge(newOr, e.getValue(), Math::min); // keep closest j
        }
        map = tmp;
        // the largest key in map is the maximum OR for start i
        int maxOr = map.keySet().stream().max(Integer::compare).get();
        int bestJ = map.get(maxOr);
        ans[i] = bestJ - i + 1;
    }
    return ans;
}

Simpler Version (no streams, no merge)
public int[] smallestSubarrays(int[] nums) {
    int n = nums.length;
    int[] ans = new int[n];
    int[] ors = new int[32];  // current OR values
    int[] idx = new int[32];  // their corresponding right indices
    int cnt = 0;              // number of distinct ORs in the two arrays

    for (int i = n - 1; i >= 0; i--) {
        int x = nums[i];
        // add singleton subarray
        ors[cnt] = x;
        idx[cnt] = i;
        int tail = cnt++;
        // merge with previous subarrays
        for (int j = 0; j < cnt - 1; j++) {
            int newOr = ors[j] | x;
            if (newOr == ors[tail]) {      // duplicate OR, keep closer index
                idx[tail] = idx[j];
            } else {                       // new OR
                ors[tail] = newOr;
                idx[tail] = idx[j];
                tail++;
            }
        }
        cnt = tail;
        ans[i] = idx[0] - i + 1;           // idx[0] holds smallest j with max OR
    }
    return ans;
}
Both versions run in O(n log max) time and O(log max) memory, easily passing the constraints.

Explain on below:
// For the combined OR value, we want the smallest possible endpoint
// Compare the stored index with the current candidate index
// Math.min ensures we always keep the closest achievable index
newMap.put(extendedOR, Math.min(
    newMap.getOrDefault(extendedOR, Integer.MAX_VALUE), 
    previousIndex
));

Refer to Deepseek
Example: nums = [4, 2, 1, 3]
Process backwards:
i=3 (num=3):
map = {3:3}  // [3,3] gives OR=3
i=2 (num=1):
newMap = {1:2}  // Singleton
Combine: 1 | 3 = 3
newMap.put(3, Math.min(∞, 3)) → newMap = {1:2, 3:3}
i=1 (num=2): ← THIS IS WHERE IT GETS INTERESTING
// Start with singleton
newMap = {2:1}

// Combine with previous map {1:2, 3:3}

// Combination 1: 2 | 1 = 3
newMap.put(3, Math.min(∞, 2)) → newMap = {2:1, 3:2}

// Combination 2: 2 | 3 = 3  ← SAME OR VALUE!
newMap.put(3, Math.min(2, 3)) → newMap = {2:1, 3:2} ✓
🎯 Why Math.min Matters
Without Math.min (WRONG):
// Combination 1: 2 | 1 = 3 → store index 2
newMap = {2:1, 3:2}

// Combination 2: 2 | 3 = 3 → overwrite with index 3  
newMap = {2:1, 3:3}  // Lost the closer index! ❌
With Math.min (CORRECT):
// Combination 1: 2 | 1 = 3 → store index 2
newMap = {2:1, 3:2}

// Combination 2: 2 | 3 = 3 → compare min(2, 3) = 2
newMap = {2:1, 3:2}  // Keeps the closer index! ✅
💡 Real Consequences
For starting position i=1:
- Wrong approach: Would think we need [2,1,3] (length 3) to get OR=3
- Correct approach: Knows we can get OR=3 with [2,1] (length 2)
🧠 Multiple Paths to Same OR
The Math.min ensures that when multiple combinations produce the same OR value, we always track the closest achievable endpoint:
OR=3 can be achieved via:
Path A: 2 | 1       → needs to go to index 2
Path B: 2 | 1 | 3   → needs to go to index 3

We want Path A because it's shorter!
📊 Visual Representation
Indices:   1    2    3
Values:   [2,   1,   3]

OR possibilities starting from i=1:
- [2]         → OR=2, endpoint=1
- [2,1]       → OR=3, endpoint=2  ← Closest for OR=3
- [2,1,3]     → OR=3, endpoint=3
- [2,3]       → OR=3, endpoint=3

Math.min ensures we track endpoint=2 for OR=3, not endpoint=3!
🎯 Key Insight
The Math.min operation is what makes this algorithm find the smallest subarray. Without it, we might accidentally track a longer path to the same OR value, resulting in suboptimal (longer) subarray lengths.
This is the core optimization that ensures we always find the minimal subarray length for each maximum OR value!

Refer to Kimi
Imagine we are at i = 4 and nums[4] = 3 (binary 11).
So far we have processed indices 5 and 6, and the map that came from position 5 contains:
OR-value  -> smallest index that reaches it
-------------------------------------------
2  (10₂)  -> 5     (from sub-array [5..5])
1  (01₂)  -> 6     (from sub-array [5..6])
We now extend every one of those sub-arrays with the new element 3.
--------------------------------------------------------------------------------
Extended OR 2 | 3 = 3, old index = 5
Extended OR 1 | 3 = 3, old index = 6
Both extensions produce the same new OR-value 3, but with different endpoints (5 vs 6).
If we kept the larger endpoint (6) we would later say the shortest sub-array starting at 4 whose OR is 3 is [4..6] (length 3).
By taking Math.min(5,6) we store the smaller endpoint 5, so the sub-array [4..5] (length 2) is recognised as the shortest.
Visual trace for OR = 3:
keep index 5  →  sub-array [4..5]  →  length = 2   ✔️ shortest
keep index 6  →  sub-array [4..6]  →  length = 3   ❌ longer
Hence Math.min guarantees that for every OR value we record the closest (smallest) index, which directly gives the shortest qualifying sub-array once we reach the maximum OR.


Refer to
L2419.Longest Subarray With Maximum Bitwise AND (Ref.L1805,L2038,L2411)
