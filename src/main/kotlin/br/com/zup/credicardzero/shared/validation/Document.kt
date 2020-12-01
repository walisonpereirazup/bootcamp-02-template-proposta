package br.com.zup.credicardzero.shared.validation

import javax.validation.Constraint
import javax.validation.Payload
import kotlin.reflect.KClass

@MustBeDocumented
@Constraint(validatedBy = [DocumentValidator::class])
@Target(AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class Document(
        val message: String = "invalid document number",
        val groups: Array<KClass<*>> = [],
        val payload: Array<KClass<out Payload>> = [])
