package br.com.lucastribioli.auth_transaction.application.service

import br.com.lucastribioli.auth_transaction.domain.dto.ResultTransactionDTO
import br.com.lucastribioli.auth_transaction.domain.dto.TransactionDTO
import br.com.lucastribioli.auth_transaction.domain.enum.CodeTransaction
import br.com.lucastribioli.auth_transaction.domain.enum.MCC
import br.com.lucastribioli.auth_transaction.infrastructure.repository.AccountRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class TransactionSimpleService(
    private val accountRepository: AccountRepository
) {
    private val log: Logger = LoggerFactory.getLogger(TransactionSimpleService::class.java)
    fun simple(transaction: TransactionDTO): ResultTransactionDTO {
        return try {
            when (MCC.getMCC(transaction.mcc!!)) {
                MCC.MEAL -> debitMeal(transaction)
                MCC.FOOD -> print("")
                MCC.CASH -> print("")
            }
            ResultTransactionDTO(code = CodeTransaction.APPROVED.code)
        } catch (error: Exception) {
            log.error(error.message)
            ResultTransactionDTO(code = CodeTransaction.GENERIC_ERROR.code)
        }
    }

    private fun debitMeal(transaction: TransactionDTO): ResultTransactionDTO {
        val account = accountRepository.findByNumber(transaction.accountId)
            ?: return ResultTransactionDTO(message = "Account not found", code = CodeTransaction.DENIED.code)
        if (account.balanceMeal < transaction.amount.abs()) return ResultTransactionDTO(
            message = "Insufficient balance",
            code = CodeTransaction.DENIED.code
        )
        account.balanceMeal = account.balanceMeal.minus(transaction.amount.abs())
        accountRepository.save(account)

        return ResultTransactionDTO(message = "Ok", code = CodeTransaction.APPROVED.code)
    }

    private fun debitFood(transaction: TransactionDTO): ResultTransactionDTO {
        val account = accountRepository.findByNumber(transaction.accountId)
            ?: return ResultTransactionDTO(message = "Account not found", code = CodeTransaction.DENIED.code)
        if (account.balanceMeal < transaction.amount.abs()) return ResultTransactionDTO(
            message = "Insufficient balance",
            code = CodeTransaction.DENIED.code
        )
        account.balanceFood = account.balanceFood.minus(transaction.amount.abs())
        accountRepository.save(account)

        return ResultTransactionDTO(message = "Ok", code = CodeTransaction.APPROVED.code)
    }

    private fun debitCash(transaction: TransactionDTO): ResultTransactionDTO {
        val account = accountRepository.findByNumber(transaction.accountId)
            ?: return ResultTransactionDTO(message = "Account not found", code = CodeTransaction.DENIED.code)
        if (account.balanceCash < transaction.amount.abs()) return ResultTransactionDTO(
            message = "Insufficient balance",
            code = CodeTransaction.DENIED.code
        )
        account.balanceCash = account.balanceCash.minus(transaction.amount.abs())
        accountRepository.save(account)

        return ResultTransactionDTO(message = "Ok", code = CodeTransaction.APPROVED.code)
    }

}