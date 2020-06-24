/**
 Refer to
 https://javabypatel.blogspot.com/2015/08/construct-binary-tree-from-inorder-and-level-order-traversals.html
Let us first understand what we want to achieve? what is the input and what will be the expected output?
Question:
Two traversals are given as input,
int[] inOrder =    { 4, 2, 6, 5, 7, 1, 3 };
int[] levelOrder = { 1, 2, 3, 4, 5, 6, 7 };
By using above 2 given In order and Level Order traversal, construct Binary Tree like shown below,
                1
               / \
              2   3
            /  \
           4    5
               /  \
              6    7 
*/

// Solution 1:
// Refer to
// https://www.techiedelight.com/construct-binary-tree-from-inorder-level-order-traversals/
// https://javabypatel.blogspot.com/2015/08/construct-binary-tree-from-inorder-and-level-order-traversals.html
/**
Algorithm
We just saw that first element of Level order traversal is root node.
Step 1:
Read the first element from Level order traversal which is root node.
Now, we come to know which is root node.(in our case it is 1)
Step 2: 
Search the same element (element 1) in In-order traversal, 
when the element is found in In-order traversal, then we can very well say that all the elements present 
before the node found are Left children/sub-tree and all the elements present after the node found are 
Right children/sub-tree of node found. (as in In-order traversal Left child's are read first and then 
root node and then right nodes.)
In our example,  
(4, 2, 6, 5, 7)  will be part of Left Sub-tree of Node 1.
(3) will be part of Right Sub-tree of Node 1.
Now we got the fresh inOrder array (4, 2, 6, 5, 7)  and (3)
Repeat the  Step 1 and Step 2 for new inOrder arrays.
So, keeping the above rule in mind, 
*/



