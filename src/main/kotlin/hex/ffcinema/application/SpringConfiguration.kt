package hex.ffcinema.application

import hex.ffcinema.adapter.db.DatabaseRatingsRepository
import hex.ffcinema.adapter.web.RatingController
import hex.ffcinema.domain.usecase.AddRatingUseCase
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2
import java.sql.Connection
import java.sql.DriverManager


@Configuration
@EnableFeignClients(basePackages= ["hex.ffcinema.adapter.web"])
@EnableConfigurationProperties
@ConfigurationPropertiesScan
@EnableSwagger2
open class SpringConfiguration (
	private val properties: Properties,
) {
	@Bean open fun ratingController() = RatingController(addRatingUseCase())
	
	@Bean open fun dbConnection(): Connection = DriverManager.getConnection(properties.dbUrl)
	@Bean open fun ratingsRepository() = DatabaseRatingsRepository(dbConnection())
	
	@Bean open fun addRatingUseCase() = AddRatingUseCase(ratingsRepository())
	
	
	@Bean open fun api(): Docket {
		return Docket(DocumentationType.SWAGGER_2)
			.select()
			.apis(RequestHandlerSelectors.any())
			.paths(PathSelectors.any())
			.build()
	}
	
}