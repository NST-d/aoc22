package Day12

import readTestLines

data class Point(val x: Int, val y: Int)

fun main() {

    fun findChar(input: List<String>, char: Char): Point {
        val x = input.indexOfFirst { it.contains("S") }
        val y = input[x].indexOf("S")
        return Point(x,y)
    }

    fun parseGraph(input: List<String>): Triple<Point, Point, HashMap<Point, MutableList<Point>>> {
        val S = findChar(input, 'S')
        val E = findChar(input, 'E')

        input[S.x].replace('S','a')
        input[E.x].replace('E', 'z')

        val n = input.size
        val m = input.first().length

        val Graph = HashMap<Point, MutableList<Point>>()


        for (i in 0 until  n) {
            for(j in 0 until m) {
                Graph[Point(i,j)] = emptyList<Point>().toMutableList()

                if (i > 0) {
                    if (input[i - 1][j] <= input[i][j] + i) {
                        Graph[Point(i,j)]?.add(Point(i-1,j))
                    }
                }
                if (i < n - 1) {
                    if (input[i + 1][j] <= input[i][j] + i) {
                        Graph[Point(i,j)]?.add(Point(i+1,j))
                    }
                }

                if (j > 0) {
                    if (input[i][j-1] <= input[i][j] + i) {
                        Graph[Point(i,j)]?.add(Point(i,j-1))
                    }
                }
                if (j < m - 1) {
                    if (input[i + 1][j] <= input[i][j] + i) {
                        Graph[Point(i,j)]?.add(Point(i,j+1))
                    }
                }
            }
        }
        return Triple(S,E, Graph)
    }

    fun part1(input: List<String>) {

        val (S, E, Graph) = parseGraph(input)

    }

    val test = readTestLines("Day12")

    println(part1(test))

}