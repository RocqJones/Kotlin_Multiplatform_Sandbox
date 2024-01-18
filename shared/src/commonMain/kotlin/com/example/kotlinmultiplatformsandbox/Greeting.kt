package com.example.kotlinmultiplatformsandbox

import daysPhrase
import kotlin.random.Random

class Greeting {
    private val platform: Platform = getPlatform()

    fun greet(): List<String> = buildList {
        add(when { Random.nextBoolean() -> "Hi!" else -> "Hello!" })
        add("Guess what this is! > ${platform.name}!")
        add(daysPhrase())
    }
}