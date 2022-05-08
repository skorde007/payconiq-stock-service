package com.payconiq.stock

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

/**
 * NOTE:
 * To Access Swagger UI for testing API endpoints: http://localhost:9999/payconiq/swagger-ui.html
 *
 * To Access H2 DB for checking records : http://localhost:9999/payconiq/h2-console
 *
 * To Build Docker Image: docker build -f Dockerfile -t payconiq-stock-service .
 *
 * To Run Docker Container: docker run -p 9999:9999 payconiq-stock-service
 *
 * @author skorde
 *
 */
@SpringBootApplication
@OpenAPIDefinition(info = Info(title = "Stock API", version = "1.0", description = "Payconiq stock service assignment project with Kotlin"))
open class PayconiqStockServiceApplication

fun main(args: Array<String>) {
	runApplication<PayconiqStockServiceApplication>(*args)
}
