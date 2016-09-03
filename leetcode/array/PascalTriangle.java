/**
* Given numRows, generate the first numRows of Pascal's triangle.
* For example, given numRows = 5,
* Return
* [
*      [1],
*     [1,1],
*    [1,2,1],
*   [1,3,3,1],
*  [1,4,6,4,1]
* ]
*/
public class Solution {
    public List<List<Integer>> generate(int numRows) {
        // Note 1: If initial as ArrayList<ArrayList<Integer>>(), will encouter
        // can not convert to List<List<Integer>> type issue.
        // The right way is first initial outside List as ArrayList, keep inside
        // List as List<Integer>, then intial the inside List as ArrayList when
        // hit the logic.
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        
        // If input 0, return null
        if(numRows <= 0) {
            return result;
        }
        
        // Use i to present how many rows
        for(int i = 1; i <= numRows; i++) {
            // Check if the first row as it only contain single value 1
            if(i == 1) {
                List<Integer> firstRow = new ArrayList<Integer>();
                firstRow.add(1);
                result.add(firstRow);
            } else {
                List<Integer> currentRow = new ArrayList<Integer>();
                // Use j to present item index on current row
                for(int j = 0; j < i; j++) {
                    if(j == 0 || j == i - 1) {
                        // Note 2: The law is first and last item of each line is 1
                        currentRow.add(1);
                    } else {
                        // Note 3: Current row item[j] = previous row
                        // item[j - 1] + item[j], as i present how many
                        // rows, the previous row index is (i - 2). 
                        Integer a = result.get(i - 2).get(j - 1);
                        Integer b = result.get(i - 2).get(j);
                        currentRow.add(a + b);
                    }
                }
                result.add(currentRow);
            }
        }
        
        return result;
    }
}
