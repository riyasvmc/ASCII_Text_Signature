package signature

import java.io.File

const val FONT_ROMAN_HEIGHT = 10
const val FONT_MEDIUM_HEIGHT = 3

const val fontRomanPath = "C:/fonts/roman.txt"
const val fontMediumPath = "C:/fonts/medium.txt"

fun main() {
    // input
    print("Enter name and surname: ")
    val name = readLine()!!
    print("Enter person's status: ")
    val tag = readLine()!!

    // add parts of rows to array
    val nameList = fetchParts(name, File(fontRomanPath), FONT_ROMAN_HEIGHT, 10)
    val tagList = fetchParts(tag, File(fontMediumPath), FONT_MEDIUM_HEIGHT, 5)

    // widths
    var widthName: Int = nameList.maxOf { it.length }
    var widthTag = tagList.maxOf { it.length }
    val sideWidth = leftBorder().length + rightBorder().length


    val isTitleWider = widthName > widthTag

    val totalWidth = (if(isTitleWider) widthName else widthTag) + sideWidth

    // output
    println(topBottomBorder(totalWidth))

    if (isTitleWider) {
        for (line in nameList) {
            println("${leftBorder()}$line${rightBorder()}")
        }
        for (line in tagList) {
            println("${leftBorder()}${addPadding(line, widthName)}${rightBorder()}")
        }
    } else {
        for (line in nameList) {
            println("${leftBorder()}${addPadding(line, widthTag)}${rightBorder()}")
        }
        for (line in tagList) {
            println("${leftBorder()}$line${rightBorder()}")
        }
    }

    println(topBottomBorder(totalWidth))
}

fun fetchParts(name: String, fontFile: File, height: Int, width: Int): MutableList<String> {
    val titleParts = mutableListOf<String>()
    for (i in 0 until height) {
        var row = ""
        for (j in name.indices) {
            row += readFontFile(fontFile, name[j], i, width)
        }
        titleParts.add(row)
    }
    return titleParts
}

fun readFontFile(fontRoman: File, character: Char, row: Int, spaceWidth: Int): String {
    var part = ""
    if (character == ' ') {
        return " ".repeat(spaceWidth)
    } else {
        var found = false
        var i = 0
        loop@ for (line in fontRoman.readLines(Charsets.US_ASCII)) {
            if (!found) {
                found = line.matches(Regex("$character [1-9]?[0-9]"))
            } else {
                if (i == row) part = line
                if (i > FONT_ROMAN_HEIGHT) break@loop
                i++
            }
        }
    }
    return part
}

fun addPadding(string: String, width: Int): String {
    val padding = (width - string.length) / 2
    return "${" ".repeat(padding)}$string${" ".repeat(width - string.length - padding)}"
}

fun topBottomBorder(length: Int) = "8".repeat(length)

fun leftBorder() = "88  "

fun rightBorder() = "  88"
