package com.payconiq.stock.dto

import com.payconiq.stock.entity.Stock


class StockDto {
    private var totalRows: Long? = null
    private var totalPages: Int? = null
    private var data: List<Stock>? = null

    fun getTotalRows(): Long? {
        return totalRows
    }

    fun setTotalRows(totalRows: Long?) {
        this.totalRows = totalRows
    }

    fun getTotalPages(): Int? {
        return totalPages
    }

    fun setTotalPages(totalPages: Int?) {
        this.totalPages = totalPages
    }

    fun getData(): List<Stock?>? {
        return data
    }

    fun setData(data: List<Stock?>?) {
        this.data = data as List<Stock>?
    }
}