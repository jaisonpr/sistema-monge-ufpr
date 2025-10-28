package br.ufpr.monge.controller;

import br.ufpr.monge.model.LancamentoSemanal;
import br.ufpr.monge.service.LancamentoSemanalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/lancamentos-semanais")
@CrossOrigin
public class LancamentoSemanalController {

    private final LancamentoSemanalService service;

    public LancamentoSemanalController(LancamentoSemanalService s) {
        this.service = s;
    }

    @PostMapping("/bolsista/{bolsistaId}/projeto/{projetoId}")
    public ResponseEntity<LancamentoSemanal> registrar(
            @PathVariable Long bolsistaId,
            @PathVariable Long projetoId,
            @RequestBody LancamentoSemanal dto) {
        return ResponseEntity.ok(service.registrar(bolsistaId, projetoId, dto));
    }

    @GetMapping("/bolsista/{bolsistaId}")
    public ResponseEntity<List<LancamentoSemanal>> listar(@PathVariable Long bolsistaId) {
        return ResponseEntity.ok(service.listarPorBolsista(bolsistaId));
    }
}
