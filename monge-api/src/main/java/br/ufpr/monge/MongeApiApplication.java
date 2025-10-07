package br.ufpr.monge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class MongeApiApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(MongeApiApplication.class, args);
    }
    
    @RestController
    public static class HelloController {
        
        @GetMapping("/")
        public String hello() {
            return "Sistema MONGE - API funcionando!";
        }
        
        @GetMapping("/status")
        public String status() {
            return "OK - MONGE API ativa";
        }
    }
}
