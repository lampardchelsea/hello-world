/**
 * Design and implement a TwoSum class. It should support the following operations: add and find.
 * add - Add the number to an internal data structure.
 * find - Find if there exists any pair of numbers which sum is equal to the value.
 * For example,
    add(1); 
    add(3); 
    add(5);
    find(4) -> true
    find(7) -> false
    
 * Java Solution
 * Since the desired class need add and get operations, HashMap is a good option for this purpose.
*/
// Refer to
// http://www.jianshu.com/p/c287d26579c7
// http://ryanleetcode.blogspot.com/2015/10/two-sum-iii-data-structure-design.html
// http://www.cnblogs.com/grandyang/p/5184143.html
// 我们先来看用哈希表的解法，我们把每个数字和其出现的次数建立映射，然后我们遍历哈希表，对于每个值，
// 我们先求出此值和目标值之间的差值t，然后我们需要分两种情况来看，如果当前值不等于差值t，那么只要
// 哈希表中有差值t就返回True，或者是当差值t等于当前值时，如果此时哈希表的映射次数大于1，则表示哈
// 希表中还有另一个和当前值相等的数字，二者相加就是目标值
import java.util.HashMap;
import java.util.Map;

public class TwoSumIII {
	 private Map<Integer, Integer> table = new HashMap<>();
	    // add - O(1) runtime, find- O(n) runtime, O(n) space- store in hash table

	    // Add the number to an internal data structure.
       public void add(int number) {
        int count = table.containsKey(number) ? table.get(number) : 0;
        table.put(number, count + 1);
       }

       // Find if there exists any pair of numbers which sum is equal to the value.
       public boolean find(int value) {
        for (Map.Entry<Integer, Integer> entry : table.entrySet()) {
            int num = entry.getKey();
            int y = value - num;
            if ( y == num) {
                // For duplicates, ensure there are at least two individual numbers.
                if (entry.getValue() >= 2) return true;
            } else if (table.containsKey(y)) {
                return true;
            }
        }
        return false;
       }

	    public static void main(String[] args) {
	        // Your TwoSum object will be instantiated and called as such:
	        // TwoSum twoSum = new TwoSum();
	        // twoSum.add(number);
	        // twoSum.find(value);
	        TwoSumIII t = new TwoSumIII();
	        t.add(1); 
	        t.add(3); 
	        t.add(5);
	        System.out.println(t.find(4)); // -> true
	        System.out.println(t.find(7)); // -> false

	    }
}
