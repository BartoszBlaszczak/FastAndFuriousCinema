package hex.ffcinema.domain

import hex.ffcinema.application.Properties
import hex.ffcinema.application.SpringConfiguration
import hex.ffcinema.domain.port.OMDMovieDetails
import hex.ffcinema.domain.port.OMDRepository
import io.kotest.core.spec.style.FunSpec
import io.kotest.spring.SpringListener
import org.springframework.boot.autoconfigure.ImportAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.openfeign.FeignAutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary

@SpringBootTest(classes = [SpringTestConfiguration::class])
@ImportAutoConfiguration(classes = [FeignAutoConfiguration::class])
abstract class Kotest(body: FunSpec.() -> Unit) : FunSpec() {
	override fun listeners() = listOf(SpringListener)
	init { body() }
}

val omdMovieTestDetails = OMDMovieDetails(
	released = "22 Jun 2001",
	runtime = "106 min",
	plot = "plot...",
	imdbRating = 5.toBigDecimal(),
)

@Configuration
open class SpringTestConfiguration(properties: Properties)
	: SpringConfiguration(properties) {
	
	@Bean
	@Primary
	open fun omdRepository(): OMDRepository = object : OMDRepository {
		override fun getDetails(omdId: String) = omdMovieTestDetails
	}
	
	@Bean open fun testDao() = TestDao(dbConnection())
}

