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

// Points:
// Refer to
// https://zhang0peter.com/2020/02/12/LeetCode-1242-Web-Crawler-Multithreaded/
/**
这道题目很新颖，意思是写一个模拟爬虫程序，从一个开始页面，爬取属于这个域名的所有网页，LeetCode提供了爬虫的接口。
题目要求必须使用多线程爬虫，不然会超时。
1.用set来存储已经爬取过的网页,这个set需要支持多线程并发修改，我使用ConcurrentHashMap
2.存储结果的List也只要支持多线程并发，我使用Collections.synchronizedList
这个解法的缺点是可能会在运行过程中产生大量的无用线程，可以使用线程池进行并发操作，减少创建线程的开销。
使用线程池的最大难点在于如何确定所有任务都执行完成，可以关闭线程池。
我尝试使用 Future, CountDownLatch，但效果都不好。最关键的是运行速度没有直接新建线程快！！

如果建太多线程不是会导致所有线程都很慢吗，是不是可以试一试Executors.newCachedThreadPool()提供的线程池，
这样每当一个线程结束，建造一个新线程的时间和资源就免了
*/

// Refer to
// https://yifei.me/note/818/
/**
1242 题要求使用多线程来实现。在现实生活中，爬虫作为一个 IO 密集型的任务，使用多线程是一项必须的优化。

在上述的单线程版本中，我们使用了 visited 这个数组来存放已经访问过的节点，如果我们采用多线程的话，并且在每个线程中并发判断某个 URL 
是否被访问过，那么势必需要给这个变量加一个锁。而我们知道，在多线程程序中，加锁往往造成性能损失最大，最容易引起潜在的 bug。
那么有没有一种办法可以不用显式加锁呢？

其实也很简单，我们只要把需要把并发访问的部分放到一个线程里就好了。这个想法是最近阅读 The Go Programming Language 得到的启发。全部代码如下：

import threading
import queue
from urllib.parse import urlsplit

class Solution:
    def crawl(self, startUrl: str, htmlParser: "HtmlParser") -> List[str]:
        domain = urlsplit(startUrl).netloc
        requestQueue = queue.Queue()
        resultQueue = queue.Queue()
        requestQueue.put(startUrl)
        for _ in range(5):
            t = threading.Thread(target=self._crawl, 
                args=(domain, htmlParser, requestQueue, resultQueue))
            t.daemon = True
            t.start()
        running = 1
        visited = set([startUrl])
        while running > 0:
            urls = resultQueue.get()
            for url in urls:
                if url in visited:
                    continue
                visited.add(url)
                requestQueue.put(url)
                running += 1
            running -= 1
        return list(visited)

    def _crawl(self, domain, htmlParser, requestQueue, resultQueue):
        while True:
            url = requestQueue.get()
            urls = htmlParser.getUrls(url)
            newUrls = []
            for url in urls:
                u = urlsplit(url)
                if u.netloc == domain:
                    newUrls.append(url)
            resultQueue.put(newUrls)
在上面的代码中，我们开启了 5 个线程并发请求，每个 worker 线程都做同样的事情：

从 requestQueue 中读取一个待访问的 url；
执行一个很耗时的网络请求：htmlParser.getUrls；
然后把获取到的新的 url 处理后放到 resultQueue 中。
而在主线程中：

从 resultQueue 中读取一个访问的结果
判断每个 URL 是否已经被访问过
并分发到 requestQueue 中。
我们可以看到在上述的过程中并没有显式使用锁（当然 queue 本身是带锁的）。原因就在于，我们把对于需要并发访问的结构限制在了一个线程中。

当然如果可以用锁的话，也可以在每个 worker 线程中计数。而这种情况下，为了使用 running > 0 这个条件，
一定要首先在发现新的 url 的时候 running++，在处理完整个页面之后再 running–。
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
class Solution {
    public List < String > crawl(String startUrl, HtmlParser htmlParser) {
        String hostname = getHostName(startUrl);
        // Initial a new crawler thread to crawl all url start with same hostname of 'startUrl'
        Crawler crawler = new Crawler(startUrl, hostname, htmlParser);
        // Initial result of thread
        crawler.result = new ArrayList < String > ();
        // Start thread, same effect as initial for DFS
        crawler.start();
        // Wait all threads done
        crawler.joinThread(crawler);
        // Return result
        return crawler.result;
    }

    private String getHostName(String url) {
        String[] splits = url.split("/");
        return splits[2];
    }
}

// Crawler thread the effect equal to original DFS
class Crawler extends Thread {
    String startUrl;
    String hostname;
    HtmlParser htmlParser;

    // Store result
    public static volatile List < String > result = new ArrayList < String > ();

    // Initial thread
    public Crawler(String startUrl, String hostname, HtmlParser htmlParser) {
        this.startUrl = startUrl;
        this.hostname = hostname;
        this.htmlParser = htmlParser;
    }

    @Override
    public void run() {
        if (!result.contains(startUrl) && startUrl.contains(hostname)) {
            result.add(startUrl);
            // Each url will initial a new thread to continue similar as DFS
            List < Thread > threads = new ArrayList < Thread > ();
            for (String url: htmlParser.getUrls(startUrl)) {
                Crawler crawler = new Crawler(url, hostname, htmlParser);
                crawler.start();
                threads.add(crawler);
            }
            // Wait all child threads execution done, then terminate main thread
            for (Thread t: threads) {
                joinThread(t);
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
class Solution {
    public List < String > crawl(String startUrl, HtmlParser htmlParser) {
        String hostname = getHostName(startUrl);
        Crawler crawler = new Crawler(startUrl, hostname, htmlParser);
        // reset result as static property belongs to class, it will go through all of the test cases
        crawler.map = new ConcurrentHashMap();
        crawler.result = crawler.map.newKeySet();
        Thread thread = new Thread(crawler);
        thread.start();
        // Wait for thread to complete
        crawler.joinThread(thread);
        return new ArrayList < String > (crawler.result);
    }

    private String getHostName(String url) {
        String[] splits = url.split("/");
        return splits[2];
    }
}

class Crawler implements Runnable {
    String startUrl;
    String hostname;
    HtmlParser htmlParser;
    public static ConcurrentHashMap < String, String > map = new ConcurrentHashMap < String, String > ();
    public static Set < String > result = map.newKeySet();

    public Crawler(String startUrl, String hostname, HtmlParser htmlParser) {
        this.startUrl = startUrl;
        this.hostname = hostname;
        this.htmlParser = htmlParser;
    }

    @Override
    public void run() {
        if (!result.contains(startUrl) && startUrl.contains(hostname)) {
            result.add(startUrl);
            List < Thread > threads = new ArrayList < Thread > ();
            for (String url: htmlParser.getUrls(startUrl)) {
                Crawler crawler = new Crawler(url, hostname, htmlParser);
                Thread thread = new Thread(crawler);
                thread.start();
                threads.add(thread);
            }
            // Wait for all threads to complete
            for (Thread t: threads) {
                joinThread(t);
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


// Solution 3: Using Executor
// Refer to
// https://blog.csdn.net/u013325815/article/details/104355226
// 就是用queue，bfs，不过要用多线程写，我抄的答案
/** 
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;
import java.net.*;
 
public class Solution {    
    ExecutorService pool = Executors.newFixedThreadPool(4);
    AtomicLong numTasks = new AtomicLong(0);
    Lock lock = new ReentrantLock();
    List<String> result = new ArrayList<String>();
    Set<String> visited = new HashSet<>();
    
    private class crawlTask implements Runnable {
        String url;
        public crawlTask(String url) {
            this.url = url;
        }
        
        @Override
        public void run() {
            try {
                for(String neighbor: HtmlHelper.parseUrls(url)) {
                    URL neighborURL = new URL(neighbor);
                    if(!neighborURL.getHost().endsWith("wikipedia.org")) {
                        continue;
                    }
                    lock.lock();
                    if(!visited.contains(neighbor)) {
                        visited.add(neighbor);
                        result.add(neighbor);
                        pool.execute(new crawlTask(neighbor));
                        numTasks.incrementAndGet();
                    }
                    lock.unlock();
                }
                
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                numTasks.decrementAndGet();
            }
        }
    }
     
    public List<String> crawler(String url) {
        visited.add(url);
        result.add(url);
        pool.execute(new crawlTask(url));
        numTasks.incrementAndGet();
        try{
            while(numTasks.get() != 0){
                Thread.sleep(10);
            } 
        } catch (Exception e) {
            e.printStackTrace();
        }
        pool.shutdown();
        return result;
    }
}
*/

// For the follow up
// Refer to
// https://leetcode.com/discuss/interview-question/124657/Design-a-distributed-web-crawler-that-will-crawl-all-the-pages-of-wikipedia/269291
/**
Q: Facebook | System Design | A web crawler that will crawl Wikipedia
We need to deploy the same software on each node. We have 10,000 nodes, the software can know about all the nodes. 
We have to minimize communication and make sure each node does equal amount of work.

Encountered this question in facebook.

My solution:
Intial Proposal:
Start out with the root page, and based on a hash function decide if that page is for you (the node) or not.

What if one node fails or does not work?
When encountering a page, ping to see whether the node handling that page is online, if not use a secondary hash function 
to determine alternate handler. If you are the alternate handler, handle the page, if not ping to see if the alternate 
handler is online. Do this for al hash functions you decide to have, more hash functions means less impact by failure of one node.

How do you know when the crawler is done?
Pass around a timestamp of the last crawled page. If the timestamp gets back to you without changing, then you are done.

A1: The tough part is to equally distribute workload without centralized components. I think Consistent Hashing is a good example.
Basically, group all nodes into a ring. Randomly distribute them onto that ring. Each node is only responsible for its left hand arc. 
For any url, hash it onto the ring as well, so that only one node will process it. This way we don't need a centralized hash table 
to avoid repeating. Also, the distribution is roughly balanced over each node. When a node visits a page, for each wiki link, hash 
it onto the ring so that another node will process it. 

A2: Did the interviewer specifically mention that you can't use a messaging queue like SQS? Not sure why exotic solutions like consistent 
hashing are being mentioned here again and again.

In a System design question, understand the scope of the problem and stay true to the original problem.
The scope was to design a web crawler using available distributed system constructs and NOT to design a distributed database or a distributed cache.

A Web crawler system design has 2 main components:

The Crawler (Write path)
The Indexer (Read path)
Make sure you ask about expected number of URLs to crawl (Write QPS) and expected number of Query API calls (Read QPS).
Make sure you ask about the SLA for the Query API. If its in tens of milliseconds at say 90 percentile, you'll probably need to cache the query results.

Begin with these high level components, then break them down into smaller sub-components, then connect these to form a coherent whole.

Crawler

For crawling you need a "seed of URLs" to begin the crawling. You'd want to put the URLs in a queue.
The queue workers would work on one URL at a time. Each queue worker, given a URL has to:
**Extract text from the URL and send it to a Document Indexing Service .
**Insert any links found in the page back into the queue. Before inserting, the links are looked up (and stored) in a Global NoSql store, 
to ensure they weren't already crawled. We use a NoSql store (and not a SQL Database) because we're doing lookup operations only and don't 
require expensive joins.
Eventually the queue will become empty. At this point, the "seeder" will reseed the queue with seed URLs and the whole process restarts.

Scaling up the crawler (only if the websites to crawl are in billions):
Your queue could be a distributed message queue (such as SQS or Azure ServiceBus).
Your NoSql store could be DynamoDB.
The interviewer would most likely know that both message queues and NoSql stores maintain replicas (typically master-slave replication) 
for fault tolerance and re-partition themselves via an algorithm like consistent hashing for scalability.
All distributed queues have a Visibility Timeout i.e. when an element is dequeued, it still remains in the queue but is made invisible to 
other dequeue requests till Visibility Timeout seconds have elapsed. A worker that is handling the dequeued element must explicitly delete 
it from the queue before Visibility Timeout seconds.

Challenges for Crawler:
How would you handle throttling from your NoSql store (say because you have too many crawlers attempting to lookup and write URLS to it)? 
If you try an exponential retry algorithm in your worker, your message queue may release the URL being already crawled to another worker.
How would you handle dead links? You'd probably want to maintain a blacklist of links that returned 404 so that your workers don't put these 
in crawler queue the next time around.
How do you handle crawling of temporarily offline websites. Assume that your worker connected to the website but it took 40 seconds to respond 
with 503 but by that time, your message queue already released the same URL to another worker who'd attempt to reconnect to the same website 
and suffer from the same fate.
How would you handle websites that failed to get crawled? You'd probably want to store them in a Dead Letter Queue to be inspected later.
Would you respect Robots.txt files while crawling? Maybe you could store the domain name + /* of such sites in the Blacklist NoSql Store.
How would you throttle requests from your crawlers running in parallel to different pages on a single website (like Wikipedia). Maybe a message 
queue is not a right fit in this design? You could probably use a Streaming queue (like Kafka/Kinesis Streams/ Azure EventHub) where the domain 
name of the URL is the partition key. This means that all sub-URLs within a domain will be handled by one worker only. But this leads to obvious 
load balancing issues. Alternatively, you could invest in a Rate Limiter that ensures that one worker does not open more than n connections to 
a single website. But what is a good value of n? Wikipedia can probably handle thousands of concurrent connections but a small company's website 
could cave in. So the value on n depends on the domain being crawled and will need tweaking via trial and error. Which means you'll need another 
NoSql store that stores domain names and n which the RateLimiter will need to cache when doing the rate limiting. Next question: what should the 
worker do if the Rate Limiter disallowed it from accessing the URL? Should it keep retrying? If yes, what if the message queue releases the same 
URL to another worker? It makes sense for the worker to drop it and go for the next URL in the queue. This will cause the message queue to release 
this current URL to another worker after Visibility Timeout seconds, and that worker might have a higher chance of succeeding. But what if the next 
URL is also of the same domain?
All in all, you should also discuss what logs/metrics you'd emit and how these are analyzed to make the crawling better. Some metrics to emit 
would be how many times a worker was rate limited, latency of every operation (such as reading entire contents a URL, time taken for ranking 
the text, indexing it etc.). For websites that caved in or were unresponsive etc, apart from the metrics, you'd also write them in special log 
files which are then machine learned to produce the Blacklist NoSql store or to recompute the number of connections for a domain to be used by 
the Rate Limiter.

Indexer

You'll need a Document Indexing Service that does 2 things on its write path:
** Insert the URL and its text into another NoSql store. This will be used for showing cached copies in case the original URLis unavailable 
or is dead. But what if the text is huge? In that case, it makes sense to store the text instead in an Object/Blob Store like S3/Azure Blob 
Storage under a key which is the hash of the URL.
** Maintain a Reverse Index that maps keywords/phrases in the text back to the original URL. You can use a Reverse Index database like Elastic 
Search.
The Document Indexing Service also exposes a query API that takes as input, a phrase + number of results (i.e. page size) to return. It would 
then break the phrase into keywords, remove "Stop words" (words like the, a, an etc), correct spelling mistakes in the keywords, add synonyms 
of certain keywords and then call into Elastic Search to return the URLs that contain these keywords. Elastic Search has plugins which already 
do spelling corrections, stop word removal etc. The URLs could then be fed into a Ranking service that ranks the URL before returning. 
If your Read QPS is higher than Write QPS, a better approach would be to do the ranking via an hourly offline process and store the rank in 
Elastic Search. That would speed up the querying path as you'd skip ranking and instead ask Elastic Search to return URLs sorted by rank. 
This also makes it easy to paginate as the first page will contain the top n results, the next page will contain the next n results etc. 
The hourly offline ranker would need what are called as "Clickstream logs" to figure out which links are being clicked more often so as 
to rank them higher.

The query API must also return a pagination token to allow the caller to continue retrieving more pages

Challenges for Indexer:
Can we speed up the read path by maintaining a cache for most commonly queried phrases? The 80/20 rule states that 80% of users query the same 
20% phrases. If so, what caching strategy to use? LRU/TTL? Should we go with a write through cache so that the crawler directly updates the 
cache? Or a cache-aside strategy where the cache is bypassed by the crawlers when they write to the Document Service? In that case, what TTL 
value would be appropriate for the cache?
What kind of distributed cache would we use? Redis/memcached? Only if asked, you can mention about consistent hashing here.
That's about it...No consistent hashing/zookeeper etc required
*/
