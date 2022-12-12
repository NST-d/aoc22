package Day08

import utils.readInputLines
import utils.readTestLines
import java.util.Arrays
import kotlin.math.max


fun main() {

    fun printArray(arr: Array<BooleanArray>){
        arr.forEach {
            it.forEach { print("$it ") }
            println()
        }
    }

    fun part1(input: List<String>): Int{
        val n = input.size
        val m = input.first().length

        val visible = Array(n) {BooleanArray(m)}

        visible[0] = BooleanArray(m) {true}
        visible[n-1] = BooleanArray(m) {true}
        for ( i in 0 until m){
            visible[i][0] = true
            visible[i][m-1] = true
        }



        for (i in 1 until n-1) {

            for(j in 1 until m-1){
                if (input[i].substring(0,j).all { it < input[i][j]}){
                    visible[i][j] = true
                }
            }

            for(j in m-2 downTo  1){
                if (input[i].substring(j+1,m).all { it < input[i][j]}){
                    visible[i][j] = true
                }
            }



        }

        for (j in 1 until m-1) {
            val column = input.map { it[j] }
            for(i in 1 until n-1){

                if (column.take(i).all { it < input[i][j]}){
                    visible[i][j] = true
                }



                if (column.takeLast(i).all { it < input[n-i-1][j]}){
                    visible[n-i-1][j] = true
                }
            }

        }



        //printArray(visible)

        return visible.sumOf { it.count { it } }

    }



    fun part2(input: List<String>):Int{
        var max = 0
        val n = input.size

        var (bestI, bestJ) = Pair(0,0)

        for (i in 1 until n-1){
            for (j in 1 until n-1){
                val height = input[i][j]
                var maxLeft = 1
                for(k in j-1 downTo 1){
                    if (input[i][k] < height){
                        maxLeft++
                    }else{
                        break
                    }
                }

                var maxRight = 1
                for(k in j+1 until n-1){
                    if (input[i][k] < height){
                        maxRight++
                    }else{
                        break
                    }
                }

                var maxUp = 1
                for(k in i-1 downTo 1){
                    if (input[k][j] < height){
                        maxUp++
                    }else{
                        break
                    }
                }

                var maxDown = 1
                for(k in i+1 until n-1){
                    if (input[k][j] < height){
                        maxDown++
                    }else{
                        break
                    }
                }

                val score = maxDown * maxUp * maxLeft * maxRight


                //println("($i, $j) $maxUp $maxRight $maxDown $maxLeft")

                if(score > max){
                    max = score
                    bestI = i
                    bestJ = j
                }

            }
        }

        //println("$bestI, $bestJ")
        return max

    }

    val test = readTestLines("Day08")
    val input = readInputLines("Day08")
    println(part2(input))

}