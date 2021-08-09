package tsp

/**
 *  Handles printing and validation of solution
 */
object Solution {
    /**
     *  Prints the total distance travelled and prints solution string
     */
    fun print() {
        strBuilder.setLength(0)
        println(
            strBuilder
                .append("Total distance travelled: ")
                .append("\n")
                .append(numberFormatter.format(Distance.total(solutionAr)))
                .append(" km")
                .append("\n")
                .append("\n")
                .append("Solution: ")
        )
        println(buildString())
    }

    /**
     *  Ensures that all cities and their neighbours distances are greater than the minimum distance
     */
    fun validate(): Boolean {
        var validSolution = true
        strBuilder.setLength(0)
        strBuilder.append("\n").append("Checking if solution valid... ")

        for (i in 0 until numberOfCities - 1) {
            if (Distance.betweenCities(solutionAr[i], solutionAr[i + 1]) < minDistanceBetweenCities) {
                validSolution = false
                strBuilder
                    .append("\n")
                    .append("Invalid solution, distance between city ")
                    .append(solutionAr[i])
                    .append(" and city ")
                    .append(solutionAr[i + 1])
                    .append(" is ")
                    .append(
                        numberFormatter.format(
                            Distance.betweenCities(
                                solutionAr[i],
                                solutionAr[i + 1]
                            )
                        )
                    )
                    .append(" km, this is less than ")
                    .append(minDistanceBetweenCities)
                    .append(" km")
                break
            }
        }

        // If the solution is valid, let the user know
        if (validSolution) strBuilder.append("\n").append("Valid solution!").append("\n")
        print(strBuilder)
        return validSolution
    }

    /**
     * Creates solution string based on solutionAr
     */
    private fun buildString(): String {
        strBuilder.setLength(0)
        for (i in 0 until numberOfCities) {
            strBuilder
                .append(solutionAr[i])
                .append(",")
        }
        strBuilder.append("0")
        return strBuilder.toString()
    }

    /**
     *  Print all coordinates of solutionAr
     */
    fun printCoordinates() {
        strBuilder.setLength(0)
        strBuilder.append("\n")
        for (i in 0 until numberOfCities) {
            city1 = solutionAr[i]
            strBuilder
                .append(xCoordinatesAr[city1])
                .append(", ")
                .append(yCoordinatesAr[city1])
                .append("\n")
        }
        println(strBuilder)
    }
}
