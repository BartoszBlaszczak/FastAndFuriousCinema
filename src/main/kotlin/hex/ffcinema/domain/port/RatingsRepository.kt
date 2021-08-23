package hex.ffcinema.domain.port

import hex.ffcinema.domain.usecase.AddRatingRequest
import java.math.BigDecimal

interface RatingsRepository {
	fun getRating(movieId: Int): BigDecimal?
	fun addRating(request: AddRatingRequest)
}