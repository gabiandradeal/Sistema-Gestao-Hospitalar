## 🏥 Sistema de Gestão Hospitalar
Projeto desenvolvido para a disciplina de Estrutura de Dados.
Nosso sistema não é apenas um código isolado; ele simula a vida real. Usamos a **AVL** para o prontuário geral, a **Hash** para a gestão crítica da UTI, e a **Heap** para priorizar emergências, enquanto a **Fila de duas pilhas** organiza o fluxo da triagem comum. O diferencial está no gerenciamento inteligente que alterna entre as filas para evitar a espera infinita de casos leves (Starvation).

## ⚙️ Núcleo do Sistema
O projeto foi estruturado para que cada operação hospitalar utilize a estrutura de dados mais eficiente para o seu propósito:

###  Fila (Baseada em 2 Pilhas - Quack)
* **Função:** Organiza o fluxo de pacientes com quadros leves (Azul/Verde).
* **Implementação:** Composta por duas instâncias de Pilha sobre **Lista Simplesmente Encadeada**. Utiliza uma lógica de transferência entre pilhas para garantir a ordem FIFO (First-In, First-Out) de forma eficiente.

###  Árvore AVL (BST Balanceada)
* **Função:** Armazenamento e busca de prontuários históricos via Nome e CPF.
* **Implementação:** Árvore Binária de Busca com **rotações automáticas** (LL, RR, LR, RL), garantindo que a altura permaneça em $O(\log n)$ e as buscas sejam sempre rápidas e estáveis.

###  Heap Binária (Max-Heap)
* **Função:** Fila de prioridades para pacientes em estado crítico (Vermelho/Laranja).
* **Implementação:** Garante que o paciente com maior urgência clínica seja movido para o topo da estrutura através de operações de *shift-up* e *shift-down* em $O(\log n)$, independentemente da ordem de chegada.

###  Tabela Hash (Chaining)
* **Função:** Gestão de leitos e acesso imediato a pacientes internados na **UTI**.
* **Implementação:** Utiliza o CPF como chave para acesso imediato. Eventuais colisões são resolvidas através de **encadeamento lateral** com listas encadeadas, garantindo integridade nos dados críticos.

## 🔄 Fluxo de Funcionamento

O sistema opera através de uma interface interativa de terminal (CLI), oferecendo 6 operações principais que cobrem toda a jornada do paciente — desde a admissão e triagem até o desfecho clínico e internação na UTI.
Para compreender visualmente, o fluxo geral do software pode ser observado com mais detalhes no nosso [Diagrama de Atividades](assets/DiagramaDeAtividades_SistemaDeGestaoHospitalar.png) desenvolvido segundo o padrão UML 2.


## 📊 Análise de Complexidade

| Estrutura | Função Principal | Complexidade (Tempo) | Nota Técnica |
| :--- | :--- | :--- | :--- |
| **Lista Encadeada** | Base para Pilha/Fila | $O(1)$ | Inserção/Remoção constante no topo. |
| **Quack (2 Pilhas)** | Triagem Geral | $O(1)$ Amortizado | Eficiência constante na maioria das operações. |
| **Árvore AVL** | Prontuário Geral | $O(\log n)$ | Custo logarítmico garantido pelo balanceamento. |
| **Heap Binária** | Fila de Emergência | $O(\log n)$ | Reordenação baseada na altura da árvore completa. |
| **Tabela Hash** | Unidade de UTI | $O(1)$ Médio | Acesso direto via Hash (Pior caso teórico $O(n)$). |

---
*Desenvolvido como parte do projeto integrador de Estrutura de Dados - UEPB.*
