/**
 * Refer to
 * https://discuss.leetcode.com/topic/2975/o-n-solution-by-using-two-pointers-without-change-anything/17
 * My solution is like this: using two pointers, one of them one step at a time. 
 * another pointer each take two steps. Suppose the first meet at step k,the length of the Cycle is r. so..2k-k=nr,k=nr
 * Now, the distance between the start node of list and the start node of cycle is s. 
 * the distance between the start of list and the first meeting node is k(the pointer 
 * which wake one step at a time waked k steps).the distance between the start node of cycle 
 * and the first meeting node is m, so...s=k-m, s=nr-m, the 1st pointer starts from beginning of the list while 
 * the 2nd pointer starts from meet point, they will meet in the cycle point but the 2nd pointer walked n times of the cycle
*/
