package hex.ffcinema.domain.usecase

import hex.ffcinema.domain.WrongRatingException
import hex.ffcinema.domain.port.RatingsRepository
import hex.ffcinema.domain.usecase.AddRatingUseCase.Companion.MAX_RATING
import hex.ffcinema.domain.usecase.AddRatingUseCase.Companion.MIN_RATING
import org.slf4j.LoggerFactory
import java.math.BigDecimal

class AddRatingUseCase(
	private val repository: RatingsRepository
) {
	companion object {
		val MIN_RATING = 1.toBigDecimal()
		val MAX_RATING = 5.toBigDecimal()
	}
	
	fun add(request: AddRatingRequest) = repository.addRating(request)
}

data class AddRatingRequest(val movieId: Int, val rating: BigDecimal) {
	init {
		if (rating < MIN_RATING || rating > MAX_RATING)
			throw WrongRatingException(rating)
	}
}