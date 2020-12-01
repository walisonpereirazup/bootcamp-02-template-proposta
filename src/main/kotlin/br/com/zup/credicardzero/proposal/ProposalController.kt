package br.com.zup.credicardzero.proposal

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.util.UriComponentsBuilder
import javax.persistence.EntityManager
import javax.transaction.Transactional
import javax.validation.Valid

@RestController
class ProposalController(val entityManager: EntityManager) {

    @Transactional
    @PostMapping("/proposals")
    fun create(
            @Valid @RequestBody request: ProposalRequest,
            uriComponentsBuilder: UriComponentsBuilder
    ): ResponseEntity<Any> {
        val proposal = request.toModel(entityManager)
        entityManager.persist(proposal)

        val uri = uriComponentsBuilder
                .path("/proposals/${proposal.id}")
                .build()
                .toUri()
        return ResponseEntity.created(uri).build()
    }

}
