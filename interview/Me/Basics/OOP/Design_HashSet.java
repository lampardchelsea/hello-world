/**
 Refer to
 https://leetcode.com/problems/design-hashset/
 Design a HashSet without using any built-in hash table libraries.

To be specific, your design should include these functions:
add(value): Insert a value into the HashSet. 
contains(value) : Return whether the value exists in the HashSet or not.
remove(value): Remove a value in the HashSet. If the value does not exist in the HashSet, do nothing.

Example:
MyHashSet hashSet = new MyHashSet();
hashSet.add(1);         
hashSet.add(2);         
hashSet.contains(1);    // returns true
hashSet.contains(3);    // returns false (not found)
hashSet.add(2);          
hashSet.contains(2);    // returns true
hashSet.remove(2);          
hashSet.contains(2);    // returns false (already removed)

Note:
All values will be in the range of [0, 1000000].
The number of operations will be in the range of [1, 10000].
Please do not use the built-in HashSet library.
*/
// Solution 1: Cheating with 2D matrix boolean array
// Refer to: Beats 100% Real Java Solution (Not boolean array)
// https://leetcode.com/problems/design-hashset/discuss/143434/Beats-100-Real-Java-Solution-(Not-boolean-array)
// itemsPerBucket is set to 1001 to deal with the edge case when the key is 0 even though the description specify 
// the number value ranges from 1-100k. This will occupy 1000 additional booleans in memory.
class MyHashSet {
    private int buckets = 1000;
    private int itemsPerBucket = 1001;
    private boolean[][] table;
    
    /** Initialize your data structure here. */
    public MyHashSet() {
        table = new boolean[buckets][];
    }

    public int hash(int key) {
        return key % buckets;
    }

    public int pos(int key) {
        return key / buckets;
    }
    
    public void add(int key) {
        int hashkey = hash(key);      
        if (table[hashkey] == null) {
            table[hashkey] = new boolean[itemsPerBucket];
        }
        table[hashkey][pos(key)] = true;
    }
    
    public void remove(int key) {
        int hashkey = hash(key);
        if (table[hashkey] != null)
            table[hashkey][pos(key)] = false;
    }
    
    /** Returns true if this set did not already contain the specified element */
    public boolean contains(int key) {
        int hashkey = hash(key);
        return table[hashkey] != null && table[hashkey][pos(key)];
    }
}

// Solution 2:
// Refer to
// https://leetcode.com/problems/design-hashset/discuss/143434/Beats-100-Real-Java-Solution-(Not-boolean-array)/229864
// Create Linkedlist on array buckets with hashing to locate key on array index
class MyHashSet {
    // load factor = 0.7, 10000 / 0.7 = 13000, 
    // no resize required to handle 10000
    int numOfBuckets = 13000;
    final List<Integer>[] nodes; 
    
    /** Initialize your data structure here. */
    public MyHashSet() {
        nodes = new LinkedList[numOfBuckets];
    }
    
    private int hashing(int key) {
        return Integer.hashCode(key) % nodes.length;
    }
    
    public void add(int key) {
        int index = hashing(key);
        if(nodes[index] == null) {
            nodes[index] = new LinkedList<Integer>();
        }
        if(nodes[index].indexOf(key) == -1) {
            nodes[index].add(key);
        }
    }
    
    public void remove(int key) {
        int index = hashing(key);
        if(nodes[index] == null) {
            return;
        }
        if(nodes[index].indexOf(key) != -1) {
            nodes[index].remove(nodes[index].indexOf(key));
        }
    }
    
    /** Returns true if this set contains the specified element */
    public boolean contains(int key) {
        int index = hashing(key);
        if(nodes[index] == null || nodes[index].indexOf(key) == -1) {
            return false;
        }
        return true;
    }
}

/**
 * Your MyHashSet object will be instantiated and called as such:
 * MyHashSet obj = new MyHashSet();
 * obj.add(key);
 * obj.remove(key);
 * boolean param_3 = obj.contains(key);
 */
