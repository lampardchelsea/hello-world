/**
 * Refer to
 * https://leetcode.com/problems/flatten-nested-list-iterator/description/
 * Given a nested list of integers, implement an iterator to flatten it.

    Each element is either an integer, or a list -- whose elements may also be integers or other lists.

    Example 1:
    Given the list [[1,1],2,[1,1]],

    By calling next repeatedly until hasNext returns false, the order of elements returned by next should be: [1,1,2,1,1].

    Example 2:
    Given the list [1,[4,[6]]],

    By calling next repeatedly until hasNext returns false, the order of elements returned by next should be: [1,4,6].
 *
 *
 * Solution
 * https://discuss.leetcode.com/topic/42042/simple-java-solution-using-a-stack-with-explanation
 * https://www.youtube.com/watch?v=LkyK5mwT3KQ
*/
/**
 * // This is the interface that allows for creating nested lists.
 * // You should not implement it, or speculate about its implementation
 * public interface NestedInteger {
 *
 *     // @return true if this NestedInteger holds a single integer, rather than a nested list.
 *     public boolean isInteger();
 *
 *     // @return the single integer that this NestedInteger holds, if it holds a single integer
 *     // Return null if this NestedInteger holds a nested list
 *     public Integer getInteger();
 *
 *     // @return the nested list that this NestedInteger holds, if it holds a nested list
 *     // Return null if this NestedInteger holds a single integer
 *     public List<NestedInteger> getList();
 * }
 */

public class NestedIterator implements Iterator<Integer> {
    Stack<NestedInteger> stack;
    public NestedIterator(List<NestedInteger> nestedList) {
        stack = new Stack<NestedInteger>();
        /**
         E.g Given nestedList = [[1,2],2,[1,1]]
             stack -> bottom [[1,1],2,[1,2]] top
             hasNext(): stack -> [[1,1],2] -> [[1,1],2,2,1] -> peek() = 1 -> true
             next(): stack -> [[1,1],2,2]
              ....
             result = 1,2,2,1,1
        */
        for(int i = nestedList.size() - 1; i >= 0; i--) {
            stack.push(nestedList.get(i));
        }
    }

    @Override
    public Integer next() {
        return stack.pop().getInteger();
    }

    @Override
    public boolean hasNext() {
        while(!stack.isEmpty()) {
            // Caution: just peek() here, pop() is in next() method
            NestedInteger e = stack.peek();
            if(e.isInteger()) {
                return true;
            }
            // If still a list, just pop out from stack
            stack.pop();
            for(int i = e.getList().size() - 1; i >= 0; i--) {
                stack.push(e.getList().get(i));
            }
        }
        return false;
    }
}

/**
 * Your NestedIterator object will be instantiated and called as such:
 * NestedIterator i = new NestedIterator(nestedList);
 * while (i.hasNext()) v[f()] = i.next();
 */





