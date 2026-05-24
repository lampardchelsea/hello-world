https://leetcode.com/problems/sort-the-people/description/
You are given an array of strings names, and an array heights that consists of distinct positive integers. Both arrays are of length n.
For each index i, names[i] and heights[i] denote the name and height of the ith person.
Return names sorted in descending order by the people's heights.
 
Example 1:
Input: names = ["Mary","John","Emma"], heights = [180,165,170]
Output: ["Mary","Emma","John"]
Explanation: Mary is the tallest, followed by Emma and John.

Example 2:
Input: names = ["Alice","Bob","Bob"], heights = [155,185,150]
Output: ["Bob","Alice","Bob"]
Explanation: The first Bob is the tallest, followed by Alice and the second Bob.
 
Constraints:
- n == names.length == heights.length
- 1 <= n <= 103
- 1 <= names[i].length <= 20
- 1 <= heights[i] <= 105
- names[i] consists of lower and upper case English letters.
- All the values of heights are distinct.
--------------------------------------------------------------------------------
Attempt 1: 2026-05-24
Solution 1: Array + Sorting (10 min)
Style 1: Index Proxy
class Solution {
    public String[] sortPeople(String[] names, int[] heights) {
        int n = names.length;
        Integer[] indices = new Integer[n];
        for(int i = 0; i < n; i++) {
            indices[i] = i;
        }
        Arrays.sort(indices, (a, b) -> (heights[b] - heights[a]));
        String[] sortedNames = new String[n];
        for(int i = 0; i < n; i++) {
            sortedNames[i] = names[indices[i]];
        }
        return sortedNames;
    }
}

Time Complexity: O(n log n) where n is the array length. 
Index creation O(n), sorting O(n log n), reconstruction O(n).
Space Complexity: O(n) for the Integer[] indices array and the output array. 
(Note: Integer boxing adds slight overhead, but it's negligible for n ≤ 1000).

Style 2: Avoid Integer boxing
class Solution {
    public String[] sortPeople(String[] names, int[] heights) {
        int n = names.length;
        int[][] person = new int[n][2];
        for(int i = 0; i < n; i++) {
            person[i][0] = heights[i];
            person[i][1] = i;
        }
        Arrays.sort(person, (a, b) -> (b[0] - a[0]));
        String[] sortedNames = new String[n];
        for(int i = 0; i < n; i++) {
            sortedNames[i] = names[person[i][1]];
        }
        return sortedNames;
    }
}

Time Complexity: O(n log n) where n is the array length. 
Index creation O(n), sorting O(n log n), reconstruction O(n).
Space Complexity: O(n) for the Integer[] indices array and the output array. 
(Note: Integer boxing adds slight overhead, but it's negligible for n ≤ 1000).

Refer to QWEN
Here's a clean and optimal Java solution for LeetCode 2418. Sort the People:
import java.util.Arrays;

class Solution {
    public String[] sortPeople(String[] names, int[] heights) {
        int n = names.length;
        
        // Step 1: Create an index array [0, 1, 2, ..., n-1]
        Integer[] indices = new Integer[n];
        for (int i = 0; i < n; i++) {
            indices[i] = i;
        }

        // Step 2: Sort indices based on corresponding heights (descending)
        Arrays.sort(indices, (a, b) -> Integer.compare(heights[b], heights[a]));

        // Step 3: Rebuild the names array using the sorted indices
        String[] sortedNames = new String[n];
        for (int i = 0; i < n; i++) {
            sortedNames[i] = names[indices[i]];
        }

        return sortedNames;
    }
}
🔍 How it Works:
1.Parallel Array Problem: Java doesn't natively sort two separate arrays together while keeping their index alignment.
2.Index Proxy: We create an Integer[] indices array representing original positions [0, 1, ..., n-1].
3.Custom Comparator: We sort indices by comparing heights[b] and heights[a]. Since heights is fixed, sorting the indices indirectly sorts the relationship between names and heights.
4.Reconstruction: We map the sorted indices back to names to produce the final result.
⏱️ Complexity:
- Time Complexity: O(n log n) where n is the array length. Index creation O(n), sorting O(n log n), reconstruction O(n).
- Space Complexity: O(n) for the Integer[] indices array and the output array. (Note: Integer boxing adds slight overhead, but it's negligible for n ≤ 1000).
💡 Performance-Optimized Variant (Zero Boxing)
If you want to avoid Integer boxing for larger inputs, use a primitive int[][] instead:
class Solution {
    public String[] sortPeople(String[] names, int[] heights) {
        int n = names.length;
        int[][] people = new int[n][2];
        for (int i = 0; i < n; i++) {
            people[i][0] = heights[i]; // column 0: height
            people[i][1] = i;          // column 1: original index
        }

        Arrays.sort(people, (a, b) -> Integer.compare(b[0], a[0]));

        String[] result = new String[n];
        for (int i = 0; i < n; i++) {
            result[i] = names[people[i][1]];
        }
        return result;
    }
}
Why this is better for production: Uses primitive int arrays → no autoboxing, better cache locality, slightly faster in tight loops.
📝 Key Interview Takeaway:
Whenever you see "sort array A based on values in array B", the standard pattern is:
1.Create index/pair structure
2.Sort with custom comparator referencing the key array
3.Project back to the target array
Let me know if you'd like a version using Java Stream API, or if you want to dive into tie-breaking logic (if heights weren't distinct)!

Refer to
L1636.Sort Array by Increasing Frequency (Ref.L451)
L2545.Sort the Students by Their Kth Score (Ref.L791)
