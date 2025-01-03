/**
 Refer to
 https://leetcode.com/problems/open-the-lock/
 You have a lock in front of you with 4 circular wheels. Each wheel has 10 slots: '0', '1', '2', '3', '4', '5', 
 '6', '7', '8', '9'. The wheels can rotate freely and wrap around: for example we can turn '9' to be '0', or 
 '0' to be '9'. Each move consists of turning one wheel one slot.

The lock initially starts at '0000', a string representing the state of the 4 wheels.

You are given a list of deadends dead ends, meaning if the lock displays any of these codes, the wheels of the 
lock will stop turning and you will be unable to open it.

Given a target representing the value of the wheels that will unlock the lock, return the minimum total number 
of turns required to open the lock, or -1 if it is impossible.

Example 1:
Input: deadends = ["0201","0101","0102","1212","2002"], target = "0202"
Output: 6
Explanation:
A sequence of valid moves would be "0000" -> "1000" -> "1100" -> "1200" -> "1201" -> "1202" -> "0202".
Note that a sequence like "0000" -> "0001" -> "0002" -> "0102" -> "0202" would be invalid,
because the wheels of the lock become stuck after the display becomes the dead end "0102".

Example 2:
Input: deadends = ["8888"], target = "0009"
Output: 1
Explanation:
We can turn the last wheel in reverse to move from "0000" -> "0009".

Example 3:
Input: deadends = ["8887","8889","8878","8898","8788","8988","7888","9888"], target = "8888"
Output: -1
Explanation:
We can't reach the target without getting stuck.

Example 4:
Input: deadends = ["0000"], target = "8888"
Output: -1
 
Constraints:
1 <= deadends.length <= 500
deadends[i].length == 4
target.length == 4
target will not be in the list deadends.
target and deadends[i] consist of digits only.
*/

// Solution 1: BFS must with visited set to block infinite loop
// Refer to
// https://leetcode.com/problems/open-the-lock/discuss/110237/Regular-java-BFS-solution-and-2-end-BFS-solution-with-improvement
class Solution {
    public int openLock(String[] deadends, String target) {
        int result = 0;
        Set<String> deadend_set = new HashSet<String>();
        for(String deadend : deadends) {
            deadend_set.add(deadend);
        }
        // Test out by
        // deadends = ["0000"], target = "8888"
        if(deadend_set.contains("0000")) {
            return -1;
        }
        // Test out by
        // deadends = ["0201","0101","0102","1212","2002"], target = "0000"
        if(target.equals("0000")) {
            return 0;
        }
        // Must have visited to remove infinite loop when encounter already visited number
        Set<String> visited = new HashSet<>();
        visited.add("0000");
        Queue<String> q = new LinkedList<String>();
        q.offer("0000");
        while(!q.isEmpty()) {
            result++;
            int size = q.size();
            for(int i = 0; i < size; i++) {
                List<String> next = new ArrayList<String>();
                String curr = q.poll();
                char[] chars = curr.toCharArray();
                for(int j = 0; j < 4; j++) {
                    char c = chars[j];
                    int add_one = ((c - '0') + 1) % 10;
                    chars[j] = (char)(add_one + '0');
                    String add_one_string = new String(chars);
                    int minus_one = ((c - '0') + 9) % 10;
                    chars[j] = (char)(minus_one + '0');
                    String minus_one_string = new String(chars);
                    if(add_one_string.equals(target) || minus_one_string.equals(target)) {
                        return result;
                    } else {
                        if(!deadend_set.contains(add_one_string) && !visited.contains(add_one_string)) {
                            next.add(add_one_string);
                            visited.add(add_one_string);
                        }
                        if(!deadend_set.contains(minus_one_string) && !visited.contains(minus_one_string)) {
                            next.add(minus_one_string);
                            visited.add(minus_one_string);
                        }
                    }
                    // Must reset chars based on original String in case each round only change 1 digit out of 4
                    // from original chars, and should not based on previous changed chars
                    chars = curr.toCharArray();
                }
                for(String s : next) {
                    q.offer(s);
                }
            }
        }
        return -1;
    }
}

// Solution 2: Bi-end BFS
// Refer to
// https://leetcode.com/problems/open-the-lock/discuss/110237/Regular-java-BFS-solution-and-2-end-BFS-solution-with-improvement
class Solution {
    public int openLock(String[] deadends, String target) {
        Set<String> begin = new HashSet<>();
        Set<String> end = new HashSet<>();
        Set<String> deads = new HashSet<>(Arrays.asList(deadends));
        begin.add("0000");
        end.add(target);
        int level = 0;
        Set<String> temp;
        while(!begin.isEmpty() && !end.isEmpty()) {
            if (begin.size() > end.size()) {
                temp = begin;
                begin = end;
                end = temp;
            }
            temp = new HashSet<>();
            for(String s : begin) {
                if(end.contains(s)) return level;
                if(deads.contains(s)) continue;
                deads.add(s);
                StringBuilder sb = new StringBuilder(s);
                for(int i = 0; i < 4; i ++) {
                    char c = sb.charAt(i);
                    String s1 = sb.substring(0, i) + (c == '9' ? 0 : c - '0' + 1) + sb.substring(i + 1);
                    String s2 = sb.substring(0, i) + (c == '0' ? 9 : c - '0' - 1) + sb.substring(i + 1);
                    if(!deads.contains(s1))
                        temp.add(s1);
                    if(!deads.contains(s2))
                        temp.add(s2);
                }
            }
            level ++;
            begin = temp;
        }
        return -1;
    }
}
