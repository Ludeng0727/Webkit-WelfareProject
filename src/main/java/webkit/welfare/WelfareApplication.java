package webkit.welfare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class WelfareApplication {

    public static void main(String[] args) {
        SpringApplication.run(WelfareApplication.class, args);
    }

}
