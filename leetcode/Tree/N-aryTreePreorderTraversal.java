/**
 Given an n-ary tree, return the preorder traversal of its nodes' values.
For example, given a 3-ary tree:
             1
         3   2   4
       5  6

Return its preorder traversal as: [1,3,5,6,2,4].

Note:
Recursive solution is trivial, could you do it iteratively?
*/
// Refer to
// https://leetcode.com/articles/introduction-to-n-ary-trees/
/**
                    A
              B     C     D
                  E   F   G

 1. Preorder Traversal
In a N-ary tree, preorder means visit the root node first and then traverse the 
subtree rooted at its children one by one. For instance, the preorder of the 
3-ary tree above is: A->B->C->E->F->D->G.

2. Postorder Traversal
In a N-ary tree, postorder means traverse the subtree rooted at its children 
first and then visit the root node itself. For instance, the postorder of the 
3-ary tree above is: B->E->F->C->G->D->A.

3. Level-order Traversal
Level-order traversal in a N-ary tree is the same with a binary tree. Typically, 
when we do breadth-first search in a tree, we will traverse the tree in level order. 
For instance, the level-order of the 3-ary tree above is: A->B->C->D->E->F->G.

===============================================================================================
1. "Top-down" Solution
"Top-down" means that in each recursion level, we will visit the node first to come up 
with some values, and parse these values to its children when calling the function recursively.
A typical "top-down" recursion function top_down(root, params) works like this:
1. return specific value for null node
2. update the answer if needed                              // answer <-- params
3. for each child node root.children[k]:
4.      ans[k] = top_down(root.children[k], new_params[k])  // new_params <-- root.val, params
5. return the answer if needed                              // answer <-- all ans[k]

2. "Bottom-up" Solution
"Bottom-up" means that in each recursion level, we will firstly call the functions recursively 
for all the children nodes and then come up with the answer according to the return values 
and the value of the root node itself.
A typical "bottom-up" recursion function bottom_up(root) works like this:
1. return specific value for null node
2. for each child node root.children[k]:
3.      ans[k] = bottom_up(root.children[k])    // call function recursively for all children
4. return answer                                // answer <- root.val, all ans[k]

===============================================================================================
Convert a N-ary Tree to a Binary Tree
There are a lot of solutions to convert a N-ary tree to a binary tree. We only introduce one 
classical solution.
The strategy follows two rules: 
1. The left child of each node in the binary tree is the next sibling of the node in the N-ary tree. 
2. The right child of each node in the binary tree is the first child of the node in the N-ary tree.

Using this strategy, you can simply convert a N-ary tree to a binary tree recursively. 
Also, you can easily recover the N-ary tree from the binary tree you converted.
The recursion recovery strategy for each node is: 
1. Deal with its children recursively. 
2. Add its left child as the next child of its parent if it has a left child. 
3. Add its right child as the first child of the node itself if it has a right child.
*/
