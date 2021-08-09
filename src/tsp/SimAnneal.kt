package tsp

import java.text.DecimalFormat
import java.util.*
import kotlin.math.exp

/**
 *  Handles all parts of simulated annealing algorithm
 */
object SimAnneal {
    // Temp cities used for swapping
    var tempCity1: Int = 0
    var tempCity2: Int = 0

    // Formats heat variable for printing in console
    private val heatFormatter = DecimalFormat("#,###.000")

    // Used for probability calculation
    val r = Random()

    /**
     *  Start simulated annealing algorithm
     */
    fun start() {
        print("Beginning simulated annealing... ")

        // Copy current solutionAr into a tempSolutionAr
        System.arraycopy(solutionAr, 0, tempSolutionAr, 0, numberOfCities)

        // A boolean to decide whether we accept the simulated annealing change
        var simAnnealAcceptingChange: Boolean

        // Heat and cooling rate of simulated annealing algorithm
        var heat = 30000.0
        val coolingRate = 0.9995

        // Until heat gets to below 1.0
        while (heat > 1.0) {
            strBuilder.setLength(0)
            simAnnealAcceptingChange = false

            // Randomly pick two cities
            city1 = (1..1000).random()
            city2 = (1..1000).random()

            // Swaps the two cities
            tempCity1 = tempSolutionAr[city1]
            tempCity2 = tempSolutionAr[city2]
            tempSolutionAr[city1] = tempCity2
            tempSolutionAr[city2] = tempCity1

            // If their distances are still over the minimum distance between cities
            if (Distance.checkOverMin(tempSolutionAr, city1) && Distance.checkOverMin(tempSolutionAr, city2)) {
                tempTotalDistance = Distance.total(tempSolutionAr)
                // Calculate probability based on heat
                if (r.nextDouble() < probability(heat)) {
                    // Accept change
                    acceptChange()
                    simAnnealAcceptingChange = true
                    print("Heat: ")
                    print((heatFormatter.format(heat)))
                }
            }

            // If we're not accepting any changes, reset TempSolutionAr
            if (!simAnnealAcceptingChange) {
                System.arraycopy(solutionAr, 0, tempSolutionAr, 0, numberOfCities)
            }

            // Cool down heat
            heat *= coolingRate
        }
    }


    /**
     *  Function for accepting changes to solutionAr
     */
    private fun acceptChange() {
        strBuilder.setLength(0)
        tempTotalDistance = Distance.total(tempSolutionAr)
        strBuilder.append("\n").append("\n")

        // Let the user know whether this is a good or bad change
        when {
            tempTotalDistance > currentTotalDistance -> strBuilder
                .append("Accepting bad change: ")
                .append("\n")
                .append("Total distance increasing by ")
                .append(numberFormatter.format(tempTotalDistance - currentTotalDistance))
                .append(" km")
            else -> strBuilder
                .append("Accepting good change: ")
                .append("\n")
                .append("Total distance decreasing by ")
                .append(numberFormatter.format(currentTotalDistance - tempTotalDistance))
                .append(" km")
        }

        // Accept changes into solutionAr
        System.arraycopy(
            tempSolutionAr,
            0,
            solutionAr,
            0,
            numberOfCities
        )
        currentTotalDistance = tempTotalDistance

        // Let user know what operation was just performed
        println(
            strBuilder
                .append("\n")
                .append("Swapping city ")
                .append(tempCity1)
                .append(" and city ")
                .append(tempCity2)
                .append("\n")
                .append("New total distance: ")
                .append(numberFormatter.format(currentTotalDistance))
                .append(" km")
        )
    }

    /**
     *  Probability calculation for accepting changes
     */
    private fun probability(heat: Double): Double {
        when {
            // If change improves distance, accept it immediately
            (tempTotalDistance < currentTotalDistance) -> {return 1.0}
            // Else, decide based on heat
            else -> {
                return exp((currentTotalDistance - tempTotalDistance) / heat)}
        }
    }
}