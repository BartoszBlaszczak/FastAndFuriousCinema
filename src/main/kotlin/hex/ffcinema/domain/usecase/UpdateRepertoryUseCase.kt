package hex.ffcinema.domain.usecase

import hex.ffcinema.domain.Hall
import hex.ffcinema.domain.NoSuchRepertoryException
import hex.ffcinema.domain.Price
import hex.ffcinema.domain.port.RepertoriesRepository
import java.time.LocalDateTime

class UpdateRepertoryUseCase(
	private val repository: RepertoriesRepository
) {
	fun update(request: UpdateMovieRepertoryRequest) {
		val success = repository.update(request)
		if(!success) throw NoSuchRepertoryException(request.id)
	}
}

data class UpdateMovieRepertoryRequest(
	val id: Int,
	val movieId: Int,
	val ticketPrice: Price,
	val time: LocalDateTime,
	val hall: Hall
)