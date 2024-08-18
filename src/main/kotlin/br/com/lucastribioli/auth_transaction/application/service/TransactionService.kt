package br.com.lucastribioli.auth_transaction.application.service

import br.com.lucastribioli.auth_transaction.domain.dto.ResultTransactionDTO
import br.com.lucastribioli.auth_transaction.domain.dto.TransactionDTO
import br.com.lucastribioli.auth_transaction.domain.entity.Account
import br.com.lucastribioli.auth_transaction.domain.entity.EventTransaction
import br.com.lucastribioli.auth_transaction.domain.enum.CodeTransaction
import br.com.lucastribioli.auth_transaction.domain.enum.MCC
import br.com.lucastribioli.auth_transaction.infrastructure.repository.AccountRepository
import br.com.lucastribioli.auth_transaction.infrastructure.repository.EventTransactionRepository
import br.com.lucastribioli.auth_transaction.infrastructure.repository.MerchantRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class TransactionService(
    private val accountRepository: AccountRepository,
    private val merchantRepository: MerchantRepository,
    private val eventTransactionRepository: EventTransactionRepository
) {
    private val log: Logger = LoggerFactory.getLogger(TransactionService::class.java)
    private val msgAccountNotFound = "Account not found"
    private val msgInsufficientBalance = "Insufficient balance"
    private val resultTransactionApprove =
        ResultTransactionDTO(message = CodeTransaction.APPROVED.name, code = CodeTransaction.APPROVED.code)
    private val resultTransactionDeniedInsufficientBalance =
        ResultTransactionDTO(message = msgInsufficientBalance, code = CodeTransaction.DENIED.code)
    private val resultTransactionDeniedAccountNotFound =
        ResultTransactionDTO(message = msgAccountNotFound, code = CodeTransaction.DENIED.code)

    fun debit(transaction: TransactionDTO): ResultTransactionDTO {
        return try {
            when (getMCCMerchantOrNull(transaction) ?: MCC.getMCC(transaction.mcc!!)) {
                MCC.MEAL -> debitMeal(transaction)
                MCC.FOOD -> debitFood(transaction)
                else -> debitCash(transaction)
            }
        } catch (error: Exception) {
            log.error(error.message)
            ResultTransactionDTO(code = CodeTransaction.GENERIC_ERROR.code)
        }
    }

    private fun debitMeal(transaction: TransactionDTO): ResultTransactionDTO {
        val account = accountRepository.findByNumber(transaction.accountId)
            ?: return resultTransactionDeniedAccountNotFound
        if (account.balanceMeal < transaction.amount.abs()) {
            if (account.balanceMeal.plus(account.balanceCash) < transaction.amount.abs()) {
                return resultTransactionDeniedInsufficientBalance
            } else {
                val valueMissed = transaction.amount.abs().minus(account.balanceMeal).abs()
                account.balanceMeal = account.balanceMeal.minus(transaction.amount.abs().minus(valueMissed))
                account.balanceCash = valueMissed
            }
        } else {
            account.balanceMeal = account.balanceMeal.minus(transaction.amount.abs())
        }
        accountRepository.save(account)
        saveEventTransaction(transaction, account)
        return resultTransactionApprove
    }

    private fun debitFood(transaction: TransactionDTO): ResultTransactionDTO {
        val account = accountRepository.findByNumber(transaction.accountId)
            ?: return resultTransactionDeniedAccountNotFound
        if (account.balanceFood < transaction.amount.abs()) {
            if (account.balanceFood.plus(account.balanceCash) < transaction.amount.abs()) {
                return resultTransactionDeniedInsufficientBalance
            } else {
                val valueMissed = transaction.amount.abs().minus(account.balanceFood).abs()
                account.balanceFood = account.balanceFood.minus(transaction.amount.abs().minus(valueMissed))
                account.balanceCash = valueMissed
            }
        } else {
            account.balanceFood = account.balanceFood.minus(transaction.amount.abs())
        }
        accountRepository.save(account)
        saveEventTransaction(transaction, account)
        return resultTransactionApprove
    }

    private fun debitCash(transaction: TransactionDTO): ResultTransactionDTO {
        val account = accountRepository.findByNumber(transaction.accountId)
            ?: return resultTransactionDeniedAccountNotFound
        if (account.balanceCash < transaction.amount.abs()) return resultTransactionDeniedInsufficientBalance
        account.balanceCash = account.balanceCash.minus(transaction.amount.abs())
        accountRepository.save(account)
        saveEventTransaction(transaction, account)
        return resultTransactionApprove
    }

    private fun getMCCMerchantOrNull(transaction: TransactionDTO): MCC? {
        val merchant = merchantRepository.findByName(removeExtraSpaces(transaction.merchant.uppercase()))
        if (merchant != null) return merchant.segment
        return null
    }

    private fun saveEventTransaction(transaction: TransactionDTO, account: Account) {
        val event = EventTransaction(
            idPayloadTransaction = transaction.id,
            account = account,
            amount = transaction.amount,
            mcc = transaction.mcc ?: "",
            merchant = transaction.merchant
        )
        eventTransactionRepository.save(event)
    }

    private fun removeExtraSpaces(merchant: String): String {
        return merchant.replace("\\s+".toRegex(), " ")
    }

}