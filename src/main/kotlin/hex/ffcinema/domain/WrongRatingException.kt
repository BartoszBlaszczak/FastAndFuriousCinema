package hex.ffcinema.domain

import java.math.BigDecimal

class WrongRatingException(val wrongValue: BigDecimal) : RuntimeException()
