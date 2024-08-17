package br.com.lucastribioli.auth_transaction.controller

import br.com.lucastribioli.auth_transaction.domain.dto.ResultTransactionDTO
import br.com.lucastribioli.auth_transaction.domain.dto.TransactionDTO
import br.com.lucastribioli.auth_transaction.domain.enum.CodeTransaction
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
    private val accountService: AccountService
) {
    val log: Logger = LoggerFactory.getLogger(AuthorizeController::class.java)
    @PostMapping("/simple",
        produces = [MediaType.APPLICATION_JSON_VALUE],
        consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun simple(@RequestBody transaction: TransactionDTO): ResponseEntity<ResultTransactionDTO> {
        accountService.findByNumber(transaction.accountId)
        log.info("Simple transaction: $transaction")
        return ResponseEntity.ok(ResultTransactionDTO(CodeTransaction.APPROVED.code))
    }

    @PostMapping("/no-benefit",
        produces = [MediaType.APPLICATION_JSON_VALUE],
        consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun noBenefit(transaction: TransactionDTO): ResponseEntity<ResultTransactionDTO> {
        log.info("No benefit transaction: $transaction")
        return ResponseEntity.ok(ResultTransactionDTO(CodeTransaction.APPROVED.code))
    }

}