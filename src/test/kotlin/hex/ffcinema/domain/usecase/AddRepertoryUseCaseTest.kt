package hex.ffcinema.domain.usecase

import hex.ffcinema.domain.Hall
import hex.ffcinema.domain.Kotest
import hex.ffcinema.domain.Price
import hex.ffcinema.domain.TestDao
import io.kotest.matchers.shouldBe
import java.time.LocalDate.now

class AddRepertoryUseCaseTest(sut: AddRepertoryUseCase, testDao: TestDao) : Kotest({
	test("add repertory") {
		// given
		val request = AddMovieRepertoryRequest(1, Price("9.99".toBigDecimal()), now().atTime(12, 0), Hall.FIRST)
		
		// when
		sut.add(request)
		
		// then
		val allRepertories = testDao.getAllRepertories()
		allRepertories
			.asSequence()
			.filter { it.movieId == request.movieId}
			.filter { it.ticketPrice == request.ticketPrice }
			.filter { it.time == request.time }
			.filter { it.hall == request.hall }
			.count() shouldBe 1
	}
})
