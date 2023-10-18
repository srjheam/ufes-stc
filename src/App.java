public class App {
    public static void main(String[] args) throws Exception {
        Validation val = CmdValidator.validateCmdArgs(args);
        if (val.hasErrors()) {
            val.printErrors();

            System.out.println();
            System.out.println("Usage:");
            System.out.println("    java -jar deputados.jar <opção_de_cargo> <caminho_arquivo_candidatos> <caminho_arquivo_votacao> <data>");

            return;
        }

        CmdArgs cmdArgs = CmdParser.parseCmdArgs(args);

        
    }
}
