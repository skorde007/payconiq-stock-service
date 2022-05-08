package com.payconiq.stock

import com.fasterxml.jackson.databind.ObjectMapper
import com.payconiq.stock.dto.StockDto
import com.payconiq.stock.entity.Stock
import com.payconiq.stock.model.StockRequest
import com.payconiq.stock.service.StockService
import org.json.JSONObject
import org.junit.Assert
import org.junit.Assert.assertTrue
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.math.BigDecimal
import java.sql.Timestamp
import java.util.*
import kotlin.collections.HashMap


@AutoConfigureMockMvc
@SpringBootTest
class PayconiqStockServiceApplicationTests {

	@Autowired
	lateinit var mockMvc: MockMvc

	@Mock
	lateinit var stockService: StockService

	@Test
	@Throws(Exception::class)
	fun testGetAllStocks() {
		val stamp = Timestamp(Date().time)
		val stockList = listOf(
			Stock(1, "TV", BigDecimal("552209"), stamp),
			Stock(2, "Mobile", BigDecimal("122207"), stamp)
		)
		val stockDto = StockDto()
		stockDto.setTotalRows(0L)
		stockDto.setTotalPages(1)
		stockDto.setData(stockList)
		Mockito.`when`(stockService.getAllStocks(Mockito.any(Int::class.java), Mockito.any(Int::class.java)))
			.thenReturn(stockDto)
		mockMvc.perform(
			MockMvcRequestBuilders.get("/api/stocks")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
		)
			.andExpect(MockMvcResultMatchers.status().isOk)
	}

	@Test
	@Throws(Exception::class)
	fun testCreateStock() {
		val stamp = Timestamp(Date().getTime())
		val stockRequest = StockRequest("Laptop", BigDecimal("7500.00"))
		stockRequest.setLastUpdate(stamp)
		val stock = Stock(3, "Laptop", BigDecimal("7500.00"), stamp)
		Mockito.`when`(stockService.createStock(Mockito.any(StockRequest::class.java))).thenReturn(stock)
		val mvcResult = mockMvc.perform(
			MockMvcRequestBuilders.post("/api/stocks")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(ObjectMapper().writeValueAsString(stockRequest))
		)
			.andReturn()
		val status = mvcResult.response.status
		val map = HashMap<String, String>()
		val jObject = JSONObject(mvcResult.response.contentAsString)
		val keys: Iterator<*> = jObject.keys()
		while (keys.hasNext()) {
			val key = keys.next() as String
			val value: String = jObject.getString(key)
			map[key] = value
		}
		if (status == HttpStatus.FOUND.value()) {
			Assert.assertEquals(map["message"], "Stock is already exist")
		} else {
			assertTrue(map.size > 0)
			Assert.assertEquals(map["id"]!!.toInt(), stock.getId())
		}
	}

	@Test
	@Throws(Exception::class)
	fun testGetStock() {
		val id = 1
		val stamp = Timestamp(Date().getTime())
		val stock = Stock(1, "TV", BigDecimal("552209"), stamp)
		Mockito.`when`(stockService.getStock(Mockito.any(Int::class.java))).thenReturn(stock)
		mockMvc.perform(
			MockMvcRequestBuilders.get("/api/stocks/$id")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
		)
			.andExpect(MockMvcResultMatchers.status().isOk)
	}

	@Test
	@Throws(Exception::class)
	fun testUpdateStock() {
		val caseId1 = 1
		val caseId2 = 4
		val stamp = Timestamp(Date().getTime())
		val stockRequest = StockRequest("Laptop", BigDecimal("6400.00"))
		stockRequest.setLastUpdate(stamp)
		val stock = Stock(1, "Laptop", BigDecimal("6400.00"), stamp)
		Mockito.`when`(
			stockService.updateStock(
				Mockito.any(StockRequest::class.java), Mockito.any(
					Int::class.java
				)
			)
		).thenReturn(stock)
		mockMvc.perform(
			MockMvcRequestBuilders.patch("/api/stocks/$caseId1")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(ObjectMapper().writeValueAsString(stockRequest))
		)
			.andExpect(MockMvcResultMatchers.status().isOk)
		Mockito.`when`(
			stockService.updateStock(
				Mockito.any(StockRequest::class.java), Mockito.any(
					Int::class.java
				)
			)
		).thenReturn(stock)
		mockMvc.perform(
			MockMvcRequestBuilders.patch("/api/stocks/$caseId2")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(ObjectMapper().writeValueAsString(stockRequest))
		)
			.andExpect(MockMvcResultMatchers.status().isNotModified)
	}

	@Test
	@Throws(Exception::class)
	fun testDeleteStock() {
		val senario = 2
		Mockito.`when`(stockService.deleteStock(Mockito.any(Int::class.java))).thenReturn(true)
		mockMvc.perform(
			MockMvcRequestBuilders.delete("/api/stocks/$senario")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
		)
			.andExpect(MockMvcResultMatchers.status().isOk)
		Mockito.`when`(stockService.deleteStock(Mockito.any(Int::class.java))).thenReturn(false)
		mockMvc.perform(
			MockMvcRequestBuilders.delete("/api/stocks/$senario")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
		)
			.andExpect(MockMvcResultMatchers.status().isNotFound)
	}
}
