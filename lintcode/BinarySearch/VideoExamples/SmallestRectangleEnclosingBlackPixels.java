/**
 * An image is represented by a binary matrix with 0 as a white pixel and 1 as a black pixel. 
 * The black pixels are connected, i.e., there is only one black region. Pixels are connected 
 * horizontally and vertically. Given the location (x, y) of one of the black pixels, return 
 * the area of the smallest (axis-aligned) rectangle that encloses all black pixels.
    For example, given the following image:
    [
      "0010",
      "0110",
      "0100"
    ]
    and x = 0, y = 2,
    Return 6
    
 * Solution
 * Refer to
 * https://www.jiuzhang.com/solutions/smallest-rectangle-enclosing-black-pixels
 *
 * Refer to
 * https://discuss.leetcode.com/topic/29006/c-java-python-binary-search-solution-with-explanation
 * This means we can do a binary search in each half to find the boundaries, 
 * if we know one black pixel's position. And we do know that.
 * To find the left boundary, do the binary search in the [0, y) range and find the first 
 * column vector who has any black pixel.
 * To determine if a column vector has a black pixel is O(m) so the search in total is O(m log n)
 * We can do the same for the other boundaries. The area is then calculated by the boundaries.
 * Thus the algorithm runs in O(m log n + n log m)
*/
public class SmallestRectangleEnclosingBlackPixels {
	public int minArea(char[][] image, int x, int y) {
        // Check null and empty case
        if(image == null || image.length == 0 || image[0].length == 0) {
           return 0;
        }
        int rows = image.length;
        int columns = image[0].length;
        int left = findLeft(image, 0, y);
        int right = findRight(image, y, columns - 1);
        int top = findTop(image, 0, x);
        int bottom = findBottom(image, x, rows - 1);
        // Don't forget '+ 1'
        return (bottom - top + 1) * (right - left + 1);
    }
   
    public boolean isEmptyRow(char[][] image, int rowNum) {
        for(int i = 0; i < image[0].length; i++) {
            if(image[rowNum][i] == '1') {
                return false;
            }
        }
        return true;
    }
    
    public boolean isEmptyColumn(char[][] image, int colNum) {
        for(int i = 0; i < image.length; i++) {
            if(image[i][colNum] == '1') {
                return false;
            }
        }
        return true;
    }
    
    // Important condition: The black pixels are connected 
    // Find the first line between [0, x] not empty
    public int findTop(char[][] image, int start, int end) {
        while(start + 1 < end) {
            int mid = start + (end - start) / 2;
            // If current row not empty, continuously 
            // search its upper part, cut lower part
            if(!isEmptyRow(image, mid)) {
               end = mid;
            } else {
               start = mid;
            }
        }
        // Note: To find the first line not empty, if 'start' indexed
        // line is empty, then its very next 'end' indexed line
        // exactly the first line not empty
        if(isEmptyRow(image, start)) {
            return end;
        } else {
            return start;
        }
    }
    
    // Find the last line between [x, rows - 1] not empty
    public int findBottom(char[][] image, int start, int end) {
        while(start + 1 < end) {
            int mid = start + (end - start) / 2;
            // If current row not empty, continuously
            // search its lower part, cut upper part
            if(!isEmptyRow(image, mid)) {
                start = mid;
            } else {
                end = mid;
            }
        }
        // Note: To find the last line not empty if 'end' indexed
        // line is empty, then its very previous 'start' indexed
        // line exactly the last line not empty
        if(isEmptyRow(image, end)) {
            return start;
        } else {
            return end; 
        }
    }
    
    // Find the first column between [0, y] not empty
    public int findLeft(char[][] image, int start, int end) {
        while(start + 1 < end) {
            int mid = start + (end - start) / 2;
            // If current column not empty, continuously
            // search its left part, cut right part
            if(!isEmptyColumn(image, mid)) {
                end = mid;
            } else {
                start = mid;
            }
        }
        if(isEmptyColumn(image, start)) {
            return end;
        } else {
            return start;
        }
    }
    
    // Find the last column between [y, columns - 1] not empty
    public int findRight(char[][] image, int start, int end) {
        while(start + 1 < end) {
            int mid = start + (end - start) / 2;
            // If current column not empty, continuously
            // search its right part, cut left part
            if(!isEmptyColumn(image, mid)) {
                start = mid;
            } else {
                end = mid;
            }
        }
        if(isEmptyColumn(image, end)) {
            return start;
        } else {
            return end;
        }
    }
    
    public static void main(String[] args) {
    	SmallestRectangleEnclosingBlackPixels s = new SmallestRectangleEnclosingBlackPixels();
    	/**
    	 "0010",
         "0110",
         "0100"
         and x = 0, y = 2,
         Should return 6
    	 */
    	// Note: Be careful on char = '0' is different than int = 0 
        char[][] image = {{'0', '0', '1', '0'}, {'0', '1', '1', '0'}, {'0', '1', '0', '0'}};
        int x = 0;
        int y = 2;
    	int result = s.minArea(image, x, y);
    	System.out.println("Minimum area is:" + result);
    }
    
}






Attempt 1: 2022-09-24 

Solution 1: Brute Force (10min)
```
public class Solution { 
    /** 
     * @param image: a binary matrix with '0' and '1' 
     * @param x: the location of one of the black pixels 
     * @param y: the location of one of the black pixels 
     * @return: an integer 
     */ 
    public int minArea(char[][] image, int x, int y) { 
        int rows = image.length; 
        int cols = image[0].length; 
        int left = cols; 
        int right = 0; 
        int up = rows; 
        int down = 0; 
        for(int i = 0; i < rows; i++) { 
            for(int j = 0; j < cols; j++) { 
                if(image[i][j] == '1') { 
                    left = Math.min(left, j); 
                    right = Math.max(right, j); 
                    up = Math.min(up, i); 
                    down = Math.max(down, i); 
                } 
            } 
        } 
        return (down - up + 1) * (right - left + 1); 
    } 
}

Space Complexity: O(1)            
Time Complexity: O(n^2)
```

Refer to
https://www.cnblogs.com/grandyang/p/5268775.html
先来看 Brute Force 的方法，这种方法的效率不高，遍历了整个数组，如果遇到了1，就更新矩形，参见代码如下
```
// Brute force 
class Solution { 
public: 
    int minArea(vector<vector<char>>& image, int x, int y) { 
        int left = y, right = y, up = x, down = x; 
        for (int i = 0; i < image.size(); ++i) { 
            for (int j = 0; j < image[i].size(); ++j) { 
                if (image[i][j] == '1') { 
                    left = min(left, j); 
                    right = max(right, j); 
                    up = min(up, i); 
                    down = max(down, i); 
                } 
            } 
        } 
        return (right - left + 1) * (down - up + 1); 
    } 
};
```

Solution 2: DFS 

Wrong DFS Solution (720min, void return traversal not reserve primitive type values in each recursion round, all primitive type values are gone with code frame popped out from stack peek position when current level recursion end, hence no correct min left, max right, min up, max down values for getting the maximum area)
```
// Wrong Solution
public class Solution { 
    /** 
     * @param image: a binary matrix with '0' and '1' 
     * @param x: the location of one of the black pixels 
     * @param y: the location of one of the black pixels 
     * @return: an integer 
     */ 
     
    public int minArea(char[][] image, int x, int y) { 
        //int rows = image.length; 
        //int cols = image[0].length; 
        //int left = cols; 
        //int right = 0; 
        //int up = rows; 
        //int down = 0; 
        int left = y; 
        int right = y; 
        int up = x; 
        int down = x; 
        helper(image, left, right, up, down, x, y); 
        return (down - up + 1) * (right - left + 1); 
    } 
    private void helper(char[][] image, int left, int right, int up, int down, int x, int y) { 
        // Base case 
        if(x < 0 || x >= image.length || y < 0 || y >= image[0].length || image[x][y] != '1') { 
            return; 
        } 
        // Update left, right, up, down 
        left = Math.min(left, y); 
        right = Math.max(right, y); 
        up = Math.min(up, x); 
        down = Math.max(down, x); 
        // Mark as visited 
        image[x][y] = '2'; 
        // Traversal on four directions 
        int[] dx = new int[]{0,0,1,-1}; 
        int[] dy = new int[]{1,-1,0,0}; 
        for(int k = 0; k < 4; k++) { 
            helper(image, left, right, up, down, x + dx[k], y + dy[k]); 
        } 
    } 
}
```

Wrong DFS Solution debugging detail: 
```
Input:
x=0,y=2 
0,0,1,0 
0,1,1,0 
0,1,0,0
----------------------------------------------------
Initial: 
left=y=2 
right=y=2 
up=x=0 
down=x=0 
dx={0,0,1,-1} 
dy={1,-1,0,0} 
---------------------------------------------------- 
Round 1: 
helper(image,left,right,up,down,x,y) 
=helper(image,2,2,0,0,0,2) 
left=Math.min(left,y)=2 
right=Math.max(right,y)=2 
up=Math.min(up,x)=0 
down=Math.max(down,x)=0 
image[0][2]='2' 
0,0,2,0 
0,1,1,0 
0,1,0,0 
stack 
======== 
 round1 -> store Round 1 primitive variable 
======== 
---------------------------------------------------- 
Round 2: 
k=0,dx=0,dy=1 
helper(image,left,right,up,down,x + dx[k],y + dy[k]) 
=helper(image,2,2,0,0,0+0,2+1) 
image[0][3]='0' != '1' -> return 
stack 
======== 
 round2 -> create once but pop out from stack immediately since image[0][3]='0' != '1' -> Later Round 3 will continue based on Round 1 
======== 
 round1 -> store Round 1 primitive variable 
======== 
---------------------------------------------------- 
Round 3: 
k=1,dx=0,dy=-1 
helper(image,left,right,up,down,x + dx[k],y + dy[k]) 
=helper(image,2,2,0,0,0+0,2-1) 
image[0][1]='0' != '1' -> return 
stack 
======== 
 round3 -> create once but pop out from stack immediately since image[0][1]='0' != '1' -> Later Round 4 will continue based on Round 1 
======== 
 round1 -> store Round 1 primitive variable 
======== 
---------------------------------------------------- 
Round 4: 
k=2,dx=1,dy=0 
helper(image,left,right,up,down,x + dx[k],y + dy[k]) 
=helper(image,2,2,0,0,0+1,2+0) 
image[1][2]='1' == '1' -> continue 
left=Math.min(left,y)=2 
right=Math.max(right,y)=2 
up=Math.min(up,x)=0 
down=Math.max(down,x)=1 
image[1][2]='2' 
0,0,2,0 
0,1,2,0 
0,1,0,0 
stack 
======== 
 round4 -> store Round 4 primitive variable 
======== 
 round1 -> store Round 1 primitive variable 
======== 
---------------------------------------------------- 
Round 5: 
k=0,dx=0,dy=1 
helper(image,left,right,up,down,x + dx[k],y + dy[k]) 
=helper(image,2,2,0,1,1+0,2+1) 
image[1][3]='0' != '1' -> return 
stack 
======== 
 round5 -> create once but pop out from stack immediately since image[1][3]='0' != '1' -> Later Round 6 will continue based on Round 4 
======== 
 round4 -> store Round 4 primitive variable 
======== 
 round1 -> store Round 1 primitive variable 
======== 
---------------------------------------------------- 
Round 6: 
k=1,dx=0,dy=-1 
helper(image,left,right,up,down,x + dx[k],y + dy[k]) 
=helper(image,2,2,0,1,1+0,2-1) 
image[1][1]='1' == '1' -> continue 
left=Math.min(left,y)=1 
right=Math.max(right,y)=2 
up=Math.min(up,x)=0 
down=Math.max(down,x)=1 
image[1][1]='2' 
0,0,2,0 
0,2,2,0 
0,1,0,0 
stack 
======== 
 round6 -> store Round 6 primitive variable 
======== 
 round4 -> store Round 4 primitive variable 
======== 
 round1 -> store Round 1 primitive variable 
======== 
---------------------------------------------------- 
Round 7: 
k=0,dx=0,dy=1 
helper(image,left,right,up,down,x + dx[k],y + dy[k]) 
=helper(image,1,2,0,1,1+0,2+1) 
image[1][3]='0' != '1' -> return 
stack 
======== 
 round7 -> create once but pop out from stack immediately since image[1][3]='0' != '1' -> Later Round 8 will continue based on Round 6 
======== 
 round6 -> store Round 6 primitive variable 
======== 
 round4 -> store Round 4 primitive variable 
======== 
 round1 -> store Round 1 primitive variable 
======== 
---------------------------------------------------- 
Round 8: 
k=1,dx=0,dy=-1 
helper(image,left,right,up,down,x + dx[k],y + dy[k]) 
=helper(image,1,2,0,1,1+0,1-1) 
image[1][0]='0' != '1' -> return 
stack 
======== 
 round8 -> create once but pop out from stack immediately since image[1][0]='0' != '1' -> Later Round 9 will continue based on Round 6 
======== 
 round6 -> store Round 6 primitive variable 
======== 
 round4 -> store Round 4 primitive variable 
======== 
 round1 -> store Round 1 primitive variable 
======== 
---------------------------------------------------- 
Round 9: 
k=2,dx=0,dy=-1 
helper(image,left,right,up,down,x + dx[k],y + dy[k]) 
=helper(image,1,2,0,1,1+1,1+0) 
image[2][1]='1' == '1' -> continue 
left=Math.min(left,y)=1 
right=Math.max(right,y)=2 
up=Math.min(up,x)=0 
down=Math.max(down,x)=2 
image[2][1]='2' 
0,0,2,0 
0,2,2,0 
0,2,0,0 
stack 
======== 
 round8 -> store Round 8 primitive variable 
======== 
 round6 -> store Round 6 primitive variable 
======== 
 round4 -> store Round 4 primitive variable 
======== 
 round1 -> store Round 1 primitive variable 
======== 
---------------------------------------------------- 
Round 10: 
k=0,dx=0,dy=1 
helper(image,left,right,up,down,x + dx[k],y + dy[k]) 
=helper(image,1,2,0,2,2+0,1+1) 
image[2][2]='0' != '1' -> return 
stack 
======== 
 round10 -> create once but pop out from stack immediately since image[2][2]='0' != '1' -> Later Round 11 will continue based on Round 9 
======== 
======== 
 round8 -> store Round 8 primitive variable 
======== 
 round6 -> store Round 6 primitive variable 
======== 
 round4 -> store Round 4 primitive variable 
======== 
 round1 -> store Round 1 primitive variable 
======== 
---------------------------------------------------- 
Round 11: 
k=1,dx=0,dy=-1 
helper(image,left,right,up,down,x + dx[k],y + dy[k]) 
=helper(image,1,2,0,2,2+0,1-1) 
image[2][0]='0' != '1' -> return 
stack 
======== 
 round11 -> create once but pop out from stack immediately since image[2][0]='0' != '1' -> Later Round 12 will continue based on Round 9 
======== 
======== 
 round8 -> store Round 8 primitive variable 
======== 
 round6 -> store Round 6 primitive variable 
======== 
 round4 -> store Round 4 primitive variable 
======== 
 round1 -> store Round 1 primitive variable 
======== 
---------------------------------------------------- 
Round 12: 
k=2,dx=1,dy=0 
helper(image,left,right,up,down,x + dx[k],y + dy[k]) 
=helper(image,1,2,0,2,2+1,1+0) 
image[3][1] not exist -> return 
stack 
======== 
 round12 -> create once but pop out from stack immediately since image[3][1] not exist -> Later Round 13 will continue based on Round 9 
======== 
======== 
 round8 -> store Round 8 primitive variable 
======== 
 round6 -> store Round 6 primitive variable 
======== 
 round4 -> store Round 4 primitive variable 
======== 
 round1 -> store Round 1 primitive variable 
======== 
---------------------------------------------------- 
Round 13: 
k=3,dx=-1,dy=0 
helper(image,left,right,up,down,x + dx[k],y + dy[k]) 
=helper(image,1,2,0,2,2-1,1+0) 
image[1][1]='2' != '1' -> return 
stack 
======== 
 round13 -> create once but pop out from stack immediately since image[1][1]='2' != '1' -> Later Round 14 will continue based on Round 9 
======== 
======== 
 round8 -> store Round 8 primitive variable 
======== 
 round6 -> store Round 6 primitive variable 
======== 
 round4 -> store Round 4 primitive variable 
======== 
 round1 -> store Round 1 primitive variable 
======== 
---------------------------------------------------- 
Round 14: 
Reach last statement in helper method, recursion on current level end, stack pop out the statistics at peek position (pop out round8) 
stack 
======== 
 round8 -> store Round 8 primitive variable -> pop out statistics at stack peek 
======== 
 round6 -> store Round 6 primitive variable 
======== 
 round4 -> store Round 4 primitive variable 
======== 
 round1 -> store Round 1 primitive variable 
======== 
And accompanied with pop out round8 statistics which stored at stack peek, the values we care about (left=1,right=2,up=0,down=2) on round8 is gone, these 4 values not deliver to round6 statistics which stored at new stack peek position after pop out round8, but 'image' as array object which stored on heap and keep updating in all rounds will maintain the status, which means even primitive values (left=1,right=2,up=0,down=2) are gone when pop out from stack, the 'image' hold the status 
0,0,2,0 
0,2,2,0 
0,2,0,0 
Now round14 actually continue based on round6 statistics which stored at new stack peek position: 
stack 
======== 
 round6 -> store Round 6 primitive variable -> round14 continues at new peek 
======== 
 round4 -> store Round 4 primitive variable 
======== 
 round1 -> store Round 1 primitive variable 
======== 
k=3,dx=-1,dy=0 
helper(image,left,right,up,down,x + dx[k],y + dy[k]) 
=helper(image,1,2,0,1,1-1,1+0) 
image[0][1]='0' != '1' -> return 
---------------------------------------------------- 
Round 15: 
Reach last statement in helper method, recursion on current level end, stack pop out the statistics at peek position (pop out round6) 
stack 
======== 
 round6 -> store Round 6 primitive variable -> pop out statistics at stack peek 
======== 
 round4 -> store Round 4 primitive variable 
======== 
 round1 -> store Round 1 primitive variable 
======== 
And accompanied with pop out round6 statistics which stored at stack peek, the values we care about (left=1,right=2,up=0,down=1) on round6 is gone, these 4 values not deliver to round4 statistics which stored at new stack peek position after pop out round6, but 'image' as array object which stored on heap and keep updating in all rounds will maintain the status, which means even primitive values (left=1,right=2,up=0,down=1) are gone when pop out from stack, the 'image' hold the status 
0,0,2,0 
0,2,2,0 
0,2,0,0 
Now round15 actually continue based on round4 statistics which stored at new stack peek position: 
stack 
======== 
 round4 -> store Round 4 primitive variable -> round15 continues at new peek 
======== 
 round1 -> store Round 1 primitive variable 
======== 
k=2,dx=1,dy=0 
helper(image,left,right,up,down,x + dx[k],y + dy[k]) 
=helper(image,2,2,0,1,1+1,2+0) 
image[2][2]='0' != '1' -> return 
k=3,dx=-1,dy=0 
helper(image,left,right,up,down,x + dx[k],y + dy[k]) 
=helper(image,2,2,0,1,1-1,2+0) 
image[0][2]='2' != '1' -> return 
---------------------------------------------------- 
Round 16: 
Reach last statement in helper method, recursion on current level end, stack pop out the statistics at peek position (pop out round4) 
stack 
======== 
 round4 -> store Round 4 primitive variable -> pop out statistics at stack peek 
======== 
 round1 -> store Round 1 primitive variable 
======== 
And accompanied with pop out round4 statistics which stored at stack peek, the values we care about (left=2,right=2,up=0,down=1) on round4 is gone, these 4 values not deliver to round1 statistics which stored at new stack peek position after pop out round4, but 'image' as array object which stored on heap and keep updating in all rounds will maintain the status, which means even primitive values (left=2,right=2,up=0,down=1) are gone when pop out from stack, the 'image' hold the status 
0,0,2,0 
0,2,2,0 
0,2,0,0 
Now round16 actually continue based on round1 statistics which stored at new stack peek position: 
stack 
======== 
 round1 -> store Round 1 primitive variable -> round15 continues at new peek 
======== 
k=3,dx=-1,dy=0 
helper(image,left,right,up,down,x + dx[k],y + dy[k]) 
=helper(image,2,2,0,1,0-1,2+0) 
image[-1][2] not exist -> return 
---------------------------------------------------- 
Round 17: 
Reach last statement in helper method, recursion on current level end, stack pop out the statistics at peek position (pop out round4) 
stack 
======== 
 round1 -> store Round 1 primitive variable -> pop out statistics at stack peek 
======== 
And accompanied with pop out round1 statistics which stored at stack peek, the values we care about (left=2,right=2,up=0,down=0) on round1 is gone, these 4 values not deliver to final statistics which stored at new stack peek position after pop out round1, but 'image' as array object which stored on heap and keep updating in all rounds will maintain the status, which means even primitive values (left=2,right=2,up=0,down=0) are gone when pop out from stack, the 'image' hold the status 
0,0,2,0 
0,2,2,0 
0,2,0,0 
stack 
======== 
 empty 
======== 
Finally we have (left=2,right=2,up=0,down=0) -> (down - up + 1) * (right - left + 1) = 1, and this is the wrong answer, since we didn't reserve correct min left, max right, min up, max down values for getting the maximum area, each time when pop up rounds statistics from peek position, all halfway left, right, up, down as primitive values are gone with stack pop up, and our function return as void, not deliver any value to parent recursion level
```

Correct DSF Solution 1 (10min,  objectify primitive variable left,  right,  up,  down, store and update as array, but tentatively will throw stack out of memory error because array 'status' is an extra storage required for each recursion level)
```
public class Solution { 
    /** 
     * @param image: a binary matrix with '0' and '1' 
     * @param x: the location of one of the black pixels 
     * @param y: the location of one of the black pixels 
     * @return: an integer 
     */ 
     
    public int minArea(char[][] image, int x, int y) { 
        //int left = y; 
        //int right = y; 
        //int up = x; 
        //int down = x; 
        // To make it trackable need to objectify primitive variable (left, right, up, down), otherwise 
        // in void return helper method, no statistics of primitive variable will reserve when pop out 
        // current recursion level from stack peek, the easy way is create an array to store and update 
        // (left, right, up, down) accordingly => status = {left, right, up, down}, then even recursion 
        // end on any level and pop out, the object 'status' only store a reference on stack, 'status' 
        // object content stored on heap, all changes from different references (when recursive call happen 
        // the reference will auto get its copies) on different recusion level will keep updating 'status' 
        // object content stored on heap, eventually this attribute make (left, right, up, down) trackable 
        // recursion level 
        int[] status = new int[] {y, y, x, x}; 
        helper(image, status, x, y); 
        //return (down - up + 1) * (right - left + 1); 
        return (status[3] - status[2] + 1) * (status[1] - status[0] + 1); 
    } 
    private void helper(char[][] image, int[] status, int x, int y) { 
        // Base case 
        if(x < 0 || x >= image.length || y < 0 || y >= image[0].length || image[x][y] != '1') { 
            return; 
        } 
        // Update left, right, up, down, since our purpose is update on original array object to implememt 
        // primitive variable trackable, hence no need to deep copy 'status' array 
        //left = Math.min(left, y); 
        //right = Math.max(right, y); 
        //up = Math.min(up, x); 
        //down = Math.max(down, x); 
        status[0] = Math.min(status[0], y); 
        status[1] = Math.max(status[1], y); 
        status[2] = Math.min(status[2], x); 
        status[3] = Math.max(status[3], x); 
        // Mark as visited 
        image[x][y] = '2'; 
        // Traversal on four directions 
        // Remove dx, dy array because of stack out of memory since each level we have to store  
        // another two arrays, which is not necessary and block by long input 
        //int[] dx = new int[]{0,0,1,-1}; 
        //int[] dy = new int[]{1,-1,0,0}; 
        //for(int k = 0; k < 4; k++) { 
        //    helper(image, status, x + dx[k], y + dy[k]); 
        //} 
        helper(image, status, x + 1, y); 
        helper(image, status, x - 1, y); 
        helper(image, status, x, y + 1); 
        helper(image, status, x, y - 1); 
    } 
}

Space Complexity: O(1)    
Time Complexity: O(mn) -> image has m rows and n columns
```

Ideally above code should work each time and pass once, but tentatively failed on large scale testing input because  array 'status' is an extra storage required for each recursion level:
```
Exception in thread "main" java.lang.StackOverflowError 
    at Solution.helper(Solution.java:42) 
    at Solution.helper(Solution.java:52) 
    at Solution.helper(Solution.java:52)
......
Expected result:
8928

Input:
size: 9214 
md5: 125b85b58786c7fcaf6749472c453d76 
["111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111","111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111","111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111","111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111","111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111","111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111","111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111","111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111","111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111","111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111","111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111","111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111","111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111","111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111","111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111","111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111","111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111","111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111","111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111","111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111","111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111","111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111","111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111","111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111","111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111","111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111","111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111011111","111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111","111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111","111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111","111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111","111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111","111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111","111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111","111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111","111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111","111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111","111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111","111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111","111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111","111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111","111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111","111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111","111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111","111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111","111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111","111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111","111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111","111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111","111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111","111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111","111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111","111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111","111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111","111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111","111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111","111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111","111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111","111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111","111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111","111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111","111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111","111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111","111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111","111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111","111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111","111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111","111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111","111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111","111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111","111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111","111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111","111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111","111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111","111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111","111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111","111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111","111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111","111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111","111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111","111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111","111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111","111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111","111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111","111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111","111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111","011111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111","111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111","111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111","111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111","111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111","111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111","111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111"]
18
20
```

Correct DSF Solution 2 (10min,  global variable to track and update left,  right,  up,  down, and no extra dx, dy array required to avoid stack over flow error)
```
public class Solution { 
    /** 
     * @param image: a binary matrix with '0' and '1' 
     * @param x: the location of one of the black pixels 
     * @param y: the location of one of the black pixels 
     * @return: an integer 
     */ 
     
    // Initial global varibale to track and update the primitive variables
    // No need to initiate and assign value for left, right, up, down at 
    // first place, we can initiate first and assign later 
    //int left = Integer.MAX_VALUE; 
    //int right = 0; 
    //int up = Integer.MAX_VALUE; 
    //int down = 0;
    int left, right, up, down; 
    public int minArea(char[][] image, int x, int y) { 
        //int left = y; 
        //int right = y; 
        //int up = x; 
        //int down = x; 
        left = y; 
        right = y; 
        up = x; 
        down = x; 
        helper(image, x, y); 
        return (down - up + 1) * (right - left + 1); 
    } 
    private void helper(char[][] image, int x, int y) { 
        // Base case 
        if(x < 0 || x >= image.length || y < 0 || y >= image[0].length || image[x][y] != '1') { 
            return; 
        } 
        // Update left, right, up, down 
        left = Math.min(left, y); 
        right = Math.max(right, y); 
        up = Math.min(up, x); 
        down = Math.max(down, x); 
        // Mark as visited 
        image[x][y] = '2'; 
        // Traversal on four directions 
        // Remove dx, dy array because of stack out of memory since each level we have to store  
        // another two arrays, which is not necessary and block by long input 
        //int[] dx = new int[]{0,0,1,-1}; 
        //int[] dy = new int[]{1,-1,0,0}; 
        //for(int k = 0; k < 4; k++) { 
        //    helper(image, x + dx[k], y + dy[k]); 
        //} 
        helper(image, x + 1, y); 
        helper(image, x - 1, y); 
        helper(image, x, y + 1); 
        helper(image, x, y - 1); 
    } 
}

Space Complexity: O(1)    
Time Complexity: O(mn) -> image has m rows and n columns
```

Solution 3: Binary Search solution (360 min, too long because of few tips:
1. To match Find Target First Occurrence template has to create two helper functions as isEmptyRow and isEmptyColumn to integrate Boolean condition check into a single brace, similar like the handling way in L875.Koko Eating Bananas, L1283.Find the Smallest Divisor Given a Threshold
2. Separately category into two phases, 1st phase find left and right most column contains '1', 2nd phase find top and bottom most row contains '1')
```
public class Solution { 
    /** 
     * @param image: a binary matrix with '0' and '1' 
     * @param x: the location of one of the black pixels 
     * @param y: the location of one of the black pixels 
     * @return: an integer 
     */ 
    public int minArea(char[][] image, int x, int y) { 
        int rows = image.length; 
        int cols = image[0].length; 
        int left = searchLeftAndRight(image, 0, y, true); 
        int right = searchLeftAndRight(image, y + 1, cols - 1, false); 
        int top = searchTopAndBottom(image, 0, x, true); 
        int bottom = searchTopAndBottom(image, x + 1, rows - 1, false); 
        return (right - left + 1) * (bottom - top + 1); 
    }

    // Find Target First Occurrence template 
    // boolean lo_to_hi means the search direction is moving forward from smallest 
    // row index to largest row index or vice verse 
    // e.g 
    // --------- lo (smallest row index, first row) 
    //    ... 
    // --------- mid 
    //    ... 
    // --------- hi (largest row index, last row) 
    // When search from lo to hi means try to find first '1' occurrence row index closest to first row 
    // When search from hi to lo means try to find first '1' occurrence row index closest to last row 
    private int searchTopAndBottom(char[][] image, int lo, int hi, boolean lo_to_hi) { 
        while(lo <= hi) { 
            int mid = lo + (hi - lo) / 2; 
            // Find top first target occurrence from lo to hi 
            if(lo_to_hi) { 
                if(!isEmptyRow(image, mid)) { 
                    hi = mid - 1; 
                } else { 
                    lo = mid + 1; 
                } 
            // Find bottom first target occurrence from hi to lo 
            } else { 
                if(!isEmptyRow(image, mid)) { 
                    lo = mid + 1; 
                } else { 
                    hi = mid - 1; 
                } 
            } 
        } 
        // No need to check '-1' case because eventually it must has a top / bottom most result even  
        // row [0, mid) has no '1' at all, because given location (x, y) means at least  
        // one element in image can be treated as top / bottom most result 
        //if(isEmptyRow(image, lo)) { 
        //    return -1; 
        //} 
        return lo_to_hi == true ? lo : hi; 
    }

    private boolean isEmptyRow(char[][] image, int row) { 
        int i = 0; 
        while(i < image[0].length) { 
            if(image[row][i] == '1') { 
                return false; 
            } 
            i++; 
        } 
        return true; 
    }

    // Find Target First Occurrence template 
    // boolean lo_to_hi means the search direction is moving forward from smallest 
    // column index to largest column index or vice verse 
    // e.g 
    // lo ...mid... hi 
    // | ...  | ... | 
    // | ...  | ... | 
    // | ...  | ... | 
    // lo = (smallest column index, first column) 
    // hi = (largest column index, last column) 
    // When search from lo to hi means try to find first '1' occurrence column index closest to first column 
    // When search from hi to lo means try to find first '1' occurrence column index closest to last column 
    private int searchLeftAndRight(char[][] image, int lo, int hi, boolean lo_to_hi) { 
        while(lo <= hi) { 
            int mid = lo + (hi - lo) / 2; 
            // Find left first target occurrence from lo to hi 
            if(lo_to_hi) { 
                if(!isEmptyColumn(image, mid)) { 
                    hi = mid - 1; 
                } else { 
                    lo = mid + 1; 
                } 
            // Find right first target occurrence from hi to lo 
            } else { 
                if(!isEmptyColumn(image, mid)) { 
                    lo = mid + 1; 
                } else { 
                    hi = mid - 1; 
                } 
            } 
        } 
        // No need to check '-1' case because eventually it must has a left / right most result even  
        // columns [0, mid) has no '1' at all, because given location (x, y) means at least  
        // one element in image can be treated as left / right most result 
        //if(isEmptyColumn(image, lo)) { 
        //    return -1; 
        //} 
        return lo_to_hi == true ? lo : hi; 
    }

    private boolean isEmptyColumn(char[][] image, int col) { 
        int i = 0; 
        while(i < image.length) { 
            if(image[i][col] == '1') { 
                return false; 
            } 
            i++; 
        } 
        return true; 
    } 
}

Space Complexity: O(1)    
Time Complexity: O(mlogn + nlogm) -> image has m rows and n columns
```

Refer to
https://www.lintcode.com/problem/600/solution/57205

方法：二分查找

想法

把二维图形投影到一维数组，并使用二分查找的方法来找边界。

算法

假设我们有一个图 1 所示的 10×11的图，我们按如下规则把每一列投影到一个向量 v里：

- v[i] = 1如果存在 x使得 image[x][i] = 1
- v[i] = 0其他情况

也就是说

如果某一列有黑色像素，那么这一列的投影对应的格子是黑色的，否则是白色的。

类似的，我们对行做同样的操作，将二维的图片投影到一维的列向量里。两个投影的向量如图 1 所示。

现在我们来说明下面的引理：

引理

如果只有一个黑色像素区域，那么一维投影数组里所有黑色的像素是连在一块的。

反证法证明

假设结论不成立，有不连接的黑色像素块 i和 j，在一维数组中 i < j。此时，存在一列 k在 (i, j)中且列 k在二维数组中没有黑色像素。此时，二维数组中至少有两个黑色区域被列 k分隔开，这与“只有一个黑色区域”矛盾。

因此，我们得出结论所有一维数组中的黑色像素是连在一起的。

有了这个引理，我们有如下算法：

- 将二维数组投影到一个列数组和一个行数组中
- 在行数组 [0, y)中二分查找 left
- 在行数组 [y + 1, n)中二分查找 right
- 在列数组 [0, x)中二分查找 top
- 在列数组 [x + 1, m)中二分查找 bottom
- 返回 (right - left) * (bottom - top)

然而，投影这一步的时间代价就需要 O(mn)，这占据了整个算法的主要时间。如果仅仅这样，我们没法比之前的方法取得更好的效果。

这里的技巧是，我们不需要把整个投影过程当做预处理过程，我们可以只在必要的时候做这个步骤。

回想一下一维数组里的二分查找，每一次我们只检查当前区间中心的元素，来决定下一次查找是在左区间还是右区间。

在二维数组中，我们可以做类似的事情。唯一的不同是这里的元素不是一个数字而是一个向量。比方说，一个 m * n的矩阵可以被看成 n个列向量。

在这个 n个元素（向量）里，我们进行二分查找来找到 left或者 right。每一次我们只检查当前区间最中心的一个列向量，来决定下一次我们查找哪一边。

最后总共会查找 O(logn)个向量，每一次检查需要 O(m)的时间（我们简单地遍历向量中所有的 m个元素）

所以总共需要O(mlogn)来找到 left和 right。
类似地，我们需要 O(nlogm)的时间去找到 top和 bottom。整个算法的时间复杂度为 O(mlogn+nlogm)。

```
public class Solution {
    public int minArea(char[][] image, int x, int y) {
        int m = image.length, n = image[0].length;
        int left = searchColumns(image, 0, y, 0, m, true);
        int right = searchColumns(image, y + 1, n, 0, m, false);
        int top = searchRows(image, 0, x, left, right, true);
        int bottom = searchRows(image, x + 1, m, left, right, false);
        return (right - left) * (bottom - top);
    }
    private int searchColumns(char[][] image, int i, int j, int top, int bottom, boolean whiteToBlack) {
        while (i != j) {
            int k = top, mid = (i + j) / 2;
            while (k < bottom && image[k][mid] == '0') ++k;
            if (k < bottom == whiteToBlack) // k < bottom means the column mid has black pixel
                j = mid; //search the boundary in the smaller half
            else
                i = mid + 1; //search the boundary in the greater half
        }
        return i;
    }
    private int searchRows(char[][] image, int i, int j, int left, int right, boolean whiteToBlack) {
        while (i != j) {
            int k = left, mid = (i + j) / 2;
            while (k < right && image[mid][k] == '0') ++k;
            if (k < right == whiteToBlack) // k < right means the row mid has black pixel
                j = mid;
            else
                i = mid + 1;
        }
        return i;
    }
}
```

复杂度分析
- 时间复杂度： O(mlogn+nlogm)。
这里，mm和 nn是图像的宽和高。我们在二分查找的过程中每次都要执行一个线性查找。前一部分介绍了这个算法是如何运作的。
- 空间复杂度： O(1)。
宽搜和线性查找都只需要线性大小的额外空间。
