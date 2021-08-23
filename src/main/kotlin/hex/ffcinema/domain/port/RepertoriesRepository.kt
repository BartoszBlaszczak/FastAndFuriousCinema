package hex.ffcinema.domain.port

import hex.ffcinema.domain.MovieRepertory
import hex.ffcinema.domain.usecase.AddMovieRepertoryRequest
import hex.ffcinema.domain.usecase.UpdateMovieRepertoryRequest
import java.time.LocalDateTime

interface RepertoriesRepository {
	fun getRepertoriesAfter(time: LocalDateTime): List<MovieRepertory>
	fun update(request: UpdateMovieRepertoryRequest): Boolean
	fun add(request: AddMovieRepertoryRequest)
}