package hex.ffcinema.domain.port

import hex.ffcinema.domain.Movie

interface MovieRepository {
	fun getMovie(movieId: Int): Movie?
}