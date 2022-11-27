import network.models.CaptchaChallenge

suspend fun main(args: Array<String>) {
    val sessionGetChallenge = CaptchaChallenge.client.sessionGetChallenge()
    println(sessionGetChallenge)
}
