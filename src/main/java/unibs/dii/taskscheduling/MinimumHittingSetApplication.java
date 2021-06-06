package unibs.dii.taskscheduling;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
@Slf4j
public class MinimumHittingSetApplication implements CommandLineRunner {



    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(MinimumHittingSetApplication.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }

    public static void setLoggingLevel(ch.qos.logback.classic.Level level) {
        ch.qos.logback.classic.Logger root = (ch.qos.logback.classic.Logger) org.slf4j.LoggerFactory.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
        root.setLevel(level);
    }

    @Override
    public void run(String... args) {

    }


}
