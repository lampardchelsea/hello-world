/**
Refer to
https://leetcode.com/problems/product-of-the-last-k-numbers/
Implement the class ProductOfNumbers that supports two methods:

1. add(int num)

Adds the number num to the back of the current list of numbers.
2. getProduct(int k)

Returns the product of the last k numbers in the current list.
You can assume that always the current list has at least k numbers.
At any time, the product of any contiguous sequence of numbers will fit into a single 32-bit integer without overflowing.

Example:
Input
["ProductOfNumbers","add","add","add","add","add","getProduct","getProduct","getProduct","add","getProduct"]
[[],[3],[0],[2],[5],[4],[2],[3],[4],[8],[2]]

Output
[null,null,null,null,null,null,20,40,0,null,32]

Explanation
ProductOfNumbers productOfNumbers = new ProductOfNumbers();
productOfNumbers.add(3);        // [3]
productOfNumbers.add(0);        // [3,0]
productOfNumbers.add(2);        // [3,0,2]
productOfNumbers.add(5);        // [3,0,2,5]
productOfNumbers.add(4);        // [3,0,2,5,4]
productOfNumbers.getProduct(2); // return 20. The product of the last 2 numbers is 5 * 4 = 20
productOfNumbers.getProduct(3); // return 40. The product of the last 3 numbers is 2 * 5 * 4 = 40
productOfNumbers.getProduct(4); // return 0. The product of the last 4 numbers is 0 * 2 * 5 * 4 = 0
productOfNumbers.add(8);        // [3,0,2,5,4,8]
productOfNumbers.getProduct(2); // return 32. The product of the last 2 numbers is 4 * 8 = 32 

Constraints:
There will be at most 40000 operations considering both add and getProduct.
0 <= num <= 100
1 <= k <= 40000
*/

// Solution 1: Prefix production + Special handle when encounter add 0 as restore list
// Style 1
// Refer to
// https://leetcode.com/problems/product-of-the-last-k-numbers/discuss/510219/Java-Maintain-a-Prefix-Product-(Handle-the-case-when-Element-is-0)
/**
class ProductOfNumbers {
    static List<Integer> prod;
    static int p;
    public ProductOfNumbers() {
        prod = new ArrayList<>();
        p = 1;
    }
    public void add(int num) {
        if(num == 0) {
            prod = new ArrayList<>();
            p = 1;
            return;
        }
        p *= num;
        prod.add(p);
    }
    public int getProduct(int k) {
        if(prod.size() < k) return 0;
        int ans = prod.get(prod.size() - 1);
        if(prod.size() == k) return ans;
        return (ans / prod.get(prod.size() - 1 - k));
    }
}
*/

// Style 2:
// Refer to
// https://leetcode.com/problems/product-of-the-last-k-numbers/discuss/512354/Java-prefix-product-beats-100-explained
/**
We can easily count the product in O(1) time if we keep the product prefix array - every next element of such array 
is the product of the previous product and next element added to the data structure.

Only problem with this approach is 0 element - it ruins this logic because every next element after it will be 0 if 
we just keep multiplying naively.

However if you think about it - we don't care about element before 0 because all such queries will return 0. The only 
sequence that can return some value > 0 will be the after the 0. This means that we essentially reset our structure 
after 0 element and start over.

For getProduct method we need to check if we have enough elements in our data structure. If not (as per problem statement 
we should always have enough) it means we have met 0 before and erased the data. In this case we can just return 0. 
Otherwise return normally as per prefix product logic - arr[N - 1]/arr[N - 1 - k].

Catches: it's easier if we initialize our list with 1 - in this case calculation became easier for cases when we added 
exactly k elements, otherwise we would need extra logic to check the size().
another cath - we can keep previous element and calculate next element based on it's value directly instead of goinmg 
every time to the list - will save some time

O(1) time for add (on average, if we skip details of dynamic array in java), O(1) getProduct method - few comparisions 
and one division.
O(n) space - potentially we can have products of all elements in a list

public class ProductOfLastKNumbers {
    List<Integer> list;
    int prev;
    public ProductOfLastKNumbers() {
        list = new ArrayList();
        list.add(1);
        prev = 1;
    }

    public void add(int num) {
        //if element is > 0 create next element in a prefix product list. Update prev to be this
        //element
        if (num > 0) {
            prev*=num;
            list.add(prev);
        }
        //if this is 0  we need to reinit the structure
        else {
            list = new ArrayList();
            list.add(1);
            prev = 1;
        }
    }

    public int getProduct(int k) {
        int N = list.size();
        //in case there are not enough elements by the problem definition it can only happen if
        //we have met 0 before. In this case return 0. In all other cases we get the product from
        //division due to prefix product property. Note that list always has n + 1 elements due to 
        //initial 1, we need it to avoid outofbounds checks for edge cases
        return (k < N) ? prev / list.get(N - 1 - k) : 0;
    }
}
*/
class ProductOfNumbers {
    List<Integer> list;
    int prevProd;
    public ProductOfNumbers() {
        list = new ArrayList<Integer>();
        prevProd = 1;
    }
    
    public void add(int num) {
        if(num == 0) {
            list = new ArrayList<Integer>();
            prevProd = 1;
        } else {
            prevProd *= num;
            list.add(prevProd);
        }
    }
    
    public int getProduct(int k) {
        int n = list.size();
        if(n < k) {
            return 0;
        } 
        int result = list.get(n - 1);
        if(n == k) {
            return result;
        } else {
            return result / list.get(n - k - 1);
        }
    }
}

/**
 * Your ProductOfNumbers object will be instantiated and called as such:
 * ProductOfNumbers obj = new ProductOfNumbers();
 * obj.add(num);
 * int param_2 = obj.getProduct(k);
 */
