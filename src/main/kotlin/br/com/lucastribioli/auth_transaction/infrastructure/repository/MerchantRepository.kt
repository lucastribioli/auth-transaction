package br.com.lucastribioli.auth_transaction.infrastructure.repository

import br.com.lucastribioli.auth_transaction.domain.entity.Merchant
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MerchantRepository: JpaRepository<Merchant, Long> {
    fun findByName(name: String): Merchant?
}