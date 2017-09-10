/**
 * Refer to
 * http://www.lintcode.com/en/problem/copy-list-with-random-pointer/#
 * A linked list is given such that each node contains an additional random pointer 
   which could point to any node in the list or null.
   Return a deep copy of the list.
 *
 * Solution
 * https://www.kancloud.cn/kancloud/data-structure-and-algorithm-notes/73016
 * 题解1 - 哈希表(两次遍历)
  首先得弄懂深拷贝的含义，深拷贝可不是我们平时见到的对基本类型的变量赋值那么简单，深拷贝常常用于对象的克隆。
  这道题要求深度拷贝一个带有 random 指针的链表，random 可能指向空，也可能指向链表中的任意一个节点。
  对于通常的单向链表，我们依次遍历并根据原链表的值生成新节点即可，原链表的所有内容便被复制了一份。但由于此题
  中的链表不只是有 next 指针，还有一个随机指针，故除了复制通常的 next 指针外还需维护新链表中的随机指针。
  容易混淆的地方在于原链表中的随机指针指向的是原链表中的节点，深拷贝则要求将随机指针指向新链表中的节点。

  所有类似的深度拷贝题目的传统做法，都是维护一个 hash table。即先按照复制一个正常链表的方式复制，复制的时候
  把复制的结点做一个 hash table，以旧结点为 key，新节点为 value。这么做的目的是为了第二遍扫描的时候我们按
  照这个哈希表把结点的 random 指针接上。
  原链表和深拷贝之后的链表如下：

  |------------|             |------------|
  |            v       ===>  |            v
  1  --> 2 --> 3 --> 4       1' --> 2'--> 3'--> 4'
  深拷贝步骤如下；

  根据 next 指针新建链表
  维护新旧节点的映射关系
  拷贝旧链表中的 random 指针
  更新新链表中的 random 指针
  其中1, 2, 3 可以合并在一起。

  public class Solution {
      public RandomListNode copyRandomList(RandomListNode head) {
          if (head == null) return null;
          RandomListNode dummy = new RandomListNode(0);
          RandomListNode curNode = dummy;
          HashMap<RandomListNode, RandomListNode> randomMap = new HashMap<RandomListNode, RandomListNode>();
          while (head != null) {
              // link newNode to new List
              RandomListNode newNode = new RandomListNode(head.label);
              curNode.next = newNode;
              // map old node head to newNode
              randomMap.put(head, newNode);
              // copy old node random pointer
              newNode.random = head.random;
              //
              head = head.next;
              curNode = curNode.next;
          }

          // re-mapping old random node to new node
          curNode = dummy.next;
          while(curNode != null) {
              if (curNode.random != null) {
                  curNode.random = randomMap.get(curNode.random);
              }
              curNode = curNode.next;
          }

          return dummy.next;
      }
  }

  源码分析
  只需要一个 dummy 存储新的拷贝出来的链表头，以用来第二次遍历时链接 random 指针。所以第一句异常检测可有可无。
  第一次链接时勿忘记同时拷贝 random 指针，但此时的 random 指针并没有真正“链接”上，实际上是链接到了原始链表的 node 上。
  第二次遍历是为了把原始链表的被链接的 node 映射到新链表中的 node，从而完成真正“链接”
  
  题解2 - 哈希表(一次遍历)
  从题解1 的分析中我们可以看到对于 random 指针我们是在第二次遍历时单独处理的，那么在借助哈希表的情况下有没有
  可能一次遍历就完成呢？我们回想一下题解1 中random 节点的处理，由于在第一次遍历完之前 random 所指向的节点是
  不知道到底是指向哪一个节点，故我们在将 random 指向的节点加入哈希表之前判断一次就好了(是否已经生成，避免对
  同一个值产生两个不同的节点)。由于 random 节点也在第一次遍历加入哈希表中，故生成新节点时也需要判断哈希表中
  是否已经存在。
*/
/**
 * Definition for singly-linked list with a random pointer.
 * class RandomListNode {
 *     int label;
 *     RandomListNode next, random;
 *     RandomListNode(int x) { this.label = x; }
 * };
 */
public class Solution {
    /**
     * @param head: The head of linked list with a random pointer.
     * @return: A new head of a deep copy of the list.
     */
    public RandomListNode copyRandomList(RandomListNode head) {
        if(head == null) {
            return null;
        }
        // 维护一个 hash table, 即先按照复制一个正常链表的方式复制, 复制的时候
        // 把复制的结点做一个 hash table, 以旧结点为 key, 新节点为 value
        Map<RandomListNode, RandomListNode> map = new HashMap<RandomListNode, RandomListNode>();
        RandomListNode dummy = new RandomListNode(0);
        // Use iterator to go through because need keep 'dummy.next' as result,
        // 'dummy' itself cannot change
        RandomListNode itr = dummy;
        RandomListNode newNode;
        while(head != null) {
            // link newNode to new List
            if(!map.containsKey(head)) {
                newNode = new RandomListNode(head.label);
                // map old node head to newNode
                map.put(head, newNode);
            } else {
                newNode = map.get(head);
            }
            itr.next = newNode;
            // re-mapping old random node to new node
            if(head.random != null) {
                // 由于 random 节点也在第一次遍历加入哈希表中, 故生成新节点时也需要判断哈希表中
                // 是否已经存在, 如果已经存在则直接从hash table的对应key的value一栏中提取
                if(map.containsKey(head.random)) {
                    newNode.random = map.get(head.random);
                } else {
                    newNode.random = new RandomListNode(head.random.label);
                    map.put(head.random, newNode.random);
                }
            }
            // move to next node on both original list and new list
            head = head.next;
            itr = itr.next;
        }
        return dummy.next;
    }
}




