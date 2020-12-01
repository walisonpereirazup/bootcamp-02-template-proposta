package br.com.zup.credicardzero.proposal

import br.com.zup.credicardzero.proposal.Proposal.Companion.FIND_BY_DOCUMENT
import br.com.zup.credicardzero.shared.validation.Document
import org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY
import org.springframework.util.Assert
import org.springframework.web.server.ResponseStatusException
import java.math.BigDecimal
import javax.persistence.EntityManager
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Positive

class ProposalRequest(
        @field:NotBlank
        @field:Document
        val document: String,

        @field:NotBlank
        @field:Email
        val email: String,

        @field:NotBlank
        val name: String,

        @field:NotBlank
        val address: String,

        @field:NotNull
        @field:Positive
        val salary: BigDecimal
) {
    fun toModel(entityManager: EntityManager): Proposal {
        verifyDocumentExistence(entityManager)

        return Proposal(document = document, email = email, name = name, address = address, salary = salary)
    }

    private fun verifyDocumentExistence(entityManager: EntityManager) {
        val proposal = entityManager.createNamedQuery(FIND_BY_DOCUMENT, Proposal::class.java)
                .setParameter("document", document)
                .resultList

        Assert.state(proposal.size <= 1, "shouldn't exist more than one proposal with same document number")

        if (proposal.isNotEmpty()) {
            throw ResponseStatusException(UNPROCESSABLE_ENTITY, "document has already been taken")
        }
    }
}
