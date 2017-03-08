/**
 * Refer to
 * https://leetcode.com/problems/copy-list-with-random-pointer/?tab=Description
 * A linked list is given such that each node contains an additional random 
 * pointer which could point to any node in the list or null.
 * Return a deep copy of the list.
 * 
 * Solution
 * Refer to
 * https://discuss.leetcode.com/topic/7594/a-solution-with-constant-space-complexity-o-1-and-linear-time-complexity-o-n
 * An intuitive solution is to keep a hash table for each node in the list, via which we 
 * just need to iterate the list in 2 rounds respectively to create nodes and assign the 
 * values for their random pointers. As a result, the space complexity of this solution 
 * is O(N), although with a linear time complexity.
 * 
 * As an optimised solution, we could reduce the space complexity into constant. The idea 
 * is to associate the original node with its copy node in a single linked list. In this way, 
 * we don't need extra space to keep track of the new nodes.
 * 
 * The algorithm is composed of the follow three steps which are also 3 iteration rounds.
 * (1) Iterate the original list and duplicate each node. The duplicate of each node follows its original immediately.
 * (2) Iterate the new list and assign the random pointer for each duplicated node.
 * (3) Restore the original list and extract the duplicated nodes.
 * 
 * http://www.cnblogs.com/springfor/p/3864457.html
 * 第一遍，对每个node进行复制，并插入其原始node的后面，新旧交替，变成重复链表。如：原始：1->2->3->null，复制后：1->1->2->2->3->3->null
 * 第二遍，遍历每个旧node，把旧node的random的复制给新node的random，因为链表已经是新旧交替的。所以复制方法为：
 * node.next.random = node.random.next
 * 前面是说旧node的next的random，就是新node的random，后面是旧node的random的next，正好是新node，是从旧random复制来的。
 *  第三遍，则是把新旧两个表拆开，返回新的表即可。
 */
public class CopyListWithRandomPointer {
	 private class RandomListNode {
		 int label;
		 RandomListNode next, random;
		 RandomListNode(int x) { 
			 this.label = x; 
		 }
     }
	
	public RandomListNode copyRandomList(RandomListNode head) {
		// Be careful, for this question, we should not use this check
		// if given input as one node, it will directly return itself
		// as original not the copy
		// E.g
		// Input: {-1,#}
		// Output: Node with label -1 was not copied but a reference to the original one.
//        if(head == null || head.next == null) {
//            return head;
//        }

		// First round: make copy of each node,
		// and link them together side-by-side in a single list.
        RandomListNode itr = head;
        RandomListNode nextTemp;
        while(itr != null) {
        	// Create a copy node based on 'label'
        	RandomListNode copy = new RandomListNode(itr.label);
        	// Reserve original next node of 'itr' into 'nextTemp'
        	nextTemp = itr.next;
        	// Insert copy node between 'itr' and 'itr.next'
            copy.next = nextTemp;
            itr.next = copy;
            // Advance 'itr' to original next node of 'itr' as 'nextTemp'
            itr = nextTemp;
        }
        
        // Second round: assign random pointers for the copy nodes.
        itr = head;
        while(itr != null) {
        	if(itr.random != null) {
        		// Current node's next node(itr.next) is a copy node, 
        		// its 'random' value will be assigned by current node's
        		// 'random' value(a node point)'s next node
        		// E.g 1 -> 1' -> 3 -> 3'
        		// if 1 has random value as 3, then 1' has random value as 3'
                itr.next.random = itr.random.next;        		
        	}
        	// Skip the copy node
        	// E.g 1 -> 1' -> 3 -> 3', if itr = 1 now, it will skip 1'
            itr = itr.next.next;
        }
        
        // Third round: restore the original list, and extract the copy list.
        itr = head;
        RandomListNode dummy = new RandomListNode(-1);
        RandomListNode copy;
        RandomListNode copyItr = dummy;
        while(itr != null) {
        	// Reuse 'nextTemp' to reserve original next node of 'itr'
        	// E.g 1 -> 1' -> 3 -> 3', if itr = 1 now, we reserve 3 in nextTemp
            nextTemp = itr.next.next;
            // E.g 1 -> 1' -> 3 -> 3', if itr = 1 now, copy is 1'
            copy = itr.next;
            // Point copyItr to copy, e.g if copyItr = dummy now, 
            // it will concatenate dummy to 1' to construct new list
            copyItr.next = copy;
            // Move copyItr to copy for next loop, e.g if copyItr = dummy now, move to 1'
            copyItr = copy;
            // Point itr to original next node which stored in 'nextTemp',
            // e.g if itr = 1 now, it will concatenate 3 to reconstruct
            // original list
            itr.next = nextTemp;
            // Move itr to nextTemp for next loop, e.g if itr = 1 now, move to 3
            itr = nextTemp;
        }
        
        return dummy.next;
    }
	
	public static void main(String[] args) {
		CopyListWithRandomPointer c = new CopyListWithRandomPointer();
		RandomListNode one = c.new RandomListNode(1);
		RandomListNode two = c.new RandomListNode(2);
		RandomListNode three = c.new RandomListNode(3);
		RandomListNode four = c.new RandomListNode(4);
		one.next = two;
		two.next = three;
		three.next = four;
		one.random = three;
		two.random = four;
		RandomListNode result = c.copyRandomList(one);
		System.out.println(result.label);
	}
}

