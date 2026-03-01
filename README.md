# Sistema-Gestao-Hospitalar

Sistema de gerenciamento hospitalar desenvolvido para a disciplina de Estrutura de Dados.

Nosso sistema não é apenas um código isolado; ele simula a vida real. Usamos a AVL para busca eficiente, a Hash para o estoque, e a Heap para salvar vidas priorizando quem mais precisa, enquanto a Fila de duas pilhas organiza o fluxo burocrático da triagem.

⚙️ Núcleo do SistemaFila (Baseada em 2 Pilhas): Organiza o fluxo burocrático da triagem.Implementação: Cada Pilha é construída sobre uma Lista Simplesmente Encadeada.Árvore AVL (BST Balanceada): Busca eficiente de prontuários de pacientes.Implementação: Rotações automáticas para manter altura $O(\log n)$.Heap Binária (Max-Heap): Salva vidas priorizando quem mais precisa.Implementação: Fila de prioridades para casos críticos de UTI/Emergência.Tabela Hash (Chaining): Controle instantâneo do estoque de medicamentos.Implementação: Tratamento de colisões via encadeamento lateral.

📊 Análise de ComplexidadeEstruturaFunção PrincipalComplexidade (Pior Caso)Lista EncadeadaBase para Pilha/Fila$O(1)$ para inserção no topo.Fila (2 Pilhas)Fluxo de Triagem$O(1)$ (Amortizado) para Enqueue/Dequeue.Árvore AVLBusca de Pacientes$O(\log n)$ garantido pelo balanceamento.Heap BináriaPrioridade de Risco$O(\log n)$ para extração do maior risco.Tabela HashEstoque de Remédios$O(1)$ médio para busca e inserção.
