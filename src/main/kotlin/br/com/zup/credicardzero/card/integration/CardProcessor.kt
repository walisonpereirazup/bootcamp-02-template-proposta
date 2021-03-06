package br.com.zup.credicardzero.card.integration

import br.com.zup.credicardzero.card.Card
import br.com.zup.credicardzero.proposal.Proposal
import br.com.zup.credicardzero.proposal.ProposalStatus
import feign.Feign
import feign.FeignException
import feign.gson.GsonDecoder
import feign.gson.GsonEncoder
import org.springframework.core.env.Environment
import org.springframework.stereotype.Component
import javax.persistence.EntityManager
import javax.transaction.Transactional

@Component
class CardProcessor(
    private val entityManager: EntityManager,
    private val environment: Environment,
) {

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    fun processCard(proposal: Proposal) {
        try {
            val response = buildClient().card(idProposta = proposal.id)
            val card = Card(
                cardholderName = response.titular,
                cardNumber = response.id,
                proposal = proposal
            )
            entityManager.persist(card)
            proposal.status = ProposalStatus.ELIGIBLE_WITH_CARD
            entityManager.merge(proposal)
        } catch (ex: FeignException.UnprocessableEntity) {
        }
    }

    private fun buildClient(): CardClient {
        return Feign.builder()
            .encoder(GsonEncoder())
            .decoder(GsonDecoder())
            .target(CardClient::class.java, environment.getProperty("cards.url"))
    }

}
