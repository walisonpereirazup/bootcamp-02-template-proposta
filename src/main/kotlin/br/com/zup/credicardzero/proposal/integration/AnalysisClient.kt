package br.com.zup.credicardzero.proposal.integration

import feign.Headers
import feign.RequestLine
import java.util.*

@Headers("Content-Type: application/json")
interface AnalysisClient {
    @RequestLine("POST /api/solicitacao")
    fun analyze(payload: InAnalysis): OutAnalysis
}

data class InAnalysis(
        val documento: String,
        val nome: String,
        val idProposta: UUID
)

data class OutAnalysis(
        val resultadoSolicitacao: AnalysisResult,
        val documento: String,
        val nome: String,
        val idProposta: String
)

enum class AnalysisResult {
    COM_RESTRICAO, SEM_RESTRICAO
}
