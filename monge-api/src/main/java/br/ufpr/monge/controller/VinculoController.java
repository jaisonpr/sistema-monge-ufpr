package br.ufpr.monge.controller;

import br.ufpr.monge.model.Bolsista;
import br.ufpr.monge.model.BolsistaProjeto;
import br.ufpr.monge.model.Orientador;
import br.ufpr.monge.model.ProjetoAcademico;
import br.ufpr.monge.model.enums.StatusVinculo;
import br.ufpr.monge.repository.BolsistaProjetoRepository;
import br.ufpr.monge.repository.BolsistaRepository;
import br.ufpr.monge.repository.OrientadorRepository;
import br.ufpr.monge.repository.ProjetoAcademicoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException; // Importado para lidar com a data
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1") // Mapeado para a mesma raiz da API
@CrossOrigin(origins = "*")
public class VinculoController {

    @Autowired
    private BolsistaRepository bolsistaRepository;

    @Autowired
    private OrientadorRepository orientadorRepository;

    @Autowired
    private ProjetoAcademicoRepository projetoAcademicoRepository;

    @Autowired
    private BolsistaProjetoRepository bolsistaProjetoRepository;

    /**
     * Endpoint para listar Bolsistas no dropdown.
     */
    @GetMapping("/bolsistas")
    public List<Bolsista> getBolsistas() {
        // findAll() é um método padrão do JpaRepository
        return bolsistaRepository.findAll();
    }

    /**
     * Endpoint para listar Orientadores no dropdown.
     */
    @GetMapping("/orientadores")
    public List<Orientador> getOrientadores() {
        // findAll() é um método padrão do JpaRepository
        return orientadorRepository.findAll();
    }

    /**
     * Endpoint para listar Projetos no dropdown.
     */
    @GetMapping("/projetos")
    public List<ProjetoAcademico> getProjetos() {
        // findAll() é um método padrão do JpaRepository
        return projetoAcademicoRepository.findAll();
    }

    /**
     * Endpoint para vincular um Bolsista a um Projeto.
     * Recebe um JSON com os IDs.
     * TIPO DO PAYLOAD CORRIGIDO para Map<String, Object>
     */
    @PostMapping("/bolsista-projeto")
    public ResponseEntity<BolsistaProjeto> vincularBolsistaProjeto(@RequestBody Map<String, Object> payload) {
        try {
            // --- 1. Extrair e Converter os Dados do Payload ---

            // IDs numéricos (Jackson converte para Integer ou Long, usamos Number para garantir)
            Long bolsistaId = ((Number) payload.get("bolsistaId")).longValue();
            Long projetoId = ((Number) payload.get("projetoId")).longValue();
            Long orientadorId = ((Number) payload.get("orientadorId")).longValue();

            // Data (vem como String do frontend)
            String dataVinculacaoStr = (String) payload.get("dataVinculacao");
            LocalDate dataVinculacao;

            // Validar e converter a string da data
            if (dataVinculacaoStr == null || dataVinculacaoStr.isEmpty()) {
                // Se a data do frontend estiver vazia, usa a data atual
                dataVinculacao = LocalDate.now();
            } else {
                try {
                    // Tenta converter a string (ex: "2025-10-24") para LocalDate
                    dataVinculacao = LocalDate.parse(dataVinculacaoStr);
                } catch (DateTimeParseException e) {
                    throw new RuntimeException("Formato de data inválido. Use AAAA-MM-DD.");
                }
            }

            // --- 2. Buscar Entidades ---
            Bolsista bolsista = bolsistaRepository.findById(bolsistaId)
                    .orElseThrow(() -> new RuntimeException("Bolsista com id " + bolsistaId + " não encontrado."));
            
            ProjetoAcademico projeto = projetoAcademicoRepository.findById(projetoId)
                    .orElseThrow(() -> new RuntimeException("Projeto com id " + projetoId + " não encontrado."));
            
            Orientador orientador = orientadorRepository.findById(orientadorId)
                    .orElseThrow(() -> new RuntimeException("Orientador com id " + orientadorId + " não encontrado."));

            bolsista.setDataInicioVinculo(dataVinculacao);
            bolsistaRepository.save(bolsista); // Salva a alteração no bolsista

            // Cria a entidade de vínculo (BolsistaProjeto)
            BolsistaProjeto novoVinculo = new BolsistaProjeto();
            novoVinculo.setBolsista(bolsista);
            novoVinculo.setProjeto(projeto);
            novoVinculo.setOrientador(orientador);
            novoVinculo.setDataVinculacao(dataVinculacao); // Usa a data (do payload ou de hoje)
            novoVinculo.setStatusVinculo(StatusVinculo.ATIVO); // Define o status padrão

            BolsistaProjeto vinculoSalvo = bolsistaProjetoRepository.save(novoVinculo);
            return new ResponseEntity<>(vinculoSalvo, HttpStatus.CREATED);

        } catch (Exception e) {
            System.err.println("Erro ao criar vínculo: " + e.getMessage());
            // Retorna 400 Bad Request se algum ID estiver errado ou houver outra falha
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}

