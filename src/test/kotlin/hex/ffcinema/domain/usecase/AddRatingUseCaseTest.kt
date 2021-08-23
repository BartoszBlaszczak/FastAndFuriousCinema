package hex.ffcinema.domain.usecase

import hex.ffcinema.domain.Kotest
import hex.ffcinema.domain.TestDao
import hex.ffcinema.domain.WrongRatingException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.collections.shouldContain
import java.math.BigDecimal

class AddRatingUseCaseTest(sut: AddRatingUseCase, testDao: TestDao) : Kotest({
	
	test("should not create too small rating") {
		// given
		val tooSmallRating = 0.toBigDecimal()
		
		// when try to create request
		// then should be an exception
		shouldThrow<WrongRatingException> { AddRatingRequest(1, tooSmallRating) }
	}
	
	test("should not create too big rating") {
		// given
		val tooBigRating = 6.toBigDecimal()
		
		// when try to create request
		// then should be an exception
		shouldThrow<WrongRatingException> { AddRatingRequest(1, tooBigRating) }
	}
	
	test("add rating") {
		// given
		val request = AddRatingRequest(1, 1.toBigDecimal())
		
		// when
		sut.add(request)
		
		// then
		testDao.getAllRatings() shouldContain Rating(1, 1.toBigDecimal())
	}
})

data class Rating(val movieId: Int, val rating: BigDecimal)