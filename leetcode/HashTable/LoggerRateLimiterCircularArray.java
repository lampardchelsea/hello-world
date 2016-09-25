/**
 * Best example is 
 * http://blog.csdn.net/jmspan/article/details/51697104
 * 
 * public class Logger {
    private List<String>[] buf = new List[10];
    private Set<String> set = new HashSet<>();
    private int from = 0;

    // Initialize your data structure here.
    public Logger() {
        for(int i = 0; i < buf.length; i++) { 
          buf[i] = new ArrayList<>();
        }
    }
    
    // Returns true if the message should be printed in the given timestamp, 
    // otherwise returns false. The timestamp is in seconds granularity.
    public boolean shouldPrintMessage(int timestamp, String message) {
        for(int t = from; t <= timestamp - 10; t++) {
            for(String s : buf[(t + buf.length) % buf.length]) {
                set.remove(s);
            }
            buf[(t + buf.length) % buf.length].clear();
        }
        
        if (!set.add(message)) {
            return false;
        } 

        buf[timestamp % buf.length].add(message);
        from = timestamp - 9;
        return true;
    }
}
*/

import java.util.HashSet;
import java.util.Set;


public class Logger{  
	private static final int DEFAULT_CAPACTIY = 10;
	private String[] queue;
	private Set<String> set;
	
	// Elegant design of member variable with for loop to remove redundant
	// elements at the first place in shouldPrintMessage() method
	private int startRecordTimestamp = 0;
	
	public Logger() {
		queue = new String[DEFAULT_CAPACTIY];
		set = new HashSet<String>();
	}
	
	public boolean shouldPrintMessage(int timestamp, String message) {
		// Every time call this method will remove all elements between latest  
		// previous start record timestamp(t = startRecordTimestamp) and current 
		// start record timestamp(t = timestamp - DEFAULT_CAPACTIY) on circular 
		// array(queue) and HashSet which bind to this array
		for(int t = startRecordTimestamp; t <= timestamp - DEFAULT_CAPACTIY; t++) {
			// Note: When calculate remainder, must use (t + DEFAULT_CAPACTIY) % DEFAULT_CAPACTIY,
			// if just use t % DEFAULT_CAPACTIY, will throw ArrayOutOfBound exception
			// Analyze
			// http://stackoverflow.com/questions/4403542/how-does-java-do-modulus-calculations-with-negative-numbers
			// Since "mathematically" both are correct:
			// -13 % 64 = -13 (on modulus 64)  
			// -13 % 64 = 51 (on modulus 64)
			// One of the options had to be chosen by Java language developers and they chose:
			// the sign of the result equals the sign of the dividend.
			// e.g If t = -8, then [-8 % 10], as sign of result will same as dividend(-8),
			// that's why we have a negative index and throw ArrayOutOfBound exception
			// As a circular array, we can use (t + DEFAULT_CAPACTIY) to make sure modulus is positive
			// which can be used as index.
			//String temp = queue[t % DEFAULT_CAPACTIY];
			String temp = queue[(t + DEFAULT_CAPACTIY) % DEFAULT_CAPACTIY];
			set.remove(temp);
			//queue[t % DEFAULT_CAPACTIY] = null;
			queue[(t + DEFAULT_CAPACTIY) % DEFAULT_CAPACTIY] = null;	
		}
		
		// Use HashSet to reflect whether duplicate message insert 
		// on top of previous 10 seconds messages which stored on
		// circular array. So every time call this method, we will
		// check message duplicate on HashSet first.
		if(!set.add(message)) {
			return false;
		}
		
		// Store message on circular array based on remainder of timestamp
		// and DEFAULT_CAPACTIY
		queue[timestamp % DEFAULT_CAPACTIY] = message;
		
		// Note: The start record timestamp not (timestamp - DEFAULT_CAPACTIY),
		// as timestamp itself as the last element on circular array,
		// only 9 previous elements need to keep.
		// e.g If timestamp = 11, then as DEFAULT_CAPACTIY = 10 which
		// represent record 10 messages based on second granularity,
		// the start record timestamp should be 11 - (10 - 1) = 2
		// e.g If timestamp = 1, then start record timestamp 2 - 9 = -7
		// And as startRecordTimestamp is member variable, its value will
		// be used on removing redundant elements when next call of this method 
		startRecordTimestamp = timestamp - (DEFAULT_CAPACTIY - 1);
		
		return true;
	}
	
	public static void main(String[] args) {
		Logger logger = new Logger();
  
		boolean result1 = logger.shouldPrintMessage(1, "foo");
		System.out.println(result1);
		
		boolean result2 = logger.shouldPrintMessage(2, "foo");
		System.out.println(result2);
		
		boolean result11 = logger.shouldPrintMessage(11, "foo");
		System.out.println(result11);
		
		boolean result21 = logger.shouldPrintMessage(21, "foo");
		System.out.println(result21);
	}
  
}
