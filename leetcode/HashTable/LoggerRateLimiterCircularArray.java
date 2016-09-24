/**
 *
*/
public class Logger {
    private List<String>[] buf = new List[10];
    private Set<String> set = new HashSet<>();
    private int from = 0;

    /** Initialize your data structure here. */
    public Logger() {
        for(int i = 0; i < buf.length; i++) { 
          buf[i] = new ArrayList<>();
        }
    }
    
    /** Returns true if the message should be printed in the given timestamp, 
    otherwise returns false. The timestamp is in seconds granularity. */
    public boolean shouldPrintMessage(int timestamp, String message) {
        for(int t = from; t <= timestamp - 10; t ++) {
            for(String s : buf[(t+buf.length) % buf.length]) {
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

/**
 * Your Logger object will be instantiated and called as such:
 * Logger obj = new Logger();
 * boolean param_1 = obj.shouldPrintMessage(timestamp,message);
 */
