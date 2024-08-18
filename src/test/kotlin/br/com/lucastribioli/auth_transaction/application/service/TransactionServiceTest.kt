package br.com.lucastribioli.auth_transaction.application.service

import br.com.lucastribioli.auth_transaction.domain.dto.TransactionDTO
import br.com.lucastribioli.auth_transaction.domain.entity.Account
import br.com.lucastribioli.auth_transaction.domain.entity.EventTransaction
import br.com.lucastribioli.auth_transaction.domain.entity.Merchant
import br.com.lucastribioli.auth_transaction.domain.enum.CodeTransaction
import br.com.lucastribioli.auth_transaction.domain.enum.MCC
import br.com.lucastribioli.auth_transaction.infrastructure.repository.AccountRepository
import br.com.lucastribioli.auth_transaction.infrastructure.repository.EventTransactionRepository
import br.com.lucastribioli.auth_transaction.infrastructure.repository.MerchantRepository
import io.github.serpro69.kfaker.Faker
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class TransactionServiceTest {
    private val accountRepository = mockk<AccountRepository>()
    private val merchantRepository = mockk<MerchantRepository>()
    private val eventTransactionRepository = mockk<EventTransactionRepository>()
    private val transactionService =
        TransactionService(accountRepository, merchantRepository, eventTransactionRepository)
    private val faker = Faker()
    private val eventTransaction = EventTransaction(
        id = faker.random.nextLong(),
        idPayloadTransaction = faker.code.asin(),
        account = Account(
            id = faker.random.nextLong(),
            number = faker.code.asin(),
            balanceCash = BigDecimal(100),
            balanceFood = BigDecimal(100),
            balanceMeal = BigDecimal(100)
        ),
        amount = BigDecimal(100),
        mcc = "5411",
        merchant = faker.company.name()
    )

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
        every { merchantRepository.findByName(any()) } returns null
        every { eventTransactionRepository.save(any()) } returns eventTransaction
        val transaction = TransactionDTO(
            id = faker.code.asin(),
            accountId = faker.code.asin(),
            amount = BigDecimal(100),
            mcc = "5411",
            merchant = faker.company.name()
        )
        val result = transactionService.debit(transaction)
        assertEquals(CodeTransaction.APPROVED.code, result.code)
        verify { accountRepository.save(any()) }
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
        every { merchantRepository.findByName(any()) } returns null
        every { eventTransactionRepository.save(any()) } returns eventTransaction
        val transaction = TransactionDTO(
            id = faker.code.asin(),
            accountId = faker.code.asin(),
            amount = BigDecimal(100),
            mcc = "5412",
            merchant = faker.company.name()
        )
        val result = transactionService.debit(transaction)
        assertEquals(CodeTransaction.APPROVED.code, result.code)
        verify { accountRepository.save(any()) }
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
        every { merchantRepository.findByName(any()) } returns null
        every { eventTransactionRepository.save(any()) } returns eventTransaction
        val transaction = TransactionDTO(
            id = faker.code.asin(),
            accountId = faker.code.asin(),
            amount = BigDecimal(100),
            mcc = "5811",
            merchant = faker.company.name()
        )
        val result = transactionService.debit(transaction)
        assertEquals(CodeTransaction.APPROVED.code, result.code)
        verify { accountRepository.save(any()) }
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
        every { merchantRepository.findByName(any()) } returns null
        every { eventTransactionRepository.save(any()) } returns eventTransaction
        val transaction = TransactionDTO(
            id = faker.code.asin(),
            accountId = faker.code.asin(),
            amount = BigDecimal(100),
            mcc = "5812",
            merchant = faker.company.name()
        )
        val result = transactionService.debit(transaction)
        assertEquals(CodeTransaction.APPROVED.code, result.code)
        verify { accountRepository.save(any()) }
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
        every { merchantRepository.findByName(any()) } returns null
        every { eventTransactionRepository.save(any()) } returns eventTransaction
        val transaction = TransactionDTO(
            id = faker.code.asin(),
            accountId = faker.code.asin(),
            amount = BigDecimal(100),
            mcc = faker.random.nextInt(10000).toString(),
            merchant = faker.company.name()
        )
        val result = transactionService.debit(transaction)
        assertEquals(CodeTransaction.APPROVED.code, result.code)
        verify { accountRepository.save(any()) }

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
        every { merchantRepository.findByName(any()) } returns null
        every { eventTransactionRepository.save(any()) } returns eventTransaction
        val transaction = TransactionDTO(
            id = faker.code.asin(),
            accountId = faker.code.asin(),
            amount = BigDecimal(100),
            mcc = "",
            merchant = faker.company.name()
        )
        val result = transactionService.debit(transaction)
        assertEquals(CodeTransaction.APPROVED.code, result.code)
        verify { accountRepository.save(any()) }
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
        every { merchantRepository.findByName(any()) } returns null
        every { eventTransactionRepository.save(any()) } returns eventTransaction
        val transaction = TransactionDTO(
            id = faker.code.asin(),
            accountId = faker.code.asin(),
            amount = BigDecimal(100),
            mcc = "5411",
            merchant = faker.company.name()
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
        every { merchantRepository.findByName(any()) } returns null
        every { eventTransactionRepository.save(any()) } returns eventTransaction
        val transaction = TransactionDTO(
            id = faker.code.asin(),
            accountId = faker.code.asin(),
            amount = BigDecimal(100),
            mcc = "5811",
            merchant = faker.company.name()
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
        every { merchantRepository.findByName(any()) } returns null
        every { eventTransactionRepository.save(any()) } returns eventTransaction
        val transaction = TransactionDTO(
            id = faker.code.asin(),
            accountId = faker.code.asin(),
            amount = BigDecimal(100),
            mcc = "5811",
            merchant = faker.company.name()
        )
        val result = transactionService.debit(transaction)
        assertEquals(CodeTransaction.APPROVED.code, result.code)
        verify { accountRepository.save(any()) }
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
        every { merchantRepository.findByName(any()) } returns null
        every { eventTransactionRepository.save(any()) } returns eventTransaction
        val transaction = TransactionDTO(
            id = faker.code.asin(),
            accountId = faker.code.asin(),
            amount = BigDecimal(100),
            mcc = "5412",
            merchant = faker.company.name()
        )
        val result = transactionService.debit(transaction)
        assertEquals(CodeTransaction.APPROVED.code, result.code)
        verify { accountRepository.save(any()) }
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
        every { merchantRepository.findByName(any()) } returns null
        every { eventTransactionRepository.save(any()) } returns eventTransaction
        val transaction = TransactionDTO(
            id = faker.code.asin(),
            accountId = faker.code.asin(),
            amount = BigDecimal(100),
            mcc = "",
            merchant = faker.company.name()
        )
        val result = transactionService.debit(transaction)
        assertEquals(CodeTransaction.DENIED.code, result.code)
    }

    @Test
    fun `should return a result transaction with code GENERIC_ERROR because exception`() {
        every { accountRepository.findByNumber(any()) } throws Exception("Error")
        every { merchantRepository.findByName(any()) } returns null
        every { eventTransactionRepository.save(any()) } returns eventTransaction
        val transaction = TransactionDTO(
            id = faker.code.asin(),
            accountId = faker.code.asin(),
            amount = BigDecimal(100),
            mcc = "",
            merchant = faker.company.name()
        )
        val result = transactionService.debit(transaction)
        assertEquals(CodeTransaction.GENERIC_ERROR.code, result.code)
    }

    @Test
    fun `should return a result transaction with code DENIED because account not found`() {
        every { accountRepository.findByNumber(any()) } returns null
        every { merchantRepository.findByName(any()) } returns null
        every { eventTransactionRepository.save(any()) } returns eventTransaction
        val transaction = TransactionDTO(
            id = faker.code.asin(),
            accountId = "",
            amount = BigDecimal(100),
            mcc = "",
            merchant = faker.company.name()
        )
        val result = transactionService.debit(transaction)
        assertEquals(CodeTransaction.DENIED.code, result.code)
    }

    @Test
    fun `should return a result transaction with code DENIED because Merchant is FOOD, mcc payload ignored`() {
        every { accountRepository.findByNumber(any()) } returns Account(
            id = faker.random.nextLong(),
            number = faker.code.asin(),
            balanceCash = BigDecimal(99),
            balanceFood = BigDecimal.ZERO,
            balanceMeal = BigDecimal(100)
        )
        every { accountRepository.save(any()) } returns Account(
            id = faker.random.nextLong(),
            number = faker.code.asin(),
            balanceCash = BigDecimal(99),
            balanceFood = BigDecimal.ZERO,
            balanceMeal = BigDecimal(100)
        )
        every { merchantRepository.findByName(any()) } returns Merchant(
            id = faker.random.nextLong(),
            name = faker.name.neutralFirstName(),
            segment = MCC.FOOD
        )
        every { eventTransactionRepository.save(any()) } returns eventTransaction
        val transaction = TransactionDTO(
            id = faker.code.asin(),
            accountId = faker.code.asin(),
            amount = BigDecimal(100),
            mcc = "5811",
            merchant = faker.company.name()
        )
        val result = transactionService.debit(transaction)
        assertEquals(CodeTransaction.DENIED.code, result.code)
    }

    @Test
    fun `should return a result transaction with code APPROVED because Merchant is MEAL, mcc payload ignored`() {
        every { accountRepository.findByNumber(any()) } returns Account(
            id = faker.random.nextLong(),
            number = faker.code.asin(),
            balanceCash = BigDecimal(99),
            balanceFood = BigDecimal.ZERO,
            balanceMeal = BigDecimal(100)
        )
        every { accountRepository.save(any()) } returns Account(
            id = faker.random.nextLong(),
            number = faker.code.asin(),
            balanceCash = BigDecimal(99),
            balanceFood = BigDecimal.ZERO,
            balanceMeal = BigDecimal(100)
        )
        every { merchantRepository.findByName(any()) } returns Merchant(
            id = faker.random.nextLong(),
            name = faker.name.neutralFirstName(),
            segment = MCC.MEAL
        )
        every { eventTransactionRepository.save(any()) } returns eventTransaction
        val transaction = TransactionDTO(
            id = faker.code.asin(),
            accountId = faker.code.asin(),
            amount = BigDecimal(100),
            mcc = "5412",
            merchant = faker.company.name()
        )
        val result = transactionService.debit(transaction)
        assertEquals(CodeTransaction.APPROVED.code, result.code)
    }


}