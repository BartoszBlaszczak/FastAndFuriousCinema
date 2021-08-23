package hex.ffcinema.domain.usecase

import hex.ffcinema.domain.Kotest
import hex.ffcinema.domain.MovieDetails
import hex.ffcinema.domain.NoSuchMovieException
import hex.ffcinema.domain.WrongRatingException
import hex.ffcinema.domain.omdMovieTestDetails
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe

class GetMovieDetailsUseCaseTest(
	private val sut: GetMovieDetailsUseCase,
	private val addRatingUseCase: AddRatingUseCase,
) : Kotest({
	
	test("get details") {
		// given
		val movieId = 3
		
		// when
		val details = sut.getDetails(movieId)
		
		// then
		details shouldBe MovieDetails(
			movieId = movieId,
			title = "The Fast and the Furious: Tokyo Drift",
			description = omdMovieTestDetails.plot,
			releaseDate = omdMovieTestDetails.released,
			rating = null,
			imdbRating = omdMovieTestDetails.imdbRating,
			runtime = omdMovieTestDetails.runtime,
		)
	}
	
	test("get details with rating") {
		// given
		val movieId = 4
		addRatingUseCase.add(AddRatingRequest(movieId, 1.toBigDecimal()))
		addRatingUseCase.add(AddRatingRequest(movieId, 2.toBigDecimal()))
		
		// when
		val details = sut.getDetails(movieId)
		
		// then
		details shouldBe MovieDetails(
			movieId = movieId,
			title = "Fast & Furious",
			description = omdMovieTestDetails.plot,
			releaseDate = omdMovieTestDetails.released,
			rating = "1.5".toBigDecimal(),
			imdbRating = omdMovieTestDetails.imdbRating,
			runtime = omdMovieTestDetails.runtime,
		)
	}
	
	test("not get details for nonexistent movieId") {
		// given
		val movieId = 0
		
		// when
		// then
		shouldThrow<NoSuchMovieException> { sut.getDetails(movieId) }
	}
})