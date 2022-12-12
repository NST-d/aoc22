package Day03

import utils.*

fun main() {
    fun part1(input : List<String>): Int =
        input
            .map { it.chunked(it.length/2) }
            .sumOf {
                val left = it[0]
                val right = it[1]

                var value = 0

                (left.toCharArray()).distinct().forEach { c: Char ->
                    if (right.contains(c)){
                        if(c.isUpperCase()){
                            value += c - 'A' + 27
                        }else{
                            value += c - 'a' +1
                        }
                        //println("$c $value")
                    }
                }
                value
            }

    fun part2(input: List<String>): Int =
        input.withIndex().groupBy {  (it.index/3) }.values
            .sumOf { group ->
                var value = 0

                group[0].value.toCharArray().distinct().forEach { c: Char ->
                    if( group[1].value.contains(c) && group[2].value.contains(c)){
                        if(c.isUpperCase()){
                            value += c - 'A' + 27
                        }else{
                            value += c - 'a' +1
                        }
                    }

                }

                value
            }


    val test = readTestLines("Day03")
    val input = readInputLines("Day03")
    println( part2(input) )
}