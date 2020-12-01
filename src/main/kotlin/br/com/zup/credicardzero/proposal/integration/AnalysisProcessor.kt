package br.com.zup.credicardzero.proposal.integration

import br.com.zup.credicardzero.proposal.Proposal
import br.com.zup.credicardzero.proposal.ProposalStatus
import feign.Feign
import feign.FeignException
import feign.gson.GsonDecoder
import feign.gson.GsonEncoder
import org.springframework.core.env.Environment
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.server.ResponseStatusException
import javax.persistence.EntityManager
import javax.transaction.Transactional

@Component
class AnalysisProcessor(
        private val entityManager: EntityManager,
        private val environment: Environment,
) {

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    fun processAnalysis(proposal: Proposal) {
        val status = retrieveProposalStatus(proposal)
        proposal.status = status
        entityManager.merge(proposal)
    }

    private fun retrieveProposalStatus(proposal: Proposal): ProposalStatus {
        return try {
            val analysis = buildClient().analyze(
                    InAnalysis(documento = proposal.document, nome = proposal.name, idProposta = proposal.id))
            when (analysis.resultadoSolicitacao) {
                AnalysisResult.SEM_RESTRICAO -> ProposalStatus.ELIGIBLE
                AnalysisResult.COM_RESTRICAO -> ProposalStatus.NOT_ELIGIBLE
            }
        } catch (ex: FeignException.UnprocessableEntity) {
            ProposalStatus.NOT_ELIGIBLE
        } catch (ex: FeignException) {
            throw ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Couldn't connect to the remote server")
        }
    }

    private fun buildClient(): AnalysisClient {
        return Feign.builder()
                .encoder(GsonEncoder())
                .decoder(GsonDecoder())
                .target(AnalysisClient::class.java, environment.getProperty("analysis.url"))
    }

}
