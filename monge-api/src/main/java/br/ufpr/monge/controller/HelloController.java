package br.ufpr.monge.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class HelloController {
    
    // Endpoint com parâmetro personalizado
    @GetMapping("/hello/{nome}")
    public String helloPersonalizado(@PathVariable String nome) {
        return "Olá " + nome + "! Bem-vindo ao Sistema MonGe da UFPR! ";
    }
    
    // Endpoint de status do sistema
    @GetMapping("/status")
    public Map<String, Object> getStatus() {
        Map<String, Object> status = new HashMap<>();
        status.put("status", "OK");
        status.put("timestamp", System.currentTimeMillis());
        status.put("uptime", "Sistema operacional");
        status.put("servico", "MONGE API");
        return status;
    }
}
