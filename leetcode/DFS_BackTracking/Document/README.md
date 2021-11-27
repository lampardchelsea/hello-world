1. When DFS need backtracking ?  https://stackoverflow.com/questions/2709030/explain-bfs-and-dfs-in-terms-of-backtracking/3156208#3156208

The confusion comes in because backtracking is something that happens during search, but it also refers to a specific problem-solving technique where a lot of backtracking is done. Such programs are called backtrackers.

Picture driving into a neighborhood, always taking the first turn you see (let's assume there are no loops) until you hit a dead end, at which point you drive back to the intersection of the next unvisited street. This the "first" kind of backtracking, and it's roughly equivalent to colloquial usage of the word.

The more specific usage refers to a problem-solving strategy that is similar to depth-first search but backtracks when it realizes that it's not worth continuing down some subtree.

Put another way -- a naive DFS blindly visits each node until it reaches the goal. Yes, it "backtracks" on leaf nodes. But a backtracker also backtracks on useless branches. One example is searching a Boggle board for words. Each tile is surrounded by 8 others, so the tree is huge, and naive DFS can take too long. But when we see a combination like "ZZQ," we can safely stop searching from this point, since adding more letters won't make that a word.

I love these lectures by Prof. Julie Zelenski. She solves 8 queens, a sudoku puzzle, and a number substitution puzzle using backtracking, and everything is nicely animated. Programming Abstractions, Lecture 10 Programming Abstractions, Lecture 11

A tree is a graph where any two vertices only have one path between them. This eliminates the possibility of cycles. When you're searching a graph, you will usually have some logic to eliminate cycles anyway, so the behavior is the same. Also, with a directed graph, you can't follow edges in the "wrong" direction.

From what I can tell, in the Stallman paper they developed a logic system that doesn't just say "yes" or "no" on a query but actually suggests fixes for incorrect queries by making the smallest number of changes. You can kind of see where the first definition of backtracking might come into play.
