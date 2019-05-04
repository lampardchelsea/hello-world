/**
 Refer to
 https://leetcode.com/problems/design-twitter/
 Design a simplified version of Twitter where users can post tweets, follow/unfollow another user 
 and is able to see the 10 most recent tweets in the user's news feed. Your design should support 
 the following methods:

postTweet(userId, tweetId): Compose a new tweet.
getNewsFeed(userId): Retrieve the 10 most recent tweet ids in the user's news feed. Each item in 
the news feed must be posted by users who the user followed or by the user herself. Tweets must be 
ordered from most recent to least recent.
follow(followerId, followeeId): Follower follows a followee.
unfollow(followerId, followeeId): Follower unfollows a followee.

Example:
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
*/

// Solution 1:
// Refer to
// https://leetcode.com/problems/design-twitter/discuss/82825/Java-OO-Design-with-most-efficient-function-getNewsFeed
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
    Map<Integer, List<Tweet>> userPostTweetMap;
    Map<Integer, List<Integer>> userRelationMap;
    int timestamp;
    
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
    
    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    public List<Integer> getNewsFeed(int userId) {
        List<Integer> result = new ArrayList<Integer>();
        List<Tweet> temp = new ArrayList<Tweet>();
        if(userPostTweetMap.get(userId) != null && userPostTweetMap.get(userId).size() > 0) {
        	temp.addAll(userPostTweetMap.get(userId));
        }
        if(userRelationMap.get(userId) != null && userRelationMap.get(userId).size() > 0) {
            for(Integer followeeId : userRelationMap.get(userId)) {
                if(followeeId != userId && userPostTweetMap.get(followeeId) != null && userPostTweetMap.get(followeeId).size() > 0) {
            		temp.addAll(userPostTweetMap.get(followeeId));
            	}
            }
        }
        MaxPQ maxPQ = new MaxPQ(temp.size());
        for(int i = 0; i < temp.size(); i++) {
            maxPQ.insert(temp.get(i));
        }
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
            if(!userRelationMap.get(followerId).contains(followeeId)) {
                userRelationMap.get(followerId).add(followeeId);
        	}
        }
    }
    
    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId) {
        if(userRelationMap.containsKey(followerId) && userRelationMap.get(followerId).contains(followeeId)) {
             userRelationMap.get(followerId).remove(Integer.valueOf(followeeId));
        }
    }
    
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
}

/**
 * Your Twitter object will be instantiated and called as such:
 * Twitter obj = new Twitter();
 * obj.postTweet(userId,tweetId);
 * List<Integer> param_2 = obj.getNewsFeed(userId);
 * obj.follow(followerId,followeeId);
 * obj.unfollow(followerId,followeeId);
 */
