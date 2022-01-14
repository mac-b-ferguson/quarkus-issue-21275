package com.bethesomm.flywaybug.app

import io.quarkus.runtime.Quarkus
import io.quarkus.runtime.QuarkusApplication
import io.quarkus.runtime.annotations.QuarkusMain
import org.slf4j.LoggerFactory

@QuarkusMain
class Main : QuarkusApplication {
    private val logger = LoggerFactory.getLogger(Main::class.java)

    override fun run(vararg args: String): Int {
        logger.info("Start application")
        Quarkus.waitForExit()
        logger.info("Finish application")
        return 0
    }
}