package com.example.kotlinmultiplatformsandbox

import api_client.RocketComponent
import daysPhrase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.random.Random
import kotlin.time.Duration.Companion.seconds

class Greeting {
    private val platform: Platform = getPlatform()
    private val rocketComponent = RocketComponent()

    /**
     * Using flows instead of suspending functions. They emit a sequence of values instead of a
     * single value that suspending functions return.
     * The Flow is created here with the flow() builder function, which wraps all the statements.
     * The Flow emits strings with a delay of one second between each emission.
     */
    fun greet(): Flow<String> = flow {
        emit(when { Random.nextBoolean() -> "Hi!" else -> "Hello!" })
        delay(1.seconds)
        emit("Guess what this is! > ${platform.name}!")
        delay(1.seconds)
        emit(daysPhrase())
        emit(rocketComponent.launchPhrase())
    }
}