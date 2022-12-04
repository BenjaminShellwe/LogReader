package top.shellwe.logreadermod;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LogReaderModApplication {

    public static void main(String[] args) {
        SpringApplication.run(LogReaderModApplication.class, args);
    }

}
