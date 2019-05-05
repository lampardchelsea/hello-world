/**
 Refer to
 https://leetcode.com/problems/keys-and-rooms/
 There are N rooms and you start in room 0.  Each room has a distinct number in 0, 1, 2, ..., N-1, 
 and each room may have some keys to access the next room. 

Formally, each room i has a list of keys rooms[i], and each key rooms[i][j] is an integer in 
[0, 1, ..., N-1] where N = rooms.length.  A key rooms[i][j] = v opens the room with number v.

Initially, all the rooms start locked (except for room 0). 

You can walk back and forth between rooms freely.

Return true if and only if you can enter every room.

Example 1:
Input: [[1],[2],[3],[]]
Output: true
Explanation:  
We start in room 0, and pick up key 1.
We then go to room 1, and pick up key 2.
We then go to room 2, and pick up key 3.
We then go to room 3.  Since we were able to go to every room, we return true.

Example 2:
Input: [[1,3],[3,0,1],[2],[0]]
Output: false
Explanation: We can't enter the room with number 2.

Note:
1 <= rooms.length <= 1000
0 <= rooms[i].length <= 1000
The number of keys in all rooms combined is at most 3000.
*/
// Solution 1: DFS
// Refer to
// https://leetcode.com/problems/keys-and-rooms/discuss/135306/BFS-(9-lines-10ms)-and-DFS-(7-lines-18ms)-in-C%2B%2B-w-beginner-friendly-explanation
/**
 DFS:
When we enter a room, mark it as visited.
Then we put the keys in the current room to our unordered_set keys.
Check all the keys we have, if we haven't visited all corresponding room, go DFS.
If we have visited all rooms, number of visited rooms should be the same as number of rooms.

Possible followup: Why would you use BFS over DFS in this solution (except that DFS takes longer here)?
Ans: If input is too large, DFS might cause stack overflow.
*/
class Solution {
    Set<Integer> set = new HashSet<Integer>();
    public boolean canVisitAllRooms(List<List<Integer>> rooms) {
        helper(0, rooms);
        return set.size() == rooms.size();
    }
    
    private void helper(int roomId, List<List<Integer>> rooms) {
        set.add(roomId);
        List<Integer> keysInRoom = rooms.get(roomId);
        for(int key : keysInRoom) {
            // Check if you already enter the room (use this key before)
            // to avoid dead loop
            if(!set.contains(key)) {
                helper(key, rooms);
            }
        }
    }
}
