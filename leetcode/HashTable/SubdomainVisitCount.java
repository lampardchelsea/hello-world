https://leetcode.com/problems/subdomain-visit-count/description/
A website domain "discuss.leetcode.com" consists of various subdomains. At the top level, we have "com", at the next level, we have "leetcode.com" and at the lowest level, "discuss.leetcode.com". When we visit a domain like "discuss.leetcode.com", we will also visit the parent domains "leetcode.com" and "com" implicitly.
A count-paired domain is a domain that has one of the two formats "rep d1.d2.d3" or "rep d1.d2" where rep is the number of visits to the domain and d1.d2.d3 is the domain itself.
For example, "9001 discuss.leetcode.com" is a count-paired domain that indicates that discuss.leetcode.com was visited 9001 times.
Given an array of count-paired domains cpdomains, return an array of the count-paired domains of each subdomain in the input. You may return the answer in any order.

Example 1:
Input: cpdomains = ["9001 discuss.leetcode.com"]
Output: ["9001 leetcode.com","9001 discuss.leetcode.com","9001 com"]
Explanation: We only have one website domain: "discuss.leetcode.com".As discussed above, the subdomain "leetcode.com" and "com" will also be visited. So they will all be visited 9001 times.

Example 2:
Input: cpdomains = ["900 google.mail.com", "50 yahoo.com", "1 intel.mail.com", "5 wiki.org"]
Output: ["901 mail.com","50 yahoo.com","900 google.mail.com","5 wiki.org","5 org","1 intel.mail.com","951 com"]
Explanation: We will visit "google.mail.com" 900 times, "yahoo.com" 50 times, "intel.mail.com" once and "wiki.org" 5 times.For the subdomains, we will visit "mail.com" 900 + 1 = 901 times, "com" 900 + 50 + 1 = 951 times, and "org" 5 times.
 
Constraints:
- 1 <= cpdomain.length <= 100
- 1 <= cpdomain[i].length <= 100
- cpdomain[i] follows either the "repi d1i.d2i.d3i" format or the "repi d1i.d2i" format.
- repi is an integer in the range [1, 10^4].
- d1i, d2i, and d3i consist of lowercase English letters.
--------------------------------------------------------------------------------
Attempt 1: 2024-05-23
Solution 1: Hash Table (10 min)
class Solution {
    public List<String> subdomainVisits(String[] cpdomains) {
        Map<String, Integer> freq = new HashMap<>();
        for(String cpdomain : cpdomains) {
            int i = cpdomain.indexOf(' ');
            int count = Integer.valueOf(cpdomain.substring(0, i));
            String s = cpdomain.substring(i + 1);
            for(int j = 0; j < s.length(); j++) {
                if(s.charAt(j) == '.') {
                    String d = s.substring(j + 1);
                    freq.put(d, freq.getOrDefault(d, 0) + count);
                }
            }
            freq.put(s, freq.getOrDefault(s, 0) + count);
        }
        List<String> result = new ArrayList<>();
        for(String d : freq.keySet()) {
            result.add(freq.get(d) + " " + d);
        }
        return result;
    }
}

Time Complexity: O(N), where N is the length of cpdomains, and assuming the length of cpdomains[i] is fixed.

Space Complexity: O(N), the space used in our count.

Refer to
https://algo.monster/liteproblems/811
Problem Description
In this problem, we're working with domain visit counts. A domain may have multiple subdomains, and the count given for a subdomain implies that all of its parent domains were visited the same number of times. A "count-paired domain" will be specified in the format rep d1.d2.d3, where rep represents the number of times the domain d1.d2.d3 was visited.
The task is to analyze an array of these count-paired domains, and output an array of count-paired domains that represent the counts of all possible subdomains. Let's take the example given 9001 discuss.leetcode.com. The count 9001 applies not only to discuss.leetcode.com, but also to its parent domains leetcode.com and com. Therefore, the output should reflect the count for all of these domains.
Intuition
The intuition behind the solution is to break down the given domains into all possible subdomains while keeping track of their visit counts. We'll iterate through each count-paired domain given in the array and parse it to extract the visit count and the domain itself. Once we have those, we'll increment the count for that domain and all of its subdomains.
To achieve this, we can use a data structure like a Python Counter to map domains to their visit counts. As we encounter each domain and its subdomains, we add the visit count to the total already stored in the Counter for that domain or subdomain.
Let's break this down further:
We traverse the given list of count-paired domains.
For each count-paired domain, we identify the visit count and the full domain.
We iterate through the characters in the full domain and whenever we encounter a dot('.') or a space (' '), we recognize a subdomain.
We update the Counter with the visit count for the domain or subdomain found.
After processing all count-paired domains, we'll have a Counter that contains all the domains and their respective total visit counts.
The final step is to format the output as requested, which would be to convert the domain and counts back into the rep d1.d2.d3 format for the result array.
By using this approach, we make sure that all the subdomains are accounted for, and we collect the total visit counts in a straightforward and efficient manner.
Solution Approach
The solution uses the Counter data structure from Python's collections module, which is a subclass of dict. It's specifically designed to count objects and is an ideal choice for this problem since we need to count visits for each domain and its subdomains.
Here's the step-by-step approach of the algorithm using the Counter:
1.Initialize a Counter instance (named cnt in the code) to keep track of the count of domains.
2.Iterate over the list of cpdomains:
- For each domain string, find the first space character which delimits the visit count and the domain.
- Extract the visit count and convert it to an integer (v).
- Iterate through each character of the domain:
- When a dot '.' or space ' ' is encountered, it marks the beginning of a (sub)domain.
- Update the Counter by adding the visit count v to the current (sub)domain.
- This is done using the notation cnt[s[i + 1 :]] += v, which incrementally adds the count to the existing value for the subdomain string s[i + 1 :]. The slicing s[i + 1 :] creates substrings representing the current domain and its subsequent subdomains each time a dot or space is encountered.
3.After the loop, cnt contains all the (sub)domains and their respective total counts.
4.Finally, the solution prepares the output by formatting the Counter items into a list of strings (f'{v} {s}'), each corresponding to a "count-paired domain".
The algorithm is efficient for a couple of reasons. First, iterating through the cpdomains array takes O(N) time, where N is the total number of domains and subdomains. Second, updating the Counter takes constant time O(1) for each subdomain because dictionaries in Python have an average-case time complexity of O(1) for updates. Lastly, the Counter nicely organizes our counts and domains, making the final output generation step simple.
Example Walkthrough
Let's consider a simple example with the following input array: ["900 google.mail.com", "50 yahoo.com", "1 intel.mail.com", "5 wiki.org"]. We need to output the counts for all domains and subdomains.
Follow these steps:
1.Initialize a Counter instance to keep track of domain counts.
2.Start with the first domain string "900 google.mail.com". Split the string at the space to get the count 900 and the domain google.mail.com.
3.Begin parsing the domain:
- Since google.mail.com has no spaces, add "google.mail.com": 900 to the Counter.
- Encounter the first dot, yielding the subdomain mail.com. Add "mail.com": 900 to the Counter.
- Encounter the second dot, yielding the subdomain com. Add "com": 900 to the Counter.
4.Move to the second domain string "50 yahoo.com":
- Split and obtain count 50 and domain yahoo.com.
- Add "yahoo.com": 50 to the Counter.
- Add "com": 50 to the existing count of com in the Counter, now com has a total of 950.
5.Process "1 intel.mail.com" similarly, resulting in:
- "intel.mail.com": 1 added to the Counter.
- "mail.com": 1 is added to existing mail.com count, now mail.com has 901.
- "com": 1 is added to existing com count, now com has 951.
6.Lastly, "5 wiki.org":
- Split into count 5 and domain wiki.org.
- Add "wiki.org": 5 to the Counter.
- Add "org": 5 to the Counter, as it's the first time we encounter org.
The Counter now has the correct total counts for all domains and subdomains. Here's what the final Counter looks like:
- {"google.mail.com": 900, "mail.com": 901, "com": 951, "yahoo.com": 50, "wiki.org": 5, "org": 5, "intel.mail.com": 1}
The next step is to format this output according to the rep d1.d2.d3 format. Iterate through the Counter and construct the result strings:
- "900 google.mail.com"
- "901 mail.com"
- "951 com"
- "50 yahoo.com"
- "5 wiki.org"
- "5 org"
- "1 intel.mail.com"
The final output is the array: ["900 google.mail.com", "901 mail.com", "951 com", "50 yahoo.com", "5 wiki.org", "5 org", "1 intel.mail.com"]. This array represents the visit counts for each domain and subdomain.
Solution Implementation
class Solution {
  
    public List<String> subdomainVisits(String[] cpdomains) {
        // Create a map to store subdomain visit counts.
        Map<String, Integer> visitCounts = new HashMap<>();
      
        // Iterate over each combined domain visit count entry.
        for (String domainInfo : cpdomains) {
            // Find the index of the first space to separate number of visits from the domain.
            int spaceIndex = domainInfo.indexOf(" ");
            // Parse the number of visits using the substring before the space.
            int visitCount = Integer.parseInt(domainInfo.substring(0, spaceIndex));
          
            // Start iterating characters at the space to check for subdomains.
            for (int i = spaceIndex; i < domainInfo.length(); ++i) {
                // Check for boundaries of subdomains.
                if (domainInfo.charAt(i) == ' ' || domainInfo.charAt(i) == '.') {
                    // Extract the subdomain using substring.
                    String subdomain = domainInfo.substring(i + 1);
                    // Update the visit count for the current subdomain.
                    visitCounts.put(subdomain, visitCounts.getOrDefault(subdomain, 0) + visitCount);
                }
            }
        }
      
        // Prepare the answer list containing formatted results.
        List<String> results = new ArrayList<>();
        // Iterate through the entry set of the visitCounts map.
        for (Map.Entry<String, Integer> entry : visitCounts.entrySet()) {
            // Format the entry as "visit_count domain" and add it to the results list.
            results.add(entry.getValue() + " " + entry.getKey());
        }
      
        return results;
    }
}
Time and Space Complexity
Time complexity of the code is determined by several factors:
Iterating through each domain in cpdomains, which gives us O(n) where n is the number of domains.
Inside the loop, the index() method is called to find the first space, which operates in O(m), where m is the length of the string s.
The inner loop enumerates over each character in the domain string s which is O(m) in the worst case.
The slicing of strings s[i + 1 :] is done in each loop iteration, which, in Python, is also O(m).
The second to the fourth steps occur within the loop of the first step, so the total time complexity is O(n * m) since we have to assume that each domain could be of variable length, and we are bound by the length of both the domains and the list itself.
Space Complexity
For space complexity, we consider the memory allocations:
The Counter object cnt storage grows with the number of distinct subdomains found, in the worst case this may contain all the subdomains which would be O(d) where d is the total number of distinct subdomains.
The created list for the final output will have as many elements as there are entries in cnt which is also O(d).
The space complexity, therefore, is O(d), which is determined by the number of unique subdomains.
