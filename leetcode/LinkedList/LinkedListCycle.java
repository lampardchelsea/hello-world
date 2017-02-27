/**
 * Refer to
 * https://discuss.leetcode.com/topic/12516/o-1-space-solution/7
 * You can make use of Floyd's cycle-finding algorithm, also know as tortoise and hare algorithm. 
 * The idea is to have two references to the list and move them at different speeds. Move one 
 * forward by 1 node and the other by 2 nodes. If the linked list has a loop they will definitely meet.
 * 
 * (1) Use two pointers, walker and runner .
 * (2) walker moves step by step. runner m oves two steps at time.
 * (3) if the Linked List has a cycle walk er and runner will meet at some point.
 * 
 * Explain how finding cycle start node in cycle linked list work ?
 * Refer to
 * http://stackoverflow.com/questions/2936213/explain-how-finding-cycle-start-node-in-cycle-linked-list-work
*/

