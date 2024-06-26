/**
 * Design a logger system that receive stream of messages along with its timestamps, 
 * each message should be printed if and only if it is not printed in the last 10 seconds.
 * 
 * Given a message and a timestamp (in seconds granularity), return true if the message 
 * should be printed in the given timestamp, otherwise returns false.
 * 
 * It is possible that several messages arrive roughly at the same time.
 * Example:
 * Logger logger = new Logger();
 * 
 * // logging string "foo" at timestamp 1
 * logger.shouldPrintMessage(1, "foo"); returns true; 
 * 
 * // logging string "bar" at timestamp 2
 * logger.shouldPrintMessage(2,"bar"); returns true;
 * 
 * // logging string "foo" at timestamp 3
 * logger.shouldPrintMessage(3,"foo"); returns false;
 * 
 * // logging string "bar" at timestamp 8
 * logger.shouldPrintMessage(8,"bar"); returns false;
 * 
 * // logging string "foo" at timestamp 10
 * logger.shouldPrintMessage(10,"foo"); returns false;
 * 
 * // logging string "foo" at timestamp 11
 * logger.shouldPrintMessage(11,"foo"); returns true;
*/
import java.util.HashMap;
import java.util.Map;


public class Logger{
  Map<String, Integer> map;
  public Logger() {
	  map = new HashMap<String, Integer>();
  }
  
  public boolean shouldPrintMessage(int timestamp, String message) {
    boolean result = true;
    Integer preTimeStamp = map.put(message, timestamp);
    
    if(preTimeStamp != null) {
    	int interval = timestamp - preTimeStamp.intValue();
    	if(interval < 9) {
        	result = false;
    	}
    }  
    
    return result;  
  }
  
  public static void main(String[] args) {
	  Logger logger = new Logger();
	  
	  boolean result1 = logger.shouldPrintMessage(1, "foo");
	  System.out.println(result1);
	  
	  boolean result2 = logger.shouldPrintMessage(2, "foo");
	  System.out.println(result2);
	  
	  boolean result11 = logger.shouldPrintMessage(11, "foo");
	  System.out.println(result11);
  }
  
}
