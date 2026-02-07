https://leetcode.com/problems/high-access-employees/description/
You are given a 2D 0-indexed array of strings, access_times, with size n. For each i where 0 <= i <= n - 1, access_times[i][0] represents the name of an employee, and access_times[i][1] represents the access time of that employee. All entries in access_times are within the same day.
The access time is represented as four digits using a 24-hour time format, for example, "0800" or "2250".
An employee is said to be high-access if he has accessed the system three or more times within a one-hour period.
Times with exactly one hour of difference are not considered part of the same one-hour period. For example, "0815" and "0915" are not part of the same one-hour period.
Access times at the start and end of the day are not counted within the same one-hour period. For example, "0005" and "2350" are not part of the same one-hour period.
Return a list that contains the names of high-access employees with any order you want.
 
Example 1:
Input: access_times = [["a","0549"],["b","0457"],["a","0532"],["a","0621"],["b","0540"]]
Output: ["a"]
Explanation: "a" has three access times in the one-hour period of [05:32, 06:31] which are 05:32, 05:49, and 06:21.But "b" does not have more than two access times at all.So the answer is ["a"].

Example 2:
Input: access_times = [["d","0002"],["c","0808"],["c","0829"],["e","0215"],["d","1508"],["d","1444"],["d","1410"],["c","0809"]]
Output: ["c","d"]
Explanation: "c" has three access times in the one-hour period of [08:08, 09:07] which are 08:08, 08:09, and 08:29."d" has also three access times in the one-hour period of [14:10, 15:09] which are 14:10, 14:44, and 15:08.However, "e" has just one access time, so it can not be in the answer and the final answer is ["c","d"].

Example 3:
Input: access_times = [["cd","1025"],["ab","1025"],["cd","1046"],["cd","1055"],["ab","1124"],["ab","1120"]]
Output: ["ab","cd"]
Explanation: "ab" has three access times in the one-hour period of [10:25, 11:24] which are 10:25, 11:20, and 11:24."cd" has also three access times in the one-hour period of [10:25, 11:24] which are 10:25, 10:46, and 10:55.So the answer is ["ab","cd"].
 
Constraints:
- 1 <= access_times.length <= 100
- access_times[i].length == 2
- 1 <= access_times[i][0].length <= 10
- access_times[i][0] consists only of English small letters.
- access_times[i][1].length == 4
- access_times[i][1] is in 24-hour time format.
- access_times[i][1] consists only of '0' to '9'.
--------------------------------------------------------------------------------
Attempt 1: 2026-02-03
Solution 1: Hash Table + Sorting (10 min, this problem is 99% similar to L1604.Alert Using Same Key-Card Three or More Times in a One Hour Period)
class Solution {
    public List<String> findHighAccessEmployees(List<List<String>> access_times) {
        List<String> result = new ArrayList<>();
        Map<String, List<Integer>> map = new HashMap<>();
        for(List<String> access_time : access_times) {
            String employee = access_time.get(0);
            int time = convert(access_time.get(1));
            map.putIfAbsent(employee, new ArrayList<>());
            map.get(employee).add(time);
        }
        for(Map.Entry<String, List<Integer>> e : map.entrySet()) {
            String employee = e.getKey();
            List<Integer> times = e.getValue();
            Collections.sort(times);
            for(int i = 0; i < times.size() - 2; i++) {
                if(times.get(i + 2) - times.get(i) < 60) {
                    result.add(employee);
                    break;
                }
            }
        }
        return result;
    }

    private int convert(String time) {
        int h = Integer.parseInt(time.substring(0, 2));
        int m = Integer.parseInt(time.substring(2));
        return h * 60 + m;
    }
}

Time Complexity: O(N log N), N = number of access records. Dominated by sorting times per employee (worst case: one employee with all N records → O(N log N))
Space Complexity: O(N) for storing access times grouped by employee

Refer to Deepseek
Here's the Java solution for LeetCode 2933 - High-Access Employees:
Problem Summary
Given a 2D array of employee access times, find all employees who had at least 3 access times within any 60-minute period. This is essentially the same problem as LeetCode 1604, but with a different input format.
Solution
Approach 1: Sorting + Sliding Window
class Solution {
    public List<String> findHighAccessEmployees(List<List<String>> access_times) {
        Map<String, List<Integer>> map = new HashMap<>();
        
        // Group times by employee name
        for (List<String> record : access_times) {
            String name = record.get(0);
            String time = record.get(1);
            
            // Convert time to minutes (4-digit string HHMM)
            int minutes = convertToMinutes(time);
            map.putIfAbsent(name, new ArrayList<>());
            map.get(name).add(minutes);
        }
        
        List<String> result = new ArrayList<>();
        
        // Check each employee
        for (Map.Entry<String, List<Integer>> entry : map.entrySet()) {
            String name = entry.getKey();
            List<Integer> times = entry.getValue();
            
            // Sort the times
            Collections.sort(times);
            
            // Check for at least 3 accesses within 60 minutes
            for (int i = 0; i <= times.size() - 3; i++) {
                if (times.get(i + 2) - times.get(i) < 60) {
                    result.add(name);
                    break;
                }
            }
        }
        
        return result;
    }
    
    private int convertToMinutes(String time) {
        // time is in format "HHMM" (4 digits)
        int hours = Integer.parseInt(time.substring(0, 2));
        int minutes = Integer.parseInt(time.substring(2));
        return hours * 60 + minutes;
    }
}
Approach 2: Optimized with PriorityQueue
class Solution {
    public List<String> findHighAccessEmployees(List<List<String>> access_times) {
        Map<String, PriorityQueue<Integer>> map = new HashMap<>();
        
        // Group times by employee using priority queue (min-heap)
        for (List<String> record : access_times) {
            String name = record.get(0);
            String time = record.get(1);
            
            int minutes = convertToMinutes(time);
            map.putIfAbsent(name, new PriorityQueue<>());
            map.get(name).offer(minutes);
        }
        
        List<String> result = new ArrayList<>();
        
        for (Map.Entry<String, PriorityQueue<Integer>> entry : map.entrySet()) {
            String name = entry.getKey();
            PriorityQueue<Integer> pq = entry.getValue();
            
            // Convert to list (already sorted by priority queue)
            List<Integer> times = new ArrayList<>();
            while (!pq.isEmpty()) {
                times.add(pq.poll());
            }
            
            // Check sliding window
            for (int i = 2; i < times.size(); i++) {
                if (times.get(i) - times.get(i - 2) < 60) {
                    result.add(name);
                    break;
                }
            }
        }
        
        return result;
    }
    
    private int convertToMinutes(String time) {
        // Faster conversion without substring
        char[] chars = time.toCharArray();
        int hours = (chars[0] - '0') * 10 + (chars[1] - '0');
        int minutes = (chars[2] - '0') * 10 + (chars[3] - '0');
        return hours * 60 + minutes;
    }
}
Approach 3: Using ArrayDeque for Sliding Window
class Solution {
    public List<String> findHighAccessEmployees(List<List<String>> access_times) {
        Map<String, List<Integer>> map = new HashMap<>();
        
        // First, collect all times for each employee
        for (List<String> record : access_times) {
            String name = record.get(0);
            String time = record.get(1);
            
            int minutes = convertToMinutes(time);
            map.computeIfAbsent(name, k -> new ArrayList<>()).add(minutes);
        }
        
        List<String> result = new ArrayList<>();
        
        for (Map.Entry<String, List<Integer>> entry : map.entrySet()) {
            String name = entry.getKey();
            List<Integer> times = entry.getValue();
            
            if (times.size() < 3) continue;
            
            Collections.sort(times);
            
            // Use a sliding window with deque
            Deque<Integer> deque = new ArrayDeque<>();
            
            for (int time : times) {
                deque.offer(time);
                
                // Remove times that are more than 60 minutes before current time
                while (!deque.isEmpty() && time - deque.peekFirst() >= 60) {
                    deque.pollFirst();
                }
                
                // Check if we have at least 3 accesses in current window
                if (deque.size() >= 3) {
                    result.add(name);
                    break;
                }
            }
        }
        
        return result;
    }
    
    private int convertToMinutes(String time) {
        int hours = Integer.parseInt(time.substring(0, 2));
        int minutes = Integer.parseInt(time.substring(2));
        return hours * 60 + minutes;
    }
}
Approach 4: One-pass with Early Termination
class Solution {
    public List<String> findHighAccessEmployees(List<List<String>> access_times) {
        // Sort the entire list by time first
        access_times.sort((a, b) -> {
            int timeA = convertToMinutes(a.get(1));
            int timeB = convertToMinutes(b.get(1));
            return timeA - timeB;
        });
        
        Map<String, List<Integer>> map = new HashMap<>();
        Set<String> highAccessEmployees = new HashSet<>();
        
        for (List<String> record : access_times) {
            String name = record.get(0);
            int time = convertToMinutes(record.get(1));
            
            // If already marked as high-access, skip
            if (highAccessEmployees.contains(name)) continue;
            
            map.putIfAbsent(name, new ArrayList<>());
            List<Integer> times = map.get(name);
            
            // Remove times that are more than 60 minutes before current time
            times.removeIf(t -> time - t >= 60);
            
            // Add current time
            times.add(time);
            
            // Check if we have at least 3 accesses
            if (times.size() >= 3) {
                highAccessEmployees.add(name);
            }
        }
        
        return new ArrayList<>(highAccessEmployees);
    }
    
    private int convertToMinutes(String time) {
        return Integer.parseInt(time.substring(0, 2)) * 60 + 
               Integer.parseInt(time.substring(2));
    }
}
Explanation
Time Complexity: O(N log N)
- Sorting times for each employee dominates
- N is total number of access records
Space Complexity: O(N)
- Store all times for each employee
Key Differences from LeetCode 1604:
1.Input Format: 2D list instead of separate arrays
2.Time Format: "HHMM" (4 digits) instead of "HH:MM"
3.Output: List of names (same)
Key Points:
1.Time Conversion: Convert "HHMM" to minutes from midnight
2.Sliding Window: Check if any 3 consecutive accesses (when sorted) are within 60 minutes
3.Strict Inequality: Use < 60 not <= 60 for the window check
4.Early Termination: Break once we find a qualifying window for an employee
Example Walkthrough
Input:
access_times = [
    ["a","0549"],
    ["b","0457"],
    ["a","0532"],
    ["a","0548"],
    ["b","0450"],
    ["b","0521"],
    ["b","0510"]
]

Step 1: Group by name:
a: [0549 → 329, 0532 → 312, 0548 → 328] 
   → sorted: [312, 328, 329]
b: [0457 → 277, 0450 → 270, 0521 → 321, 0510 → 310]
   → sorted: [270, 277, 310, 321]

Step 2: Check each employee:
- a: 329 - 312 = 17 < 60 → High-access
- b: Check windows:
  * [270, 277, 310]: 310 - 270 = 40 < 60 → High-access
  * No need to check further

Output: ["a","b"]
Edge Cases
// Test Cases:
1. Exactly 3 accesses at same time:

   [["a","0000"],["a","0000"],["a","0000"]] → ["a"]

2. Accesses at 59, 119, 120 minutes:

   [["a","0059"],["a","0119"],["a","0120"]] → []
   // 120 - 59 = 61 > 60

3. Accesses that just make the cut:

   [["a","0100"],["a","0101"],["a","0159"]] → ["a"]
   // 159 - 100 = 59 < 60

4. More than 3 accesses:

   [["a","0100"],["a","0101"],["a","0102"],["a","0103"]] → ["a"]

5. Single employee with 2 accesses:

   [["a","0100"],["a","0101"]] → []
Comparison with LeetCode 1604
Aspect2933 (High-Access Employees)1604 (Alert Using Same Key-Card)Input FormatList of [name, time] pairsSeparate name and time arraysTime Format"HHMM" (4-digit string)"HH:MM" (5-character string)Core LogicSame sliding window checkSame sliding window checkOutputList of namesList of names
The first approach is the most straightforward and efficient. The key insight is to sort times for each employee and check if any three consecutive times have a span of less than 60 minutes.


Refer to
L1604.Alert Using Same Key-Card Three or More Times in a One Hour Period
