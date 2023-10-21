import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class CmdParser {
    public static CmdArgs parseCmdArgs(String[] args) {
        if (args.length != 4) {
            throw new IllegalArgumentException("Invalid number of arguments.");
        }

        Cargo tipoCargo = parseTipoCargo(args[0]);
        String caminhoArquivoCandidatos = parseCaminhoArquivoCandidatos(args[1]);
        String caminhoArquivoVotacao = parseCaminhoArquivoVotacao(args[2]);
        LocalDate data = parseData(args[3]);

        return new CmdArgs(tipoCargo, caminhoArquivoCandidatos, caminhoArquivoVotacao, data);
    }

    private static Cargo parseTipoCargo(String tipoCargoStr) {
        if (tipoCargoStr.equals("--estadual")) {
            return Cargo.DEPUTADO_ESTADUAL;
        }
        else if (tipoCargoStr.equals("--federal")) {
            return Cargo.DEPUTADO_FEDERAL;
        }
        else {
            throw new IllegalArgumentException("Invalid <opcao_de_cargo>. Valid options are: --estadual, --federal.");
        }
    }

    private static String parseCaminhoArquivoCandidatos(String caminhoArquivoCandidatos) {
        if (!caminhoArquivoCandidatos.endsWith(".csv")) {
            throw new IllegalArgumentException("Invalid file extension for <caminho_arquivo_candidatos>. Valid extension is: `.csv`.");
        }

        try {
            Paths.get(caminhoArquivoCandidatos);
        } catch (InvalidPathException | NullPointerException ex) {
            throw new IllegalArgumentException("Invalid file path for <caminho_arquivo_candidatos>.");
        }

        return caminhoArquivoCandidatos;
    }

    private static String parseCaminhoArquivoVotacao(String caminhoArquivoVotacao) {
        if (!caminhoArquivoVotacao.endsWith(".csv")) {
            throw new IllegalArgumentException("Invalid file extension for <caminho_arquivo_votacao>. Valid extension is: `.csv`.");
        }

        try {
            Paths.get(caminhoArquivoVotacao);
        } catch (InvalidPathException | NullPointerException ex) {
            throw new IllegalArgumentException("Invalid file path for <caminho_arquivo_votacao>.");
        }

        return caminhoArquivoVotacao;
    }

    private static LocalDate parseData(String dataStr) {
        try {
            return LocalDate.parse(dataStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        } catch (DateTimeParseException ex) {
            throw new IllegalArgumentException("Invalid date format for <data>. Valid format is: `dd/MM/yyyy`.");
        }
    }
}
