package hex.ffcinema.domain.usecase

import hex.ffcinema.domain.Hall
import hex.ffcinema.domain.NoSuchRepertoryException
import hex.ffcinema.domain.Price
import hex.ffcinema.domain.port.RepertoriesRepository
import java.time.LocalDateTime

class AddRepertoryUseCase(
	private val repository: RepertoriesRepository
) {
	fun add(request: AddMovieRepertoryRequest) = repository.add(request)
}

data class AddMovieRepertoryRequest(
	val movieId: Int,
	val ticketPrice: Price,
	val time: LocalDateTime,
	val hall: Hall
)