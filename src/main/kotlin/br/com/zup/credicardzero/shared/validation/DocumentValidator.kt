package br.com.zup.credicardzero.shared.validation

import org.hibernate.validator.internal.constraintvalidators.hv.br.CNPJValidator
import org.hibernate.validator.internal.constraintvalidators.hv.br.CPFValidator
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class DocumentValidator : ConstraintValidator<Document, String?> {

    override fun initialize(constraint: Document) {
        // nothing to initialize
    }

    override fun isValid(value: String?, context: ConstraintValidatorContext?): Boolean {
        if (value.isNullOrBlank()) {
            return true
        }

        val cpfValidator = CPFValidator()
        val cnpjValidator = CNPJValidator()

        cpfValidator.initialize(null)
        cnpjValidator.initialize(null)

        return cpfValidator.isValid(value, context) || cnpjValidator.isValid(value, context)
    }

}
