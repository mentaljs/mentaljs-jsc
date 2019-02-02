package com.openland.open.processor

import com.google.auto.service.AutoService
import com.openland.open.*
import com.squareup.kotlinpoet.*
import java.io.File
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.Processor
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.ElementKind
import javax.lang.model.element.TypeElement
import javax.lang.model.type.ExecutableType
import javax.lang.model.type.TypeKind

@AutoService(Processor::class)
class MentalModuleProcessor : AbstractProcessor() {

    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        return mutableSetOf(MentalModule::class.java.name)
    }

    override fun getSupportedSourceVersion(): SourceVersion {
        return SourceVersion.latest()
    }

    override fun process(p0: MutableSet<out TypeElement>, roundEnv: RoundEnvironment): Boolean {
        val path = processingEnv.options["kapt.kotlin.generated"]

        roundEnv.getElementsAnnotatedWith(MentalModule::class.java).forEach {
            val pack = processingEnv.elementUtils.getPackageOf(it).toString()
            val name = it.simpleName.toString()
            val rname = name + "_Descriptor"

            val fileBuilder = FileSpec.builder(pack, "$rname.kt")
                    .addStaticImport("com.openland.open", "ModuleMethodInvoker")
            val typeSpecBuilder = TypeSpec.objectBuilder(rname)
                    .superclass(ModuleDescriptor::class.java)

            var body = "return mapOf<String, ModuleMethodInvoker>("
            var bodyFirst = true
            it.enclosedElements
                    .filter { it.kind === ElementKind.METHOD }
                    .filter { it.getAnnotation(MentalMethod::class.java) != null }
                    .forEach {
                        val parameters = (it.asType() as ExecutableType).parameterTypes

                        var invokerBody = "(source as $name).${it.simpleName}("
                        invokerBody += parameters.mapIndexed { index, it ->
                            if (it.kind == TypeKind.INT) {
                                "(arguments[$index] as MethodArgument.NumberArgument).value.toInt()"
                            } else if (it.toString() == "java.lang.String") {
                                "(arguments[$index] as MethodArgument.StringArgument).value"
                            } else {
                                throw Error(it.toString())
                            }
                        }.joinToString(separator = ", ")
                        invokerBody += ")\n"

                        if (bodyFirst) {
                            bodyFirst = false
                        } else {
                            body += ", "
                        }
                        body += "\"${it.simpleName}\" to ${rname}_${it.simpleName}"

                        fileBuilder.addType(TypeSpec.objectBuilder(rname + "_" + it.simpleName.toString())
                                .superclass(ModuleMethodInvoker::class.java)
                                .addModifiers(KModifier.PRIVATE)
                                .addFunction(FunSpec.builder("invoke")
                                        .addModifiers(KModifier.OVERRIDE)
                                        .addParameter("source", Any::class)
                                        .addParameter("arguments", Array<MethodArgument>::class.java)
                                        .addCode(invokerBody)
                                        .build())
                                .build())
                    }
            body += ")\n"

            val file = fileBuilder.addType(
                    typeSpecBuilder.addFunction(
                            FunSpec.builder("getModuleMethods")
                                    .addModifiers(KModifier.OVERRIDE)
                                    .addCode(body)
                                    .build()
                    ).build())
                    .build()
            file.writeTo(File(path))
        }


        return true
    }
}