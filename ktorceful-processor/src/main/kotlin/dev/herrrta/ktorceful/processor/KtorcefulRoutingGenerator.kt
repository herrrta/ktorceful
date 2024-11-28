package dev.herrrta.ktorceful.processor

import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSValueParameter

internal interface KtorcefulRoutingGenerator: KtorcefulGenerator {
    val declaration: KSClassDeclaration
    val functions: Map<String, List<KSValueParameter>>

    val import get() = "import ${declaration.qualifiedName?.asString()}"
    val function get() = "ktorceful${declaration}Route"

    override fun run() {
        w.println()
        w.println("fun Application.$function() {")
        w.println("    routing {")
        generateRouting()
        w.println("    }")
        w.println("}")
    }
}