package tsp

/**
 * Handles all parts of two-opt swap algorithm
 */
object TwoOpt {
    private var improving: Int = 0

    /**
     * Starts two-opt algorithm and continues until no improvements found
     */
    fun start() {
        println()
        println("Beginning two-opt swap... ")
        while (improving < 3) {
            for (i in 1 until numberOfCities - 1) {
                for (j in i + 1 until numberOfCities - 1) {
                    swap(i, j) // initiate two-opt swap using i and j
                }
            }
            improving++
        }
    }

    /**
     * Performs two-opt swap
     */
    private fun swap(i: Int, j: Int) {
        strBuilder.setLength(0)
        tempSolutionList.clear()

        // Take route [0] to [i-1] and add in order
        for (k in 0 until i) {
            tempSolutionList.add(solutionAr[k])
        }

        // Take route [i] to [j] and add in reverse order
        for (k in j downTo i) {
            tempSolutionList.add(solutionAr[k])
        }

        // Take route [j+1] to end and add in order
        for (k in j + 1 until numberOfCities) {
            tempSolutionList.add(solutionAr[k])
        }

        tempSolutionAr = tempSolutionList.stream().mapToInt { k: Int? -> k!! }.toArray()

        // If all distances are still over 100 km following two-opt swap, then continue
        if (!Distance.checkOverMin(tempSolutionAr, i) || !Distance.checkOverMin(tempSolutionAr, j)) {
            return
        }

        // Calculate new total distance
        tempTotalDistance = Distance.total(tempSolutionAr)

        // If distance is an improvement, then continue
        if (tempTotalDistance >= currentTotalDistance) {
            return
        }

        // Pass improvement so solutionAr
        solutionAr = tempSolutionList.stream().mapToInt { k: Int? -> k!! }.toArray()
        currentTotalDistance = tempTotalDistance

        // Let two-opt know we're still improving
        improving = 0

        print(
            strBuilder
                .append("\n")
                .append("i is ")
                .append(i)
                .append(" and j is ")
                .append(j)
                .append("\n")
                .append("Two-opt swapping city ")
                .append(solutionAr[i])
                .append(" and city ")
                .append(solutionAr[j])
                .append("\n")
                .append("New total distance: ")
                .append(numberFormatter.format(currentTotalDistance))
                .append(" km")
                .append("\n")
        )
    }
}
