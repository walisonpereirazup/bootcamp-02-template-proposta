package br.com.zup.credicardzero.proposal

import java.math.BigDecimal
import java.util.*

class ProposalResponse(
    val id: UUID,
    val document: String,
    val email: String,
    val name: String,
    val address: String,
    val salary: BigDecimal,
    var status: ProposalStatus,
    val cardNumber: String,
) {
    constructor(proposal: Proposal) : this(
        id = proposal.id,
        document = proposal.document,
        email = proposal.email,
        name = proposal.name,
        address = proposal.address,
        salary = proposal.salary,
        status = proposal.status,
        cardNumber = proposal.cardNumber
    )
}
