// Caminho: monge-api/src/main/java/br/ufpr/monge/controller/CadastroController.java
package br.ufpr.monge.controller;

// Importações necessárias
import br.ufpr.monge.model.Bolsista;
import br.ufpr.monge.model.Orientador;
import br.ufpr.monge.model.ProjetoAcademico;
import br.ufpr.monge.model.enums.StatusBolsa;
import br.ufpr.monge.model.enums.StatusProjeto;
import br.ufpr.monge.model.enums.TipoPerfil;
import br.ufpr.monge.repository.BolsistaRepository;
import br.ufpr.monge.repository.OrientadorRepository;
import br.ufpr.monge.repository.ProjetoAcademicoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1") // Define o prefixo /api/v1
@CrossOrigin(origins = "*") // Permite que o frontend acesse a API [cite: 602]
public class CadastroController {

    @Autowired
    private BolsistaRepository bolsistaRepository; // Repositório para salvar Bolsista [cite: 662]

    @Autowired
    private OrientadorRepository orientadorRepository; // Repositório para salvar Orientador [cite: 647]

    @Autowired
    private ProjetoAcademicoRepository projetoAcademicoRepository; // Repositório para salvar Projeto [cite: 452]

    /**
     * Endpoint para receber o cadastro de Bolsista do frontend[cite: 569].
     * O @RequestBody converte o JSON recebido em um objeto Bolsista.
     */
    @PostMapping("/cadastro/bolsista")
    public ResponseEntity<Bolsista> cadastrarBolsista(@RequestBody Bolsista bolsista) {
        try {
            // O formulário do app.js [cite: 568] não envia todos os campos obrigatórios.
            // Definimos valores padrão aqui antes de salvar.
            bolsista.setPerfil(TipoPerfil.BOLSISTA); // [cite: 10]
            bolsista.setAtivo(true); // [cite: 11]

            // Campos obrigatórios da entidade Bolsista [cite: 236, 238, 239, 240]
            if (bolsista.getCpf() == null) {
                // Gerando um CPF de exemplo único para evitar falha de 'unique constraint'
                bolsista.setCpf("000.000." + (System.currentTimeMillis() % 100000));
            }
            if (bolsista.getDataInicioVinculo() == null) {
                bolsista.setDataInicioVinculo(LocalDate.now()); // [cite: 239]
            }
            if (bolsista.getStatusBolsa() == null) {
                bolsista.setStatusBolsa(StatusBolsa.ATIVA); // [cite: 240, 781]
            }
            if (bolsista.getEmail() == null || bolsista.getEmail().isEmpty()) {
                throw new IllegalArgumentException("O campo 'email' é obrigatório.");
            }
            String email = bolsista.getEmail();
            int atIndex = email.indexOf('@'); // Encontra a posição do '@'

            if (atIndex != -1) {
                // Se encontrou o '@', pega o texto do início (índice 0) até a posição do '@'
                bolsista.setLogin(email.substring(0, atIndex));
            } else {
                // Se não houver '@', usa o email inteiro como login (para evitar erros)
                bolsista.setLogin(email);
            }
            // Define os valores padrão de Bolsista
            bolsista.setPerfil(TipoPerfil.BOLSISTA);
            bolsista.setAtivo(true);

            Bolsista novoBolsista = bolsistaRepository.save(bolsista);
            // Retorna 201 Created e o objeto salvo
            return new ResponseEntity<>(novoBolsista, HttpStatus.CREATED);

        } catch (Exception e) {
            // Retorna um erro caso o email/login/cpf/matricula já exista
            System.err.println("Erro ao salvar bolsista: " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Endpoint para receber o cadastro de Orientador do frontend[cite: 571, 574].
     */
    @PostMapping("/cadastro/orientador")
    public ResponseEntity<Orientador> cadastrarOrientador(@RequestBody Orientador orientador) {
        try {
            // Define valores padrão antes de salvar
            orientador.setPerfil(TipoPerfil.ORIENTADOR); // [cite: 10, 527]
            orientador.setAtivo(true); // [cite: 11]

            if (orientador.getSiape() == null) {
                // Gerando um Siape de exemplo único
                orientador.setSiape("SIAPE-" + (System.currentTimeMillis() % 100000)); // [cite: 154]
            } if (orientador.getEmail() == null || orientador.getEmail().isEmpty()) {
                throw new IllegalArgumentException("O campo 'email' é obrigatório.");
            }
            String email = orientador.getEmail();
            int atIndex = email.indexOf('@'); // Encontra a posição do '@'

            if (atIndex != -1) {
                // Se encontrou o '@', pega o texto do início (índice 0) até a posição do '@'
                orientador.setLogin(email.substring(0, atIndex));
            } else {
                // Se não houver '@', usa o email inteiro como login (para evitar erros)
                orientador.setLogin(email);
            }

            Orientador novoOrientador = orientadorRepository.save(orientador);
            return new ResponseEntity<>(novoOrientador, HttpStatus.CREATED);

        } catch (Exception e) {
            System.err.println("Erro ao salvar orientador: " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Endpoint para receber o cadastro de Projeto Acadêmico do frontend[cite: 576, 579].
     */
    @PostMapping("/cadastro/projetoAcademico")
    public ResponseEntity<ProjetoAcademico> cadastrarProjeto(@RequestBody ProjetoAcademico projeto) {
        try {
            // O formulário em app.js não envia 'codigo' ou 'status_projeto'
            if (projeto.getCodigo() == null) {
                projeto.setCodigo("PROJ-" + (System.currentTimeMillis() % 100000)); // [cite: 304]
            }
            if (projeto.getStatusProjeto() == null) {
                projeto.setStatusProjeto(StatusProjeto.ATIVO); // [cite: 305, 668]
            }

            ProjetoAcademico novoProjeto = projetoAcademicoRepository.save(projeto);
            return new ResponseEntity<>(novoProjeto, HttpStatus.CREATED);

        } catch (Exception e) {
            System.err.println("Erro ao salvar projeto: " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}