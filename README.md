# Travelling salesman problem using simulated annealing and two-opt (Kotlin)
This code aims to find the shortest route that visits every city in a set of 1,001 cities. Each city must be more than 100 km away from the last. A dynamically updating graph (JFreeChart) displays the current solution, refreshing every second.

The solution is validated to ensure no cities repeat etc. Solution + coordinates are then printed.

Run TSP.kt to begin solver.

## Nearest neighbour (always selecting closest city)
Nearest neighbour ignores long-term benefits in favour of immediate gains, getting stuck in a local minimum; there may exist a sequence of changes that initially make the answer worse but eventually lead to a better final answer.

Nearest neighbour gives an answer of 486,356 km for the set of cities in coordinates.csv.

## Simulated annealing
Simulated annealing has a temperature property that starts off high and gradually falls as the algorithm progresses. The probability of accepting a bad change is dependent on how high the temperature is and how bad the change is. Initially, the algorithm accepts almost any change. It then progressively gets less and less likely to accept bad changes.

Before simulated annealing, a random solution is generated. This route will have a length of approximately 7,400,000 km.

Simulated annealing swaps two random cities in the route. If this reduces the total length, the change is accepted. However, if the new route is worse, the change is only accepted _sometimes_ (depending on the heat).

This prevents the solution from getting stuck in a local minimum; the earlier described situation in which no one change makes the solution better.

## Two-opt
Two-opt takes a route that crosses over itself and reorders it such that it does not. The algorithm checks the distance improvements of every city connection swapped with every other city connection, repeatedly making swaps until there’s been no improvement for several runs.

This vastly improves the solution obtained by simulated annealing.

## Final result
Combining simulated annealing and two-opt gave an answer of 371,181 km for the set of cities in coordinates.csv.
