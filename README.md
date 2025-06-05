# 🗂️ Simulador de Sistema de Arquivos em Java

Repositório disponível em: [https://github.com/leduardopacheco/simulador_sistema_arquivos](https://github.com/leduardopacheco/simulador_sistema_arquivos)

Este projeto é um simulador simples de sistema de arquivos que permite a criação, manipulação e remoção de arquivos e diretórios, além de manter um log (journal) das operações realizadas.


## 1. 📂 Descrição do Sistema de Arquivos

Um **sistema de arquivos** é uma estrutura de dados utilizada pelo sistema operacional para controlar como os dados são armazenados e recuperados em dispositivos de armazenamento, como HDs, SSDs e pendrives. Ele organiza os dados em arquivos e diretórios, permitindo que os usuários acessem, modifiquem e organizem informações de forma eficiente e segura.

### 🔹 Importância

A principal função de um sistema de arquivos é fornecer um método confiável de armazenamento e recuperação de dados. Sem ele, os dados seriam armazenados como um bloco contínuo de bits, dificultando sua localização e gerenciamento. Além disso, sistemas de arquivos modernos oferecem suporte a permissões, criptografia, compressão e recursos de tolerância a falhas.

---

## 🧾 Journaling em Sistemas de Arquivos

**Journaling** é uma técnica usada por sistemas de arquivos para garantir a integridade dos dados em caso de falhas, como quedas de energia ou travamentos do sistema. Ele funciona registrando mudanças em um log (diário) antes de aplicá-las ao sistema de arquivos principal. Caso ocorra uma falha, o sistema pode usar esse log para restaurar o estado consistente anterior.

### 🎯 Propósito

- Evitar corrupção de dados em situações inesperadas
- Melhorar a recuperação após falhas
- Reduzir a necessidade de verificações longas após reinicializações

### ⚙️ Funcionamento

1. Uma operação (como criar ou mover um arquivo) é registrada no journal.
2. Após a confirmação da gravação no journal, a operação é aplicada ao sistema de arquivos.
3. Quando concluída com sucesso, a entrada no journal pode ser descartada.

### 🧩 Tipos de Journaling

- **Write-Ahead Logging (WAL):** Registra todas as operações no log antes de modificar os dados reais. Usado em sistemas como ext3 (modo ordered).
- **Log-Structured File System:** Trata o armazenamento como um log contínuo, onde todas as operações são gravadas sequencialmente. Isso pode melhorar o desempenho em certas cargas de trabalho.
- **Metadata Journaling:** Apenas as alterações de metadados (como nomes de arquivos e permissões) são registradas no journal. Mais rápido, porém menos seguro que o journaling completo.

## 2. Estrutura de Dados e Journaling

### Estrutura de Dados

Para representar o sistema de arquivos, utilizamos uma abordagem orientada a objetos com classes Java que simulam a hierarquia de diretórios e arquivos de um sistema real. As principais estruturas são:

- **`Diretorio`**: Representa um diretório no sistema de arquivos. Ele contém:
    - Um nome.
    - Um mapa de subdiretórios (`Map<String, Diretorio>`) que permite a criação de hierarquias.
    - Um mapa de arquivos (`Map<String, Arquivo>`) que armazena os arquivos diretamente contidos neste diretório.

- **`Arquivo`**: Representa um arquivo, contendo:
    - Um nome.
    - Um conteúdo (como uma `String`, podendo ser expandido futuramente para incluir outros metadados).

- **`FileSystemSimulator`**: Classe principal que gerencia a árvore de diretórios. Ela mantém:
    - Uma referência ao diretório raiz (`raiz`).
    - Uma instância de `Journal` que registra todas as operações.
    - Métodos para criar, renomear, apagar, copiar e listar arquivos e diretórios.

Essas estruturas permitem simular uma navegação semelhante ao funcionamento de sistemas Unix-like, com diretórios e arquivos organizados em uma hierarquia de caminhos.

---

### Journaling

O sistema implementa um **mecanismo de journaling** simples por meio da classe `Journal`, que tem o objetivo de registrar todas as operações realizadas no sistema de arquivos.  
O tipo de journaling adotado é um **journaling de metadados em nível de operação**, **não transacional**, focado exclusivamente no registro textual das ações, sem controle de rollback ou commit.

Ele oferece:

- **Registro de Operações**:
    - Cada ação significativa (como criação, remoção ou renomeação de arquivos/diretórios) é registrada com um timestamp.
    - O método `registrar(String entrada)` adiciona entradas no log e também imprime na saída padrão com o prefixo `[JOURNAL]`.

- **Persistência do Log**:
    - O log é salvo em um arquivo `journal.log` usando o método `salvar(String caminho)`, permitindo persistência das operações realizadas.
    - Na inicialização do sistema, o log é recarregado com `carregar(String caminho)`.

- **Visualização do Log**:
    - A função `mostrarLog()` imprime o histórico completo das operações no console.

- **Formato das Entradas**:
    - Cada entrada é registrada no seguinte formato:
      ```
      [yyyy-MM-dd HH:mm:ss] Descrição da operação
      ```

- **Exemplo de entradas**:
    - `[2025-06-05 12:30:12] Criado diretório: /projetos/teste`
    - `[2025-06-05 12:30:45] Arquivo copiado: /projetos/relatorio.txt → /backup/relatorio.txt`

Este mecanismo simples de journaling é importante para depuração e pode futuramente ser estendido para permitir recuperação automática de falhas ou auditoria de operações.

---

## 3. ✅ Funcionalidades

- ✅ Criar diretórios e subdiretórios
- ✅ Criar, copiar, renomear e apagar arquivos
- ✅ Renomear e apagar diretórios
- ✅ Listar o conteúdo de diretórios
- ✅ Registro (journal) persistente de todas as ações realizadas
- ✅ Salvamento automático do estado do sistema em arquivo `.dat`

---

## 4. 🚀 Como Executar

### Pré-requisitos
- Java 8 ou superior

### Compilação

```bash
javac *.java
```

### Execução

```bash
java Main
```

---

## 🧾 Comandos Disponíveis

```bash
criar_diretorio /caminho
apagar_diretorio /caminho
renomear_diretorio /caminho novo_nome
criar_arquivo /caminho/arquivo.txt
copiar_arquivo /origem.txt /destino.txt
apagar_arquivo /caminho/arquivo.txt
renomear_arquivo /caminho/arquivo.txt novo_nome.txt
listar /caminho
log
sair
```

---

## 🧠 Observações Técnicas

- Toda operação executada é salva automaticamente em `base.dat` e registrada em `journal.log`.
- O sistema utiliza `Serializable` para persistir o estado do sistema de arquivos em disco.
- A classe `Journal` permite que o sistema retome o histórico mesmo após reinicialização.

---

## 📌 Autor

Projeto acadêmico desenvolvido por **Luiz Eduardo Pacheco e Alexsandro Lopes**