package hex.ffcinema.adapter.web

import hex.ffcinema.domain.port.OMDMovieDetails
import hex.ffcinema.domain.port.OMDRepository
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(value = "OMDClient", url = "http://www.omdbapi.com/?apikey=\${omd.apikey}", primary = false)
interface OMDClient: OMDRepository {
	
	@GetMapping
	override fun getDetails(@RequestParam(name = "i") omdId: String): OMDMovieDetails
}