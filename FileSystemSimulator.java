import java.io.*;
import java.util.*;

public class FileSystemSimulator implements Serializable {
    private Diretorio raiz;
    private Journal journal;

    public FileSystemSimulator() {
        raiz = new Diretorio("/");
        journal = new Journal();
    }

    public void salvarSistema() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("base.dat"))) {
            out.writeObject(raiz);
        } catch (IOException e) {
            System.out.println("Erro ao salvar sistema de arquivos.");
        }
        journal.salvar("journal.log");
    }

    public void carregarSistema() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("base.dat"))) {
            raiz = (Diretorio) in.readObject();
        } catch (Exception ignored) {
            raiz = new Diretorio("/");
        }
        journal.carregar("journal.log");
    }

    private Diretorio navegarParaDiretorio(String caminho) {
        String[] partes = caminho.split("/");
        Diretorio atual = raiz;
        for (String parte : partes) {
            if (!parte.isEmpty()) {
                atual = atual.getSubdiretorios().get(parte);
                if (atual == null) return null;
            }
        }
        return atual;
    }

    public void criarDiretorio(String caminho) {
        String[] partes = caminho.split("/");
        Diretorio atual = raiz;
        for (int i = 1; i < partes.length; i++) {
            String nomeDir = partes[i];
            if (!atual.getSubdiretorios().containsKey(nomeDir)) {
                atual.getSubdiretorios().put(nomeDir, new Diretorio(nomeDir));
                journal.registrar("Criado diretório: " + caminho);
                salvarSistema();
            } else if (i == partes.length - 1) {
                System.out.println("Diretório já existe: " + caminho);
                return;
            }
            atual = atual.getSubdiretorios().get(nomeDir);
        }
    }


    public void apagarDiretorio(String caminho) {
        int index = caminho.lastIndexOf("/");
        String pai = caminho.substring(0, index);
        String nome = caminho.substring(index + 1);
        Diretorio dirPai = navegarParaDiretorio(pai);
        if (dirPai != null && dirPai.getSubdiretorios().containsKey(nome)) {
            dirPai.removerSubdiretorio(nome);
            journal.registrar("Removido diretório: " + caminho);
            salvarSistema();
        }
    }

    public void renomearDiretorio(String caminho, String novoNome) {
        int index = caminho.lastIndexOf("/");
        String pai = caminho.substring(0, index);
        String nomeAtual = caminho.substring(index + 1);
        Diretorio dirPai = navegarParaDiretorio(pai);
        if (dirPai != null && dirPai.getSubdiretorios().containsKey(nomeAtual)) {
            Diretorio dir = dirPai.getSubdiretorios().remove(nomeAtual);
            dir.setNome(novoNome);
            dirPai.getSubdiretorios().put(novoNome, dir);
            journal.registrar("Renomeado diretório: " + caminho + " → " + novoNome);
            salvarSistema();
        }
    }

    public void criarArquivo(String caminho) {
        int index = caminho.lastIndexOf("/");
        if (index == -1 || index == caminho.length() - 1) {
            System.out.println("Caminho inválido para arquivo.");
            return;
        }

        String dirPath = caminho.substring(0, index);
        String nome = caminho.substring(index + 1);
        Diretorio dir = navegarParaDiretorio(dirPath);

        if (dir == null) {
            System.out.println("Diretório não encontrado: " + dirPath);
            return;
        }

        if (dir.getArquivos().containsKey(nome)) {
            System.out.println("Arquivo já existe: " + caminho);
            return;
        }

        dir.getArquivos().put(nome, new Arquivo(nome));
        journal.registrar("Criado arquivo: " + caminho);
        salvarSistema();
    }


    public void copiarArquivo(String origem, String destino) {
        int i1 = origem.lastIndexOf("/");
        int i2 = destino.lastIndexOf("/");
        String dir1 = origem.substring(0, i1);
        String dir2 = destino.substring(0, i2);
        String nomeOrigem = origem.substring(i1 + 1);
        String nomeDestino = destino.substring(i2 + 1);
        Diretorio d1 = navegarParaDiretorio(dir1);
        Diretorio d2 = navegarParaDiretorio(dir2);
        if (d1 != null && d2 != null && d1.getArquivos().containsKey(nomeOrigem)) {
            Arquivo arqOriginal = d1.getArquivos().get(nomeOrigem);
            Arquivo copia = new Arquivo(nomeDestino);
            copia.setConteudo(arqOriginal.getConteudo());
            d2.getArquivos().put(nomeDestino, copia);
            journal.registrar("Arquivo copiado: " + origem + " → " + destino);
            salvarSistema();
        }
    }

    public void apagarArquivo(String caminho) {
        int index = caminho.lastIndexOf("/");
        String dirPath = caminho.substring(0, index);
        String nome = caminho.substring(index + 1);
        Diretorio dir = navegarParaDiretorio(dirPath);
        if (dir != null) {
            dir.getArquivos().remove(nome);
            journal.registrar("Removido arquivo: " + caminho);
            salvarSistema();
        }
    }

    public void renomearArquivo(String caminho, String novoNome) {
        int index = caminho.lastIndexOf("/");
        String dirPath = caminho.substring(0, index);
        String nome = caminho.substring(index + 1);
        Diretorio dir = navegarParaDiretorio(dirPath);
        if (dir != null && dir.getArquivos().containsKey(nome)) {
            Arquivo arq = dir.getArquivos().remove(nome);
            arq.setNome(novoNome);
            dir.getArquivos().put(novoNome, arq);
            journal.registrar("Arquivo renomeado: " + caminho + " → " + novoNome);
            salvarSistema();
        }
    }

    public void listarDiretorio(String caminho) {
        Diretorio dir = navegarParaDiretorio(caminho);
        if (dir != null) {
            System.out.println("📁 " + caminho);
            for (String d : dir.getSubdiretorios().keySet()) {
                System.out.println("  [DIR] " + d);
            }
            for (String f : dir.getArquivos().keySet()) {
                System.out.println("  [ARQ] " + f);
            }
        } else {
            System.out.println("Diretório não encontrado.");
        }
    }

    public void listarEstrutura() {
        listarEstruturaRecursiva(raiz, "");
    }

    private void listarEstruturaRecursiva(Diretorio dir, String prefixo) {
        System.out.println(prefixo + "📁 " + dir.getNome());

        for (Arquivo arquivo : dir.getArquivos().values()) {
            System.out.println(prefixo + "  └── 📄 " + arquivo.getNome());
        }

        for (Diretorio sub : dir.getSubdiretorios().values()) {
            listarEstruturaRecursiva(sub, prefixo + "  ");
        }
    }

    public void mostrarJournal() {
        journal.mostrarLog();
    }
}
