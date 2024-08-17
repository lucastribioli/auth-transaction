package br.com.lucastribioli.auth_transaction.application.service

import br.com.lucastribioli.auth_transaction.domain.dto.TransactionDTO
import br.com.lucastribioli.auth_transaction.domain.entity.Account
import br.com.lucastribioli.auth_transaction.domain.enum.CodeTransaction
import br.com.lucastribioli.auth_transaction.infrastructure.repository.AccountRepository
import io.github.serpro69.kfaker.Faker
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class TransactionServiceTest {
    private val accountRepository = mockk<AccountRepository>()
    private val transactionService = TransactionService(accountRepository)
    private val faker = Faker()

    @Test
    fun `should return a result transaction with code APPROVED to mcc FOOD number 5411`() {
        every { accountRepository.findByNumber(any()) } returns Account(
            id = faker.random.nextLong(),
            number = faker.code.asin(),
            balanceCash = BigDecimal(100),
            balanceFood = BigDecimal(100),
            balanceMeal = BigDecimal(100)
        )
        every { accountRepository.save(any()) } returns Account(
            id = faker.random.nextLong(),
            number = faker.code.asin(),
            balanceCash = BigDecimal(100),
            balanceFood = BigDecimal(100),
            balanceMeal = BigDecimal(100)
        )
        val transaction = TransactionDTO(
            id = faker.code.asin(),
            accountId = faker.code.asin(),
            amount = BigDecimal(100),
            mcc = "5411",
            merchant = faker.name.neutralFirstName()
        )
        val result = transactionService.debit(transaction)
        assertEquals(CodeTransaction.APPROVED.code, result.code)
    }

    @Test
    fun `should return a result transaction with code APPROVED to mcc FOOD number 5412`() {
        every { accountRepository.findByNumber(any()) } returns Account(
            id = faker.random.nextLong(),
            number = faker.code.asin(),
            balanceCash = BigDecimal.ZERO,
            balanceFood = BigDecimal(100),
            balanceMeal = BigDecimal.ZERO
        )
        every { accountRepository.save(any()) } returns Account(
            id = faker.random.nextLong(),
            number = faker.code.asin(),
            balanceCash = BigDecimal.ZERO,
            balanceFood = BigDecimal(100),
            balanceMeal = BigDecimal.ZERO
        )
        val transaction = TransactionDTO(
            id = faker.code.asin(),
            accountId = faker.code.asin(),
            amount = BigDecimal(100),
            mcc = "5412",
            merchant = faker.name.neutralFirstName()
        )
        val result = transactionService.debit(transaction)
        assertEquals(CodeTransaction.APPROVED.code, result.code)
    }

    @Test
    fun `should return a result transaction with code APPROVED to mcc MEAL number 5811`() {
        every { accountRepository.findByNumber(any()) } returns Account(
            id = faker.random.nextLong(),
            number = faker.code.asin(),
            balanceCash = BigDecimal.ZERO,
            balanceFood = BigDecimal.ZERO,
            balanceMeal = BigDecimal(100)
        )
        every { accountRepository.save(any()) } returns Account(
            id = faker.random.nextLong(),
            number = faker.code.asin(),
            balanceCash = BigDecimal.ZERO,
            balanceFood = BigDecimal.ZERO,
            balanceMeal = BigDecimal(100)
        )
        val transaction = TransactionDTO(
            id = faker.code.asin(),
            accountId = faker.code.asin(),
            amount = BigDecimal(100),
            mcc = "5811",
            merchant = faker.name.neutralFirstName()
        )
        val result = transactionService.debit(transaction)
        assertEquals(CodeTransaction.APPROVED.code, result.code)
    }

    @Test
    fun `should return a result transaction with code APPROVED to mcc MEAL number 5812`() {
        every { accountRepository.findByNumber(any()) } returns Account(
            id = faker.random.nextLong(),
            number = faker.code.asin(),
            balanceCash = BigDecimal.ZERO,
            balanceFood = BigDecimal.ZERO,
            balanceMeal = BigDecimal(100)
        )
        every { accountRepository.save(any()) } returns Account(
            id = faker.random.nextLong(),
            number = faker.code.asin(),
            balanceCash = BigDecimal.ZERO,
            balanceFood = BigDecimal.ZERO,
            balanceMeal = BigDecimal(100)
        )
        val transaction = TransactionDTO(
            id = faker.code.asin(),
            accountId = faker.code.asin(),
            amount = BigDecimal(100),
            mcc = "5812",
            merchant = faker.name.neutralFirstName()
        )
        val result = transactionService.debit(transaction)
        assertEquals(CodeTransaction.APPROVED.code, result.code)
    }

    @Test
    fun `should return a result transaction with code APPROVED to mcc CASH number any`() {
        every { accountRepository.findByNumber(any()) } returns Account(
            id = faker.random.nextLong(),
            number = faker.code.asin(),
            balanceCash = BigDecimal(100),
            balanceFood = BigDecimal.ZERO,
            balanceMeal = BigDecimal(100)
        )
        every { accountRepository.save(any()) } returns Account(
            id = faker.random.nextLong(),
            number = faker.code.asin(),
            balanceCash = BigDecimal(100),
            balanceFood = BigDecimal.ZERO,
            balanceMeal = BigDecimal(100)
        )
        val transaction = TransactionDTO(
            id = faker.code.asin(),
            accountId = faker.code.asin(),
            amount = BigDecimal(100),
            mcc = faker.random.nextInt(10000).toString(),
            merchant = faker.name.neutralFirstName()
        )
        val result = transactionService.debit(transaction)
        assertEquals(CodeTransaction.APPROVED.code, result.code)
    }

    @Test
    fun `should return a result transaction with code APPROVED to mcc CASH number empty`() {
        every { accountRepository.findByNumber(any()) } returns Account(
            id = faker.random.nextLong(),
            number = faker.code.asin(),
            balanceCash = BigDecimal(100),
            balanceFood = BigDecimal.ZERO,
            balanceMeal = BigDecimal(100)
        )
        every { accountRepository.save(any()) } returns Account(
            id = faker.random.nextLong(),
            number = faker.code.asin(),
            balanceCash = BigDecimal(100),
            balanceFood = BigDecimal.ZERO,
            balanceMeal = BigDecimal(100)
        )
        val transaction = TransactionDTO(
            id = faker.code.asin(),
            accountId = faker.code.asin(),
            amount = BigDecimal(100),
            mcc = "",
            merchant = faker.name.neutralFirstName()
        )
        val result = transactionService.debit(transaction)
        assertEquals(CodeTransaction.APPROVED.code, result.code)
    }

    @Test
    fun `should return a result transaction with code DENIED to mcc FOOD when insufficient amount`() {
        every { accountRepository.findByNumber(any()) } returns Account(
            id = faker.random.nextLong(),
            number = faker.code.asin(),
            balanceCash = BigDecimal(12),
            balanceFood = BigDecimal.ZERO,
            balanceMeal = BigDecimal(100)
        )
        every { accountRepository.save(any()) } returns Account(
            id = faker.random.nextLong(),
            number = faker.code.asin(),
            balanceCash = BigDecimal(12),
            balanceFood = BigDecimal.ZERO,
            balanceMeal = BigDecimal(100)
        )
        val transaction = TransactionDTO(
            id = faker.code.asin(),
            accountId = faker.code.asin(),
            amount = BigDecimal(100),
            mcc = "5411",
            merchant = faker.name.neutralFirstName()
        )
        val result = transactionService.debit(transaction)
        assertEquals(CodeTransaction.DENIED.code, result.code)
    }

    @Test
    fun `should return a result transaction with code DENIED to mcc MEAL when insufficient amount`() {
        every { accountRepository.findByNumber(any()) } returns Account(
            id = faker.random.nextLong(),
            number = faker.code.asin(),
            balanceCash = BigDecimal(10),
            balanceFood = BigDecimal(100),
            balanceMeal = BigDecimal.ZERO
        )
        every { accountRepository.save(any()) } returns Account(
            id = faker.random.nextLong(),
            number = faker.code.asin(),
            balanceCash = BigDecimal(10),
            balanceFood = BigDecimal(100),
            balanceMeal = BigDecimal.ZERO
        )
        val transaction = TransactionDTO(
            id = faker.code.asin(),
            accountId = faker.code.asin(),
            amount = BigDecimal(100),
            mcc = "5811",
            merchant = faker.name.neutralFirstName()
        )
        val result = transactionService.debit(transaction)
        assertEquals(CodeTransaction.DENIED.code, result.code)
    }

    @Test
    fun `should return a result transaction with code APPROVED to mcc MEAL using CASH`() {
        every { accountRepository.findByNumber(any()) } returns Account(
            id = faker.random.nextLong(),
            number = faker.code.asin(),
            balanceCash = BigDecimal(10),
            balanceFood = BigDecimal(100),
            balanceMeal = BigDecimal(90)
        )
        every { accountRepository.save(any()) } returns Account(
            id = faker.random.nextLong(),
            number = faker.code.asin(),
            balanceCash = BigDecimal(10),
            balanceFood = BigDecimal(100),
            balanceMeal = BigDecimal(90)
        )
        val transaction = TransactionDTO(
            id = faker.code.asin(),
            accountId = faker.code.asin(),
            amount = BigDecimal(100),
            mcc = "5811",
            merchant = faker.name.neutralFirstName()
        )
        val result = transactionService.debit(transaction)
        assertEquals(CodeTransaction.APPROVED.code, result.code)
    }
    @Test
    fun `should return a result transaction with code APPROVED to mcc FOOD using CASH`() {
        every { accountRepository.findByNumber(any()) } returns Account(
            id = faker.random.nextLong(),
            number = faker.code.asin(),
            balanceCash = BigDecimal(99),
            balanceFood = BigDecimal(1),
            balanceMeal = BigDecimal(2)
        )
        every { accountRepository.save(any()) } returns Account(
            id = faker.random.nextLong(),
            number = faker.code.asin(),
            balanceCash = BigDecimal(99),
            balanceFood = BigDecimal(1),
            balanceMeal = BigDecimal(2)
        )
        val transaction = TransactionDTO(
            id = faker.code.asin(),
            accountId = faker.code.asin(),
            amount = BigDecimal(100),
            mcc = "5412",
            merchant = faker.name.neutralFirstName()
        )
        val result = transactionService.debit(transaction)
        assertEquals(CodeTransaction.APPROVED.code, result.code)
    }

    @Test
    fun `should return a result transaction with code DENIED to mcc CASH`() {
        every { accountRepository.findByNumber(any()) } returns Account(
            id = faker.random.nextLong(),
            number = faker.code.asin(),
            balanceCash = BigDecimal(99),
            balanceFood = BigDecimal(100),
            balanceMeal = BigDecimal(100)
        )
        every { accountRepository.save(any()) } returns Account(
            id = faker.random.nextLong(),
            number = faker.code.asin(),
            balanceCash = BigDecimal(99),
            balanceFood = BigDecimal(100),
            balanceMeal = BigDecimal(100)
        )
        val transaction = TransactionDTO(
            id = faker.code.asin(),
            accountId = faker.code.asin(),
            amount = BigDecimal(100),
            mcc = "",
            merchant = faker.name.neutralFirstName()
        )
        val result = transactionService.debit(transaction)
        assertEquals(CodeTransaction.DENIED.code, result.code)
    }

}