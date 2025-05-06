package minseok.cqrschallenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.ActiveProfiles;

@TestConfiguration(proxyBeanMethods = false)
@ActiveProfiles("test")
public class CqrsChallengeApplicationTests {

    public static void main(String[] args) {
        SpringApplication.from(CqrsChallengeApplication::main)
            .with(CqrsChallengeApplicationTests.class)
            .run(args);
    }
}