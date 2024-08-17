package br.com.lucastribioli.auth_transaction.infrastructure.runner

import br.com.lucastribioli.auth_transaction.domain.entity.Account
import br.com.lucastribioli.auth_transaction.domain.entity.Merchant
import br.com.lucastribioli.auth_transaction.domain.enum.MCC
import br.com.lucastribioli.auth_transaction.infrastructure.repository.AccountRepository
import br.com.lucastribioli.auth_transaction.infrastructure.repository.MerchantRepository
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import java.math.BigDecimal

class DataInitializarTest {
    private val accountRepository = mockk<AccountRepository>()
    private val merchantRepository = mockk<MerchantRepository>()
    private val listAccounts = listOf(
        Account(
            number = "1234",
            name = "Lucas",
            balanceFood = BigDecimal.ZERO,
            balanceMeal = BigDecimal("450.00"),
            balanceCash = BigDecimal("1000.55")
        ),
        Account(
            number = "77693",
            name = "João",
            balanceFood = BigDecimal("988.00"),
            balanceMeal = BigDecimal("98.00"),
            balanceCash = BigDecimal("1000.55")
        ),
        Account(
            number = "523",
            name = "Maria",
            balanceFood = BigDecimal("68.00"),
            balanceMeal = BigDecimal("980.00"),
            balanceCash = BigDecimal.ZERO,
        ),
        Account(
            number = "671",
            name = "Fulano",
            balanceFood = BigDecimal.ZERO,
            balanceMeal = BigDecimal.ZERO,
            balanceCash = BigDecimal.ZERO,
        )
    )
    private val listMerchants = listOf(
        Merchant(
            name = "Loja do seu Zé",
            segment = MCC.FOOD
        ),
        Merchant(
            name = "PADARIA DO ZE               SAO PAULO BR",
            segment = MCC.MEAL
        ),
        Merchant(
            name = "Posto Ipiranga",
            segment = MCC.CASH
        ),
    )

    @Test
    fun `should initialize data without errors to count zero`() {
        every { accountRepository.count() } returns 0
        every { merchantRepository.count() } returns 0
        every {
            accountRepository.saveAll(listAccounts)
        } returns listOf()
        every { merchantRepository.saveAll(listMerchants) } returns listOf()
        assertDoesNotThrow { DataInitializer(accountRepository, merchantRepository).run(null) }
    }

    @Test
    fun `should initialize data without errors to count not zero`() {
        every { accountRepository.count() } returns 1
        every { merchantRepository.count() } returns 1
        assertDoesNotThrow { DataInitializer(accountRepository, merchantRepository).run(null) }
    }

}