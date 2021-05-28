/**
Refer to
https://leetcode.com/problems/alert-using-same-key-card-three-or-more-times-in-a-one-hour-period/
LeetCode company workers use key-cards to unlock office doors. Each time a worker uses their key-card, 
the security system saves the worker's name and the time when it was used. The system emits an alert 
if any worker uses the key-card three or more times in a one-hour period.

You are given a list of strings keyName and keyTime where [keyName[i], keyTime[i]] corresponds to a 
person's name and the time when their key-card was used in a single day.

Access times are given in the 24-hour time format "HH:MM", such as "23:51" and "09:49".

Return a list of unique worker names who received an alert for frequent keycard use. Sort the names 
in ascending order alphabetically.

Notice that "10:00" - "11:00" is considered to be within a one-hour period, while "22:51" - "23:52" 
is not considered to be within a one-hour period.

Example 1:
Input: keyName = ["daniel","daniel","daniel","luis","luis","luis","luis"], keyTime = ["10:00","10:40","11:00","09:00","11:00","13:00","15:00"]
Output: ["daniel"]
Explanation: "daniel" used the keycard 3 times in a one-hour period ("10:00","10:40", "11:00").

Example 2:
Input: keyName = ["alice","alice","alice","bob","bob","bob","bob"], keyTime = ["12:01","12:00","18:00","21:00","21:20","21:30","23:00"]
Output: ["bob"]
Explanation: "bob" used the keycard 3 times in a one-hour period ("21:00","21:20", "21:30").

Example 3:
Input: keyName = ["john","john","john"], keyTime = ["23:58","23:59","00:01"]
Output: []

Example 4:
Input: keyName = ["leslie","leslie","leslie","clare","clare","clare","clare"], keyTime = ["13:00","13:20","14:00","18:00","18:51","19:30","19:49"]
Output: ["clare","leslie"]

Constraints:
1 <= keyName.length, keyTime.length <= 105
keyName.length == keyTime.length
keyTime[i] is in the format "HH:MM".
[keyName[i], keyTime[i]] is unique.
1 <= keyName[i].length <= 10
keyName[i] contains only lowercase English letters.
*/

// Solution 1: HashMap/dict + Sliding scan 3-consecutive-elements window
// Refer to
// https://leetcode.com/problems/alert-using-same-key-card-three-or-more-times-in-a-one-hour-period/discuss/876864/JavaPython-3-HashMapdict-%2B-Sliding-Window.
/**
Method 1: Scan 3-consecutive-elements window

    public List<String> alertNames(String[] keyName, String[] keyTime) {
        Map<String, TreeSet<Integer>> nameToTime = new HashMap<>();
        for (int i = 0; i < keyName.length; ++i) {
            int time = Integer.parseInt(keyTime[i].substring(0, 2)) * 60 + Integer.parseInt(keyTime[i].substring(3));
            nameToTime.computeIfAbsent(keyName[i], s -> new TreeSet<>()).add(time);
        }
        TreeSet<String> names = new TreeSet<>();
        for (Map.Entry<String, TreeSet<Integer>> e : nameToTime.entrySet()) {
            List<Integer> list = new ArrayList<>(e.getValue());
            for (int i = 2; i < list.size(); ++i) {
                if (list.get(i) - list.get(i - 2) <= 60 ) {
                    names.add(e.getKey());
                    break;
                }
            }
        }
        return new ArrayList<>(names);
    }
*/

// https://leetcode.com/problems/alert-using-same-key-card-three-or-more-times-in-a-one-hour-period/discuss/877222/Java-Simple-Hash-ArrayList
/**
class Solution {
    public List<String> alertNames(String[] keyName, String[] keyTime) {
        Map<String, List<Integer>> map = new HashMap<>();
        List<String> res = new ArrayList<>();
        for (int i = 0; i < keyName.length; i++) { // cache all visits for same person.
            String k = keyName[i];
            map.computeIfAbsent(k, g -> new ArrayList<>());
            map.get(k).add(getTime(keyTime[i]));
        }
        
        for (String k : map.keySet()) {
            List<Integer> l = map.get(k);
            Collections.sort(l);  // sort to find the connective checkins
            for (int i = 2; i < l.size(); i++)
                if (l.get(i) - l.get(i - 2) <= 60) {  // connective 3 within 60 mins.
                    res.add(k);
                    break;
                }
        }
        
        Collections.sort(res);
        return res;
    }
    
    private int getTime(String t) {  // transfer stirng to relative mins.
        String[] ss = t.split(":");
        return Integer.parseInt(ss[1]) + 60 * Integer.parseInt(ss[0]);
    }
}
*/

class Solution {
    public List<String> alertNames(String[] keyName, String[] keyTime) {
        // {name, list<timestamp>}
        Map<String, List<Integer>> map = new HashMap<String, List<Integer>>();
        for(int i = 0; i < keyName.length; i++) {
            String key = keyName[i];
            int time = convert(keyTime[i]);
            map.putIfAbsent(key, new ArrayList<Integer>());
            map.get(key).add(time);
        }
        List<String> result = new ArrayList<String>();
        for(String str : map.keySet()) {
            List<Integer> list = map.get(str);
            Collections.sort(list);
            // Sliding scan 3-consecutive-elements window 
            for(int i = 2; i < list.size(); i++) {
                if(list.get(i) - list.get(i - 2) <= 60) {
                    result.add(str);
                    break;
                }
            }
        }
        Collections.sort(result);
        return result;
    }
    
    private int convert(String time) {
        String[] str = time.split(":");
        String hour = str[0];
        String min = str[1];
        return Integer.valueOf(hour) * 60 + Integer.valueOf(min);
    } 
}
