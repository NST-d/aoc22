package Day06

import readInputLines
import readTestLines
fun main() {
    fun part1(input: List<String>) =
        input.map {
            var value = 0
            for( i in 4 until it.length){
               if (it.substring(i-4,i).toList().distinct().count() == 4){
                   value = i
                   break;
               }
            }
            value
        }

    fun part2(input: List<String>) =
        input.map {
            var value = 0
            for( i in 14 until it.length){
                if (it.substring(i-14,i).toList().distinct().count() == 14){
                    value = i
                    break;
                }
            }
            value
        }


    val test = readTestLines("Day06")
    val input = readInputLines("Day06")

    println( part2(input) )
}