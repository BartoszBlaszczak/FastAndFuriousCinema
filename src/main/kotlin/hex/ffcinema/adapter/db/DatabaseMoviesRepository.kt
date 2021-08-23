package hex.ffcinema.adapter.db

import hex.ffcinema.domain.Movie
import hex.ffcinema.domain.port.MovieRepository
import java.sql.Connection


class DatabaseMoviesRepository(connection: Connection) : DatabaseRepository(connection), MovieRepository {
	
	private val getMovieStatement = connection.prepareStatement("select * from movie where id = ?")
	
	override fun getMovie(movieId: Int) =
		getMovieStatement.apply { setInt(1, movieId) }
			.executeQuery()
			.use { resultSet ->
				resultSet.takeIf { it.next() }?.let { results ->
					Movie(
						results.getInt("id"),
						results.getString("title"),
						results.getString("omd_id"),
					)
				}
			}
}