/**
 * Refer to
 * http://www.lintcode.com/en/problem/lru-cache/#
 * Design and implement a data structure for Least Recently Used (LRU) cache. 
   It should support the following operations: get and set.
   get(key) - Get the value (will always be positive) of the key if the key exists in the cache, otherwise return -1.
   set(key, value) - Set or insert the value if the key is not already present. When the cache reached its capacity, 
                     it should invalidate the least recently used item before inserting a new item.
 *
 * Solution
 * http://www.jiuzhang.com/solutions/lru-cache/
*/
// Solution 1: HashMap + DoublyLinkedList
// On DoublyLinkedList, the head.next should be least recently used node, the tail.prev should be most recently used node,
// so if reach to the capacity, always remove the head.next node out of DoublyLinkedList and map, and if insert the new
// node, have to add as tail.prev on both DoublyLinkedList and map.
public class LRUCache {
    private class Node {
        int key;
        int val;
        Node prev;
        Node next;
        public Node(int key, int val) {
            this.key = key;
            this.val = val;
            this.prev = null;
            this.next = null;
        }
    }    

    int capacity;
    // Two major structure to build LRU Cache
    // 1. HashMap
    // 2. DoublyLinkedList (head, tail)
    Map<Integer, Node> map = new HashMap<Integer, Node>();
    // Initial dummy nodes as head and tail
    Node head = new Node(-1, -1);
    Node tail = new Node(-1, -1);

    // @param capacity, an integer
    public LRUCache(int capacity) {
        this.capacity = capacity;
        head.next = tail;
        tail.prev = head;
    }

    // @return an integer
    public int get(int key) {
        if(!map.containsKey(key)) {
            return -1;
        }
        // If contains key's mapping Node, remove it
        // from current position and add to tail
        Node current = map.get(key);
        current.prev.next = current.next;
        current.next.prev = current.prev;
        move_to_tail(current);
        return current.val;
    }

    // @param key, an integer
    // @param value, an integer
    // @return nothing
    public void set(int key, int value) {
        // This internal 'get' method will update the key's position in the linked list.
        if(get(key) != -1) {
            map.get(key).val = value;
            return;
        }
        // If already reach capacity, need remove least used node
        // which stores at the start(head.next) of DoublyLinkedList
        if(map.size() == capacity) {
            // Remove from HashMap
            map.remove(head.next.key);
            // Remove from DoublyLinkedList
            head.next = head.next.next;
            head.next.prev = head;
        }
        Node insert = new Node(key, value);
        map.put(key, insert);
        move_to_tail(insert);
    }
    
    private void move_to_tail(Node current) {
        // The order should not change
        current.prev = tail.prev;
        tail.prev = current;
        current.prev.next = current;
        current.next = tail;
    }
}

// Solution 2: Reverse to Solution 1, store least recent used node at tail.prev, most recent used node at head.next
class LRUCache {
    private class Node {
        int key;
        int val;
        Node prev;
        Node next;
        
        public Node(int k, int v) {
            this.key = k;
            this.val = v;
            this.prev = null;
            this.next = null;
        }
    }
    
    Node head = new Node(-1, -1);
    Node tail = new Node(-1, -1);
    Map<Integer, Node> map = new HashMap<Integer, Node>();
    int capacity;
    
    public LRUCache(int capacity) {
        this.capacity = capacity;
        head.next = tail;
        tail.prev = head;
    }
    
    // Store least recent used node at tail.prev, most recent used node at head.next
    public int get(int key) {
        if(!map.containsKey(key)) {
            return -1;
        }
        // Remove node from current position and relocate it to head.next
        // No order change
        Node cur = map.get(key);
        cur.prev.next = cur.next;
        cur.next.prev = cur.prev;
        move_to_head(cur);
        return cur.val;
    }
    
    private void move_to_head(Node cur) {
        cur.next = head.next;
        head.next.prev = cur;
        head.next = cur;
        cur.prev = head;
    }
    
    public void put(int key, int value) {
        if(get(key) != -1) {
            map.get(key).val = value;
            return;
        }
        // Remove the least recent used node at tail.prev, also remove from map
        if(map.size() == capacity) {
            map.remove(tail.prev.key);
            // No order change
            tail.prev = tail.prev.prev;
            tail.prev.next = tail;
        }
        Node insert = new Node(key, value);
        map.put(key, insert);
        move_to_head(insert);
    }
}

/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache obj = new LRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */


// Solution 3: HashMap + SingleLinkedList


