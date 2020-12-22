package br.com.zup.credicardzero.biometric

import br.com.zup.credicardzero.proposal.Proposal
import javax.persistence.EntityManager
import javax.validation.constraints.NotBlank

class BiometricRequest(
    fingerprint: String
) {

    fun toModel(cardId: String, entityManager: EntityManager): Any? {
        val proposal = entityManager.createNamedQuery(Proposal.FIND_BY_DOCUMENT, Proposal::class.java)
        return null
    }

}
