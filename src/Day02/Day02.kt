package Day02

import utils.*
enum class RPS(val score: Int){
    ROCK(1),
    PAPER(2),
    SCISSOR (3),
}

fun winScore(opponent: RPS, player: RPS): Int {
    if(opponent == player) return 3
    if((opponent.ordinal+1)%3 == player.ordinal  ) return 6
    return 0
}

fun getRPS(input:Char, base:Char) =
    when( input-base){
        0 -> RPS.ROCK
        1 -> RPS.PAPER
        2 -> RPS.SCISSOR
        else -> error("Unssuporter input $input for base $base")
    }


fun main() {
    fun part1(input : List<String>)=
        input.sumOf {
            val opponent = getRPS(it[0], 'A')
            val recommended = getRPS(it[2], 'X')

            val score =recommended.score + winScore(opponent, recommended)

            //println("$opponent vs $recommended -> $score")
            score
            }

    fun part2(input: List<String>) =
        input.sumOf {
            val opponent = getRPS(it[0], 'A')

            val recommended  = when(it[2]){
                'X' -> RPS.values()[(opponent.ordinal +2) % 3]
                'Y' -> opponent
                'Z' -> RPS.values()[(opponent.ordinal+1) % 3]
                else -> error("")
            }

            val score = recommended.score + winScore(opponent, recommended)
            //println("$opponent vs $recommended -> $score")
            score
        }

    val test = readTestLines("Day02")
    val input = readInputLines("Day02")
    println(part2(input))
}