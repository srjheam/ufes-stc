import java.time.LocalDate;

public class CmdArgs {
    private Cargo tipoCargo;
    private String caminhoArquivoCandidatos;
    private String caminhoArquivoVotacao;
    private LocalDate data;

    public CmdArgs(Cargo tipoCargo, String caminhoArquivoCandidatos, String caminhoArquivoVotacao, LocalDate data) {
        this.tipoCargo = tipoCargo;
        this.caminhoArquivoCandidatos = caminhoArquivoCandidatos;
        this.caminhoArquivoVotacao = caminhoArquivoVotacao;
        this.data = data;
    }
}
