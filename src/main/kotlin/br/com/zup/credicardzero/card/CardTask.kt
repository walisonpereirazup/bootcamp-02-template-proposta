package br.com.zup.credicardzero.card

import br.com.zup.credicardzero.card.integration.CardProcessor
import br.com.zup.credicardzero.proposal.Proposal
import br.com.zup.credicardzero.proposal.Proposal.Companion.FIND_BY_STATUS
import br.com.zup.credicardzero.proposal.ProposalStatus.ELIGIBLE
import org.hibernate.LockOptions
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.transaction.support.TransactionTemplate
import java.util.concurrent.atomic.AtomicBoolean
import javax.persistence.EntityManager
import javax.persistence.LockModeType

@Component
class CardTask(
    val entityManager: EntityManager,
    val cardProcessor: CardProcessor,
    val transactionTemplate: TransactionTemplate
) : Runnable {

    private val closed: AtomicBoolean = AtomicBoolean()

    @Scheduled(fixedDelay = 5_000)
    override fun run() {

        while (!closed.get()) {
            transactionTemplate.execute {
                val unfinished = entityManager.createNamedQuery(FIND_BY_STATUS, Proposal::class.java)
                    .setParameter("status", ELIGIBLE)
                    .setMaxResults(15)
                    .setLockMode(LockModeType.PESSIMISTIC_WRITE)
                    .setHint("javax.persistence.lock.timeout", LockOptions.SKIP_LOCKED)
                    .resultList

                if (unfinished.isEmpty()) {
                    closed.set(true)
                }

                unfinished.forEach(cardProcessor::processCard)
            }
        }

    }

}
