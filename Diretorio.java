import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Diretorio implements Serializable {
    private String nome;
    private Map<String, Arquivo> arquivos = new HashMap<>();
    private Map<String, Diretorio> subdiretorios = new HashMap<>();

    public Diretorio(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String novoNome) {
        this.nome = novoNome;
    }

    public Map<String, Arquivo> getArquivos() {
        return arquivos;
    }

    public Map<String, Diretorio> getSubdiretorios() {
        return subdiretorios;
    }

    public void removerSubdiretorio(String nome) {
        subdiretorios.remove(nome);
    }

    @Override
    public String toString() {
        return nome;
    }
}
