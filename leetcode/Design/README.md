# Leetcode Design issues
https://leetcode.com/tag/design

# DDIA
https://github.com/lampardchelsea/ddia

# System Design Primer
https://github.com/lampardchelsea/system-design-primer

System Design
一亩三分地强调了无数遍的DDIA，确实有用，值得精读，如果时间有限，推荐精度第2章的data model部分和第5，6，7，9章，略读10，11章
有了这些基础知识，再结合一些design例题，差不多就能应付比较初级的design了，例题可以去System design primer(github)上找，也可以看youtube
需要注意的是，primer里总会讲到什么CDN，load balancer等各种fancy的部件，甚至还有前端怎么设计等等，然而楼主面了8场design，没有一个是让我设计这么high level，从前端到后端到DB的大系统，更多的是给定一个非常实际的应用问题，然后设计后端的service以及data model，比如一道经典题目：如何query全站过去5分钟内最popular的posts，如果是一小时，一整天呢？要考虑如何存数据才能更快的索引，如何query DB才能不overload，是online service还是offline等，根本不会问你前端怎么搞，CDN怎么能让系统变快，另外还多人提到估算数据量或者QPS，我觉得估算出是GB还是TB还是PB级别的数据量就够了，不需要算的很准确，更不需要根据你设计的表来算每个field是多少byte
当然，design没有边界，花10小时准备和花1000小时准备肯定不一样，以上是针对mid level的，不适合更高级的senior啥的

BQ
网上有太多例题，按照Amazon LP，大概选两三个project能体现自己的各种leadership就行，在答的时候切忌长篇大论讲project细节，面试官根本不关心你DB怎么设计的，他只想听各种点，你能很快的把这些点说出来就足够了，这一步得花2个晚上准备就OK了

Project Deep Dive
有些公司很喜欢问你以前的project的细节，所以起码找一个你认为有tech难点的project，认认真真的想一下各种细节，细到一个完全没有接触过这个project的人在听你讲完以后能够搞懂你为什么做各种选择和decision，为什么用某种技术，以及各种design细节等等，按照30分钟准备，才不会问到卡壳
