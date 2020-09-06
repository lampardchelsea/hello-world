/**
 Refer to
 https://leetcode.com/problems/path-in-zigzag-labelled-binary-tree/
 In an infinite binary tree where every node has two children, the nodes are labelled in row order.
                                             1
                                     3              2
                                 4      5        6      7
                               15 14  13 12    11 10   9  8
 
In the odd numbered rows (ie., the first, third, fifth,...), the labelling is left to right, while in the even numbered rows 
(second, fourth, sixth,...), the labelling is right to left.
Given the label of a node in this tree, return the labels in the path from the root of the tree to the node with that label.

Example 1:                 
Input: label = 14
Output: [1,3,4,14]

Example 2:
Input: label = 26
Output: [1,2,6,10,26]

Constraints:
1 <= label <= 10^6
*/

// Solution 1: Math
// Refer to
// https://leetcode.com/problems/path-in-zigzag-labelled-binary-tree/discuss/323312/Simple-solution-in-java-(Using-properties-of-complete-binary-tree)-(O-log-N)
/**
Calculate current depth of the label
Calculate offset (for each depth, values lie from 2^d -> 2^(d+1) -1
Find the real parent based on offset
Repeat until we reach 1
e.g. parent of 14 is 4
depth = 3, values in this depth lie from 8 to 15 (since it is a complete binary tree)
offset = 15 - 14 = 1
real parent of 14 = parent of ( 8 + offset ) = parent (9) = 9/2 = 4
*/

// https://leetcode.com/problems/path-in-zigzag-labelled-binary-tree/discuss/323312/Simple-solution-in-java-(Using-properties-of-complete-binary-tree)-(O-log-N)/298413
/**
I also hope to help other confused people to understand this solution.

int d = (int)(Math.log(parent) / Math.log(2))
acquire each parent node's the number of levels (assume the node '1' is located in 0-th level, the node '2' and '3' is located in 1-th level and so on)
int offset = (int)Math.pow(2, d + 1) - 1 - parent
figure out the remaining node at the following the parent (according to question's sequence)
(int)Math.pow(2, d + 1) - 1 means that this complete binary tree ending with this level (also full binary tree) total the number of nodes
parent = ((int)Math.pow(2, d) + offset) / 2
acquire the node's parent node
(int)Math.pow(2, d) means that acquire all number of nodes in this level this statement needs to portray a sketch in order to describe it better

Levels																
0								                                1								
1				                3								                                2				
2		        4				                5				                 6				             7		
3	15(8 + 0)		14(8 + 1)		13(8 + 2)		12(8 + 3)		11(8 + 4)		10(8 + 5)		9(8 + 6)		8(8 + 7)	

Levels															
0								                                1							
1				                2								                                3			
2		        4				                5				                 6				             7	
3	8(8 + 0)		9(8 + 1)		10(8 + 2)		11(8 + 3)		12(8 + 4)		13(8 + 5)		14(8 + 6)		15(8 + 7)
Attention:

parent label is only used to attain this parent node's level
Actually, this is a conversion
It is so regretful not to upload the sketch in the LeetCode Comments. Verbal description is not as efficient as a sketch.
*/

class Solution {
    public List<Integer> pathInZigZagTree(int label) {
        LinkedList<Integer> result = new LinkedList<Integer>();
        result.addFirst(label);
        int parent = label;
        while(parent != 1) {
            int depth = (int)(Math.log(parent) / Math.log(2));
            int offset = (int)Math.pow(2, depth + 1) - parent - 1;
            parent = ((int)Math.pow(2, depth) + offset) / 2;
            result.addFirst(parent);
        }
        return result;
    }
}
