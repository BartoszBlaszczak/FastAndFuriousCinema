package hex.ffcinema.application

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("ff")
data class Properties(
	var dbUrl: String = "",
)