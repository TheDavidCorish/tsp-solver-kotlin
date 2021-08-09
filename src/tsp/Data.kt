package tsp

import java.io.*
import java.nio.charset.StandardCharsets

/**
 * Stores x and y coordinates
 */
var xCoordinatesAr: DoubleArray = DoubleArray(numberOfCities)
var yCoordinatesAr: DoubleArray = DoubleArray(numberOfCities)

/**
 * Stores solution so far
 */
var solutionAr: IntArray = IntArray(numberOfCities)

/**
 * Used for modifying solutionAr
 */
var tempSolutionAr: IntArray = IntArray(numberOfCities)
val tempSolutionList: MutableCollection<Int> = ArrayList()

/**
 * Used for tracking two cities to swap/distance
 */
var city1: Int = 0
var city2: Int = 0

/**
 * Stores current distance from first city to last city
 */
var currentTotalDistance: Double = 0.0
var tempTotalDistance: Double = 0.0

/**
 * Initialise coordinates and solution arrays by reading coordinates.csv and creating a random solution
 */
object Data {

    /**
     * Calls readCoordinatesCSV and randomise function
     */
    fun initialise() {
        print("Reading coordinates.csv and placing into solutionAr...")
        readCoordinatesCSV()
        randomise()
    }

    /**
     * Reads in coordinates.csv and places its data into an xCoordinatesAr and yCoordinatesAr
     */
    private fun readCoordinatesCSV() {
        val delimiter = ","
        try {
            val file = File("coordinates.csv")
            val fileReader = FileReader(file, StandardCharsets.UTF_8)
            val bufferedReader = BufferedReader(fileReader)
            var line: String
            var tempAr: Array<String>
            var i = 0
            while (numberOfCities > i) {
                line = bufferedReader.readLine()
                tempAr = line.split(delimiter.toRegex()).toTypedArray()
                xCoordinatesAr[i] = tempAr[0].toDouble()
                yCoordinatesAr[i] = tempAr[1].toDouble()
                i++
            }
            bufferedReader.close()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (ioe: IOException) {
            ioe.printStackTrace()
        }
    }

    /**
     * Creates a randomised TSP solution that fits specification
     */
    private fun randomise() {
        println("Randomising solutionAr... ")

        // Creates a boolean array equivalent to the number of cities
        val citiesBoolAr = BooleanArray(numberOfCities)

        // Sets city #1 to be 0 as set out in specification
        city1 = 0
        tempSolutionList.add(city1)

        // Adds cities to list until all cities used
        while (tempSolutionList.size < numberOfCities) {
            // Randomly select next city to add
            city2 = (1..1000).random()
            // If city hasn't been used before and its distance is greater than minimum distance...
            if (!citiesBoolAr[city2] && (minDistanceBetweenCities < Distance.betweenCities(city1, city2))) {
                // Add the city!
                tempSolutionList.add(city2)
                // Let boolean array know that city has been used
                citiesBoolAr[city2] = true
                city1 = city2
            }
        }

        // Sets final city to be 0 as set out in specification
        tempSolutionList.add(0)

        // Transfer list into solutionAr
        solutionAr = tempSolutionList.stream().mapToInt { i: Int? -> i!! }.toArray()

        // Set distance to be equal to the total journey distance of our randomised solution
        currentTotalDistance = Distance.total(solutionAr)
    }
}
