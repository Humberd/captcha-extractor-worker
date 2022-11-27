import db.DbConnector
import db.models.CaptchaChallenges
import org.ktorm.dsl.from
import org.ktorm.dsl.select

suspend fun main(args: Array<String>) {
//    val sessionGetChallenge = CaptchaChallenge.client.sessionGetChallenge()
//    println(sessionGetChallenge)

    for (row in DbConnector.database.from(CaptchaChallenges).select()) {
        println(row[CaptchaChallenges.id])
    }
}

