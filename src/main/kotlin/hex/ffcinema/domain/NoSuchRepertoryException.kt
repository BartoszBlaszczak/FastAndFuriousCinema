package hex.ffcinema.domain

class NoSuchRepertoryException(val wrongId: Int) : RuntimeException()
