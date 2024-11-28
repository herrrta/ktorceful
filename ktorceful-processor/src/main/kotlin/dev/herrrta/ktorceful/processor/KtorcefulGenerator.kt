package dev.herrrta.ktorceful.processor

import java.io.PrintWriter

internal interface KtorcefulGenerator: Runnable {
    val w: PrintWriter
    fun generateRouting()
}