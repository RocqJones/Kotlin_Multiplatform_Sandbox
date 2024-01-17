package com.example.kotlinmultiplatformsandbox

import kotlin.random.Random

class Greeting {
    private val platform: Platform = getPlatform()

    fun greet(): String {
        val firstWord  = when {
            Random.nextBoolean() -> "Hi!"
            else -> "Hello!"
        }
        return "$firstWord Guess what this is! > ${platform.name}!"
    }
}