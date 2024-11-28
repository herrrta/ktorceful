@file:OptIn(KspExperimental::class)

package dev.herrrta.ktorceful.processor

import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.getDeclaredFunctions
import com.google.devtools.ksp.getKotlinClassByName
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSTypeReference
import com.google.devtools.ksp.symbol.KSValueParameter

fun Sequence<KSTypeReference>.filterSuperTypesRecursively(predicate: (KSTypeReference) -> Boolean): Sequence<KSTypeReference> {
    val sequence = filter(predicate)

    if (sequence.any(predicate)) {
        val rootFunctions = sequence
            .flatMap { (it.resolve().declaration as KSClassDeclaration).superTypes }
            .filterSuperTypesRecursively(predicate)

        return sequence + rootFunctions
    }
    return sequence
}

internal inline fun <reified T> KSClassDeclaration.getImplSuperTypeFunctions(resolver: Resolver): Map<String, List<KSValueParameter>> {
    val functions = this.getDeclaredFunctions()
    val baseType =
        resolver.getKotlinClassByName(T::class.qualifiedName.toString())?.asStarProjectedType()
    return superTypes
        .filterSuperTypesRecursively { baseType?.isAssignableFrom(it.resolve()) == true }
        .flatMap { superType ->
            (superType.resolve().declaration as KSClassDeclaration)
                .getDeclaredFunctions()
                .mapNotNull { superTypeFunc ->
                    functions.firstOrNull { func ->
                            val funcParams = func.parameters.map { it.name?.asString() }

                            superTypeFunc.simpleName.asString() == func.simpleName.asString() &&
                            funcParams.containsAll(
                                superTypeFunc.parameters.map { it.name?.asString() }
                            )
                        }
                        ?.parameters
                        ?.let { superTypeFunc.toString() to it }
                }
        }.toMap()
}


internal inline fun <reified T : Annotation> KSClassDeclaration.firstAnnotation() =
    annotations.firstOrNull { it.shortName.asString() == T::class.simpleName }