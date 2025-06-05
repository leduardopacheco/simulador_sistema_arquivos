# 🗂️ Simulador de Sistema de Arquivos em Java

Este projeto é um simulador simples de sistema de arquivos que permite a criação, manipulação e remoção de arquivos e diretórios, além de manter um log (journal) das operações realizadas.

## 📁 Estrutura do Projeto

- `Arquivo.java`: Representa um arquivo com nome e conteúdo.
- `Diretorio.java`: Representa um diretório que pode conter arquivos e subdiretórios.
- `FileSystemSimulator.java`: Núcleo do sistema de arquivos, com funcionalidades de criação, remoção, cópia e listagem.
- `Journal.java`: Registro persistente de todas as operações realizadas.
- `Main.java`: Interface de linha de comando para interação com o simulador.
- `journal.log`: Arquivo de log das operações realizadas.
- `comandos.txt`: Arquivo exemplo com comandos que podem ser usados para teste.

---

## ✅ Funcionalidades

- ✅ Criar diretórios e subdiretórios
- ✅ Criar, copiar, renomear e apagar arquivos
- ✅ Renomear e apagar diretórios
- ✅ Listar o conteúdo de diretórios
- ✅ Registro (journal) persistente de todas as ações realizadas
- ✅ Salvamento automático do estado do sistema em arquivo `.dat`

---

## 🚀 Como Executar

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