import requests
import json
import pandas as pd
import time

# --- CONFIGURAÇÃO DO USUÁRIO (MONGE-WEB) ---
config = {
    # Token Project Analysis (sqp_...) fornecido
    "token": "sqp_dc66b63e813c736e7770e2a8d9a257103d149b89", 
    "project_key": "monge-web",  # Chave exata passada no scanner
    "prefix": "monge_web",       # Prefixo para os arquivos de saída
    "sonar_url": "http://localhost:9000"
}

def run_full_extraction():
    print(f"--- Iniciando extração TOTAL para: {config['project_key']} ---")
    
    url = f"{config['sonar_url']}/api/issues/search"
    all_issues = []
    page = 1
    page_size = 500
    
    while True:
        print(f"Buscando página {page}...")
        
        params = {
            "componentKeys": config['project_key'],
            "ps": page_size,
            "p": page,
            "additionalFields": "_all",
            "statuses": "OPEN,CONFIRMED,REOPENED"
        }
        
        try:
            # Autenticação Bearer Token (Padrão novo do SonarQube para tokens sqp_)
            # Se der erro 401, tente usar auth=(token, '') como no script anterior
            response = requests.get(url, params=params, auth=(config['token'], ''))
            
            if response.status_code == 401:
                print("❌ Erro de Autenticação: Verifique se o Token está correto.")
                break
            
            response.raise_for_status()
            data = response.json()
            
            batch = data.get('issues', [])
            
            if not batch:
                break
                
            all_issues.extend(batch)
            print(f"   - Recuperadas {len(batch)} issues nesta página.")
            
            if len(batch) < page_size:
                break
                
            page += 1
            
        except requests.exceptions.RequestException as e:
            print(f"Erro fatal na página {page}: {e}")
            break

    print(f"\nTotal final recuperado: {len(all_issues)} issues.")

    if not all_issues:
        return

    # --- 1. Salvar JSON Bruto ---
    filename_json = f"{config['prefix']}_ALL_issues.json"
    with open(filename_json, 'w', encoding='utf-8') as f:
        json.dump(all_issues, f, indent=4)

    # --- 2. Processar para CSV ---
    structured_data = []
    for issue in all_issues:
        file_path = issue.get('component', '').split(':')[-1]
        
        structured_data.append({
            'key': issue.get('key'),
            'rule': issue.get('rule'),
            'severity': issue.get('severity'),
            'type': issue.get('type'),
            'file': file_path,
            'line': issue.get('line', ''),
            'effort': issue.get('debt', '0min'),
            'message': issue.get('message'),
            'author': issue.get('author', 'unknown'),
            'creation_date': issue.get('creationDate'),
            'status': issue.get('status')
        })
        
    df = pd.DataFrame(structured_data)
    
    # Ordenar por Esforço (Debt) para ver os piores primeiro
    # Nota: ordenação alfabética simples, ideal seria converter para minutos, 
    # mas para visualização rápida funciona.
    df.sort_values(by='effort', ascending=False, inplace=True)
    
    filename_csv = f"{config['prefix']}_ALL_issues.csv"
    df.to_csv(filename_csv, index=False)
    
    print(f"\n[SUCESSO] Arquivos gerados para o Frontend:")
    print(f" 1. JSON: {filename_json}")
    print(f" 2. CSV:  {filename_csv}")

if __name__ == "__main__":
    run_full_extraction()