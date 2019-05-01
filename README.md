# Gobang
## Team Members
- Kai Mo [github](https://github.com/MarkKai)
- Quan Liu [github](https://github.com/hans12x)
- Xiaoqian Xu [github](https://github.com/Cecilia-xu)
- Tianqi Xie [github](https://github.com/kikixie95)
- Haochen Wei 
## Background
Gomoku, which is originate in Eastern-Asian more than 200 years ago, is still very popular now. It is an abstract strategy board game, using 15*15 grid intersections. The rule of Gomoku can be listed as followed:
1. The game allows only 2 players. 
2. Players alternate turns placing a stone of their color (black or white) on an empty intersection.
3. The winner is the first player to form an unbroken chain of five stones horizontally, vertically, or diagonally as figure 1 shows below.
## Introduction
What we had implemented is a Java application which is an automatic Gomoku game application with 3 difficulty levels. In this application, a player can play Gomoku against the robot. Our robot can capture the coordinate of the player to place the chess. Also, the robot has three different difficulty levels that player can choose; robot will take different strategy for evaluating the next step with different difficulty respectively, by applying a series of algorithms, our robot can compute an optimal coordinate to decide which position to place the chess and make sure a relatively high rate of success. Besides, in order to simulate a real-world game, we design a similar chess board with clear grid as our user interface by using Java GUI. The player can click the mouse to place the chess while the robot can also place the chess after computing the position of the chess where the player placed.
## Main Algorithms
1. Game-tree algorithm
2. Minimax Algorithm
3. Alpha Beta Pruning
4. Score evaluation
5. Speed up - boundary of searching
## User Interface
<img src="https://github.com/MarkKai/Gobang/blob/master/userInterface/1.png" height="50%" width="50%">
