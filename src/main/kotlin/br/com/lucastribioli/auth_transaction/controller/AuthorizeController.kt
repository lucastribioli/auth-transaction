package br.com.lucastribioli.auth_transaction.controller

import br.com.lucastribioli.auth_transaction.application.service.TransactionService
import br.com.lucastribioli.auth_transaction.domain.dto.ResultTransactionDTO
import br.com.lucastribioli.auth_transaction.domain.dto.TransactionDTO
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/authorize")
class AuthorizeController(
    private val transactionService: TransactionService
) {
    val log: Logger = LoggerFactory.getLogger(AuthorizeController::class.java)
    @PostMapping("/transaction",
        produces = [MediaType.APPLICATION_JSON_VALUE],
        consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun transaction(@RequestBody transaction: TransactionDTO): ResponseEntity<ResultTransactionDTO> {
        log.info("Transaction: $transaction")
        return ResponseEntity.ok(transactionService.debit(transaction))
    }


}