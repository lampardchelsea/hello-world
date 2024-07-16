/**
 * Linkedin phone interview question (by QiQi)
 *
 * Refer to
 * https://segmentfault.com/a/1190000003906667
 * This is a follow up of Shortest Word Distance. The only difference is now you are given the list of words 
   and your method will be called repeatedly many times with different parameters. How would you optimize it?

    Design a class which receives a list of words in the constructor, and implements a method that takes two words 
    word1 and word2 and return the shortest distance between these two words in the list.

    For example, Assume that words = ["practice", "makes", "perfect", "coding", "makes"].
    Given word1 = “coding”, word2 = “practice”, return 3. 
    Given word1 = "makes", word2 = "coding", return 1.
    Note: You may assume that word1 does not equal to word2, and word1 and word2 are both in the list.
 *
 * 
 * Solution
 * https://www.youtube.com/watch?v=2MviYrr7a-k
 * https://discuss.leetcode.com/topic/20643/java-solution-using-hashmap/14
 * https://segmentfault.com/a/1190000003906667
 * https://discuss.leetcode.com/topic/20643/java-solution-using-hashmap
 * https://discuss.leetcode.com/topic/20641/java-solution-using-minimum-difference-between-2-sorted-arrays
*/
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ShortestDistanceWordII { 
	/**
	 * Refer to
	 * https://segmentfault.com/a/1190000003906667
	 * 哈希表法
	 * 复杂度
	 * 时间 O(N) 空间 O(N)
	 * 思路
	 * 因为会多次调用，我们不能每次调用的时候再把这两个单词的下标找出来。我们可以用一个哈希表，
	   在传入字符串数组时，就把每个单词的下标找出存入表中。这样当调用最短距离的方法时，我们只要
	   遍历两个单词的下标列表就行了。具体的比较方法，则类似merge two list，每次比较两个list
	   最小的两个值，得到一个差值。然后把较小的那个给去掉。因为我们遍历输入数组时是从前往后的，
	   所以下标列表也是有序的。
	*/
	Map<String, List<Integer>> map = new HashMap<String, List<Integer>>();
	public ShortestDistanceWordII(String[] words) {
		for(int i = 0; i < words.length; i++) {
			if(!map.containsKey(words[i])) {
				List<Integer> list = new ArrayList<Integer>();
				list.add(i);
				map.put(words[i], list);
			} else {
				map.get(words[i]).add(i);
			}			
		}
	}
	
	
	// Solution 1: HashMap with O(m * n) Time Complexity
	// https://www.youtube.com/watch?v=2MviYrr7a-k
	// https://discuss.leetcode.com/topic/20643/java-solution-using-hashmap/14
	public int shortest(String word1, String word2) {
	    int min = Integer.MAX_VALUE;
	    for(Integer num1: map.get(word1)) {
		for(Integer num2: map.get(word2)) {
		    min = Math.min(min, Math.abs(num1 - num2));
		}
	    }
	    return min;
	}
		
        // Solution 2: HashMap with O(m + n) Time Complexity
	// Refer to
	// https://segmentfault.com/a/1190000003906667
	// https://discuss.leetcode.com/topic/20643/java-solution-using-hashmap
	// https://discuss.leetcode.com/topic/20641/java-solution-using-minimum-difference-between-2-sorted-arrays
	public int shortest(String word1, String word2) {
		List<Integer> list1 = map.get(word1);
		List<Integer> list2 = map.get(word2);
		int p1 = 0;
		int p2 = 0;
		int result = Integer.MAX_VALUE;
		while(p1 < list1.size() && p2 < list2.size()) {
			result = Math.min(result, Math.abs(list1.get(p1) - list2.get(p2)));
			if(list1.get(p1) < list2.get(p2)) {
				p1++;
			} else {
				p2++;
			}
		}
		return result;
	}
	
	public static void main(String[] args) {
		String[] strings = {"practice", "makes", "perfect", "coding", "makes"};
		ShortestDistanceWordII s = new ShortestDistanceWordII(strings);
		String word1 = "coding";
		//String word2 = "practice";
		String word2 = "makes";
		int result = s.shortest(word1, word2);
		System.out.println(result);
	}
}
































































































https://leetcode.ca/all/244.html
Design a class which receives a list of words in the constructor, and implements a method that takes two words word1 and word2 and return the shortest distance between these two words in the list. Your method will be called repeatedly many times with different parameters.
Example:
Assume that words = ["practice", "makes", "perfect", "coding", "makes"].
Input: word1 = “coding”, word2 = “practice”
Output: 3
Input: word1 = "makes", word2 = "coding"
Output: 1
Note:
You may assume that word1 does not equal to word2, and word1 and word2 are both in the list.
--------------------------------------------------------------------------------
Attempt 1: 2024-07-15
Solution 1: Hash Table + Two Pointers (30 min)
// Class to calculate the shortest distance between two words in a dictionary.
class WordDistance {
    // Create a map to hold lists of indices for each word
    private Map<String, List<Integer>> wordIndicesMap = new HashMap<>();

    // Constructor takes an array of words and populates the map with the words and their indices.
    public WordDistance(String[] wordsDict) {
        for (int i = 0; i < wordsDict.length; ++i) {
            // For each word, add the current index to its list in the map.
            // If the word is not already in the map, create a new list for it.
            wordIndicesMap.computeIfAbsent(wordsDict[i], k -> new ArrayList<>()).add(i);
        }
    }

    // Method to find the shortest distance between two words.
    public int shortest(String word1, String word2) {
        // Retrieve the lists of indices for the two words.
        List<Integer> indicesWord1 = wordIndicesMap.get(word1);
        List<Integer> indicesWord2 = wordIndicesMap.get(word2);

        // Initialize the answer with a high value to ensure it gets replaced.
        int shortestDistance = Integer.MAX_VALUE;

        // Pointers to iterate through the lists for both words.
        int i = 0;
        int j = 0;

        // Iterate through both lists to find the smallest distance.
        while (i < indicesWord1.size() && j < indicesWord2.size()) {
            int indexWord1 = indicesWord1.get(i);
            int indexWord2 = indicesWord2.get(j);
          
            // Update the shortest distance with the minimum distance found so far.
            shortestDistance = Math.min(shortestDistance, Math.abs(indexWord1 - indexWord2));

            // Move the pointer that points to the smaller index forward.
            if (indexWord1 <= indexWord2) {
                ++i;
            } else {
                ++j;
            }
        }
        // Return the shortest distance found.
        return shortestDistance;
    }
}

/**
 * The following comments are provided as an example of how you might use the WordDistance class.
 * WordDistance object can be created by passing in a dictionary of words.
 * You can then call the 'shortest' method to find the shortest distance between any two words.
 */

// Example of using the WordDistance class.
/*
WordDistance wordDistance = new WordDistance(wordsDict);
int result = wordDistance.shortest("word1", "word2");
*/

Refer to
https://algo.monster/liteproblems/244
Problem Description
The problem provides us with the task of designing a data structure that must be able to compute the shortest distance between any two distinct strings within an array of strings. This implies that we first need to initialize the data structure with an array of strings, which we'll refer to as wordsDict. Once initialized, we should be able to query this data structure with two different strings, and it should return the smallest possible index difference between these two strings within the wordsDict array.
This could be visualized as an array where we want to find the minimum distance between two elements, where the elements are the positions of the words we're interested in. For example, if wordsDict is ["practice", "makes", "perfect", "coding", "makes"], and we query for the distance between "coding" and "practice", the returned value should be 3, as the closest "practice" to "coding" is three indices away.
Intuition
To efficiently find the shortest distance between two words in the dictionary, a preprocessing step is required during initialization. We traverse the wordsDict array once and create a hash map where keys are the distinct words from the array, and values are lists of indices where each key word is located in the original array. This preprocessing step allows for a quick lookup of the positions of any word, facilitating the computation of the distances between any two words.
Once the positions are mapped, to find the shortest distance between word1 and word2, we get their list of indices from our hash map. We need to find the minimum difference between any two indices from these lists. The lists are already sorted because the indices were appended in the order they were encountered during initialization.
A two-pointer approach efficiently solves the problem of finding the minimum difference. Start with the first index of each list, and at each step, move the pointer that points to the smaller index to the next index in its list. The intuition behind this is that we try to close the gap between word1 and word2 by moving forward in the list where the current index is smaller. This approach will traverse the two lists simultaneously and will always give the smallest possible difference in indices (thus the shortest distance) between word1 and word2. The process repeats until we have fully traversed one of the lists, ensuring that no potential shorter distance is missed.
The overall time complexity for the shortest operation, after the initial preprocessing, is O(N + M), where N and M are the number of occurrences of word1 and word2 in the dictionary, respectively. The preprocessing itself is O(K), where K is the total number of words in the dictionary, with the space complexity also being O(K) for storing the hash map.
Solution Approach
In the reference solution, the WordDistance class is defined, which implements the required functionality. The class uses a hash map (dictionary in Python) to store the indices of each word as they appear in wordsDict. Python's defaultdict from the collections module is used to handle the automatic creation of entries for new words, with each entry being a list that gets appended with the current index (i) every time the word (w) is encountered in the array.
Here is a breakdown of the implementation:
1.Initialization (init): This method takes a list of strings wordsDict as input. It iterates over wordsDict, enumerating each word with its index. For every word-index pair, it appends the index to the list that corresponds to the word in the hash map self.d. By the end of this process, we have a dictionary where each word is associated with a list of indices indicating its positions in the wordsDict array.
self.d = defaultdict(list)
for i, w in enumerate(wordsDict):
    self.d[w].append(i)
2.Finding Shortest Distance (shortest): This method calculates the shortest distance between two words word1 and word2. By accessing self.d[word1] and self.d[word2], it retrieves the two lists of indices for the given words. The algorithm then initializes two pointers, i and j, starting at 0. These pointers will traverse the index lists. 
The variable ans is initialized to infinity (inf). It will be updated with the minimum difference between indices found so far as we iterate through the lists of indices. Following a two-pointer approach, the algorithm compares the indices at the current pointers and updates ans with the smaller of the current difference and the previously stored ans. Based on which index is smaller, the corresponding pointer (i or j) is incremented to move to the next index in the respective list. 
The loop continues until one list is fully traversed, ensuring that the minimum distance has been found:
a, b = self.d[word1], self.d[word2]
ans = inf
i = j = 0
while i < len(a) and j < len(b):
    ans = min(ans, abs(a[i] - b[j]))
    if a[i] <= b[j]:
        i += 1
    else:
        j += 1
return ans
In the above loop, at each step, because the two lists are sorted by the nature of their construction, we can guarantee that moving the pointer past the smaller index will give us a pair of indices that is potentially closer than the previous one. This algorithm effectively finds the minimum distance between the two words, which is the absolute difference between their closest indices.
The WordDistance class can then be instantiated with a string array wordsDict, and the shortest method can be called to find the shortest distance between any two words.
Example Walkthrough
Let's demonstrate how the WordDistance class and its methods work using a simple example.
Suppose our wordsDict is ["the", "quick", "brown", "fox", "quick"]. We initialize the WordDistance class with this list of strings. During the initialization process, the class builds the hash map self.d which will contain:
- "the" -> [0]
- "quick" -> [1, 4]
- "brown" -> [2]
- "fox" -> [3]
Now, let's say we want to find the shortest distance between the words "brown" and "quick". We call the shortest method with these two words. Here's what happens inside this method:
1.We access the two lists of indices for the words "brown" and "quick" from our hash map, which gives us a = [2] and b = [1, 4].
2.We initialize two pointers i and j and a variable ans which will keep track of the minimum distance. Initially, i = 0, j = 0, ans = inf.
3.We enter a while-loop, which will continue until we have fully traversed one of the lists.
4.Inside the loop, we compare a[i] with b[j]. At the start, a[i] = 2 and b[j] = 1. The difference |2 - 1| = 1 is less than ans, so we update ans = 1.
5.Since the current value at a[i] is greater than b[j], we increment j to move to the next index in list b.
6.Now, i = 0 and j = 1, which means a[i] = 2 and b[j] = 4. The difference |2 - 4| = 2 is not less than the current ans, so ans remains 1.
7.We've reached the end of list a, so the loop terminates.
Since we have examined all possible pairs of indices for "brown" and "quick", we have found the shortest distance to be 1, which is the minimum index difference between these words in the wordsDict.
Finally, we return ans which is 1, concluding that the shortest distance between "brown" and "quick" in the wordsDict array is 1.
Solution Implementation
// Class to calculate the shortest distance between two words in a dictionary.
class WordDistance {
    // Create a map to hold lists of indices for each word
    private Map<String, List<Integer>> wordIndicesMap = new HashMap<>();

    // Constructor takes an array of words and populates the map with the words and their indices.
    public WordDistance(String[] wordsDict) {
        for (int i = 0; i < wordsDict.length; ++i) {
            // For each word, add the current index to its list in the map.
            // If the word is not already in the map, create a new list for it.
            wordIndicesMap.computeIfAbsent(wordsDict[i], k -> new ArrayList<>()).add(i);
        }
    }

    // Method to find the shortest distance between two words.
    public int shortest(String word1, String word2) {
        // Retrieve the lists of indices for the two words.
        List<Integer> indicesWord1 = wordIndicesMap.get(word1);
        List<Integer> indicesWord2 = wordIndicesMap.get(word2);

        // Initialize the answer with a high value to ensure it gets replaced.
        int shortestDistance = Integer.MAX_VALUE;

        // Pointers to iterate through the lists for both words.
        int i = 0;
        int j = 0;

        // Iterate through both lists to find the smallest distance.
        while (i < indicesWord1.size() && j < indicesWord2.size()) {
            int indexWord1 = indicesWord1.get(i);
            int indexWord2 = indicesWord2.get(j);
          
            // Update the shortest distance with the minimum distance found so far.
            shortestDistance = Math.min(shortestDistance, Math.abs(indexWord1 - indexWord2));

            // Move the pointer that points to the smaller index forward.
            if (indexWord1 <= indexWord2) {
                ++i;
            } else {
                ++j;
            }
        }
        // Return the shortest distance found.
        return shortestDistance;
    }
}

/**
 * The following comments are provided as an example of how you might use the WordDistance class.
 * WordDistance object can be created by passing in a dictionary of words.
 * You can then call the 'shortest' method to find the shortest distance between any two words.
 */

// Example of using the WordDistance class.
/*
WordDistance wordDistance = new WordDistance(wordsDict);
int result = wordDistance.shortest("word1", "word2");
*/
Time and Space Complexity
Initialization (init)
Time Complexity: O(N), where N is the length of wordsDict. This is because we're iterating through each word in the list and inserting the index into the dictionary.
Space Complexity: O(N), as in the worst-case scenario, we're storing the index of each word within the dictionary, which would require space proportional to the number of words.
Shortest Method (shortest)
Time Complexity: O(L + M), where L is the length of the list associated with word1 and M is the length of the list associated with word2. In the worst case, we traverse each list once in a single run of the while loop.
Space Complexity: O(1), because we're only using a few variables (i, j, ans, a, b) for calculations and not utilizing any additional space that is dependent on the size of the input.
--------------------------------------------------------------------------------
Why we cannot use single pass like Leetcode 243 pointer movement logic directly in Leetcode 244 ?
Refer to chatGPT
In Leetcode 244, the problem involves finding the shortest distance between two words, but with an additional complexity: the function shortestWordDistance can be called multiple times with different pairs of words, and it needs to handle the scenario where the two words can be the same. This adds a layer of complexity that makes the single pass approach less straightforward compared to Leetcode 243.
Key Differences Between Leetcode 243 and 244
1.Single Query vs. Multiple Queries:
- Leetcode 243: You find the shortest distance between two given words in a single pass.
- Leetcode 244: You might need to find the shortest distance for multiple pairs of words, which may include scenarios where both words are the same.
2.Handling Same Words:
- Leetcode 243: The words are different, simplifying the logic since you only need to track the most recent positions of word1 and word2.
- Leetcode 244: The words can be the same, requiring special handling to avoid considering the same index for both words.
Implementation Differences
Leetcode 243 Single Pass Approach:
- Traverse the list once, updating indices for word1 and word2, and compute the minimum distance when both indices are found.
Leetcode 244 Two-Pointer Approach:
- Use two separate lists to store indices of occurrences for word1 and word2.
- Use a two-pointer technique to find the minimum distance by comparing elements in the two lists.
Why Single Pass Logic from 243 Can't Be Directly Applied to 244
1.Complexity of Handling Multiple Pairs:
- In Leetcode 244, since the method can be called multiple times with different word pairs, maintaining separate lists of indices for each word and using a two-pointer approach ensures that the solution remains efficient even when handling multiple queries.
2.Handling Same Words:
- When word1 and word2 are the same, you need to ensure you don't compare the same index with itself. Using a single pass approach complicates this because you need to distinguish between consecutive occurrences of the same word while maintaining the shortest distance calculation.

Refer to
L243.Shortest Word Distance (Ref.L244)
