package br.com.zup.credicardzero.proposal

import br.com.zup.credicardzero.proposal.integration.AnalysisProcessor
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.http.ResponseEntity
import org.springframework.transaction.support.TransactionTemplate
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.util.UriComponentsBuilder
import java.util.*
import javax.persistence.EntityManager
import javax.validation.Valid

@RestController
class ProposalController(
    val entityManager: EntityManager,
    val transactionTemplate: TransactionTemplate,
    val analysisProcessor: AnalysisProcessor
) {

    @PostMapping("/proposals")
    fun create(
        @Valid @RequestBody request: ProposalRequest,
        uriComponentsBuilder: UriComponentsBuilder
    ): ResponseEntity<Any> {
        val proposal = request.toModel(entityManager)
        transactionTemplate.execute { entityManager.persist(proposal) }

        analysisProcessor.processAnalysis(proposal)

        val uri = uriComponentsBuilder
            .path("/proposals/${proposal.id}")
            .build()
            .toUri()
        return ResponseEntity.created(uri).build()
    }

    @GetMapping("/proposals/{id}")
    fun retrieve(@PathVariable id: UUID): ResponseEntity<ProposalResponse> {
        val proposal = entityManager.find(Proposal::class.java, id)
            ?: throw ResponseStatusException(NOT_FOUND, "resource does not exists")

        return ResponseEntity.ok(ProposalResponse(proposal))
    }

}
