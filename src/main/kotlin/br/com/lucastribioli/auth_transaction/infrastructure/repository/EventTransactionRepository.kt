package br.com.lucastribioli.auth_transaction.infrastructure.repository

import br.com.lucastribioli.auth_transaction.domain.entity.EventTransaction
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface EventTransactionRepository: JpaRepository<EventTransaction, Long> {
}