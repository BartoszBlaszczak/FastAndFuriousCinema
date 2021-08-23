package hex.ffcinema.domain

class NoSuchMovieException(val wrongMovieId: Int) : RuntimeException()
