package com.company.app24thjuly;

import com.company.app24thjuly.bot.config.BotConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties({BotConfig.class})
@SpringBootApplication
public class App24thJulyApplication {

    public static void main(String[] args) {
        SpringApplication.run(App24thJulyApplication.class, args);
    }

}
