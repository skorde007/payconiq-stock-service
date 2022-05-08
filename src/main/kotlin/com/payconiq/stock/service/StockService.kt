package com.payconiq.stock.service

import com.payconiq.stock.dto.StockDto
import com.payconiq.stock.entity.Stock
import com.payconiq.stock.model.StockRequest

interface StockService {

    @Throws(Exception::class)
    fun getAllStocks(index: Int, pageSize: Int): StockDto?

    @Throws(Exception::class)
    fun createStock(stockRequest: StockRequest?): Stock?

    @Throws(Exception::class)
    fun getStock(id: Int): Stock?

    @Throws(Exception::class)
    fun updateStock(stockRequest: StockRequest?, id: Int): Stock?

    @Throws(Exception::class)
    fun deleteStock(id: Int): Boolean
}