/**
 * Refer to
 * https://leetcode.com/problems/remove-duplicates-from-sorted-list-ii/#/description
 * Given a sorted linked list, delete all nodes that have duplicate numbers, 
 * leaving only distinct numbers from the original list.
 * For example,
 * Given 1->2->3->3->4->4->5, return 1->2->5.
 * Given 1->1->1->2->3, return 2->3.
 */
public class RemoveDuplicatesFromSortedListII {
	private class ListNode {
		int val;
		ListNode next;
		ListNode(int x) { 
			val = x; 
		}
	}
	
	// If the linked list is not the sorted one, cannot use this three pointers solution
	/**
	 * Solution 1:
	 * http://www.cnblogs.com/springfor/p/3862077.html
	 * 这道题与I的区别就是要把所有重复的node删除。因此，还是利用I中去重的方法，引用双指针，并且增加一个新的指针，
	 * 指向当前的前一个node，使用3个指针（prev，current，post）来遍历链表。 
	 * 最开始还是需要建立一个fakehead，让fakehead的next指向head。然后，使用我刚才说过的3个指针方法来初始化3个指针，如下： 
		  ListNode ptr0 = fakehead; //prev
		  ListNode ptr1 = fakehead.next; //current
		  ListNode ptr2 = fakehead.next.next; //post
	 * 同时还需要引入一个布尔型的判断flag，来帮助判断当前是否遇到有重复，这个flag能帮助识别是否需要删除重复。
	 * 当没有遇到重复值（flag为false）时，3个指针同时往后移动：
	      ptr0 = ptr1;
	      ptr1 = ptr2;
	      ptr2 = ptr2.next; 
	 * 当遇到重复值时，设置flag为true，并让ptr2一直往后找找到第一个与ptr1值不等的位置时停止，这时，ptr1指向的node的值
	 * 是一个重复值，需要删除，所以这时就需要让ptr0的next连上当前的ptr2，这样就把所有重复值略过了(这样有效的前提是因为
	 * linkedlist已经是sorted，所有的同样字母已经被归结在一起，这一点很重要，否则无法实现用三指针跳过所有同样字符的模式)。
	 * 然后，让ptr1和ptr2往后挪动继续查找。
	 * 这里还需要注意的是，当ptr2一直往后找的过程中，是有可能ptr2==null（这种情况就是list的最后几个元素是重复的，
	 * 例如1->2->3->3->null)，这时ptr1指向的值肯定是需要被删除的，所以要特殊处理，令ptr0的next等于null，把重复值删掉。
	 * 其他情况说明最后几个元素不重复，不需要处理结尾，遍历就够了。
	 */
	public ListNode deleteDuplicates(ListNode head) {
        if(head == null || head.next == null)
            return head;
        
        ListNode fakehead = new ListNode(0);
        fakehead.next = head;
        
        ListNode ptr0 = fakehead;
        ListNode ptr1 = fakehead.next;
        ListNode ptr2 = fakehead.next.next;
        
        boolean flag = false;
        while(ptr2!=null){
            if(ptr1.val == ptr2.val){
                flag = true;
                ptr2 = ptr2.next;
                if(ptr2 == null)
                    ptr0.next = null;
            }else{
                if(flag){
                    ptr0.next = ptr2;
                    flag = false;
                }else{
                    ptr0 = ptr1;
                }
                ptr1 = ptr2;
                ptr2 = ptr2.next;
            }
        }
        return fakehead.next;
    }
	
	/**
	 * Solution 2:
	 * Refer to
	 * https://discuss.leetcode.com/topic/3890/my-accepted-java-code
	 * Just need 2 pointers
	 */
	public ListNode deleteDuplicates2(ListNode head) {
        if(head == null || head.next == null) {
            return head;
        }
        ListNode dummy = new ListNode(-1);
        dummy.next = head;
        ListNode curr = head;
        ListNode prev = dummy;
        while(curr != null) {
            while(curr.next != null && curr.val == curr.next.val) {
                curr = curr.next;
            }
            if(prev.next == curr) {
                prev = prev.next;
            } else {
                prev.next = curr.next;
            }
            curr = curr.next;
        }
        return dummy.next;
	}
	
	public static void main(String[] args) {
		RemoveDuplicatesFromSortedListII r = new RemoveDuplicatesFromSortedListII();
		ListNode one = r.new ListNode(1);
		ListNode oneAgain = r.new ListNode(1);
		ListNode two = r.new ListNode(2);
		ListNode twoAgain = r.new ListNode(2);
		ListNode three = r.new ListNode(3);
		one.next = oneAgain;
		oneAgain.next = two;
		two.next = twoAgain;
		twoAgain.next = three;
		ListNode result = r.deleteDuplicates2(one);
		System.out.println(result.val);
	}
}

// Re-work
// Style 1:
class Solution {
    public ListNode deleteDuplicates(ListNode head) {
        if(head == null) {
            return head;
        }
        // dummy -> head -> head.next
        //  prev    curr    iter
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode prev = dummy;
        ListNode curr = head;
        ListNode iter = head.next;
        int currValCount = 1;
        while(iter != null) {
            if(curr.val == iter.val) {
                currValCount++;
                // Test case: No different value node than current node till end
                // [1,2,2] -> expected [1]
                // Since not able to use 'prev.next = iter' to remove all duplicates
                // node, have to clean up all nodes from 'prev.next' till end
                if(iter.next == null) {
                    prev.next = null;
                }
            } else {
                if(currValCount > 1) {
                    prev.next = iter; // Remove all duplicate nodes including first occurrence
                    curr = iter;      // Update curr to iter
                    currValCount = 1; // Reset count to 1
                } else {
                    prev = curr;
                    curr = iter;
                }
            }
            iter = iter.next;
        }
        // Test case: [1,2,2] -> expected [1] output []
        // Or we can put the case handling here
        //if(currValCount > 1) {
        //    prev.next = null;
        //}
        return dummy.next;
    }
}

// Style 2:
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    public ListNode deleteDuplicates(ListNode head) {
        if(head == null) {
            return head;
        }
        // dummy -> head -> head.next
        //  prev    curr    iter
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode prev = dummy;
        ListNode curr = head;
        ListNode iter = head.next;
        int currValCount = 1;
        while(iter != null) {
            if(curr.val == iter.val) {
                currValCount++;
                // Test case: No different value node than current node till end
                // [1,2,2] -> expected [1]
                // Since not able to use 'prev.next = iter' to remove all duplicates
                // node, have to clean up all nodes from 'prev.next' till end
                //if(iter.next == null) {
                //    prev.next = null;
                //}
            } else {
                if(currValCount > 1) {
                    prev.next = iter; // Remove all duplicate nodes including first occurrence
                    curr = iter;      // Update curr to iter
                    currValCount = 1; // Reset count to 1
                } else {
                    prev = curr;
                    curr = iter;
                }
            }
            iter = iter.next;
        }
        // Test case: [1,2,2] -> expected [1] output []
        // Or we can put the case handling here
        if(currValCount > 1) {
            prev.next = null;
        }
        return dummy.next;
    }
}


// Solution 2:
// Refer to
// https://leetcode.com/problems/remove-duplicates-from-sorted-list-ii/discuss/28335/My-accepted-Java-code
class Solution {
    public ListNode deleteDuplicates(ListNode head) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode prev = dummy;
        ListNode curr = head;
        while(curr != null) {
            while(curr.next != null && curr.val == curr.next.val) {
                curr = curr.next;
            }
            // Refer to
            // https://leetcode.com/problems/remove-duplicates-from-sorted-list-ii/discuss/28335/My-accepted-Java-code/152490
            // Why use pre.next == cur not pre.next.val == cur.val?
            // To check whether cur has moved forward or not, because you want 
            // to make sure if current position's element is unique, then you 
            // can move forward, so you have to compare pointers. value's 
            // comparison is only to find dups and discard them.
            if(prev.next == curr) {
                prev = prev.next;
            } else {
                prev.next = curr.next;
            }
            curr = curr.next;
        }
        return dummy.next;
    }
}

// https://leetcode.wang/leetCode-82-Remove-Duplicates-from-Sorted-ListII.html
class Solution {
    public ListNode deleteDuplicates(ListNode head) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode prev = dummy;
        ListNode curr = head;
        boolean equal = false;
        while(curr != null) {
            // curr 和 curr.next 相等，curr 不停后移
            while(curr.next != null && curr.val == curr.next.val) {
                curr = curr.next;
                equal = true;
            }
            if(equal) {
                // 发生了相等的情况, prev.next 直接指向 curr.next 删除所有重复数字
                prev.next = curr.next;
                equal = false;
            } else {
                // 没有发生相等的情况, prev 移到 curr 的地方
                prev = curr;
            }
            // curr 后移
            curr = curr.next;
        }
        return dummy.next;
    }
}

// Solution 3: Recursive
// Refer to
// https://leetcode.wang/leetCode-82-Remove-Duplicates-from-Sorted-ListII.html
// https://leetcode.com/problems/remove-duplicates-from-sorted-list-ii/discuss/28339/My-Recursive-Java-Solution
class Solution {
    public ListNode deleteDuplicates(ListNode head) {
        if(head == null) {
            return head;
        }
        // 如果头结点和后边的节点相等
        if(head.next != null && head.val == head.next.val) {
            // 跳过所有重复数字
            while(head.next != null && head.val == head.next.val) {
                head = head.next;
            }
            // 将所有重复数字去掉后，进入递归
            return deleteDuplicates(head.next);
        // 头结点和后边的节点不相等
        } else {
            // 保留头结点，后边的所有节点进入递归
            head.next = deleteDuplicates(head.next);
        }
        // 返回头结点
        return head;
    }
}





























































https://leetcode.com/problems/remove-duplicates-from-sorted-list-ii/

Given the head of a sorted linked list, delete all nodes that have duplicate numbers, leaving only distinct numbers from the original list. Return the linked list sorted as well.

Example 1:


```
Input: head = [1,2,3,3,4,4,5]
Output: [1,2,5]
```

Example 2:


```
Input: head = [1,1,1,2,3]
Output: [2,3]
```
 
Constraints:
- The number of nodes in the list is in the range [0, 300].
- -100 <= Node.val <= 100
- The list is guaranteed to be sorted in ascending order.
---
Attempt 1: 2023-02-11

Wrong Solution
If just copy the same way from L83.Remove Duplicates from Sorted List, it won't work
Test case:
Input: 1 -> 1 -> 1 -> 2 -> 3
Output: 1->2->3
Expected: 2->3
Not able to remove the first node '1'
```
/** 
 * Definition for singly-linked list. 
 * public class ListNode { 
 *     int val; 
 *     ListNode next; 
 *     ListNode() {} 
 *     ListNode(int val) { this.val = val; } 
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; } 
 * } 
 */ 
class Solution { 
    public ListNode deleteDuplicates(ListNode head) { 
        if(head == null || head.next == null) { 
            return head; 
        } 
        ListNode dummy = new ListNode(); 
        dummy.next = head; 
        ListNode iter = dummy; 
        while(iter.next != null && iter.next.next != null) { 
            if(iter.next.val == iter.next.next.val) { 
                // Here is the copy from L83.Remove Duplicates from Sorted List, but wrong
                iter.next = iter.next.next.next;
            } else { 
                iter = iter.next; 
            } 
        } 
        return dummy.next; 
    } 
}
```

Solution 1:  Iterative Solution (60 min)
```
/** 
 * Definition for singly-linked list. 
 * public class ListNode { 
 *     int val; 
 *     ListNode next; 
 *     ListNode() {} 
 *     ListNode(int val) { this.val = val; } 
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; } 
 * } 
 */ 
class Solution { 
    public ListNode deleteDuplicates(ListNode head) { 
        if(head == null || head.next == null) { 
            return head; 
        } 
        ListNode dummy = new ListNode(); 
        dummy.next = head; 
        ListNode iter = dummy; 
        while(iter.next != null && iter.next.next != null) { 
            // Detect duplicates exist in list 
            if(iter.next.val == iter.next.next.val) { 
                // Declear duplciate value for current section 
                int dup_val = iter.next.val; 
                // Recursively skip duplicate value node one by one, 
                // and different from L83 is in L82 here we will also 
                // skip the initial node contains duplicate value 
                // e.g 1->1->1->2->3 
                // In L83 we only skip 2nd and 3rd '1' => 1->2->3 
                // In L82 we skip all three '1' => 2->3 
                while(iter.next != null && iter.next.val == dup_val) { 
                    iter.next = iter.next.next; 
                } 
            } else { 
                iter = iter.next; 
            } 
        } 
        return dummy.next; 
    } 
}

Time Complexity: O(n)      
Space Complexity: O(1)
```

Refer to
https://leetcode.com/problems/remove-duplicates-from-sorted-list-ii/solutions/2419088/very-easy-100-fully-explained-java-c-python-js-c-python3/
```
class Solution { 
    public ListNode deleteDuplicates(ListNode head) { 
        // Special case... 
        if (head == null || head.next == null) 
            return head; 
        // create a fake node that acts like a fake head of list pointing to the original head and it points to the original head...... 
        ListNode fake = new ListNode(0); 
        fake.next = head; 
        ListNode curr = fake; 
        // Loop till curr.next and curr.next.next not null 
        while(curr.next != null && curr.next.next != null){         // curr.next means the next node of curr pointer and curr.next.next means the next of next of curr pointer... 
            // if the value of curr.next and curr.next.next is same... 
            // There is a duplicate value present in the list... 
            if(curr.next.val == curr.next.next.val) { 
                int duplicate = curr.next.val; 
                // If the next node of curr is not null and its value is eual to the duplicate value... 
                while(curr.next !=null && curr.next.val == duplicate) { 
                    // Skip those element and keep updating curr... 
                    curr.next = curr.next.next; 
                } 
            } 
            // Otherwise, move curr forward... 
            else{ 
                curr = curr.next; 
            } 
        } 
        return fake.next;       // Return the linked list... 
    } 
}
```

Refer to
https://leetcode.com/problems/remove-duplicates-from-sorted-list-ii/solutions/952302/remove-duplicates-from-sorted-list-ii/

Approach 1: Sentinel Head + Predecessor

Sentinel Head
Let's start from the most challenging situation: the list head is to be removed.


Figure 1. The list head is to be removed.

The standard way to handle this use case is to use the so-called Sentinel Node. Sentinel nodes are widely used for trees and linked lists as pseudo-heads, pseudo-tails, etc. They are purely functional and usually don't hold any data. Their primary purpose is to standardize the situation to avoid edge case handling.

For example, let's use here pseudo-head with zero value to ensure that the situation "delete the list head" could never happen, and all nodes to delete are "inside" the list.

Delete Internal Nodes
The input list is sorted, and we can determine if a node is a duplicate by comparing its value to the node after it in the list. Step by step, we could identify the current sublist of duplicates.

Now it's time to delete it using pointer manipulations. Note that the first node in the duplicates sublist should be removed as well. That means that we have to track the predecessor of duplicates sublist, i.e., the last node before the sublist of duplicates.


Figure 2. The sentinel head, the predecessor, and the sublist of duplicates to delete.

Having predecessor, we skip the entire duplicate sublist and make predecessor to point to the node after the sublist.


Figure 2. Delete the sublist of duplicates.

Implementation
```
class Solution { 
    public ListNode deleteDuplicates(ListNode head) { 
        // sentinel 
        ListNode sentinel = new ListNode(0, head); 
        // predecessor = the last node  
        // before the sublist of duplicates 
        ListNode pred = sentinel; 
         
        while (head != null) { 
            // if it's a beginning of duplicates sublist  
            // skip all duplicates 
            if (head.next != null && head.val == head.next.val) { 
                // move till the end of duplicates sublist 
                while (head.next != null && head.val == head.next.val) { 
                    head = head.next;     
                } 
                // skip all duplicates 
                pred.next = head.next;      
            // otherwise, move predecessor 
            } else { 
                pred = pred.next;     
            } 
                 
            // move forward 
            head = head.next;     
        }   
        return sentinel.next; 
    } 
}
```
Complexity Analysis
- Time complexity: O(N) since it's one pass along the input list.
- Space complexity: O(1) because we don't allocate any additional data structure.
---

Solution 2:  Recursive Solution (30 min)
```
/** 
 * Definition for singly-linked list. 
 * public class ListNode { 
 *     int val; 
 *     ListNode next; 
 *     ListNode() {} 
 *     ListNode(int val) { this.val = val; } 
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; } 
 * } 
 */ 
class Solution { 
    public ListNode deleteDuplicates(ListNode head) { 
        if(head == null || head.next == null) { 
            return head; 
        } 
        int cur_val = head.val; 
        ListNode p = head.next; 
        if(p.val == cur_val) { 
            while(p != null && p.val == cur_val) { 
                p = p.next; 
            } 
            return deleteDuplicates(p); 
        } else { 
            head.next = deleteDuplicates(p); 
            return head; 
        } 
    } 
}

=============================================================================
OR remove ListNode p = head.next, and make more similar to L83. Remove Duplicates from Sorted List

/** 
 * Definition for singly-linked list. 
 * public class ListNode { 
 *     int val; 
 *     ListNode next; 
 *     ListNode() {} 
 *     ListNode(int val) { this.val = val; } 
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; } 
 * } 
 */ 
class Solution { 
    public ListNode deleteDuplicates(ListNode head) { 
        if(head == null || head.next == null) { 
            return head; 
        } 
        // If current node is not unique, return deleteDuplicates with head.next 
        if(head.next.val == head.val) { 
            while(head.next != null && head.next.val == head.val) { 
                head.next = head.next.next; 
            } 
            // 'head' also removed, hence no connection between 'head' and 'head.next', 
            // hence no "head.next = deleteDuplicates(head.next)", and we only want 
            // start recursion with 'head.next', so directly "deleteDuplicates(head.next)" 
            return deleteDuplicates(head.next); 
        // If current node is unique, link it to the result of next list made by recursive call, similar as how L83.Remove Duplicates from Sorted List 
        } else { 
            head.next = deleteDuplicates(head.next); 
            return head; 
        } 
    } 
}

Time Complexity: O(n)      
Space Complexity: O(1)
```

Refer to
https://leetcode.com/problems/remove-duplicates-from-sorted-list-ii/solutions/28355/simple-and-clear-c-recursive-solution/
```
class Solution { 
public: 
    ListNode* deleteDuplicates(ListNode* head) { 
        if (!head) return 0; 
        if (!head->next) return head; 
         
        int val = head->val; 
        ListNode* p = head->next; 
         
        if (p->val != val) { 
            head->next = deleteDuplicates(p); 
            return head; 
        } else { 
            while (p && p->val == val) p = p->next; 
            return deleteDuplicates(p); 
        } 
    } 
};
```

https://leetcode.com/problems/remove-duplicates-from-sorted-list-ii/solutions/28339/my-recursive-java-solution/
if current node is not unique, return deleteDuplicates with head.next.
If current node is unique, link it to the result of next list made by recursive call.
```
public ListNode deleteDuplicates(ListNode head) { 
    if (head == null) return null;  
    if (head.next != null && head.val == head.next.val) { 
        while (head.next != null && head.val == head.next.val) { 
            head = head.next; 
        } 
        return deleteDuplicates(head.next); 
    } else { 
        head.next = deleteDuplicates(head.next); 
    } 
    return head; 
}
```
