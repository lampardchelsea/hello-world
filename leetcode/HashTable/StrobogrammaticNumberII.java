/**
 * A strobogrammatic number is a number that looks the same when rotated 180 degrees (looked at upside down).
 * Find all strobogrammatic numbers that are of length = n.
 * For example, Given n = 2, return ["11","69","88","96"].
 *
 * 中间插入法
 * 复杂度
 * 时间 O(N) 空间 O(1)
 * 思路
 * 找出所有的可能，必然是深度优先搜索。但是每轮搜索如何建立临时的字符串呢？因为数是“对称”的，
 * 我们插入一个字母就知道对应位置的另一个字母是什么，所以我们可以从中间插入来建立这个临时的字符串。
 * 这样每次从中间插入两个“对称”的字符，之前插入的就被挤到两边去了。这里有几个边界条件要考虑：
 * 如果是第一个字符，即临时字符串为空时进行插入时，不能插入'0'，因为没有0开头的数字
 * 如果n=1的话，第一个字符则可以是'0'
 * 如果只剩下一个带插入的字符，这时候不能插入'6'或'9'，因为他们不能和自己产生映射，翻转后就不是自己了
 * 这样，当深度优先搜索时遇到这些情况，则要相应的跳过
 * 注意
 * 为了实现从中间插入，我们用StringBuilder在length/2的位置insert就行了
*/
