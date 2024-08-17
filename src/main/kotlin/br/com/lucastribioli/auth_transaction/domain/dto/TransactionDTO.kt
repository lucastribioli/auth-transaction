package br.com.lucastribioli.auth_transaction.domain.dto

import java.math.BigDecimal

data class TransactionDTO(
    val id: String,
    val accountId: String,
    val amount: BigDecimal,
    val mcc: String?,
    val merchant: String
)
