package br.com.lucastribioli.auth_transaction.domain.enum

enum class MCC {
    FOOD,
    MEAL,
    CASH;

    companion object {
        fun getMCC(code: String): MCC {
            return when (code) {
                "5411", "5412" -> FOOD
                "5811", "5812" -> MEAL
                else -> CASH
            }
        }
    }
}