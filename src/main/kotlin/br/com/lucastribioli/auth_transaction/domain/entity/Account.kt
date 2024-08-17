package br.com.lucastribioli.auth_transaction.domain.entity

import jakarta.persistence.*
import java.math.BigDecimal


@Entity
@Table(name = "account")
data class Account(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Column(length = 70, unique = true)
    val number: String = "",
    @Column(length = 150)
    val name: String = "",
    @Column(name= "balance_food")
    var balanceFood: BigDecimal = BigDecimal.ZERO,
    @Column(name= "balance_meal")
    var balanceMeal: BigDecimal = BigDecimal.ZERO,
    @Column(name= "balance_cash")
    var balanceCash: BigDecimal = BigDecimal.ZERO,
)
