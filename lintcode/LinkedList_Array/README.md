孟思：喜欢考array,喜欢考bfs, dfs
来自yelp经典的一题关于bfs寻找node之间的距离：
定义两个用户的距离来自是否有共同的评论，如果有共同餐厅评论，距离定义为1，如果两个用户拥有同一个朋友则定义为距离2。。。。
给定3个database table关于用户，餐厅，评论，如何设计API来寻找用户间的距离 ?
思路：把给定用户加入到queue中，通过用户table找到与此用户有相同餐厅评论的所有用户，也加入到queue中，在bfs中就是第二层，以此类推找到第三层，第四层。。。
这样层数基本就代表了用户间的距离


# Lintcode LinkedList and Array issues
<p>1. [Lintcode LinkedList and Array issues]

# Useful links for specific questions
<p>Merge Intervals
<p>1. [Template (Sort require first)] (http://www.jiuzhang.com/solutions/merge-intervals/)

<p>Insert Interval
<p>1. [Template (Intuitive solution as overlapping or not)] (http://www.jiuzhang.com/solutions/insert-interval/)

