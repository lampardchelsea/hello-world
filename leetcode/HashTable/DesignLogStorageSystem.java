/**
Refer to
http://buttercola.blogspot.com/2019/05/leetcode-635-design-log-storage-system.html
You are given several logs that each log contains a unique id and timestamp. Timestamp is a string that has the 
following format: Year:Month:Day:Hour:Minute:Second, for example, 2017:01:01:23:59:59. All domains are zero-padded decimal numbers.
Design a log storage system to implement the following functions:
void Put(int id, string timestamp): Given a log's unique id and timestamp, store the log in your storage system.

int[] Retrieve(String start, String end, String granularity): Return the id of logs whose timestamps are within 
the range from start to end. Start and end all have the same format as timestamp. However, granularity means 
the time level for consideration. For example, start = "2017:01:01:23:59:59", end = "2017:01:02:23:59:59", 
granularity = "Day", it means that we need to find the logs within the range from Jan. 1st 2017 to Jan. 2nd 2017.

Example 1:
put(1, "2017:01:01:23:59:59");
put(2, "2017:01:01:22:59:59");
put(3, "2016:01:01:00:00:00");
retrieve("2016:01:01:01:01:01","2017:01:01:23:00:00","Year"); 
// return [1,2,3], because you need to return all logs within 2016 and 2017.
retrieve("2016:01:01:01:01:01","2017:01:01:23:00:00","Hour"); 
// return [1,2], because you need to return all logs start from 2016:01:01:01 to 2017:01:01:23, where log 3 is left outside the range.

Note:
There will be at most 300 operations of Put or Retrieve.
Year ranges from [2000,2017]. Hour ranges from [00,23].
Output for Retrieve has no order required.
*/

// Solution 1: Mapping granularity with timestamp indexes
// Style 1:
// Refer to
// https://massivealgorithms.blogspot.com/2017/07/leetcode-635-design-log-storage-system.html
// https://www.cnblogs.com/grandyang/p/7224525.html
/**
这道题让我们设计一个日志存储系统，给了日志的生成时间和日志编号，日志的生成时间是精确到秒的，然后我们主要需要完成一个retrieve函数，
这个函数会给一个起始时间，结束时间，还有一个granularity精确度，可以精确到任意的年月日时分秒，可以分析下题目中的例子，应该不难理解。
我们首先需要一个数据结构来存储每个日志的编号和时间戳，那么这里我们就用一个数组，里面存pair，这样就能存下日志的数据了。然后由于我们
要用到精确度，所以我们用一个units数组来列出所有可能的精确度了。下面就是本题的难点了，如何能正确的在时间范围内取出日志。由于精确度
的存在，比如精确度是Day，那么我们就不关心后面的时分秒是多少了，只需要比到天就行了。判断是否在给定的时间范围内的方法也很简单，看其
是否大于起始时间，且小于结束时间，我们甚至可以直接用字符串相比较，不用换成秒啥的太麻烦。所以我们可以根据时间精度确定要比的子字符串
的位置，然后直接相比就行了。所以我们需要一个indices数组，来对应我们的units数组，记录下每个时间精度下取出的字符的个数。然后在
retrieve函数中，遍历所有的日志，快速的根据时间精度取出对应的时间戳并且和起始结束时间相比，在其之间的就把序号加入结果res即可
*/
class Solution {
    List<String[]> timestamps = new LinkedList<>();
    List<String> units = Arrays.asList("Year", "Month", "Day", "Hour", "Minute", "Second");
    int[] indices = new int[]{4,7,10,13,16,19};
    
    public void put(int id, String timestamp) { 
        timestamps.add(new String[]{Integer.toString(id), timestamp});
    }

    public List<Integer> retrieve(String s, String e, String gra) {
        List<Integer> res = new LinkedList<>();
        int idx = indices[units.indexOf(gra)];
        for (String[] timestamp : timestamps) {
            if (timestamp[1].substring(0, idx).compareTo(s.substring(0, idx)) >= 0 &&
                timestamp[1].substring(0, idx).compareTo(e.substring(0, idx)) <= 0) res.add(Integer.parseInt(timestamp[0]));
        }
        return res;
    }
}

// Style 2:
class LogSystem {
    TreeMap<String, List<Integer>> timeToIdMap;
    String minTimeStamp;
    String maxTimeStamp;
    Map<String, Integer> graMap;
 
    public LogSystem() {
        timeToIdMap = new TreeMap<>();
        minTimeStamp = "2000:01:01:00:00:00";
        maxTimeStamp = "2017:12:31:23:59:59";
        graMap = new HashMap<>();
        graMap.put("Year", 4);
        graMap.put("Month", 7);
        graMap.put("Day", 10);
        graMap.put("Hour", 13);
        graMap.put("Minute", 16);
        graMap.put("Second", 19);
    }
     
    public void put(int id, String timestamp) {
        List<Integer> idList = timeToIdMap.getOrDefault(timestamp, new ArrayList<>());
        idList.add(id);
        timeToIdMap.put(timestamp, idList);
    }
     
    public List<Integer> retrieve(String s, String e, String gra) {
        List<Integer> ans = new ArrayList<>();
        int graIdx = graMap.get(gra);
        String startTime = s.substring(0, graIdx) + minTimeStamp.substring(graIdx);
        String endTime = e.substring(0, graIdx) + maxTimeStamp.substring(graIdx);
 
        NavigableMap<String, List<Integer>> subMap = timeToIdMap.subMap(
            startTime, true, endTime, true);
 
        for (String timeStamp : subMap.keySet()) {
            ans.addAll(subMap.get(timeStamp));
        }
 
        return ans;
    }
}
