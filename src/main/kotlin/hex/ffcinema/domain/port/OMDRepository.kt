package hex.ffcinema.domain.port

import java.math.BigDecimal

interface OMDRepository {
	fun getDetails(omdId: String): OMDMovieDetails
}

data class OMDMovieDetails(
	val released: String,
	val runtime: String,
	val plot: String,
	val imdbRating: BigDecimal,
)
