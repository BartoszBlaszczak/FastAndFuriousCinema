package hex.ffcinema.adapter.db

import hex.ffcinema.domain.port.RatingsRepository
import hex.ffcinema.domain.usecase.AddRatingRequest
import java.math.BigDecimal
import java.sql.Connection

class DatabaseRatingsRepository(connection: Connection) : DatabaseRepository(connection), RatingsRepository {
	
	private val getRatingStatement = connection.prepareStatement("SELECT avg(rating) from rating where movie_id = ?;")
	
	override fun getRating(movieId: Int): BigDecimal? =
		getRatingStatement.apply { setInt(1, movieId) }
			.executeQuery()
			.use { it.takeIf { it.next() }?.getBigDecimal(1) }
	
	private val addRatingStatement = connection.prepareStatement("INSERT INTO rating(movie_id, rating) VALUES (?, ?);")
	
	override fun addRating(request: AddRatingRequest) {
		addRatingStatement.apply {
			setInt(1, request.movieId)
			setBigDecimal(2, request.rating)
		}.execute()
	}
}