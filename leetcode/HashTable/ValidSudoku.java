/**
 * Determine if a Sudoku is valid, according to: 
 * Sudoku Puzzles - The Rules.
 * http://sudoku.com.au/TheRules.aspx
 * The Sudoku board could be partially filled, where empty cells are filled with the character '.'.
 * A partially filled sudoku which is valid.
 * Note:
 * A valid Sudoku board (partially filled) is not necessarily solvable. Only the filled cells need to be validated.
 * Subscribe to see which companies asked this question
 * 
 * Analyze
 *  这道题利用的是HashSet的唯一性来帮助check。
 * 先按每行check，如果是'.'说明还没填字，是合法的，往下走，如果没在set中存过就加一下，
 * 如果便利过程中出现了在set中存在的key值，说明有重复的数字在一行，不合法，return false。
 * 再按照这个方法check列。
 * 最后按照这个方法check小方块。
 * 注意小方块的ij取法。对于当前这块板子来说，总共有9个小方格，按0~8从左到右依次编号。
 * 按编号求'/'就是求得当前小方格的第一行横坐标，因为每个小方格有3行，所以循环3次。
 * 按编号求'%'就是求得当前小方格的第一列纵坐标，因为每个小方格有3列，所以循环3次。 
 * 对9个小方格依次走一边，就完成了检查小方格的工作。
*/
