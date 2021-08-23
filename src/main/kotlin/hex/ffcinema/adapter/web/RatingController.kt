package hex.ffcinema.adapter.web

import hex.ffcinema.domain.WrongRatingException
import hex.ffcinema.domain.usecase.AddRatingRequest
import hex.ffcinema.domain.usecase.AddRatingUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController


@RestController
open class RatingController(
	private val addRatingUseCase: AddRatingUseCase,
) {
	
	@PostMapping("/rating")
	open fun rate(@RequestBody request: AddRatingRequest) = addRatingUseCase.add(request)
	
	@ExceptionHandler(value = [WrongRatingException::class])
	open fun exception(exception: WrongRatingException): ResponseEntity<Any> {
		return ResponseEntity(
			"Rating must be between ${AddRatingUseCase.MIN_RATING} and ${AddRatingUseCase.MAX_RATING}",
			HttpStatus.BAD_REQUEST
		)
	}
}

