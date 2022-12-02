import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads lines from the given input txt file.
 */
fun readInputLines(name: String) = File("src/$name", "input.txt")
    .readLines()

fun readInputString(name: String) = File("src/$name", "input.txt")
    .readText()

fun readTestLines(name: String) = File("src/$name", "test.txt")
    .readLines()

fun readTestString(name: String) = File("src/$name", "test.txt")
    .readText()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')
