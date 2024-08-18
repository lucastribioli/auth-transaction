package br.com.lucastribioli.auth_transaction.infrastructure.runner

import br.com.lucastribioli.auth_transaction.domain.entity.Account
import br.com.lucastribioli.auth_transaction.domain.entity.Merchant
import br.com.lucastribioli.auth_transaction.domain.enum.MCC
import br.com.lucastribioli.auth_transaction.infrastructure.repository.AccountRepository
import br.com.lucastribioli.auth_transaction.infrastructure.repository.MerchantRepository
import jakarta.transaction.Transactional
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component
import java.math.BigDecimal

@Component
class DataInitializer(
    private val accountRepository: AccountRepository,
    private val merchantRepository: MerchantRepository
) : ApplicationRunner {

    @Transactional
    override fun run(args: ApplicationArguments?) {
        if (accountRepository.count() == 0L) {
            accountRepository.saveAll(
                listOf(
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

            )
        }
        if (merchantRepository.count() == 0L) {
            merchantRepository.saveAll(
                listOf(
                    Merchant(
                        name = "LOJA DO MANUEL ALAGOAS BR",
                        segment = MCC.FOOD
                    ),
                    Merchant(
                        name = "PADARIA DO ZE SAO PAULO BR",
                        segment = MCC.MEAL
                    ),
                    Merchant(
                        name = "POSTO IPIRANGA MINAS GERAIS BR",
                        segment = MCC.CASH
                    ),
                )

            )
        }
    }
}