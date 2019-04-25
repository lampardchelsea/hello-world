/**
 Refer to
 https://leetcode.com/problems/time-based-key-value-store/
 Create a timebased key-value store class TimeMap, that supports two operations.

1. set(string key, string value, int timestamp)

Stores the key and value, along with the given timestamp.
2. get(string key, int timestamp)

Returns a value such that set(key, value, timestamp_prev) was called previously, with timestamp_prev <= timestamp.
If there are multiple such values, it returns the one with the largest timestamp_prev.
If there are no values, it returns the empty string ("").
 

Example 1:
Input: 
inputs = ["TimeMap","set","get","get","set","get","get"], 
inputs = [[],["foo","bar",1],["foo",1],["foo",3],["foo","bar2",4],["foo",4],["foo",5]]
Output: [null,null,"bar","bar",null,"bar2","bar2"]

Explanation:   
TimeMap kv;   
kv.set("foo", "bar", 1); // store the key "foo" and value "bar" along with timestamp = 1   
kv.get("foo", 1);  // output "bar"   
kv.get("foo", 3); // output "bar" since there is no value corresponding to foo at timestamp 3 
                  //and timestamp 2, then the only value is at timestamp 1 ie "bar"   
kv.set("foo", "bar2", 4);   
kv.get("foo", 4); // output "bar2"   
kv.get("foo", 5); //output "bar2"   

Example 2:
Input: 
inputs = ["TimeMap","set","set","get","get","get","get","get"], 
inputs = [[],["love","high",10],["love","low",20],["love",5],["love",10],["love",15],["love",20],["love",25]]
Output: [null,null,null,"","high","high","low","low"]

Note:
All key/value strings are lowercase.
All key/value strings have length in the range [1, 100]
The timestamps for all TimeMap.set operations are strictly increasing.
1 <= timestamp <= 10^7
TimeMap.set and TimeMap.get functions will be called a total of 120000 times (combined) per test case.
*/
// Solution 1: TreeMap to sort out previous timestamp just before given timestamp
// Refer to
// https://leetcode.com/problems/time-based-key-value-store/solution/
/**
 Intuition and Algorithm
 In Java, we can use TreeMap.floorKey(timestamp) to find the largest timestamp smaller than the given timestamp
*/
class TimeMap {
    Map<String, TreeMap<Integer, String>> map;
        
    /** Initialize your data structure here. */
    public TimeMap() {
        map = new HashMap<String, TreeMap<Integer, String>>();
    }
    
    public void set(String key, String value, int timestamp) {
        if(!map.containsKey(key)) {
            map.put(key, new TreeMap<Integer, String>());
        }
        map.get(key).put(timestamp, value);
    }
    
    public String get(String key, int timestamp) {
        if(!map.containsKey(key)) {
            return "";
        }
        TreeMap<Integer, String> temp = map.get(key);
        Integer t = temp.floorKey(timestamp);
        return t != null ? temp.get(t) : "";
    }
}

/**
 * Your TimeMap object will be instantiated and called as such:
 * TimeMap obj = new TimeMap();
 * obj.set(key,value,timestamp);
 * String param_2 = obj.get(key,timestamp);
 */
 
 // Solution 2: Hashmap + Binary Search
 // Refer to
 // 
