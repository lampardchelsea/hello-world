/**
 Refer to
 https://leetcode.com/problems/binary-tree-coloring-game/
 Two players play a turn based game on a binary tree.  We are given the root of this binary tree, and the number of 
 nodes n in the tree.  n is odd, and each node has a distinct value from 1 to n.

Initially, the first player names a value x with 1 <= x <= n, and the second player names a value y with 1 <= y <= n 
and y != x.  The first player colors the node with value x red, and the second player colors the node with value y blue.

Then, the players take turns starting with the first player.  In each turn, that player chooses a node of their color 
(red if player 1, blue if player 2) and colors an uncolored neighbor of the chosen node (either the left child, right 
child, or parent of the chosen node.)

If (and only if) a player cannot choose such a node in this way, they must pass their turn.  If both players pass their 
turn, the game ends, and the winner is the player that colored more nodes.

You are the second player.  If it is possible to choose such a y to ensure you win the game, return true.  If it is not 
possible, return false.

Example 1:
Input: root = [1,2,3,4,5,6,7,8,9,10,11], n = 11, x = 3
Output: true
Explanation: The second player can choose the node with value 2.

Constraints:
root is the root of a binary tree with n nodes and distinct node values from 1 to n.
n is odd.
1 <= x <= n <= 100
*/

// Solution 1: 3 subtrees (root, right, left) relations + DFS traversal with global parameter to record values
// Refer to
// https://leetcode.com/problems/binary-tree-coloring-game/discuss/350570/JavaC%2B%2BPython-Simple-recursion-and-Follow-Up
/**
Intuition
The first player colors a node,
there are at most 3 nodes connected to this node.
Its left, its right and its parent.
Take this 3 nodes as the root of 3 subtrees.

The second player just color any one root,
and the whole subtree will be his.
And this is also all he can take,
since he cannot cross the node of the first player.

Explanation
count will recursively count the number of nodes,
in the left and in the right.
n - left - right will be the number of nodes in the "subtree" of parent.
Now we just need to compare max(left, right, parent) and n / 2

Complexity
Time O(N)
Space O(height) for recursion

Fun Moment of Follow-up:
Alex and Lee are going to play this turned based game.
Alex draw the whole tree. root and n will be given.
Note the n will be odd, so no tie in the end.

Now Lee says that, this time he wants to color the node first.

1.Return true if Lee can ensure his win, otherwise return false
2.Could you find the set all the nodes,
  that Lee can ensure he wins the game?
  Return the size of this set.
3.What is the complexity of your solution?

Solution to the follow up 1
Yes, similar to the solution 877. Stone Game
Just return true.
But this time, Lee's turn to ride shotgun today! Bravo.

Java/C++
    return true;
Python:
    return True
    
Solution to the follow up 2
There will one and only one node in the tree,
that can Lee's win.

Java/C++
    return 1
Python:
    return 1
    
Solution to the follow up 3
It can be solve in O(N).
*/
class Solution {
    int left;
    int right;
    int val;
    public boolean btreeGameWinningMove(TreeNode root, int n, int x) {
        if(root == null) {
            return false;
        }
        val = x;
        count(root);
        return Math.max(n - left - right - 1, Math.max(left, right)) > n / 2;
    }
    
    private int count(TreeNode node) {
        if(node == null) {
            return 0;
        }
        int l = count(node.left);
        int r = count(node.right);
        // Record count of subtree nodes at certain node with given value
        if(node.val == val) {
            left = l;
            right = r;
        }
        return l + r + 1;
    }
}

// Solution 2: Another style of recursive + DFS 2 level traversal
// https://leetcode.com/problems/binary-tree-coloring-game/discuss/367682/Simple-Clean-Java-Solution
/**
Short explanation:
When you find the selected node, there are three different paths you can block: left right parent In order to guarantee 
your win, one of those paths should include more nodes than the sum of other two paths.
*/
class Solution {
    public boolean btreeGameWinningMove(TreeNode root, int n, int x) {
        if(root == null) {
            return false;
        }
        if(root.val == x) {
            int left = count(root.left);
            int right = count(root.right);
            int parent = n - left - right - 1;
            return parent > (left + right) || left > (parent + right) || right > (left + parent);
        }
        return btreeGameWinningMove(root.left, n, x) || btreeGameWinningMove(root.right, n, x);
    }
    
    private int count(TreeNode node) {
        if(node == null) {
            return 0;
        }
        int left = count(node.left);
        int right = count(node.right);
        return left + right + 1;
    }
}


