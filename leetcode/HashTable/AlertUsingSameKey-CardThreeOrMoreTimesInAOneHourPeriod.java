https://leetcode.com/problems/alert-using-same-key-card-three-or-more-times-in-a-one-hour-period/description/
LeetCode company workers use key-cards to unlock office doors. Each time a worker uses their key-card, the security system saves the worker's name and the time when it was used. The system emits an alert if any worker uses the key-card three or more times in a one-hour period.
You are given a list of strings keyName and keyTime where [keyName[i], keyTime[i]] corresponds to a person's name and the time when their key-card was used in a single day.
Access times are given in the 24-hour time format "HH:MM", such as "23:51" and "09:49".
Return a list of unique worker names who received an alert for frequent keycard use. Sort the names in ascending order alphabetically.
Notice that "10:00" - "11:00" is considered to be within a one-hour period, while "22:51" - "23:52" is not considered to be within a one-hour period.
 
Example 1:
Input: keyName = ["daniel","daniel","daniel","luis","luis","luis","luis"], keyTime = ["10:00","10:40","11:00","09:00","11:00","13:00","15:00"]
Output: ["daniel"]
Explanation: "daniel" used the keycard 3 times in a one-hour period ("10:00","10:40", "11:00").

Example 2:
Input: keyName = ["alice","alice","alice","bob","bob","bob","bob"], keyTime = ["12:01","12:00","18:00","21:00","21:20","21:30","23:00"]
Output: ["bob"]
Explanation: "bob" used the keycard 3 times in a one-hour period ("21:00","21:20", "21:30").
 
Constraints:
- 1 <= keyName.length, keyTime.length <= 105
- keyName.length == keyTime.length
- keyTime[i] is in the format "HH:MM".
- [keyName[i], keyTime[i]] is unique.
- 1 <= keyName[i].length <= 10
- keyName[i] contains only lowercase English letters.
--------------------------------------------------------------------------------
Attempt 1: 2026-02-02
Solution 1: Hash Table + Sorting (30 min)
class Solution {
    public List<String> alertNames(String[] keyName, String[] keyTime) {
        Map<String, List<Integer>> map = new HashMap<>();
        for(int i = 0; i < keyName.length; i++) {
            String person = keyName[i];
            int time = convert(keyTime[i]);
            map.putIfAbsent(person, new ArrayList<>());
            map.get(person).add(time);
        }
        List<String> result = new ArrayList<>();
        for(Map.Entry<String, List<Integer>> e : map.entrySet()) {
            String person = e.getKey();
            List<Integer> times = e.getValue();
            Collections.sort(times);
            // Check if any 3 accesses within 60 minutes using sliding window
            for(int i = 0; i < times.size() - 2; i++) {
                // Note: <= 60, not < 60
                // Test out by:
                // keyName = ["daniel","daniel","daniel","luis","luis","luis","luis"]
                // keyTime = ["10:00","10:40","11:00","09:00","11:00","13:00","15:00"]
                // Output = []
                // Expected = ["daniel"]
                if(times.get(i + 2) - times.get(i) <= 60) {
                    result.add(person);
                    // Stop checking for this employee if already add the name once
                    // Test out by:
                    // keyName = ["a","a","a","a","a","a","a","b","b","b","b","b","b","b","c","c","c","c","c","c","c","c","c"]
                    // keyTime = ["00:37","11:24","14:35","21:25","15:48","20:28","07:30","09:26","10:32","20:10","19:26","08:13","01:08","15:49","02:34","06:48","04:33","07:18","00:05","06:44","13:33","04:12","03:54"]
                    // Output = ["c","c"]
                    // Expected = ["c"]
                    break;
                }
            }
        }
        // Sort names alphabetically
        // Test out by:
        // keyName = ["leslie","leslie","leslie","clare","clare","clare","clare"]
        // keyTime = ["13:00","13:20","14:00","18:00","18:51","19:30","19:49"]
        // Output = ["leslie","clare"]
        // Expected = ["clare","leslie"]
        Collections.sort(result);
        return result;
    }

    private int convert(String time) {
        int hours = Integer.parseInt(time.substring(0, 2));
        int mins = Integer.parseInt(time.substring(3));
        return hours * 60 + mins;
    }
}

Time Complexity: O(N log N), N = number of access records. Dominated by sorting times per employee (worst case: one employee with all N records → O(N log N))
Space Complexity: O(N) for storing access times grouped by employee

Refer to Deepseek
Here's the Java solution for LeetCode 1604:
Problem Summary
Given lists of keycard access records (name and time), return a list of names (sorted) of employees who used their keycard 3 or more times within a one-hour period.
Solution
Approach 1: Sorting + Sliding Window
class Solution {
    public List<String> alertNames(String[] keyName, String[] keyTime) {
        Map<String, List<Integer>> map = new HashMap<>();
        
        // Group times by name
        for (int i = 0; i < keyName.length; i++) {
            String name = keyName[i];
            String time = keyTime[i];
            
            // Convert time to minutes
            int minutes = convertToMinutes(time);
            
            map.putIfAbsent(name, new ArrayList<>());
            map.get(name).add(minutes);
        }
        
        List<String> result = new ArrayList<>();
        
        // Check each employee
        for (Map.Entry<String, List<Integer>> entry : map.entrySet()) {
            String name = entry.getKey();
            List<Integer> times = entry.getValue();
            
            // Sort times
            Collections.sort(times);
            
            // Check if any 3 accesses within 60 minutes using sliding window
            for (int i = 2; i < times.size(); i++) {
                if (times.get(i) - times.get(i - 2) <= 60) {
                    result.add(name);
                    break; // No need to check further for this employee
                }
            }
        }
        
        // Sort names alphabetically
        Collections.sort(result);
        return result;
    }
    
    private int convertToMinutes(String time) {
        String[] parts = time.split(":");
        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);
        return hours * 60 + minutes;
    }
}
Approach 2: Using PriorityQueue (Stream-based)
class Solution {
    public List<String> alertNames(String[] keyName, String[] keyTime) {
        Map<String, PriorityQueue<Integer>> map = new HashMap<>();
        
        // Add all times to priority queues (min-heap)
        for (int i = 0; i < keyName.length; i++) {
            String name = keyName[i];
            String time = keyTime[i];
            
            int minutes = convertToMinutes(time);
            map.putIfAbsent(name, new PriorityQueue<>());
            map.get(name).offer(minutes);
        }
        
        List<String> result = new ArrayList<>();
        
        for (Map.Entry<String, PriorityQueue<Integer>> entry : map.entrySet()) {
            String name = entry.getKey();
            PriorityQueue<Integer> pq = entry.getValue();
            
            // Convert priority queue to list (already sorted)
            List<Integer> times = new ArrayList<>();
            while (!pq.isEmpty()) {
                times.add(pq.poll());
            }
            
            // Check for 3 accesses within 60 minutes
            for (int i = 2; i < times.size(); i++) {
                if (times.get(i) - times.get(i - 2) <= 60) {
                    result.add(name);
                    break;
                }
            }
        }
        
        Collections.sort(result);
        return result;
    }
    
    private int convertToMinutes(String time) {
        return Integer.parseInt(time.substring(0, 2)) * 60 + 
               Integer.parseInt(time.substring(3));
    }
}
Approach 3: Optimized with Array and Bucket Sort
class Solution {
    public List<String> alertNames(String[] keyName, String[] keyTime) {
        Map<String, List<Integer>> map = new HashMap<>();
        
        // Group times by name
        for (int i = 0; i < keyName.length; i++) {
            String name = keyName[i];
            String time = keyTime[i];
            
            // Convert HH:MM to minutes
            int hours = (time.charAt(0) - '0') * 10 + (time.charAt(1) - '0');
            int minutes = (time.charAt(3) - '0') * 10 + (time.charAt(4) - '0');
            int totalMinutes = hours * 60 + minutes;
            
            map.computeIfAbsent(name, k -> new ArrayList<>()).add(totalMinutes);
        }
        
        List<String> result = new ArrayList<>();
        
        for (Map.Entry<String, List<Integer>> entry : map.entrySet()) {
            String name = entry.getKey();
            List<Integer> times = entry.getValue();
            
            // Only check if employee has at least 3 accesses
            if (times.size() >= 3) {
                Collections.sort(times);
                
                // Use sliding window of size 3
                for (int i = 0; i <= times.size() - 3; i++) {
                    if (times.get(i + 2) - times.get(i) <= 60) {
                        result.add(name);
                        break;
                    }
                }
            }
        }
        
        Collections.sort(result);
        return result;
    }
}
Approach 4: One-pass with Early Detection
class Solution {
    public List<String> alertNames(String[] keyName, String[] keyTime) {
        // Sort by time first
        int n = keyName.length;
        Integer[] indices = new Integer[n];
        for (int i = 0; i < n; i++) indices[i] = i;
        
        Arrays.sort(indices, (a, b) -> {
            return convertTime(keyTime[a]) - convertTime(keyTime[b]);
        });
        
        Map<String, LinkedList<Integer>> map = new HashMap<>();
        Set<String> alerted = new HashSet<>();
        
        for (int idx : indices) {
            String name = keyName[idx];
            int time = convertTime(keyTime[idx]);
            
            // If already alerted, skip
            if (alerted.contains(name)) continue;
            
            map.putIfAbsent(name, new LinkedList<>());
            LinkedList<Integer> times = map.get(name);
            
            // Remove times that are more than 60 minutes before current time
            while (!times.isEmpty() && time - times.peekFirst() > 60) {
                times.pollFirst();
            }
            
            // Add current time
            times.addLast(time);
            
            // Check if we have 3 or more accesses within 60 minutes
            if (times.size() >= 3) {
                alerted.add(name);
            }
        }
        
        List<String> result = new ArrayList<>(alerted);
        Collections.sort(result);
        return result;
    }
    
    private int convertTime(String time) {
        return ((time.charAt(0) - '0') * 10 + (time.charAt(1) - '0')) * 60 +
               ((time.charAt(3) - '0') * 10 + (time.charAt(4) - '0'));
    }
}
Explanation
Time Complexity: O(N log N)
- Sorting names or times dominates
- N is total number of access records
Space Complexity: O(N)
- Store all times for each employee
Key Points:
1.Time Conversion: Convert "HH:MM" to minutes for easier comparison
2.Sliding Window: Check if any 3 consecutive accesses (when sorted) are within 60 minutes
3.Grouping: Group access times by employee name
4.Sorting: Sort times for each employee, then sort result names alphabetically
Example Walkthrough
Input:
keyName = ["daniel","daniel","daniel","luis","luis","luis","luis"]
keyTime = ["10:00","10:40","11:00","09:00","11:00","13:00","15:00"]

Step 1: Group by name:
daniel: [600, 640, 660]  (10:00, 10:40, 11:00)
luis:   [540, 660, 780, 900]  (09:00, 11:00, 13:00, 15:00)

Step 2: Check each employee:
- daniel: 660 - 600 = 60 ≤ 60 → Alert!
- luis: Check windows:
  * 540, 660, 780: 780 - 540 = 240 > 60
  * 660, 780, 900: 900 - 660 = 240 > 60 → No alert

Output: ["daniel"]
Edge Cases
// Test Cases:
1. All accesses within one hour:

   keyName = ["a","a","a"]
   keyTime = ["00:00","00:30","01:00"] → ["a"]

2. Accesses exactly at boundaries:

   keyName = ["a","a","a"]
   keyTime = ["00:00","00:59","01:00"] → ["a"] (01:00 - 00:00 = 60)

3. Accesses spread out:

   keyName = ["a","a","a"]
   keyTime = ["00:00","00:31","01:01"] → [] (01:01 - 00:00 = 61 > 60)

4. Multiple employees:

   keyName = ["a","a","a","b","b","b","c","c","c"]
   keyTime = ["00:00","00:20","00:40","01:00","01:20","01:40","02:00","02:20","02:40"]
   → ["a","b","c"]

5. Same minute:

   keyName = ["a","a","a"]
   keyTime = ["00:00","00:00","00:00"] → ["a"]
The first approach (sorting + sliding window) is the most straightforward and efficient. The key insight is that we only need to check consecutive accesses in sorted time order, not all possible combinations.

Q1: If the 1 hour definition expands to concatenation hours between two days, for example, someone used the keycard 3 times after 11:30 pm on day 1 and before 12:30 am on day 2, what should we do ?
Great question! This is actually an important edge case that many real-world implementations need to handle. The original LeetCode problem doesn't explicitly state whether times can cross midnight, but based on typical real-world scenarios, we should handle this case.
The Cross-Midnight Problem
When time spans can cross midnight (e.g., 23:30 to 00:30), we have two approaches:
Approach 1: Handle 24-hour cycles without date (Simpler but Limited)
If we don't have date information and only have HH:MM format, we can handle this by:
1.Converting all times to minutes in a continuous timeline
2.When comparing times, consider that times from the previous day might be close to times from the next day
Here's a modified solution that handles the cross-midnight case:
class Solution {
    public List<String> alertNames(String[] keyName, String[] keyTime) {
        Map<String, List<Integer>> map = new HashMap<>();
        
        // Group times by name
        for (int i = 0; i < keyName.length; i++) {
            String name = keyName[i];
            String time = keyTime[i];
            
            int minutes = convertToMinutes(time);
            map.putIfAbsent(name, new ArrayList<>());
            map.get(name).add(minutes);
        }
        
        List<String> result = new ArrayList<>();
        
        // Check each employee
        for (Map.Entry<String, List<Integer>> entry : map.entrySet()) {
            String name = entry.getKey();
            List<Integer> times = entry.getValue();
            
            // Sort times
            Collections.sort(times);
            
            // Check if any 3 accesses within 60 minutes using sliding window
            // Handle cross-midnight by duplicating times
            List<Integer> extendedTimes = new ArrayList<>(times);
            
            // For cross-midnight handling: add times from "next day" by adding 1440 minutes
            for (int time : times) {
                if (time < 60) { // Times near midnight (00:00 to 01:00)
                    extendedTimes.add(time + 1440); // Add as next day's time
                }
            }
            
            // Sort again including next day times
            Collections.sort(extendedTimes);
            
            for (int i = 2; i < extendedTimes.size(); i++) {
                if (extendedTimes.get(i) - extendedTimes.get(i - 2) <= 60) {
                    result.add(name);
                    break;
                }
            }
        }
        
        Collections.sort(result);
        return result;
    }
    
    private int convertToMinutes(String time) {
        String[] parts = time.split(":");
        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);
        return hours * 60 + minutes;
    }
}
Approach 2: Better Solution - Use Actual Timestamps (If Date is Available)
If we had date information, the solution would be:
class Solution {
    public List<String> alertNames(String[] keyName, String[] keyTime) {
        // Assuming keyTime format: "YYYY-MM-DD HH:MM"
        Map<String, List<Long>> map = new HashMap<>();
        
        for (int i = 0; i < keyName.length; i++) {
            String name = keyName[i];
            String datetime = keyTime[i];
            
            long timestamp = convertToTimestamp(datetime);
            map.computeIfAbsent(name, k -> new ArrayList<>()).add(timestamp);
        }
        
        List<String> result = new ArrayList<>();
        
        for (Map.Entry<String, List<Long>> entry : map.entrySet()) {
            List<Long> times = entry.getValue();
            Collections.sort(times);
            
            for (int i = 2; i < times.size(); i++) {
                // Check if within 60 minutes (60 * 60 * 1000 milliseconds)
                if (times.get(i) - times.get(i - 2) <= 60 * 60 * 1000) {
                    result.add(entry.getKey());
                    break;
                }
            }
        }
        
        Collections.sort(result);
        return result;
    }
    
    private long convertToTimestamp(String datetime) {
        // Parse datetime string to timestamp
        // Format: "YYYY-MM-DD HH:MM"
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            return sdf.parse(datetime).getTime();
        } catch (ParseException e) {
            throw new RuntimeException("Invalid datetime format");
        }
    }
}
Approach 3: Most Robust - Handle Circular Time Without Date
This approach handles the circular nature of time by considering all possible windows:
class Solution {
    public List<String> alertNames(String[] keyName, String[] keyTime) {
        Map<String, List<Integer>> map = new HashMap<>();
        
        for (int i = 0; i < keyName.length; i++) {
            String name = keyName[i];
            int minutes = convertToMinutes(keyTime[i]);
            map.computeIfAbsent(name, k -> new ArrayList<>()).add(minutes);
        }
        
        List<String> result = new ArrayList<>();
        
        for (Map.Entry<String, List<Integer>> entry : map.entrySet()) {
            List<Integer> times = entry.getValue();
            if (times.size() < 3) continue;
            
            Collections.sort(times);
            
            // Check normal windows
            for (int i = 2; i < times.size(); i++) {
                if (times.get(i) - times.get(i - 2) <= 60) {
                    result.add(entry.getKey());
                    break;
                }
            }
            
            // Check cross-midnight windows
            if (!result.contains(entry.getKey())) {
                // For times near midnight, check with wrapped-around times
                List<Integer> wrappedTimes = new ArrayList<>();
                for (int time : times) {
                    if (time < 60) { // Near midnight of next day
                        wrappedTimes.add(time + 1440);
                    }
                }
                
                // Also include early morning times (next day perspective)
                for (int time : times) {
                    if (time >= 1380) { // 23:00 or later
                        wrappedTimes.add(time);
                    }
                }
                
                Collections.sort(wrappedTimes);
                
                for (int i = 2; i < wrappedTimes.size(); i++) {
                    if (wrappedTimes.get(i) - wrappedTimes.get(i - 2) <= 60) {
                        result.add(entry.getKey());
                        break;
                    }
                }
            }
        }
        
        Collections.sort(result);
        return result;
    }
    
    private int convertToMinutes(String time) {
        return Integer.parseInt(time.substring(0, 2)) * 60 + 
               Integer.parseInt(time.substring(3));
    }
}
Example of Cross-Midnight Alert
Times: ["23:45", "23:55", "00:05"]
Minutes: [1425, 1435, 5]

Without cross-midnight handling:
- 5 - 1425 = -1420 (abs 1420) > 60 ❌ No alert

With cross-midnight handling:
- Treat "00:05" as 1445 minutes (next day)
- Check windows: 1425, 1435, 1445
- 1445 - 1425 = 20 ≤ 60 ✅ Alert!
Key Insights for Cross-Midnight:
1.The Challenge: When times cross midnight (e.g., 23:45, 23:55, 00:05), the minutes-based approach fails because 00:05 appears "earlier" than 23:45 when sorted.
2.Solutions:
- Without date: Duplicate early morning times by adding 1440 minutes (one day) to handle them as if they're from the next day
- With date: Use actual timestamps with date information
- Circular buffer: Treat time as circular and check windows that wrap around midnight
3.Real-World Implication:
In security systems, a one-hour period from 11:30 PM to 12:30 AM should indeed be considered continuous. Most real implementations would use timestamp with date to avoid this ambiguity.
4.LeetCode Context:
The original problem likely doesn't test this edge case extensively, but in interviews, discussing this nuance shows deeper understanding of time-based problems.


Refer to
L2933.High-Access Employees (Ref.L1604)
