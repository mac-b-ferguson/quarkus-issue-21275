package com.bethesomm.flywaybug.app

import io.quarkus.test.junit.QuarkusTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

@QuarkusTest
internal class MainTest {

    @Test
    fun run() {
        println("Starting test run")
    }
}