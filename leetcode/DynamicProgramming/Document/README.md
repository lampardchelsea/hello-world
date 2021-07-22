http://www.mathcs.emory.edu/~cheung/Courses/323/Syllabus/DynProg/

#Basic knapsack vs. Unbounded knapsack problem vs. complete knapsack problem
Have N Kinds of items and a maximum weightW Backpack. Firsti The weight of this item isw[i], The value isv[i]. 
Solve which items are loaded into the backpack so that the total weight of these items does not exceed the 
capacity of the backpack, and the total value is the largest.

The Knapsack problem is a NP-complete problem of combinatorial optimization. Given a set of items, each item 
has its own weight and price. Within the limited total weight, how do we choose to make the total price of 
the item the highest.

If only 0 or 1 of each item can be selected, the problem is called0-1 backpack problem 。
If the item is restrictedj Can only choose at mostmaxj , The problem is calledBounded knapsack problem。
If you don’t limit the quantity of each item, you can choose any number of each item, then the problem is 
calledUnbounded knapsack problem or complete knapsack problem。

Refer to https://www.programmersought.com/article/63586116042/
