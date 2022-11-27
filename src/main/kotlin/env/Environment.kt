package env

import io.github.cdimascio.dotenv.dotenv

private val env = dotenv()

object Environment {
    val CAPTCHA_REQUEST_PAYLOAD = env["CAPTCHA_REQUEST_PAYLOAD"]!!
    val CAPTCHA_REQUEST_COOKIES_HEADER = env["CAPTCHA_REQUEST_COOKIES_HEADER"]!!
    val POSTGRES_USER = env["POSTGRES_USER"]!!
    val POSTGRES_PASSWORD = env["POSTGRES_PASSWORD"]!!
}
