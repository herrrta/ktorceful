package dev.herrrta.ktorceful.processor

import java.io.PrintWriter
import java.time.ZonedDateTime

internal class KtorcefulGeneratorImpl(
    override val w: PrintWriter
) : KtorcefulGenerator {
    private val imports: MutableList<String> = mutableListOf()
    private val routeFunctions: MutableList<String> = mutableListOf()
    private val generators: MutableList<KtorcefulRoutingGenerator> = mutableListOf()

    override fun run() {
        packageDeclaration()
        importDependencies()
        imports.forEach(w::println)
        generators.forEach(Runnable::run)
        generateRouting()
    }

    override fun generateRouting() {
        w.println()
        w.println("fun Application.allKtorcefulRoutes() {")
        routeFunctions.forEach {
            w.println("    $it()")
        }
        w.println("}")
    }

    private fun packageDeclaration() {
        w.println("// generated at ${ZonedDateTime.now()}")
        w.println("package dev.herrrta.ktorceful")
        w.println()
    }

    private fun importDependencies() {
        w.println("import ${Symbols.KTOR_APPLICATION}")
        w.println("import ${Symbols.KTOR_ROUTING}")
        w.println("import ${Symbols.KTOR_RESOURCES}")
        w.println()
        w.println("import ${Symbols.KTORCEFUL_ENTITY_RESOURCE}")
        w.println()
    }

    fun add(generator: KtorcefulRoutingGenerator) {
        imports.add(generator.import)
        routeFunctions.add(generator.function)
        generators.add(generator)
    }
}