package br.com.lucastribioli.auth_transaction.infrastructure.repository

import br.com.lucastribioli.auth_transaction.domain.entity.Account
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AccountRepository: JpaRepository<Account, Long> {
    fun findByNumber(number: String): Account?

}