package Day04

import readInputLines
import readTestLines

fun main() {
    fun part1(input: List<String>)=
        input.map {
            it.split(',')
        }.sumOf {
            val first = it[0].split('-')
            val second = it[1].split('-')

            var value = 0

            if (first[0].toInt() >= second[0].toInt() && first[1].toInt() <= second[1].toInt()){
                value = 1
            }else if (first[0].toInt() <= second[0].toInt() && first[1].toInt() >= second[1].toInt()    ){
                value = 1
            }

            value
        }

    fun part2(input: List<String>)=
        input.map {
            it.split(',')
        }.count {
            val first = it[0].split('-')
            val second = it[1].split('-')

            ( second[0].toInt() <= first[1].toInt() && first[1].toInt() <= second[1].toInt()  ) ||
            ( second[0].toInt() <= first[0].toInt() && first[0].toInt() <= second[1].toInt() ) ||
            ( first[0].toInt() <= second[0].toInt() && second[0].toInt() <= first[1].toInt() ) ||
            ( first[0].toInt() <= second[1].toInt() && second[1].toInt() <= first[1].toInt() )
        }

    val input = readInputLines("Day04")
    val test = readTestLines("Day04")
    println(part2(input ))
}