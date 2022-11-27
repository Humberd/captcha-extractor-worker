package network.models

import com.fasterxml.jackson.annotation.JsonProperty
import env.Environment
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import retrofit2.http.*


object CaptchaChallenge {
    private const val BASE_URL = "https://www.erepublik.com"
    val client: Api

    init {
        val interceptor = HttpLoggingInterceptor({ println(it) })
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val httpClient: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        client = Retrofit.Builder()
            .client(httpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(JacksonConverterFactory.create())
            .build().create(Api::class.java)
    }

    interface Api {
        @Headers(
            "User-Agent:Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:107.0) Gecko/20100101 Firefox/107.0",
            "Content-Type:application/x-www-form-urlencoded; charset=UTF-8"
        )
        @POST("/en/main/sessionGetChallenge")
        suspend fun sessionGetChallenge(
            @Body body: RequestBody = Environment.CAPTCHA_REQUEST_PAYLOAD.toRequestBody("text/plain".toMediaTypeOrNull()),
            @Header("Cookie") cookie: String = Environment.CAPTCHA_REQUEST_COOKIES_HEADER
        ): ResponseModel
    }

    data class ResponseModel(
        @JsonProperty("src") val imageBase64Src: String,
        @JsonProperty("imageId") val imageId: String,
        @JsonProperty("challengeId") val challengeId: String,
        @JsonProperty("minCnt") val badgeCount: Int,
        @JsonProperty("onLoad") val onLoad: String
    )
}
