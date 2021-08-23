package hex.ffcinema.domain.usecase

import hex.ffcinema.domain.MovieDetails
import hex.ffcinema.domain.NoSuchMovieException
import hex.ffcinema.domain.port.MovieRepository
import hex.ffcinema.domain.port.OMDRepository
import hex.ffcinema.domain.port.RatingsRepository

class GetMovieDetailsUseCase(
	private val omdRepository: OMDRepository,
	private val movieRepository: MovieRepository,
	private val ratingsRepository: RatingsRepository,
) {
	fun getDetails(movieId: Int): MovieDetails {
		val movie = movieRepository.getMovie(movieId) ?: throw NoSuchMovieException(movieId)
		val rating = ratingsRepository.getRating(movieId)
		val omdMovieDetails = omdRepository.getDetails(movie.omdId)
		
		return MovieDetails(
			movieId = movieId,
			title = movie.title,
			description = omdMovieDetails.plot,
			releaseDate = omdMovieDetails.released,
			rating = rating,
			imdbRating = omdMovieDetails.imdbRating,
			runtime = omdMovieDetails.runtime,
		)
	}
}