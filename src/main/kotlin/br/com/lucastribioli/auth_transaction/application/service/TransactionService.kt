package br.com.lucastribioli.auth_transaction.application.service

import br.com.lucastribioli.auth_transaction.domain.dto.ResultTransactionDTO
import br.com.lucastribioli.auth_transaction.domain.dto.TransactionDTO
import br.com.lucastribioli.auth_transaction.domain.enum.CodeTransaction
import br.com.lucastribioli.auth_transaction.domain.enum.MCC
import br.com.lucastribioli.auth_transaction.infrastructure.repository.AccountRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class TransactionService(
    private val accountRepository: AccountRepository
) {
    private val log: Logger = LoggerFactory.getLogger(TransactionService::class.java)
    fun debit(transaction: TransactionDTO): ResultTransactionDTO {
        return try {
            when (MCC.getMCC(transaction.mcc!!)) {
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
            ?: return ResultTransactionDTO(message = "Account not found", code = CodeTransaction.DENIED.code)
        if (account.balanceMeal < transaction.amount.abs()) {
            if (account.balanceMeal.add(account.balanceCash) < transaction.amount.abs()) {
                return ResultTransactionDTO(
                    message = "Insufficient balance MEAL",
                    code = CodeTransaction.DENIED.code
                )
            }else {
                val valueMissed =  transaction.amount.abs().minus(account.balanceMeal).abs()
                account.balanceMeal = account.balanceMeal.minus(transaction.amount.abs().minus(valueMissed))
                account.balanceCash = valueMissed
            }
        } else {
            account.balanceMeal = account.balanceMeal.minus(transaction.amount.abs())
        }
        accountRepository.save(account)
        return ResultTransactionDTO(message = CodeTransaction.APPROVED.name , code = CodeTransaction.APPROVED.code)
    }

    private fun debitFood(transaction: TransactionDTO): ResultTransactionDTO {
        val account = accountRepository.findByNumber(transaction.accountId)
            ?: return ResultTransactionDTO(message = "Account not found", code = CodeTransaction.DENIED.code)
        if (account.balanceFood < transaction.amount.abs()) {
            if (account.balanceFood.add(account.balanceCash) < transaction.amount.abs()) {
                return ResultTransactionDTO(
                    message = "Insufficient balance FOOD",
                    code = CodeTransaction.DENIED.code
                )
            }else {
                val valueMissed =  transaction.amount.abs().minus(account.balanceFood).abs()
                account.balanceFood = account.balanceFood.minus(transaction.amount.abs().minus(valueMissed))
                account.balanceCash = valueMissed
            }
        } else {
            account.balanceFood = account.balanceFood.minus(transaction.amount.abs())
        }

        return ResultTransactionDTO(message = CodeTransaction.APPROVED.name, code = CodeTransaction.APPROVED.code)
    }

    private fun debitCash(transaction: TransactionDTO): ResultTransactionDTO {
        val account = accountRepository.findByNumber(transaction.accountId)
            ?: return ResultTransactionDTO(message = "Account not found", code = CodeTransaction.DENIED.code)
        if (account.balanceCash < transaction.amount.abs()) return ResultTransactionDTO(
            message = "Insufficient balance CASH",
            code = CodeTransaction.DENIED.code
        )
        account.balanceCash = account.balanceCash.minus(transaction.amount.abs())
        accountRepository.save(account)

        return ResultTransactionDTO(message = CodeTransaction.APPROVED.name, code = CodeTransaction.APPROVED.code)
    }

}