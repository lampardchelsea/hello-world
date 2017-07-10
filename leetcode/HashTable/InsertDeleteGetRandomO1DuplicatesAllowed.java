import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * Refer to
 * https://leetcode.com/problems/insert-delete-getrandom-o1-duplicates-allowed/#/description
 * Design a data structure that supports all following operations in average O(1) time.
 * Note: Duplicate elements are allowed.

    insert(val): Inserts an item val to the collection.
    remove(val): Removes an item val from the collection if present.
    getRandom: Returns a random element from current collection of elements. The probability of each element being returned is linearly related to the number of same value the collection contains.

	Example:
	
	// Init an empty collection.
	RandomizedCollection collection = new RandomizedCollection();
	
	// Inserts 1 to the collection. Returns true as the collection did not contain 1.
	collection.insert(1);
	
	// Inserts another 1 to the collection. Returns false as the collection contained 1. Collection now contains [1,1].
	collection.insert(1);
	
	// Inserts 2 to the collection, returns true. Collection now contains [1,1,2].
	collection.insert(2);
	
	// getRandom should return 1 with the probability 2/3, and returns 2 with the probability 1/3.
	collection.getRandom();
	
	// Removes 1 from the collection, returns true. Collection now contains [1,2].
	collection.remove(1);
	
	// getRandom should return 1 and 2 both equally likely.
	collection.getRandom();
 * 
 * Solution
 * https://discuss.leetcode.com/topic/53216/java-solution-using-a-hashmap-and-an-arraylist-along-with-a-follow-up-131-ms/4
 * The idea is to add a set to the hashMap to remember all the locations of a duplicated number.
 * 
 * https://discuss.leetcode.com/topic/53216/java-solution-using-a-hashmap-and-an-arraylist-along-with-a-follow-up-131-ms/6
 * One question: what is the time complexity for a set to return its iterator?
 * According to Javadoc
 * Iterating over this set requires time proportional to the sum of the HashSet instance's size (the number of elements) 
 * plus the "capacity" of the backing HashMap instance (the number of buckets).
 * Therefore, we can consider replacing the HashSet with a LinkedHashSet which should have a constant time of getting the 
 * first element because the order of all elements is maintained during each operation. Here is the modification.
 * 
 * https://discuss.leetcode.com/topic/53688/java-haspmap-linkedhashset-arraylist-155-ms
 * Replace HashSet with LinkedHashSet
 * 
 * HashSet vs LinkedHashSet
 * https://stackoverflow.com/a/38141941/6706875
 * A HashSet is unordered and unsorted Set. LinkedHashSet is the ordered version of HashSet.The only difference between 
 * HashSet and LinkedHashSet is that LinkedHashSet maintains the insertion order. When we iterate through a HashSet, 
 * the order is unpredictable while it is predictable in case of LinkedHashSet. The reason why LinkedHashSet maintains 
 * insertion order is because the underlying data structure is a doubly-linked list.
 */
public class InsertDeleteGetRandomO1DuplicateAllowed {
	List<Integer> nums;
	Map<Integer, Set<Integer>> locs;
	
    /** Initialize your data structure here. */
    public InsertDeleteGetRandomO1DuplicateAllowed() {
        nums = new ArrayList<Integer>();
        locs = new HashMap<Integer, Set<Integer>>();
    }
    
    /** Inserts a value to the collection. Returns true if the collection did not already contain the specified element. */
    public boolean insert(int val) {
    	boolean contain = locs.containsKey(val);
    	if(!contain) {
    		locs.put(val, new HashSet<Integer>());
    	}
    	locs.get(val).add(nums.size());
    	nums.add(val);
    	return !contain;
    }
    
    /** Removes a value from the collection. Returns true if the collection contained the specified element. */
    public boolean remove(int val) {
        boolean contain = locs.containsKey(val);
        if(!contain) {
        	return false;
        }
        // Get the first element by using Set iterator
        // if change to LinkedHashSet will have constant
        // time of getting the first element
        int loc = locs.get(val).iterator().next();
        locs.get(val).remove(loc);
        if(loc < nums.size() - 1) {
        	int lastone = nums.get(nums.size() - 1);
        	nums.set(loc, lastone);
        	locs.get(lastone).remove(nums.size() - 1);
        	locs.get(lastone).add(loc);
        }
        nums.remove(nums.size() - 1);
        if(locs.get(val).isEmpty()) {
        	locs.remove(val);
        }
        return true;
    }
    
    /** Get a random element from the collection. */
    public int getRandom() {
        return nums.get(new Random().nextInt(nums.size()));   
    }
}

