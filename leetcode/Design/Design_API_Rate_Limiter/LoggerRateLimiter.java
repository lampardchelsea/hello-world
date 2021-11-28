/**
Refer to
https://aaronice.gitbook.io/lintcode/hash-table/logger-rate-limiter
Design a logger system that receive stream of messages along with its timestamps, each message should be printed if and only if it isnot printed in the last 10 seconds.
Given a message and a timestamp (in seconds granularity), return true if the message should be printed in the given timestamp, otherwise returns false.
It is possible that several messages arrive roughly at the same time.
Example:
Logger logger = new Logger();

// logging string "foo" at timestamp 1
logger.shouldPrintMessage(1, "foo"); returns true; 

// logging string "bar" at timestamp 2
logger.shouldPrintMessage(2,"bar"); returns true;

// logging string "foo" at timestamp 3
logger.shouldPrintMessage(3,"foo"); returns false;

// logging string "bar" at timestamp 8
logger.shouldPrintMessage(8,"bar"); returns false;

// logging string "foo" at timestamp 10
logger.shouldPrintMessage(10,"foo"); returns false;

// logging string "foo" at timestamp 11
logger.shouldPrintMessage(11,"foo"); returns true;
*/

/**
Analysis
Naive implementation would be using hashmap, with key of log message and value of timestamp.
It would pass the OJ, however, there are concerns on the capacity, thinking of it as a cache, then certain eviction policy needed 
to remove obsolete record to avoid exploding the data structure in memory.
简单地用HashMap可以实现功能，但并不完美，没有考虑到大数据且很多message不同的情形，这样HashMap很有可能造成内存过载。归根结底，这是一个微型系统设计 
- design a logger system，因此也考验系统扩展性和健壮性。
如果使用Java的LinkedHashMap，可以很方便地实现eviction。实际上LinkedHashMap也可以用于实现LRU。
https://www.baeldung.com/java-linked-hashmap
LinkedHashMap<Integer, String> map = new LinkedHashMap<>(16, .75f, true);
initial capacity, custom load factor (LF), and access-order (vs default insertion-order)
This mechanism ensures that the order of iteration of elements is the order in which the elements were last accessed, from least-recently 
accessed to most-recently accessed. The order of elements in the key set is transformed as we perform access operations on the map.
自定义 removeEldestEntry()：
protected boolean removeEldestEntry(Map.Entry<String, Integer> eldest) {    return lastSecond - eldest.getValue() > 10;}
相关问题： https://leetcode.com/problems/design-hit-counter/
*/

// Solution 1: Naive Implementation Using HashMap (not considering capacity) - （134 ms）
class Logger {
    Map<String, Integer> logs;

    /** Initialize your data structure here. */
    public Logger() {
        this.logs = new HashMap<>();
    }

    /** Returns true if the message should be printed in the given timestamp, otherwise returns false.
        If this method returns false, the message will not be printed.
        The timestamp is in seconds granularity. */
    public boolean shouldPrintMessage(int timestamp, String message) {
        if (logs.containsKey(message)) {
            if (timestamp - logs.get(message) >= 10) {
                logs.put(message, timestamp);
                return true;
            } else {
                return false;
            }
        } else {
            logs.put(message, timestamp);
        }
        return true;
    }
}


// Solution 2: Using LinkedHashMap (@agnhugo) - (147ms)
public class Logger {

    public Map<String, Integer> map;
    int lastSecond = 0;

    /** Initialize your data structure here. */
    public Logger() {
        map = new java.util.LinkedHashMap<String, Integer>(100, 0.6f, true) {
            protected boolean removeEldestEntry(Map.Entry<String, Integer> eldest) {
                return lastSecond - eldest.getValue() > 10;
            }
        };
    }

    /** Returns true if the message should be printed in the given timestamp, otherwise returns false.
        If this method returns false, the message will not be printed.
        The timestamp is in seconds granularity. */
    public boolean shouldPrintMessage(int timestamp, String message) {
        lastSecond = timestamp;
        if(!map.containsKey(message)||timestamp - map.get(message) >= 10){
            map.put(message,timestamp);
            return true;
        }
        return false;
    }
}

// Solution 3: Using Queue (@han_xuan)
class Logger {
    // Algo thinking Queue
    // time = O(N)

    class TimeMsg {
        int timestamp;
        String msg;
        public TimeMsg(int timestamp, String msg) {
            this.timestamp = timestamp;
            this.msg = msg;
        }
    }

    /** Initialize your data structure here. */
    private static final int MAX_TIME_WINDOW = 10;

    Queue<TimeMsg> msgQueue;
    public Logger() {
        msgQueue = new LinkedList<>();
    }

    /** Returns true if the message should be printed in the given timestamp, otherwise returns false.
    If this method returns false, the message will not be printed.
    The timestamp is in seconds granularity. */
    public boolean shouldPrintMessage(int timestamp, String message) {

        while (!msgQueue.isEmpty() && timestamp - msgQueue.peek().timestamp >= MAX_TIME_WINDOW) {
            msgQueue.poll();
        }

        Iterator<TimeMsg> iter = msgQueue.iterator();
        while (iter.hasNext()) {
            TimeMsg tm = iter.next();
            if (tm.msg.equals(message)) return false;
        }


        msgQueue.offer(new TimeMsg(timestamp, message));

        return true;
    }
}
