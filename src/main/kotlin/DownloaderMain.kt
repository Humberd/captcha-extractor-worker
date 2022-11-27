import db.DbConnector
import db.models.CaptchaChallengeDb
import mu.KotlinLogging
import network.models.CaptchaChallenge
import org.ktorm.dsl.insert
import retrofit2.HttpException

private val logger = KotlinLogging.logger {}


suspend fun main() {
    downloadAndSaveChallengeTask()
}

suspend fun downloadAndSaveChallengeTask() {
    var counter = 0;
    while (true) {
        downloadAndSaveChallenge()
        counter++
        logger.info { "Download and save captcha challenge completed. Total added: $counter" }
    }
}

suspend fun downloadAndSaveChallenge() {
    val response = try {
        CaptchaChallenge.client.sessionGetChallenge()
    } catch (e: HttpException) {
        if (e.code() == 403) {
            logger.error(e) { "403: Invalid sessionGetChallenge payload. Update CAPTCHA_REQUEST_PAYLOAD and CAPTCHA_REQUEST_COOKIES_HEADER env variables" }
        }
        throw e
    }

    DbConnector.database.insert(CaptchaChallengeDb) {
        set(it.id, response.challengeId)
        set(it.imageId, response.imageId)
        set(it.badgeCount, response.badgeCount)
        set(it.imageBase64Src, response.imageBase64Src)
    }
}
