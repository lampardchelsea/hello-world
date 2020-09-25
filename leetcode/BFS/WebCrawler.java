/**
 Refer to
 https://code.dennyzhang.com/web-crawler
 Given a url startUrl, implement a web crawler to crawl all links that are under the same hostname as startUrl.
 
 Your crawler should:
 1. Start from the page: startUrl
 2. Call HtmlParser.getUrls(url) to get all urls from a webpage of given url.
 3. Do not crawl the same link twice.
 4. Only crawl the links that are under the same hostname as startUrl.
 
 As shown in the example url above, the hostname is example.org. For simplicity sake, you may assume all urls use 
 http protocol without any port specified. For example, the urls http://leetcode.com/problems and http://leetcode.com/contest 
 are under the same hostname, while urls http://example.org/test and http://example.com/abc are not under the same hostname.

Example 1:
Input:
urls = [
  "http://news.yahoo.com",
  "http://news.yahoo.com/news",
  "http://news.yahoo.com/news/topics/",
  "http://news.google.com",
  "http://news.yahoo.com/us"
]
edges = [[2,0],[2,1],[3,2],[3,1],[0,4]]
startUrl = "http://news.yahoo.com/news/topics/"
Output: [
  "http://news.yahoo.com",
  "http://news.yahoo.com/news",
  "http://news.yahoo.com/news/topics/",
  "http://news.yahoo.com/us"
]
Explanation:
Edges show how these urls connect with each other.
1. startUrl http://news.yahoo.com/news/topics/ (index 2) links to:
     -> http://news.yahoo.com/news (index 1)
     -> http://news.yahoo.com      (index 0). 
2. http://news.yahoo.com (index 0) links to:
     -> http://news.yahoo.com/us   (index 4). 
     
Example 2:
Input: 
urls = [
  "http://news.yahoo.com",
  "http://news.yahoo.com/news",
  "http://news.yahoo.com/news/topics/",
  "http://news.google.com"
]
edges = [[0,2],[2,1],[3,2],[3,1],[3,0]]
startUrl = "http://news.google.com"
Output: ["http://news.google.com"]
Explanation: The startUrl links to all other pages that do not share the same hostname.

Constraints:
1 <= urls.length <= 1000
1 <= urls[i] <= 300
Hostname labels may contain only the ASCII letters ‘a’ through ‘z’ (in a case-insensitive manner), 
the digits ‘0’ through ‘9’ and the hyphen-minus character (‘-‘).
The hostname may not start or end with the hyphen-minus character (‘-‘).
See: https://en.wikipedia.org/wiki/Hostname#Restrictions_on_valid_hostnames
You may assume there’re no duplicates in url library.
*/
