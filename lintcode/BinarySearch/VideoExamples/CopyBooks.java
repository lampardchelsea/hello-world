https://www.lintcode.com/problem/copy-books/description

Given n books and the i-th book has pages[i] pages. There are k persons to copy these books.

These books list in a row and each person can claim a continous range of books. For example, one copier can copy the books from i-th to j-th continously, but he can not copy the 1st book, 2nd book and 4th book (without 3rd book).

They start copying books at the same time and they all cost 1 minute to copy 1 page of a book. What's the best strategy to assign books so that the slowest copier can finish at earliest time?

Return the shortest time that the slowest copier spends.

Example 1:
```
Input: pages = [3, 2, 4], k = 2 
Output: 5 
Explanation:  
    First person spends 5 minutes to copy book 1 and book 2. 
    Second person spends 4 minutes to copy book 3.
```

Example 2:
```
Input: pages = [3, 2, 4], k = 3 
Output: 4 
Explanation: Each person copies one of the books.
```

---
Attempt 1: 2022-10-04

Solution 1: 60min, Binary Search Find Lower Boundary (template based on while(lo <= hi), refer L704.Binary Search), too long since hard to come with auxiliary function to help sort out the Binary Search model
```
public class Solution { 
    /** 
     * @param pages: an array of integers 
     * @param k: An integer 
     * @return: an integer 
     */ 
    public int copyBooks(int[] pages, int k) { 
        // 完成时间的 lower bound: max(pages), 理解为一共有len(pages)个人，每个人只复印一本书 
        // 完成时间的 upper bound: sum(pages), 理解为只有一个人要独自复印所有的书 
        int lo = 0; 
        int hi = 0; 
        for(int page : pages) { 
            lo = Math.max(lo, page); 
            hi += page; 
        } 
        while(lo <= hi) { 
            int mid = lo + (hi - lo) / 2; 
            if(peopleNeeded(pages, mid, k)) { 
                hi = mid - 1; 
            } else { 
                lo = mid + 1; 
            } 
        } 
        return lo; 
    } 
    // 用二分法来做。二分可能的答案。每次判断什么呢？判断k个人能不能在这个时间里搞定。 
    // peopleNeeded()这个函数用来统计给定一个时间minTime需要多少人可以在这个时间内 
    // 搞定抄那么多书。如果>k，说明这个时间k个人搞不定，时间要再久一点才可以。如果<=k， 
    // 说明不到k个人就能在这个时间内搞定，我们可以试试更少一点的时间。 
    private boolean peopleNeeded(int[] pages, int minTimeNeeded, int k) { 
        // 至少有一个人copy，所以count从1开始 
        int people = 1; 
        int sum = 0; 
        for(int page : pages) { 
            // Must pre-check the 'sum + page > minTimeNeeded', then 'sum += page' 
            // reverse order will lead wrong calculation on 'people' 
            if(sum + page > minTimeNeeded) { 
                people++; 
                sum = 0; 
            } 
            sum += page; 
        } 
        return people <= k; 
    } 
}

Space Complexity: O(1)           
Time Complexity: O(nlogn)
```

Refer to
https://yeqiuquan.blogspot.com/2017/03/lintcode-437-copy-books.html
用二分法来做。二分可能的答案。每次判断什么呢？判断k个人能不能在这个时间里搞定。people()这个函数用来统计给定一个时间minTime需要多少人可以在这个时间内搞定抄那么多书。如果>k，说明这个时间k个人搞不定，时间要再久一点才可以。如果<k，说明不到k个人就能在这个时间内搞定，我们可以试试更少一点的时间。


```
public class Solution {
    /**
     * @param pages: an array of integers
     * @param k: an integer
     * @return: an integer
     */
    public int copyBooks(int[] pages, int k) {
        // write your code here
        
        if (pages == null || pages.length == 0) {
            return 0;
        }
        
        int start = 0;
        int end = 0;
        for (int i = 0; i < pages.length; i++) {
            end += pages[i];
            start = Math.max(start, pages[i]);
        }
        
        while (start + 1 < end) {
            int mid = start + (end - start) / 2;
            if (people(pages, mid) > k) {
                start = mid;
            }
            else {
                end = mid;
            }
        }
        
        if (people(pages, start) <= k) {
            return start;
        }
        return end;
        
    }
    
    public int people(int[] pages, int minTime) {
        if (pages.length == 0) {
            return 0;
        }
        
        int sum = 0;
        int people = 1;
        for (int i = 0; i < pages.length; i++) {
            if (sum + pages[i] > minTime) {
                people++;
                sum = 0;
            }
            sum += pages[i];
        }
        
        return people;
    }
}
```

Refer to
https://www.lintcode.com/problem/copy-books/solution/17469
非常值得总结的二分答案的题目思路就是，对于 完成时间 进行二分查找，找到第一个 不多于K个人能在给定的完成时间里完成 复印len(pages)本书 这一任务的 完成时间
完成时间的lower bound: max(pages), 理解为一共有len(pages)个人，每个人只复印一本书
完成时间的upper bound: sum(pages), 理解为只有一个人要独自复印所有的书
```
class Solution:
    """
    @param pages: an array of integers
    @param k: An integer
    @return: an integer
    """
    def copyBooks(self, pages, k):
        # write your code here
        
        if not pages:
            
            return 0 
            
        start = max(pages)
        end = sum(pages)
        
        while start + 1 < end:
            
            mid = start + (end - start) // 2 
            
            if self.can_complete(pages, k, mid):
                
                end = mid 
                
            else:
                
                start = mid 
                
        if self.can_complete(pages, k, start):
            
            return start 
            
        # Since even if there is only one person,
        # he or she can copy all books, just return end.
        return end 
        
    def can_complete(self, pages, k, tl):
        
        num = 1 
        
        pageSum = 0
        
        for page in pages:
            
            if pageSum + page <= tl:
                
                pageSum += page 
                
            else:
                
                num += 1 
                pageSum = page 
                
        return num <= k
```

---
Solution 2: DP 
