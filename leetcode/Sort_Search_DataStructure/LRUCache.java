/**
 * Design and implement a data structure for Least Recently Used (LRU) cache. 
 * It should support the following operations: get and set.
 * get(key) - Get the value (will always be positive) of the key if the key exists 
 * in the cache, otherwise return -1.
 * set(key, value) - Set or insert the value if the key is not already present. 
 * When the cache reached its capacity, it should invalidate the least recently 
 * used item before inserting a new item.
 *
 * HashMap related part:
 * http://coding-geek.com/how-does-a-hashmap-work-in-java/
 * http://yikun.github.io/2015/04/01/Java-HashMap%E5%B7%A5%E4%BD%9C%E5%8E%9F%E7%90%86%E5%8F%8A%E5%AE%9E%E7%8E%B0/
 * http://stackoverflow.com/questions/4553624/hashmap-get-put-complexity
 *
 * DoublyLinkedList related part:
 * https://github.com/lampardchelsea/hello-world/blob/master/leetcode/Sort_Search_DataStructure/DoublyLinkedList.java
 * 
 * Design part:
 * http://www.learn4master.com/data-structures/hashtable/leetcode-lru-cache-solution-in-java
 * http://www.cnblogs.com/springfor/p/3869393.html
 * https://segmentfault.com/a/1190000003743083
*/
import java.util.HashMap;
import java.util.Map;

/**
 * Refer to
 * https://segmentfault.com/a/1190000003743083
 * 
 * 复杂度
 * 时间 Get O(1) Set O(1) 空间 O(N)
 * 思路
 * 缓存讲究的就是快，所以我们必须做到O(1)的获取速度，这样看来只有哈希表可以胜任。但是哈希表无序的，我们没办法在缓存满时，
 * 将最早更新的元素给删去。那么是否有一种数据结构，可以将先进的元素先出呢？那就是队列。所以我们将元素存在队列中，并用
 * 一个哈希表记录下键值和元素的映射，就可以做到O(1)获取速度，和先进先出的效果。然而，当我们获取一个元素时，还需要把这个
 * 元素再次放到队列头，这个元素可能在队列的任意位置，可是队列并不支持对任意位置的增删操作。而最适合对任意位置增删操作的
 * 数据结构又是什么呢？是链表。我可以用链表来实现一个队列，这样就同时拥有链表和队列的特性了。不过，如果仅用单链表的话，
 * 在任意位置删除一个节点还是很麻烦的，要么记录下该节点的上一个节点，要么遍历一遍。所以双向链表是最好的选择。我们用双向
 * 链表实现一个队列用来记录每个元素的顺序，用一个哈希表来记录键和值的关系，就行了。
 * 
 * 注意
 * 这题更多的是考察用数据结构进行设计的能力，再写代码时尽量将子函数拆分出来，先写个整体的框架。
 * 移出链表最后一个节点时，要记得在链表和哈希表中都移出该元素，所以节点中也要记录Key的信息，方便在哈希表中移除
 */
public class LRUCache {
	// Also, this implementation follows the way of Java Standard Library LinkedList, only
	// create dummy header to build Doubly Circular LinkedList, not like the way need to
	// create both header and tail, because header.previous = header.next = header when
	// initialize, and header.previous is actually tail. 
	private Entry header;
	private Map<Integer, Entry> map;
	private int capacity;
	private int size;
	
	private class Entry {
		Integer val;
		Integer key;
		Entry previous;
		Entry next;
		
		public Entry(Integer val, Integer key, Entry next, Entry previous) {
			this.val = val;
			this.key = key;
			this.next = next;
			this.previous = previous;
		}
	}
	
    public LRUCache(int capacity) {
    	this.header = new Entry(null, null, null, null);
    	header.previous = header.next = header;
    	this.map = new HashMap<Integer, Entry>();
    	this.capacity = capacity;
    	this.size = 0;
    }
    
    public int get(int key) {
    	Entry e = map.get(key);
    	if(e != null) {
    		moveToHead(e);
    		return e.val;
    	} else {
    		return -1;
    	}
    }
        
    public void set(int key, int value) {
        Entry e = map.get(key);
        if(e == null) {
        	e = new Entry(value, key, header.next, header);
        	attachToHead(e);
        	size++;
        } else {
        	e.val = value;
        	moveToHead(e);
        }
        
        if(size > capacity) {
        	removeLast();
        	size--;
        }
        
        // Also need to update HashMap
        map.put(key, e);
    }
    
    public void moveToHead(Entry e) {
    	e.previous.next = e.next;
    	e.next.previous = e.previous;
    	attachToHead(e);
    }
    
    public void attachToHead(Entry e) {
        e.next = header.next;
        e.next.previous = e;
        header.next = e;
        e.previous = header;
    }
    
    public void removeLast() {
    	Entry tail = header.previous;
    	tail.previous.next = tail.next;
    	tail.next.previous = tail.previous;
    	
    	// Also need to remove key-entry pair on map
    	map.remove(tail.key);
    }
}
