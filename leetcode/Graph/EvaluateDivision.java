/**
 Refer to
 https://leetcode.com/problems/evaluate-division/
 Equations are given in the format A / B = k, where A and B are variables represented as strings, 
 and k is a real number (floating point number). Given some queries, return the answers. 
 If the answer does not exist, return -1.0.

Example:
Given a / b = 2.0, b / c = 3.0.
queries are: a / c = ?, b / a = ?, a / e = ?, a / a = ?, x / x = ? .
return [6.0, 0.5, -1.0, 1.0, -1.0 ].

The input is: vector<pair<string, string>> equations, vector<double>& values, 
vector<pair<string, string>> queries , where equations.size() == values.size(), and the values 
are positive. This represents the equations. Return vector<double>.

According to the example above:
equations = [ ["a", "b"], ["b", "c"] ],
values = [2.0, 3.0],
queries = [ ["a", "c"], ["b", "a"], ["a", "e"], ["a", "a"], ["x", "x"] ].

The input is always valid. You may assume that evaluating the queries will result in no division by 
zero and there is no contradiction.
*/

// Solution 1: Build graph (weighted path as mutual direction)
// Refer to
// https://leetcode.com/problems/evaluate-division/discuss/88169/java-ac-solution-using-graph
// https://leetcode.com/problems/evaluate-division/discuss/88169/Java-AC-Solution-using-graph/93115
/**
 If a/b = 2.0 and b/c = 3.0, we can treat a,b, and c as vertices.
 then edge(a,b) weight 2.0 and edge(b,c) weight 3.0
 backward edge(b,a) weight 1/2.0 and backward edge(c,b)weight 1/3.0
 query a,c is a path from a to c, distance (a,c) = weight(a,b) * weight(b,c)
*/

// Style 1: DFS helper method return as void
class Solution {
    public double[] calcEquation(List<List<String>> equations, double[] values, List<List<String>> queries) {
        double[] result = new double[queries.size()];
        // Build graph
        Map<String, List<Edge>> adjs = new HashMap<String, List<Edge>>();
        for(int i = 0; i < equations.size(); i++) {
            List<String> equation = equations.get(i);
            String u = equation.get(0);
            String v = equation.get(1);
            double weight = values[i];
            Edge ef = new Edge(v, weight);
            Edge eb = new Edge(u, 1.0 / weight);
            if(!adjs.containsKey(u)) {
                adjs.put(u, new ArrayList<Edge>());
            }
            adjs.get(u).add(ef);
            if(!adjs.containsKey(v)) {
                adjs.put(v, new ArrayList<Edge>());
            }
            adjs.get(v).add(eb);
        }
        // Find path on graph between start and end point
        for(int i = 0; i < queries.size(); i++) {
            List<String> query = queries.get(i);
            String start = query.get(0);
            String end = query.get(1);
            Set<String> visited = new HashSet<String>();
            helper(start, end, adjs, visited, result, i, 1.0);
            // If keep as initial value 0.0 means no path between
            // start and end point, as required change to -1.0
            // This check is mandatory, below input able to test out
            // equations = [["a","b"],["c","d"]]
            // values = [1.0,1.0]
            // queries = [["a","c"],["b","d"],["b","a"],["d","c"]]
            if(result[i] == 0) {
                result[i] = -1.0;
            }
        }
        return result;
    }
    
    private void helper(String start, String end, Map<String, List<Edge>> adjs, Set<String> visited, double[] result, int index, double distance) {
        if(!adjs.containsKey(start) || !adjs.containsKey(end)) {
            result[index] = -1.0;
            return;
        }
        if(start.equals(end)) {
            result[index] = distance;
            return;
        }
        if(visited.contains(start)) {
            return;
        }
        visited.add(start);
        List<Edge> list = adjs.get(start);
        for(Edge e : list) {
            helper(e.to, end, adjs, visited, result, index, distance * e.weight);
        }
    }
    
    class Edge {        
        String to;
        double weight;
        public Edge(String s, double w) {
            this.to = s;
            this.weight = w;
        }
    }
}

// Style 2: DFS method return distance
class Solution {
    public double[] calcEquation(List<List<String>> equations, double[] values, List<List<String>> queries) {
        double[] result = new double[queries.size()];
        // Build graph
        Map<String, List<Edge>> adjs = new HashMap<String, List<Edge>>();
        for(int i = 0; i < equations.size(); i++) {
            List<String> equation = equations.get(i);
            String u = equation.get(0);
            String v = equation.get(1);
            double weight = values[i];
            Edge ef = new Edge(v, weight);
            Edge eb = new Edge(u, 1.0 / weight);
            if(!adjs.containsKey(u)) {
                adjs.put(u, new ArrayList<Edge>());
            }
            adjs.get(u).add(ef);
            if(!adjs.containsKey(v)) {
                adjs.put(v, new ArrayList<Edge>());
            }
            adjs.get(v).add(eb);
        }
        // Find path on graph between start and end point
        for(int i = 0; i < queries.size(); i++) {
            List<String> query = queries.get(i);
            String start = query.get(0);
            String end = query.get(1);
            Set<String> visited = new HashSet<String>();
            result[i] = helper(start, end, adjs, visited, 1.0);
        }
        return result;
    }
    
    private double helper(String start, String end, Map<String, List<Edge>> adjs, Set<String> visited, double distance) {
        if(!adjs.containsKey(start) || !adjs.containsKey(end)) {
            return -1.0;
        }
        if(start.equals(end)) {
            return distance;
        }
        if(!visited.contains(start)) {
            visited.add(start);
            List<Edge> list = adjs.get(start);
            for(Edge e : list) {
                double result = helper(e.to, end, adjs, visited, distance * e.weight);
                if(result != -1.0) {
                    return result;
                }
            }
        }
        return -1.0;
    }
    
    class Edge {        
        String to;
        double weight;
        public Edge(String s, double w) {
            this.to = s;
            this.weight = w;
        }
    }
}
