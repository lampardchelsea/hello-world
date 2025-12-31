https://leetcode.com/problems/stone-game-vi/description/
Alice and Bob take turns playing a game, with Alice starting first.
There are n stones in a pile. On each player's turn, they can remove a stone from the pile and receive points based on the stone's value. Alice and Bob may value the stones differently.
You are given two integer arrays of length n, aliceValues and bobValues. Each aliceValues[i] and bobValues[i] represents how Alice and Bob, respectively, value the ith stone.
The winner is the person with the most points after all the stones are chosen. If both players have the same amount of points, the game results in a draw. Both players will play optimally. Both players know the other's values.
Determine the result of the game, and:
- If Alice wins, return 1.
- If Bob wins, return -1.
- If the game results in a draw, return 0.
 
Example 1:
Input: aliceValues = [1,3], bobValues = [2,1]
Output: 1
Explanation:
If Alice takes stone 1 (0-indexed) first, Alice will receive 3 points.Bob can only choose stone 0, and will only receive 2 points.Alice wins.

Example 2:
Input: aliceValues = [1,2], bobValues = [3,1]
Output: 0
Explanation:
If Alice takes stone 0, and Bob takes stone 1, they will both have 1 point.Draw.

Example 3:
Input: aliceValues = [2,4,3], bobValues = [1,6,7]
Output: -1
Explanation:
Regardless of how Alice plays, Bob will be able to have more points than Alice.For example, if Alice takes stone 1, Bob can take stone 2, and Alice takes stone 0, Alice will have 6 points to Bob's 7.Bob wins.
 
Constraints:
- n == aliceValues.length == bobValues.length
- 1 <= n <= 10^5
- 1 <= aliceValues[i], bobValues[i] <= 100
--------------------------------------------------------------------------------
Attempt 1: 2025-12-31
Solution 1: Math + Greedy + Sorting (10 min)
class Solution {
    public int stoneGameVI(int[] aliceValues, int[] bobValues) {
        /**
        The optimal strategy is to pick stones that give you the maximum 
        relative advantage = (your value + opponent's value)!
        Why? Because picking stone i gives you aliceValues[i] and denies 
        Bob bobValues[i], so the net advantage is aliceValues[i] + bobValues[i].
         */
        int n = aliceValues.length;
        int[][] stones = new int[n][2];
        // Store pairs: (combined value, index)
        for(int i = 0; i < n; i++) {
            stones[i][0] = aliceValues[i] + bobValues[i]; // combined value
            stones[i][1] = i; // original index
        }
        // Sort by combined value in descending order
        Arrays.sort(stones, (a, b) -> b[0] - a[0]);
        int aliceScore = 0;
        int bobScore = 0;
        for(int i = 0; i < n; i++) {
            int idx = stones[i][1];
            // Alice picks first, then Bob, alternating
            if(i % 2 == 0) {
                aliceScore += aliceValues[idx]; // Alice's turn
            } else {
                bobScore += bobValues[idx]; // Bob's turn
            }
        }
        return Integer.compare(aliceScore, bobScore);
    }
}

Time Complexity: O(n*logn)
Space Complexity: O(n)

Solution 2: Counting Sort (30 min)
class Solution {
    public int stoneGameVI(int[] aliceValues, int[] bobValues) {
        // Since values ≤ 100, combined ≤ 200
        // Create fixed-size buckets
        List<Integer>[] buckets = new ArrayList[201];
        for(int i = 0; i <= 200; i++) {
            buckets[i] = new ArrayList<>();
        }
        // Bucket sort: O(n)
        for(int i = 0; i < aliceValues.length; i++) {
            int sum = aliceValues[i] + bobValues[i];
            buckets[sum].add(i);
        }
        // Process in descending order: O(201 + n) = O(n)
        boolean aliceTurn = true;
        int aliceScore = 0;
        int bobScore = 0;
        for(int i = 200; i >= 0; i--) {
            for(int idx : buckets[i]) {
                if(aliceTurn) {
                    aliceScore += aliceValues[idx];
                } else {
                    bobScore += bobValues[idx];
                }
                // Each new index we have to switch a person
                aliceTurn = !aliceTurn;
            }
        }
        return Integer.compare(aliceScore, bobScore);
    }
}

Time Complexity: O(n)
Space Complexity: O(n)

Refer to Deepseek
Here's the Java solution for LeetCode 1686. Stone Game VI:
🎯 Problem Understanding
- Two players: Alice and Bob
- aliceValues[i]: value of stone i for Alice
- bobValues[i]: value of stone i for Bob
- Players take turns picking stones (Alice first)
- New rule: When a player picks stone i, both players get their respective value
- Goal: Maximize your total minus opponent's total
- Return: 1 if Alice wins, -1 if Bob wins, 0 if tie
🔍 Key Insight
The optimal strategy is to pick stones that give you the maximum relative advantage = (your value + opponent's value)!
Why? Because picking stone i gives you aliceValues[i] and denies Bob bobValues[i], so the net advantage is aliceValues[i] + bobValues[i].
📝 Greedy Solution
class Solution {
    public int stoneGameVI(int[] aliceValues, int[] bobValues) {
        int n = aliceValues.length;
        
        // Store pairs: (combined value, index)
        int[][] stones = new int[n][2];
        for (int i = 0; i < n; i++) {
            stones[i][0] = aliceValues[i] + bobValues[i]; // combined value
            stones[i][1] = i; // original index
        }
        
        // Sort by combined value in descending order
        Arrays.sort(stones, (a, b) -> b[0] - a[0]);
        
        int aliceScore = 0, bobScore = 0;
        
        // Alice picks first, then Bob, alternating
        for (int i = 0; i < n; i++) {
            int idx = stones[i][1];
            if (i % 2 == 0) { // Alice's turn
                aliceScore += aliceValues[idx];
            } else { // Bob's turn
                bobScore += bobValues[idx];
            }
        }
        
        return Integer.compare(aliceScore, bobScore);
    }
}
⚡ Optimized Version (No 2D Array)
class Solution {
    public int stoneGameVI(int[] aliceValues, int[] bobValues) {
        int n = aliceValues.length;
        
        // Create array of indices and sort by combined value
        Integer[] indices = new Integer[n];
        for (int i = 0; i < n; i++) {
            indices[i] = i;
        }
        
        // Sort indices by (aliceValues[i] + bobValues[i]) descending
        Arrays.sort(indices, (a, b) -> 
            (bobValues[b] + aliceValues[b]) - (bobValues[a] + aliceValues[a]));
        
        int aliceScore = 0, bobScore = 0;
        
        for (int i = 0; i < n; i++) {
            int idx = indices[i];
            if (i % 2 == 0) { // Alice's turn
                aliceScore += aliceValues[idx];
            } else { // Bob's turn
                bobScore += bobValues[idx];
            }
        }
        
        if (aliceScore > bobScore) return 1;
        if (aliceScore < bobScore) return -1;
        return 0;
    }
}
🧮 Mathematical Proof
Let's prove why greedy works:
For Alice picking stone i:
- Alice gains: aliceValues[i]
- Bob loses potential: bobValues[i] (he won't get it)
- Net advantage: aliceValues[i] + bobValues[i]
For Bob picking stone i:
- Bob gains: bobValues[i]
- Alice loses potential: aliceValues[i]
- Net advantage for Alice: -(aliceValues[i] + bobValues[i])
So the relative advantage of picking stone i is ±(aliceValues[i] + bobValues[i]). Players should pick stones with largest combined values.
🔄 Alternative Implementation with Priority Queue
class Solution {
    public int stoneGameVI(int[] aliceValues, int[] bobValues) {
        int n = aliceValues.length;
        
        // Max heap by combined value
        PriorityQueue<int[]> pq = new PriorityQueue<>(
            (a, b) -> b[0] - a[0]
        );
        
        for (int i = 0; i < n; i++) {
            pq.offer(new int[]{aliceValues[i] + bobValues[i], i});
        }
        
        int aliceScore = 0, bobScore = 0;
        boolean aliceTurn = true;
        
        while (!pq.isEmpty()) {
            int[] stone = pq.poll();
            int idx = stone[1];
            
            if (aliceTurn) {
                aliceScore += aliceValues[idx];
            } else {
                bobScore += bobValues[idx];
            }
            
            aliceTurn = !aliceTurn;
        }
        
        return Integer.compare(aliceScore, bobScore);
    }
}
📊 Space-Optimized Solution
class Solution {
    public int stoneGameVI(int[] aliceValues, int[] bobValues) {
        int n = aliceValues.length;
        
        // Single array: store combined values
        int[] combined = new int[n];
        for (int i = 0; i < n; i++) {
            combined[i] = aliceValues[i] + bobValues[i];
        }
        
        // Sort indices by combined value (indirect sort)
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < n; i++) indices.add(i);
        
        indices.sort((a, b) -> combined[b] - combined[a]);
        
        int alice = 0, bob = 0;
        for (int i = 0; i < n; i++) {
            int idx = indices.get(i);
            if (i % 2 == 0) {
                alice += aliceValues[idx];
            } else {
                bob += bobValues[idx];
            }
        }
        
        return alice > bob ? 1 : (alice < bob ? -1 : 0);
    }
}
🔍 Example Walkthrough
Input:
aliceValues = [1,3]
bobValues   = [2,1]
Step 1: Calculate combined values:
- Stone 0: 1 + 2 = 3
- Stone 1: 3 + 1 = 4
Step 2: Sort by combined (descending):
- Stone 1 (value 4)
- Stone 0 (value 3)
Step 3: Play:
1.Alice takes stone 1: Alice +3, Bob +0
2.Bob takes stone 0: Alice +0, Bob +2
Scores:
- Alice: 3
- Bob: 2
- Alice wins → return 1
📈 Complexity Analysis
SolutionTimeSpaceNotesArray SortO(n log n)O(n)Best practicalPriority QueueO(n log n)O(n)Slightly slowerCounting SortO(n + K)O(K)If values small
n ≤ 10⁵, so O(n log n) is acceptable.
🧪 Test Cases
public static void main(String[] args) {
    Solution sol = new Solution();
    
    // Example 1
    int[] alice1 = {1,3};
    int[] bob1 = {2,1};
    System.out.println(sol.stoneGameVI(alice1, bob1)); // 1
    
    // Example 2
    int[] alice2 = {1,2};
    int[] bob2 = {3,1};
    System.out.println(sol.stoneGameVI(alice2, bob2)); // 0
    
    // Example 3
    int[] alice3 = {2,4,3};
    int[] bob3 = {1,6,7};
    System.out.println(sol.stoneGameVI(alice3, bob3)); // -1 (Bob wins)
}
🎯 Comparison with Other Stone Games
GameKey InsightComplexityStrategyStone Game VICombined values matterO(n log n)Greedy by sumStone Game IPrefix sums, interval DPO(n²)Dynamic ProgrammingStone Game IIVariable M parameterO(n³)DP with state (i,M)Stone Game IIIFixed moves (1-3 stones)O(n)Linear DP
💡 Why This is Different
Stone Game VI is not a DP problem - it's a greedy game where:
1.Both players have complete information
2.The optimal move is independent of future moves
3.There's a dominant strategy: always pick stone with largest (your_value + opponent_value)
⚡ Optimization for Special Cases
If values are small (≤ 1000), we can use counting sort:
class Solution {
    public int stoneGameVI(int[] aliceValues, int[] bobValues) {
        int n = aliceValues.length;
        int maxVal = 2000; // Max combined value
        
        // Bucket sort by combined value
        List<List<Integer>> buckets = new ArrayList<>(maxVal + 1);
        for (int i = 0; i <= maxVal; i++) {
            buckets.add(new ArrayList<>());
        }
        
        for (int i = 0; i < n; i++) {
            int combined = aliceValues[i] + bobValues[i];
            buckets.get(combined).add(i);
        }
        
        int aliceScore = 0, bobScore = 0;
        boolean aliceTurn = true;
        
        // Process from highest combined value to lowest
        for (int val = maxVal; val >= 0; val--) {
            for (int idx : buckets.get(val)) {
                if (aliceTurn) {
                    aliceScore += aliceValues[idx];
                } else {
                    bobScore += bobValues[idx];
                }
                aliceTurn = !aliceTurn;
            }
        }
        
        return Integer.compare(aliceScore, bobScore);
    }
}
✅ Key Takeaways
1.Combined value = your gain + opponent's denial
2.Greedy works: Always pick highest combined value
3.Sorting is sufficient: No need for DP
4.O(n log n) is optimal for general case
This is one of the simpler Stone Game problems once you realize the greedy strategy!

Do we have any solution only cost O(n) time ?
Yes! For Stone Game VI, we can achieve O(n) time using counting sort if the values are bounded. The standard solution is O(n log n) because we sort, but if we know the value ranges, we can optimize to O(n).
🚀 O(n) Counting Sort Solution
class Solution {
    public int stoneGameVI(int[] aliceValues, int[] bobValues) {
        int n = aliceValues.length;
        
        // Find maximum possible combined value
        int maxCombined = 0;
        for (int i = 0; i < n; i++) {
            maxCombined = Math.max(maxCombined, aliceValues[i] + bobValues[i]);
        }
        
        // Bucket sort: store indices by their combined value
        List<Integer>[] buckets = new ArrayList[maxCombined + 1];
        for (int i = 0; i <= maxCombined; i++) {
            buckets[i] = new ArrayList<>();
        }
        
        // Place each stone in its bucket based on combined value
        for (int i = 0; i < n; i++) {
            int combined = aliceValues[i] + bobValues[i];
            buckets[combined].add(i);
        }
        
        // Collect stones from highest to lowest combined value
        int[] sortedIndices = new int[n];
        int idx = 0;
        
        for (int val = maxCombined; val >= 0; val--) {
            for (int stoneIdx : buckets[val]) {
                sortedIndices[idx++] = stoneIdx;
            }
        }
        
        // Calculate scores
        int aliceScore = 0, bobScore = 0;
        for (int i = 0; i < n; i++) {
            int stoneIdx = sortedIndices[i];
            if (i % 2 == 0) { // Alice's turn
                aliceScore += aliceValues[stoneIdx];
            } else { // Bob's turn
                bobScore += bobValues[stoneIdx];
            }
        }
        
        return Integer.compare(aliceScore, bobScore);
    }
}
🔍 But Wait - Is This Truly O(n)?
Technically yes, but with caveats:
1.Time Complexity: O(n + K) where K = max combined value
2.If K is O(n): Then O(n + n) = O(n)
3.If K is huge: Could be worse than O(n log n)
📊 Comparison of Solutions
SolutionTimeSpaceWhen to UseArrays.sort()O(n log n)O(n)General case (always works)Counting SortO(n + K)O(n + K)When K ≤ 10⁶ (values bounded)Quick SelectO(n) avgO(n)If we only need top stones
⚡ Even Better: Quick Select for O(n) Average
We don't actually need full sorting! We just need to process stones in descending order of combined value. We can use quick select to find medians:
class Solution {
    public int stoneGameVI(int[] aliceValues, int[] bobValues) {
        int n = aliceValues.length;
        
        // Store combined values with indices
        int[][] stones = new int[n][2];
        for (int i = 0; i < n; i++) {
            stones[i][0] = aliceValues[i] + bobValues[i];
            stones[i][1] = i;
        }
        
        // Quick select to process in approximate order
        // Not fully sorted, but good enough for greedy
        shuffle(stones);
        quickSelect(stones, 0, n - 1);
        
        int aliceScore = 0, bobScore = 0;
        for (int i = 0; i < n; i++) {
            int stoneIdx = stones[i][1];
            if (i % 2 == 0) {
                aliceScore += aliceValues[stoneIdx];
            } else {
                bobScore += bobValues[stoneIdx];
            }
        }
        
        return Integer.compare(aliceScore, bobScore);
    }
    
    private void quickSelect(int[][] arr, int left, int right) {
        if (left >= right) return;
        
        int pivotIndex = partition(arr, left, right);
        
        // Only sort if we need better ordering
        // For greedy, partial ordering might be enough
        quickSelect(arr, left, pivotIndex - 1);
        quickSelect(arr, pivotIndex + 1, right);
    }
    
    private int partition(int[][] arr, int left, int right) {
        int pivot = arr[right][0];
        int i = left;
        
        for (int j = left; j < right; j++) {
            if (arr[j][0] >= pivot) { // Descending order
                swap(arr, i, j);
                i++;
            }
        }
        swap(arr, i, right);
        return i;
    }
    
    private void swap(int[][] arr, int i, int j) {
        int[] temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
    
    private void shuffle(int[][] arr) {
        Random rand = new Random();
        for (int i = arr.length - 1; i > 0; i--) {
            int j = rand.nextInt(i + 1);
            swap(arr, i, j);
        }
    }
}
🎯 Best Practical O(n) Solution
Actually, we can do even simpler - we don't need any sorting at all if we use a max-heap of size 2!
class Solution {
    public int stoneGameVI(int[] aliceValues, int[] bobValues) {
        int n = aliceValues.length;
        
        // Find the TWO stones with highest combined values
        // Alice always picks the highest, Bob picks second highest
        // Then repeat...
        
        // But this gets complex. Let me think...
        // Actually, we need full ordering for optimal play
    }
}
🤔 Can We Really Avoid Sorting?
No, not in the general case. Here's why:
1.Greedy requires global ordering: To pick optimally, Alice needs to know which stone has the highest combined value overall
2.Comparison-based lower bound: Any comparison-based algorithm that finds the maximum must do Ω(n log n) comparisons
3.Information theory: Determining the total order of n elements requires Ω(n log n) time
🔬 Theoretical Lower Bound
For comparison-based algorithms, finding the maximum of n elements takes:
- Worst case: Ω(n log n) comparisons
- Best case with prior knowledge: O(n)
But we can beat this with non-comparison based sorting like counting sort, if values are bounded.
📈 When Counting Sort is O(n)
For counting sort to be O(n), we need:
1.K = O(n) where K is the range of values
2.Values are integers (not floating point)
LeetCode constraints:
- 1 <= aliceValues[i], bobValues[i] <= 100
- So 0 <= combined <= 200
- K = 200, which is O(1) constant!
✅ True O(n) Solution for LeetCode Constraints
Given LeetCode's constraints (values ≤ 100), here's the true O(n) solution:
class Solution {
    public int stoneGameVI(int[] aliceValues, int[] bobValues) {
        int n = aliceValues.length;
        
        // Since values ≤ 100, combined ≤ 200
        // Create fixed-size buckets
        List<Integer>[] buckets = new ArrayList[201]; // 0-200
        
        for (int i = 0; i <= 200; i++) {
            buckets[i] = new ArrayList<>();
        }
        
        // Bucket sort: O(n)
        for (int i = 0; i < n; i++) {
            int combined = aliceValues[i] + bobValues[i];
            buckets[combined].add(i);
        }
        
        // Process in descending order: O(201 + n) = O(n)
        int aliceScore = 0, bobScore = 0;
        boolean aliceTurn = true;
        
        for (int val = 200; val >= 0; val--) {
            for (int idx : buckets[val]) {
                if (aliceTurn) {
                    aliceScore += aliceValues[idx];
                } else {
                    bobScore += bobValues[idx];
                }
                aliceTurn = !aliceTurn;
            }
        }
        
        if (aliceScore > bobScore) return 1;
        if (aliceScore < bobScore) return -1;
        return 0;
    }
}
🧪 Proof: Why This is O(n)
1.Bucket initialization: O(201) = O(1) constant
2.Placing stones: O(n) - one pass
3.Processing buckets: O(201 + n) = O(n)
4.Total: O(n) + O(n) + O(1) = O(n)
🎯 Final Verdict
For general unbounded values: O(n log n) is optimal
For bounded values (like LeetCode): O(n) is achievable with counting sort
The key insight: We can trade space for time when values have limited range.
So yes, for Stone Game VI with the given constraints, O(n) is possible and optimal!



Refer to
L877.Stone Game (Ref.L1690)
L1140.Stone Game II (Ref.L877,L1690)
L1406.Stone Game III (Ref.L877,L1140,L1690)
L1510.Stone Game IV (Ref.L877,L1140,L1406,L1690)
L1563.Stone Game V (Ref.L877,L1140,L1406,L1510)
L1690.Stone Game VII (Ref.L1770)
1872
2029
