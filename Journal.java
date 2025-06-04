import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Journal implements Serializable {
    private List<String> log = new ArrayList<>();
    private transient SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public void registrar(String entrada) {
        String timestamp = sdf.format(new Date());
        String linha = "[" + timestamp + "] " + entrada;
        log.add(linha);
        System.out.println("[JOURNAL] " + linha);
    }

    public void mostrarLog() {
        System.out.println("==== Log de Operações ====");
        for (String entrada : log) {
            System.out.println(entrada);
        }
    }

    public void salvar(String caminho) {
        File arquivo = new File(caminho);
        try {
            if (!arquivo.exists()) {
                arquivo.createNewFile(); // Cria o arquivo se ele não existir
            }
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivo))) {
                for (String entrada : log) {
                    writer.write(entrada);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar journal: " + e.getMessage());
        }
    }

    public void carregar(String caminho) {
        File arquivo = new File(caminho);
        if (!arquivo.exists()) {
            try {
                arquivo.createNewFile(); // Cria o arquivo vazio se ele não existir
            } catch (IOException e) {
                System.out.println("Erro ao criar arquivo de log: " + e.getMessage());
                return;
            }
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                log.add(linha);
            }
        } catch (IOException ignored) {}
    }
}
