package hex.ffcinema.adapter.db

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet


abstract class DatabaseRepository(private val connection: Connection) {
	
	init {
		createTableMovie()
		createTableRating()
		createTableRepertory()
		assureMovies()
	}
	
	private fun createTableMovie() = connection.prepareStatement(
		"""create table if not exists movie
(
	id     INTEGER
		constraint post_pk primary key autoincrement,
	title  TEXT not null,
	omd_id TEXT not null
);
"""
	).execute()
	
	private fun createTableRating() = connection.prepareStatement(
		"""
create table if not exists rating
(
	id       INTEGER
		constraint post_pk primary key autoincrement,
	movie_id INTEGER not null,
	rating   INTEGER not null,
	date     DATETIME not null default current_timestamp,
	FOREIGN KEY (movie_id)
		REFERENCES movie (id)
);
"""
	).execute()
	
	private fun createTableRepertory() = connection.prepareStatement(
		"""
create table if not exists repertory
(
	id           INTEGER
		constraint post_pk primary key autoincrement,
	movie_id     INTEGER  not null,
	ticket_price NUMERIC  not null,
	time         DATETIME not null,
	hall         TEXT     not null,
	FOREIGN KEY (movie_id)
		REFERENCES movie (id)
);
"""
	).execute()
	
	private fun assureMovies() {
		val moviesCount = connection
			.prepareStatement("select count(*) from movie;").executeQuery()
			.use { resultSet -> resultSet.next(); resultSet.getInt(1) }
		
		if (moviesCount == 0) {
			connection.prepareStatement(
				"""
			insert into movie (title, omd_id) VALUES
			('The Fast and the Furious', 'tt0232500'),
			('2 Fast 2 Furious', 'tt0322259'),
			('The Fast and the Furious: Tokyo Drift', 'tt0463985'),
			('Fast & Furious', 'tt1013752'),
			('Fast Five', 'tt1596343'),
			('Fast & Furious 6', 'tt1905041'),
			('Furious 7', 'tt2820852'),
			('The Fate of the Furious', 'tt4630562');"""
			).execute()
		}
	}
	
	protected fun <T> findAll(
		mutableList: MutableList<T>,
		query: PreparedStatement,
		mapper: (ResultSet) -> T
	): List<T> {
		query.executeQuery().use { while (it.next()) mutableList.add(mapper(it)) }
		return mutableList
	}
}