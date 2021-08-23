package hex.ffcinema.domain.usecase

import hex.ffcinema.domain.Hall
import hex.ffcinema.domain.Kotest
import hex.ffcinema.domain.Price
import io.kotest.matchers.shouldBe
import java.time.LocalDateTime

class GetRepertoriesUseCaseTest(
	sut: GetRepertoriesUseCase,
	addRepertoryUseCase: AddRepertoryUseCase
) : Kotest({
	test("add repertory") {
		// given
		val past = LocalDateTime.now().minusDays(1)
		val future = LocalDateTime.now().plusDays(1)
		
		addRepertoryUseCase.add(AddMovieRepertoryRequest(2, Price(1.toBigDecimal()), past, Hall.FIRST))
		addRepertoryUseCase.add(AddMovieRepertoryRequest(2, Price(2.toBigDecimal()), future, Hall.FIRST))
		addRepertoryUseCase.add(AddMovieRepertoryRequest(2, Price(3.toBigDecimal()), future, Hall.SECOND))
		
		// when
		val repertories = sut.getRepertories()
		
		// then
		repertories.filter { it.movieId == 2 }.size shouldBe 2
	}
})
