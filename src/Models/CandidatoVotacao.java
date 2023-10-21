import java.time.LocalDate;

public class CandidatoVotacao extends Candidato{
    private boolean eleito;
    private int numeroVotos;
    
    public CandidatoVotacao(String numero, Partido partido, String nome, String nomeUrna, LocalDate dataNascimento, boolean sexo, Cargo cargo, boolean eleito, int numeroVotos) {
        super(numero, partido, nome, nomeUrna, dataNascimento, sexo, cargo);
        this.eleito = eleito;
        this.numeroVotos = numeroVotos;
    }
}
