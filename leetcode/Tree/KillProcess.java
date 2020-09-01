/**
Refer to
https://github.com/grandyang/leetcode/issues/582
Given n processes, each process has a unique PID (process id) and its PPID (parent process id).

Each process only has one parent process, but may have one or more children processes. This is just like a tree structure. 
Only one process has PPID that is 0, which means this process has no parent process. All the PIDs will be distinct positive integers.

We use two list of integers to represent a list of processes, where the first list contains PID for each process 
and the second list contains the corresponding PPID.

Now given the two lists, and a PID representing a process you want to kill, return a list of PIDs of processes that 
will be killed in the end. You should assume that when a process is killed, all its children processes will be killed. 
No order is required for the final answer.

Example 1:
Input: 
pid =  [1, 3, 10, 5]
ppid = [3, 0, 5, 3]
kill = 5
Output: [5,10]
Explanation: 
           3
         /   \
        1     5
             /
            10
Kill 5 will also kill 10.
 
Note:
The given kill id is guaranteed to be one of the given PIDs.
n >= 1.
*/

// Solution 1: DFS Recurisve + HashMap
// Refer to
// https://massivealgorithms.blogspot.com/2017/05/leetcode-582-kill-process.html
// https://webcache.googleusercontent.com/search?q=cache:zPR9W0ea2Y0J:https://leetcode.com/articles/kill-process/+&cd=1&hl=en&ct=clnk&gl=us
/**
 Algorithm
 Instead of making the tree structure, we can directly make use of a data structure which stores a particular process 
 value and the list of its direct children. For this, in the current implementation, we make use of a hashmap map, 
 which stores the data in the form parent:[listofallitsdirectchildren].
 Thus, now, by traversing just once over the ppid array, and adding the corresponding pid values to the children 
 list at the same time, we can obtain a better structure storing the parent-children relationship.
 Again, similar to the previous approach, now we can add the process to be killed to the return list, and keep on adding 
 its children to the return list in a recursive manner by obtaining the child information from the structure created previously.

 Complexity Analysis
 Time complexity : O(n). We need to traverse over the ppid array of size n once. The getAllChildren function also 
                         takes atmost n time, since no node can be a child of two nodes.
 Space complexity : O(n). map of size n is used.
*/
class Solution {
    public List < Integer > killProcess(List < Integer > pid, List < Integer > ppid, int kill) {
        // Build ppid-pid key value pair relationship, ppid(> 0) is the unique key,
        // it may relate to one or multiple pid(s)
        Map < Integer, List < Integer >> map = new HashMap < Integer, List < Integer >> ();
        for (int i = 0; i < ppid.size(); i++) {
            if (ppid.get(i) > 0) {
                List < Integer > list = map.getOrDefault(ppid.get(i), new ArrayList < Integer > ());
                list.add(pid.get(i));
                map.put(ppid.get(i), list);
            }
        }
        List < Integer > result = new ArrayList < Integer > ();
        result.add(kill);
        helper(map, result, kill);
        return result;
    }

    private void helper(Map < Integer, List < Integer >> map, List < Integer > result, int kill) {
        // Terminate condition is map not contains 'kill' number as ppid key 
        // in ppid-pid key value pair
        if (map.containsKey(kill)) {
            List < Integer > pids = map.get(kill);
            for (int pid: pids) {
                result.add(pid);
                helper(map, result, pid);
            }
        }
    }
}

// Solution 2: BFS + HashMap
// Refer to
// https://webcache.googleusercontent.com/search?q=cache:zPR9W0ea2Y0J:https://leetcode.com/articles/kill-process/+&cd=1&hl=en&ct=clnk&gl=us
/**
 Algorithm
 We can also make use of Breadth First Search to obtain all the children(direct + indirect) of a particular node, once the data structure 
 of the form (process:[listofallitsdirectchildren] has been obtained. The process of obtaining the data structure is the same as in the 
 previous approach.
 In order to obtain all the child processes to be killed for a particular parent chosen to be killed, we can make use of Breadth First 
 Search. For this, we add the node to be killed to a queue. Then, we remove an element from the front of the queue and add it 
 to the return list. Further, for every element removed from the front of the queue, we add all its direct children(obtained from the 
 data structure created) to the end of the queue. We keep on doing so till the queue becomes empty.
 
 Complexity Analysis
 Time complexity : O(n). We need to traverse over the ppid array of size nn once. Also, atmost nn additions/removals are done from the queue.
 Space complexity : O(n). mapmap of size n is used.
*/
class Solution {
    public List < Integer > killProcess(List < Integer > pid, List < Integer > ppid, int kill) {
        // Build ppid-pid key value pair relationship, ppid(> 0) is the unique key,
        // it may relate to one or multiple pid(s)
        Map < Integer, List < Integer >> map = new HashMap < Integer, List < Integer >> ();
        for (int i = 0; i < ppid.size(); i++) {
            if (ppid.get(i) > 0) {
                List < Integer > list = map.getOrDefault(ppid.get(i), new ArrayList < Integer > ());
                list.add(pid.get(i));
                map.put(ppid.get(i), list);
            }
        }
        List < Integer > result = new ArrayList < Integer > ();
        Queue < Integer > q = new LinkedList < Integer > ();
        q.offer(kill);
        while (!q.isEmpty()) {
            int cur_ppid = q.poll();
            result.add(cur_ppid);
            if (map.containsKey(cur_ppid)) {
                for (int cur_pid: map.get(cur_ppid)) {
                    q.offer(cur_pid);
                }
            }
        }
        return result;
    }
}

// Solution 3:
