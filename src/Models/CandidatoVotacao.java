import java.time.LocalDate;

public class CandidatoVotacao extends Candidato{
    private boolean eleito;
    private boolean destinacao;
    private int numeroVotos;
 
    public CandidatoVotacao(Cargo cargo, boolean deferido, String numero, String nomeUrna, Partido partido, LocalDate dataNascimento, boolean sexo, boolean eleito, boolean destinacao, int numeroVotos) {
        super(cargo, deferido, numero, nomeUrna, partido, dataNascimento, sexo);
        this.eleito = eleito;
        this.destinacao = destinacao;
        this.numeroVotos = numeroVotos;
    }

}
