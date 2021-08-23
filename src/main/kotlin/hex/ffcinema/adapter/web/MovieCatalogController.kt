package hex.ffcinema.adapter.web

import hex.ffcinema.domain.MovieDetails
import hex.ffcinema.domain.NoSuchMovieException
import hex.ffcinema.domain.usecase.GetMovieDetailsUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController


@RestController
open class MovieCatalogController(
	private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
) {
	@GetMapping("movie/{id}")
	open fun getMovieDetails(@PathVariable id: Int): MovieDetails = getMovieDetailsUseCase.getDetails(id)
	
	@ExceptionHandler(value = [NoSuchMovieException::class])
	open fun exception(exception: NoSuchMovieException): ResponseEntity<Any> {
		return ResponseEntity(
			"There is no such movie: ${exception.wrongMovieId}",
			HttpStatus.BAD_REQUEST
		)
	}
}

