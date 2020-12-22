package br.com.zup.credicardzero.biometric

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.util.UriComponentsBuilder
import java.util.*
import javax.persistence.EntityManager
import javax.transaction.Transactional
import javax.validation.Valid

@RestController
class BiometricController(
    val entityManager: EntityManager,
) {

    @Transactional
    @PostMapping("/cards/{cardId}/biometrics")
    fun create(
        @PathVariable cardId: String,
        @Valid @RequestBody request: BiometricRequest,
        uriComponentsBuilder: UriComponentsBuilder
    ): ResponseEntity<*> {
        val biometric = request.toModel(cardId, entityManager)
        entityManager.persist(biometric)

        val uri = uriComponentsBuilder
            .path("/cards/${cardId}/biometrics/${biometric}")
            .build()
            .toUri()
        return ResponseEntity.created(uri).build<Any>()
    }

}
