import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        FileSystemSimulator fs = new FileSystemSimulator();
        fs.carregarSistema();

        Scanner sc = new Scanner(System.in);
        String linha;

        System.out.println("=== Simulador de Sistema de Arquivos ===");
        while (true) {
            System.out.print("> ");
            linha = sc.nextLine();
            String[] cmd = linha.split(" ");
            if (cmd[0].equals("sair")) break;

            try {
                switch (cmd[0]) {
                    case "criar_diretorio": fs.criarDiretorio(cmd[1]); break;
                    case "apagar_diretorio": fs.apagarDiretorio(cmd[1]); break;
                    case "renomear_diretorio": fs.renomearDiretorio(cmd[1], cmd[2]); break;
                    case "criar_arquivo": fs.criarArquivo(cmd[1]); break;
                    case "copiar_arquivo": fs.copiarArquivo(cmd[1], cmd[2]); break;
                    case "apagar_arquivo": fs.apagarArquivo(cmd[1]); break;
                    case "renomear_arquivo": fs.renomearArquivo(cmd[1], cmd[2]); break;
                    case "listar": fs.listarDiretorio(cmd[1]); break;
                    case "log": fs.mostrarJournal(); break;
                    default: System.out.println("Comando desconhecido.");
                }
            } catch (Exception e) {
                System.out.println("Erro no comando: " + e.getMessage());
            }

            fs.salvarSistema(); // salva após cada operação
        }

        sc.close();
    }
}
