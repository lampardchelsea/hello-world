import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

/**
 * Refer to
 * https://leetcode.com/problems/interleaving-string/#/description
 *  Given s1, s2, s3, find whether s3 is formed by the interleaving of s1 and s2.
	For example,
	Given:
	s1 = "aabcc",
	s2 = "dbbca",
	
	When s3 = "aadbbcbcac", return true.
	When s3 = "aadbbbaccc", return false. 
 * 
 * Solution
 * https://discuss.leetcode.com/topic/6562/8ms-c-solution-using-bfs-with-explanation
 * If we expand the two strings s1 and s2 into a chessboard, then this problem can be transferred into 
 * a path seeking problem from the top-left corner to the bottom-right corner. The key is, each cell 
 * (y, x) in the board corresponds to an interval between y-th character in s1 and x-th character in s2. 
 * And adjacent cells are connected with like a grid. A BFS can then be efficiently performed to find the path.
 * 
 * Better to illustrate with an example here:
 * Say s1 = "aab" and s2 = "abc". s3 = "aaabcb". Then the board looks like

	o--a--o--b--o--c--o
	|     |     |     |
	a     a     a     a
	|     |     |     |
	o--a--o--b--o--c--o
	|     |     |     |
	a     a     a     a
	|     |     |     |
	o--a--o--b--o--c--o
	|     |     |     |
	b     b     b     b
	|     |     |     |
	o--a--o--b--o--c--o

 * Each "o" is a cell in the board. We start from the top-left corner, and try to move right or down. 
 * If the next char in s3 matches the edge connecting the next cell, then we're able to move. When we 
 * hit the bottom-right corner, this means s3 can be represented by interleaving s1 and s2. One possible 
 * path for this example is indicated with "x"es below:

	x--a--x--b--o--c--o
	|     |     |     |
	a     a     a     a
	|     |     |     |
	o--a--x--b--o--c--o
	|     |     |     |
	a     a     a     a
	|     |     |     |
	o--a--x--b--x--c--x
	|     |     |     |
	b     b     b     b
	|     |     |     |
	o--a--o--b--o--c--x

 * Note if we concatenate the chars on the edges we went along, it's exactly s3. And we went through all 
 * the chars in s1 and s2, in order, exactly once.
 * Therefore if we view this board as a graph, such path finding problem is trivial with BFS. I use an 
 * unordered_map to store the visited nodes, which makes the code look a bit complicated. But a vector 
 * should be enough to do the job.
 * 
 * Although the worse case timeis also O(mn), typically it doesn't require us to go through every node 
 * to find a path. Therefore it's faster than regular DP than average.
 * 
 * https://discuss.leetcode.com/topic/30127/summary-of-solutions-bfs-dfs-dp 
 * BFS solution (6ms)
 * Imagine a grid, which x-axis and y-axis are s1 and s2, matching s3 is the same as
 * finding a path from (0,0) to (len1, len2). It actually becomes a
 * BFS on grid. Since we don't need exact paths, a HashSet of
 * coordinates is used to eliminate duplicated paths.
 */
public class InterleavingString {
	public boolean isInterleave(String s1, String s2, String s3) {
        int len1 = s1.length();
        int len2 = s2.length();
        int len3 = s3.length();
        if(len1 + len2 != len3) {
            return false;
        }
        Set<Integer> set = new HashSet<Integer>();
        Queue<Integer> queue = new LinkedList<Integer>();
        int matched = 0;
        queue.offer(0);
        while(queue.size() > 0 && matched < len3) {
            int size = queue.size();
            for(int i = 0; i < size; i++) {
                int val = queue.poll();
                int posX = val / len3;
                int posY = val % len3;
                // Q: I don't understand why you use cache. contains(posX * s3.length() + posY) 
                //    in the DFS solution? Any reason why you pick posX * s3.length() + posY 
                //    as the signature?
                // A: You are probably aware that we can represent a position in a matrix 
                //    as y * width + x or x * height + y. This is similar, except that I'm 
                //    using s3.length() instead of s2.length(). There is no particular reason 
                //    to choose s3.length() over s2.length(). Both should work just fine, 
                //    since both are guaranteed to be larger than posY.
                if(posX < len1 && s1.charAt(posX) == s3.charAt(matched)) {
                    int key = (posX + 1) * len3 + posY;
                    if(!set.contains(key)) {
                        set.add(key);
                        queue.offer(key);
                    }
                }
                if(posY < len2 && s2.charAt(posY) == s3.charAt(matched)) {
                    int key = posX * len3 + (posY + 1);
                    if(!set.contains(key)) {
                        set.add(key);
                        queue.offer(key);
                    }
                }
            }
            matched++;
        }
        return queue.size() > 0 && matched == len3;
    }
	
	public static void main(String[] args) {
		
	}
}
