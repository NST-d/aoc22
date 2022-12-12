package Day11

import readInputString
import readTestString

operator fun <T> List<T>.component6() = this[5]

class Monkey(
    var worryLevels: MutableList<Long>,
    val operation: (Long) -> Long,
    val testCondition: Long,
    val passTo: Pair<Int, Int>,
    val updateMonkey: (Int, Long) -> Unit
) {

    var limiter = 1L
    var inspectingCounter = 0L

    fun Update() {
        worryLevels
            .map { operation(it) }
            .map { it%limiter }
            .forEach {
                inspectingCounter++
                if (it % testCondition == 0L) {
                    updateMonkey(passTo.first, it)
                } else {
                    updateMonkey(passTo.second, it)
                }
            }
        worryLevels = emptyList<Long>().toMutableList()
    }


}

fun parseMonkeys(input: String): List<Monkey>{
    val monkeyText = input.split("\n\n")

    lateinit var monkeys: List<Monkey>

    fun updateMonkey(index: Int, element: Long) {
        monkeys[index].worryLevels.add(element)
    }


    monkeys = List(monkeyText.size) {
        val (monkey, start, operationString, testString, passTrue, passFalse) = monkeyText[it].split("\n")

        val itemWorryLevels = start.substring(18)
            .split(", ")
            .map { it.toLong() }.toMutableList()

        val operationArgument = operationString.substring(25)
        val operation = when (operationString[23]) {
            '+' -> { x: Long ->
                x + if (operationArgument == "old") {
                    x
                } else {
                    operationArgument.toLong()
                }
            }
            '-' -> { x: Long ->
                x - if (operationArgument == "old") {
                    x
                } else {
                    operationArgument.toLong()
                }
            }
            '*' -> { x: Long ->
                x * if (operationArgument == "old") {
                    x
                } else {
                    operationArgument.toLong()
                }
            }
            '/' -> { x: Long ->
                x / if (operationArgument == "old") {
                    x
                } else {
                    operationArgument.toLong()
                }
            }
            else -> error("Unkown operation: $operationString")
        }

        val testCompare = testString.substring(21).toLong()


        val passTo = Pair(
            passTrue.split(" ").last().toInt(),
            passFalse.split(" ").last().toInt()
        )
        Monkey(itemWorryLevels, operation, testCompare, passTo) { i, e -> updateMonkey(i, e) }
    }

    return monkeys
}

fun main() {
    fun part1(input: String): Long {

        val monkeys = parseMonkeys(input)
        monkeys.forEach { it.limiter = 3 }

        val print =
        // listOf(1,20,1000,2000,3000,4000,5000,6000,7000,8000,9000,10_000)
        emptyList<Int>()

        println(monkeys.map { it.worryLevels })
        for( i in 1..20){
            monkeys.forEach { it.Update() }
            if (print.contains(i)) {
                println(monkeys.map { it.inspectingCounter })
                println(monkeys.map { it.worryLevels })
                println()
            }
        }


        return monkeys.map { it.inspectingCounter }
            //max 2
            .sortedDescending().take(2)
            .fold(1L) { acc, t -> acc * t }
    }

    fun part2(input: String): Long {
        val monkeys = parseMonkeys(input)

        val limiter = monkeys.map { it.testCondition }.fold(1L) {acc, t -> acc* t}
        monkeys.forEach { it.limiter = limiter }

        val print =
            // listOf(1,20,1000,2000,3000,4000,5000,6000,7000,8000,9000,10_000)
            emptyList<Int>()

        println(monkeys.map { it.worryLevels })
        for( i in 1..10_000){
            monkeys.forEach { it.Update() }
            if (print.contains(i)) {
                println(monkeys.map { it.inspectingCounter })
                println(monkeys.map { it.worryLevels })
                println()
            }
        }


        return monkeys.map { it.inspectingCounter }
            //max 2
            .sortedDescending().take(2)
            .fold(1L) { acc, t -> acc * t }
    }

    val test = readTestString("Day11").trimIndent()
    val input = readInputString("Day11").trimIndent()

    println(part2(input))
}