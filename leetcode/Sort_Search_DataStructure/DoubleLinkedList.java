/******************************************************************************
 *  Refer to
 *  http://algs4.cs.princeton.edu/13stacks/DoublyLinkedList.java.html
 * 
 *  Compilation:  javac DoublyLinkedList.java
 *  Execution:    java DoublyLinkedList
 *  Dependencies: StdOut.java
 *
 *  A list implemented with a doubly linked list. The elements are stored
 *  (and iterated over) in the same order that they are inserted.
 * 
 *  % java DoublyLinkedList 10
 *  10 random integers between 0 and 99
 *  24 65 2 39 86 24 50 47 13 4 
 *
 *  add 1 to each element via next() and set()
 *  25 66 3 40 87 25 51 48 14 5 
 *
 *  multiply each element by 3 via previous() and set()
 *  75 198 9 120 261 75 153 144 42 15 
 *
 *  remove elements that are a multiple of 4 via next() and remove()
 *  75 198 9 261 75 153 42 15 
 *
 *  remove elements that are even via previous() and remove()
 *  75 9 261 75 153 15 
 *
 ******************************************************************************/
 
