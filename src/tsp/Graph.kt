package tsp

import org.jfree.chart.ChartFactory
import org.jfree.chart.ChartPanel
import org.jfree.chart.plot.PlotOrientation
import org.jfree.data.xy.XYSeries
import org.jfree.data.xy.XYSeriesCollection
import java.awt.BorderLayout
import java.util.*
import javax.swing.JFrame
import javax.swing.SwingUtilities

/**
 * Dynamic graph that shows all coordinates, using JFreeChart library
 */
object Graph {
    val series = XYSeries("plots", false)
    var data = XYSeriesCollection(series)

    /**
     * Create initial graph
     */
    fun start() {
        val labels = arrayOf("TSP", "X", "Y")
        val options = booleanArrayOf(false, true, false)
        val orient = PlotOrientation.VERTICAL
        val chart = ChartFactory.createXYLineChart(
            labels[0], labels[1], labels[2], data, orient, options[0], options[1], options[2]
        )
        val chartPanel = ChartPanel(chart)
        SwingUtilities.invokeLater {
            val f = JFrame()
            with(f) {
                defaultCloseOperation = JFrame.EXIT_ON_CLOSE
                add(chartPanel, BorderLayout.CENTER)
                title = "Plot coordinate pairs"
                isResizable = true
                pack()
                setLocationRelativeTo(null)
                isVisible = true
            }
        }
        refreshChart()
    }

    /**
     * Refresh graph every second with any changes made to solutionAr
     */
    fun refreshChart() {
        val timer = Timer()
        timer.scheduleAtFixedRate(
            object : TimerTask() {
                override fun run() {
                    series.clear()
                    for(i in 0 until solutionAr.size) {
                        series.add(xCoordinatesAr[solutionAr[i]], yCoordinatesAr[solutionAr[i]])
                    }
                    data = XYSeriesCollection(series)
                }
            },
            0, 1000
        )
    }
}