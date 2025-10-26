package br.ufpr.monge.service;

import br.ufpr.monge.model.*;
import br.ufpr.monge.model.enums.*;
import br.ufpr.monge.repository.*;
import java.util.List;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;

@Service
public class LancamentoAprovacaoService {

    private final LancamentoSemanalRepository lancamentoRepo;
    private final OrientadorRepository orientadorRepo;

    public LancamentoAprovacaoService(LancamentoSemanalRepository lancamentoRepo, 
                                    OrientadorRepository orientadorRepo) {
        this.lancamentoRepo = lancamentoRepo;
        this.orientadorRepo = orientadorRepo;
    }

    // Listar TODOS os lançamentos pendentes
    public List<LancamentoSemanal> listarTodosPendentes() {
        return lancamentoRepo.findByStatusLancamento(StatusLancamento.PENDENTE);
    }

    public LancamentoSemanal aprovarLancamento(Long lancamentoId, Long orientadorId, String feedback) {
        LancamentoSemanal lancamento = lancamentoRepo.findById(lancamentoId)
                .orElseThrow(() -> new EntityNotFoundException("Lançamento não encontrado"));
        
        Orientador orientador = orientadorRepo.findById(orientadorId)
                .orElseThrow(() -> new EntityNotFoundException("Orientador não encontrado"));

        // Verificar se o orientador tem permissão para aprovar este lançamento
        if (!lancamentoRepo.orientadorTemPermissaoParaProjeto(orientadorId, lancamento.getProjeto().getId())) {
            throw new SecurityException("Orientador não tem permissão para aprovar este lançamento");
        }

        lancamento.setStatusLancamento(StatusLancamento.APROVADO);
        lancamento.setFeedbackOrientador(feedback);
        lancamento.setDataAprovacao(LocalDateTime.now());
        lancamento.setOrientadorAvaliador(orientador);

        return lancamentoRepo.save(lancamento);
    }

    public LancamentoSemanal rejeitarLancamento(Long lancamentoId, Long orientadorId, String feedback) {
        LancamentoSemanal lancamento = lancamentoRepo.findById(lancamentoId)
                .orElseThrow(() -> new EntityNotFoundException("Lançamento não encontrado"));
        
        Orientador orientador = orientadorRepo.findById(orientadorId)
                .orElseThrow(() -> new EntityNotFoundException("Orientador não encontrado"));

        // Verificar se o orientador tem permissão para rejeitar este lançamento
        if (!lancamentoRepo.orientadorTemPermissaoParaProjeto(orientadorId, lancamento.getProjeto().getId())) {
            throw new SecurityException("Orientador não tem permissão para rejeitar este lançamento");
        }

        lancamento.setStatusLancamento(StatusLancamento.REPROVADO);
        lancamento.setFeedbackOrientador(feedback);
        lancamento.setDataAprovacao(LocalDateTime.now());
        lancamento.setOrientadorAvaliador(orientador);

        return lancamentoRepo.save(lancamento);
    }

    public LancamentoSemanal editarLancamento(Long lancamentoId, Long orientadorId, LancamentoSemanal dadosAtualizados) {
        LancamentoSemanal lancamento = lancamentoRepo.findById(lancamentoId)
                .orElseThrow(() -> new EntityNotFoundException("Lançamento não encontrado"));

        // Verificar permissão
        if (!lancamentoRepo.orientadorTemPermissaoParaProjeto(orientadorId, lancamento.getProjeto().getId())) {
            throw new SecurityException("Orientador não tem permissão para editar este lançamento");
        }

        // Atualizar campos editáveis
        if (dadosAtualizados.getAtividadesRealizadas() != null) {
            lancamento.setAtividadesRealizadas(dadosAtualizados.getAtividadesRealizadas());
        }
        if (dadosAtualizados.getHorasRealizadas() != null) {
            lancamento.setHorasRealizadas(dadosAtualizados.getHorasRealizadas());
        }
        if (dadosAtualizados.getJustificativaFalta() != null) {
            lancamento.setJustificativaFalta(dadosAtualizados.getJustificativaFalta());
        }
        if (dadosAtualizados.getObservacoes() != null) {
            lancamento.setObservacoes(dadosAtualizados.getObservacoes());
        }

        lancamento.setStatusLancamento(StatusLancamento.APROVADO);
        lancamento.setOrientadorAvaliador(orientadorRepo.findById(orientadorId).orElse(null));

        return lancamentoRepo.save(lancamento);
    }
}