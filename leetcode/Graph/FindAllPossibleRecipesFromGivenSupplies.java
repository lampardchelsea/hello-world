https://leetcode.com/problems/find-all-possible-recipes-from-given-supplies/description/
You have information about n different recipes. You are given a string array recipes and a 2D string array ingredients. The ith recipe has the name recipes[i], and you can create it if you have all the needed ingredients from ingredients[i]. A recipe can also be an ingredient for other recipes, i.e., ingredients[i] may contain a string that is in recipes.
You are also given a string array supplies containing all the ingredients that you initially have, and you have an infinite supply of all of them.
Return a list of all the recipes that you can create. You may return the answer in any order.
Note that two recipes may contain each other in their ingredients.
 
Example 1:
Input: recipes = ["bread"], ingredients = [["yeast","flour"]], supplies = ["yeast","flour","corn"]
Output: ["bread"]
Explanation:We can create "bread" since we have the ingredients "yeast" and "flour".

Example 2:
Input: recipes = ["bread","sandwich"], ingredients = [["yeast","flour"],["bread","meat"]], supplies = ["yeast","flour","meat"]
Output: ["bread","sandwich"]
Explanation:We can create "bread" since we have the ingredients "yeast" and "flour".We can create "sandwich" since we have the ingredient "meat" and can create the ingredient "bread".

Example 3:
Input: recipes = ["bread","sandwich","burger"], ingredients = [["yeast","flour"],["bread","meat"],["sandwich","meat","bread"]], supplies = ["yeast","flour","meat"]
Output: ["bread","sandwich","burger"]
Explanation:We can create "bread" since we have the ingredients "yeast" and "flour".We can create "sandwich" since we have the ingredient "meat" and can create the ingredient "bread".We can create "burger" since we have the ingredient "meat" and can create the ingredients "bread" and "sandwich".
 
Constraints:
- n == recipes.length == ingredients.length
- 1 <= n <= 100
- 1 <= ingredients[i].length, supplies.length <= 100
- 1 <= recipes[i].length, ingredients[i][j].length, supplies[k].length <= 10
- recipes[i], ingredients[i][j], and supplies[k] consist only of lowercase English letters.
- All the values of recipes and supplies combined are unique.
- Each ingredients[i] does not contain any duplicate values.
--------------------------------------------------------------------------------
Attempt 1: 2026-06-16
Solution 1: Toplogical Sort (60 min)
class Solution {
    public List<String> findAllRecipes(String[] recipes, List<List<String>> ingredients, String[] supplies) {
        // 记录每道菜尚未满足的原料数量 (入度)
        Map<String, Integer> inDegree = new HashMap<>();
        // 建图: 原料 -> 依赖此原料的菜谱列表
        Map<String, List<String>> graph = new HashMap<>();
        for(int i = 0; i < recipes.length; i++) {
            inDegree.put(recipes[i], ingredients.get(i).size());
            for(String ing : ingredients.get(i)) {
                graph.putIfAbsent(ing, new ArrayList<>());
                graph.get(ing).add(recipes[i]);
            }
        }
        List<String> result = new ArrayList<>();
        // 将所有初始供应加入队列
        Queue<String> q = new LinkedList<>();
        for(String supply : supplies) {
            q.offer(supply);
        }
        while(!q.isEmpty()) {
            String supply = q.poll();
            // 非原料跳过
            if(!graph.containsKey(supply)) {
                continue;
            }
            for(String recipe : graph.get(supply)) {
                int newDegree = inDegree.get(recipe) - 1;
                inDegree.put(recipe, newDegree);
                if(newDegree == 0) {
                    result.add(recipe);
                    // 做好的菜也作为原料
                    q.offer(recipe);
                }
            }
        }
        return result;
    }
}

时间复杂度: O(V + E)，其中 V 是所有不同菜谱和原料的数量，E 是依赖关系的数量（即所有 ingredients 列表中的元素总数）。
空间复杂度: O(V + E)，用于存储图和入度信息。

Solution 2: DFS + Cycle Detection (60 min)
class Solution {
    public List<String> findAllRecipes(String[] recipes, List<List<String>> ingredients, String[] supplies) {
        // 1. 建立菜谱到所需材料的映射
        Map<String, List<String>> graph = new HashMap<>();
        for(int i = 0; i < recipes.length; i++) {
            graph.put(recipes[i], ingredients.get(i));
        }
        // 2. 供应品集合
        Set<String> supplySet = new HashSet<>(Arrays.asList(supplies));
        // 3. 状态记忆: 0=未访问, 1=访问中, 2=可制作, 3=不可制作
        Map<String, Integer> memo = new HashMap<>();
        List<String> result = new ArrayList<>();
        for(String recipe : recipes) {
            if(canMake(recipe, supplySet, graph, memo)) {
                result.add(recipe);
            }
        }
        return result;
    }

    private boolean canMake(String recipe, Set<String> supplySet, Map<String, List<String>> graph, Map<String, Integer> memo) {
        if(supplySet.contains(recipe)) {
            return true;
        }
        if(!graph.containsKey(recipe)) {
            return false;
        }
        Integer state = memo.get(recipe);
        if(state != null) {
            // 遇到环
            if(state == 1) {
                return false;
            // 2=可制作
            } else if(state == 2) {
                return true;
            // 3=不可制作
            } else {
                return false;
            }
        }
        // 标记为访问中（入栈）
        memo.put(recipe, 1);
        // 检查所有原料
        for(String ing : graph.get(recipe)) {
            // 只要有一种原料不可制作，当前菜谱就不可制作
            if(!canMake(ing, supplySet, graph, memo)) {
                // 存储失败状态
                memo.put(recipe, 3);
                return false;
            }
        }
        // 所有原料都可制作
        memo.put(recipe, 2);
        return true;
    }
}

时间复杂度: O(V + E)，其中 V 是所有不同菜谱和原料的数量，E 是依赖关系的数量（即所有 ingredients 列表中的元素总数）。
空间复杂度: O(V + E)，memo play as recursion stack

Refer to Deepseek
这道题的核心是处理菜谱之间的依赖关系。可以把每道菜看成一个节点，它需要的原材料（可能包括其他菜）就是它的前置依赖。
解决这类依赖关系问题，最经典、高效的方法就是拓扑排序（Topological Sort），也就是常说的 Kahn 算法。
以下是基于拓扑排序的 Java 解法，代码清晰，并附有详细注释。
解题思路：拓扑排序
1.建图与统计：
- 遍历所有菜谱，对于菜谱 recipe 及其需要的原料列表 ingredients。
- 使用一个 HashMap<String, Integer> (inDegree) 来记录每道菜尚未拥有的原料数量，初始值就是它需要的原料总数。
- 同时，构建一个反向依赖图 HashMap<String, List<String>> (graph)。键（Key）是原料，值（Value）是依赖这个原料的所有菜谱。这表示，一旦这个原料变得可用，就能帮助这些菜谱减少一个依赖。
2.初始化队列：
- 将所有初始就拥有的 supplies 加入一个队列 (Queue) 中。这些是初始可用的“原料”。
3.拓扑排序处理：
- 当队列不为空时，取出一个可用的原料 item。
- 检查 graph 中是否有依赖 item 的菜谱（即 item 是哪些菜的原料）。
- 如果有，遍历这些菜谱 recipe，将其在 inDegree 中的计数减 1，表示它所需的一个原料已经备齐。
- 当某个 recipe 的 inDegree 变为 0 时，说明它所有的原料都已就绪，这道菜就可以被制作出来。将它加入答案列表，并同时将它作为一个新的可用原料加入队列，因为它可以用来制作其他依赖它的菜。
4.返回结果：队列处理完毕后，答案列表中即为所有可以做出的菜。
Java 代码实现
import java.util.*;

class Solution {
    public List<String> findAllRecipes(String[] recipes, 
                                       List<List<String>> ingredients, 
                                       String[] supplies) {
        // 1. 构建图: 原料 -> 依赖此原料的菜谱列表
        Map<String, List<String>> graph = new HashMap<>();
        // 2. 记录每道菜还缺少的原料数量 (入度)
        Map<String, Integer> inDegree = new HashMap<>();

        int n = recipes.length;
        for (int i = 0; i < n; i++) {
            String recipe = recipes[i];
            // 初始化入度为该菜谱需要的原料总数
            inDegree.put(recipe, ingredients.get(i).size());
            
            for (String ing : ingredients.get(i)) {
                // 将当前菜谱加入到其原料的依赖列表中
                graph.putIfAbsent(ing, new ArrayList<>());
                graph.get(ing).add(recipe);
            }
        }

        // 3. 将所有初始供应加入队列
        Queue<String> queue = new LinkedList<>();
        for (String supply : supplies) {
            queue.offer(supply);
        }

        List<String> result = new ArrayList<>();
        
        // 4. 拓扑排序处理
        while (!queue.isEmpty()) {
            String available = queue.poll();
            
            // 如果当前可用原料不是任何菜谱的原料，则跳过
            if (!graph.containsKey(available)) {
                continue;
            }
            
            // 遍历所有依赖此原料的菜谱
            for (String recipe : graph.get(available)) {
                // 该菜谱所需的一种原料已满足，入度减 1
                int newDegree = inDegree.get(recipe) - 1;
                inDegree.put(recipe, newDegree);
                
                // 如果入度变为 0，说明所有原料都已备齐
                if (newDegree == 0) {
                    result.add(recipe);      // 记录可以做出的菜
                    queue.offer(recipe);     // 这道菜本身也可以作为原料继续使用
                }
            }
        }
        
        return result;
    }
}
复杂度分析
- 时间复杂度: O(V + E)，其中 V 是所有不同菜谱和原料的数量，E 是依赖关系的数量（即所有 ingredients 列表中的元素总数）。
- 空间复杂度: O(V + E)，用于存储图和入度信息。
💡 另一种思路：DFS + 记忆化搜索
核心思想：对每道菜递归检查其所有原料，利用状态标记避免重复和死循环。
class Solution {
    public List<String> findAllRecipes(String[] recipes, 
                                       List<List<String>> ingredients, 
                                       String[] supplies) {
        // 将供应品存入集合以便快速查询
        Set<String> supplySet = new HashSet<>(Arrays.asList(supplies));
        // 记录菜谱序号，方便快速获取原料列表
        Map<String, Integer> recipeIndex = new HashMap<>();
        for (int i = 0; i < recipes.length; i++) {
            recipeIndex.put(recipes[i], i);
        }
        
        // 状态: 0=未访问, 1=访问中(用于检测循环), 2=可制作
        Map<String, Integer> memo = new HashMap<>();
        List<String> result = new ArrayList<>();
        
        for (String recipe : recipes) {
            if (canMake(recipe, supplySet, recipeIndex, ingredients, memo)) {
                result.add(recipe);
            }
        }
        return result;
    }
    
    private boolean canMake(String item, 
                            Set<String> supplySet,
                            Map<String, Integer> recipeIndex,
                            List<List<String>> ingredients,
                            Map<String, Integer> memo) {
        // 如果已经在供应中，直接可制作
        if (supplySet.contains(item)) return true;
        // 如果不是菜谱，则不可制作
        if (!recipeIndex.containsKey(item)) return false;
        
        // 检查记忆化状态
        Integer state = memo.get(item);
        if (state != null) {
            if (state == 1) return false; // 遇到循环依赖
            return state == 2;
        }
        
        // 标记为访问中
        memo.put(item, 1);
        int idx = recipeIndex.get(item);
        for (String ing : ingredients.get(idx)) {
            // 如果任何一种原料不可制作，则当前菜谱不可制作
            if (!canMake(ing, supplySet, recipeIndex, ingredients, memo)) {
                memo.put(item, 0); // 标记为不可制作
                return false;
            }
        }
        // 所有原料都可制作
        memo.put(item, 2);
        return true;
    }
}
- 复杂度: 每个菜谱和原料至多被访问一次，故 O(V+E) 时间，O(V) 空间（递归深度可能为 O(V)）。
--------------------------------------------------------------------------------
对比与选择
- BFS 思路清晰，实现简单，且天然处理循环依赖（入度不会减到0），适合面试。
- DFS 递归理解较抽象，但代码稍短（如果不算记忆化），且能提前检测循环。
两种方法均能 AC，推荐优先使用 BFS（拓扑排序）。

Refer to Kimi
Here are both BFS (Topological Sort) and DFS solutions for LeetCode 2115. Find All Possible Recipes from Given Supplies.
--------------------------------------------------------------------------------
Solution 1: BFS (Kahn's Algorithm / Topological Sort)
This is the standard approach. We treat recipes as nodes and ingredients as dependencies.
import java.util.*;

class Solution {
    public List<String> findAllRecipes(
            String[] recipes, 
            List<List<String>> ingredients, 
            String[] supplies) {
        
        // Graph: ingredient -> list of recipes that need it
        Map<String, List<String>> graph = new HashMap<>();
        
        // Indegree: how many ingredients each recipe still needs
        Map<String, Integer> indegree = new HashMap<>();
        
        // Build graph and initialize indegrees
        for (int i = 0; i < recipes.length; i++) {
            indegree.put(recipes[i], ingredients.get(i).size());
            for (String ing : ingredients.get(i)) {
                graph.computeIfAbsent(ing, k -> new ArrayList<>()).add(recipes[i]);
            }
        }
        
        // Queue starts with all supplies (they are immediately available)
        Deque<String> queue = new ArrayDeque<>();
        for (String s : supplies) {
            queue.offer(s);
        }
        
        List<String> ans = new ArrayList<>();
        
        // BFS
        while (!queue.isEmpty()) {
            String current = queue.poll();
            
            for (String recipe : graph.getOrDefault(current, Collections.emptyList())) {
                indegree.put(recipe, indegree.get(recipe) - 1);
                
                // All ingredients available -> recipe can be made
                if (indegree.get(recipe) == 0) {
                    ans.add(recipe);
                    queue.offer(recipe);  // Now this recipe is also a supply
                }
            }
        }
        
        return ans;
    }
}
--------------------------------------------------------------------------------
Solution 2: DFS (Memoization)
DFS checks each recipe recursively. We use a Set for O(1) supply lookup and memoization to avoid recomputation.
import java.util.*;

class Solution {
    public List<String> findAllRecipes(
            String[] recipes, 
            List<List<String>> ingredients, 
            String[] supplies) {
        
        // Quick lookup for supplies
        Set<String> available = new HashSet<>(Arrays.asList(supplies));
        
        // Map recipe name -> its ingredient list
        Map<String, List<String>> recipeMap = new HashMap<>();
        for (int i = 0; i < recipes.length; i++) {
            recipeMap.put(recipes[i], ingredients.get(i));
        }
        
        // Memoization: recipe -> can it be made?
        // 0 = unvisited, 1 = visiting (in current DFS stack, cycle detected), 2 = can make
        Map<String, Integer> memo = new HashMap<>();
        
        List<String> ans = new ArrayList<>();
        
        for (String recipe : recipes) {
            if (canMake(recipe, recipeMap, available, memo)) {
                ans.add(recipe);
            }
        }
        
        return ans;
    }
    
    private boolean canMake(
            String recipe,
            Map<String, List<String>> recipeMap,
            Set<String> available,
            Map<String, Integer> memo) {
        
        // Already computed
        if (memo.containsKey(recipe)) {
            return memo.get(recipe) == 2;
        }
        
        // Not a known recipe (shouldn't happen for valid input, but safe check)
        if (!recipeMap.containsKey(recipe)) {
            return available.contains(recipe);
        }
        
        // Mark as visiting (detect cycle)
        memo.put(recipe, 1);
        
        // Check all ingredients
        for (String ing : recipeMap.get(recipe)) {
            // If ingredient is a supply, skip
            if (available.contains(ing)) {
                continue;
            }
            
            // If ingredient is another recipe, check recursively
            if (recipeMap.containsKey(ing)) {
                if (!canMake(ing, recipeMap, available, memo)) {
                    memo.put(recipe, 0);  // Actually we can just leave as 1 or use 0 for false
                    return false;
                }
            } else {
                // Not a supply and not a recipe -> impossible
                memo.put(recipe, 0);
                return false;
            }
        }
        
        // All ingredients available -> recipe can be made
        memo.put(recipe, 2);
        available.add(recipe);  // Now this recipe is also available for others
        return true;
    }
}
--------------------------------------------------------------------------------
Solution 3: DFS with Cycle Detection (Cleaner State Enum)
Using explicit states for better readability:
import java.util.*;

class Solution {
    
    // 0 = unvisited, 1 = visiting, 2 = can make, 3 = cannot make
    private static final int UNVISITED = 0;
    private static final int VISITING = 1;
    private static final int CAN_MAKE = 2;
    private static final int CANNOT_MAKE = 3;
    
    public List<String> findAllRecipes(
            String[] recipes, 
            List<List<String>> ingredients, 
            String[] supplies) {
        
        Set<String> available = new HashSet<>(Arrays.asList(supplies));
        Map<String, List<String>> recipeMap = new HashMap<>();
        Map<String, Integer> state = new HashMap<>();
        
        for (int i = 0; i < recipes.length; i++) {
            recipeMap.put(recipes[i], ingredients.get(i));
            state.put(recipes[i], UNVISITED);
        }
        
        List<String> ans = new ArrayList<>();
        
        for (String recipe : recipes) {
            if (dfs(recipe, recipeMap, available, state) == CAN_MAKE) {
                ans.add(recipe);
            }
        }
        
        return ans;
    }
    
    private int dfs(
            String recipe,
            Map<String, List<String>> recipeMap,
            Set<String> available,
            Map<String, Integer> state) {
        
        // Already computed
        if (state.containsKey(recipe) && state.get(recipe) != UNVISITED) {
            return state.get(recipe);
        }
        
        // Base case: it's a supply
        if (available.contains(recipe) && !recipeMap.containsKey(recipe)) {
            return CAN_MAKE;
        }
        
        // Not a recipe and not a supply
        if (!recipeMap.containsKey(recipe)) {
            return CANNOT_MAKE;
        }
        
        // Mark as visiting (detect cycle)
        state.put(recipe, VISITING);
        
        for (String ing : recipeMap.get(recipe)) {
            int result;
            
            if (available.contains(ing)) {
                continue;  // Ingredient is available
            }
            
            result = dfs(ing, recipeMap, available, state);
            
            if (result == VISITING || result == CANNOT_MAKE) {
                // Cycle detected or ingredient cannot be made
                state.put(recipe, CANNOT_MAKE);
                return CANNOT_MAKE;
            }
        }
        
        // All ingredients can be made
        state.put(recipe, CAN_MAKE);
        available.add(recipe);  // This recipe is now available
        return CAN_MAKE;
    }
}
--------------------------------------------------------------------------------
Comparison
AspectBFS (Topological)DFS (Memoization)Core ideaProcess in dependency orderRecursively check each ingredientCycle handlingNatural (nodes in cycle never reach indegree 0)Explicit state tracking (VISITING)TimeO(V + E)O(V + E)SpaceO(V + E)O(V + E) recursion stackIntuition"What can I make now?""Can I make this recipe?"Best forWhen you want all possible recipesWhen checking specific recipes or need path tracing
Both approaches are optimal. The BFS solution is generally preferred for this problem because it's more straightforward and naturally handles the "cascading availability" logic.

追问：我知道Leetcode 210. Course Schedule II的DFS+记忆化搜索的解法如下：
class Solution { 
    public int[] findOrder(int numCourses, int[][] prerequisites) { 
        List<Integer> tmp = new ArrayList<Integer>(); 
        boolean[] visited = new boolean[numCourses];   
        boolean[] recursionStack = new boolean[numCourses];   
        // Build graph   
        Map<Integer, List<Integer>> graph = new HashMap<Integer, List<Integer>>();   
        for(int i = 0; i < numCourses; i++) {   
            graph.put(i, new ArrayList<Integer>());   
        }   
        for(int[] pre : prerequisites) {   
            graph.get(pre[1]).add(pre[0]);   
        }   
        // DFS to find cycle based on each node   
        for(int i = 0; i < numCourses; i++) {   
            // Additional check 'visited' or not to promote  
            // efficiency on the fly since 'visited' will be  
            // update during DFS  
            if(!visited[i]) {  
                // If we have cycle then not able to finish   
                if(hasCycle(i, graph, visited, recursionStack, tmp)) {   
                    return new int[]{};
                }
            }
        }
        int[] result = new int[numCourses]; 
        for(int i = 0; i < numCourses; i++) { 
            result[i] = tmp.get(i); 
        } 
        return result; 
    } 
     
    private boolean hasCycle(int course, Map<Integer, List<Integer>> graph, boolean[] visited, boolean[] recursionStack, List<Integer> tmp) {   
        // If course detect on same path again then find cycle   
        if(recursionStack[course]) {   
            return true;   
        }   
        if(visited[course]) {   
            return false;   
        }      
        // Don't add 'course' before 'no cycle' confirmed 
        // Test out: 
        // Input: 4, [[1,0],[2,0],[3,1],[3,2]] 
        // Output: [0,1,3,2] 
        // Expected: [0,2,1,3] 
        //tmp.add(course); 
        visited[course] = true;   
        // Backtrack the current path since switch to other path   
        recursionStack[course] = true;   
        for(int neighbour : graph.get(course)) {   
            if(hasCycle(neighbour, graph, visited, recursionStack, tmp)) {   
                return true;   
            }   
        } 
        recursionStack[course] = false;   
        // The only difference than L207.Course Schedule which record path 
        // 1. Must put 'course' into path reversely, always insert at index = 0 
        // 2. Only add 'course' after 'no cycle' confirmed 
        tmp.add(0, course); 
        return false;   
    }    
}
能否结合这个Leetcode 210的这个解法详细对比和解释一下Leetcode 2115的DFS + 记忆化搜索解法？尤其是backtrack的部分？
Refer to Deepseek
好的，我们来详细对比 LeetCode 210 和 2115 的 DFS + 记忆化搜索解法，并着重剖析 DFS 中的 回溯（backtrack） 部分。
--------------------------------------------------------------------------------
一、LeetCode 210 的 DFS 解法回顾（你提供的代码）
核心机制
- visited：标记节点是否已被完全处理（即已经加入拓扑排序结果）。一旦 visited[course] == true，表示该节点及其所有依赖都已处理完毕，不会再被访问。
- recursionStack：标记当前 DFS 递归路径上的节点。用于检测环：如果在递归过程中再次遇到已经在栈中的节点，说明存在环。
- tmp：存储拓扑排序结果（逆序）。因为 DFS 是后序添加（先处理邻接，再加入当前节点），所以最终 tmp 中是从后往前排列的，我们将其逆序后得到正序（但这里直接 tmp.add(0, course) 始终保持头部插入，最终得到的就是拓扑顺序）。
回溯（backtrack）体现
recursionStack[course] = true;         // 进入递归，标记在路径上
for (int neighbour : graph.get(course)) {
    if (hasCycle(neighbour, ...)) return true;
}
recursionStack[course] = false;        // 退出递归，清除路径标记（回溯）
tmp.add(0, course);                    // 后序加入结果
这里的 回溯 就是 recursionStack[course] = false，它撤销了当前节点在递归路径上的标记，让后续其他分支的搜索不受影响。同时，visited[course] 保持 true，表示该节点已经处理完毕，无需再重复计算。
--------------------------------------------------------------------------------
二、LeetCode 2115 的 DFS + 记忆化搜索解法
问题特点
- 节点有两种：原料（初始供应） 和 菜谱。
- 菜谱依赖一组原料，原料可以是初始供给，也可以是其他菜谱。
- 需要找出所有可以制作的菜谱。
- 存在循环依赖（如 A 依赖 B，B 依赖 A）时，这些菜谱均无法制作。
DFS 设计思路
与 210 类似，我们也需要：
1.记忆化：记录每个菜谱/原料是否可制作，避免重复计算。
2.环检测：在 DFS 递归过程中，如果遇到当前路径上的节点，说明出现循环依赖，则该节点不可制作。
通常使用状态数组或 HashMap 来存储每个节点的状态：
- 0 = 未访问
- 1 = 正在访问（在递归栈中）
- 2 = 可制作（成功）
- 3 = 不可制作（失败）
Java 实现（带详细注释）
class Solution {
    public List<String> findAllRecipes(String[] recipes, 
                                       List<List<String>> ingredients, 
                                       String[] supplies) {
        // 1. 建立菜谱到索引的映射
        Map<String, Integer> recipeIndex = new HashMap<>();
        for (int i = 0; i < recipes.length; i++) {
            recipeIndex.put(recipes[i], i);
        }
        
        // 2. 供应品集合
        Set<String> supplySet = new HashSet<>(Arrays.asList(supplies));
        
        // 3. 状态记忆: 0=未访问, 1=访问中, 2=可制作, 3=不可制作
        Map<String, Integer> memo = new HashMap<>();
        
        List<String> result = new ArrayList<>();
        for (String recipe : recipes) {
            if (canMake(recipe, supplySet, recipeIndex, ingredients, memo)) {
                result.add(recipe);
            }
        }
        return result;
    }
    
    private boolean canMake(String item,
                            Set<String> supplySet,
                            Map<String, Integer> recipeIndex,
                            List<List<String>> ingredients,
                            Map<String, Integer> memo) {
        // 基础情况：如果是供应品，直接可制作
        if (supplySet.contains(item)) return true;
        
        // 如果不是菜谱，则不可能制作（因为不是供应品，也不是菜谱）
        if (!recipeIndex.containsKey(item)) return false;
        
        // 检查记忆
        Integer state = memo.get(item);
        if (state != null) {
            if (state == 1) return false; // 遇到环
            return state == 2;            // 2 可制作，3 不可制作
        }
        
        // 标记为访问中（入栈）
        memo.put(item, 1);
        
        int idx = recipeIndex.get(item);
        // 检查所有原料
        for (String ing : ingredients.get(idx)) {
            if (!canMake(ing, supplySet, recipeIndex, ingredients, memo)) {
                // 只要有一种原料不可制作，当前菜谱就不可制作
                memo.put(item, 3);  // 存储失败状态
                return false;
            }
        }
        
        // 所有原料都可制作
        memo.put(item, 2);
        return true;
    }
}
回溯部分在哪里？
在这个 DFS 中，我们没有显式地像 210 那样用一个 recursionStack 数组来标记路径，而是利用状态 1 充当 recursionStack 的作用：
- 当进入 canMake(item) 时，首先检查 memo.get(item)，如果状态为 1，说明当前节点正在递归栈中，即出现了环，直接返回 false。
- 在递归返回后，我们没有显式清除状态 1，而是直接覆盖为 2 或 3。这是因为对于该节点，递归已经结束，其最终状态已经确定，不再需要保留“访问中”标记。
从抽象层面看，回溯体现在：
- 当发现某个原料不可制作时，我们回退到当前菜谱，并判定当前菜谱也不可制作。
- 我们撤销（覆盖）了当前菜谱的“访问中”状态，并给它一个最终结果（2 或 3）。
这与 210 中的 recursionStack[course]=false 本质上相同，都是在递归返回后，清除当前节点在路径上的标记。只不过 2115 使用状态值覆盖，而 210 使用单独的布尔数组。
--------------------------------------------------------------------------------
三、对比总结
方面LeetCode 210LeetCode 2115节点类型课程（全部可处理）原料（供应品） + 菜谱（需判断）目标找到一种拓扑排序找出所有可制作的菜谱记忆化visited 标记节点是否处理完毕memo 存储节点最终状态（可/不可制作）环检测recursionStack 标记当前路径memo 状态 1 表示正在访问回溯动作退出时清除 recursionStack，并加入结果退出时覆盖状态为最终结果（2 或 3）结果构建后序加入 tmp（逆序）直接收集状态为 2 的菜谱递归逻辑先处理邻接，若无环则加入结果先检查所有原料，全部成功则成功
回溯的本质
在两种解法中，回溯都是为了在递归返回时，撤销当前节点对“当前路径”的影响，从而让其他分支的搜索不受干扰。同时，回溯也伴随着对结果的记录（210 加入拓扑序，2115 存入最终状态）。
--------------------------------------------------------------------------------
四、易混淆点澄清
1.为什么 210 需要 visited 而 2115 只需 memo 状态？
- 210 中 visited 表示“该节点已处理完毕”，在拓扑排序中，一个节点一旦处理完就不会再被访问；而 2115 中的 memo 同时记录了“已访问过”和“最终结果”，不需要单独区分。
2.为什么 2115 没有显式的 recursionStack 清除？
- 因为我们将状态值 1 直接覆盖为最终结果，这相当于在退出时清除了路径标记。如果递归过程中碰到 1，就判定为环。
3.两种 DFS 的时间复杂度均为 O(V+E)，其中 V 是节点数（课程或菜谱+原料），E 是依赖边数。
--------------------------------------------------------------------------------
希望这个对比能帮你深刻理解 DFS 回溯在依赖关系问题中的应用。如果你还有疑问，欢迎继续追问！

Refer to Kimi
好的，下面详细对比 LeetCode 210. Course Schedule II 和 LeetCode 2115. Find All Possible Recipes from Given Supplies 的 DFS 解法，重点解释两者的异同和 backtrack 机制。
--------------------------------------------------------------------------------
一、核心思想对比
特性LeetCode 210 (Course Schedule II)LeetCode 2115 (Find All Possible Recipes)图的方向pre[1] → pre[0]（先修课 → 后续课）ingredient → recipe（原料 → 成品）目标输出一个合法的拓扑排序（所有课程都要排）输出所有能制作的 recipe（部分可能做不出）DFS 目的检测环 + 记录拓扑序检测环/不可制作 + 标记能否制作状态设计visited[] + recursionStack[]（两个 boolean 数组）state Map（一个 int：0/1/2/3）结果收集tmp.add(0, course) 逆序插入available.add(recipe) + 加入答案列表Backtrack 时机递归返回后 recursionStack[course] = false递归返回后根据子结果设置 state
--------------------------------------------------------------------------------
二、LeetCode 210 的 DFS 详解
状态设计
boolean[] visited = new boolean[numCourses];      // 全局：这个节点是否被彻底处理过
boolean[] recursionStack = new boolean[numCourses]; // 路径：当前 DFS 路径上是否正在访问
- visited：一旦某个节点的所有子节点都处理完了，设为 true。后续其他路径遇到它直接跳过，避免重复计算。
- recursionStack：标记当前 DFS 路径上的节点。如果沿着这条路径又遇到 recursionStack[course] == true，说明有环。
Backtrack 机制
recursionStack[course] = true;   // 进入：标记当前路径
for (int neighbour : graph.get(course)) {
    if (hasCycle(neighbour, ...)) return true;  // 子路径有环，直接返回
}
recursionStack[course] = false;  // 退出：撤销路径标记
tmp.add(0, course);              // 无环确认，逆序插入拓扑序
为什么 recursionStack[course] = false 是 backtrack？
想象 DFS 沿着路径 A → B → C 走：
- 进入 A：recursionStack[A] = true
- 进入 B：recursionStack[B] = true
- 进入 C：recursionStack[C] = true
- C 处理完，返回 B 之前：recursionStack[C] = false
此时如果 B 还有另一个邻居 D，路径变成 A → B → D。C 不在新路径上，所以 C 必须被"释放"。这就是 backtrack——撤销当前路径的状态，让其他路径可以重新使用这些节点。
但 visited[C] = true 保持不变，因为 C 的所有子节点已经处理完了，结果不会再变。
为什么 tmp.add(0, course) 是逆序？
DFS 是"先走到最深处，再返回"。对于拓扑排序，一个节点要排在它的所有后继之后。所以：
- 先递归处理所有邻居（后继课程）
- 邻居都处理完了，当前课程才"安全"地加入结果
- 每次插入头部，保证依赖关系：后继在前，前驱在后
A → B → C
DFS 顺序：C 先完成，然后 B，然后 A
tmp: [C] → [B, C] → [A, B, C]
--------------------------------------------------------------------------------
三、LeetCode 2115 的 DFS 详解（Solution 3）
状态设计（合并为一个 enum）
private static final int UNVISITED = 0;      // 从未访问
private static final int VISITING = 1;       // 当前 DFS 路径上（相当于 recursionStack）
private static final int CAN_MAKE = 2;       // 已确认可以制作（相当于 visited + 成功）
private static final int CANNOT_MAKE = 3;    // 已确认无法制作（相当于 visited + 失败）
为什么用一个 int 而不是两个 boolean？
因为 2115 的节点有三种最终状态：
- 还没算过（UNVISITED）
- 正在算（VISITING）→ 用于环检测
- 算完了：可以制作（CAN_MAKE）或 无法制作（CANNOT_MAKE）
而 210 只有两种最终状态：访问过/没访问过（有环就直接返回失败了，不需要记录"访问过但失败"）。
Backtrack 机制
state.put(recipe, VISITING);  // 进入：标记当前路径

for (String ing : recipeMap.get(recipe)) {
    int result = dfs(ing, ...);
    
    if (result == VISITING || result == CANNOT_MAKE) {
        state.put(recipe, CANNOT_MAKE);  // 子节点有环或做不出，当前也做不出
        return CANNOT_MAKE;
    }
}

// 所有子节点都能做出
state.put(recipe, CAN_MAKE);
available.add(recipe);  // 这个 recipe 现在也是可用原料了
return CAN_MAKE;
对比 210 的 backtrack：
LeetCode 210LeetCode 2115进入时recursionStack[course] = truestate.put(recipe, VISITING)递归子节点有环返回 true子节点 CANNOT_MAKE 或 VISITING，当前也失败返回前recursionStack[course] = false没有显式撤销 VISITING！最终状态visited[course] = true（隐含在检查中）直接设为 CAN_MAKE 或 CANNOT_MAKE
关键区别：2115 不需要显式撤销 VISITING！
为什么？因为 2115 的 state 一旦被设为 CAN_MAKE 或 CANNOT_MAKE，就永远不会再变回 VISITING。而在 210 中，recursionStack 必须在返回后撤销，因为同一个节点可能在另一条完全不同的 DFS 路径中被重新访问。
但在 2115 中，如果一个节点被标记为 CANNOT_MAKE，后续任何路径再遇到它，直接返回失败即可，不需要重新计算。所以不需要"撤销"——状态是累积的、单向的。
为什么 2115 的 available.add(recipe) 相当于拓扑序？
这和 210 的 tmp.add(0, course) 异曲同工：
- 210：课程 C 的所有先修课都排好了，C 才能加入结果
- 2115：recipe 的所有 ingredients 都确认可得了，这个 recipe 才能加入 available 集合
available 集合就是动态的"supplies 池"，随着 DFS 深入不断扩大。
--------------------------------------------------------------------------------
四、核心差异总结
LeetCode 210:
  DFS 目标：找出一个完整的拓扑排序
  状态：visited（全局缓存）+ recursionStack（路径检测，必须 backtrack）
  结果收集：逆序插入，所有节点都必须出现
  
LeetCode 2115:
  DFS 目标：判断每个 recipe 是否能做
  状态：4-state enum（合并了 visited + recursionStack + 成功/失败）
  结果收集：能做就加入 available，不需要记录完整顺序
  不需要显式 backtrack recursionStack，因为最终状态是永久的
--------------------------------------------------------------------------------
五、如果把 210 改造成 2115 的风格
为了让对比更清晰，这里展示如何用 2115 的"4-state"风格重写 210：
class Solution {
    private static final int UNVISITED = 0;
    private static final int VISITING = 1;
    private static final int SAFE = 2;      // 无环，已加入拓扑序
    private static final int CYCLIC = 3;    // 有环
    
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        Map<Integer, List<Integer>> graph = new HashMap<>();
        for (int i = 0; i < numCourses; i++) graph.put(i, new ArrayList<>());
        for (int[] pre : prerequisites) graph.get(pre[1]).add(pre[0]);
        
        int[] state = new int[numCourses];
        List<Integer> result = new ArrayList<>();
        
        for (int i = 0; i < numCourses; i++) {
            if (state[i] == UNVISITED) {
                if (dfs(i, graph, state, result) == CYCLIC) {
                    return new int[]{};
                }
            }
        }
        
        // result 已经是逆序的，因为每次 add(0, ...)
        return result.stream().mapToInt(Integer::intValue).toArray();
    }
    
    private int dfs(int course, Map<Integer, List<Integer>> graph, int[] state, List<Integer> result) {
        if (state[course] == VISITING) return CYCLIC;   // 遇到当前路径上的节点 = 环
        if (state[course] == SAFE) return SAFE;          // 已处理过，无环
        
        state[course] = VISITING;  // 进入路径
        
        for (int next : graph.get(course)) {
            if (dfs(next, graph, state, result) == CYCLIC) {
                return CYCLIC;  // 子路径有环，当前也失败
            }
        }
        
        // 无环，加入结果（逆序）
        state[course] = SAFE;      // 注意：这里直接设为 SAFE，不需要撤销 VISITING
        result.add(0, course);
        return SAFE;
    }
}
注意：这里 210 也不需要显式撤销 VISITING，因为一旦确认 SAFE，状态就永久不变了。原始 210 代码保留 recursionStack[course] = false 是为了清晰表达 backtrack 语义，实际上在最终版本中可以和 2115 一样省略（因为 state[course] = SAFE 覆盖了它）。
--------------------------------------------------------------------------------
六、一句话总结
210 的 recursionStack = false 是"路径回溯"，让节点能被其他路径重新访问；2115 不需要这个，因为一旦算出 CAN_MAKE/CANNOT_MAKE，结果永久有效，不存在"重新访问再计算"的需求。


Refer to
L210.P17.3.Course Schedule II (Ref.L207,L261)
L1711.Count Good Meals
