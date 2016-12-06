/**
 * Design and implement a data structure for Least Recently Used (LRU) cache. 
 * It should support the following operations: get and set.
 * get(key) - Get the value (will always be positive) of the key if the key exists 
 * in the cache, otherwise return -1.
 * set(key, value) - Set or insert the value if the key is not already present. 
 * When the cache reached its capacity, it should invalidate the least recently 
 * used item before inserting a new item.
 *
 * HashMap related part:
 * http://coding-geek.com/how-does-a-hashmap-work-in-java/
 * http://yikun.github.io/2015/04/01/Java-HashMap%E5%B7%A5%E4%BD%9C%E5%8E%9F%E7%90%86%E5%8F%8A%E5%AE%9E%E7%8E%B0/
 * http://stackoverflow.com/questions/4553624/hashmap-get-put-complexity
 *
 * DoublyLinkedList related part:
 * https://github.com/lampardchelsea/hello-world/blob/master/leetcode/Sort_Search_DataStructure/DoublyLinkedList.java
 * 
 * Design part:
 * http://www.learn4master.com/data-structures/hashtable/leetcode-lru-cache-solution-in-java
 * http://www.cnblogs.com/springfor/p/3869393.html
 * https://segmentfault.com/a/1190000003743083
*/
