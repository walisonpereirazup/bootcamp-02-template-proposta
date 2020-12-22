package br.com.zup.credicardzero.card

import br.com.zup.credicardzero.proposal.Proposal
import java.util.*
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.OneToOne
import javax.persistence.Table
import javax.validation.Valid
import javax.validation.constraints.NotBlank

@Entity
@Table(name = "cards")
data class Card(
    @Id
    val id: UUID = UUID.randomUUID(),

    @field:NotBlank
    val cardholderName: String,

    @field:NotBlank
    val cardNumber: String,

    @OneToOne
    @Valid
    val proposal: Proposal
)
