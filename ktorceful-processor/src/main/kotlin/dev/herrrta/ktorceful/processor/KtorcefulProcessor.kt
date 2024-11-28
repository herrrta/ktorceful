package dev.herrrta.ktorceful.processor

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.ClassKind
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSVisitorVoid
import com.google.devtools.ksp.validate
import dev.herrrta.ktorceful.annotation.Ktorceful
import dev.herrrta.ktorceful.core.interfaces.HTTPMethod
import dev.herrrta.ktorceful.dao.interfaces.EntityRoute
import io.ktor.http.HttpMethod
import java.io.PrintWriter

internal class KtorcefulProcessor(
    private val logger: KSPLogger,
    private val codeGenerator: CodeGenerator,
) : SymbolProcessor {
    val allowedMethodNames = HttpMethod.DefaultMethods.map { it.value.lowercase() }

    override fun process(resolver: Resolver): List<KSAnnotated> {
        val symbols = resolver.getSymbolsWithAnnotation(
            annotationName = Ktorceful::class.qualifiedName.toString()
        )

        if (!symbols.iterator().hasNext())
            return emptyList()

        val file = codeGenerator.createNewFile(
            dependencies = Dependencies(false, *resolver.getAllFiles().toList().toTypedArray()),
            packageName = "dev.herrrta.ktorceful",
            fileName = "GeneratedKtorcefulRoutes",
        )

        file.use { out ->
            PrintWriter(out).use { writer ->
                val mainGenerator = KtorcefulGeneratorImpl(writer)

                symbols
                    .filter { it is KSClassDeclaration && it.validate() }
                    .forEach {
                        it.accept(Visitor(mainGenerator, writer, resolver), Unit)
                    }

                mainGenerator.run()
            }
        }
        return emptyList()
    }

    inner class Visitor(
        private val generator: KtorcefulGeneratorImpl,
        private val writer: PrintWriter,
        private val resolver: Resolver
    ) : KSVisitorVoid() {
        override fun visitClassDeclaration(classDeclaration: KSClassDeclaration, data: Unit) {
            if (classDeclaration.classKind != ClassKind.CLASS) {
                logger.error("Only class can be annotated with @${Ktorceful::class.simpleName}")
                return
            }

            if (classDeclaration.annotations.find { it.shortName.asString() == "Resource" } == null) {
                logger.error("@${Ktorceful::class.simpleName} requires ${Symbols.KTOR_RESOURCES_RESOURCE}")
                return
            }

            generator.add(
                KtorcefulRoutingGeneratorImpl(
                    w = writer,
                    declaration = classDeclaration,
                    functions = classDeclaration
                        .getImplSuperTypeFunctions<HTTPMethod>(resolver)
                        .filter { it.key in allowedMethodNames },
                    entityFunctions = classDeclaration
                        .getImplSuperTypeFunctions<EntityRoute>(resolver)
                        .filter { it.key in allowedMethodNames },
                )
            )
        }
    }
}