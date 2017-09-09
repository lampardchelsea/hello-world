/**
 * Refer to
 * http://www.lintcode.com/en/problem/rehashing/
 * The size of the hash table is not determinate at the very beginning. If the total size of keys 
 * is too large (e.g. size >= capacity / 10), we should double the size of the hash table and 
 * rehash every keys. Say you have a hash table looks like below:

	size=3, capacity=4
	
	[null, 21, 14, null]
	       ↓    ↓
	       9   null
	       ↓
	      null
	The hash function is:
	
	int hashcode(int key, int capacity) {
	    return key % capacity;
	}
	here we have three numbers, 9, 14 and 21, where 21 and 9 share the same position as they all 
	have the same hashcode 1 (21 % 4 = 9 % 4 = 1). We store them in the hash table by linked list.
	
	rehashing this hash table, double the capacity, you will get:
	
	size=3, capacity=8
	
	index:   0    1    2    3     4    5    6   7
	hash : [null, 9, null, null, null, 21, 14, null]
	Given the original hash table, return the new hash table after rehashing .
	
	 Notice
	
	For negative integer in hash table, the position can be calculated as follow:
	
	C++/Java: if you directly calculate -4 % 3 you will get -1. 
	You can use function: a % b = (a % b + b) % b to make it is a non negative integer.
	Python: you can directly use -1 % 3, you will get 2 automatically.
	Have you met this question in a real interview? Yes
	Example
	Given [null, 21->9->null, 14->null, null],
	return [null, 9->null, null, null, null, 21->null, 14->null, null]
 *
 * 
 * Solution
 * http://www.jiuzhang.com/solutions/rehashing/
 * 
 */
public class Reharshing {
    private class ListNode {
        int val;
		ListNode next;
		ListNode(int x) {
		    val = x;
		    next = null;
		}
    }
    
    public ListNode[] rehashing(ListNode[] hashTable) {
        if(hashTable.length <= 0) {
            return hashTable;
        }
        int newCapacity = 2 * hashTable.length;
        ListNode[] newTable = new ListNode[newCapacity];
        for(int i = 0; i < hashTable.length; i++) {
            while(hashTable[i] != null) {
                int newIndex = (hashTable[i].val % newCapacity + newCapacity) % newCapacity;
                if(newTable[newIndex] == null) {
                    newTable[newIndex] = new ListNode(hashTable[i].val);
                } else {
                    ListNode dummy = new ListNode(0);
                    dummy.next = newTable[newIndex];
                    // Must check on 'dummy.next != null', not 'dummy != null'
                    while(dummy.next != null) {
                        dummy = dummy.next;
                    }
                    dummy.next = new ListNode(hashTable[i].val);
                }
                hashTable[i] = hashTable[i].next;
            }
        }
        return newTable;
    }
    
    public static void main(String[] args) {
    	Reharshing r = new Reharshing();
    	// Input: [null,null,29->5->null]
    	// Expected output: [null,null,null,null,null,29->5->null]
    	ListNode temp = r.new ListNode(29);
    	temp.next = r.new ListNode(5);
    	ListNode[] hashTable = {null, null, temp};
    	r.rehashing(hashTable);
    }
}
