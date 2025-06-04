import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Journal implements Serializable {
    private List<String> log = new ArrayList<>();

    public void registrar(String entrada) {
        log.add(entrada);
        System.out.println("[JOURNAL] " + entrada);
    }

    public void mostrarLog() {
        System.out.println("==== Log de Operações ====");
        for (String entrada : log) {
            System.out.println(entrada);
        }
    }

    public void salvar(String caminho) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminho))) {
            for (String entrada : log) {
                writer.write(entrada);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar journal.");
        }
    }

    public void carregar(String caminho) {
        try (BufferedReader reader = new BufferedReader(new FileReader(caminho))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                log.add(linha);
            }
        } catch (IOException ignored) {}
    }
}
