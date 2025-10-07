// URL base da API
        const API_URL = 'http://localhost:8080/api/v1';

        // Função para chamar o endpoint hello/{nome}
        async function chamarHello() {
            const nome = document.getElementById('nomeInput').value.trim();
            const resultadoDiv = document.getElementById('resultadoHello');
            
            if (!nome) {
                resultadoDiv.textContent = 'Por favor, digite um nome!';
                return;
            }
            
            try {
                resultadoDiv.textContent = 'Carregando...';
                const response = await axios.get(`${API_URL}/hello/${nome}`);
                resultadoDiv.textContent = response.data;
            } catch (error) {
                resultadoDiv.textContent = `Erro: ${error.message}`;
            }
        }

        // Função para chamar o endpoint status
        async function chamarStatus() {
            const resultadoDiv = document.getElementById('resultadoStatus');
            
            try {
                resultadoDiv.textContent = 'Carregando...';
                const response = await axios.get(`${API_URL}/status`);
                resultadoDiv.textContent = JSON.stringify(response.data, null, 2);
            } catch (error) {
                resultadoDiv.textContent = `Erro: ${error.message}`;
            }
        }

        // Permitir Enter no input
        document.getElementById('nomeInput').addEventListener('keypress', function(e) {
            if (e.key === 'Enter') {
                chamarHello();
            }
        });