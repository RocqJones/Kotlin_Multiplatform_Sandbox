package api_client

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.json.Json
import model.RocketLaunch

/**
 * The JSON serializer here is configured in a way that it prints JSON in a more readable manner
 * with the prettyPrint property.
 * It is more flexible when reading malformed JSON with isLenient, and it ignores keys that haven't
 * been declared in the rocket launch model with ignoreUnknownKeys.
 */
class RocketComponent {
    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(
                Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                }
            )
        }
    }

    /**
     * httpClient.get() function to retrieve information about rocket launches.
     * httpClient.get()" is also a suspending function because it needs to retrieve data over the
     * network asynchronously without blocking threads.
     *
     *
     * Convert the launch date from UTC to your local date and format the output: "MMMM DD, YYYY" format
     */
    private suspend fun getDateOfLastSuccessfulLaunch(): String {
        val rockets: List<RocketLaunch> =
            httpClient.get("https://api.spacexdata.com/v4/launches").body()
        val lastSuccessLaunch = rockets.last { it.launchSuccess == true }
        val date = Instant.parse(lastSuccessLaunch.launchDateUTC).toLocalDateTime(
            TimeZone.currentSystemDefault()
        )

        return "${date.month} ${date.dayOfMonth}, ${date.year}"
    }

    /**
     * This will create a message using the getDateOfLastSuccessfulLaunch() function
     */
    suspend fun launchPhrase(): String = try {
        "The last successful launch was on ${getDateOfLastSuccessfulLaunch()} 🚀"
    } catch (e: Exception) {
        println("Exception during getting the date of the last successful launch $e")
        "Error occurred"
    }
}