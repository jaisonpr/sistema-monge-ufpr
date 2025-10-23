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
  document.querySelectorAll('.page-principal, .page-cadastro-bolsista, .page-cadastro-orientador')
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
  }

  // fechar sidebar no mobile
  if (window.innerWidth <= 768) {
    const sidebar = document.getElementById('sidebar');
    if (sidebar) sidebar.classList.remove('active');
  }
}

/* -------------------------
   Chamadas à API
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
    // se response.data for string ou objeto, normalizamos para exibição
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

/* -------------------------
   Envio de formulários
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
    curso: document.getElementById('cursoBolsista').value,
    telefone: document.getElementById('telefoneBolsista').value,
    tipoBolsa: document.getElementById('tipoBolsa').value,
    observacoes: document.getElementById('observacoesBolsista').value
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
    telefone: document.getElementById('telefoneOrientador').value,
    departamento: document.getElementById('departamentoOrientador').value,
    titulacao: document.getElementById('titulacao').value,
    areaAtuacao: document.getElementById('areaAtuacao').value,
    observacoes: document.getElementById('observacoesOrientador').value
  };

  try {
    const response = await axios.post(`${API_URL}/cadastro/orientador`, dados);
    resultadoDiv.textContent = 'Orientador cadastrado com sucesso!\n\n' + JSON.stringify(response.data, null, 2);
    limparFormularioOrientador();
  } catch (error) {
    resultadoDiv.textContent = `Erro ao cadastrar orientador: ${getErrorMessage(error)}`;
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
    } else {
      navegarPara('principal');
    }
  });

  // Detectar URL inicial e navegar para a página correta
  const path = window.location.pathname;
  if (path === '/cadastro/bolsista/' || path === '/cadastro/bolsista') {
    navegarPara('cadastro-bolsista');
  } else if (path === '/cadastro/orientador/' || path === '/cadastro/orientador') {
    navegarPara('cadastro-orientador');
  } else {
    navegarPara('principal');
  }
});
