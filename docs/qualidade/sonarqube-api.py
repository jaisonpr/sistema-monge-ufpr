import requests
import json
import pandas as pd
import time

# --- CONFIGURAÇÃO DO USUÁRIO ---
monge = {
    "token": "squ_4c1f778897432fab25781203ad837ae2f7a29322", 
    "project_name": "monge-api",
    "prefix": "monge",
    # "rule": "java:S3776",  <-- COMENTADO PARA TRAZER TUDO
    "sonar_url": "http://localhost:9000"
}

def run_full_extraction():
    print(f"--- Iniciando extração TOTAL para: {monge['project_name']} ---")
    
    url = f"{monge['sonar_url']}/api/issues/search"
    all_issues = []
    page = 1
    page_size = 500 # Máximo permitido pelo SonarQube
    
    while True:
        print(f"Buscando página {page}...")
        
        params = {
            "componentKeys": monge['project_name'],
            # "rules": monge['rule'], # REMOVIDO: Traz todas as regras
            "ps": page_size,
            "p": page,
            "additionalFields": "_all",
            "statuses": "OPEN,CONFIRMED,REOPENED" # Opcional: foca em dívida ativa (ignora o que já foi fechado)
        }
        
        try:
            response = requests.get(url, params=params, auth=(monge['token'], ''))
            response.raise_for_status()
            data = response.json()
            
            batch = data.get('issues', [])
            total_server = data.get('total', 0)
            
            if not batch:
                break # Sai do loop se não houver mais issues
                
            all_issues.extend(batch)
            
            print(f"   - Recuperadas {len(batch)} issues nesta página.")
            
            # Se o número de issues retornadas for menor que o tamanho da página, acabaram os dados.
            if len(batch) < page_size:
                break
                
            page += 1
            
        except requests.exceptions.RequestException as e:
            print(f"Erro fatal na página {page}: {e}")
            break

    print(f"\nTotal final recuperado: {len(all_issues)} issues.")

    if not all_issues:
        return

    # --- 1. Salvar JSON Bruto (Backup Completo) ---
    filename_json = f"{monge['prefix']}_ALL_issues.json"
    with open(filename_json, 'w', encoding='utf-8') as f:
        json.dump(all_issues, f, indent=4)

    # --- 2. Processar para CSV (Planilha Geral) ---
    structured_data = []
    for issue in all_issues:
        # Tratamento seguro para textRange (nem toda issue tem linha de código associada)
        text_range = issue.get('textRange') or {}
        file_path = issue.get('component', '').split(':')[-1]
        
        structured_data.append({
            'key': issue.get('key'),
            'rule': issue.get('rule'),           # Importante para ver qual regra violou
            'severity': issue.get('severity'),   # BLOCKER, CRITICAL, MAJOR...
            'type': issue.get('type'),           # CODE_SMELL, BUG, VULNERABILITY
            'file': file_path,
            'line': issue.get('line', ''),       # Linha principal
            'effort': issue.get('debt', '0min'), # O tempo para corrigir
            'message': issue.get('message'),
            'author': issue.get('author', 'unknown'),
            'creation_date': issue.get('creationDate'),
            'status': issue.get('status')
        })
        
    df = pd.DataFrame(structured_data)
    
    # Ordenar por Data de Criação (do mais antigo para o novo)
    df.sort_values(by='creation_date', inplace=True)
    
    filename_csv = f"{monge['prefix']}_ALL_issues.csv"
    df.to_csv(filename_csv, index=False)
    
    print(f"\n[SUCESSO] Arquivos gerados:")
    print(f" 1. JSON (Raw): {filename_json}")
    print(f" 2. CSV (Excel): {filename_csv}")

if __name__ == "__main__":
    run_full_extraction()