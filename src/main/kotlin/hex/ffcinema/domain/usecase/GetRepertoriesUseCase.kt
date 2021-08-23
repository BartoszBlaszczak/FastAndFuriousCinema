package hex.ffcinema.domain.usecase

import hex.ffcinema.domain.port.RepertoriesRepository
import java.time.LocalDateTime

class GetRepertoriesUseCase(
	private val repository: RepertoriesRepository
) {
	fun getRepertories() = repository.getRepertoriesAfter(LocalDateTime.now())
}