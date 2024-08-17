package br.com.lucastribioli.auth_transaction.domain.enum

enum class CodeTransaction (val code: String) {
    APPROVED("00"),
    DENIED("51"),
    GENERIC_ERROR("07")
}