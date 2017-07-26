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
