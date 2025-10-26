package br.ufpr.monge.controller;

import br.ufpr.monge.model.*;
import br.ufpr.monge.model.enums.*;
import br.ufpr.monge.repository.*;
import br.ufpr.monge.service.*;
import br.ufpr.monge.dto.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/orientador/lancamentos")
public class OrientadorLancamentoController {

    private final LancamentoAprovacaoService lancamentoAprovacaoService;

    public OrientadorLancamentoController(LancamentoAprovacaoService lancamentoAprovacaoService) {
        this.lancamentoAprovacaoService = lancamentoAprovacaoService;
    }

    // 1. Listar TODOS os lançamentos pendentes - SEM filtro por orientador
    @GetMapping("/pendentes")
    public List<LancamentoSemanal> listarTodosPendentes() {
        return lancamentoAprovacaoService.listarTodosPendentes();
    }

    // 2. Aprovar lançamento com feedback
    @PostMapping("/{lancamentoId}/aprovar")
    public LancamentoSemanal aprovarLancamento(
            @PathVariable Long lancamentoId,
            @RequestParam Long orientadorId,
            @RequestBody FeedbackRequest feedbackRequest) {
        
        return lancamentoAprovacaoService.aprovarLancamento(
            lancamentoId, orientadorId, feedbackRequest.getFeedback());
    }

    // 3. Rejeitar lançamento com justificativa
    @PostMapping("/{lancamentoId}/rejeitar")
    public LancamentoSemanal rejeitarLancamento(
            @PathVariable Long lancamentoId,
            @RequestParam Long orientadorId,
            @RequestBody FeedbackRequest feedbackRequest) {
        
        return lancamentoAprovacaoService.rejeitarLancamento(
            lancamentoId, orientadorId, feedbackRequest.getFeedback());
    }

    // 4. Editar lançamento (correções pelo orientador)
    @PutMapping("/{lancamentoId}")
    public LancamentoSemanal editarLancamento(
            @PathVariable Long lancamentoId,
            @RequestParam Long orientadorId,
            @RequestBody LancamentoSemanal dadosAtualizados) {
        
        return lancamentoAprovacaoService.editarLancamento(
            lancamentoId, orientadorId, dadosAtualizados);
    }
}