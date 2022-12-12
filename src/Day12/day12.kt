package Day12

import utils.*
import java.lang.Integer.min

data class Point(val x: Int, val y: Int)

data class DistancePoints( val point: Point, var distance: Int)
fun main() {

    fun findChar(input: List<String>, char: Char): Point {
        val x = input.indexOfFirst { it.contains(char) }
        val y = input[x].indexOf(char)
        return Point(x,y)
    }

    fun findAllChars(input: List<String>, char: Char): List<Point>{
        val list = emptyList<Point>().toMutableList()
        for (i in input.indices){
            for(j in input[i].indices ){
                if(input[i][j] == char){
                    list.add(Point(i,j))
                }
            }
        }
        return list
    }

    fun parseGraph(input: List<String>): Triple<Point, Point, HashMap<Point, MutableList<Point>>> {
        var input = input.toMutableList()
        val S = findChar(input, 'S')
        val E = findChar(input, 'E')

        input[S.x] = input[S.x].replace('S','a')
        input[E.x] = input[E.x].replace('E', 'z')

        val n = input.size
        val m = input.first().length

        val Graph = HashMap<Point, MutableList<Point>>()


        for (i in 0 until  n) {
            for(j in 0 until m) {
                Graph[Point(i,j)] = emptyList<Point>().toMutableList()

                if (i > 0) {
                    if (input[i - 1][j] <= input[i][j] + 1) {
                        Graph[Point(i,j)]?.add(Point(i-1,j))
                    }
                }
                if (i < n - 1) {
                    if (input[i + 1][j] <= input[i][j] + 1) {
                        Graph[Point(i,j)]?.add(Point(i+1,j))
                    }
                }

                if (j > 0) {
                    if (input[i][j-1] <= input[i][j] + 1) {
                        Graph[Point(i,j)]?.add(Point(i,j-1))
                    }
                }
                if (j < m - 1) {
                    if (input[i][j+1] <= input[i][j] + 1) {
                        Graph[Point(i,j)]?.add(Point(i,j+1))
                    }
                }
            }
        }
        return Triple(S,E, Graph)
    }

    fun dijskstra(s: Point, e: Point, graph: Map<Point, List<Point>>): Int{
        val reachedPoints = listOf(DistancePoints(s,0)).toMutableList()
        val reachablePoints = graph[s]!!.map { DistancePoints(it, 1) }.toMutableList()

        while (!reachedPoints.map { it.point }.contains(e)) {

            val nearest = reachablePoints.minByOrNull { it.distance } ?: return Int.MAX_VALUE

            reachablePoints.remove(nearest)
            reachedPoints.add(nearest)

            val newReachable = graph[nearest.point]?: emptyList()

            val actualReachable = newReachable.filter {
                !reachablePoints.map { it.point }.contains(it)
                        &&
                        !reachedPoints.map { it.point }.contains(it)
            }

            reachablePoints.addAll(actualReachable.map { DistancePoints(it, nearest.distance +1) })
        }
        val eIndex =  reachedPoints.indexOfFirst { it.point == e }
        return reachedPoints[eIndex].distance
    }

    fun part1(input: List<String>): Int {

        val (S, E, Graph) = parseGraph(input)
        return dijskstra(S, E, Graph)

    }

    fun part2(input: List<String>): Int{
        val starters = findAllChars(input, 'a')
        val (S, E, Graph) = parseGraph(input)
        val minS = dijskstra(S, E, Graph)
        //APSP is probably more efficient, but 5 min runtime is better than a lot longer coding
        val minPerStarter = starters.minOfOrNull { dijskstra(it, E, Graph) }
        return min(minS, minPerStarter?:-1)
    }


    val test = readTestLines("Day12")
    val input = readInputLines("Day12")

    println(part2(input))

}