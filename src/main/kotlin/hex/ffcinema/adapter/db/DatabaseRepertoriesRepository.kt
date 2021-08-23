package hex.ffcinema.adapter.db

import hex.ffcinema.domain.Hall
import hex.ffcinema.domain.MovieRepertory
import hex.ffcinema.domain.Price
import hex.ffcinema.domain.port.RepertoriesRepository
import hex.ffcinema.domain.usecase.AddMovieRepertoryRequest
import hex.ffcinema.domain.usecase.UpdateMovieRepertoryRequest
import java.sql.Connection
import java.sql.Timestamp
import java.time.LocalDateTime

class DatabaseRepertoriesRepository(connection: Connection) : DatabaseRepository(connection), RepertoriesRepository {
	
	private val findRepertories = connection.prepareStatement(
		"select * from repertory join movie on movie.id = repertory.movie_id where repertory.time >= ? order by repertory.time desc;"
	)
	
	override fun getRepertoriesAfter(time: LocalDateTime): List<MovieRepertory> =
		findAll(
			mutableListOf(),
			findRepertories.apply { setTimestamp(1, Timestamp.valueOf(time)) }) { resultSet ->
			MovieRepertory(
				id = resultSet.getInt("id"),
				movieId = resultSet.getInt("movie_id"),
				title = resultSet.getString("title"),
				ticketPrice = Price(resultSet.getBigDecimal("ticket_price")),
				time = resultSet.getTimestamp("time").toLocalDateTime(),
				hall = Hall.valueOf(resultSet.getString("hall")),
			)
		}
	
	private val updateRepertoryStatement = connection.prepareStatement(
		"UPDATE repertory SET movie_id = ?, ticket_price = ?, time = ?, hall = ? WHERE id = ?;"
	)
	
	override fun update(request: UpdateMovieRepertoryRequest): Boolean {
		updateRepertoryStatement.setInt(1, request.movieId)
		updateRepertoryStatement.setBigDecimal(2, request.ticketPrice.amount)
		updateRepertoryStatement.setTimestamp(3, Timestamp.valueOf(request.time))
		updateRepertoryStatement.setString(4, request.hall.name)
		updateRepertoryStatement.setInt(5, request.id)
		
		val result = updateRepertoryStatement.executeUpdate()
		return result == 1
	}
	
	private val addRepertoryStatement = connection.prepareStatement(
		"INSERT INTO repertory (movie_id, ticket_price, time, hall) VALUES (?, ?, ?, ?);"
	)
	
	override fun add(request: AddMovieRepertoryRequest) {
		addRepertoryStatement.setInt(1, request.movieId)
		addRepertoryStatement.setBigDecimal(2, request.ticketPrice.amount)
		addRepertoryStatement.setTimestamp(3, Timestamp.valueOf(request.time))
		addRepertoryStatement.setString(4, request.hall.name)
		
		addRepertoryStatement.execute()
	}
	
}