package hex.ffcinema.adapter.web

import hex.ffcinema.domain.MovieRepertory
import hex.ffcinema.domain.NoSuchRepertoryException
import hex.ffcinema.domain.usecase.AddMovieRepertoryRequest
import hex.ffcinema.domain.usecase.AddRepertoryUseCase
import hex.ffcinema.domain.usecase.GetRepertoriesUseCase
import hex.ffcinema.domain.usecase.UpdateMovieRepertoryRequest
import hex.ffcinema.domain.usecase.UpdateRepertoryUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController


@RestController
open class RepertoryController(
	private val getRepertoriesUseCase: GetRepertoriesUseCase,
	private val addRepertoryUseCase: AddRepertoryUseCase,
	private val updateRepertoryUseCase: UpdateRepertoryUseCase,
) {
	
	@GetMapping("repertory")
	open fun movieCatalog(): List<MovieRepertory> = getRepertoriesUseCase.getRepertories()
	
	@PostMapping("/repertory")
	open fun addRepertory(@RequestBody request: AddMovieRepertoryRequest) = addRepertoryUseCase.add(request)
	
	@PutMapping("/repertory")
	open fun updateRepertory(@RequestBody request: UpdateMovieRepertoryRequest) = updateRepertoryUseCase.update(request)
	
	@ExceptionHandler(value = [NoSuchRepertoryException::class])
	open fun exception(exception: NoSuchRepertoryException): ResponseEntity<Any> {
		return ResponseEntity(
			"There is no such repertory: ${exception.wrongId}",
			HttpStatus.BAD_REQUEST
		)
	}
}
