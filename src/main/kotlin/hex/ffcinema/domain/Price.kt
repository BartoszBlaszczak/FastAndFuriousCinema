package hex.ffcinema.domain

import hex.ffcinema.domain.Currency.USD
import java.math.BigDecimal

data class Price(val amount: BigDecimal, val currency: Currency = USD)

enum class Currency{
	USD
}
