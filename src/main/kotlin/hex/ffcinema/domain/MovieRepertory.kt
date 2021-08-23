package hex.ffcinema.domain

import java.time.LocalDateTime

data class MovieRepertory(
	val id: Int,
	val movieId: Int,
	val title: String,
	val ticketPrice: Price,
	val time: LocalDateTime,
	val hall: Hall
)