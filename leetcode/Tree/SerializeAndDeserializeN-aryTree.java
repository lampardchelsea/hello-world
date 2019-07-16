/**
 Refer to
 http://shibaili.blogspot.com/2018/11/428-serialize-and-deserialize-n-ary-tree.html
 Serialization is the process of converting a data structure or object into a sequence of bits so that it 
 can be stored in a file or memory buffer, or transmitted across a network connection link to be reconstructed 
 later in the same or another computer environment.
Design an algorithm to serialize and deserialize an N-ary tree. An N-ary tree is a rooted tree in which each 
node has no more than N children. There is no restriction on how your serialization/deserialization algorithm 
should work. You just need to ensure that an N-ary tree can be serialized to a string and this string can be 
deserialized to the original tree structure.

For example, you may serialize the following 3-ary tree
                   1
               3   2   4
             5   6

as [1 [3[5 6] 2 4]]. You do not necessarily need to follow this format, so please be creative and come up 
with different approaches yourself.

Note:

N is in the range of [1, 1000]
Do not use class member/global/static variables to store states. Your serialize and deserialize algorithms should be stateless.
*/
class Solution {
    public String serialize(Node root) {
        if(root == null) {
            return "";
        }
        Queue<Node> q = new LinkedList<Node>();
        StringBuilder sb = new StringBuilder();
        sb.append(Integer.toString(root.val)).append(",#,");
        q.offer(root);
        while(!q.isEmpty()) {
            Node node = q.poll();
            for(Node n : node.children) {
               sb.append(Integer.toString(n.val)).append(",");
               q.add(n);
            }
            sb.append("#,");
        }
        return sb.toString();
    }
  
    public Node deserialize(String data) {
        
    }
}
