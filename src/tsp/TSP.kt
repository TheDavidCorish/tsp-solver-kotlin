package tsp

import java.text.DecimalFormat

/**
 * Travelling salesman problem using simulated annealing and two-opt
 *
 * @author David Corish
 */

/**
 * Set this to number of cities in coordinates.csv
 */
const val numberOfCities: Int = 1001

/**
 * Set this to minimum distance (in km) required between each city
 */
const val minDistanceBetweenCities: Int = 100

/**
 * StringBuilder and numberFormatter shared between all .kt files
 */
val strBuilder: StringBuilder = StringBuilder(32)
val numberFormatter: DecimalFormat = DecimalFormat("#,###.00")

/**
 *  Main function that calls TSP functions
 *  Comment out simulated annealing or two-opt if you don't want to use them
 */
fun main() {
    // Used for calculating program runtime
    val startOfMain = System.currentTimeMillis()

    // Starts dynamic graph
    Graph.start()

    // Creates initial arrays and randomises a solution
    Data.initialise()

    // Simulated annealing algorithm
    SimAnneal.start()

    // Two-opt algorithm
    TwoOpt.start()

    // Checks if solution fits specification and then prints coordinates and solution
    if (Solution.validate()) {
        Solution.printCoordinates()
        Solution.print()
    }

    // Print program runtime
    strBuilder.setLength(0)
    println(
        strBuilder
            .append("\n")
            .append("Program completed in ")
            .append((System.currentTimeMillis() - startOfMain))
            .append(" ms")
    )
}
