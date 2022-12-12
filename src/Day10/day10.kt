package Day10

import readInputLines
import readTestLines

class Machine(instructions: List<Instruction>) {

    private val instructions: Map<Int, Instruction>
    private var registerOverTime: MutableMap<Int, Int>

    init {
        this.instructions = HashMap()
        this.registerOverTime = HashMap()
        registerOverTime[1] = 1
        instructions.fold(1) { acc, instruction ->
            this.instructions[acc] = instruction
            acc + instruction.cycles
        }
        evaluateInstructions()
    }

    fun signalStrength(cycle: Int) = cycle * (registerOverTime[cycle]?: 0)


    val finalRegisterState get() = registerOverTime[registerOverTime.keys.max()]!!

    fun printStateOverTime(){
        for (i in 1 .. instructions.map { (key, instruction) -> instruction.cycles }.sum() ) {
            println("${i}: ${registerOverTime[i]} -[${instructions[i]?:""}]-> ${registerOverTime[i+1]}")
        }
    }

    interface Instruction {
        fun execute(machine: Machine, cycle: Int)
        val cycles: Int
    }

    class AddInstruction(val value: Int, override val cycles: Int = 2) : Instruction {

        override fun execute(machine: Machine, cycle: Int) {
            machine.registerOverTime[cycle] = machine.registerOverTime[cycle]!! + value
        }

        override fun toString(): String {
            return  "Add $value"
        }
    }

    class NoopInstruction(override val cycles: Int = 1) : Instruction {
        override fun execute(machine: Machine, cycle: Int) {
            /* no-op */
        }

        override fun toString(): String {
            return "Noop"
        }
    }

    private fun evaluateInstructions(){
        for((start,instruction) in instructions ) {
            for( i in 1 ..  instruction.cycles){
                registerOverTime[start + i] = registerOverTime[start] ?: 0
            }
            instruction.execute(this, start+instruction.cycles)
        }
    }


}

fun main() {




    fun part1(input: List<String>): Int{

        val instructions = input.map {
            val cmd = it.split(" ")
                when(cmd[0]){
                    "noop" -> Machine.NoopInstruction()
                    "addx" -> Machine.AddInstruction(cmd[1].toInt())
                    else -> error("Unknown instruction")
                }
            }

        val machine = Machine(instructions)

        machine.printStateOverTime()

        return (20..240 step 40 ).map {
             machine.signalStrength(it)
         }.sum()
    }

    val simple = """noop
addx 3
addx -5""".split("\n")
    val test = readTestLines("Day10")
    val input = readInputLines("Day10")

    println(part1(input))
}