package dev.herrrta.ktorceful.processor

import com.google.devtools.ksp.processing.SymbolProcessorProvider
import com.tschuchort.compiletesting.JvmCompilationResult
import com.tschuchort.compiletesting.KotlinCompilation
import com.tschuchort.compiletesting.SourceFile
import com.tschuchort.compiletesting.kspIncremental
import com.tschuchort.compiletesting.symbolProcessorProviders
import com.tschuchort.compiletesting.useKsp2
import org.intellij.lang.annotations.Language
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import org.junit.After
import org.junit.Before
import java.nio.file.Path
import kotlin.io.path.ExperimentalPathApi
import kotlin.io.path.deleteRecursively
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCompilerApi::class)
class KtorcefulProcessorTest {
    private lateinit var tempDir: Path

    @OptIn(ExperimentalPathApi::class)
    @Before
    fun before() {
        tempDir = kotlin.io.path.createTempDirectory("test")
        tempDir.deleteRecursively()
    }

    @OptIn(ExperimentalPathApi::class)
    @After
    fun after() {
        tempDir.deleteRecursively()
    }

    @Test
    fun `test compile`() {
        val result = compile(
            """
            package test
            class TestClass
            """,
            KtorcefulProcessorProvider()
        )
        assertEquals(KotlinCompilation.ExitCode.OK, result.exitCode)
    }

    @Test
    fun `test missing resource annotation`() {
        val result = compile(
            """
            package test
            import dev.herrrta.ktorceful.annotation.Ktorceful
            @Ktorceful
            class Test
            """,
            KtorcefulProcessorProvider()
        )
        assertEquals(KotlinCompilation.ExitCode.COMPILATION_ERROR, result.exitCode)
    }

    @Test
    fun `test basic route compile`() {
        val result = compile(
            """
            package test
            import io.ktor.resources.Resource
            import io.ktor.server.routing.RoutingCall
            import dev.herrrta.ktorceful.annotation.Ktorceful
            import dev.herrrta.ktorceful.core.interfaces.*

            @Ktorceful
            @Resource("test")
            class TestClass: Get, Post {
                override suspend fun get(call: RoutingCall) = Unit
                override suspend fun post(call: RoutingCall) = Unit
            }
            """,
            KtorcefulProcessorProvider()
        )
        assertEquals(KotlinCompilation.ExitCode.OK, result.exitCode)
    }

    @Test
    fun `test entity route compile`() {
        val result = compile(
            """
            package test
            import io.ktor.resources.Resource
            import io.ktor.server.routing.RoutingCall
            import kotlin.reflect.KClass
            import dev.herrrta.ktorceful.annotation.Ktorceful
            import dev.herrrta.ktorceful.core.interfaces.*
            import dev.herrrta.ktorceful.dao.interfaces.*

            data class Test(val id: Int)

            @Ktorceful
            @Resource("test")
            class TestClass: CreateEntity<Test>, GetEntity<Test, Int> {
                override suspend fun get(call: RoutingCall) = Unit
                override suspend fun get(call: RoutingCall, pk: Int, klass: KClass<Test>) = Unit
                override suspend fun post(call: RoutingCall, klass: KClass<Test>) = Unit
                override suspend fun getInstance(pk: Int): Test? = null
            }
            """,
            KtorcefulProcessorProvider()
        )
        assertEquals(KotlinCompilation.ExitCode.OK, result.exitCode)
    }

    private fun compile(
        @Language("kotlin") contents: String,
        vararg providers: SymbolProcessorProvider,
    ): JvmCompilationResult {
        val sourceFile = SourceFile.kotlin("source.kt", contents)
        val compilation = KotlinCompilation()
            .apply {
                useKsp2()
                languageVersion = "2.0"
                apiVersion = "1.7"
                workingDir = tempDir.toFile()
                inheritClassPath = true
                symbolProcessorProviders = providers.toMutableList()
                sources = listOf(sourceFile)
                verbose = false
                kspIncremental = false
            }
        return compilation.compile()
    }
}