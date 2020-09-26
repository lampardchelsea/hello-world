/**
 Refer to
 https://zhang0peter.com/2020/02/12/LeetCode-1242-Web-Crawler-Multithreaded/
 Given a url startUrl, implement a web crawler to crawl all links that are under the same hostname as startUrl.
 
 Your crawler should:
 1. Start from the page: startUrl
 2. Call HtmlParser.getUrls(url) to get all urls from a webpage of given url.
 3. Do not crawl the same link twice.
 4. Only crawl the links that are under the same hostname as startUrl.
 
 As shown in the example url above, the hostname is example.org. For simplicity sake, you may assume all urls use 
 http protocol without any port specified. For example, the urls http://leetcode.com/problems and http://leetcode.com/contest 
 are under the same hostname, while urls http://example.org/test and http://example.com/abc are not under the same hostname.

 The HtmlParser interface is defined as such:
 interface HtmlParser {
   // Return a list of all urls from a webpage of given url.
   // This is a blocking call, that means it will do HTTP request and return when this request is finished.
   public List<String> getUrls(String url);
 }
 Note that getUrls(String url) simulates performing a HTTP request. You can treat it as a blocking function call which waits 
 for a HTTP request to finish. It is guaranteed that getUrls(String url) will return the urls within 15ms. Single-threaded 
 solutions will exceed the time limit so, can your multi-threaded web crawler do better?

 Below are two examples explaining the functionality of the problem, for custom testing purposes you’ll have three variables urls, 
 edges and startUrl. Notice that you will only have access to startUrl in your code, while urls and edges are not directly 
 accessible to you in code.

 Follow up:
 Assume we have 10,000 nodes and 1 billion URLs to crawl. We will deploy the same software onto each node. The software can know 
 about all the nodes. We have to minimize communication between machines and make sure each node does equal amount of work. 
 How would your web crawler design change?
 What if one node fails or does not work?
 How do you know when the crawler is done?
 
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

// Solution 1: Extends Thread
// Refer to
// https://leetcode.jp/leetcode-1242-web-crawler-multithreaded-%e8%a7%a3%e9%a2%98%e6%80%9d%e8%b7%af%e5%88%86%e6%9e%90/
/**
 这本是一道dfs题目，但要使用多线程来解决。可以理解为dfs的多线程升级版。标准的dfs解法可以参照 LEETCODE 1236. Web Crawler 
 解题思路分析 这篇文章。普通做dfs深度优先搜索时，我们需要通过一个起点，不停的dfs递归调用搜索到所有节点，本题也不例外，
 dfs是核心思路，不同的是，我们需要将dfs方法内的代码放到线程中，在做dfs递归操作时，不是递归调用本身，而是需要再开启一个新的线程而已。
 需要注意的一点是，多线程不同于单线程，在我们开启一个线程后，主线程并不会等待子线程执行结束再返回结果，因此我们应该想到，
 在返回结果之前，必须等待所有子线程执行结束，否则我们无法得到所有结果。等待子线程结束的方法很多，在java中最为简单的便是
 使用Thread.join()方法，这样主线程会被挂起，当join的子线程执行完毕之后，主线程才会继续向下执行。
 import java.net.URI;
 class Solution {
   public List<String> crawl(String startUrl, HtmlParser htmlParser) {
        // 取得startUrl的域名
        String host = URI.create(startUrl).getHost();
        // 新建一个线程，爬取startUrl中的所有链接
        Crawler crawler = new Crawler(startUrl, host, htmlParser);
        // 初始化线程的返回结果
        crawler.res = new ArrayList<>();
        // 开启线程（相当于从起点开始dfs）
        crawler.start();
        // 等待线程执行结束
        Crawler.joinThread(crawler);
        // 返回线程的执行结果
        return crawler.res;
    }
}
// 爬虫线程（相当于原始的dfs方法）
class Crawler extends Thread {
    String startUrl; // 当前url
    String hostname; // 域名
    HtmlParser htmlParser; // 爬虫接口
    // 返回结果
    public static volatile List<String> res = new ArrayList<>();
    // 初始化线程
    public Crawler(String startUrl, String hostname, HtmlParser htmlParser){
        this.startUrl = startUrl;
        this.hostname = hostname;
        this.htmlParser = htmlParser;
    }
    @Override 
    public void run(){
        // 获得当前url的域名
        String host=URI.create(startUrl).getHost();
        // 如果当前域名不属于目标网站，或者当前域名已经爬过，略过
        if(!host.equals(hostname) || res.contains(startUrl)){
            return;
        }
        // 将当前url加入结果集
        res.add(startUrl);
        // 记录当前url页面包含的链接
        // 每个链接启动一个新的线程继续dfs
        List<Thread> threads = new ArrayList<>();
        for(String s: htmlParser.getUrls(startUrl)){
            Crawler crawler = new Crawler(s, hostname, htmlParser);
            crawler.start();
            threads.add(crawler);
        }
        // 等待每个子线程执行结束后，再结束当前线程
        for(Thread t: threads){
            joinThread(t);
        }
    }
    
    public static void joinThread(Thread thread){
        try{
            thread.join();
        } catch(InterruptedException e){
        }
    }
}
本题解法执行时间为2ms。
Runtime: 2 ms, faster than 97.83% of Java online submissions for Web Crawler Multithreaded.
Memory Usage: 89.5 MB, less than 100.00% of Java online submissions for Web Crawler Multithreaded.
*/

// Solution 2: Implement Runnable
// Refer to
// http://guanzhou.pub/2020/07/01/LeetCode1/
/**
 Style 1:
 class Solution {
    public List<String> crawl(String startUrl, HtmlParser htmlParser) {

        // find hostname
        int index = startUrl.indexOf('/', 7);
        String hostname = (index != -1) ? startUrl.substring(0, index) : startUrl;

        // multi-thread
        Crawler crawler = new Crawler(startUrl, hostname, htmlParser);
        crawler.map = new ConcurrentHashMap<>(); // reset result as static property belongs to class, it will go through all of the test cases
        crawler.result = crawler.map.newKeySet();
        Thread thread = new Thread(crawler);
        thread.start();

        crawler.joinThread(thread); // wait for thread to complete
        return new ArrayList<>(crawler.result);
    }
}

class Crawler implements Runnable {
    String startUrl;
    String hostname;
    HtmlParser htmlParser;
    public static ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
    public static Set<String> result = map.newKeySet();

    public Crawler(String startUrl, String hostname, HtmlParser htmlParser) {
        this.startUrl = startUrl;
        this.hostname = hostname;
        this.htmlParser = htmlParser;
    }

    @Override
    public void run() {
        if (this.startUrl.startsWith(hostname) && !this.result.contains(this.startUrl)) {
            this.result.add(this.startUrl);
            List<Thread> threads = new ArrayList<>();
            for (String s : htmlParser.getUrls(startUrl)) {
			    if(result.contains(s)) continue;
                Crawler crawler = new Crawler(s, hostname, htmlParser);
                Thread thread = new Thread(crawler);
                thread.start();
                threads.add(thread);
            }
            for (Thread t : threads) {
                joinThread(t); // wait for all threads to complete
            }
        }
    }

    public static void joinThread(Thread thread) {
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

Sytle 2:
class Solution {
    public List<String> crawl(String startUrl, HtmlParser htmlParser) {
        String hostname = getHostname(startUrl);

        Set<String> visited = ConcurrentHashMap.newKeySet();
        visited.add(startUrl);

        return crawl(startUrl, htmlParser, hostname, visited)
            .collect(Collectors.toList());
    }

    private Stream<String> crawl(String startUrl, HtmlParser htmlParser, String hostname, Set<String> visited) {
        Stream<String> stream = htmlParser.getUrls(startUrl)
            .parallelStream()
            .filter(url -> isSameHostname(url, hostname))
            .filter(url -> visited.add(url))
            .flatMap(url -> crawl(url, htmlParser, hostname, visited));

        return Stream.concat(Stream.of(startUrl), stream);
    }

    private String getHostname(String url) {
        int idx = url.indexOf('/', 7);
        return (idx != -1) ? url.substring(0, idx) : url;
    }

    private boolean isSameHostname(String url, String hostname) {
        if (!url.startsWith(hostname)) {
            return false;
        }
        return url.length() == hostname.length() || url.charAt(hostname.length()) == '/';
    }
}
*/
