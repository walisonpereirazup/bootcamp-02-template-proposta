package br.com.zup.credicardzero.proposal

import br.com.zup.credicardzero.card.Card
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
    val card: CardResponse?,
) {
    constructor(proposal: Proposal) : this(
        id = proposal.id,
        document = proposal.document,
        email = proposal.email,
        name = proposal.name,
        address = proposal.address,
        salary = proposal.salary,
        status = proposal.status,
        card = proposal.card?.let { CardResponse(it) }
    )

    class CardResponse(val cardholderName: String, val cardNumber: String) {
        constructor(card: Card) : this(card.cardholderName, card.cardNumber)
    }
}
