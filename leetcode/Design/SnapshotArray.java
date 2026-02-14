https://leetcode.com/problems/snapshot-array/description/
Implement a SnapshotArray that supports the following interface:
- SnapshotArray(int length) initializes an array-like data structure with the given length. Initially, each element equals 0.
- void set(index, val) sets the element at the given index to be equal to val.
- int snap() takes a snapshot of the array and returns the snap_id: the total number of times we called snap() minus 1.
- int get(index, snap_id) returns the value at the given index, at the time we took the snapshot with the given snap_id
 
Example 1:
Input: ["SnapshotArray","set","snap","set","get"][[3],[0,5],[],[0,6],[0,0]]
Output: [null,null,0,null,5]
Explanation: 
SnapshotArray snapshotArr = new SnapshotArray(3); // set the length to be 3
snapshotArr.set(0,5);  // Set array[0] = 5
snapshotArr.snap();  // Take a snapshot, return snap_id = 0
snapshotArr.set(0,6);
snapshotArr.get(0,0);  // Get the value of array[0] with snap_id = 0, return 5
 
Constraints:
- 1 <= length <= 5 * 104
- 0 <= index < length
- 0 <= val <= 109
- 0 <= snap_id < (the total number of times we call snap())
- At most 5 * 104 calls will be made to set, snap, and get.
--------------------------------------------------------------------------------
Attempt 1: 2026-02-08
Solution 1: Hash Table + Binary Search (30min)
class SnapshotArray {
    // For each index, store a list of (snap_id, value) pairs representing updates
    // [index][(snap_id, value)]
    List<List<int[]>> history;
    int snapId;
    public SnapshotArray(int length) {
        history = new ArrayList<>();
        snapId = 0;
        // Initialize with value 0 at snap_id 0
        for(int i = 0; i < length; i++) {
            history.add(new ArrayList<>());
            history.get(i).add(new int[]{0, 0});
        }
    }
    
    public void set(int index, int val) {
        List<int[]> list = history.get(index);
        // Optimization for Same-Snapshot Updates:
        // - If multiple set() calls happen before a snap(), only the last 
        // value at current snap_id matters
        // - Overwrite the last entry instead of appending redundant updates
        if(!list.isEmpty() && list.get(list.size() - 1)[0] == snapId) {
            list.get(list.size() - 1)[1] = val;
        } else {
            list.add(new int[]{snapId, val});
        }
    }
    
    public int snap() {
        return snapId++;
    }
    
    // Find Upper Boundary (if no match snap_id)
    // Find target Occurrence (if exactly a match)
    public int get(int index, int snap_id) {
        List<int[]> list = history.get(index);
        int lo = 0;
        int hi = list.size() - 1;
        while(lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if(list.get(mid)[0] == snap_id) {
                return list.get(mid)[1];
            } else if(list.get(mid)[0] > snap_id) {
                hi = mid - 1;
            } else {
                lo = mid + 1;
            }
        }
        //return list.get(lo - 1)[1];
        return list.get(hi)[1];
    }
}

/**
 * Your SnapshotArray object will be instantiated and called as such:
 * SnapshotArray obj = new SnapshotArray(length);
 * obj.set(index,val);
 * int param_2 = obj.snap();
 * int param_3 = obj.get(index,snap_id);
 */
 
 Total Space: O(n + m) where m = total set() operations

Refer to QWEN
Here's an efficient and well-commented Java solution for LeetCode 1146: Snapshot Array:
import java.util.*;

class SnapshotArray {
    // For each index, store a list of (snap_id, value) pairs representing updates
    private List<List<int[]>> history; // int[] = {snap_id, value}
    private int snapId; // Current snap_id counter
    
    public SnapshotArray(int length) {
        history = new ArrayList<>(length);
        snapId = 0;
        
        // Initialize each index with initial value 0 at snap_id 0
        for (int i = 0; i < length; i++) {
            List<int[]> indexHistory = new ArrayList<>();
            indexHistory.add(new int[]{0, 0}); // (snap_id=0, value=0)
            history.add(indexHistory);
        }
    }
    
    public void set(int index, int val) {
        List<int[]> indexHistory = history.get(index);
        int[] lastUpdate = indexHistory.get(indexHistory.size() - 1);
        
        // Optimization: if last update was at current snap_id, overwrite it
        // (avoids storing redundant updates within same snapshot period)
        if (lastUpdate[0] == snapId) {
            lastUpdate[1] = val;
        } else {
            indexHistory.add(new int[]{snapId, val});
        }
    }
    
    public int snap() {
        return snapId++; // Return current snap_id, then increment
    }
    
    public int get(int index, int snap_id) {
        List<int[]> indexHistory = history.get(index);
        
        // Binary search: find rightmost update with snap_id <= requested snap_id
        int left = 0, right = indexHistory.size() - 1;
        int resultIndex = 0;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (indexHistory.get(mid)[0] <= snap_id) {
                resultIndex = mid; // Candidate found
                left = mid + 1;    // Try to find a more recent update
            } else {
                right = mid - 1;
            }
        }
        
        return indexHistory.get(resultIndex)[1];
    }
}
Key Insights:
1.Space-Efficient Design:
- Instead of copying the entire array on each snap() (O(n) space/time), store only changes per index
- Each index maintains its own history of (snap_id, value) pairs
2.Optimization for Same-Snapshot Updates:
- If multiple set() calls happen before a snap(), only the last value at current snap_id matters
- Overwrite the last entry instead of appending redundant updates
3.Binary Search for Efficient Lookup:
- History for each index is naturally sorted by snap_id (monotonically increasing)
- get(index, snap_id) uses binary search to find the most recent value set at or before the requested snapshot
Example Walkthrough:
SnapshotArray snapArr = new SnapshotArray(3);
snapArr.set(0, 5);    // history[0] = [(0,0) → (0,5)] (overwritten initial 0)
snapArr.snap();       // snap_id=0 → returns 0, then snapId=1
snapArr.set(0, 6);    // history[0] = [(0,5), (1,6)]
snapArr.get(0, 0);    // Binary search: finds (0,5) → returns 5
snapArr.set(0, 7);    // history[0] = [(0,5), (1,6), (1,7)] (overwrites 6 at snap_id=1)
snapArr.snap();       // snap_id=1 → returns 1, then snapId=2
snapArr.snap();       // snap_id=2 → returns 2, then snapId=3
snapArr.get(0, 2);    // Binary search: finds (1,7) → returns 7 (no update at snap_id=2)

Edge Cases Handled:
ScenarioBehaviorget() before any set()Returns initial value 0 (stored at snap_id=0)Multiple set() before first snap()Only last value at snap_id=0 is keptget(index, snap_id) where snap_id > last snapshotReturns value from most recent snapshot before requested idLarge number of snapshots (up to 5×10⁴)Binary search ensures O(log k) per get()
Complexity Analysis:
OperationTime ComplexitySpace ComplexityConstructorO(n)O(n)set()O(1) amortizedO(1) per updatesnap()O(1)O(1)get()O(log k) where k = updates for indexO(1)
- Total Space: O(n + m) where m = total set() operations
- Why efficient?: Avoids O(n) per snapshot (naive approach would be 10⁴ × 10⁴ = 10⁸ operations worst-case)
Why This Solution Works:
✅ Optimal space usage: Only stores actual changes, not full array copies
✅ Correct semantics: Handles all edge cases including initial values and same-snapshot updates
✅ Fast queries: Binary search ensures logarithmic lookup time
✅ Production-ready: Includes optimization to avoid redundant same-snapshot entries
This implementation efficiently handles the maximum constraints (length ≤ 5×10⁴, snaps ≤ 5×10⁴) while maintaining correctness for all operations. 📸

Refer to Deepseek
Here's the Java solution for LeetCode 1146 - Snapshot Array:
Problem Summary
Design a SnapshotArray that supports:
- SnapshotArray(length): Initialize array of given length with all zeros
- set(index, val): Set element at index to val
- snap(): Take snapshot, return snap_id (starts at 0)
- get(index, snap_id): Return value at index at given snapshot
Solution
Approach 1: TreeMap for Each Index (Most Efficient)
class SnapshotArray {
    private List<TreeMap<Integer, Integer>> arr;
    private int snapId;

    public SnapshotArray(int length) {
        arr = new ArrayList<>();
        snapId = 0;
        
        // Initialize each index with a TreeMap
        for (int i = 0; i < length; i++) {
            arr.add(new TreeMap<>());
            arr.get(i).put(0, 0); // Initial value at snap_id 0 is 0
        }
    }
    
    public void set(int index, int val) {
        // Store value for current snapId
        arr.get(index).put(snapId, val);
    }
    
    public int snap() {
        return snapId++;
    }
    
    public int get(int index, int snap_id) {
        // Get the floor entry (largest key <= snap_id)
        TreeMap<Integer, Integer> map = arr.get(index);
        return map.floorEntry(snap_id).getValue();
    }
}
Approach 2: Binary Search on History Lists
class SnapshotArray {
    private List<List<int[]>> history; // [index][(snap_id, value)]
    private int snapId;

    public SnapshotArray(int length) {
        history = new ArrayList<>();
        snapId = 0;
        
        for (int i = 0; i < length; i++) {
            history.add(new ArrayList<>());
            // Initialize with value 0 at snap_id 0
            history.get(i).add(new int[]{0, 0});
        }
    }
    
    public void set(int index, int val) {
        List<int[]> list = history.get(index);
        
        // If last entry has same snapId, update it
        if (!list.isEmpty() && list.get(list.size() - 1)[0] == snapId) {
            list.get(list.size() - 1)[1] = val;
        } else {
            // Otherwise add new entry
            list.add(new int[]{snapId, val});
        }
    }
    
    public int snap() {
        return snapId++;
    }
    
    public int get(int index, int snap_id) {
        List<int[]> list = history.get(index);
        
        // Binary search to find value at snap_id
        int left = 0, right = list.size() - 1;
        int result = 0;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (list.get(mid)[0] == snap_id) {
                return list.get(mid)[1];
            } else if (list.get(mid)[0] < snap_id) {
                result = list.get(mid)[1]; // Potential candidate
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        return result;
    }
}
Approach 3: Efficient Binary Search with Collections.binarySearch
class SnapshotArray {
    private List<List<int[]>> arr;
    private int snapId;

    public SnapshotArray(int length) {
        arr = new ArrayList<>();
        
        for (int i = 0; i < length; i++) {
            arr.add(new ArrayList<>());
            // Initialize with (snap_id, value) = (0, 0)
            arr.get(i).add(new int[]{0, 0});
        }
        
        snapId = 0;
    }
    
    public void set(int index, int val) {
        List<int[]> list = arr.get(index);
        int[] last = list.get(list.size() - 1);
        
        // If we already have an entry for current snapId, update it
        if (last[0] == snapId) {
            last[1] = val;
        } else {
            // Otherwise add new entry
            list.add(new int[]{snapId, val});
        }
    }
    
    public int snap() {
        return snapId++;
    }
    
    public int get(int index, int snap_id) {
        List<int[]> list = arr.get(index);
        
        // Binary search for snap_id
        int left = 0, right = list.size() - 1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (list.get(mid)[0] <= snap_id) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        // right is the index of last entry with snap_id <= target
        return list.get(right)[1];
    }
}
Approach 4: Optimized with Copy-on-Write
class SnapshotArray {
    private int[] array;
    private List<int[]>[] history;
    private int snapId;

    public SnapshotArray(int length) {
        array = new int[length];
        history = new ArrayList[length];
        
        for (int i = 0; i < length; i++) {
            history[i] = new ArrayList<>();
        }
        
        snapId = 0;
    }
    
    public void set(int index, int val) {
        array[index] = val;
    }
    
    public int snap() {
        // Record current state of all elements
        for (int i = 0; i < array.length; i++) {
            history[i].add(new int[]{snapId, array[i]});
        }
        return snapId++;
    }
    
    public int get(int index, int snap_id) {
        List<int[]> list = history[index];
        
        // Binary search for the value at snap_id
        int left = 0, right = list.size() - 1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (list.get(mid)[0] == snap_id) {
                return list.get(mid)[1];
            } else if (list.get(mid)[0] < snap_id) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        // If snap_id not found, return value from previous snapshot
        if (right >= 0) {
            return list.get(right)[1];
        }
        return 0;
    }
}
Explanation
Time Complexity:
- set(index, val): O(1) for TreeMap, O(1) amortized for list approach
- snap(): O(1)
- get(index, snap_id): O(log k) where k is number of changes at that index
Space Complexity: O(n + m)
- n: length of array
- m: total number of set operations
Key Insights:
1.Don't Store Full Array for Each Snapshot: That would be O(n * s) space
2.Store Only Changes: For each index, store (snap_id, value) pairs when value changes
3.Binary Search for Retrieval: When getting value at snap_id, find the most recent change ≤ snap_id
Example Walkthrough
Operations:
SnapshotArray(3) → arr = [0,0,0]
set(0,5) → arr = [5,0,0]
snap() → returns 0, snap_id becomes 1
set(0,6) → arr = [6,0,0]
get(0,0) → returns 5 (value at snapshot 0)

History for index 0:
[
    (0, 0),  // Initial
    (0, 5),  // set at snap_id 0
    (1, 6)   // set at snap_id 1
]

get(0,0): binary search finds (0,5) → returns 5
get(0,1): binary search finds (1,6) → returns 6
get(1,1): index 1 never changed → returns initial 0
Edge Cases
// Test Cases:
1. Multiple sets at same snapshot:

   set(0,1), set(0,2), snap() → get(0,0) should return 2

2. Get at non-existent snapshot:

   snap() returns 0, get(0,5) → should return value at last snapshot (0)

3. No changes to index:

   snap(), snap(), get(0,1) → returns 0

4. Large number of operations:

   Should handle efficiently without O(n) per snap
Comparison of Approaches
ApproachProsConsTreeMapClean, easy floorEntrySlightly more memoryList + Binary SearchLess memory overheadNeed custom binary searchCopy-on-WriteSimpleO(n) per snap, inefficient
TreeMap approach is generally the best because:
- Clean code using floorEntry()
- Efficient O(log k) operations
- Handles all edge cases gracefully
Can you show explain more about below logic ?
// If last entry has same snapId, update it
if (!list.isEmpty() && list.get(list.size() - 1)[0] == snapId) {
    list.get(list.size() - 1)[1] = val;
} else {
    // Otherwise add new entry
    list.add(new int[]{snapId, val});
}
Certainly! Let's break down the logic for the set method in Approach 2 (Binary Search on History Lists) for LeetCode 1146 – Snapshot Array.
The Problem Context
We need to record the value of an index at different snapshots. A straightforward way is to store for each index a list of (snap_id, value) pairs, in the order they were set. When we later call get(index, snap_id), we want the value that was current at that snapshot.
The Challenge
Multiple set operations can happen before a snap() is called, i.e., within the same snapshot interval. For example:
- set(0, 5) at snapshot 0
- set(0, 6) also at snapshot 0 (before any snap)
- Then snap() is called → snapshot 0 is finalized with value 6 at index 0.
If we naively added every set as a new entry, we'd have two entries for snapshot 0: (0,5) and (0,6). This is redundant and wastes space, because only the last value before the snapshot matters. Moreover, when retrieving with get(0, 0), we'd need to find the correct one – binary search would still work (since both have same snap_id, we'd pick the last one if we do a floor search), but it's inefficient and leads to unnecessary growth of the list.
The Optimization
To avoid duplicate entries for the same snapshot, we check if the last entry in the list already belongs to the current snapshot ID. If it does, we simply update its value instead of appending a new entry.
Code Breakdown
// Get the history list for this index
List<int[]> list = history.get(index);

// Check if the last entry has the same snapId as the current one
if (!list.isEmpty() && list.get(list.size() - 1)[0] == snapId) {
    // Yes – overwrite the value
    list.get(list.size() - 1)[1] = val;
} else {
    // No – this is the first set in this snapshot, add a new entry
    list.add(new int[]{snapId, val});
}
Step-by-Step Example
Assume we start with a fresh array and snapId = 0.
1.Initial state
For each index, we often pre‑add a base entry (0, 0) (or you can handle it implicitly). Let's say we start with an empty list and treat the initial value as 0 (so get returns 0 if no entry ≤ snapId exists).
But for clarity, suppose we have an initial entry (0, 0) added during construction.
2.First set at snapshot 0
set(0, 5)
- The last entry is (0, 0) with snapId = 0, which matches current snapId = 0.
- Condition is true → we update it to (0, 5).
Now list = [(0,5)].
3.Second set at the same snapshot
set(0, 6) (still snapId = 0)
- Last entry is (0, 5), snapId = 0 → update to (0, 6).
List remains [(0,6)].
4.snap() is called
snapId becomes 1.
5.Set in the new snapshot
set(0, 7) (now snapId = 1)
- Last entry is (0, 6) with snapId = 0, which is different from current snapId = 1.
- Condition false → we append a new entry (1, 7).
List = [(0,6), (1,7)].
6.Another set in snapshot 1
set(0, 8) (snapId still 1)
- Last entry is (1, 7) with snapId = 1 → update it to (1, 8).
List = [(0,6), (1,8)].
Now, when we call get(0, 0), we binary search for the largest entry with snapId ≤ 0, which is (0,6) → returns 6.
get(0, 1) returns 8, the correct value for snapshot 1.
Why This Is Important
- Space efficiency: Only one entry per snapshot per index (the final value before the snapshot).
- Time efficiency: The history list size is bounded by the number of snapshots where the index actually changed, not by the number of set calls.
- Correctness: It preserves the semantics of snapshots – the snapshot captures the last written value before it was taken.
This pattern is common in snapshot problems where we only care about the state at snapshot boundaries, not intermediate changes. It keeps the data structure lean and the binary search simple (since snap_ids in the list are strictly increasing).
