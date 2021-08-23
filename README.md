# Fast & Furious Cinema
This is demo application for small cinema backend.
It is written in kotlin 1.5 and uses gradle 7+ to build and JVM 8+ to run.

Requirements may be found [here](https://gist.github.com/wbaumann/aaa5ef095e213ffbea35b7ca3cc251a7).

This application uses OpenMovieDatabase as a movies' description deliver so
before run it remember to set property `omd.apikey` to your OpenMovieDatabase api key.

To build this application & generate executable jar run `./gradlew build`.

Build reports can be found in `build/reports/`.

Artifact should be located in `build/libs/FastAndFuriousCinema-[version].jar.`

After run, you may find API documentation at [swagger](http://localhost:8080/swagger-ui.html).

Example requests may be found in [exampleRequests](./exampleRequests) catalog.

Notes:

* For ease of use this app uses SQLite as a persistence layer so there is no need to set up database. DB file is named database.sqlite and is created by app. For "real" production it should be replaced by some professional db e.g. PostgreSQL.
* For ease of use this app uses no security layer. For "real" production it should be implemented.
* This application uses plain JDBC as a database connector. After years of using JPA & JooQ solutions I think that good old JDBC is a better way to work with DB.
