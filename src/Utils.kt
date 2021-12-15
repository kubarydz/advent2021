import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads lines from the given input txt file.
 */
//fun readInput(name: String) = File("src", "$name.txt").readLines()

fun readSample(name: String) = File("src/$name", "sample.txt").readLines()

fun readInput(name: String) = File("src/$name", "input.txt").readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5(): String = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray())).toString(16)

/*
 * Reads lines in the given file
 * Returns list of Ints
 */
fun readInputToInts(name: String) = readInput(name).map { it.toInt() }

fun readInputToIntsComma(name: String) = readInput(name).first().split(',').map { it.toInt() }