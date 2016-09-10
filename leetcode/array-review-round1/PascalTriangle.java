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
* 
* Below code has three major problems:
* 1. The outside for loop should start with 1, which means numRows at least is 1,
*    otherwise output empty list.
* 2. The first row should treat totally seperate, which means currentRow only 
*    represent start from second row if numRows given larger than 1.
* 3. currentRow for loop control should use j < i, not j < length, as current
*    row element numbers is i, not numRows.
* 4. No handle for numRows is 0.
* 
* public class Solution {
    public List<List<Integer>> generate(int numRows) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        --> add numRows = 0 handle
        for(int i = 0; i < numRows; i++) { --> start with 1
            List<Integer> currentRow = new ArrayList<Integer>();
            if(i == 0) {
                currentRow.add(1); --> Replace with individual presentation firstRow
            } else {
                for(int j = 0; j < numRows; j++) {
                    if(j == 0 || j == numRows - 1) {
                        currentRow.add(1);
                    } else {
                        int a = result.get(i - 2).get(j - 1);
                        int b = result.get(i - 2).get(j);
                        currentRow.add(a + b);
                    }
                }
            }
            
            result.add(currentRow);
        }
        
        return result;
    }
}
*/
public class Solution {
    public List<List<Integer>> generate(int numRows) {
      List<List<Integer>> result = new ArrayList<List<Integer>>();
      if(numRows == 0) {
        return result;
      }
      
      for(int i = 1; i <= numRows; i++) {
        if(i == 1) {
          List<Integer> firstRow = new ArrayList<Integer>();
          firstRow.add(1);
          result.add(firstRow);
        } else {
          List<Integer> currentRow = new ArrayList<Integer>();
          for(int j = 0; j < i; j++) {
            if(j == 0 || j == i - 1) {
              currentRow.add(1);
            } else {
              int a = result.get(i - 2).get(j - 1);
              int b = result.get(i - 2).get(j);
              currentRow.add(a + b);
            }
          }
          result.add(currentRow);
        }
      }
      
      return result;
    }
}




