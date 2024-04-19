/**
 Refer to
 https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree/
 * Given a binary tree, find the lowest common ancestor (LCA) of two given nodes in the tree.
 * According to the definition of LCA on Wikipedia: “The lowest common ancestor is defined between 
 * two nodes v and w as the lowest node in T that has both v and w as descendants (where we allow 
 * a node to be a descendant of itself).”

        _______3______
       /              \
    ___5__          ___1__
   /      \        /      \
   6      _2       0       8
         /  \
         7   4
 * For example, the lowest common ancestor (LCA) of nodes 5 and 1 is 3. Another example is LCA 
 * of nodes 5 and 4 is 5, since a node can be a descendant of itself according to the LCA definition.
*/
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 *
 * Solution:
 * https://segmentfault.com/a/1190000003509399
 * 深度优先标记
 * 复杂度
 * 时间 O(h) 空间 O(h) 递归栈空间
 * 思路
 * 我们可以用深度优先搜索，从叶子节点向上，标记子树中出现目标节点的情况。如果子树中有目标节点，标记为那个目标节点，
 * 如果没有，标记为null。显然，如果左子树、右子树都有标记，说明就已经找到最小公共祖先了。如果在根节点为p的左右子树
 * 中找p、q的公共祖先，则必定是p本身。
 * 换个角度，可以这么想：如果一个节点左子树有两个目标节点中的一个，右子树没有，那这个节点肯定不是最小公共祖先。
 * 如果一个节点右子树有两个目标节点中的一个，左子树没有，那这个节点肯定也不是最小公共祖先。只有一个节点正好左子树有，
 * 右子树也有的时候，才是最小公共祖先。
 *
 * http://www.geeksforgeeks.org/lowest-common-ancestor-binary-tree-set-1/
 * Note: This solution contain a wrong condition as :
        // If either n1 or n2 matches with root's key, report
        // the presence by returning root (Note that if a key is
        // ancestor of other, then the ancestor key becomes LCA
        if (node.data == n1 || node.data == n2)
            return node;
 * The correct version should be 
        if (node == n1 || node == n2)
            return node;
 * Method 2 (Using Single Traversal)
 * The method 1 finds LCA in O(n) time, but requires three tree traversals plus extra spaces for path arrays. 
 * If we assume that the keys n1 and n2 are present in Binary Tree, we can find LCA using single traversal of 
 * Binary Tree and without extra storage for path arrays.
 * The idea is to traverse the tree starting from root. If any of the given keys (n1 and n2) matches with root, 
 * then root is LCA (assuming that both keys are present). If root doesn’t match with any of the keys, we recur 
 * for left and right subtree. The node which has one key present in its left subtree and the other key present 
 * in right subtree is the LCA. If both keys lie in left subtree, then left subtree has LCA also, otherwise LCA 
 * lies in right subtree.
 */
public class Solution {
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        // If either p or q matches with root(NOT root's val), report
        // the presence by returning root (Note that if a key is
        // ancestor of other, then the ancestor root becomes LCA)
        // The wrong condition is below:
        // if(root == null || root.val == p.val || root.val == q.val) {
        //    return root;
        // }
        if(root == null || root == p || root == q) {
            return root;
        }
        
        // The real situation of how LCA exist is only three cases after recursively
        // search left and right subtree and record matching LCA node if exists
        // by return the LCA node as left_lca and right_lca.
        
        // Case 1: left_lca != null && right_lca == null
        // this express LCA in left subtree, and we only need to look for LCA in left 
        // subtree and return node left_lca or null if not found
        // Case 2: left_lca == null && right_lca != null
        // this express LCA in right subtree, and we only need to look for LCA in right
        // subtree and return node right_lca or null if not found
        // Case 3: left_lca != null && right_lca != null
        // this express current node is LCA because p and q separately exist on left and
        // right subtree.
        // Now we need to check Case 3 first, and if not satisfy, then use trinary
        // express to return either node left_lca or right_lca as Case 1 or Case 2
        TreeNode left_lca = lowestCommonAncestor(root.left, p, q);
        TreeNode right_lca = lowestCommonAncestor(root.right, p, q);
        
        // First check Case 3
        if(left_lca != null && right_lca != null) {
            return root;
        }
        
        // Then check Case 1 and 2
        return left_lca != null ? left_lca : right_lca;
    }
}

// How to think about recursion on Tree ?
// Lowest Common Ancestor Between 2 Binary Tree Nodes (A Recursive Approach)
// A much better and detailed explain
// Refer to
// Back To Back SWE
// https://www.youtube.com/watch?v=py3R23aAPCA
/**
 When you try to figure out these recursive problem trying to learn recursion
in general, this is the key whatever I am trying to resolve a tree problem,
i have a tree, what is my focus of the tree, and let me show you what you
should focus on, a single node, whenever you are doing a recursive problem,
forget what needs to happen to the whole tree, forget about what needs to
happen at the leaves what needs to happen in the recursive case the base case,
don't worry about any of that, this will save you so much time, because this
has confused me a lot of times when i try to learn in the beginning and try to
figure out how these things works. Only focus on a single single node, what
needs to happen at node for me to solve the problem ? This node represents a
function call and what we're going to see is this node is the key to getting us
to an answer and what we need to do is define what I always say and call a policy
which is basically our code in the recursive function to handle. Given a certain
state what needs to happen at one node that's all I care about and we're going
to investigate that right now. Ok, so let's look at a single node and let's
look at a left subtree and a right subtree, this is how this is an overaching
framework for seeing any tree problem. We will always have a note which we call
root or we call node we could call it anything and then we will have a left
subtree and a right subtree we can do anything with those subtrees. Remember we're
going from the bottom to the top and if I hold a node and I know that I've found
the one of the items to my right, and one of the items to my left, and they're
going to be unique instances, so there's no duplicates here. What I am going to
know is what I am holding is a common ancestor. Is it the lowest common ancestor
though it has to be, our recursion is going from bottom upward so what we know
at that point is if I searched my left I searched my right and I find a node on left
I find a node on right, either x can be left or right, same for y. They are going
to be unique, so there won't be duplicate.
                          root 
                          /  \
                       left   right
                        (x)    (y)
What will happen is this roots must be the lowest common ancestor, right. If I search
the left and I find x if I search the right and I find y, the root has to be the
lowest common ancestor, and what we need to see is the other case that I just said
if we find y and then we find x on the left, now this is the kind of a key to this
product this is a key momnent that we realizing. Again, we broken this down we said
forget about the tree, what needs to happen at this node is I need to search to my left
to your right, what needs to happen is I need to search the left subtree and the right
subtree and what happens is if I find both then that means this lowest common ancestor,
that is the key case where we found our item and what do we return ? we return this node.
The answer to our question when we make a function call is going to be what is the lowest
common ancestor given a root of a tree and two notes to find. This node is the answer to
that question, and we return it upwards and again the code is in the description go
between this and the code to further understand this deeply.
     this is the node to return    --->    root
                                           /  \
                                        left   right
                                         (x)    (y)

So we saw the full case, the case where I find x and then y or y and then x, then the node
is the lowest common ancestor because of the nature of recursion and the laws we defined.
     1.Find both case  --->   root
                              /  \
                           left   right
                           (x/y)  (y/x)

So what if we find one on the left and then nothing on the right, so can this node be the
lowest common ancestor, the root here can't be the lowest common ancestor, I would have to have 
found the why here in this case or if this was why I would have to have found the x
here again, on left / right child node, it could be x or y, you're just saying do I have a
value here and if I have a value here, what that means is this node is an ancestor to one
of the values but its not the lowest common ancestor, what we see here is that we couldn't
find anything on the right so what we do here at this node we return the result from the left,
because this node can't be the lowset common ancestor, we wouldn't want to return the node
we're holding. We would want to return the node to the left so what that node represents is
we're saying this is the node y, from this call I want to return that because I'm going to say 
from roots I can reach y, from root I can reach y. So let's see the other case, and I'm
going to slowly build this up to a final point I'm gonna make. 
     2.Find either but not both  --->    root
                                         /  \
                                     left   right
                                     (x/y)  (nothing)
So we see here found nothing on the left, if I found somthing on the right, that means I
either can reach x or y from this node, so I don't return myself but return the x or the y
whatever I found back upwards saying I can reach this value from this root. 
     2.Find either but not both  --->    root
                                         /  \
                                     left   right
                                     (nothing)  (x/y)
So each call, remember, represents asking what is the lowest common ancestor from this node,
so what we need to string this all together because it's confusing when you see these cases
individually. Let's put it all together into a little code run-through so that you can see 
this kind of happening live.

So we are going to find lowest common ancestor for two nodes and were given a roots, so there's 
our function right there, so what we first need to do is define base cases when do
we have an answer to a search call ? We know the answer to a search call when we have no as
the root that means we can't find a lowest common ancestor we just return null. 
     if(root == null) return null;

And we have an answer when the node I'm grabbing we're grabbing a node with each call, each call 
has a node to work with which is root, if root is either of the values I just returned
this node, I return myself because I have found what I'm searching for, I found either x or y, 
so let's put that right here, 
     if(root.val == x.val || root.val == y.val) return root;

and what we need to do is if neither of these are satisfied, then we need to perform a search
from this node, we need to find either x or y on the left or right, so we put a search right
here, and what's gonna happen is drill downwards, downwards, downwards... recursion, recursion, 
recursion, it's gonna keep going downwards as long as our base cases are not hit, our recursion 
keeps going down.   
     TreeNode leftSearchResult = search(root.left, x, y);
     TreeNode rightSearchResult = search(root.right, x, y);

What happens is we need to analyze our answer when the recursion comes back up, because our
base cases will get hit and our base cases will return back up to us and then we will have to
analyze our results, our results will come back to us because we just drill downwards, base case 
is pushed back upwards. So what we need to do is look back to what we were just talking
about our cases if I get nothing on the left, I just return what I got on the right, if I get
noting on the right, I just return what I got on the left, and I just put those there
     if(leftSearchResult == null) return rightSearchResult;
	    if(rightSearchResult == null) return leftSearchResult;
	 
if both come back then that means the node I'm holding we search to the right I found something we 
search to the left I found something, that means the node I am holding is the lowest common ancestor, 
remember recursions going bottom to top, so we just return ourselves
in that case as final statement below
     return root;

So the whole point of this is we search search search and we return we drill down and then come back 
up because of base cases, and we're going to analyze our left and right, based on the cases we defined. 
We know what our node is and so imagine I find both and I'm the lowest common ancestor, I return myself 
to my parent who says okay I had my left, right search, I have let's say it was on our right, I have 
this result and maybe nothing comes up on the left, and what happens is the node that was the lowest 
common ancestor keeps getting passed upwards and then eventually it's returned as the answer.

So this is basically the understanding behind the recursive algorithm to solve this lowest common ancestor 
problem, and we should look at the time and space complexities right now.
*/
class Solution {
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if(root == null) {
            return null;
        }
        if(root.val == p.val || root.val == q.val) {
            return root;
        }
        TreeNode leftSearchResult = lowestCommonAncestor(root.left, p, q);
        TreeNode rightSearchResult = lowestCommonAncestor(root.right, p, q);
        if(leftSearchResult == null) {
            return rightSearchResult;
        }
        if(rightSearchResult == null) {
            return leftSearchResult;
        }
        return root;
    }
}

// Solution 2: Iterative
// Refer to
// https://xuyiruan.com/2019/02/06/Lowest-Common-Ancestor-Series/
/**
 DFS iterative Algorithm
 1.traverse tree itertively with stack to look for p and q
 2.use HashMap<TreeNode, TreeNode> parent to record <child, parent> relation.
 3.once both p and q found (child, parent relation for both p and q found)
 4.add p's all ancester to a Set
 5.traverse q's ancesters in order, and first shared ancester is the shared LCA
*/
// https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree/discuss/65236/JavaPython-iterative-solution
// https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree/discuss/303992/JAVA%3A-iterative-and-recursive-with-detailed-explanation
/**
  Non Recursive BFS Approach: TC - O(n)
  Step 1: traverse all the tree and save the node-parent pairs (in a hash map) for all the nodes in the 
          tree (till the point we encounter BOTH p and q)
  Step 2: start from p and save all the parents of p till root (including p and root) in a hash set
  Step 3: start from q and and navigate through all the parents till root and whenever you see a parent 
          that is present in the set (common ancestor for p and q), that is LCA
*/
class Solution {
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        // [key, value] -> [child, parent]
        Map<TreeNode, TreeNode> parents = new HashMap<TreeNode, TreeNode>();
        Stack<TreeNode> stack = new Stack<TreeNode>();
        // Pre-order traverse
        parents.put(root, null);
        stack.push(root);
        while(!parents.containsKey(p) || !parents.containsKey(q)) {
            TreeNode node = stack.pop();
            if(node.left != null) {
                parents.put(node.left, node);
                stack.push(node.left);
            }
            if(node.right != null) {
                parents.put(node.right, node);
                stack.push(node.right);
            }
        }
        // Find all parents from 'p' till root
        Set<TreeNode> parents_p = new HashSet<TreeNode>();
        while(p != null) {
            parents_p.add(p);
            p = parents.get(p);
        }
        // Starting from 'q', get all its parents till the root and 
        // in the process, whenever you see the parent node that is 
        // already present in the 'parents_p' set, that's the LCA node
        while(!parents_p.contains(q)) {
            q = parents.get(q);
        }
        return q;
    }
}
























































































https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree/

Given a binary tree, find the lowest common ancestor (LCA) of two given nodes in the tree.
According to the definition of LCA on Wikipedia: “The lowest common ancestor is defined between two nodes p and q as the lowest node in 
T that has both p and q as descendants (where we allow a node to be a descendant of itself).”

Example 1:


Input: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 1
Output: 3
Explanation: The LCA of nodes 5 and 1 is 3.

Example 2:


Input: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 4
Output: 5t
Explanation: The LCA of nodes 5 and 4 is 5, since a node can be a descendant of itself according to the LCA definition.

Example 3:
Input: root = [1,2], p = 1, q = 2
Output: 1
 
Constraints:
- The number of nodes in the tree is in the range [2, 10^5].
- -10^9 <= Node.val <= 10^9
- All Node.val are unique.
- p != q
- p and q will exist in the tree.
--------------------------------------------------------------------------------
Attempt 1: 2022-12-03
Solution 1:  Divide and Conquer (30 min)
/** 
 * Definition for a binary tree node. 
 * public class TreeNode { 
 *     int val; 
 *     TreeNode left; 
 *     TreeNode right; 
 *     TreeNode(int x) { val = x; } 
 * } 
 */ 
class Solution { 
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) { 
        if(root == null || root == p || root == q) { 
            return root; 
        } 
        TreeNode left = lowestCommonAncestor(root.left, p, q); 
        TreeNode right = lowestCommonAncestor(root.right, p, q); 
        if(left != null && right != null) { 
            return root; 
        } 
        if(left != null) { 
            return left; 
        } else { 
            return right; 
        } 
    } 
}

Complexity Analysis 
Time Complexity: O(N). Where N is the number of nodes in the binary tree. In the worst case we might be visiting all the nodes of the binary tree. 
Space Complexity: O(N). This is because the maximum amount of space utilized by the recursion stack would be N since the height of a skewed binary tree could be N.

Refer to
https://segmentfault.com/a/1190000003509399
深度优先标记
复杂度
时间 O(h) 空间 O(h) 递归栈空间
思路
我们可以用深度优先搜索，从叶子节点向上，标记子树中出现目标节点的情况。如果子树中有目标节点，标记为那个目标节点，如果没有，标记为null。显然，如果左子树、右子树都有标记，说明就已经找到最小公共祖先了。如果在根节点为p的左右子树中找p、q的公共祖先，则必定是p本身。
换个角度，可以这么想：如果一个节点左子树有两个目标节点中的一个，右子树没有，那这个节点肯定不是最小公共祖先。如果一个节点右子树有两个目标节点中的一个，左子树没有，那这个节点肯定也不是最小公共祖先。只有一个节点正好左子树有，右子树也有的时候，才是最小公共祖先。
代码
public class Solution { 
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) { 
        //发现目标节点则通过返回值标记该子树发现了某个目标结点 
        if(root == null || root == p || root == q) return root; 
        //查看左子树中是否有目标结点，没有为null 
        TreeNode left = lowestCommonAncestor(root.left, p, q); 
        //查看右子树是否有目标节点，没有为null 
        TreeNode right = lowestCommonAncestor(root.right, p, q); 
        //都不为空，说明左右子树都有目标结点，则公共祖先就是本身 
        if(left!=null&&right!=null) return root; 
        //如果发现了目标节点，则继续向上标记为该目标节点 
        return left == null ? right : left; 
    } 
}

Refer to
https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree/discuss/1405170/4-STEPS-SOLUTION-or-Easy-Heavily-EXPLAINED-with-COMPLEXITIES
EXPLANATION
- We'll do just normal tree traversal of the given binary tree recursivly.
- For finding LCA (lowest common ancestor) we've following conditions for every node in the tree,
- But before that, this solutions works under the assumption that both Node 'p' & Node 'q' will present in the tree...
- if single one of the node is present in the tree, it'll not work or simply return null.
CONDITIONS: -
1.if current node is same as 'p' OR 'q'.
2.if one of it's subtrees contains 'p' and other 'q' (subtrees means, left_sub_tree and right_sub_tree).
3.if one of it's subtree contains both 'p' & 'q'.
4.if none of it's subtrees contains any of 'p' & 'q'.
- Note: that's a tricky implementation, but works well under the assumption that 'p' & 'q' will be definitely present.

EFFICIENT SOLUTION
- Runtime: 15ms [C++]
TreeNode* lowestCommonAncestor(TreeNode* root, TreeNode* p, TreeNode* q) {
    if(root == NULL) return NULL;
    if(root->val == p->val || root->val == q->val) return root;       // 👉 FIRST CONDITION...

    TreeNode* lca1 = lowestCommonAncestor(root->left, p, q);          // traverse on the left part of the tree
    TreeNode* lca2 = lowestCommonAncestor(root->right, p, q);         // traverse on the right part of the tree

    if(lca1 != NULL && lca2 != NULL) return root;                     // 👉 SECOND CONDITION... (IF BOTH SUB-TREE CONTAINS 'p' & 'q' RESPECTIVELY)
    if(lca1 != NULL) return lca1;                                     // 👉 THIRD CONDITION...
    return lca2;                                                      // 👉 FOURTH CONDITION...
}
TIME COMPLEXITY : 
O(N),Where N : total number of nodes in the BT
SPACE COMPLEXITY :
O(H) or O(N) (Worse Case), Where H : total height of tree for recursion stack
--------------------------------------------------------------------------------
Solution 2:  Promote Divide and Conquer with flag when both p and q in same left subtree to skip redundant scanning in right subtree (30 min)
/** 
 * Definition for a binary tree node. 
 * public class TreeNode { 
 *     int val; 
 *     TreeNode left; 
 *     TreeNode right; 
 *     TreeNode(int x) { val = x; } 
 * } 
 */ 
class Solution { 
    boolean found = false; 
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) { 
        if(found) { 
            return null; 
        } 
        if(root == null || root == p || root == q) { 
            return root; 
        } 
        TreeNode left = lowestCommonAncestor(root.left, p, q); 
        TreeNode right = lowestCommonAncestor(root.right, p, q); 
        if(left != null && right != null) { 
            found = true; 
            return root; 
        } 
        if(left != null) { 
            return left; 
        } else { 
            return right; 
        } 
    } 
}

Complexity Analysis 
Time Complexity: O(N). Where N is the number of nodes in the binary tree. In the worst case we might be visiting all the nodes of the binary tree.  
Space Complexity: O(N). This is because the maximum amount of space utilized by the recursion stack would be N since the height of a skewed binary tree could be N.

Refer to
https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree/discuss/65226/My-Java-Solution-which-is-easy-to-understand/112901
This is a good solution but un-necessarily does the extra work of checking the whole tree if we have already found the ancestor in the left subtree.

https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree/discuss/65226/My-Java-Solution-which-is-easy-to-understand/184794
You can add some flags when you've already found both p q under a same subtree, if you want to.

https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree/discuss/65226/My-Java-Solution-which-is-easy-to-understand/195686
boolean found = false; 
public TreeNode helper(TreeNode root, TreeNode p, TreeNode q) 
{ 
    if(found||root==null) return null; 
    TreeNode left = helper(root.left, p, q); 
    TreeNode right = helper(root.right, p, q); 
     
    if(left!=null&&right!=null)  
    { 
        found = true; 
        return root; 
    } 
    if(root.val==p.val||root.val==q.val)  
        return root; 
    else if(left!=null)  
        return left; 
    else if(right!=null)  
        return right; 
     
    return null; 
}

Test Case:
/** 
* e.g
*           3 
*         /   \  
*        9     20 
*       / \   /  \ 
*      8  10 15   7 
*
* Test with 8 and 10 both under left subtree, after adding flag it will skip scanning right subtree
*/


class Solution {
    public static void main(String[] args) { 
       Test b = new Test(); 
       TreeNode three = b.new TreeNode(3); 
       TreeNode nine = b.new TreeNode(9); 
       TreeNode tweeten = b.new TreeNode(20); 
       TreeNode fifteen = b.new TreeNode(15); 
       TreeNode seven = b.new TreeNode(7); 
       TreeNode eight = b.new TreeNode(8); 
       TreeNode ten = b.new TreeNode(10); 
     
       three.left = nine; 
       three.right = tweeten; 
       tweeten.left = fifteen; 
       tweeten.right = seven; 
       nine.left = eight; 
       nine.right = ten;  
       TreeNode result = b.lowestCommonAncestor(three, eight, ten); 
       System.out.println(result); 
    }

    boolean found = false; 
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) { 
        if(found) { 
            return null; 
        } 
        if(root == null || root == p || root == q) { 
            return root; 
        } 
        TreeNode left = lowestCommonAncestor(root.left, p, q); 
        TreeNode right = lowestCommonAncestor(root.right, p, q); 
        if(left != null && right != null) { 
            found = true; 
            return root; 
        } 
        if(left != null) { 
            return left; 
        } else { 
            return right; 
        } 
    }
}

--------------------------------------------------------------------------------
Solution 3:  BFS iterative traversal (30 min)
/** 
 * Definition for a binary tree node. 
 * public class TreeNode { 
 *     int val; 
 *     TreeNode left; 
 *     TreeNode right; 
 *     TreeNode(int x) { val = x; } 
 * } 
 */ 
class Solution { 
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) { 
        // {child -> parent} 
        Map<TreeNode, TreeNode> map = new HashMap<TreeNode, TreeNode>(); 
        Queue<TreeNode> queue = new LinkedList<TreeNode>(); 
        map.put(root, null); 
        queue.offer(root); 
        while(!map.containsKey(p) || !map.containsKey(q)) { 
            TreeNode node = queue.poll(); 
            if(node.left != null) { 
                map.put(node.left, node); 
                queue.offer(node.left); 
            } 
            if(node.right != null) { 
                map.put(node.right, node); 
                queue.offer(node.right); 
            } 
        } 
        Set<TreeNode> p_parents = new HashSet<TreeNode>(); 
        while(p != null) { 
            p_parents.add(p); 
            p = map.get(p); 
        } 
        while(!p_parents.contains(q)) { 
            q = map.get(q); 
        } 
        return q; 
    } 
}

Complexity Analysis 
Time Complexity : O(N). Where N is the number of nodes in the binary tree. In the worst case we might be visiting all the nodes of the binary tree. 
Space Complexity : O(N). In the worst case space utilized by the stack(queue), the parent pointer dictionary and the ancestor set, would be N each, since the height of a skewed binary tree could be N.

Refer to
https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree/discuss/65236/JavaPython-iterative-solution
To find the lowest common ancestor, we need to find where is p and q and a way to track their ancestors. A parent pointer for each node found is good for the job. After we found both p and q, we create a set of p's ancestors. Then we travel through q's ancestors, the first one appears in p's is our answer.

Iterative Algorithm
 1.traverse tree iteratively with stack (queue) to look for p and q
 2.use HashMap<TreeNode, TreeNode> parent to record <child, parent> relation.
 3.once both p and q found (child, parent relation for both p and q found)
 4.add p's all ancestor to a Set
 5.traverse q's ancestors in order, and first shared ancestor is the shared LCA
public class Solution { 
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) { 
        Map<TreeNode, TreeNode> parent = new HashMap<>(); 
        Deque<TreeNode> stack = new ArrayDeque<>(); 
        parent.put(root, null); 
        stack.push(root); 
        while (!parent.containsKey(p) || !parent.containsKey(q)) { 
            TreeNode node = stack.pop(); 
            if (node.left != null) { 
                parent.put(node.left, node); 
                stack.push(node.left); 
            } 
            if (node.right != null) { 
                parent.put(node.right, node); 
                stack.push(node.right); 
            } 
        } 
        Set<TreeNode> ancestors = new HashSet<>(); 
        while (p != null) { 
            ancestors.add(p); 
            p = parent.get(p); 
        } 
        while (!ancestors.contains(q)) 
            q = parent.get(q); 
        return q; 
    } 
}

Instead of Stack, BFS more prefer Queue to traversal
Refer to
https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree/discuss/65236/JavaPython-iterative-solution/66954
TreeNode* lowestCommonAncestor(TreeNode* root, TreeNode* p, TreeNode* q) { 
    unordered_map<TreeNode*, TreeNode*> parents; 
    parents[root] = nullptr; 
    queue<TreeNode*> qu; 
    qu.push(root); 
    while (!parents.count(p) || !parents.count(q)) { 
        int qsize = (int)qu.size(); 
        for (int i = 0; i < qsize; ++i) { 
            auto node = qu.front(); 
            qu.pop(); 
            if (node -> left) { 
                parents[node -> left] = node; 
                qu.push(node -> left); 
            } 
            if (node -> right) { 
                parents[node -> right] = node; 
                qu.push(node -> right); 
            } 
        } 
    } 
    unordered_set<TreeNode*> ancestors; 
    while (p) ancestors.insert(p), p = parents[p]; 
    while (q && !ancestors.count(q)) q = parents[q]; 
    return q; 
}
      
Refer to
L865.Smallest Subtree with all the Deepest Nodes (Ref.L236)
