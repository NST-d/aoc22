package Day09

import utils.*
import kotlin.math.abs
import kotlin.math.sign


fun main() {

    data class Point(var x: Int, var y: Int){
        fun follow(other: Point){
            if(abs(other.x-this.x) > 1){
                //move in the right direction
                this.x += sign((other.x - this.x).toDouble()).toInt()
                //diagonal joining
                if(this.y != other.y){
                    this.y += sign((other.y - this.y).toDouble()).toInt()
                }
            }else if(abs(other.y- this.y) > 1){
                //move in the right direction
                this.y += sign((other.y - this.y).toDouble()).toInt()
                //diagonal joining
                if(this.x != other.x){
                    this.x += sign((other.x - this.x).toDouble()).toInt()
                }
            }
        }
    }

    fun printField(points: List<Point>, sizeX: Int, sizeY : Int){
        val field = Array(sizeY) { CharArray(sizeX){'.'} }

        points.forEachIndexed { index, point ->
            field[point.y][point.x] = "$index"[0]
        }

        field.reversed().forEach { println(String(it)) }
    }

    fun part1(input: List<String>):Int{
        val h = Point(0,0)
        val t = Point(0,0)

        val visitedPosition = LinkedHashSet<Point>()

        input.forEach {
            println(it)
            val (direction, amount) = it.split(" ")
            //loop invariant: t and h are adjacent
            repeat(amount.toInt()){
                when(direction){
                    "R" -> h.x++
                    "L" -> h.x--
                    "U" -> h.y++
                    "D" -> h.y--
                }

                t.follow(h)

                visitedPosition.add(t.copy())
                println("h $h, t $t")
            }
        }

        return visitedPosition.count()
    }

    fun part2(input: List<String>):Int{

        val points = List<Point>(10) { Point(0,0)}

        val visitedPosition = LinkedHashSet<Point>()

        input.forEach {
            println(it)
            val (direction, amount) = it.split(" ")
            //loop invariant: t and h are adjacent
            repeat(amount.toInt()){
                when(direction){
                    "R" -> points[0].x++
                    "L" -> points[0].x--
                    "U" -> points[0].y++
                    "D" -> points[0].y--
                }

                points.drop(1).forEachIndexed { i, point ->
                    point.follow(points[i])
                }

                visitedPosition.add(points.last().copy())
                //println(points)
                //printFiled(points, 6,5)
            }
        }

        return visitedPosition.count()
    }

    val test = readTestLines("Day09")
    val input = readInputLines("Day09")
    println(part2(input))
}