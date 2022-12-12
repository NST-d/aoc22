package Day05

import utils.*
import java.util.*

fun main() {

    fun parseInput(input: String): Pair<Array<Stack<Char>>, List<String>> {
        val split = input.split("\n\n".toRegex(), limit = 2)
        val cratesString = split[0].split("\n").toMutableList()
        val moves = split[1].split("\n")

        val amountCrates = cratesString.removeLast().split("   ").count()
        val crates = Array(amountCrates) { Stack<Char>() }

        cratesString.reversed()
            .forEach { it ->
                //entry is "[A] " -> max 4 chars
                it.chunked(4).forEachIndexed { j, entry ->
                    if (entry.isNotBlank()) {
                        crates[j].push(entry[1])
                    }
                }
            }
        return Pair(crates, moves)
    }

    fun part1(input: String): String {

        val (crates, moves) = parseInput(input)
        //println(crates.contentToString())

        moves.map {
            val words = it.split(" ")

            Triple(words[1].toInt(), words[3].toInt() - 1, words[5].toInt() - 1)
        }.forEach { (amount, src, dest) ->
            repeat(amount) {
                crates[dest].push(crates[src].pop())
            }
            //println(crates.contentToString())
        }

        return String(crates.map { it.peek() }.toCharArray())
    }

    fun part2(input: String): String {
        val (crates, moves) = parseInput(input)
        //println(crates.contentToString())
        moves.map {
            val words = it.split(" ")

            Triple(words[1].toInt(), words[3].toInt() - 1, words[5].toInt() - 1)
        }.forEach { (amount, src, dest) ->
            val temp = List(amount) { crates[src].pop() }

            temp.reversed().forEach {
                crates[dest].push(it)
            }
            //println(crates.contentToString())
        }
        return String(crates.map { it.peek() }.toCharArray())
    }

    val test = readTestString("Day05").trimIndent()
    val input = readInputString("Day05").trimIndent()

    println(part1(input))
    println(part2(input))
}


