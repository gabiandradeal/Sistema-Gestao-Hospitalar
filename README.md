### Sistema-Gestao-Hospitalar

🏥Sistema de gerenciamento hospitalar desenvolvido para a disciplina de Estrutura de Dados.
Nosso sistema não é apenas um código isolado; ele simula a vida real. Usamos a AVL para busca eficiente, a Hash para o estoque, e a Heap para salvar vidas priorizando quem mais precisa, enquanto a Fila de duas pilhas organiza o fluxo burocrático da triagem.

## ⚙️ Núcleo do Sistema
O projeto foi estruturado para que cada operação hospitalar utilize a estrutura de dados mais eficiente para o seu propósito:
###  Fila (Baseada em 2 Pilhas)
* **Função:** Organiza o fluxo burocrático da triagem e recepção.
* **Implementação:** Seguindo os requisitos, a fila é composta por duas instâncias de Pilha, onde cada **Pilha** é construída sobre uma **Lista Simplesmente Encadeada** customizada.

###  Árvore AVL (BST Balanceada)
* **Função:** Busca eficiente e organizada de prontuários de pacientes.
* **Implementação:** Árvore Binária de Busca com **rotações automáticas** (LL, RR, LR, RL), garantindo que a altura permaneça sempre em $O(\log n)$, mesmo com milhares de cadastros.

###  Heap Binária (Max-Heap)
* **Função:** Fila de prioridades que "salva vidas" ao processar emergências.
* **Implementação:** Garante que o paciente com maior nível de risco (maior chave) seja sempre o próximo a ser atendido, independentemente da ordem de chegada.

###  Tabela Hash (Chaining)
* **Função:** Controle instantâneo do estoque de medicamentos.
* **Implementação:** Utiliza uma função de espalhamento para acesso em tempo constante $O(1)$. Eventuais colisões de índices são resolvidas através de **encadeamento lateral** (chaining) com listas.
  
## 📊 Análise de Complexidade

| Estrutura | Função Principal | Complexidade (Pior Caso) |
| :--- | :--- | :--- |
| **Lista Encadeada** | Base para Pilha/Fila | $O(1)$ para inserção no topo |
| **Fila (2 Pilhas)** | Fluxo de Triagem | $O(1)$ (Amortizado) |
| **Árvore AVL** | Busca de Pacientes | $O(\log n)$ |
| **Heap Binária** | Prioridade de Risco | $O(\log n)$ |
| **Tabela Hash** | Estoque de Remédios | $O(1)$ médio |
