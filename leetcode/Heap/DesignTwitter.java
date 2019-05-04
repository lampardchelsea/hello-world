import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * Refer to
 * https://leetcode.com/problems/design-twitter/
 * Design a simplified version of Twitter where users can post tweets, follow/unfollow another user and is able 
 * to see the 10 most recent tweets in the user's news feed. Your design should support the following methods:
 * 
 * postTweet(userId, tweetId): Compose a new tweet.
 * getNewsFeed(userId): Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed 
 *                      must be posted by users who the user followed or by the user herself. Tweets must be ordered 
 *                      from most recent to least recent.
 * follow(followerId, followeeId): Follower follows a followee.
 * unfollow(followerId, followeeId): Follower unfollows a followee.
 * 
 * Example:
	Twitter twitter = new Twitter();
	
	// User 1 posts a new tweet (id = 5).
	twitter.postTweet(1, 5);
	
	// User 1's news feed should return a list with 1 tweet id -> [5].
	twitter.getNewsFeed(1);
	
	// User 1 follows user 2.
	twitter.follow(1, 2);
	
	// User 2 posts a new tweet (id = 6).
	twitter.postTweet(2, 6);
	
	// User 1's news feed should return a list with 2 tweet ids -> [6, 5].
	// Tweet id 6 should precede tweet id 5 because it is posted after tweet id 5.
	twitter.getNewsFeed(1);
	
	// User 1 unfollows user 2.
	twitter.unfollow(1, 2);
	
	// User 1's news feed should return a list with 1 tweet id -> [5],
	// since user 1 is no longer following user 2.
	twitter.getNewsFeed(1);
 *
 * Refer to
 * http://www.cnblogs.com/grandyang/p/5577038.html
 * 
 * Solution 1:
 * Refer to
 * https://leetcode.com/problems/design-twitter/discuss/82825/Java-OO-Design-with-most-efficient-function-getNewsFeed
 * 
 * Solution 2:
 * 这道题让我们设计个简单的推特，具有发布消息，获得新鲜事，添加关注和取消关注等功能。我们需要用两个哈希表来做，第一个是建立
 * 用户和其所有好友之间的映射，另一个是建立用户和其所有消息之间的映射。由于获得新鲜事是需要按时间顺序排列的，那么我们可以用
 * 一个整型变量cnt来模拟时间点，每发一个消息，cnt自增1，那么我们就知道cnt大的是最近发的。那么我们在建立用户和其所有消息之
 * 间的映射时，还需要建立每个消息和其时间点cnt之间的映射。这道题的主要难点在于实现getNewsFeed()函数，这个函数获取自己和
 * 好友的最近10条消息，我们的做法是用户也添加到自己的好友列表中，然后遍历该用户的所有好友，遍历每个好友的所有消息，维护一个
 * 大小为10的哈希表，如果新遍历到的消息比哈希表中最早的消息要晚，那么将这个消息加入，然后删除掉最早的那个消息，这样我们就可
 * 以找出最近10条消息了
 * 
 * For me, not use fixed 10 size hash table to store, but use max heap to sort everything.
 */

// Solution 1:
class Twitter {
    private static int timeStamp = 0;
    Map<Integer, User> userMap;
    
    // OO design so User can follow, unfollow and post itself
    public class User {
        public int id;
        public Set<Integer> followed;
        public Tweet tweet_head;
        public User(int id) {
            this.id = id;
            this.followed = new HashSet<Integer>();
            follow(id); // first follow itself
            this.tweet_head = null;
        }
        
        public void follow(int id) {
            followed.add(id);
        }
        
        public void unfollow(int id) {
            followed.remove(id);
        }
        
        // everytime user post a new tweet, add it to the head of tweet list.
        public void post(int id) {
            Tweet t = new Tweet(id);
            t.next = tweet_head;
            tweet_head = t;
        }
    }
        
    private class Tweet {
        public int id;
        public int time;
        public Tweet next;
        public Tweet(int id) {
            this.id = id;
            this.time = timeStamp++;
            this.next = null;
        }
    }
    
    /** Initialize your data structure here. */
    public Twitter() {
        userMap = new HashMap<Integer, User>();
    }
    
    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {
        if(!userMap.containsKey(userId)) {
            User u = new User(userId);
            userMap.put(userId, u);
        }
        userMap.get(userId).post(tweetId);
    } 
    
    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    // Best part of this.
	// First get all tweets lists from one user including itself and all people it followed.
	// Second add all heads into a max heap. Every time we poll a tweet with 
	// largest time stamp from the heap, then we add its next tweet into the heap.
	// So after adding all heads we only need to add 9 tweets at most into this 
	// heap before we get the 10 most recent tweet.
    public List<Integer> getNewsFeed(int userId) {
        List<Integer> result = new LinkedList<Integer>();
        if(!userMap.containsKey(userId)) {
            return result;
        }
        Set<Integer> users = userMap.get(userId).followed;
        PriorityQueue<Tweet> pq = new PriorityQueue<Tweet>(users.size(), (a, b) -> (b.time - a.time));
        for(int user : users) {
            Tweet t = userMap.get(user).tweet_head;
            // very imporant! If we add null to the head we are screwed.
            if(t != null) {
                pq.offer(t);
            }
        }
        int count = 0;
        while(!pq.isEmpty() && count < 10) {
            Tweet t = pq.poll();
            result.add(t.id);
            count++;
            if(t.next != null) {
                pq.offer(t.next);
            }
        }
        return result;
    }
    
    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    public void follow(int followerId, int followeeId) {
        if(!userMap.containsKey(followerId)) {
            User u = new User(followerId);
            userMap.put(followerId, u);
        }
        if(!userMap.containsKey(followeeId)) {
            User u = new User(followeeId);
            userMap.put(followeeId, u);
        }
        userMap.get(followerId).follow(followeeId);
    }
    
    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId) {
        if(!userMap.containsKey(followerId) || followerId == followeeId) {
            return;
        }
        userMap.get(followerId).unfollow(followeeId);
    }
}

/**
 * Your Twitter object will be instantiated and called as such:
 * Twitter obj = new Twitter();
 * obj.postTweet(userId,tweetId);
 * List<Integer> param_2 = obj.getNewsFeed(userId);
 * obj.follow(followerId,followeeId);
 * obj.unfollow(followerId,followeeId);
 */


// Solution 2:
public class Twitter {
	// Map store specific user and its posted tweet
    Map<Integer, List<Tweet>> userPostTweetMap;
    // Map store specific user as follower and its all followee
    Map<Integer, List<Integer>> userRelationMap;
    // Use counter to simulate real timestamp
    int timestamp;
    
    // The Tweet class associate timestamp and tweetid together
    // used for MaxPQ heap sort
    private class Tweet {
        int timestamp;
        int tweetId;
        public Tweet(int timestamp, int tweetId) {
            this.timestamp = timestamp;
            this.tweetId = tweetId;
        }
    }
    
    /** Initialize your data structure here. */
    public Twitter() {
        userPostTweetMap = new HashMap<Integer, List<Tweet>>();
        userRelationMap = new HashMap<Integer, List<Integer>>();
        timestamp = 0;
    }
    
    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {
    	// Every post will increase counter by 1
        timestamp++;
        Tweet tweet = new Tweet(timestamp, tweetId);
        if(!userPostTweetMap.containsKey(userId)) {
            List<Tweet> tweetIdList = new ArrayList<Tweet>();
            tweetIdList.add(tweet);
            userPostTweetMap.put(userId, tweetIdList);
        } else {
            userPostTweetMap.get(userId).add(tweet);
        }
    }
    
    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users 
     * who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    public List<Integer> getNewsFeed(int userId) {
        List<Integer> result = new ArrayList<Integer>();
        List<Tweet> temp = new ArrayList<Tweet>();
        // Add user's self post(if already have post) before adding followee's post
        if(userPostTweetMap.get(userId) != null && userPostTweetMap.get(userId).size() > 0) {
        	temp.addAll(userPostTweetMap.get(userId));
        }
        // Add followee's post for specific follower(given by userId)
        if(userRelationMap.get(userId) != null && userRelationMap.get(userId).size() > 0) {
            for(Integer followeeId : userRelationMap.get(userId)) {
            	// If follower follow himself/herself, not add again
            	if(followeeId != userId && userPostTweetMap.get(followeeId) != null && userPostTweetMap.get(followeeId).size() > 0) {
            		temp.addAll(userPostTweetMap.get(followeeId));
            	}
            }
        }
        // Put all related user's(user and its followee) tweet onto max priority queue
        MaxPQ maxPQ = new MaxPQ(temp.size());
        for(int i = 0; i < temp.size(); i++) {
            maxPQ.insert(temp.get(i));
        }
        // If posted tweet less than 10, return all of them, if over 10, return top 10
        if(temp.size() < 10) {
            for(int i = 0; i < temp.size(); i++) {
                result.add(maxPQ.delMax().tweetId);
            }
        } else {
            for(int i = 0; i < 10; i++) {
                result.add(maxPQ.delMax().tweetId);
            }
        }
        return result;
    }
    
    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    public void follow(int followerId, int followeeId) {
        if(!userRelationMap.containsKey(followerId)) {
            List<Integer> followeeIds = new ArrayList<Integer>();
            followeeIds.add(followeeId);
            userRelationMap.put(followerId, followeeIds);
        } else {
        	// If follower already follow the followee should not follow again(not add into list)
        	if(!userRelationMap.get(followerId).contains(followeeId)) {
                userRelationMap.get(followerId).add(followeeId);
        	}
        }
    }
    
    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId) {
         if(userRelationMap.containsKey(followerId) && userRelationMap.get(followerId).contains(followeeId)) {
        	 // Directly remove(followeeId) will point to another remove API of ArrayList
             userRelationMap.get(followerId).remove(Integer.valueOf(followeeId));
         }
    }
    
    // Create Max Pirority Queue based on heap
    private class MaxPQ {
        Tweet[] pq;
        int n;
        public MaxPQ(int initialCapacity) {
            pq = new Tweet[initialCapacity + 1];
            n = 0;
        }
        
        public void insert(Tweet tweet) {
            pq[++n] = tweet;
            swim(n);
        }
        
        public Tweet delMax() {
            Tweet max = pq[1];
            exch(1, n--);
            sink(1);
            return max;
        }
        
        public void sink(int k) {
            while(k <= n/2) {
                int j = 2 * k;
                if(j < n && less(j, j + 1)) {
                    j++;
                }
                if(!less(k, j)) {
                    break;
                }
                exch(k, j);
                k = j;
            }
        }
        
        public void swim(int k) {
            while(k > 1 && less(k/2, k)) {
                exch(k/2, k);
                k = k/2;
            } 
        }
        
        public boolean less(int v, int w) {
            return pq[v].timestamp - pq[w].timestamp < 0;
        }
        
        public void exch(int v, int w) {
            Tweet swap = pq[v];
            pq[v] = pq[w];
            pq[w] = swap;
        }
    }
    
    public static void main(String[] args) {
    	// Test 1: Input as below, the change happen on unfollow() method which require 
    	//         remove(Integer.valueOf(followeeId)) as directly remove(followeeId) will
    	//         point to another remove API of ArrayList
    	//         userRelationMap.get(followerId).remove(Integer.valueOf(followeeId));
    	// Action steps --> ["Twitter","postTweet","getNewsFeed","follow","postTweet","getNewsFeed","unfollow","getNewsFeed"]
 	    // Step values  --> [[],[1,5],[1],[1,2],[2,6],[1],[1,2],[1]]
//    	Twitter twitter = new Twitter();
//    	twitter.postTweet(1, 5);
//    	List<Integer> list1 = twitter.getNewsFeed(1);
//    	System.out.println(list1.toString());
//    	
//    	twitter.follow(1, 2);
//    	twitter.postTweet(2, 6);
//    	List<Integer> list2 = twitter.getNewsFeed(1);
//    	System.out.println(list2.toString());
//    	
//    	twitter.unfollow(1, 2);
//    	List<Integer> list3 = twitter.getNewsFeed(1);
//    	System.out.println(list3.toString());
    	
    	// Test 2: Input as below, the change happen on getNewsFeed() method which require
    	//         additional check on whether the specific user is already post tweet or
    	//         not to avoid NullPointException
    	// Action steps --> ["Twitter","postTweet","getNewsFeed","follow","getNewsFeed","unfollow","getNewsFeed"]
    	// Step values  --> [[],[1,1],[1],[2,1],[2],[2,1],[2]]
//    	Twitter twitter = new Twitter();
//    	twitter.postTweet(1, 1);
//    	List<Integer> list1 = twitter.getNewsFeed(1);
//    	System.out.println(list1.toString());
//    	
//    	twitter.follow(2, 1);
//    	List<Integer> list2 = twitter.getNewsFeed(2);
//    	System.out.println(list2.toString());
//    	
//    	twitter.unfollow(2, 1);
//    	List<Integer> list3 = twitter.getNewsFeed(2);
//    	System.out.println(list3.toString());
    	
    	// Test 3: Similar as Test 2, add Null check for all followeeid
    	// Action steps --> ["Twitter","follow","getNewsFeed"]
    	// Step values  --> [[],[1,5],[1]]
//    	Twitter twitter = new Twitter();    	
//    	twitter.follow(1, 5);
//    	List<Integer> list1 = twitter.getNewsFeed(1);
//    	System.out.println(list1.toString());
    	
    	// Test 4: Input as below, the change happen on getNewsFeed() method which require
    	//         additional check on whether userRelationMap really contains certain 
    	//         key value pair for specific follower and followee, e.g here we have userRelationMap
    	//         contains {followerid, followeeid} = {2, 8}, size not equal to 0, but not
    	//         contains key value pair for followerid = 1, need check on specific followerid
    	//         as "userRelationMap.get(userId) != null && userRelationMap.get(userId).size() > 0"
    	// Action steps --> ["Twitter","postTweet","follow","postTweet","postTweet","postTweet","unfollow","getNewsFeed","postTweet","postTweet",
    	//                    "postTweet","postTweet","follow","postTweet","postTweet","postTweet","postTweet","postTweet","postTweet","follow",
    	//                    "postTweet","postTweet","postTweet","postTweet","getNewsFeed","postTweet"]
    	// Step values  --> [[],[6,422],[2,8],[3,887],[1,614],[4,127],[1,4],[1],[4,300],[8,497],[6,710],[3,77],[8,8],[6,330],[3,291],[2,665],
    	//                   [8,818],[7,542],[5,634],[1,6],[9,565],[6,136],[7,342],[10,591],[3],[9,953]]
//    	Twitter twitter = new Twitter();
//    	twitter.postTweet(6, 422);
//    	twitter.follow(2, 8);
//    	twitter.postTweet(3, 887);
//    	twitter.postTweet(1, 614);
//    	twitter.postTweet(4, 127);
//    	twitter.unfollow(1, 4);
//    	List<Integer> list1 = twitter.getNewsFeed(1);
//    	System.out.println(list1.toString());
    	
    	// Test 5: Input as below, the change happen on getNewsFeed() method which require additional check 
    	//         on whether the follower follow himself/herself, if yes not add him/her post again with
    	//         "if(followeeId != userId && userPostTweetMap.get(followeeId) != null && userPostTweetMap.get(followeeId).size() > 0)"
    	// Action steps --> ["Twitter","postTweet","follow","getNewsFeed"]
    	// Step values  --> [[],[1,5],[1,1],[1]]
//    	Twitter twitter = new Twitter();
//    	twitter.postTweet(1, 5);
//    	twitter.follow(1, 1);
//    	List<Integer> list1 = twitter.getNewsFeed(1);
//    	System.out.println(list1.toString());
    	
    	// Test 6: Input as below, the change happen on follow() method which require additional check
    	//         on whether the follower follow followee again, if yes not add followee into list again
    	//         if(!userRelationMap.get(followerId).contains(followeeId))
    	// Action steps --> ["Twitter","postTweet","follow","follow","getNewsFeed"]
    	// Step values  --> [[],[2,5],[1,2],[1,2],[1]]
    	Twitter twitter = new Twitter();
    	twitter.postTweet(2, 5);
    	twitter.follow(1, 2);
    	twitter.follow(1, 2);
    	List<Integer> list1 = twitter.getNewsFeed(1);
    	System.out.println(list1.toString());
    }
}

/**
 * Your Twitter object will be instantiated and called as such:
 * Twitter obj = new Twitter();
 * obj.postTweet(userId,tweetId);
 * List<Integer> param_2 = obj.getNewsFeed(userId);
 * obj.follow(followerId,followeeId);
 * obj.unfollow(followerId,followeeId);
 */
