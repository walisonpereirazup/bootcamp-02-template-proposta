package br.com.zup.credicardzero.card.integration

import feign.Headers
import feign.Param
import feign.RequestLine
import java.util.*

@Headers("Content-Type: application/json")
interface CardClient {
    @RequestLine("POST /api/cartoes/{idProposta}")
    fun card(@Param("idProposta") idProposta: UUID): OutCard
}

data class OutCard(val id: String)
