/**
 Refer to
 https://leetcode.com/problems/shortest-path-to-get-all-keys/
 We are given a 2-dimensional grid. "." is an empty cell, "#" is a wall, "@" is the starting point, ("a", "b", ...) 
 are keys, and ("A", "B", ...) are locks.
 
 We start at the starting point, and one move consists of walking one space in one of the 4 cardinal directions.  
 We cannot walk outside the grid, or walk into a wall.  If we walk over a key, we pick it up.  We can't walk over 
 a lock unless we have the corresponding key.
 
 For some 1 <= K <= 6, there is exactly one lowercase and one uppercase letter of the first K letters of the English 
 alphabet in the grid.  This means that there is exactly one key for each lock, and one lock for each key; and also 
 that the letters used to represent the keys and locks were chosen in the same order as the English alphabet.
 
 Return the lowest number of moves to acquire all keys.  If it's impossible, return -1.
 
Example 1:
Input: ["@.a.#","###.#","b.A.B"]
Output: 8

Example 2:
Input: ["@..aA","..B#.","....b"]
Output: 6

Note:
1 <= grid.length <= 30
1 <= grid[0].length <= 30
grid[i][j] contains only '.', '#', '@', 'a'-'f' and 'A'-'F'
The number of keys is in [1, 6].  Each key has a different letter and opens exactly one lock.
*/

// Solution 1: BFS + key set present as string and able to re-visit same cell after getting new key
// Refer to
// https://leetcode.com/problems/shortest-path-to-get-all-keys/discuss/146878/Java-BFS-Solution
// https://leetcode.com/problems/shortest-path-to-get-all-keys/discuss/146941/C%2B%2B-BFS-with-current-key-recorded-visited-map-(12ms)
class State {
    int x;
    int y;
    String keys;
    public State(int x, int y, String keys) {
        this.x = x;
        this.y = y;
        this.keys = keys;
    }
}

class Solution {	
	public int shortestPathAllKeys(String[] grid) {
        int[] dx = new int[] {0,0,1,-1};
        int[] dy = new int[] {-1,1,0,0};
        int m = grid.length;
        int n = grid[0].length();
        // Available keys in grid, e.g if only 'a', 'b' present in
        // grid, then available keys number is 2, if 'a', 'b', 'c'
        // present in grid, then available keys number is 3
        int keysNum = 0;
        Queue<State> q = new LinkedList<State>();
        Set<String> visited = new HashSet<String>();
        // Find initial '@' position in grid and assign corresponding
        // initial keys value as all 6 digits as 'x' since no keys
        // detect yet
        for(int i = 0; i < m; i++) {
            for(int j = 0; j < n; j++) {
                char c = grid[i].charAt(j);
                if(c == '@') {
                    q.offer(new State(i, j, "xxxxxx"));
                    // The combination is coordinates plus current key set string
                    visited.add(i + " " + j + " " + "xxxxxx");
                }
                if(c >= 'a' && c <= 'f') {
                    keysNum = Math.max(keysNum, c - 'a' + 1);
                }
            }
        }
        // Build final key based on all available keys in grid
        // and make up non-available keys with 'x'
        // Based on: For some 1 <= K <= 6, there is exactly one 
        // lowercase and one uppercase letter of the first K 
        // letters of the English alphabet in the grid --> 
        // (first K... means continuous keys) which means if 2 
        // keys found, then final keys as "ab" only, won't be "ac"
        int totalPossibleKeys = 6;
        String finalKeys = "";
        for(int i = 0; i < keysNum; i++) {
            char c = (char) ('a' + i);
            finalKeys += c;
        }
        for(int i = 0; i < totalPossibleKeys - keysNum; i++) {
            finalKeys += 'x';
        }
        int step = 0;
        while(!q.isEmpty()) {
            int size = q.size();
            for(int i = 0; i < size; i++) {
                State cur = q.poll();
                int x = cur.x;
                int y = cur.y;
                String curKeys = cur.keys;
                if(curKeys.equals(finalKeys)) {
                    return step;
                }
                for(int j = 0; j < 4; j++) {
                    int new_x = x + dx[j];
                    int new_y = y + dy[j];
                    String new_key = curKeys;
                    if(new_x >= 0 && new_x < m && new_y >= 0 && new_y < n) {
                        char c = grid[new_x].charAt(new_y);
                        String upperCaseCurKeys = new_key.toUpperCase();
                        int keyIndex = upperCaseCurKeys.indexOf("" + c);
                        // If encounter wall or currently not able to pass lock
                        // since no key available (maybe able in futrue when get
                        // key we are able to come back to pass the lock)
                        if(c == '#' || (c >= 'A' && c <= 'F' && keyIndex == -1)) {
                            continue;
                        }
                        // Find new key then update current key string (the string
                        // initially set as "xxxxxx" and keep update when find new key)
                        if(c >= 'a' && c <= 'f') {
                            int replaceIndex = c - 'a';
                            char[] chars = new_key.toCharArray();
                            chars[replaceIndex] = c;
                            new_key = new String(chars);
                        }
                        // If not visited before (not visited on coordinate or visited
                        // on same coordinate but different on key set since we may able
                        // to get new keys when go through other cells then we can come
                        // back to go through the lock)
                        if(!visited.contains(new_x + " " + new_y + " " + new_key)) {
                            visited.add(new_x + " " + new_y + " " + new_key);
                            q.offer(new State(new_x, new_y, new_key));
                        }
                    }
                }
            }
            step++;
        }
        return -1;
    }
}

// Solution 2: BFS + bitwise coding key set and able to re-visit same cell after getting new key
// Refer to
// https://leetcode.com/problems/shortest-path-to-get-all-keys/discuss/146878/Java-BFS-Solution
/**
1.Use Bit to represent the keys.
2.Use State to represent visited states.
class Solution {
    class State {
        int keys, i, j;
        State(int keys, int i, int j) {
            this.keys = keys;
            this.i = i;
            this.j = j;
        }
    }
    public int shortestPathAllKeys(String[] grid) {
        int x = -1, y = -1, m = grid.length, n = grid[0].length(), max = -1;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                char c = grid[i].charAt(j);
                if (c == '@') {
                    x = i;
                    y = j;
                }
                if (c >= 'a' && c <= 'f') {
                    max = Math.max(c - 'a' + 1, max);
                }
            }
        }
        State start = new State(0, x, y);
        Queue<State> q = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        visited.add(0 + " " + x + " " + y);
        q.offer(start);
        int[][] dirs = new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        int step = 0;
        while (!q.isEmpty()) {
            int size = q.size();
            while (size-- > 0) {
                State cur = q.poll();
                if (cur.keys == (1 << max) - 1) {
                    return step;
                }
                for (int[] dir : dirs) {
                    int i = cur.i + dir[0];
                    int j = cur.j + dir[1];
                    int keys = cur.keys;
                    if (i >= 0 && i < m && j >= 0 && j < n) {
                        char c = grid[i].charAt(j);
                        if (c == '#') {
                            continue;
                        }
                        if (c >= 'a' && c <= 'f') {
                            keys |= 1 << (c - 'a');
                        }
                        if (c >= 'A' && c <= 'F' && ((keys >> (c - 'A')) & 1) == 0) {
                            continue;
                        }
                        if (!visited.contains(keys + " " + i + " " + j)) {
                            visited.add(keys + " " + i + " " + j);
                            q.offer(new State(keys, i, j));
                        }
                    }
                }
            }
            step++;
        }
        return -1;
    }
}
*/
class State {
    int x;
    int y;
    int keys;
    public State(int x, int y, int keys) {
        this.x = x;
        this.y = y;
        this.keys = keys;
    }
}

class Solution {	
	public int shortestPathAllKeys(String[] grid) {
        int[] dx = new int[] {0,0,1,-1};
        int[] dy = new int[] {-1,1,0,0};
        int m = grid.length;
        int n = grid[0].length();
        // Available keys in grid, e.g if only 'a', 'b' present in
        // grid, then available keys number is 2, if 'a', 'b', 'c'
        // present in grid, then available keys number is 3
        int keysNum = 0;
        Queue<State> q = new LinkedList<State>();
        Set<String> visited = new HashSet<String>();
        // Find initial '@' position in grid and assign corresponding
        // initial keys value as all 6 digits as 'x' since no keys
        // detect yet
        for(int i = 0; i < m; i++) {
            for(int j = 0; j < n; j++) {
                char c = grid[i].charAt(j);
                if(c == '@') {
                    q.offer(new State(i, j, 0));
                    // The combination is coordinates plus current key set bit value
                    visited.add(i + " " + j + " " + 0);
                }
                if(c >= 'a' && c <= 'f') {
                    keysNum = Math.max(keysNum, c - 'a' + 1);
                }
            }
        }
        // Build final key based on all available keys in grid
        // and present as bitwise as 1 available key mapping
        // digital 1 in the position (from right to left)
        // Based on: For some 1 <= K <= 6, there is exactly one 
        // lowercase and one uppercase letter of the first K 
        // letters of the English alphabet in the grid --> 
        // (first K... means continuous keys) which means if 2 
        // keys found, then final keys as "ab" only, won't be "ac"
        int finalKeys = (1 << keysNum) - 1;
        int step = 0;
        while(!q.isEmpty()) {
            int size = q.size();
            for(int i = 0; i < size; i++) {
                State cur = q.poll();
                int x = cur.x;
                int y = cur.y;
                int curKeys = cur.keys;
                if(curKeys == finalKeys) {
                    return step;
                }
                for(int j = 0; j < 4; j++) {
                    int new_x = x + dx[j];
                    int new_y = y + dy[j];
                    int new_key = curKeys;
                    if(new_x >= 0 && new_x < m && new_y >= 0 && new_y < n) {
                        // If encounter wall or currently not able to pass lock
                        // since no key available (maybe able in futrue when get
                        // key we are able to come back to pass the lock)
                        char c = grid[new_x].charAt(new_y);
                        if(c == '#' || (c >= 'A' && c <= 'F' && ((new_key >> (c - 'A')) & 1) == 0)) {
                            continue;
                        }
                        // Find new key then update current key set bit value
                        if(c >= 'a' && c <= 'f') {
                            new_key |= 1 << (c - 'a');
                        }
                        // If not visited before (not visited on coordinate or visited
                        // on same coordinate but different on key set since we may able
                        // to get new keys when go through other cells then we can come
                        // back to go through the lock)
                        if(!visited.contains(new_x + " " + new_y + " " + new_key)) {
                            visited.add(new_x + " " + new_y + " " + new_key);
                            q.offer(new State(new_x, new_y, new_key));
                        }
                    }
                }
            }
            step++;
        }
        return -1;
    }
}
