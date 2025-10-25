// js/app.js

const API_URL = 'http://localhost:8080/api/v1';

/* -------------------------
   Helpers: submenu / menu
   ------------------------- */
function toggleSubmenu(event) {
    event.preventDefault();
    const submenu = document.getElementById('submenuCadastros');
    const arrow = event.currentTarget.querySelector('.menu-arrow');
    submenu.classList.toggle('expanded');
    arrow.classList.toggle('expanded');
}

function abrirSubmenu() {
    const submenu = document.getElementById('submenuCadastros');
    const arrow = document.querySelector('.menu-arrow');
    if (submenu) submenu.classList.add('expanded');
    if (arrow) arrow.classList.add('expanded');
}

function fecharSubmenu() {
    const submenu = document.getElementById('submenuCadastros');
    const arrow = document.querySelector('.menu-arrow');
    if (submenu) submenu.classList.remove('expanded');
    if (arrow) arrow.classList.remove('expanded');
}

function toggleMenu() {
    const sidebar = document.getElementById('sidebar');
    if (sidebar) sidebar.classList.toggle('active');
}

/* -------------------------
   Navegação (SPA-like)
   ------------------------- */
function navegarPara(pagina) {
    // esconder todas as páginas
    document.querySelectorAll('.page-principal, .page-cadastro-bolsista, .page-cadastro-orientador, .page-cadastro-projeto-academico, .page-vincular-bolsista-projeto, .page-lancamento-semanal')
        .forEach(page => page.style.display = 'none');

    // remover classe active de itens do menu e submenu
    document.querySelectorAll('.menu-item, .submenu-item')
        .forEach(item => item.classList.remove('active'));

    // mostrar a página correspondente e atualizar URL
    if (pagina === 'principal') {
        const page = document.getElementById('pagePrincipal');
        if (page) page.style.display = 'block';
        const firstMenu = document.querySelectorAll('.menu-item')[0];
        if (firstMenu) firstMenu.classList.add('active');
        window.history.pushState({}, '', `${window.location.origin}/`);
        fecharSubmenu();
    } else if (pagina === 'cadastro-bolsista') {
        const page = document.getElementById('pageCadastroBolsista');
        if (page) page.style.display = 'block';
        const firstSub = document.querySelectorAll('.submenu-item')[0];
        if (firstSub) firstSub.classList.add('active');
        window.history.pushState({}, '', `${window.location.origin}/cadastro/bolsista/`);
        abrirSubmenu();
    } else if (pagina === 'cadastro-orientador') {
        const page = document.getElementById('pageCadastroOrientador');
        if (page) page.style.display = 'block';
        const secondSub = document.querySelectorAll('.submenu-item')[1];
        if (secondSub) secondSub.classList.add('active');
        window.history.pushState({}, '', `${window.location.origin}/cadastro/orientador/`);
        abrirSubmenu();
    } else if (pagina === 'cadastro-projeto-academico') {
        const page = document.getElementById('pageCadastroProjetoAcademico');
        if (page) page.style.display = 'block';
        const thirdSub = document.querySelectorAll('.submenu-item')[2];
        if (thirdSub) thirdSub.classList.add('active');
        window.history.pushState({}, '', `${window.location.origin}/cadastro/projetoAcademico/`);
        abrirSubmenu();
    } else if (pagina === 'vincular-bolsista-projeto') {
        const page = document.getElementById('pageVincularBolsistaProjeto');
        if (page) page.style.display = 'block';
        const fourthMenu = document.querySelectorAll('.menu-item')[3]; 
        if (fourthMenu) fourthMenu.classList.add('active');
        window.history.pushState({}, '', `${window.location.origin}/vincularBolsistaProjeto/`);
        fecharSubmenu();

        // ADICIONADO: Carrega os dados dos dropdowns
        loadBolsistas();
        loadProjetos();
        loadOrientadores();
    } else if (pagina === 'lancamento-semanal') {
        const page = document.getElementById('pageLancamentoSemanal');
        if (page) page.style.display = 'block';
        const fifthMenu = document.querySelectorAll('.menu-item')[4];
        if (fifthMenu) fifthMenu.classList.add('active')
        window.history.pushState({}, '', `${window.location.origin}/lancamentoSemanal/`);
        fecharSubmenu();

        loadBolsistasLancamento();   
        loadProjetosLancamento();    
    }

    // fechar sidebar no mobile
    if (window.innerWidth <= 768) {
        const sidebar = document.getElementById('sidebar');
        if (sidebar) sidebar.classList.remove('active');
    }
}

/* -------------------------
   Chamadas à API (Página Principal)
   ------------------------- */
async function chamarHello() {
  const nome = document.getElementById('nomeInput').value.trim();
  const resultadoDiv = document.getElementById('resultadoHello');
  if (!resultadoDiv) return;

  if (!nome) {
    resultadoDiv.textContent = 'Por favor, digite um nome!';
    return;
  }

  try {
    resultadoDiv.textContent = 'Carregando...';
    const response = await axios.get(`${API_URL}/hello/${encodeURIComponent(nome)}`);
    resultadoDiv.textContent = typeof response.data === 'string'
      ? response.data
      : JSON.stringify(response.data, null, 2);
  } catch (error) {
    resultadoDiv.textContent = `Erro: ${getErrorMessage(error)}`;
  }
}

async function chamarStatus() {
  const resultadoDiv = document.getElementById('resultadoStatus');
  if (!resultadoDiv) return;

  try {
    resultadoDiv.textContent = 'Carregando...';
    const response = await axios.get(`${API_URL}/status`);
    resultadoDiv.textContent = JSON.stringify(response.data, null, 2);
  } catch (error) {
    resultadoDiv.textContent = `Erro: ${getErrorMessage(error)}`;
  }
}

/* -------------------------------------------
   LÓGICA PÁGINA VINCULAR (ADICIONADA)
   ------------------------------------------- */

// 1. Carrega Bolsistas
async function loadBolsistas() {
  const select = document.getElementById('selectBolsista');
  if (!select) return;
  try {
    const response = await axios.get(`${API_URL}/bolsistas`);
    select.innerHTML = '<option value="">Selecione um bolsista</option>';
    response.data.forEach(bolsista => {
      const option = document.createElement('option');
      option.value = bolsista.id;
      option.textContent = `${bolsista.nome} (Matr: ${bolsista.matricula})`;
      select.appendChild(option);
    });
  } catch (error) {
    select.innerHTML = '<option value="">Erro ao carregar bolsistas</option>';
  }
}

// 2. Carrega Projetos
async function loadProjetos() {
  const select = document.getElementById('selectProjeto');
  if (!select) return;
  try {
    const response = await axios.get(`${API_URL}/projetos`);
    select.innerHTML = '<option value="">Selecione um projeto</option>';
    response.data.forEach(projeto => {
      const option = document.createElement('option');
      option.value = projeto.id;
      option.textContent = `${projeto.titulo} (Cód: ${projeto.codigo})`;
      select.appendChild(option);
    });
  } catch (error) {
    select.innerHTML = '<option value="">Erro ao carregar projetos</option>';
  }
}

// 3. Carrega Orientadores
async function loadOrientadores() {
  const select = document.getElementById('selectOrientador');
  if (!select) return;
  try {
    const response = await axios.get(`${API_URL}/orientadores`);
    select.innerHTML = '<option value="">Selecione um orientador</option>';
    response.data.forEach(orientador => {
      const option = document.createElement('option');
      option.value = orientador.id;
      option.textContent = `${orientador.nome} (SIAPE: ${orientador.siape})`;
      select.appendChild(option);
    });
  } catch (error) {
    select.innerHTML = '<option value="">Erro ao carregar orientadores</option>';
  }
}

// 4. Função para Vincular
async function vincularBolsistaProjeto() {
  const bolsistaId = document.getElementById('selectBolsista').value;
  const projetoId = document.getElementById('selectProjeto').value;
  const orientadorId = document.getElementById('selectOrientador').value;
  const resultadoDiv = document.getElementById('resultadoVincular');
  
  if (!resultadoDiv) return;

  if (!bolsistaId || !projetoId || !orientadorId) {
    resultadoDiv.textContent = 'Erro: Todos os campos são obrigatórios!';
    resultadoDiv.style.color = 'red';
    return;
  }

  const vinculoData = {
    bolsistaId: parseInt(bolsistaId),
    projetoId: parseInt(projetoId),
    orientadorId: parseInt(orientadorId),
    dataVinculacao: document.getElementById('dataVinculacao').value,
  };

  try {
    resultadoDiv.textContent = 'Vinculando...';
    // Assumindo que seu endpoint de POST é /bolsista-projeto
    const response = await axios.post(`${API_URL}/bolsista-projeto`, vinculoData);
    resultadoDiv.textContent = 'Bolsista vinculado com sucesso!';
    limparFormularioVincularBolsistaProjeto();
  } catch (error) {
    resultadoDiv.textContent = `Erro ao vincular: ${getErrorMessage(error)}`;
  }
}


/* -------------------------
   Envio de formulários (Cadastros)
   ------------------------- */
async function enviarCadastroBolsista(event) {
  event.preventDefault();
  const resultadoDiv = document.getElementById('resultadoCadastroBolsista');
  if (!resultadoDiv) return;

  resultadoDiv.style.display = 'block';
  resultadoDiv.textContent = 'Enviando cadastro de bolsista...';

  const dados = {
    nome: document.getElementById('nomeBolsista').value,
    email: document.getElementById('emailBolsista').value,
    matricula: document.getElementById('matriculaBolsista').value,
    cpf: document.getElementById('cpfBolsista').value,
    endereco: document.getElementById('enderecoBolsista').value,
    telefone: document.getElementById('telefoneBolsista').value,
    cargaHorariaSemanal: document.getElementById('cargaHorariaBolsista').value
  };

  try {
    const response = await axios.post(`${API_URL}/cadastro/bolsista`, dados);
    resultadoDiv.textContent = 'Bolsista cadastrado com sucesso!\n\n' + JSON.stringify(response.data, null, 2);
    limparFormularioBolsista();
  } catch (error) {
    resultadoDiv.textContent = `Erro ao cadastrar bolsista: ${getErrorMessage(error)}`;
  }
}

async function enviarCadastroOrientador(event) {
  event.preventDefault();
  const resultadoDiv = document.getElementById('resultadoCadastroOrientador');
  if (!resultadoDiv) return;

  resultadoDiv.style.display = 'block';
  resultadoDiv.textContent = 'Enviando cadastro de orientador...';

  const dados = {
    nome: document.getElementById('nomeOrientador').value,
    email: document.getElementById('emailOrientador').value,
    telefoneContato: document.getElementById('telefoneOrientador').value,
    departamento: document.getElementById('departamentoOrientador').value,
    titulacao: document.getElementById('titulacao').value,
    siape: document.getElementById('siape').value
  };

  try {
    const response = await axios.post(`${API_URL}/cadastro/orientador`, dados);
    resultadoDiv.textContent = 'Orientador cadastrado com sucesso!\n\n' + JSON.stringify(response.data, null, 2);
    limparFormularioOrientador();
  } catch (error) {
    resultadoDiv.textContent = `Erro ao cadastrar orientador: ${getErrorMessage(error)}`;
  }
}

async function enviarCadastroProjetoAcademico(event) {
  event.preventDefault();
  const resultadoDiv = document.getElementById('resultadoCadastroProjetoAcademico');
  if (!resultadoDiv) return;

  resultadoDiv.style.display = 'block';
  resultadoDiv.textContent = 'Enviando cadastro de projeto academico...';

  const dados = {
    titulo: document.getElementById('nomeProjetoAcademico').value,
    descricao: document.getElementById('decricaoProjetoAcademico').value,
    unidadeVinculada: document.getElementById('unidadeVinculadaProjetoAcademico').value,
    programaFomento: document.getElementById('programaFomentoProjetoAcademico').value,
    orcamento: document.getElementById('orcamentoProjetoAcademico').value,
    dataInicio: document.getElementById('dataInicioProjetoAcademico').value,
  };

  try {
    const response = await axios.post(`${API_URL}/cadastro/projetoAcademico`, dados);
    resultadoDiv.textContent = 'Projeto Academico cadastrado com sucesso!\n\n' + JSON.stringify(response.data, null, 2);
    limparFormularioProjetoAcademico();
  } catch (error) {
    resultadoDiv.textContent = `Erro ao cadastrar Projeto Academico: ${getErrorMessage(error)}`;
  }
}

/* -------------------------
   Limpar formulários
   ------------------------- */
function limparFormularioBolsista() {
  const form = document.getElementById('formCadastroBolsista');
  if (form) form.reset();
}

function limparFormularioOrientador() {
  const form = document.getElementById('formCadastroOrientador');
  if (form) form.reset();
}

function limparFormularioProjetoAcademico() {
  const form = document.getElementById('formCadastroProjetoAcademico');
  if (form) form.reset();
}

function limparFormularioVincularBolsistaProjeto() {
  const form = document.getElementById('formVincularBolsistaProjeto');
  if (form) form.reset();
}

/* -------------------------
   Utilidades
   ------------------------- */
function getErrorMessage(error) {
  if (!error) return 'Erro desconhecido';
  if (error.response && error.response.data) {
    try {
      return typeof error.response.data === 'string'
        ? error.response.data
        : JSON.stringify(error.response.data);
    } catch (e) {
      return String(error.response.data);
    }
  }
  return error.message || String(error);
}

/* -------------------------
   Inicialização e histórico
   ------------------------- */
document.addEventListener('DOMContentLoaded', function () {
    // Atalhos: Enter no campo nome chama o hello
    const nomeInput = document.getElementById('nomeInput');
    if (nomeInput) {
        nomeInput.addEventListener('keypress', function (e) {
            if (e.key === 'Enter') chamarHello();
        });
    }

    // Tratar popstate (botões voltar/avançar)
    window.addEventListener('popstate', function () {
        const path = window.location.pathname;
        if (path === '/cadastro/bolsista/' || path === '/cadastro/bolsista') {
            navegarPara('cadastro-bolsista');
        } else if (path === '/cadastro/orientador/' || path === '/cadastro/orientador') {
            navegarPara('cadastro-orientador');
        } else if (path === '/cadastro/projetoAcademico/' || path === '/cadastro/projetoAcademico') {
            navegarPara('cadastro-projeto-academico');
        } else if (path === '/vincularBolsistaProjeto/' || path === '/vincularBolsistaProjeto') {
            navegarPara('vincular-bolsista-projeto');
        } else if (path === '/lancamentoSemanal/' || path === '/lancamentoSemanal') {
            navegarPara('lancamento-semanal');
        } else {
            navegarPara('principal')
        }
    });

    // Detectar URL inicial e navegar para a página correta
    const path = window.location.pathname;
    if (path === '/cadastro/bolsista/' || path === '/cadastro/bolsista') {
        navegarPara('cadastro-bolsista');
    } else if (path === '/cadastro/orientador/' || path === '/cadastro/orientador') {
        navegarPara('cadastro-orientador');
    } else if (path === '/cadastro/projetoAcademico/' || path === '/cadastro/projetoAcademico') {
        navegarPara('cadastro-projeto-academico');
    } else if (path === '/vincularBolsistaProjeto/' || path === '/vincularBolsistaProjeto') {
        navegarPara('vincular-bolsista-projeto');
    } else if (path === '/lancamentoSemanal/' || path === '/lancamentoSemanal') {
        navegarPara('lancamento-semanal');
    } else {
        navegarPara('principal');
    }
    });

async function loadBolsistasLancamento() {
    const select = document.getElementById('selectBolsistaLancamento');
    const res = await axios.get(`${API_URL}/bolsistas`);
    select.innerHTML = '<option value="">Selecione...</option>';
    res.data.forEach(b => {
        const opt = document.createElement('option');
        opt.value = b.id;
        opt.textContent = `${b.nome} (${b.matricula})`;
        select.appendChild(opt);
    });
}

async function loadProjetosLancamento() {
    const select = document.getElementById('selectProjetoLancamento');
    const res = await axios.get(`${API_URL}/projetos`);
    select.innerHTML = '<option value="">Selecione...</option>';
    res.data.forEach(p => {
        const opt = document.createElement('option');
        opt.value = p.id;
        opt.textContent = `${p.titulo} (${p.codigo})`;
        select.appendChild(opt);
    });
}

async function enviarLancamentoSemanal() {
    const resultado = document.getElementById('resultadoLancamento');
    resultado.textContent = 'Enviando...';

    const bolsistaId = document.getElementById('selectBolsistaLancamento').value;
    const projetoId = document.getElementById('selectProjetoLancamento').value;

    const dados = {
        semanaReferencia: document.getElementById('semanaReferencia').value,
        atividadesRealizadas: document.getElementById('atividadesRealizadas').value,
        horasRealizadas: parseInt(document.getElementById('horasRealizadas').value),
        justificativaFalta: document.getElementById('justificativaFalta').value
    };

    try {
        await axios.post(`${API_URL}/lancamentos-semanais/bolsista/${bolsistaId}/projeto/${projetoId}`, dados);
        resultado.textContent = 'Lançamento registrado com sucesso!';
    } catch (error) {
        resultado.textContent = `Erro: ${getErrorMessage(error)}`;
    } 
}
