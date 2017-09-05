/**
 * Refer to
 * http://www.cnblogs.com/grandyang/p/5184143.html
 * Design and implement a TwoSum class. It should support the following operations:add and find.

    add - Add the number to an internal data structure.
    find - Find if there exists any pair of numbers which sum is equal to the value.

    For example,
    add(1); add(3); add(5);
    find(4) -> true
    find(7) -> false
 *
 * Solution
 * http://www.cnblogs.com/grandyang/p/5184143.html
 * 这道题让我们设计一个Two Sum的数据结构，跟LeetCode的第一道题Two Sum没有什么太大的不一样，作为LeetCode的首题，
   Two Sum的名气不小啊，正所谓平生不会TwoSum，刷尽LeetCode也枉然。记得原来在背单词的时候，总是记得第一个单词是
   abandon，结果有些人背来背去还在abandon，有时候想想刷题其实跟背GRE红宝书没啥太大的区别，都是一个熟练功夫，
   并不需要有多高的天赋，只要下足功夫，都能达到一个很不错的水平，套用一句鸡汤问来激励下吧，“有些时候我们的努力程度
   根本达不到需要拼天赋的地步”，好了，不闲扯了，来看题吧。不过这题也没啥可讲的，会做Two Sum的这题就很简单了，我们
   先来看用哈希表的解法，我们把每个数字和其出现的次数建立映射，然后我们遍历哈希表，对于每个值，我们先求出此值和目标
   值之间的差值t，然后我们需要分两种情况来看，如果当前值不等于差值t，那么只要哈希表中有差值t就返回True，或者是当差
   值t等于当前值时，如果此时哈希表的映射次数大于1，则表示哈希表中还有另一个和当前值相等的数字，二者相加就是目标值
*/

public class TwoSum {
	private Map<Integer, Integer> hash;
	private List<Integer> nums;
	public TwoSum() {
	    hash = new HashMap<Integer, Integer>();
		nums = new ArrayList<Integer>();
	}
	
	// Add the number to an internal data structure
	public void add(int number) {
		nums.add(number);
		if(!hash.containsKey(number)) {
		    hash.put(number, 1);
		} else {
		    hash.put(number, hash.get(number) + 1);
		}
	}
	
	// Find if there exists any pair of numbers which sum is equal to the value
	public boolean find(int value) {
	    for(int i = 0; i < nums.size(); i++) {
		    if(nums.get(i) == value - nums.get(i)) {
			    if(hash.get(nums.get(i)) > 1) {
				     return true;
				}
			} else if(hash.containsKey(value - nums.get(i))) {
			     return true;
			}
		}
		return false;
	}
}
