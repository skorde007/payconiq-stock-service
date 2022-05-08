package com.payconiq.stock.service

import com.payconiq.stock.dao.StockDao
import com.payconiq.stock.dto.StockDto
import com.payconiq.stock.entity.Stock
import com.payconiq.stock.model.StockRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.sql.Timestamp
import java.util.*

@Service
class StockServiceImpl: StockService {

    @Autowired
    lateinit var stockDao: StockDao

    @Throws(Exception::class)
    private fun prepareReportData(page: Page<Stock?>?): StockDto? {
        val totalElements: Long = page?.totalElements ?: 0
        val totalPages: Int = page?.totalPages ?: 0
        val stock: List<Stock> = page?.content as List<Stock>
        val response = StockDto()
        response.setTotalRows(totalElements)
        response.setTotalPages(totalPages)
        response.setData(stock)
        return response
    }

    @Throws(Exception::class)
    override fun getAllStocks(index: Int, pageSize: Int): StockDto? {
        val paging: Pageable = PageRequest.of(index, pageSize)
        val stock: Page<Stock?>? = stockDao.findAllStocks(paging)
        return prepareReportData(stock)
    }

    @Throws(Exception::class)
    override fun createStock(stockRequest: StockRequest?): Stock? {
        if (stockRequest != null) {
            val stockExist: Stock? = stockDao.getStockByName(stockRequest.getName())
            if (Objects.isNull(stockExist)) {
                val stockObj = Stock()
                stockObj.setName(if (stockRequest.getName() != null) stockRequest.getName() else null)
                stockObj.setCurrentPrice(if (stockRequest.getCurrentPrice() != null) stockRequest.getCurrentPrice() else null)
                val stamp = Timestamp(Date().time)
                stockObj.setLastUpdate(stamp)
                val stock: Stock = stockDao.save(stockObj)
                if (!Objects.isNull(stock))
                    return stock
            }
        }
        return null
    }

    @Throws(Exception::class)
    override fun getStock(id: Int): Stock? {
        val stock: Optional<Stock> = stockDao.findById(id)
        return stock.get()
    }

    @Throws(Exception::class)
    override fun updateStock(stockRequest: StockRequest?, id: Int): Stock? {
        if (stockRequest != null) {
            val stockExist: Optional<Stock> = stockDao.findById(id)
            if (stockExist.isPresent) {
                val stockObj: Stock = stockExist.get()
                stockObj.setName(if (stockRequest.getName() != null) stockRequest.getName() else stockObj.getName())
                stockObj.setCurrentPrice(if (stockRequest.getCurrentPrice() != null) stockRequest.getCurrentPrice() else null)
                val stamp = Timestamp(Date().time)
                stockObj.setLastUpdate(stamp)
                val stock: Stock = stockDao.save(stockObj)
                if (!Objects.isNull(stock))
                    return stock
            }
        }
        return null
    }

    @Throws(Exception::class)
    override fun deleteStock(id: Int): Boolean {
        val stockExist: Optional<Stock> = stockDao.findById(id)
        if (stockExist.isPresent) {
            stockDao.deleteById(stockExist.get().getId())
            return true
        }
        return false
    }
}