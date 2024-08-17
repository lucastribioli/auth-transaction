package br.com.lucastribioli.auth_transaction.controller

import br.com.lucastribioli.auth_transaction.application.service.TransactionService
import br.com.lucastribioli.auth_transaction.domain.dto.ResultTransactionDTO
import br.com.lucastribioli.auth_transaction.domain.dto.TransactionDTO
import br.com.lucastribioli.auth_transaction.domain.enum.CodeTransaction

import com.fasterxml.jackson.module.kotlin.jsonMapper
import io.github.serpro69.kfaker.Faker
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import java.math.BigDecimal


private const val PAYLOAD_TRANSACTION_MEAL = "src/test/resources/payload_transaction_meal.json"

class AuthorizeControllerTest {
    private val transactionService = mockk<TransactionService>()
    private val jsonMapper = jsonMapper()
    private val faker = Faker()

    private val transactionDTO = TransactionDTO(
        id = faker.code.asin(),
        accountId = faker.code.asin(),
        amount = BigDecimal("100.00"),
        mcc = "5812",
        merchant = faker.company.name()
    )

    @Test
    fun `should return 200 when transaction is authorized and return code APPROVED`() {
        every { transactionService.debit(transactionDTO) } returns ResultTransactionDTO(
            message = CodeTransaction.APPROVED.name,
            code = CodeTransaction.APPROVED.code
        )
        val result = AuthorizeController(transactionService).transaction(transactionDTO)

        assertEquals(HttpStatus.OK, result.statusCode)
        assertEquals(CodeTransaction.APPROVED.code, result.body?.code)
    }
    @Test
    fun `should return 200 when transaction is authorized and return code DENIED`() {
        every { transactionService.debit(transactionDTO) } returns ResultTransactionDTO(
            message = CodeTransaction.DENIED.name,
            code = CodeTransaction.DENIED.code
        )
        val result = AuthorizeController(transactionService).transaction(transactionDTO)

        assertEquals(HttpStatus.OK, result.statusCode)
        assertEquals(CodeTransaction.DENIED.code, result.body?.code)
    }

    @Test
    fun `should return 200 when transaction is authorized and return code GENERIC_ERROR`() {
        every { transactionService.debit(transactionDTO) } returns ResultTransactionDTO(
            message = CodeTransaction.GENERIC_ERROR.name,
            code = CodeTransaction.GENERIC_ERROR.code
        )
        val result = AuthorizeController(transactionService).transaction(transactionDTO)

        assertEquals(HttpStatus.OK, result.statusCode)
        assertEquals(CodeTransaction.GENERIC_ERROR.code, result.body?.code)
    }


}