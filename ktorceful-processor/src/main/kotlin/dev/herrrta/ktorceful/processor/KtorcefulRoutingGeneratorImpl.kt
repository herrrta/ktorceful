package dev.herrrta.ktorceful.processor

import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.isAnnotationPresent
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSTypeReference
import com.google.devtools.ksp.symbol.KSValueParameter
import dev.herrrta.ktorceful.annotation.ResourceCompanion
import java.io.PrintWriter


internal class KtorcefulRoutingGeneratorImpl(
    val entityFunctions: Map<String, List<KSValueParameter>>,
    override val declaration: KSClassDeclaration,
    override val functions: Map<String, List<KSValueParameter>>,
    override val w: PrintWriter
) : KtorcefulRoutingGenerator {
    override fun run() {
        w.println()
        generateTypeSafeResource()
        super.run()
    }

    override fun generateRouting() {
        functions.forEach { func ->
            w.println("        ${func.key}<$declaration> { it.${func.key}(call) }")
        }

        entityFunctions.forEach { func ->
            val pkType = func.value
                .find { it.name?.asString() == "pk" }
                ?.type

            val klassType = func.value
                .find { it.name?.asString() == "klass" }?.type?.element?.typeArguments
                ?.first()?.type?.resolve()?.declaration?.qualifiedName?.asString()

            if (pkType !== null) {
                w.println("        ${func.key}<${pkRoute(pkType)}> {")
                w.println("            it.parent.${func.key}(call, it.pk, ${klassType}::class)")
            }
            else {
                w.println("        ${func.key}<$declaration> {")
                w.println("            it.${func.key}(call, ${klassType}::class)")
            }
            w.println("        }")
        }
    }

    private fun pkRoute(type: KSTypeReference?) = "EntityResource.Pk<$declaration, $type>"

    @OptIn(KspExperimental::class)
    private fun generateTypeSafeResource() {
        val companion = declaration.declarations
            .filterIsInstance<KSClassDeclaration>()
            .firstOrNull { it.isCompanionObject && it.isAnnotationPresent(ResourceCompanion::class) }

        entityFunctions.forEach { func ->
            func.value
                .find { it.name?.asString() == "pk" }
                ?.let { param ->
                    companion?.let {
                       w.println("fun $declaration.$it.pk(pk: ${param.type}) = ${pkRoute(param.type)}($declaration(), pk)")
                    }
                }
        }
    }
}