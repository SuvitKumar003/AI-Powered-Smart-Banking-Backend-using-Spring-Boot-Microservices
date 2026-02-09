package com.bank.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SmartBankingApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartBankingApplication.class, args);
        System.out.println("\n" +
                "╔═══════════════════════════════════════════════════════════╗\n" +
                "║                                                           ║\n" +
                "║   🏦 Smart Banking Backend - AI Powered                   ║\n" +
                "║                                                           ║\n" +
                "║   ✅ Application started successfully!                    ║\n" +
                "║   🌐 Server running on: http://localhost:8080             ║\n" +
                "║   📚 Swagger UI: http://localhost:8080/swagger-ui.html    ║\n" +
                "║   🗄️  H2 Console: http://localhost:8080/h2-console        ║\n" +
                "║                                                           ║\n" +
                "╚═══════════════════════════════════════════════════════════╝\n");
    }
}
