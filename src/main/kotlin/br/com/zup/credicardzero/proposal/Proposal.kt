package br.com.zup.credicardzero.proposal

import br.com.zup.credicardzero.proposal.Proposal.Companion.FIND_BY_DOCUMENT
import br.com.zup.credicardzero.proposal.Proposal.Companion.FIND_BY_STATUS
import java.math.BigDecimal
import java.util.*
import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Positive

@Entity
@Table(
    name = "proposals", uniqueConstraints = [
        UniqueConstraint(columnNames = ["document"], name = "uk_proposal_document"),
    ]
)
@NamedQueries(
    NamedQuery(name = FIND_BY_DOCUMENT, query = "select p from Proposal p where p.document = :document"),
    NamedQuery(name = FIND_BY_STATUS, query = "select p from Proposal p where p.status = :status")
)
data class Proposal(
    @Id
    val id: UUID = UUID.randomUUID(),

    @field:NotBlank
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
    val salary: BigDecimal,

    @Enumerated(EnumType.STRING)
    var status: ProposalStatus = ProposalStatus.DEFERRED
) {

    var cardNumber: String = ""
        set(value) {
            field = value
            status = ProposalStatus.ELIGIBLE_WITH_CARD
        }

    companion object {
        const val FIND_BY_DOCUMENT: String = "Proposal.findByDocument"
        const val FIND_BY_STATUS: String = "Proposal.findByStatus"
    }
}
