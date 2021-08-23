package hex.ffcinema.domain.usecase

import hex.ffcinema.domain.Hall
import hex.ffcinema.domain.Kotest
import hex.ffcinema.domain.MovieRepertory
import hex.ffcinema.domain.NoSuchRepertoryException
import hex.ffcinema.domain.Price
import hex.ffcinema.domain.TestDao
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import java.time.LocalDate.now

class UpdateRepertoryUseCaseTest(
	private val sut: UpdateRepertoryUseCase,
	private val addRepertoryUseCase: AddRepertoryUseCase,
	private val getRepertoryUseCase: GetRepertoriesUseCase,
	private val testDao: TestDao
) : Kotest({
	this as UpdateRepertoryUseCaseTest
	
	test("update repertory") {
		// given
		val repertory = addRepertory()
		val request = updateRequest(repertory)
		
		// when
		sut.update(request)
		
		// then
		val allRepertories = testDao.getAllRepertories()
		allRepertories
			.asSequence()
			.filter { it.id == request.id }
			.filter { it.movieId == request.movieId }
			.filter { it.ticketPrice == request.ticketPrice }
			.filter { it.time == request.time }
			.filter { it.hall == request.hall }
			.count() shouldBe 1
	}
	
	test("exception for updating nonexistent repertory") {
		// given
		val nonexistentRepertoryId = 0
		val request =
			UpdateMovieRepertoryRequest(
				id = nonexistentRepertoryId,
				movieId = 1,
				ticketPrice = Price(2.toBigDecimal()),
				time = now().atStartOfDay(),
				hall = Hall.SECOND
			)
		
		// when
		shouldThrow<NoSuchRepertoryException> { sut.update(request) }
		
		// then
		val allRepertories = testDao.getAllRepertories()
		allRepertories.count { it.id == nonexistentRepertoryId } shouldBe 0
	}
}) {
	fun addRepertory(): MovieRepertory {
		addRepertoryUseCase.add(
			AddMovieRepertoryRequest(
				3,
				Price(1.toBigDecimal()),
				now().plusDays(1).atStartOfDay(),
				Hall.FIRST
			)
		)
		return getRepertoryUseCase.getRepertories().first { it.movieId == 3 }
	}
	
	fun updateRequest(currentRepertory: MovieRepertory) =
		UpdateMovieRepertoryRequest(
			currentRepertory.id,
			currentRepertory.movieId,
			Price(2.toBigDecimal()),
			currentRepertory.time.plusHours(1),
			Hall.SECOND
		)
}
