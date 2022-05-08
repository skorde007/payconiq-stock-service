package com.payconiq.stock.entity

import java.math.BigDecimal
import java.sql.Timestamp
import javax.persistence.*
import javax.validation.constraints.NotNull


@Entity
@Table(name = "stock")
class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private var id = 0

    @Column(name = "name")
    @NotNull(message = "name is mandatory")
    private var name: String? = null

    @Column(name = "current_price")
    @NotNull(message = "currentPrice is mandatory")
    private var currentPrice: BigDecimal? = null

    @Column(name = "last_update")
    private var lastUpdate: Timestamp? = null

    constructor() {}

    constructor(id: Int, name: String?, currentPrice: BigDecimal?, lastUpdate: Timestamp?) {
        this.id = id
        this.name = name
        this.currentPrice = currentPrice
        this.lastUpdate = lastUpdate
    }

    fun getId(): Int {
        return id
    }

    fun setId(id: Int) {
        this.id = id
    }

    fun getName(): String? {
        return name
    }

    fun setName(name: String?) {
        this.name = name
    }

    fun getCurrentPrice(): BigDecimal? {
        return currentPrice
    }

    fun setCurrentPrice(currentPrice: BigDecimal?) {
        this.currentPrice = currentPrice
    }

    fun getLastUpdate(): Timestamp? {
        return lastUpdate
    }

    fun setLastUpdate(lastUpdate: Timestamp?) {
        this.lastUpdate = lastUpdate
    }
}