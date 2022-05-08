package com.payconiq.stock.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.math.BigDecimal
import java.sql.Timestamp

@JsonIgnoreProperties(ignoreUnknown = true)
class StockRequest {
    @JsonIgnore
    private var id = 0
    private var name: String? = null
    private var currentPrice: BigDecimal? = null

    @JsonIgnore
    private var lastUpdate: Timestamp? = null

    constructor() {}

    constructor(id: Int, name: String?, currentPrice: BigDecimal?, lastUpdate: Timestamp?) {
        this.id = id
        this.name = name
        this.currentPrice = currentPrice
        this.lastUpdate = lastUpdate
    }

    constructor(name: String?, currentPrice: BigDecimal?) {
        this.name = name
        this.currentPrice = currentPrice
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