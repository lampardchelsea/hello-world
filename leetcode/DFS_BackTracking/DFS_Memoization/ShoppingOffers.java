/**
 Refer to
 https://leetcode.com/problems/shopping-offers/
 In LeetCode Store, there are some kinds of items to sell. Each item has a price.

However, there are some special offers, and a special offer consists of one or more 
different kinds of items with a sale price.

You are given the each item's price, a set of special offers, and the number we need 
to buy for each item. The job is to output the lowest price you have to pay for exactly 
certain items as given, where you could make optimal use of the special offers.

Each special offer is represented in the form of an array, the last number represents 
the price you need to pay for this special offer, other numbers represents how many 
specific items you could get if you buy this offer.

You could use any of special offers as many times as you want.

Example 1:
Input: [2,5], [[3,0,5],[1,2,10]], [3,2]
Output: 14
Explanation: 
There are two kinds of items, A and B. Their prices are $2 and $5 respectively. 
In special offer 1, you can pay $5 for 3A and 0B
In special offer 2, you can pay $10 for 1A and 2B. 
You need to buy 3A and 2B, so you may pay $10 for 1A and 2B (special offer #2), and $4 for 2A.

Example 2:
Input: [2,3,4], [[1,1,0,4],[2,2,1,9]], [1,2,1]
Output: 11
Explanation: 
The price of A is $2, and $3 for B, $4 for C. 
You may pay $4 for 1A and 1B, and $9 for 2A ,2B and 1C. 
You need to buy 1A ,2B and 1C, so you may pay $4 for 1A and 1B (special offer #1), and $3 for 1B, $4 for 1C. 
You cannot add more items, though only $9 for 2A ,2B and 1C.

Note:
There are at most 6 kinds of items, 100 special offers.
For each item, you need to buy at most 6 of them.
You are not allowed to buy more items than you want, even if that would lower the overall price.
*/
// Solution 1: Using Recursion [Accepted]
// https://leetcode.com/problems/shopping-offers/solution/
// https://leetcode.com/problems/shopping-offers/discuss/105212/Very-Easy-to-understand-JAVA-Solution-beats-95-with-explanation
// Runtime: 8 ms, faster than 71.06% of Java online submissions for Shopping Offers.
// Memory Usage: 40.6 MB, less than 57.69% of Java online submissions for Shopping Offers.
/**
 Before discussing the steps involved in the process, we need to note a few points. 
 Firstly, whenever an offer is used from amongst the ones available in the specialspecial list, 
 we need to update the needsneeds appropriately, such that the number of items in the current 
 offer of each type are deducted from the ones in the corresponding entry in needsneeds.

Further, an offer can be used only if the number of items, of each type, required for using the offer, 
is lesser than or equal to the ones available in the current needsneeds.

Now, let's discuss the algorithm. We make use of a shopping(price,special,needs) function, 
which takes the priceprice and specialspecial list along with the current(updated) needsneeds 
as the input and returns the minimum cost of buying these items as required by this needsneeds list.

In every call of the function shopping(price,special,needs), we do as follows:

Determine the cost of buying items as per the needsneeds array, without applying any offer. 
Store the result in resres.

Iterate over every offer in the specialspecial list. For every offer chosen, repeat steps 3 to 5.

Create a copy of the current needsneeds in a cloneclone list(so that the original needs can be used again, 
while selecting the next offer).

Try to apply the current offer. If possible, update the required number of items in cloneclone.

If the current offer could be applied, find the minimum cost out of resres and 
offer_\current + shopping(price,special,clone). Here, offer_\current refers to the price that needs 
to be paid for the current offer. Update the resres with the minimum value.

Return the resres corresponding to the minimum cost.

We need to note that the cloneclone needs to be updated afresh from needsneeds(coming to the current 
function call) when we choose a new offer. This needs to be done, because solely applying the next 
offer could result in a lesser cost than the one resulting by using the previous offer first.
*/
class Solution {
    public int shoppingOffers(List<Integer> price, List<List<Integer>> special, List<Integer> needs) {
        return helper(price, special, needs);  
    }
    
    private int helper(List<Integer> price, List<List<Integer>> special, List<Integer> needs) {
        int min = directPurchase(price, needs);
        for(int i = 0; i < special.size(); i++) {
            List<Integer> offer = special.get(i);
            List<Integer> temp = new ArrayList<Integer>();
            for(int j = 0; j < needs.size(); j++) {
                // check if the current offer is valid
                if(needs.get(j) < offer.get(j)) {
                    temp = null;
                    break;
                }
                temp.add(needs.get(j) - offer.get(j));
            }
            // use the current offer and try next
            if(temp != null) {
                min = Math.min(min, offer.get(offer.size() - 1) + helper(price, special, temp));
            }
        }
        return min;
    }
    
    private int directPurchase(List<Integer> price, List<Integer> needs) {
        int min = 0;
        for(int i = 0; i < price.size(); i++) {
            min += price.get(i) * needs.get(i);
        }
        return min;
    }
}

// Solution 2: Using Recursion with memoization
// Refer to
// https://leetcode.com/problems/shopping-offers/discuss/105212/Very-Easy-to-understand-JAVA-Solution-beats-95-with-explanation
// https://leetcode.com/problems/shopping-offers/discuss/105212/Very-Easy-to-understand-JAVA-Solution-beats-95-with-explanation/142244
// https://leetcode.com/problems/shopping-offers/discuss/105212/Very-Easy-to-understand-JAVA-Solution-beats-95-with-explanation/137618
// Runtime: 4 ms, faster than 91.09% of Java online submissions for Shopping Offers.
// Memory Usage: 38.5 MB, less than 73.08% of Java online submissions for Shopping Offers.
/**
In the last approach, we can observe that the same needsneeds can be reached by applying the offers in 
various orders. e.g. We can choose the first offer followed by the second offer or vice-versa. 
But, both lead to the same requirement of updated needsneeds and the cost as well. Thus, instead of 
repeating the whole process for the same needsneeds state through various recursive paths, we can 
create an entry corresponding to the current set of needsneeds in a HashMap, mapmap, which stores 
the minimum cost corresponding to this set of needsneeds. Thus, whenever the same call is made again 
in the future through a different path, we need not repeat the whole process over, and we can directly 
return the result stored in the mapmap.
*/
class Solution {
    public int shoppingOffers(List<Integer> price, List<List<Integer>> special, List<Integer> needs) {
        Map<List<Integer>, Integer> map = new HashMap<List<Integer>, Integer>();
        return helper(price, special, needs, map, 0);  
    }
    
    private int helper(List<Integer> price, List<List<Integer>> special, List<Integer> needs, Map<List<Integer>, Integer> map, int cur) {
        if(map.containsKey(needs)) {
            return map.get(needs);
        }
        int min = directPurchase(price, needs);
        for(int i = cur; i < special.size(); i++) {
            List<Integer> offer = special.get(i);
            List<Integer> temp = new ArrayList<Integer>();
            for(int j = 0; j < needs.size(); j++) {
                // check if the current offer is valid
                if(needs.get(j) < offer.get(j)) {
                    temp = null;
                    break;
                }
                temp.add(needs.get(j) - offer.get(j));
            }
            // use the current offer and try next
            if(temp != null) {
                min = Math.min(min, offer.get(offer.size() - 1) + helper(price, special, temp, map, i));
                map.put(needs, min);
            }
        }
        return min;
    }
    
    private int directPurchase(List<Integer> price, List<Integer> needs) {
        int min = 0;
        for(int i = 0; i < price.size(); i++) {
            min += price.get(i) * needs.get(i);
        }
        return min;
    }
}
