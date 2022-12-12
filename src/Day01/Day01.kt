import utils.*
import kotlin.math.max

fun main() {
    fun part1(input: List<String>) =
        input.fold ( Pair(0,0)) { acc, line ->
            var max = acc.first
            var sum = acc.second
            if ( line.isEmpty() ){
                max = max(acc.first, acc.second)
                sum = 0
            }else{
                sum += line.toInt()
            }
            Pair(max,sum)
        }.first


    fun part2(input: String) =
        input.split("\n\n")
            .map {
                it.split("\n").sumOf { it.toInt() }
            }.sortedDescending()
            .take(3)
            .sum()


    // test if implementation meets criteria from the description, like:
    val testInput = readTestLines("Day01")
    check(part1(testInput) == 24000)

    val inputLines = readInputLines("Day01")
    val inputString = readInputString("Day01").trimIndent()

    println(part1(inputLines))
    println(part2(inputString))
}
