package br.com.lucastribioli.auth_transaction.domain.entity

import br.com.lucastribioli.auth_transaction.domain.enum.MCC
import jakarta.persistence.*

@Entity
@Table(name = "merchant")
data class Merchant(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Column(length = 150)
    val name: String = "",
    @Column(length = 50)
    @Enumerated(EnumType.STRING)
    val segment: MCC = MCC.CASH
)
