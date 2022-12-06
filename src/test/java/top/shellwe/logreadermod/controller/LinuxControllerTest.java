package top.shellwe.logreadermod.controller;

import org.junit.jupiter.api.Test;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static org.junit.jupiter.api.Assertions.*;

class LinuxControllerTest {
    private static final Logger logger = LogManager.getLogger("logger test");
    @Test
    void getConnect() {
        logger.info("Hello, shellwe is trying coding logging");

        if (logger.isDebugEnabled()) {
            logger.debug("Logging debug");
        }

        logger.debug("Integer.MAX_VALUE = %,d", Integer.MAX_VALUE);
        logger.debug("Long.MAX_VALUE = %,d", Long.MAX_VALUE);
        logger.error("Unable to process request due to {}");
    }
}
