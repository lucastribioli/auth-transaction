package br.com.lucastribioli.auth_transaction.domain.entity

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "event_transaction")
data class EventTransaction(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Column(name = "id_payload_transaction", length = 100)
    val idPayloadTransaction: String = "",
    @ManyToOne
    @JoinColumn(name = "id_account")
    val account: Account,
    val amount: BigDecimal,
    @Column(length = 50)
    val mcc: String = "",
    @Column(length = 150)
    val merchant: String? = "",
    val dateTime: LocalDateTime = LocalDateTime.now()
)
