package com.amaterisa.movielistapp.utils

object ListUtils {
    fun transformStringToList(string: String): List<Int> {
        if (string.isEmpty()) {
            return emptyList()
        }
        return string.split(",").map { it.toInt() }
    }

    fun transformListToString(list: List<Int>): String {
        return list.joinToString(",")
    }
}