package br.ufpr.monge.service;

import br.ufpr.monge.model.*;
import br.ufpr.monge.model.enums.StatusLancamento;
import br.ufpr.monge.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class LancamentoSemanalService {

    private final LancamentoSemanalRepository lancamentoRepo;
    private final BolsistaRepository bolsistaRepo;
    private final ProjetoAcademicoRepository projetoRepo;

    public LancamentoSemanalService(LancamentoSemanalRepository lancamento, BolsistaRepository bolsista, ProjetoAcademicoRepository projeto) {
        this.lancamentoRepo = lancamento;
        this.bolsistaRepo = bolsista;
        this.projetoRepo = projeto;
    }

    public LancamentoSemanal registrar(Long bolsistaId, Long projetoId, LancamentoSemanal novo) {
        Bolsista bolsista = bolsistaRepo.findById(bolsistaId)
                .orElseThrow(() -> new EntityNotFoundException("Bolsista não encontrado"));
        ProjetoAcademico projeto = projetoRepo.findById(projetoId)
                .orElseThrow(() -> new EntityNotFoundException("Projeto não encontrado"));

        novo.setBolsista(bolsista);
        novo.setProjeto(projeto);
        novo.setStatusLancamento(StatusLancamento.PENDENTE);
        return lancamentoRepo.save(novo);
    }

    public List<LancamentoSemanal> listarPorBolsista(Long bolsistaId) {
        return lancamentoRepo.findByBolsistaId(bolsistaId);
    }
}
