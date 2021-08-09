package tsp

import kotlin.math.sqrt


/**
 * Handles all distance calculations
 */
object Distance {
    /**
     * Calculates total distance of journey
     */
    fun total(tempSolutionAr: IntArray)
            : Double {
        var totalDistance = 0.0
        for (i in 0 until numberOfCities - 1) {
            city1 = tempSolutionAr[i]
            city2 = tempSolutionAr[i + 1]
            val distanceBetweenCities = Distance.betweenCities(city1, city2)
            totalDistance += distanceBetweenCities
        }
        return totalDistance
    }

    /**
     * Calculates distance between two cities, based on Haversine formula
     */
    fun betweenCities(city1: Int, city2: Int)
            : Double {
        val earthRadius = 6371.0
        val lat1 = Math.toRadians(xCoordinatesAr[city1])
        val lon1 = Math.toRadians(yCoordinatesAr[city1])
        val lat2 = Math.toRadians(xCoordinatesAr[city2])
        val lon2 = Math.toRadians(yCoordinatesAr[city2])
        val latDistance = lat2 - lat1
        val lonDistance = lon2 - lon1
        val x =
            (StrictMath.pow(StrictMath.sin(latDistance / 2.0), 2.0) +
                    (StrictMath.cos(lat1) *
                            StrictMath.cos(lat2) *
                            StrictMath.pow(StrictMath.sin(lonDistance / 2.0), 2.0)))
        val y = StrictMath.asin(sqrt(x)) * 2.0
        return y * earthRadius
    }

    /**
     * Checks that cities and their neighbours' distances are over the minimum distance
     */
    fun checkOverMin(tempAr: IntArray, cityToCheck: Int): Boolean {
        // Handles edge cases of 0 and 1000
        return when (cityToCheck) {
            0 -> minDistanceBetweenCities < Distance.betweenCities(
                tempAr[cityToCheck],
                tempAr[cityToCheck + 1]
            )
            numberOfCities-1 -> minDistanceBetweenCities < Distance.betweenCities(
                tempAr[cityToCheck],
                tempAr[cityToCheck - 1]
            )
            else -> (minDistanceBetweenCities < Distance.betweenCities(tempAr[cityToCheck], tempAr[cityToCheck - 1])
                    &&
                    minDistanceBetweenCities < Distance.betweenCities(tempAr[cityToCheck], tempAr[cityToCheck + 1])
                    )
        }
    }
}