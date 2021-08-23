package hex.ffcinema.domain

import hex.ffcinema.domain.usecase.Rating
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet

class TestDao(private val connection: Connection) {
	fun getAllRatings() =
		findAll(
			mutableListOf(),
			connection.prepareStatement("select * from rating;")) { resultSet ->
			Rating(
				movieId = resultSet.getInt("movie_id"),
				rating = resultSet.getBigDecimal("rating"),
			)
		}
	
	fun getAllRepertories() =
		findAll(
			mutableListOf(),
			connection.prepareStatement("select * from repertory;")) { resultSet ->
			MovieRepertory(
				id = resultSet.getInt("id"),
				movieId = resultSet.getInt("movie_id"),
				title = "",
				ticketPrice = Price(resultSet.getBigDecimal("ticket_price")),
				time = resultSet.getTimestamp("time").toLocalDateTime(),
				hall = Hall.valueOf(resultSet.getString("hall")),
			)
		}
		
	private fun <T> findAll(
		mutableList: MutableList<T>,
		query: PreparedStatement,
		mapper: (ResultSet) -> T
	): List<T> {
		query.executeQuery().use { while (it.next()) mutableList.add(mapper(it)) }
		return mutableList
	}
}