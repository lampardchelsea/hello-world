/**
Refer to
https://leetcode.com/problems/destination-city/
You are given the array paths, where paths[i] = [cityAi, cityBi] means there exists a direct path going from cityAi to cityBi. 
Return the destination city, that is, the city without any path outgoing to another city.

It is guaranteed that the graph of paths forms a line without any loop, therefore, there will be exactly one destination city.

Example 1:
Input: paths = [["London","New York"],["New York","Lima"],["Lima","Sao Paulo"]]
Output: "Sao Paulo" 
Explanation: Starting at "London" city you will reach "Sao Paulo" city which is the destination city. 
Your trip consist of: "London" -> "New York" -> "Lima" -> "Sao Paulo".

Example 2:
Input: paths = [["B","C"],["D","B"],["C","A"]]
Output: "A"
Explanation: All possible trips are: 
"D" -> "B" -> "C" -> "A". 
"B" -> "C" -> "A". 
"C" -> "A". 
"A". 
Clearly the destination city is "A".

Example 3:
Input: paths = [["A","Z"]]
Output: "Z"

Constraints:
1 <= paths.length <= 100
paths[i].length == 2
1 <= cityAi.length, cityBi.length <= 10
cityAi != cityBi
All strings consist of lowercase and uppercase English letters and the space character.
*/
class Solution {
    public String destCity(List<List<String>> paths) {
        Map<String, List<Integer>> map = new HashMap<String, List<Integer>>();
        for(List<String> path : paths) {
            if(!map.containsKey(path.get(0))) {
                map.put(path.get(0), new ArrayList<Integer>());
            }
            map.get(path.get(0)).add(0);
            if(!map.containsKey(path.get(1))) {
                map.put(path.get(1), new ArrayList<Integer>());
            }
            map.get(path.get(1)).add(1);
        }
        for(Map.Entry<String, List<Integer>> entry : map.entrySet()) {
            if(entry.getValue().size() == 1 && entry.getValue().get(0) == 1) {
                return entry.getKey();
            }
        }
        return "";
    }
}

// Better promotion by set used only
// Refer to
// https://leetcode.com/problems/destination-city/discuss/609868/Java-tricky-4-lines-using-one-set
/**
[
["London","New York"]
["New York","Lima"]
["Lima","Sao Paulo"]
]

Obeservation : destination city will always be the right one

we transverse the input for two times
first time, we add all the cities on the right side to a set
second time, we check if the city occurs on the left side, if so, remove it from the set
result will be the city left in the set

class Solution {
    public String destCity(List<List<String>> paths) {
        Set<String> set= new HashSet<>();
        for (List<String> l: paths) set.add(l.get(1));
        for (List<String> l: paths) set.remove(l.get(0));
        return set.iterator().next();
    }
}
*/
class Solution {
    public String destCity(List<List<String>> paths) {
        Set<String> set = new HashSet<String>();
        for(List<String> path : paths) {
            set.add(path.get(1));
        }
        for(List<String> path : paths) {
            set.remove(path.get(0));
        }
        return set.iterator().next();
    }
}
