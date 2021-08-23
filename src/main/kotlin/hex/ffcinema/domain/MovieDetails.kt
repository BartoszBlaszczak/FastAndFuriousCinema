package hex.ffcinema.domain

import java.math.BigDecimal

data class MovieDetails(
	val movieId: Int,
	val title: String,
	val description: String,
	val releaseDate: String,
	val rating: BigDecimal?,
	val imdbRating: BigDecimal,
	val runtime: String
)