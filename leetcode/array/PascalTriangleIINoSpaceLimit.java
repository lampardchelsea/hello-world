/**
 * Given an index k, return the kth row of the Pascal's triangle.
 * For example, given k = 3,
 * Return [1,3,3,1].
 * Note:
 * Could you optimize your algorithm to use only O(k) extra space?
 * 
 * If not limit the space, very similar to PascalTriangle, first
 * fully create the PascalTriangle, then get its corresponding row.
 * Note: Need to care about relation between rowIndex and numRows.
 * 
 * Below code has big issue, if rowIndex != 0, it will never create
 * the first row (only one 1), but PascalTriangle as an integrate
 * structure, the first row is necessary and should create in any
 * case.
 * 
 * public class Solution {
    public List<Integer> getRow(int rowIndex) {
        List<List<Integer>> pascalTriangle = new ArrayList<List<Integer>>();
        List<Integer> result = new ArrayList<Integer>();
        
        if(rowIndex < 0) {
            return result;
        }

        // This case will mistakenly skip if rowIndex != 0
        if(rowIndex == 0) {
            List<Integer> firstRow = new ArrayList<Integer>();
            firstRow.add(1);
            pascalTriangle.add(firstRow);
        }
        
        for(int i = 1; i <= rowIndex; i++) {
            List<Integer> currentRow = new ArrayList<Integer>();
            for(int j = 0; j <= i; j++) {
                if(j == 0 || j == i) {
                    currentRow.add(1);
                } else {
                    int a = pascalTriangle.get(i - 1).get(j - 1);
                    int b = pascalTriangle.get(i - 1).get(j);
                    currentRow.add(a + b);
                }
            }
            pascalTriangle.add(currentRow);
        }
        
        result = pascalTriangle.get(rowIndex);
        
        return result;
    }
}
 * 
*/
public class Solution {
    public List<Integer> getRow(int rowIndex) {
        List<List<Integer>> pascalTriangle = new ArrayList<List<Integer>>();
        List<Integer> result = new ArrayList<Integer>();
        
        // Number of rows equals rowIndex plus one
        // e.g If given rowIndex as 3, means return the 4th row, need to create
        // 4 rows initally to get the 4th one.
        int numRows = rowIndex + 1;
        
        if(numRows <= 0) {
            return result;
        }
        
        for(int i = 1; i <= numRows; i++) {
            if(i == 1) {
                List<Integer> firstRow = new ArrayList<Integer>();
                firstRow.add(1);
                pascalTriangle.add(firstRow);
            } else {
                List<Integer> currentRow = new ArrayList<Integer>();
                for(int j = 0; j < i; j++) {
                    if(j == 0 || j == i - 1) {
                        currentRow.add(1);
                    } else {
                        int a = pascalTriangle.get(i - 2).get(j - 1);
                        int b = pascalTriangle.get(i - 2).get(j);
                        currentRow.add(a + b);
                    }
                }
                pascalTriangle.add(currentRow);
            }
        }
        
        result = pascalTriangle.get(rowIndex);
        
        return result;
    }
}
