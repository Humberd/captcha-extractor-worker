package db

import env.Environment
import org.ktorm.database.Database
import org.ktorm.support.postgresql.PostgreSqlDialect

object DbConnector {
    val database: Database by lazy {
        Database.connect(
            url = "jdbc:postgresql://${Environment.POSTGRES_HOST}:${Environment.POSTGRES_PORT}/${Environment.POSTGRES_DB}",
            user = Environment.POSTGRES_USER,
            password = Environment.POSTGRES_PASSWORD,
            dialect = PostgreSqlDialect(),

        )
    }
}
