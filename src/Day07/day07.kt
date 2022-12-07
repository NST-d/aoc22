fun main() {
    data class FileTree(val name: String,
                        var size: Int = 0,
                        val children: MutableList<FileTree> = emptyList<FileTree>().toMutableList(),
                        val parent: FileTree? = null,
                        val directory : Boolean= true){
        fun addSize(value: Int){
            size += value
            parent?.addSize(value)
        }
        fun addChild(name: String): FileTree {
            require(directory)
            val child = FileTree(name = name, parent = this)
            children.add(child)
            return child
        }

        fun addFile(name: String, size: Int){
            val child = FileTree(name = name, size = size, parent = this, directory = false)
            addSize(size)
            children.add(child)
        }

        fun getSumOfSmallDirs(): Int{
            var sum = 0
            if(directory && size <= 100000){
                sum = size
            }
            sum += children.filter { it.directory }.sumOf { it.getSumOfSmallDirs()}
            return sum
        }

        fun findSmallestForDeletion(needed: Int, currentSmallest: Int): Int{
            var smallest = currentSmallest
            if(size < needed){
                return currentSmallest
            }else {
                return  children.filter { it.directory }.minOfOrNull { it.findSmallestForDeletion(needed, size) }?: size
            }
        }


        override fun toString() :String{
            return if(directory) {
                "$name $size\n" + children.joinToString("\n", "[", "]")
            } else{
                "File $name $size"
            }
        }
    }

    fun parseCommands(input: String): FileTree{
        val root = FileTree(name = "/" )
        var currentDir = root

        val commands = input.split('$').toMutableList()
        commands.removeFirst()
        for (command in commands){
            val lines = command.split("\n")
            //println(lines[0])
            when{
                lines[0] == " cd /" -> currentDir = root
                lines[0] == " cd .." -> currentDir = currentDir.parent!!
                lines[0].substring(1,3) == "cd" -> {
                    val dirName = lines[0].substring(4)
                    currentDir = currentDir.children.find { it.name == dirName } ?: currentDir.addChild(dirName)
                }
                lines[0] == " ls" -> {
                    for (i in 1 until lines.size){
                        if(lines[i].isBlank()){
                            continue
                        }
                        if (lines[i].substring(0,3)=="dir"){
                            val dirName = lines[i].substring(4)
                            if(currentDir.children.count { it.name == dirName } == 0) {
                                currentDir.addChild(dirName)
                            }
                        }else{
                            val split = lines[i].split(" ")
                            val size = split[0].toInt()
                            val name = split[1]

                            if(currentDir.children.count { it.name == name } == 0) {
                                currentDir.addFile(name, size)
                            }
                        }
                    }
                }
            }
        }
        return root
    }

    fun part1(input: String): Int{
        val root = parseCommands(input)
        return root.getSumOfSmallDirs()
    }

    fun part2(input:String) : Int{
        val root = parseCommands(input)
        val max = 70000000
        val needed = 30000000
        return root.findSmallestForDeletion(needed - (max - root.size), max )
    }

    val test = readTestString("Day07").trimIndent()
    val input = readInputString("Day07").trimIndent()

    println(part2(input))
}