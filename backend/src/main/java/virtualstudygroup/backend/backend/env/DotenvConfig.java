package virtualstudygroup.backend.backend.env;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;



@Configuration
public class DotenvConfig {

    @PostConstruct
    public void loadEnv() {
        Dotenv dotenv = Dotenv.load();
        if (dotenv.entries().isEmpty()) {
            System.out.println("Failed to load .env file! Check the file path and file name.");
        } else {
            dotenv.entries().forEach(entry -> {
                System.out.println("Loaded: " + entry.getKey() + " = " + entry.getValue());
            });
        }
    }
}
