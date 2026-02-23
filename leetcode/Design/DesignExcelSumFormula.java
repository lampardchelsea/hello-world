https://leetcode.ca/2017-08-22-631-Design-Excel-Sum-Formula/
Your task is to design the basic function of Excel and implement the function of sum formula. Specifically, you need to implement the following functions:
Excel(int H, char W): This is the constructor. The inputs represents the height and width of the Excel form. H is a positive integer, range from 1 to 26. It represents the height. W is a character range from ‘A’ to ‘Z’. It represents that the width is the number of characters from ‘A’ to W. The Excel form content is represented by a height * width 2D integer array C, it should be initialized to zero. You should assume that the first row of C starts from 1, and the first column of C starts from ‘A’.
void Set(int row, char column, int val): Change the value at C(row, column) to be val.
int Get(int row, char column): Return the value at C(row, column).
int Sum(int row, char column, List of Strings : numbers): This function calculate and set the value at C(row, column), where the value should be the sum of cells represented by numbers. This function return the sum result at C(row, column). This sum formula should exist until this cell is overlapped by another value or another sum formula.
numbers is a list of strings that each string represent a cell or a range of cells. If the string represent a single cell, then it has the following format: ColRow. For example, “F7” represents the cell at (7, F).
If the string represent a range of cells, then it has the following format: ColRow1:ColRow2. The range will always be a rectangle, and ColRow1 represent the position of the top-left cell, and ColRow2 represents the position of the bottom-right cell.
Example 1:
Excel(3,"C"); 
// construct a 3*3 2D array with all zero.
//   A B C
// 1 0 0 0
// 2 0 0 0
// 3 0 0 0

Set(1, "A", 2);
// set C(1,"A") to be 2.
//   A B C
// 1 2 0 0
// 2 0 0 0
// 3 0 0 0

Sum(3, "C", ["A1", "A1:B2"]);
// set C(3,"C") to be the sum of value at C(1,"A") and the values sum of the rectangle range 
// whose top-left cell is C(1,"A") and bottom-right cell is C(2,"B"). Return 4. 
//   A B C
// 1 2 0 0
// 2 0 0 0
// 3 0 0 4

Set(2, "B", 2);
// set C(2,"B") to be 2. Note C(3, "C") should also be changed.
//   A B Cf
// 1 2 0 0
// 2 0 2 0
// 3 0 0 6
Note:
1.You could assume that there won’t be any circular sum reference. For example, A1 = sum(B1) and B1 = sum(A1).
2.The test cases are using double-quotes to represent a character.
3.Please remember to RESET your class variables declared in class Excel, as static/class variables are persisted across multiple test cases. Please see here for more details.
--------------------------------------------------------------------------------
Attempt 1: 2026-02-14
Solution 1: Design + Harsh Table + DFS (180 min)
import java.util.*;
public class Excel {
    // 单元格的内部表示
    class Cell {
    int val; // 当前值
    Map<Integer, Integer> formula; // 公式依赖: key是单元格ID(row*26+col)，value是出现次数
        Cell(int val) {
            this.val = val;
            this.formula = new HashMap<>();
        }
    }
    
    private Cell[][] sheet;
    private int height;
    private int width;
    // 反向依赖图: key是单元格ID，value是依赖于它的所有单元格ID集合
    private Map<Integer, Set<Integer>> reverseGraph;
    
    public Excel(int height, char width) {
        this.height = height;
        this.width = width - 'A' + 1; // 将字符列转换为整数列数
        sheet = new Cell[height][this.width];
        reverseGraph = new HashMap<>();
    
        // 初始化所有单元格，值为0
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < this.width; j++) {
                sheet[i][j] = new Cell(0);
            }
        }
    }
    
    public void set(int row, char column, int val) {
        int r = row - 1; // 转换为0-based索引
        int c = column - 'A';
        Cell cell = sheet[r][c];
    
        // 如果这个单元格之前有公式，需要从反向图中移除它
        if (!cell.formula.isEmpty()) {
            removeFromReverseGraph(r, c);
            cell.formula.clear();
        }
    
        // 计算变化值并传播
        int delta = val - cell.val;
        if (delta != 0) {
            cell.val = val;
            propagateChange(r, c, delta);
        }
    }
    
    public int get(int row, char column) {
        int r = row - 1;
        int c = column - 'A';
        return sheet[r][c].val;
    }
    
    public int sum(int row, char column, List<String> numbers) {
        int r = row - 1;
        int c = column - 'A';
        Cell cell = sheet[r][c];
    
        // 如果这个单元格之前有公式，从反向图中移除
        if (!cell.formula.isEmpty()) {
            removeFromReverseGraph(r, c);
            cell.formula.clear();
        }
    
        // 解析公式，构建新的依赖关系
        Map<Integer, Integer> newFormula = new HashMap<>();
        for (String num : numbers) {
            if (num.contains(":")) {
                // 处理范围: "A1:B2"
                String[] parts = num.split(":");
                int[] topLeft = parsePos(parts[0]);
                int[] bottomRight = parsePos(parts[1]);
    
                for (int i = topLeft[0]; i <= bottomRight[0]; i++) {
                    for (int j = topLeft[1]; j <= bottomRight[1]; j++) {
                        int id = i * 26 + j;
                        newFormula.put(id, newFormula.getOrDefault(id, 0) + 1);
                    }
                }
            } else {
                // 处理单个单元格: "A1"
                int[] pos = parsePos(num);
                int id = pos[0] * 26 + pos[1];
                newFormula.put(id, newFormula.getOrDefault(id, 0) + 1);
            }
        }
    
        cell.formula = newFormula;
    
        // 更新反向图：让当前单元格依赖于公式中的每个单元格
        for (int id : newFormula.keySet()) {
            reverseGraph.computeIfAbsent(id, k -> new HashSet<>()).add(r * 26 + c);
        }
    
        // 计算初始值
        int sum = 0;
        for (Map.Entry<Integer, Integer> entry : newFormula.entrySet()) {
            int id = entry.getKey();
            int count = entry.getValue();
            int[] pos = {id / 26, id % 26};
            sum += sheet[pos[0]][pos[1]].val * count;
        }
    
        // 更新当前单元格的值并传播变化
        int delta = sum - cell.val;
        cell.val = sum;
        if (delta != 0) {
            propagateChange(r, c, delta);
        }
    
        return cell.val;
    }
    
    // 解析单元格位置如 "A1" 为 [row, col] (0-based)
    private int[] parsePos(String pos) {
        int col = pos.charAt(0) - 'A';
        int row = Integer.parseInt(pos.substring(1)) - 1;
        return new int[]{row, col};
    }
    
    // 从反向图中移除当前单元格的所有依赖
    private void removeFromReverseGraph(int r, int c) {
        int id = r * 26 + c;
        // 这个单元格依赖的所有单元格
        Set<Integer> deps = sheet[r][c].formula.keySet();
        for (int depId : deps) {
            Set<Integer> dependents = reverseGraph.get(depId);
            if (dependents != null) {
                dependents.remove(id);
            }
        }
    }
    
    // 传播变化: 当(r,c)的值变化了delta，更新所有依赖于它的单元格
    private void propagateChange(int r, int c, int delta) {
        int id = r * 26 + c;
        Set<Integer> dependents = reverseGraph.get(id);
        if (dependents == null) return;
    
        // 遍历所有依赖于当前单元格的单元格
        for (int dependentId : new HashSet<>(dependents)) {
            int dr = dependentId / 26;
            int dc = dependentId % 26;
            Cell dependentCell = sheet[dr][dc];
    
            // 该单元格在公式中出现的次数
            int count = dependentCell.formula.getOrDefault(id, 0);
    
            // 更新值
            dependentCell.val += delta * count;
    
            // 递归传播
            propagateChange(dr, dc, delta * count);
        }
    }
    
    public static void main(String[] args) {
        Excel excel = new Excel(3, 'C');
        excel.set(1, 'A', 2);
        List<String> numbers = new ArrayList<>();
        numbers.add("A1");
        numbers.add("A1:B2");
        // 加入下面一句输入方便观察被用于计算加权的 “int count = dependentCell.formula.getOrDefault(id, 0);”
        // 这句话的结果 count 大于1 的情况，原因是在最后的 “excel.set(2, 'B', 2);” 执行后,
        // sheet[1][1] 的值的更新变动会被计算 “int result = excel.sum(3, 'C', numbers);”
        // 中的2个范围 ("A1:B2", "A1:B3") 同时引用, 而造成 count = 2 的最初原因是在构造
        // sheet[2][2] 的 formula 的过程中形成了对 sheet[1][1] 的依赖关系为 {id, count} = {27, 2}
        numbers.add("A1:B3");
        int result = excel.sum(3, 'C', numbers);
        excel.set(2, 'B', 2);
    }
}

Refer to Deepseek
这道题是 LeetCode 上一道 困难 级别的设计题，要求我们实现一个简化版的 Excel，核心难点在于支持求和公式的动态更新：当公式引用的单元格发生变化时，公式所在单元格的值也要自动更新。
题目链接：631. Design Excel Sum Formula
核心数据结构与算法设计
1.单元格存储：每个单元格需要存储两个信息：
- val：当前的实际数值。
- formula：如果该单元格是由公式定义的，需要存储一个 Map，键是它引用的单元格坐标，值是该单元格在公式中出现的次数（因为同一个单元格可能在范围引用中被重复计算）。
2.公式解析与依赖反转：
- 在 sum 操作中，我们需要解析输入的字符串列表，将单个单元格 "A1" 或范围 "A1:B2" 展开为具体的单元格坐标，并统计每个坐标出现的次数。
- 为了在 set 操作时能级联更新所有依赖于它的公式单元格，我们需要维护一个反向依赖图。即，每个单元格需要知道有哪些公式单元格引用了它。当它的值改变时，通知这些依赖者更新。
3.动态更新机制：
- 当通过 set 修改一个普通单元格的值时，计算其新旧值的差值（delta）。
- 遍历所有依赖于该单元格的公式单元格，将它们的值加上 delta * 该单元格在公式中出现的次数。
- 然后，这些公式单元格作为新的依赖源，继续向下传播这个变化。这是一个深度优先搜索（DFS）或递归的过程。
Java 解决方案
import java.util.*;

public class Excel {
    // 单元格的内部表示
    class Cell {
        int val; // 当前值
        Map<Integer, Integer> formula; // 公式依赖: key是单元格ID(row*26+col)，value是出现次数
        
        Cell(int val) {
            this.val = val;
            this.formula = new HashMap<>();
        }
    }
    
    private Cell[][] sheet;
    private int height;
    private int width;
    // 反向依赖图: key是单元格ID，value是依赖于它的所有单元格ID集合
    private Map<Integer, Set<Integer>> reverseGraph;
    
    public Excel(int height, char width) {
        this.height = height;
        this.width = width - 'A' + 1; // 将字符列转换为整数列数
        sheet = new Cell[height][this.width];
        reverseGraph = new HashMap<>();
        
        // 初始化所有单元格，值为0
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < this.width; j++) {
                sheet[i][j] = new Cell(0);
            }
        }
    }
    
    public void set(int row, char column, int val) {
        int r = row - 1; // 转换为0-based索引
        int c = column - 'A';
        Cell cell = sheet[r][c];
        
        // 如果这个单元格之前有公式，需要从反向图中移除它
        if (!cell.formula.isEmpty()) {
            removeFromReverseGraph(r, c);
            cell.formula.clear();
        }
        
        // 计算变化值并传播
        int delta = val - cell.val;
        if (delta != 0) {
            cell.val = val;
            propagateChange(r, c, delta);
        }
    }
    
    public int get(int row, char column) {
        int r = row - 1;
        int c = column - 'A';
        return sheet[r][c].val;
    }
    
    public int sum(int row, char column, List<String> numbers) {
        int r = row - 1;
        int c = column - 'A';
        Cell cell = sheet[r][c];
        
        // 如果这个单元格之前有公式，从反向图中移除
        if (!cell.formula.isEmpty()) {
            removeFromReverseGraph(r, c);
            cell.formula.clear();
        }
        
        // 解析公式，构建新的依赖关系
        Map<Integer, Integer> newFormula = new HashMap<>();
        for (String num : numbers) {
            if (num.contains(":")) {
                // 处理范围: "A1:B2"
                String[] parts = num.split(":");
                int[] topLeft = parsePos(parts[0]);
                int[] bottomRight = parsePos(parts[1]);
                
                for (int i = topLeft[0]; i <= bottomRight[0]; i++) {
                    for (int j = topLeft[1]; j <= bottomRight[1]; j++) {
                        int id = i * 26 + j;
                        newFormula.put(id, newFormula.getOrDefault(id, 0) + 1);
                    }
                }
            } else {
                // 处理单个单元格: "A1"
                int[] pos = parsePos(num);
                int id = pos[0] * 26 + pos[1];
                newFormula.put(id, newFormula.getOrDefault(id, 0) + 1);
            }
        }
        
        cell.formula = newFormula;
        
        // 更新反向图：让当前单元格依赖于公式中的每个单元格
        // 反向图 reverseGraph 的设计目的是：当某个单元格（被依赖者）的值发生变化时，
        // 我们需要快速找到所有依赖于它的单元格（依赖者），以便更新它们。
        // 所以 reverseGraph 的键应该是被依赖的单元格，值应该是依赖于它的单元格集合。
        // 当前单元格（例如 C3）是一个公式单元格，它的值依赖于其他单元格（比如 A1 和 B2）。
        // 为了在 A1 或 B2 发生变化时能够自动更新 C3，我们需要维护一个反向依赖图：
        // 以被依赖的单元格（如 A1）为键，存储所有依赖于它的单元格（如 C3）的集合。
        for (int id : newFormula.keySet()) {
            reverseGraph.computeIfAbsent(id, k -> new HashSet<>()).add(r * 26 + c);
        }
        
        // 计算初始值
        int sum = 0;
        for (Map.Entry<Integer, Integer> entry : newFormula.entrySet()) {
            int id = entry.getKey();
            int count = entry.getValue();
            int[] pos = {id / 26, id % 26};
            sum += sheet[pos[0]][pos[1]].val * count;
        }
        
        // 更新当前单元格的值并传播变化
        int delta = sum - cell.val;
        cell.val = sum;
        if (delta != 0) {
            propagateChange(r, c, delta);
        }
        
        return cell.val;
    }
    
    // 解析单元格位置如 "A1" 为 [row, col] (0-based)
    private int[] parsePos(String pos) {
        int col = pos.charAt(0) - 'A';
        int row = Integer.parseInt(pos.substring(1)) - 1;
        return new int[]{row, col};
    }
    
    // 从反向图中移除当前单元格的所有依赖
    private void removeFromReverseGraph(int r, int c) {
        int id = r * 26 + c;
        // 这个单元格依赖的所有单元格
        Set<Integer> deps = sheet[r][c].formula.keySet();
        for (int depId : deps) {
            Set<Integer> dependents = reverseGraph.get(depId);
            if (dependents != null) {
                dependents.remove(id);
            }
        }
    }
    
    // 传播变化: 当(r,c)的值变化了delta，更新所有依赖于它的单元格
    private void propagateChange(int r, int c, int delta) {
        int id = r * 26 + c;
        Set<Integer> dependents = reverseGraph.get(id);
        if (dependents == null) return;
        
        // 遍历所有依赖于当前单元格的单元格
        for (int dependentId : new HashSet<>(dependents)) {
            int dr = dependentId / 26;
            int dc = dependentId % 26;
            Cell dependentCell = sheet[dr][dc];
            
            // 该单元格在公式中出现的次数
            int count = dependentCell.formula.getOrDefault(id, 0);
            
            // 更新值
            dependentCell.val += delta * count;
            
            // 递归传播
            propagateChange(dr, dc, delta * count);
        }
    }
}
核心逻辑详解
1.公式存储 (Cell 类)：
- 每个单元格存储一个 Map<Integer, Integer> 作为它的公式。键是它依赖的单元格的 ID（row * 26 + col 编码），值是该单元格在求和公式中出现的次数。如果这个 Map 为空，说明它是一个普通单元格。
2.反向依赖图 (reverseGraph)：
- 这是一个 Map<Integer, Set<Integer>>，键是一个单元格的 ID，值是一个 Set，包含所有依赖于这个单元格的公式单元格的 ID。
- 当通过 set 修改 (1, 'A') 时，我们可以通过 reverseGraph.get(id_of_A1) 快速找到所有需要更新的公式单元格（如 (3, 'C')）。
3.sum 方法流程：
- 清理旧依赖：如果目标单元格之前有公式，需要从 reverseGraph 中清除它建立的旧依赖关系。
- 解析新公式：遍历 numbers 列表，解析单个单元格或范围，统计每个被引用单元格的出现次数，存入 newFormula。
- 建立新依赖：将当前单元格添加到它所依赖的每一个单元格的 reverseGraph 条目中。
- 计算初始值：遍历 newFormula，从对应的单元格中取出当前值，乘以出现次数，求和。
- 更新并传播：设置当前单元格的值为计算出的和。如果新旧值有差异，需要调用 propagateChange 将差异传播给依赖于它的其他公式单元格。
4.set 方法流程：
- 如果目标单元格是公式单元格，需要先清理其依赖关系。
- 计算 delta = 新值 - 旧值。
- 更新目标单元格的值。
- 调用 propagateChange，将 delta 传播给所有依赖于它的公式单元格。
5.propagateChange 方法（DFS 传播）：
- 给定一个发生变化的单元格 (r, c) 及其变化值 delta。
- 从 reverseGraph 中找出所有依赖于它的单元格。
- 对于每一个依赖单元格，根据它在公式中引用 (r, c) 的次数 count，将其值增加 delta * count。
- 然后，递归地调用 propagateChange，将这些依赖单元格的变化继续向下传播。
示例运行
按照题目示例：
1.new Excel(3, "C")：初始化一个 3x3 的表格。
2.set(1, "A", 2)：单元格 A1 的值变为 2。此时没有依赖，无传播。
3.sum(3, "C", ["A1", "A1:B2"])：
- 解析公式：A1 出现 2 次（一次单独，一次在范围内），A2、B1、B2 各出现 1 次。
- 初始和 = A1*2 + A2*1 + B1*1 + B2*1 = 2*2 + 0 = 4。C3 的值设为 4。
- 反向图更新：A1、A2、B1、B2 的依赖集合中都加入 C3。
4.set(2, "B", 2)：
- 单元格 B2 的值从 0 变为 2，delta = 2。
- 查找依赖于 B2 的单元格，发现 C3。
- C3 的公式中，B2 出现 1 次，因此 C3 的值增加 2 * 1 = 2，从 4 变为 6。
- C3 变化了 2，再查找依赖于 C3 的单元格（这里没有），传播结束。
5.get(3, "C")：返回 6，符合预期。
复杂度分析
- 时间复杂度：set 和 sum 操作在最坏情况下可能需要 O(R*C) 的时间来遍历整个依赖链，其中 R 是行数，C 是列数。但由于题目限制最多 100 次调用，且单元格数量有限，这是可以接受的 。
- 空间复杂度：O(R*C) 用于存储表格和反向依赖图。

Q1：Can you explain why we do below ?
// 如果这个单元格之前有公式，需要从反向图中移除它
if (!cell.formula.isEmpty()) {
    removeFromReverseGraph(r, c);
    cell.formula.clear();
}
我们被问到：在 set 方法中，为什么要检查并清除单元格之前的公式？并调用 removeFromReverseGraph 清除其在反向图中的依赖关系？
我们需要解释这个逻辑的必要性。用户可能是在理解代码时看到这段，想知道为什么这么做。
回答应该包括：
1.背景：每个单元格可能有两种状态：普通值（无公式）或公式单元格（由 sum 定义，依赖其他单元格）。公式单元格的值由依赖决定，并且被记录在反向图中，以便当依赖改变时它会被更新。
2.当 set 直接设置一个单元格的值时，意味着我们想让这个单元格变成一个普通值单元格，不再由公式驱动。因此必须：a) 清除它原有的公式定义（如果有的话），因为它现在是一个直接赋值的单元格。b) 更重要的是，它之前作为公式单元格，可能依赖于其他单元格（即它在反向图中是被依赖者）。但这里“从反向图中移除它”是指清除它作为依赖者所建立的链接，即它依赖于其他单元格的关系。注意代码中的 removeFromReverseGraph 是从 reverseGraph 中删除当前单元格对它所依赖的单元格的引用。因为 reverseGraph 是以被依赖者为键，存储依赖者。所以 removeFromReverseGraph 会遍历当前单元格的 formula 中所有它依赖的单元格（depId），从这些 depId 的依赖者集合中移除当前单元格的 id。这是必要的，因为当前单元格不再由公式定义，它不再需要跟踪这些依赖关系了。c) 同时，需要清除 cell.formula，因为它现在是普通单元格。
3.如果不清除旧的公式和反向图，之后如果这些被依赖的单元格发生变化，仍然会错误地尝试更新当前单元格（因为它还在反向图中），但当前单元格的值现在是一个直接赋值，不应该被自动更新。这会导致数据不一致。
4.同理，sum 方法在重新定义公式时，也会先清除旧的依赖关系，然后再建立新的。
需要解释清楚反向图的含义：键是被依赖单元格ID，值是该单元格的所有依赖者（即公式单元格）。所以当我们要让一个单元格变为普通值时，需要从所有它依赖的单元格的依赖者列表中移除它。
还要注意，当单元格之前是普通值时，它的 formula 为空，所以不会进入这个 if 分支。
在 set 方法中，当我们直接给一个单元格赋值时，这个单元格就不再是一个由公式定义的单元格，而变成了一个普通的数值单元格。因此，我们必须清除它之前可能存在的公式定义及其在依赖关系网络中的记录。
为什么需要这么做？
1.单元格的双重身份
- 一个单元格可能通过 sum 被设为公式单元格，它内部保存了一个 formula 映射，记录了它依赖哪些其他单元格（例如 A1 出现几次）。
- 同时，反向图 reverseGraph 记录了每个被依赖的单元格（键）被哪些公式单元格（值）所依赖。这样，当被依赖单元格的值改变时，可以快速通知所有依赖它的公式单元格更新。
2.set 操作意味着身份的转变
当用户调用 set(row, column, val) 时，我们希望该单元格从此以后是一个普通单元格，其值固定为 val，不再随其他单元格的变化而自动更新。因此：
- 清除自身的公式：cell.formula.clear() 移除了它与其他单元格的依赖关系（它依赖别人）。
- 从反向图中移除自身：removeFromReverseGraph(r, c) 会遍历它原先依赖的所有单元格，从这些被依赖单元格的“依赖者列表”中删除当前单元格。这样，将来那些被依赖单元格发生变化时，就不会再错误地尝试更新这个已经变成普通值的单元格。
3.如果不清理，会导致数据不一致
假设单元格 C3 原来是公式 =A1+B2，它依赖 A1 和 B2。现在通过 set 把 C3 改为固定值 5。如果不清理反向图，那么之后 A1 的值变化时，系统仍会按照反向图中的记录去更新 C3，从而把 C3 的值又改回一个根据公式计算的结果，这与 set 的意图完全违背。
总结
- cell.formula 记录了当前单元格依赖谁（出边）
- reverseGraph 记录了当前单元格被谁依赖（入边）
- 当通过 set 把一个公式单元格变为普通单元格时，必须切断这两类边，确保它不再参与任何自动更新链条。
- 同样，在 sum 方法重新定义公式时，也会先调用相同的清理逻辑，再建立新的依赖关系。

Q2：In which situation (or during what input) below statement result 'count' will be more than 1 ?
int count = dependentCell.formula.getOrDefault(id, 0);
在 propagateChange 方法中，count 表示被依赖的单元格（即发生变化的那个单元格）在依赖它的公式单元格中出现的次数。这个次数大于 1 的情况，通常发生在以下场景：
1. 公式列表中显式重复引用同一个单元格
例如，sum(3, "C", ["A1", "A1", "B2"]) 中，A1 被列出了两次，那么它在公式中的计数就是 2。
2. 单个单元格引用与范围引用重叠
如题目示例 ["A1", "A1:B2"]：
- 范围 "A1:B2" 已经包含了 A1。
- 再加上单独的 "A1"，导致 A1 总共出现两次。
- 因此，当 A1 变化时，delta 需要乘以 2 才能正确更新公式单元格。
3. 多个范围重叠
如果公式是 ["A1:B2", "A1:C3"]，那么 A1 同时出现在两个范围中，计数就会增加。具体次数取决于重叠情况。
4. 同一个单元格在同一个范围中被多次计数？
实际上，一个范围定义（如 "A1:B2"）中的每个单元格只会出现一次。但多个范围或显式引用可以导致重复计数。
为什么需要这样设计？
因为公式求和是累加每个引用的值，如果同一个单元格被多次引用，它的值就应该被多次计入总和。所以当该单元格的值变化时，变化量必须乘以引用次数，才能正确更新公式单元格的值。
示例验证
在 LeetCode 631 的题目示例中，C3 的公式 ["A1", "A1:B2"] 导致 A1 的计数为 2。当 A1 的值从 2 变为 3（delta = 1）时，C3 应该增加 1 * 2 = 2，从 4 变为 6。这正是 propagateChange 中 count 的作用。

Refer to QWEN
Here's an efficient and well-commented Java solution for LeetCode 631: Design Excel Sum Formula:
import java.util.*;

class Excel {
    private int height;
    private int width; // number of columns (A=0, B=1, ..., Z=25)
    private int[][] grid; // current computed values for each cell
    
    // formulas: cellKey -> list of referenced cell keys (flattened references)
    private Map<String, List<String>> formulas;
    
    // dependents: for each cell X, which cells depend on X (i.e., have formulas referencing X)
    private Map<String, Set<String>> dependents;
    
    public Excel(int height, char widthChar) {
        this.height = height;
        this.width = widthChar - 'A' + 1;
        this.grid = new int[height][width];
        this.formulas = new HashMap<>();
        this.dependents = new HashMap<>();
    }
    
    // Helper: Convert (row, col) to string key like "A1"
    private String key(int row, char col) {
        return col + String.valueOf(row);
    }
    
    // Parse range notation "A1:B2" into list of individual cell keys
    private List<String> parseRange(String range) {
        List<String> cells = new ArrayList<>();
        
        if (!range.contains(":")) {
            // Single cell reference (e.g., "A1")
            cells.add(range);
            return cells;
        }
        
        // Range reference (e.g., "A1:C3")
        String[] parts = range.split(":");
        String start = parts[0];
        String end = parts[1];
        
        char startCol = start.charAt(0);
        int startRow = Integer.parseInt(start.substring(1));
        char endCol = end.charAt(0);
        int endRow = Integer.parseInt(end.substring(1));
        
        // Generate all cells in the rectangular range (inclusive)
        for (char c = startCol; c <= endCol; c++) {
            for (int r = startRow; r <= endRow; r++) {
                cells.add(key(r, c));
            }
        }
        return cells;
    }
    
    public void set(int row, char column, int val) {
        String cell = key(row, column);
        
        // Clear any existing formula for this cell
        if (formulas.containsKey(cell)) {
            removeFormula(cell);
        }
        
        // Update value and propagate changes through dependency graph
        updateValue(cell, val);
    }
    
    // Remove formula and clean up dependency graph
    private void removeFormula(String cell) {
        List<String> refs = formulas.get(cell);
        for (String ref : refs) {
            Set<String> deps = dependents.get(ref);
            if (deps != null) {
                deps.remove(cell);
                // Optional cleanup: remove empty dependency sets
                if (deps.isEmpty()) {
                    dependents.remove(ref);
                }
            }
        }
        formulas.remove(cell);
    }
    
    // Update cell value and propagate changes to all dependents using BFS
    private void updateValue(String cell, int newVal) {
        int row = Integer.parseInt(cell.substring(1));
        char colChar = cell.charAt(0);
        int col = colChar - 'A';
        int oldVal = grid[row - 1][col];
        
        // No change needed
        if (oldVal == newVal) return;
        
        grid[row - 1][col] = newVal;
        
        // BFS to propagate changes through dependency graph
        Queue<String> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        
        // Start with direct dependents of this cell
        Set<String> directDeps = dependents.getOrDefault(cell, new HashSet<>());
        for (String dep : directDeps) {
            if (visited.add(dep)) {
                queue.offer(dep);
            }
        }
        
        while (!queue.isEmpty()) {
            String cur = queue.poll();
            
            // Skip non-formula cells (shouldn't happen, but defensive)
            if (!formulas.containsKey(cur)) continue;
            
            // Recalculate current cell's value based on its formula
            List<String> refs = formulas.get(cur);
            int sum = 0;
            for (String ref : refs) {
                int r = Integer.parseInt(ref.substring(1));
                char c = ref.charAt(0);
                sum += grid[r - 1][c - 'A'];
            }
            
            // Update only if value actually changed
            int curRow = Integer.parseInt(cur.substring(1));
            int curCol = cur.charAt(0) - 'A';
            int oldCurVal = grid[curRow - 1][curCol];
            
            if (sum != oldCurVal) {
                grid[curRow - 1][curCol] = sum;
                
                // Enqueue dependents of this recalculated cell
                Set<String> curDeps = dependents.getOrDefault(cur, new HashSet<>());
                for (String nextDep : curDeps) {
                    if (visited.add(nextDep)) {
                        queue.offer(nextDep);
                    }
                }
            }
        }
    }
    
    public int get(int row, char column) {
        return grid[row - 1][column - 'A'];
    }
    
    public int sum(int row, char column, String[] numbers) {
        String cell = key(row, column);
        
        // Remove existing formula if this cell already has one
        if (formulas.containsKey(cell)) {
            removeFormula(cell);
        }
        
        // Flatten all references (handle both single cells and ranges)
        List<String> allRefs = new ArrayList<>();
        for (String num : numbers) {
            allRefs.addAll(parseRange(num));
        }
        
        // Store the formula (flattened references for efficient recalculation)
        formulas.put(cell, allRefs);
        
        // Update dependency graph: this cell depends on all referenced cells
        for (String ref : allRefs) {
            dependents.computeIfAbsent(ref, k -> new HashSet<>()).add(cell);
        }
        
        // Calculate initial sum based on current values
        int sum = 0;
        for (String ref : allRefs) {
            int r = Integer.parseInt(ref.substring(1));
            char c = ref.charAt(0);
            sum += grid[r - 1][c - 'A'];
        }
        
        // Update cell value (will trigger propagation if needed)
        updateValue(cell, sum);
        
        return sum;
    }
}
Key Design Insights:
1.Dependency Tracking:
- formulas: Maps each formula cell → list of cells it references (flattened ranges)
- dependents: Maps each cell → set of cells that depend on it (reverse graph for propagation)
2.Efficient Propagation:
- When a cell changes, use BFS to recalculate all affected dependents
- Only propagate when values actually change (avoid unnecessary work)
- Flattened references enable O(1) per-reference lookup during recalculation
3.Formula Management:
- Overwriting a formula cell (via set() or new sum()) automatically clears old dependencies
- Range parsing handles both single cells ("A1") and rectangular ranges ("A1:C3")
4.No Cycle Handling Needed:
- Problem guarantees no circular dependencies in test cases
- BFS propagation naturally terminates without cycles
Example Walkthrough:
Excel excel = new Excel(3, 'C'); // 3 rows, columns A-C

excel.set(1, 'A', 2);    // A1 = 2
excel.set(1, 'B', 3);    // B1 = 3
excel.sum(1, 'C', new String[]{"A1:B1"}); // C1 = A1+B1 = 5

excel.set(1, 'A', 5);    // A1 = 5 → triggers recalc: C1 = 5+3 = 8
excel.get(1, 'C');       // Returns 8

excel.sum(2, 'C', new String[]{"A1:A1", "C1:C1"}); // C2 = A1 + C1 = 5 + 8 = 13
excel.set(1, 'B', 10);   // B1 = 10 → C1 = 5+10=15 → C2 = 5+15=20
excel.get(2, 'C');       // Returns 20

Complexity Analysis:
OperationTime ComplexitySpace ComplexityConstructorO(1)O(H×W) for gridset()O(D) where D = number of affected dependentsO(1)get()O(1)O(1)sum()O(R + D) where R = referenced cells, D = affected dependentsO(R) for formula storage
- H = height (≤ 26), W = width (≤ 26)
- Worst-case propagation visits all cells (O(H×W) = O(676)), which is efficient for constraints
Edge Cases Handled:
✅ Overwriting formula cells with set() clears dependencies
✅ Overwriting formula with new sum() replaces old formula cleanly
✅ Range parsing handles single cells, single-row/column ranges, and full rectangles
✅ Value unchanged → no propagation (optimization)
✅ Multiple references to same cell in formula (e.g., ["A1", "A1"]) handled correctly
This solution efficiently models Excel's dependency tracking and recalculation behavior while handling all problem constraints correctly. 📊
