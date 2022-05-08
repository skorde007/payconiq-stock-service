package com.payconiq.stock.controller

import com.payconiq.stock.exception.StockFoundException
import com.payconiq.stock.model.StockRequest
import com.payconiq.stock.service.StockService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.validation.Valid


@RestController
@RequestMapping("/api")
class StockController {

    @Autowired
    lateinit var stockService: StockService

    @GetMapping(value= ["/info"])
    fun info(): String {
        return "This is Payconiq Stock Service"
    }

    /**
     * Get a list of stocks
     *
     * @param index
     * @param pageSize
     * @return Object
     * @throws Exception
     */
    @GetMapping(value= ["/stocks"], produces= [MediaType.APPLICATION_JSON_VALUE])
    @Throws(Exception::class)
    fun getAllStocks(
        @RequestParam(name = "index", defaultValue = "0", required = false) index: Int,
        @RequestParam(name = "pageSize", defaultValue = "100", required = false) pageSize: Int
    ): ResponseEntity<Any?>? {
        return ResponseEntity(stockService.getAllStocks(index, pageSize), HttpStatus.OK)
    }

    /**
     * Create a stock
     *
     * @param stockReqObj
     * @return Object
     * @throws Exception
     */
    @PostMapping(value= ["/stocks"],
        produces= [MediaType.APPLICATION_JSON_VALUE],
        consumes= [MediaType.APPLICATION_JSON_VALUE])
    @Throws(Exception::class)
    fun createStock(@Valid @RequestBody stockRequest: StockRequest?): ResponseEntity<Any?>? {
        val stock = stockService.createStock(stockRequest)
        if (Objects.isNull(stock)) {
            throw StockFoundException("Stock is already exist")
        }
        return ResponseEntity(stock, HttpStatus.CREATED)
    }

    /**
     * Get one stock from list
     *
     * @param id
     * @return Object
     * @throws Exception
     */
    @GetMapping(value= ["/stocks/{id}"], produces= [MediaType.APPLICATION_JSON_VALUE])
    @Throws(Exception::class)
    fun getStock(@PathVariable(name = "id", required = true) id: Int): ResponseEntity<Any?>? {
        return ResponseEntity(stockService.getStock(id), HttpStatus.OK)
    }

    /**
     * Update the price of a single stock
     *
     * @param stockRequest
     * @param id
     * @return Object
     * @throws Exception
     */
    @PatchMapping(value= ["/stocks/{id}"],
        produces= [MediaType.APPLICATION_JSON_VALUE],
        consumes= [MediaType.APPLICATION_JSON_VALUE])
    @Throws(Exception::class)
    fun updateStock(
        @Valid @RequestBody stockRequest: StockRequest?,
        @PathVariable(name = "id", required = true) id: Int
    ): ResponseEntity<Any?>? {
        val stock = stockService.updateStock(stockRequest, id)
        if (Objects.isNull(stock)) {
            return ResponseEntity(HttpStatus.NOT_MODIFIED)
        } else {
            return ResponseEntity(stock, HttpStatus.OK)
        }
    }

    /**
     * Delete a single stock
     *
     * @param id
     * @throws Exception
     */
    @DeleteMapping(value= ["/stocks/{id}"], produces= [MediaType.APPLICATION_JSON_VALUE])
    @Throws(Exception::class)
    fun deleteStock(@PathVariable(name = "id", required = true) id: Int): ResponseEntity<Void?>? {
        val status = stockService.deleteStock(id)
        return if (!status) {
            ResponseEntity(HttpStatus.NOT_FOUND)
        } else ResponseEntity(HttpStatus.OK)
    }
}