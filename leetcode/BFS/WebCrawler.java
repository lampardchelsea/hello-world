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

// Solution 1: Stack
// Refer to
// https://github.com/varunu28/LeetCode-Java-Solutions/blob/master/Medium/Web%20Crawler.java
/**
 * // This is the HtmlParser's API interface.
 * // You should not implement it, or speculate about its implementation
 * interface HtmlParser {
 *     public List<String> getUrls(String url) {}
 * }
 */
class Solution {
    public List<String> crawl(String startUrl, HtmlParser htmlParser) {
        Set<String> visited = new HashSet<>();
        Stack<String> stack = new Stack<>();
        stack.push(startUrl);
        String hostname = getHostname(startUrl);
        while(!stack.isEmpty()) {
            String popped = stack.pop();
            visited.add(popped);
            List<String> connectedUrls = htmlParser.getUrls(popped);
            for (String url : connectedUrls) {
                if (!visited.contains(url) && url.contains(hostname)) {
                    stack.push(url);
                }
            }
        }
        return new ArrayList<>(visited);
    }
 
    private String getHostname(String url) {
        String[] splits = url.split("/");
        return splits[2];
    }
}

// Solution 2: DFS
// Refer to
// https://leetcode.jp/leetcode-1236-web-crawler-%E8%A7%A3%E9%A2%98%E6%80%9D%E8%B7%AF%E5%88%86%E6%9E%90/
/**
 解题思路分析：这是一道简单的搜索类题目，不论dfs或是bfs均可解题。在搜索时要注意排重，遇到已经抓取过的页面需要跳过
Set<String> res = new HashSet<>(); // 返回结果
public List<String> crawl(String startUrl, HtmlParser htmlParser) {
    String host = getUrl(startUrl); // 取得域名
    res.add(startUrl); // 将startUrl添加至返回结果
    dfs(startUrl, host, htmlParser); // 开始dfs
    return new ArrayList<>(res);
}

void dfs(String startUrl, String host, HtmlParser htmlParser){
    // 取得当前页面包含的所有链接
    List<String> urls = htmlParser.getUrls(startUrl);
    // 通过每一个链接继续dfs
    for(String url : urls){
        // 如果该链接已经爬过或是与网站域名不一致时跳过
        if(res.contains(url)||!getUrl(url).equals(host)){
            continue;
        }
        // 将该链接加入返回结果
        res.add(url);
        // 继续dfs
        dfs(url, host, htmlParser);
    }
}
public String getUrl(String url) {
    String[] args = url.split("/");
    return args[2];
}
*/
/**
 * // This is the HtmlParser's API interface.
 * // You should not implement it, or speculate about its implementation
 * interface HtmlParser {
 *     public List<String> getUrls(String url) {}
 * }
 */
class Solution {
    Set<String> visited = new Hashset<String>();
    public List<String> crawl(String startUrl, HtmlParser htmlParser) {
        String hostname = getHostName(startUrl);
        helper(startUrl, hostname, htmlParser);
        return new ArrayList<String>(visited);
    }
 
    private void helper(String startUrl, String hostname, HtmlParser htmlParser) {
        List<String> urls = htmlParser.getUrls(startUrl);
        for(String url : urls) {
            if(!visited.contains(url) && url.contains(hostname)) {
                visited.add(url);
                helper(url, hostname, htmlParser);
            }
        }
    }   
 
    private String getHostName(String startUrl) {
        String[] ss = startUrl.split("/");
        return ss[2];
    }
}

// Solution 3: Queue + BFS
// Refer to
// https://leetcode.jp/leetcode-1236-web-crawler-%E8%A7%A3%E9%A2%98%E6%80%9D%E8%B7%AF%E5%88%86%E6%9E%90/
/**
bfs也不难，同样是模板级的代码
Set<String> res = new HashSet<>(); // 返回结果
public List<String> crawl(String startUrl, HtmlParser htmlParser) {
    String host = getUrl(startUrl); // 取得域名
    Queue<String> q = new LinkedList<>(); // bfs用的queue
    res.add(startUrl); // 添加startUrl至返回结果
    q.offer(startUrl); // 添加startUrl至bfs的Queue
    while(q.size()>0){
        String url = q.poll(); // 取得一个url
        // 查看当前url包含的所有链接
        List<String> urls = htmlParser.getUrls(url);
        for(String u : urls){ // 循环每一个链接
            // 如果该链接已经爬过或者不属于当前域名，跳过
            if(res.contains(u)||!getUrl(u).equals(host)){
                continue;
            }
            res.add(u); // 添加该链接至返回结果
            q.offer(u); // 添加该链接至bfs的Queue
        }
    }
    return new ArrayList<>(res);
}
*/

// https://wentao-shao.gitbook.io/leetcode/data-structure/1236.web-crawler
/**
 * // This is the HtmlParser's API interface.
 * // You should not implement it, or speculate about its implementation
 * interface HtmlParser {
 *     public List<String> getUrls(String url) {}
 * }
 */
class Solution {
    public List<String> crawl(String startUrl, HtmlParser htmlParser) {
        Set<String> visited = new HashSet<String>();
        Queue<String> q = new LinkedList<String>();
        String hostname = getHostName(startUrl);
        q.offer(hostname);
        visited.add(hostname);
        while(!q.isEmpty()) {
            String currUrl = q.poll();
            for(String url : htmlParser.getUrls(currUrl)) {
                if(!visited.contains(url) && url.contains(hostname)) {
                    q.offer(url);
                    visited.add(url);
                }
            }
        }
        return new ArrayList<String>(visited);
    }
 
    private String getHostName(String startUrl) {
        String[] ss = startUrl.split("/");
        return ss[2];
    }
}
