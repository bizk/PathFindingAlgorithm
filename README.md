# PathFindingAlgorithm

This is the final project for the subject programming 3 witch aims to cover the highest part of algorithms implementations and solutions focusing
over decreasing computing and memory complexity.

The main 4 points of this subjects are:
- Dynamic Programming
- Greedy solutions
- Divide and Conquer
- Backtracking
- Big O notation

### Context

We have a 1000x1000 canvas map where we can select a start point, an end point and we have the chance to create obstacles or more dense paths. 

Each movement in up, left, right, down direction cost 1 point, and each movement to any of the edges costs 1.41 points

We cant move trough those part with density equals or over 4, but moving trough the rest of ties each movement cost is equal to (movementCost + tileDensity)


### Goal

Find the shortest path in the fastest way


### Solution

Without knowing it, after starting with a backtrack algorithm and working over its optimization, we arrived at a solution similar to the
A* solution.

Our solution consist of an algorithm that priors going directly trough directly but keeping the possible other paths and their cost, 
The idea is to always expand over the most promising nodes and not going back to those visited, always choosing the best option while still
exploring other possible solutions..


***GRADE 10/10***
