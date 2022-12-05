package top.shellwe.logreadermod.controller;

import org.junit.jupiter.api.Test;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static org.junit.jupiter.api.Assertions.*;

class LinuxControllerTest {
    private static final Logger logger = LogManager.getLogger("HelloWorld");
    @Test
    void getConnect() {
        logger.info("Hello, shellwe is trying coding logging！");
    }
}
