package com.payconiq.stock.dao

import com.payconiq.stock.entity.Stock
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import javax.transaction.Transactional


interface StockDao: JpaRepository<Stock, Int> {

    @Transactional
    @Query("select s from Stock s")
    fun findAllStocks(paging: Pageable?): Page<Stock?>?

    @Transactional
    @Query("select s from Stock s where s.name= :name")
    fun getStockByName(@Param("name") name: String?): Stock?
}